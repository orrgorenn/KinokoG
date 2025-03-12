package mapleglory.server.dialog.miniroom;

import mapleglory.packet.field.FieldPacket;
import mapleglory.packet.field.MiniRoomPacket;
import mapleglory.packet.user.UserPacket;
import mapleglory.packet.world.WvsContext;
import mapleglory.provider.ItemProvider;
import mapleglory.provider.item.ItemInfo;
import mapleglory.server.node.ServerExecutor;
import mapleglory.server.packet.InPacket;
import mapleglory.util.Locked;
import mapleglory.world.GameConstants;
import mapleglory.world.item.InventoryOperation;
import mapleglory.world.item.InventoryType;
import mapleglory.world.item.Item;
import mapleglory.world.item.ItemAttribute;
import mapleglory.world.user.User;

import java.util.*;

public final class PersonalShop extends MiniRoom {
    private final String title;
    private final User owner;
    private final Map<Integer, User> guests = new HashMap<>();
    private final List<PlayerShopItem> items = new ArrayList<>();
    private final List<String> blockedList = new ArrayList<>();
    private boolean open = false;

    public PersonalShop(String title, User owner) {
        this.title = title;
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public User getOwner() {
        return owner;
    }

    public boolean isOwner(User user) {
        return owner.getCharacterId() == user.getCharacterId();
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<PlayerShopItem> getItems() {
        return items;
    }

    @Override
    public void handlePacket(Locked<User> locked, MiniRoomProtocol mrp, InPacket inPacket) {
        final User user = locked.get();
        switch (mrp) {
            case PSP_PutItem -> {
                final int targetType = inPacket.decodeByte(); // nTI
                final int targetPosition = inPacket.decodeShort(); // nPos
                final int setCount = inPacket.decodeShort(); // nCount / nSet
                final int setSize = inPacket.decodeShort(); // nSet
                final int price = inPacket.decodeInt(); // nPrice
                // Validate action
                final long totalPrice = ((long) price * setCount);
                final InventoryType inventoryType = InventoryType.getByValue(targetType);
                if (inventoryType == null || inventoryType == InventoryType.EQUIPPED ||
                        targetPosition < 0 || setCount <= 0 || setSize <= 0 || price <= 0 ||
                        totalPrice <= 0 || totalPrice > Integer.MAX_VALUE ||
                        isOpen() || user.getCharacterId() != owner.getCharacterId() ||
                        items.size() >= GameConstants.PLAYER_SHOP_SLOT_MAX) {
                    log.error("Received invalid personal shop action {}", mrp);
                    user.dispose();
                    return;
                }
                // Resolve item
                final int totalCount = setCount * setSize;
                final Item item = user.getInventoryManager().getInventoryByType(inventoryType).getItem(targetPosition);
                if (item == null || item.getQuantity() < totalCount) {
                    log.error("Could not resolve item in inventory type {} position {} for personal shop action {}", inventoryType, targetPosition, mrp);
                    user.dispose();
                    return;
                }
                final Optional<ItemInfo> itemInfoResult = ItemProvider.getItemInfo(item.getItemId());
                if (itemInfoResult.isEmpty()) {
                    log.error("Could not resolve item info for item ID : {}", item.getItemId());
                    user.dispose();
                    return;
                }
                final ItemInfo itemInfo = itemInfoResult.get();
                if ((item.hasAttribute(ItemAttribute.EQUIP_BINDED) || itemInfo.isQuest() || itemInfo.isTradeBlock()) && !item.isPossibleTrading()) {
                    log.error("Tried to put an untradable item into personal shop");
                    user.dispose();
                    return;
                }
                // Move item from inventory to shop
                final Optional<InventoryOperation> removeItemResult = user.getInventoryManager().removeItem(targetPosition, item, totalCount);
                if (removeItemResult.isEmpty()) {
                    throw new IllegalStateException("Could not remove item from inventory");
                }
                if (item.getQuantity() > totalCount) {
                    final Item partialItem = new Item(item);
                    partialItem.setItemSn(user.getNextItemSn());
                    partialItem.setQuantity((short) totalCount);
                    items.add(new PlayerShopItem(partialItem, setCount, setSize, price));
                } else {
                    items.add(new PlayerShopItem(item, setCount, setSize, price));
                }
                user.write(WvsContext.inventoryOperation(removeItemResult.get(), true));
                user.write(MiniRoomPacket.PlayerShop.refresh(items));
            }
            case PSP_BuyItem -> {
                final int itemIndex = inPacket.decodeByte(); // nIdx
                final int setCount = inPacket.decodeShort();
                inPacket.decodeInt(); // ItemCRC
                if (itemIndex < 0 || itemIndex >= items.size() || setCount <= 0 || !isOpen() || user.getCharacterId() == owner.getCharacterId()) {
                    log.error("Received invalid personal shop action {}", mrp);
                    user.dispose();
                    return;
                }

                // Get the item being purchased
                PlayerShopItem shopItem = items.get(itemIndex);

                // Check if requested quantity is available
                if (setCount > shopItem.getSetCount()) {
                    user.write(MiniRoomPacket.PlayerShop.buyResult(PlayerShopBuyResult.NoStock)); // Not enough sets available
                    return;
                }

                // Calculate total price
                int price = shopItem.getPrice() * setCount;

                // Check if buyer has enough mesos
                if (user.getInventoryManager().getMoney() < price) {
                    user.write(MiniRoomPacket.PlayerShop.buyResult(PlayerShopBuyResult.NoMoney));
                    return;
                }

                // Calculate total quantity
                int quantity = setCount * shopItem.getSetSize();

                // Check if quantity exceeds what's available
                if (quantity > shopItem.getSetCount()) {
                    log.error("Requested quantity exceeds available quantity for item at index {}", itemIndex);
                    user.write(MiniRoomPacket.PlayerShop.buyResult(PlayerShopBuyResult.NoStock));
                    user.dispose();
                    return;
                }

                // Create copy of item with appropriate quantity
                Item purchasedItem = getItem(quantity, shopItem, user);

                // Try to add item to buyer's inventory
                Optional<List<InventoryOperation>> addItemResult = user.getInventoryManager().addItem(purchasedItem);
                if (addItemResult.isEmpty()) {
                    user.write(MiniRoomPacket.PlayerShop.buyResult(PlayerShopBuyResult.NoSlot)); // Inventory full
                    return;
                }

                // Remove mesos from buyer
                boolean removeMesosResult = user.getInventoryManager().addMoney(-price);
                if (!removeMesosResult) {
                    log.error("PSP - Failed to remove mesos from user {}", user.getCharacterId());
                    user.dispose();
                    return;
                }

                // addAccumulatedMesos(price);

                // Update the shop's inventory
                if (setCount >= shopItem.getSetCount()) {
                    // Remove item completely if all sets were purchased
                    items.remove(itemIndex);
                } else {
                    // Update remaining quantity
                    shopItem.setSetCount(shopItem.getSetCount() - setCount);
                    if (quantity < shopItem.getItem().getQuantity()) {
                        shopItem.getItem().setQuantity((short)(shopItem.getItem().getQuantity() - quantity));
                    }
                }

                // Notify buyer of successful purchase
                user.write(MiniRoomPacket.PlayerShop.buyResult(PlayerShopBuyResult.Success));

                // Send shop refresh to all users
                broadcastPacket(MiniRoomPacket.PlayerShop.refresh(items));

                // Inform owner about the sale if they're in the shop
                owner.write(MiniRoomPacket.PlayerShop.addSoldItem(itemIndex, setCount, user.getCharacterName()));

                // Log the transaction
                log.info("User {} purchased {}x {} from {}'s shop for {} mesos",
                        user.getCharacterName(), quantity, purchasedItem.getItemId(),
                        owner.getCharacterName(), price);
            }
            case PSP_MoveItemToInventory -> {
                final int itemIndex = inPacket.decodeShort(); // nIdx
                if (itemIndex < 0 || itemIndex >= items.size() || isOpen() || user.getCharacterId() != owner.getCharacterId()) {
                    log.error("Received invalid personal shop action {}", mrp);
                    return;
                }
                final Optional<List<InventoryOperation>> addItemResult = user.getInventoryManager().addItem(items.remove(itemIndex).getItem());
                if (addItemResult.isEmpty()) {
                    throw new IllegalStateException("Could not add item to inventory");
                }
                user.write(WvsContext.inventoryOperation(addItemResult.get(), true));
                user.write(MiniRoomPacket.PlayerShop.refresh(items));
            }
            case PSP_DeliverBlackList -> {
                if (isOpen() || user.getCharacterId() != owner.getCharacterId() || items.isEmpty()) {
                    log.error("Received invalid personal shop action {}", mrp);
                    return;
                }
                final int size = inPacket.decodeShort();
                for (int i = 0; i < size; i++) {
                    blockedList.add(inPacket.decodeString()); // CConfig->m_asBlackList
                }
                setOpen(true);
                owner.getField().broadcastPacket(UserPacket.userMiniRoomBalloon(owner, this));
            }
            default -> {
                log.error("Unhandled personal shop action {}", mrp);
            }
        }
    }

    private static Item getItem(int quantity, PlayerShopItem shopItem, User user) {
        Item purchasedItem;
        if (quantity == shopItem.getItem().getQuantity()) {
            // Buying the whole item
            purchasedItem = shopItem.getItem();
        } else {
            // Buying a portion
            purchasedItem = new Item(shopItem.getItem());
            purchasedItem.setItemSn(user.getNextItemSn());
            purchasedItem.setQuantity((short) quantity);
        }
        return purchasedItem;
    }

    @Override
    public MiniRoomType getType() {
        return MiniRoomType.PersonalShop;
    }

    @Override
    public boolean checkPassword(String password) {
        return true;
    }

    @Override
    public int getMaxUsers() {
        return 3;
    }

    @Override
    public boolean addUser(User user) {
        if (blockedList.stream().anyMatch((name) -> name.equalsIgnoreCase(user.getCharacterName()))) {
            return false;
        }
        for (int i = 1; i <= 3; i++) {
            if (!guests.containsKey(i)) {
                guests.put(i, user);
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<Integer, User> getUsers() {
        final Map<Integer, User> users = new HashMap<>();
        users.put(0, owner);
        users.putAll(guests);
        return users;
    }

    // Utils

    public void leaveUnsafe(User user) {
        assert user.isLocked();
        // Check if user is the owner
        if (user.getCharacterId() == owner.getCharacterId()) {
            // Owner is leaving - need to close the shop

            // First notify all guests that the shop is closing
            for (Map.Entry<Integer, User> entry : guests.entrySet()) {
                User guest = entry.getValue();
                guest.write(MiniRoomPacket.leave(entry.getKey(), LeaveType.HostOut));
                guest.setDialog(null);
            }

            // Return all items to owner's inventory
            for (PlayerShopItem shopItem : items) {
                Optional<List<InventoryOperation>> addItemResult = user.getInventoryManager().addItem(shopItem.getItem());
                if (addItemResult.isEmpty()) {
                    // Log error if item couldn't be returned
                    log.error("Could not return item {} to owner's inventory when closing shop", shopItem.getItem().getItemId());
                    continue;
                }
                user.write(WvsContext.inventoryOperation(addItemResult.get(), true));
            }

            // Clear shop items
            items.clear();

            // Remove shop balloon and close shop
            owner.getField().broadcastPacket(UserPacket.userMiniRoomBalloonRemove(owner));
            user.write(MiniRoomPacket.leave(0, LeaveType.UserRequest));
            user.setDialog(null);

            // Close the shop
            close();
        } else {
            // A guest is leaving
            Integer guestPosition = null;
            for (Map.Entry<Integer, User> entry : guests.entrySet()) {
                if (entry.getValue().getCharacterId() == user.getCharacterId()) {
                    guestPosition = entry.getKey();
                    break;
                }
            }

            if (guestPosition != null) {
                // Remove guest from map
                guests.remove(guestPosition);
                // Notify everyone in the shop that the guest left
                broadcastPacket(MiniRoomPacket.leave(guestPosition, LeaveType.UserRequest));
            } else {
                log.error("User {} tried to leave shop but isn't in the guest list", user.getCharacterId());
            }
        }
    }
}
package mapleglory.provider.item;

import mapleglory.provider.ProviderError;
import mapleglory.provider.WzProvider;
import mapleglory.provider.wz.property.WzListProperty;
import mapleglory.util.Locked;
import mapleglory.world.item.InventoryManager;
import mapleglory.world.user.User;

import java.util.ArrayList;
import java.util.List;

public class ItemRewardInfo {
    private final int itemId;
    private final List<ItemRewardEntry> entries;

    public ItemRewardInfo(int itemId, List<ItemRewardEntry> entries) {
        this.itemId = itemId;
        this.entries = entries;
    }

    public int getItemId() {
        return itemId;
    }

    public List<ItemRewardEntry> getEntries() {
        return entries;
    }

    public boolean canAddReward(Locked<User> locked) {
        final InventoryManager im = locked.get().getInventoryManager();
        for (ItemRewardEntry entry : getEntries()) {
            if (!im.canAddItem(entry.getItemId(), entry.getCount())) {
                return false;
            }
        }
        return true;
    }

    public static ItemRewardInfo from(int itemId, WzListProperty rewardList) throws ProviderError {
        final List<ItemRewardEntry> entries = new ArrayList<>();
        for (var entry : rewardList.getItems().entrySet()) {
            if (!(entry.getValue() instanceof WzListProperty rewardProp)) {
                throw new ProviderError("Failed to resolve reward prop");
            }
            entries.add(new ItemRewardEntry(
                    WzProvider.getInteger(rewardProp.get("item")),
                    WzProvider.getInteger(rewardProp.get("count")),
                    WzProvider.getInteger(rewardProp.get("prob")),
                    WzProvider.getInteger(rewardProp.get("period"), 0),
                    WzProvider.getString(rewardProp.get("Effect"), null)
            ));
        }
        return new ItemRewardInfo(itemId, entries);
    }
}

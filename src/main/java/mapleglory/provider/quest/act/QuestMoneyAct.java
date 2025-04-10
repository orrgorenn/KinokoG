package mapleglory.provider.quest.act;

import mapleglory.packet.user.QuestPacket;
import mapleglory.packet.world.MessagePacket;
import mapleglory.packet.world.WvsContext;
import mapleglory.world.item.InventoryManager;
import mapleglory.world.user.User;
import mapleglory.world.user.stat.Stat;

public final class QuestMoneyAct implements QuestAct {
    private final int money;

    public QuestMoneyAct(int money) {
        this.money = money;
    }

    @Override
    public boolean canAct(User user, int rewardIndex) {
        final long newMoney = ((long) user.getInventoryManager().getMoney()) + money;
        if (newMoney > Integer.MAX_VALUE || newMoney < 0) {
            user.write(QuestPacket.failedMeso());
            return false;
        }
        return true;
    }

    @Override
    public boolean doAct(User user, int rewardIndex) {
        final InventoryManager im = user.getInventoryManager();
        if (!im.addMoney(money)) {
            return false;
        }
        user.write(WvsContext.statChanged(Stat.MONEY, im.getMoney(), false));
        user.write(MessagePacket.incMoney(money));
        return true;
    }
}

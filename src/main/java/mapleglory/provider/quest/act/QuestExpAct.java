package mapleglory.provider.quest.act;

import mapleglory.packet.world.MessagePacket;
import mapleglory.util.Util;
import mapleglory.world.user.User;

public final class QuestExpAct implements QuestAct {
    private final int exp;

    public QuestExpAct(int exp) {
        this.exp = exp;
    }

    @Override
    public boolean canAct(User user, int rewardIndex) {
        return true;
    }

    @Override
    public boolean doAct(User user, int rewardIndex) {
        user.addExp(exp * Util.getQuestRateByMap(user.getFieldId()));
        user.write(MessagePacket.incExp(exp, 0, true, true));
        return true;
    }
}

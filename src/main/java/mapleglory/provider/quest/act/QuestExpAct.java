package mapleglory.provider.quest.act;

import mapleglory.packet.world.MessagePacket;
import mapleglory.world.user.User;

import static mapleglory.world.GameConstants.QUEST_RATE;

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
        user.addExp(exp * QUEST_RATE);
        user.write(MessagePacket.incExp(exp, 0, true, true));
        return true;
    }
}

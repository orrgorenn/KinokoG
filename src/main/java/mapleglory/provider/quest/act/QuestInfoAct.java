package mapleglory.provider.quest.act;

import mapleglory.packet.world.MessagePacket;
import mapleglory.util.Locked;
import mapleglory.world.quest.QuestRecord;
import mapleglory.world.user.User;

public final class QuestInfoAct implements QuestAct {
    private final int questId;
    private final String info;

    public QuestInfoAct(int questId, String info) {
        this.questId = questId;
        this.info = info;
    }

    @Override
    public boolean canAct(Locked<User> locked, int rewardIndex) {
        return true;
    }

    @Override
    public boolean doAct(Locked<User> locked, int rewardIndex) {
        final User user = locked.get();
        final QuestRecord qr = user.getQuestManager().setQuestInfoEx(questId, info);
        user.write(MessagePacket.questRecord(qr));
        return true;
    }
}

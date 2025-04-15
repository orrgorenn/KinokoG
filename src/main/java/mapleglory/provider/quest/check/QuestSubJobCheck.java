package mapleglory.provider.quest.check;

import mapleglory.world.user.User;

public final class QuestSubJobCheck implements QuestCheck {
    private final int subJobFlags;

    public QuestSubJobCheck(int subJobFlags) {
        this.subJobFlags = subJobFlags;
    }

    @Override
    public boolean check(User user) {
        final int subJob = user.getCharacterStat().getSubJob();
        return ((1 << subJob) & subJobFlags) != 0;
    }
}

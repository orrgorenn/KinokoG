package mapleglory.provider.quest.check;

import mapleglory.world.user.User;

public final class QuestLevelCheck implements QuestCheck {
    private final int level;
    private final boolean isMinimum;

    public QuestLevelCheck(int level, boolean isMinimum) {
        this.level = level;
        this.isMinimum = isMinimum;
    }

    @Override
    public boolean check(User user) {
        if (isMinimum) {
            return user.getLevel() >= level;
        } else {
            return user.getLevel() <= level;
        }
    }
}

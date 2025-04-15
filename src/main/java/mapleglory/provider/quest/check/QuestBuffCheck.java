package mapleglory.provider.quest.check;

import mapleglory.world.user.User;
import mapleglory.world.user.stat.SecondaryStat;

public final class QuestBuffCheck implements QuestCheck {
    private final int buffItemId;
    private final boolean isExcept;

    public QuestBuffCheck(int buffItemId, boolean isExcept) {
        this.buffItemId = buffItemId;
        this.isExcept = isExcept;
    }

    @Override
    public boolean check(User user) {
        return isSet(user.getSecondaryStat(), -buffItemId) ^ isExcept;
    }

    private static boolean isSet(SecondaryStat secondaryStat, int rOption) {
        return secondaryStat.getTemporaryStats().values().stream()
                .anyMatch(option -> option.rOption == rOption);
    }
}

package mapleglory.provider.quest.check;

import mapleglory.world.user.User;
import mapleglory.world.user.stat.CharacterTemporaryStat;

public final class QuestMorphCheck implements QuestCheck {
    private final int morph;

    public QuestMorphCheck(int morph) {
        this.morph = morph;
    }

    @Override
    public boolean check(User user) {
        return user.getSecondaryStat().getOption(CharacterTemporaryStat.Morph).nOption == morph;
    }
}

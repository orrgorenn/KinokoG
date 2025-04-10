package mapleglory.provider.quest.act;

import mapleglory.world.user.User;

public interface QuestAct {
    boolean canAct(User user, int rewardIndex);

    boolean doAct(User user, int rewardIndex);
}

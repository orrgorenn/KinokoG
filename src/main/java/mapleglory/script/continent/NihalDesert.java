package mapleglory.script.continent;

import mapleglory.script.common.Script;
import mapleglory.script.common.ScriptHandler;
import mapleglory.script.common.ScriptManager;
import mapleglory.world.quest.QuestRecordType;

public final class NihalDesert extends ScriptHandler {
    // MAGATIA

    @Script("q6032e")
    public static void q6032e(ScriptManager sm) {
        sm.setQRValue(QuestRecordType.MorensNewDiscovery, "111");
        sm.forceCompleteQuest(6032);
    }

    @Script("q6030e")
    public static void q6030e(ScriptManager sm) {
        sm.forceCompleteQuest(6030);
    }

    @Script("q6031e")
    public static void q6031e(ScriptManager sm) {
        sm.forceCompleteQuest(6031);
    }

    @Script("q6033e")
    public static void q6033e(ScriptManager sm) {
        sm.sayNext("Hm, so you claim to have brought the " + blue(itemName(4260003)) + "? Ok, let's take a look into it.");
        if (!sm.hasQuestStarted(6033) || !sm.removeItem(4260003, 1)) {
            sm.sayOk("Hey, what's wrong? I did tell you to make a monster crystal to pass my test, didn't I? Buying one or crafting before the start of the test is NOT part of the deal. Go craft me an " + blue(itemName(4260003)) + ".");
            return;
        }

        sm.sayBoth("You indeed have crafted a fine piece of Monster Crystal, I see. You passed! Now, I shall teach you the next steps of the Maker skill. Keep the monster crystal with you as well, it's your work.");
        int skillId = (int) Math.floor(sm.getJob().getJobId() / 1000.0) * 10000000 + 1007;
        sm.addSkill(skillId, 2, 3);
        sm.getUser().addQuestExp(230000);
        sm.forceCompleteQuest(6033);
    }

    @Script("q6036e")
    public static void q6036e(ScriptManager sm) {
        sm.sayNext("Bothering me again? What's it?");
        if (!sm.removeItem(4031980, 1)) {
            sm.sayOk("... Please step aside, I can't finish this work if I'm being distracted at every moment.");
            return;
        }

        sm.sayBoth("You crafted a " + blue(itemName(4031980)) + "?! How comes, how did you do it?? ... Well, that can't be helped, I guess. The student surpassed the teacher! Youth sure do wonders to one's perception capabilities.\r\n\r\nYou are now ready to take the last step on mastering the Maker skill, contemplate it at it's finest form!");
        int skillId = (int) Math.floor(sm.getJob().getJobId() / 1000.0) * 10000000 + 1007;
        sm.addSkill(skillId, 3, 3);
        sm.getUser().addQuestExp(300000);
        sm.forceCompleteQuest(6036);
    }
}
package mapleglory.script.continent;

import mapleglory.script.common.Script;
import mapleglory.script.common.ScriptHandler;
import mapleglory.script.common.ScriptManager;

public class PhantomForest extends ScriptHandler {
    @Script("Fallen_Woods")
    public static void Fallen_Woods(ScriptManager sm) {
        sm.sayOk("I see you there.  What do you want?");
    }

    @Script("q8224s")
    public static void q8224s(ScriptManager sm) {
        // TODO: Make GMS
        sm.sayNext("Please find 25 Phantom Seeds.");
        sm.forceStartQuest(8224);
    }

    @Script("q8225s")
    public static void q8225s(ScriptManager sm) {
        // TODO: Make GMS
        sm.sayNext("...Wow.");
        sm.getUser().addQuestExp(35935);
        sm.forceCompleteQuest(8225);
    }

    @Script("q8226s")
    public static void q8226s(ScriptManager sm) {
        // TODO: Make GMS
        sm.sayNext("Please find 50 Elder Ashes");
        sm.forceStartQuest(8226);
    }
}

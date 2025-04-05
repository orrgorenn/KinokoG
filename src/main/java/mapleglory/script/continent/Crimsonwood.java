package mapleglory.script.continent;

import mapleglory.script.common.Script;
import mapleglory.script.common.ScriptHandler;
import mapleglory.script.common.ScriptManager;

public class Crimsonwood extends ScriptHandler {
    @Script("q8230s")
    public static void q8230s(ScriptManager sm) {
        // TODO: GMS
        sm.sayNext("Please find the Crimsonwood Keystone.");
        sm.forceStartQuest(8230);
        if (!sm.hasQuestCompleted(8223) && !sm.hasQuestStarted(8223)) {
            sm.forceStartQuest(8223);
        }
    }

    @Script("q8230e")
    public static void q8230e(ScriptManager sm) {
        // TODO: GMS
        if (!sm.hasItem(3992041, 1)) {
            sm.sayOk("Please find the Crimsonwood Keystone.");
            return;
        }

        sm.forceCompleteQuest(8230);
        sm.sayNext("Good job. Now we can proceed.");
    }

    @Script("q8227s")
    public static void q8227s(ScriptManager sm) {
        // TODO: GMS
        sm.sayNext("Please find someone to translate this.");
        if (!sm.addItem(4032032, 1)) {
            sm.sayOk("Please make room in your inventory.");
            return;
        }

        sm.forceStartQuest(8227);
    }

    @Script("q8228s")
    public static void q8228s(ScriptManager sm) {
        // TODO: GMS
        if (!sm.hasItem(4032032, 1)) {
            sm.sayOk("I think you forgot to bring something.");
            return;
        }
        sm.sayNext("Wow. Hold on while I translate this...");
        sm.removeItem(4032032);
        sm.forceStartQuest(8228);
    }

    @Script("q8228e")
    public static void q8228e(ScriptManager sm) {
        // TODO: GMS
        sm.sayNext("Here you are!");
        if (!sm.addItem(4032018, 1)) {
            sm.sayOk("Please make room in your inventory.");
            return;
        }

        sm.forceCompleteQuest(8228);
    }

    @Script("q8229s")
    public static void q8229s(ScriptManager sm) {
        // TODO: GMS
        sm.sayNext("Wow. Get this to Jack!");
        sm.forceStartQuest(8229);
    }

    @Script("q8229e")
    public static void q8229e(ScriptManager sm) {
        // TODO: GMS
        if (!sm.hasItem(4032018, 1)) {
            sm.sayOk("I think you forgot to bring something.");
            return;
        }
        sm.sayNext("Thanks!");
        sm.removeItem(4032018);
        sm.forceCompleteQuest(8229);
    }
}

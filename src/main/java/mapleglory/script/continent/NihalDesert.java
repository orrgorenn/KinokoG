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
}
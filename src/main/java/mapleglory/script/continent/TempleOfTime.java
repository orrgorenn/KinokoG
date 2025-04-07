package mapleglory.script.continent;

import mapleglory.script.common.Script;
import mapleglory.script.common.ScriptHandler;
import mapleglory.script.common.ScriptManager;
import mapleglory.world.quest.QuestRecordType;

public class TempleOfTime extends ScriptHandler {
    @Script("timeQuest")
    public static void timeQuest(ScriptManager sm) {
        int fieldId = sm.getFieldId();
        sm.playPortalSE();
        int mapTo = (fieldId - 270010000) / 100;
        if (mapTo < 5 && sm.hasQuestCompleted(3500 + mapTo)) {
            sm.warp(fieldId + 10, "out00");
        } else if (mapTo == 5 && sm.hasQuestCompleted(3502 + mapTo)) {
            sm.warp(270020000, "out00");
        } else if (mapTo > 100 && mapTo < 105 && sm.hasQuestCompleted(3407 + mapTo)) {
            sm.warp(fieldId + 10, "out00");
        } else if (mapTo == 105 && sm.hasQuestCompleted(3514)) {
            sm.warp(270030000, "out00");
        } else if (mapTo > 200 && mapTo < 205 && sm.hasQuestCompleted(3314 + mapTo)) {
            sm.warp(fieldId + 10, "out00");
        } else if (mapTo == 205 && sm.hasQuestCompleted(3519)) {
            sm.warp(270040000, "out00");
        } else if (mapTo == 300 && (sm.hasItem(4032002) || sm.hasQuestCompleted(3522))) {
            sm.warp(270040100, "out00");
        } else {
            if (mapTo > 200) {
                sm.message("As the time starts to flow oddly, you are transported back to a safe lane.");
                sm.warp(270030000, "in00");
            } else if (mapTo > 100) {
                sm.message("As the time starts to flow oddly, you are transported back to a safe lane.");
                sm.warp(270020000, "in00");
            } else {
                sm.message("As the time starts to flow oddly, you are transported back to a safe lane.");
                sm.warp(270010000, "in00");
            }
        }
    }

    @Script("q3523s")
    public static void q3523s(ScriptManager sm) { handleMemoryKeep(sm, 3523); }

    @Script("q3524s")
    public static void q3524s(ScriptManager sm) { handleMemoryKeep(sm, 3524); }

    @Script("q3525s")
    public static void q3525s(ScriptManager sm) { handleMemoryKeep(sm, 3525); }

    @Script("q3526s")
    public static void q3526s(ScriptManager sm) { handleMemoryKeep(sm, 3526); }

    @Script("q3527s")
    public static void q3527s(ScriptManager sm) { handleMemoryKeep(sm, 3527); }

    @Script("q3529s")
    public static void q3529s(ScriptManager sm) { handleMemoryKeep(sm, 3529); }

    @Script("q3539s")
    public static void q3539s(ScriptManager sm) { handleMemoryKeep(sm, 3539); }

    @Script("q3540s")
    public static void q3540s(ScriptManager sm) { handleMemoryKeep(sm, 3540); }

    @Script("q3541s")
    public static void q3541s(ScriptManager sm) { handleMemoryKeep(sm, 3541); }

    private static void handleMemoryKeep(ScriptManager sm, int questId) {
        sm.sayNext("Oh my gosh, you've grown so much since we first met! You've lost your memories? I'll take care of that.");
        sm.forceCompleteQuest(questId);
        sm.setQRValue(QuestRecordType.MemoryKeeper, "1");
    }
}

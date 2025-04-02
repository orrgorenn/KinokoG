package mapleglory.script.continent;

import mapleglory.script.common.Script;
import mapleglory.script.common.ScriptHandler;
import mapleglory.script.common.ScriptManager;

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
}

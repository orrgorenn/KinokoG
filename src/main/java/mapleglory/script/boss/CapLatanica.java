package mapleglory.script.boss;

import mapleglory.script.common.Script;
import mapleglory.script.common.ScriptHandler;
import mapleglory.script.common.ScriptManager;
import mapleglory.world.field.mob.MobAppearType;

public class CapLatanica extends ScriptHandler {
    @Script("captinsg00")
    public static void captinsg00(ScriptManager sm) {
        // Singapore : Ghost Ship 7 (541010060)
        //   in00 (-97, 332)
        sm.playPortalSE();
        sm.warp(541010100, "sp");
    }

    @Script("sgboss0")
    public static void sgboss0(ScriptManager sm) {
        // sgboss0 (5411000)
        //   Singapore : The Engine Room (541010100)
        sm.broadcastSoundEffect("Bgm09/TimeAttack");
        sm.spawnMob(9420513, MobAppearType.NORMAL, -148, 225, false, true);
        sm.broadcastMessage("As you wish. Here comes Capt. Latanica!", true);
    }

    @Script("captinsg01")
    public static void captinsg01(ScriptManager sm) {
        // Bob : Ghost Ship Keeper (9270033)
        //   Singapore : The Engine Room (541010100)
        if(sm.askYesNo("I can help you escape his wrath... do you want to leave?")) {
            sm.setReactorState(5411000, 0);
            sm.warp(541010110);
        }
    }
}

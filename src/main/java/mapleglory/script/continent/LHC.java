package mapleglory.script.continent;

import mapleglory.script.common.Script;
import mapleglory.script.common.ScriptHandler;
import mapleglory.script.common.ScriptManager;

public class LHC extends ScriptHandler {
    @Script("lionCastle_enter")
    public static void lionCastle_enter(ScriptManager sm) {
        sm.playPortalSE();
        sm.warp(211060010, "west00");
    }

    @Script("gotoNext1")
    public static void gotoNext1(ScriptManager sm) {
        sm.playPortalSE();
        sm.warp(211060300, "west00");
    }

    @Script("gotoNext2_1")
    public static void gotoNext2_1(ScriptManager sm) {
        sm.playPortalSE();
        sm.warp(211060500, "west00");
    }

    @Script("gotoNext2_2")
    public static void gotoNext2_2(ScriptManager sm) {
        sm.playPortalSE();
        sm.warp(211060410, "in00");
    }

    @Script("2ndTowerTop")
    public static void secondTowerTop(ScriptManager sm) {
        sm.playPortalSE();
        sm.warp(211060401);
    }

    @Script("3rdTowerTop")
    public static void thirdTowerTop(ScriptManager sm) {
        sm.playPortalSE();
        sm.warp(211060601);
    }

    @Script("vanleonItem0")
    public static void vanleonItem0(ScriptManager sm) {
        sm.message("Not implemented yet.");
    }
}

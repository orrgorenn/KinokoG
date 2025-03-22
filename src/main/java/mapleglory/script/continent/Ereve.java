package mapleglory.script.continent;

import mapleglory.script.common.Script;
import mapleglory.script.common.ScriptHandler;
import mapleglory.script.common.ScriptManager;

public class Ereve extends ScriptHandler {
    @Script("cygnus_lv120")
    public static void cygnus_lv120(ScriptManager sm) {
        // Kidan : Knight Trainer (1102003)
        //   Empress' Road : Knights Chamber (130000100)
        //   Empress's Road : Knights Chamber (130000101)
        sm.sayOk("Welcome to the Hall of Knights.");
    }
}

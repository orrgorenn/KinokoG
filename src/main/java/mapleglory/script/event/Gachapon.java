package mapleglory.script.event;

import mapleglory.handler.stage.GachaponHandler;
import mapleglory.script.common.Script;
import mapleglory.script.common.ScriptHandler;
import mapleglory.script.common.ScriptManager;
import mapleglory.util.Tuple;
import mapleglory.world.quest.QuestRecordType;


public class Gachapon extends ScriptHandler {
    final static int DEF_RETURN_MAP = 100000000;
    final static int GACHAPON_TICKET = 5220000;
    @Script("GachaponEvent")
    public static void gachaponEvent(ScriptManager sm) {
        // Gaga (9000021)
        //   Henesys : Henesys (100000000)
        //   Ellinia : Ellinia (101000000)
        //   Perion : Perion (102000000)
        //   Kerning City : Kerning City (103000000)
        //   Lith Harbor : Lith Harbor (104000000)
        //   Sleepywood : Sleepywood (105000000)
        //   Dungeon : Sleepywood (105040300)
        //   Empress' Road : Crossroads of Ereve (130000200)
        //   Orbis : Orbis (200000000)
        //   El Nath : El Nath (211000000)
        //   Ludibrium : Ludibrium (220000000)
        //   Omega Sector : Omega Sector (221000000)
        //   Korean Folk Town : Korean Folk Town (222000000)
        //   Aquarium : The Center Hall (230000001)
        //   Leafre : Leafre (240000000)
        //   Mu Lung : Mu Lung (250000000)
        //   Herb Town : Herb Town (251000000)
        //   The Burning Road : Ariant (260000000)
        //   Sunset Road : Magatia (261000000)
        //   Black Wing Territory : Edelstein (310000000)
        //   Malaysia : Trend Zone Metropolis (550000000)
        //   Malaysia : Kampung Village (551000000)
        //   Fairytale Land : Fairytale Land Entrance   (910030000)
        //   Hidden Street : Cassandra's Shore (970020000)
        //   헌팅 애드벌룬 : 대기실 (970050000)
        //   헌팅 애드벌룬 : 대기실 (970050001)
        //   헌팅 애드벌룬 : 대기실 (970050002)
        //   헌팅 애드벌룬 : 대기실 (970050003)
        //   헌팅 애드벌룬 : 대기실 (970050004)
        //   헌팅 애드벌룬 : 대기실 (970050005)
        //   헌팅 애드벌룬 : 대기실 (970050006)
        //   헌팅 애드벌룬 : 대기실 (970050007)
        //   헌팅 애드벌룬 : 대기실 (970050008)
        //   헌팅 애드벌룬 : 대기실 (970050009)
        final int returnMap = !sm.getQRValue(QuestRecordType.GachaponEvent).isEmpty() ? Integer.parseInt(sm.getQRValue(QuestRecordType.GachaponEvent)) : DEF_RETURN_MAP;

        if (sm.getFieldId() == 910030000) {
            if (sm.askYesNo("Are you sure you want to leave? You won't be able to return once you exit the map.")) {
                sm.warp(returnMap);
            }
        } else {
            sm.sayOk("Gachapon's are now live for testing!\r\nBut only if you can find a ticket...");
        }
    }

    @Script("gachapon10")
    public static void gachapon10(ScriptManager sm) {
        // Gachapon (9100109)
        //   New Leaf City : NLC Town Center (600000000)
        handleGachapon(sm, "New Leaf City", "new_leaf_city");
    }

    @Script("gachapon18")
    public static void gachapon18(ScriptManager sm) {
        // Gachapon (9100117)
        //   Nautilus : Mid Floor - Hallway (120000200)
        handleGachapon(sm, "Nautilus", "nautilus");
    }

    @Script("gachapon1")
    public static void gachapon1(ScriptManager sm) {
        // Gachapon (9100100)
        //   Henesys : Henesys Market (100000100)
        handleGachapon(sm, "Henesys Market", "henesys");
    }

    @Script("gachapon2")
    public static void gachapon2(ScriptManager sm) {
        // Gachapon (9100101)
        //   Ellinia : Ellinia (101000000)
        handleGachapon(sm, "Ellinia", "ellinia");
    }

    @Script("gachapon3")
    public static void gachapon3(ScriptManager sm) {
        // Gachapon (9100102)
        //   Perion : Perion (102000000)
        handleGachapon(sm, "Perion", "perion");
    }

    @Script("gachapon4")
    public static void gachapon4(ScriptManager sm) {
        // Gachapon (9100103)
        //   Kerning City : Kerning City (103000000)
        handleGachapon(sm, "Kerning City", "kerning_city");
    }

    @Script("gachapon5")
    public static void gachapon5(ScriptManager sm) {
        // Gachapon (9100104)
        //   Dungeon : Sleepywood (105040300)
        handleGachapon(sm, "Sleepywood Dungeon", "sleepywood");
    }

    @Script("gachapon6")
    public static void gachapon6(ScriptManager sm) {
        // Gachapon (9100105)
        //   Zipangu : Mushroom Shrine (800000000)
        handleGachapon(sm, "Mushroom Shrine", "mushroom_shrine");
    }

    @Script("gachapon7")
    // Zipangu: Spa (M)
        // Gachapon (9100106)
        //   Zipangu : Spa (M) (809000101)
    public static void gachapon7(ScriptManager sm) {
        handleGachapon(sm, "Zipangu Spa (M)", "zipangu_spa_m");
    }

    @Script("gachapon8")
    // Zipangu: Spa (F)
        // Gachapon (9100107)
        //   Zipangu : Spa (F) (809000201)
    public static void gachapon8(ScriptManager sm) {
        handleGachapon(sm, "Zipangu Spa (F)", "zipangu_spa_f");
    }

    public static void handleGachapon(ScriptManager sm, String location, String gachaName) {
        if (!sm.hasItem(GACHAPON_TICKET, 1)) {
            sm.sayOk("It doesn't seem like you have a Gachapon ticket. Please purchase one and try again.");
            return;
        }

        if (
                sm.getUser().getInventoryManager().getEquipInventory().getRemaining() < 1
                        || sm.getUser().getInventoryManager().getConsumeInventory().getRemaining() < 1
                        || sm.getUser().getInventoryManager().getEtcInventory().getRemaining() < 1
                        || sm.getUser().getInventoryManager().getInstallInventory().getRemaining() < 1
        ) {
            sm.sayOk("Please make room in your EQP, USE, ETC, and SET-UP inventories.");
            return;
        }

        Tuple<Integer, Integer> item = GachaponHandler.rollGachapon(gachaName);
        sm.addItem(item.getLeft(), item.getRight());
        sm.removeItem(GACHAPON_TICKET, 1);
        sm.sayNext("You have obtained #b#t" + item.getLeft() + "##k from " + location + ".\r\nThank you for using our Gachapon services. Please come again!");
    }
}

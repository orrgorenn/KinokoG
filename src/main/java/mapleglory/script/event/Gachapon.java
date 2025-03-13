package mapleglory.script.event;

import mapleglory.handler.stage.GachaponHandler;
import mapleglory.provider.ItemProvider;
import mapleglory.provider.item.ItemInfo;
import mapleglory.provider.reward.Reward;
import mapleglory.script.common.Script;
import mapleglory.script.common.ScriptHandler;
import mapleglory.script.common.ScriptManager;
import mapleglory.server.ServerConfig;
import mapleglory.util.Tuple;
import mapleglory.util.Util;
import mapleglory.world.field.drop.Drop;
import mapleglory.world.field.drop.DropOwnType;
import mapleglory.world.item.Item;
import mapleglory.world.item.ItemVariationOption;
import mapleglory.world.quest.QuestRecordType;

import java.util.Optional;


public class Gachapon extends ScriptHandler {
    final static int DEF_RETURN_MAP = 100000000;
    final static int GACHAPON_TICKET = 5220000;
    @Script("GachaponEvent")
    public static void gachaponEvent(ScriptManager sm) {
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
        // New Leaf City
        handleGachapon(sm, "New Leaf City", "new_leaf_city");
    }

    @Script("gachapon18")
    public static void gachapon18(ScriptManager sm) {
        // Nautilus
        handleGachapon(sm, "Nautilus", "nautilus");
    }

    @Script("gachapon1")
    public static void gachapon1(ScriptManager sm) {
        // Henesys Market
        handleGachapon(sm, "Henesys Market", "henesys");
    }

    @Script("gachapon2")
    public static void gachapon2(ScriptManager sm) {
        // Ellinia
        handleGachapon(sm, "Ellinia", "ellinia");
    }

    @Script("gachapon3")
    public static void gachapon3(ScriptManager sm) {
        // Perion
        handleGachapon(sm, "Perion", "perion");
    }

    @Script("gachapon4")
    public static void gachapon4(ScriptManager sm) {
        // Kerning City
        handleGachapon(sm, "Kerning City", "kerning_city");
    }

    @Script("gachapon5")
    public static void gachapon5(ScriptManager sm) {
        // Dungeon : Sleepywood
        handleGachapon(sm, "Sleepywood Dungeon", "sleepywood");
    }

    @Script("gachapon6")
    public static void gachapon6(ScriptManager sm) {
        // Mushroom Shrine
        handleGachapon(sm, "Mushroom Shrine", "mushroom_shrine");
    }

    @Script("gachapon7")
    // Zipangu: Spa (M)
    public static void gachapon7(ScriptManager sm) {
        handleGachapon(sm, "Zipangu Spa (M)", "zipangu_spa_m");
    }

    @Script("gachapon8")
    // Zipangu: Spa (F)
    public static void gachapon8(ScriptManager sm) {
        handleGachapon(sm, "Zipangu Spa (F)", "zipangu_spa_f");
    }

    private static void handleGachapon(ScriptManager sm, String location, String gachaName) {
        if(sm.askYesNo("You have a #b#t" + GACHAPON_TICKET + "##k. Would you like to use it?")) {
            if (!sm.hasItem(GACHAPON_TICKET, 1)) {
                sm.sayOk("It doesn't seem like you have a Gachapon ticket. Please purchase one and try again.");
                return;
            }
            Tuple<Integer, Integer> item = GachaponHandler.rollGachapon(gachaName);
            if (!sm.canAddItem(item.getLeft(), item.getRight())) {
                sm.sayOk("Please make room in your inventory.");
                return;
            }
            sm.removeItem(GACHAPON_TICKET, 1);
            sm.addItem(item.getLeft(), item.getRight());
            sm.sayNext("You have obtained #b#t" + item.getLeft() + "##k from " + location + ".\r\nThank you for using our Gachapon services. Please come again!");
        }
    }
}

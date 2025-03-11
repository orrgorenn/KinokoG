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

    @Script("gachapon6")
    public static void gachapon6(ScriptManager sm) {
        if(ServerConfig.GACHAPON_ENABLED) {
            sm.sayNext("#eLimited Time Sell!#k\r\n#v2049100# #t2049100#\r\n#v2049300# #t2049300#\r\n#v2070018# #t2070018#\r\n#v2070007# #t2070007#");
            if (!sm.hasItem(5220000, 1)) {
                sm.sayOk("It doesn't seem like you have a Gachapon ticket. Please purchase one and try again.");
                return;
            }
            Tuple<Integer, Integer> item = GachaponHandler.rollGachapon("mushroom_shrine");
            if (!sm.canAddItem(item.getLeft(), item.getRight())) {
                sm.sayOk("Please make room in your inventory.");
                return;
            }
            sm.removeItem(5220000, 1);
            sm.addItem(item.getLeft(), item.getRight());
            sm.sayNext("You have obtained #b#t" + item.getLeft() + "##k from Mushroom Shrine Gachapon.\r\nThank you for using our Gachapon services. Please come again!");
        } else {
            sm.sayOk("Gachapon is in progress.");
        }
    }
}

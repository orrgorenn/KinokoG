package mapleglory.script.continent;

import mapleglory.script.common.Script;
import mapleglory.script.common.ScriptHandler;
import mapleglory.script.common.ScriptManager;

public class NewLeafCity extends ScriptHandler {
    @Script("q8231s")
    public static void q8231s(ScriptManager sm) {
        // Fool's Gold (8231 - start)
        sm.sayNext("With all the strange occurrences in Masteria and New Leaf City, Lita Lawless must be busy! I'll bet that she's willing to accept my help...maybe I can earn some mesos in the process! A quick jaunt to the Kerning City subway and I'll be on my way to New Leaf City!");
        sm.sayBoth("I spoke with Lita, and it looks like there a trickster roaming about the Phantom Forest. It caused her quite a bit of trouble-surprising for a warrior of her caliber. I've agreed to help rid the Forest of these nefarious creatures, and as proof, I have to bring her 30 of the strange silver clovers-she called them Lucky Charms. I'll be on guard-that forest is haunted...or so I've heard...");
        if (sm.askYesNo("So you'll help me?")) {
            sm.forceStartQuest(8231);
        }
    }

    @Script("q8233s")
    public static void q8233s(ScriptManager sm) {
        // Rags to Riches (8233 - start)
        sm.sayNext("With all the strange occurrences in Masteria and New Leaf City, Lita Lawless must be busy! I'll bet that she's willing to accept my help...maybe I can earn some mesos in the process! A quick jaunt to the Kerning City subway and I'll be on my way to New Leaf City!");
        sm.sayBoth("I spoke with Lita, and it seems that there are strange, powerful spirits drifting about the Phantom Forest. These strange spirits seemingly have no desire, save for tormenting others. I've agreed to eliminate 30 of them, and bring their soiled rags to Lita as proof of valor. I'd better keep sharp-that forest has driven quite a few travelers mad with its confusion...");
        if (sm.askYesNo("So you'll help me?")) {
            sm.forceStartQuest(8233);
        }
    }

    @Script("q8235s")
    public static void q8235s(ScriptManager sm) {
        // One Step A-Head (8235 - start)
        sm.sayNext("With all the strange occurrences in Masteria and New Leaf City, Lita Lawless must be busy! I'll bet that she's willing to accept my help...maybe I can earn some mesos in the process! A quick jaunt to the Kerning City subway and I'll be on my way to New Leaf City!");
        sm.sayBoth("I spoke with Lita, and she told me the tragic origin of the Headless Horseman. He was a former warrior of Crimsonwood Keep that was experimented on by the mysterious Alchemist, the same one who corrupted the Krakians. He now roams the Phantom Forest, and I must bring his Jack O'Lantern head to Lita as proof of my triumph!");
        if (sm.askYesNo("So you'll help me?")) {
            sm.forceStartQuest(8235);
        }
    }

    @Script("q8237s")
    public static void q8237(ScriptManager sm) {
        // Catch a Bigfoot by the Toe (8237 - start)
        sm.sayNext("With all the strange occurrences in Masteria and New Leaf City, Lita Lawless must be busy! I'll bet that she's willing to accept my help...maybe I can earn some mesos in the process! A quick jaunt to the Kerning City subway and I'll be on my way to New Leaf City!");
        sm.sayBoth("I spoke with Lita, and it seems that there are strange, powerful spirits drifting about the Phantom Forest. These strange spirits seemingly have no desire, save for tormenting others. I've agreed to eliminate 30 of them, and bring their soiled rags to Lita as proof of valor. I'd better keep sharp-that forest has driven quite a few travelers mad with its confusion...");
        if (sm.askYesNo("So you'll help me?")) {
            sm.forceCompleteQuest(4918);
            sm.forceCompleteQuest(4911);
            sm.forceStartQuest(8237);
        }
    }

    @Script("q8238s")
    public static void q8238s(ScriptManager sm) {
        // Catch a Bigfoot by the Toe (8238 - start)
        // TODO: implement logic to control times
        if (sm.askYesNo("Hey, traveler! I need your help. A new threat has appeared to the citizens of the New Leaf City. I'm currently recruiting anyone, and this time's target #ris the Bigfoot#k. Are you in?")) {
            sm.sayOk("Very well. Get me #r1 #t4032013##k, asap. The NLC is counting on you.");
            sm.forceStartQuest(8238);
        } else {
            sm.sayOk("Okay, then. See you around.");
        }
    }

    @Script("q8221s")
    public static void q8221s(ScriptManager sm) {
        // TODO: GMS like interaction
        sm.sayNext("Please find 10 Gold Ore, 4 Typhon Feather, 1 Power Crystal Ore.");
        sm.forceStartQuest(8221);
    }

    @Script("q8219s")
    public static void q8219s(ScriptManager sm) {
        // TODO: GMS like interaction
        sm.sayNext("Please find my brother.");
        sm.forceStartQuest(8219);
    }

    @Script("q8219e")
    public static void q8219e(ScriptManager sm) {
        // TODO: GMS like interaction
        sm.sayNext("...");
        if (!sm.addItem(3992040, 1)) {
            sm.sayOk("Please make room in your SET-UP inventory.");
            return;
        }
        sm.getUser().addQuestExp(175000);
        sm.forceCompleteQuest(8219);
    }
}

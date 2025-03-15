package mapleglory.script.party;

import mapleglory.script.common.Script;
import mapleglory.script.common.ScriptHandler;
import mapleglory.script.common.ScriptManager;
import mapleglory.world.quest.QuestRecordType;

import java.util.List;
import java.util.Map;

public class MuLungDojo extends ScriptHandler {
    final static int DOJO_ENTRANCE_MAP = 925020001;
    final static int DOJO_EXIT_MAP = 925020002;
    final static int DOJO_WIN_MAP = 925020003;
    final static int DOJO_TRAINING_MAP = 925020010;
    final static int DOJO_FIRST_STAGE_MAP_NORMAL = 925020100;
    final static int DOJO_POINTS_QID = 8000;
    final static int WEATHER_EFFECT_ID = 5120024;
    final static int DOJO_EMBLEM_ID = 4001620;
    final static int DOJO_MIN_LEVEL = 25;
    final static int DOJO_MAX_POINTS_PER_DAY = 1500;

    @Script("dojang_enter")
    public static void dojang_enter(ScriptManager sm) {
        if (sm.getLevel() < DOJO_MIN_LEVEL) {
            sm.sayOk("Hey! Are you mocking my master? Who do you think you are to challenge him? This is a joke! You should at least be level #b25#k.");
            return;
        }

        if (sm.getFieldId() == DOJO_ENTRANCE_MAP) {
            final int answer = sm.askMenu("My master is the strongest person in Mu Lung, and you want to challenge him? Fine, but you'll regret it later.", Map.of(
                    0, "#bI want to challenge him alone.",
                    1, "I want to challenge him with a party.\r\n\r\n",
                    2, "I want to receive a belt.",
                    3, "I want to reset my training points.",
                    4, "I want to receive a medal.",
                    5, "What is a Mu Lung Dojo#k"
            ));

            switch (answer) {
                case 0 -> {
                    if (!sm.getQRValue(QuestRecordType.MuLungDojoTutorial).equals("1")) {
                        if (sm.askYesNo("Hey there! You! This is your first time, huh? Well, my master doesn't just meet with anyone. He's a busy man. And judging by your looks, I don't think he'd bother. Ha! But, today's your lucky day... I tell you what, if you can defeat me, I'll allow you to see my Master. So what do you say?")) {
                            System.out.println("test");
                        } else {
                            sm.sayOk("Haha! Who are you trying to impress with a heart like that? Go back home where you belong!");
                        }
                    }
                }
                case 5 -> {
                    sm.sayOk("Our master is the strongest person in Mu Lung. The place he built is called the Mu Lung Dojo, a building that is #r38 stories#k tall! You can train yourself as you go up each level. Of course, it'll be hard for someone at your level to reach the top.");
                }
            }
        } else {
            if (sm.askYesNo("What, you're giving up? You just need to get to the next level! Do you really want to quit and leave?")) {

            }
        }
    }

//    @Script("dojang_Eff")
//    public static void dojang_Eff(ScriptManager sm) {
//        final int stage = Math.floor(sm.getFieldId() / 100) % 100;
//    }

    @Script("dojang_Msg")
    public static void dojang_Msg(ScriptManager sm) {
        List<String> messages = List.of("Your courage for challenging the Mu Lung Dojo is commendable!", "If you want to taste the bitterness of defeat, come on in!", "I will make you thoroughly regret challenging the Mu Lung Dojo! Hurry up!");
        if (sm.getFieldId() == 925020000) {
            sm.getField().blowWeather(5120024, messages.get((int) (Math.random() * messages.size())), 20);
            sm.getUser().resetDojoEnergy();
        } else {
            sm.getField().blowWeather(5120024, "Ha! Let's see what you got! I won't let you leave unless you defeat me first!", 20);
        }
    }
}

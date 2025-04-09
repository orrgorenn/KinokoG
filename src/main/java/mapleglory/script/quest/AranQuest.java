package mapleglory.script.quest;

import mapleglory.packet.user.UserLocal;
import mapleglory.provider.reward.Reward;
import mapleglory.script.common.Script;
import mapleglory.script.common.ScriptHandler;
import mapleglory.script.common.ScriptManager;
import mapleglory.script.common.ScriptMessageParam;
import mapleglory.world.job.Job;
import mapleglory.world.quest.QuestRecordType;

import java.util.List;

public final class AranQuest extends ScriptHandler {
    @Script("rien")
    public static void rien(ScriptManager sm) {
        // Snow Island : Rien (140000000)
        if (sm.hasQuestCompleted(21101) && !sm.hasQRValue(QuestRecordType.AranGuideEffect, "guide=1")) {
            sm.addQRValue(QuestRecordType.AranGuideEffect, "guide=1");
            sm.write(UserLocal.openSkillGuide());
        }
    }

    @Script("enterGym")
    public static void enterGym(ScriptManager sm) {
        // Snow Island : Dangerous Forest (140010100)
        //   in00 (-1999, 86)
        if (sm.hasQuestStarted(21701)) {
            sm.playPortalSE();
            sm.warp(914010000, "out00");
        } else if (sm.hasQuestStarted(21702)) {
            sm.playPortalSE();
            sm.warp(914010100, "out00");
        } else if (sm.hasQuestStarted(21703)) {
            sm.playPortalSE();
            sm.warp(914010200, "out00");
        } else {
            sm.message("You will be allowed to enter the Penguin Training Ground only if you are receiving a lesson from Puo.");
        }
    }

    @Script("enterPort")
    public static void enterPort(ScriptManager sm) {
        // Snow Island : Snow-covered Field 3 (140020200)
        //   east00 (4769, 84)
        sm.playPortalSE();
        sm.warp(140020300, "west00");
    }

    @Script("enterInfo")
    public static void enterInfo(ScriptManager sm) {
        // Lith Harbor : Lith Harbor (104000000)
        //   in03 (405, 406)
        sm.playPortalSE();
        sm.warp(104000004, "out00");
    }

    @Script("q21100s")
    public static void q21100s(ScriptManager sm) {
        // The Five Heroes (21100 - start)
        sm.sayNext("There isn't much record left of the heroes that fought against the Black Mage. Even in the Book of Prophecy, the only information available is that there were five of them. There is nothing about who they were or what they looked like. Is there anything you remember? Anything at all?", ScriptMessageParam.FLIP_SPEAKER);
        sm.sayBoth("I don't remember a thing...", ScriptMessageParam.PLAYER_AS_SPEAKER);
        sm.sayBoth("As I expected. Of course, the curse of the Black Mage was strong enough to wipe out all of your memory. But even if that's the case, there has got to be a point where the past will uncover, especially now that we are certain you are one of the heroes. I know you've lost your armor and weapon during the battle but... Oh, yes, yes. I almost forgot! Your #bweapon#k!", ScriptMessageParam.FLIP_SPEAKER);
        sm.sayBoth("My weapon?", ScriptMessageParam.PLAYER_AS_SPEAKER);
        sm.sayBoth("I found an incredible weapon while digging through blocks of ice a while back. I figured the weapon belonged to a hero, so I brought it to town and placed it somewhere in the center of the town. Haven't you seen it? #bThe #p1201001##k... \r\r#i4032372#\r\rIt looks like this...", ScriptMessageParam.FLIP_SPEAKER);
        sm.sayBoth("Come to think of it, I did see a #p1201001# in town.", ScriptMessageParam.PLAYER_AS_SPEAKER);
        if (!sm.askAccept("Yes, that's it. According to what's been recorded, the weapon of a hero will recognize its rightful owner, and if you're the hero that used the #p1201001#, the #p1201001# will react when you grab the #p1201001#. Please go find the #b#p1201001# and click on it.#k")) {
            sm.sayNext("What's stopping you? I promise, I won't be disappointed even if the #p1201001# shows no reaction to you. Please, rush over there and grab the #p1201001#. Just #bclick#k on it.", ScriptMessageParam.FLIP_SPEAKER);
            return;
        }
        sm.forceCompleteQuest(21100);
        sm.sayOk("If the #p1201001# reacts to you, then we'll know that you're #bAran#k, the hero that wielded a #p1201001#.", ScriptMessageParam.FLIP_SPEAKER);
        sm.reservedEffect("Effect/Direction1.img/aranTutorial/ClickPoleArm");
    }

    @Script("q21101s")
    public static void q21101s(ScriptManager sm) {
        // The Polearm-Wielding Hero (21101 - start)
        if (!sm.askYesNo("#b(Are you certain that you were the hero that wielded the #p1201001#? Yes, you're sure. You better grab the #p1201001# really tightly. Surely it will react to you.)#k")) {
            sm.sayNext("#b(You need to think about this for a second...)#k");
            return;
        }
        if (!sm.addItem(1142129, 1)) {
            sm.sayNext("Please check if your inventory is full or not.");
            return;
        }
        sm.setJob(Job.ARAN_1);
        sm.forceCompleteQuest(21101);
        sm.sayNext("#b(You might be starting to remember something...)#k", ScriptMessageParam.NOT_CANCELLABLE, ScriptMessageParam.PLAYER_AS_SPEAKER);
        sm.setDirectionMode(true, 0);
        sm.warp(914090100);
    }

    @Script("q21700s")
    public static void q21700s(ScriptManager sm) {
        // New Beginnings (21700 - start)
        sm.sayNext("It seems like you've started to remember things. Your Polearm must have recognized you. This means you are surely #bAran, the wielder of Polearms#k. Is there anything else you remember? Skills you used with the Polearm perhaps? Anything?", ScriptMessageParam.FLIP_SPEAKER);
        sm.sayBoth("#b(You tell her that you remember a few skills.)#k", ScriptMessageParam.PLAYER_AS_SPEAKER);
        sm.sayBoth("That's not a lot, but it's progress. Our focus, then, should be to get you back to the state before you were frozen. You may have lost your memory, but I'm sure it won't take long for you to recover the abilities that your body remembers.", ScriptMessageParam.FLIP_SPEAKER);
        sm.sayBoth("How do I recover my abilities?", ScriptMessageParam.PLAYER_AS_SPEAKER);
        if (!sm.askAccept("There is only one way to do that. Train! Train! Train! Train! If you continue to train, your body will instinctively remember its abilities. To help you through the process, I'll introduce you to an instructor.")) {
            sm.sayNext("No? Are you saying you can train on your own? I'm just letting you know that you'll get better results if you train with an instructor. You can't live in this world alone. You must learn to get along with other people.");
            return;
        }
        if (!sm.addItem(1442000, 1)) {
            sm.sayNext("Please check if your inventory is full or not.");
            return;
        }
        sm.forceStartQuest(21700);
        sm.sayNext("I gave you a #bPolearm#k because I figured it would be best for you to use a weapon you're familiar with. It will be useful in your training.");
        sm.sayPrev("You'll find a Training Center if you exit to the #bleft#k. There, you'll meet #b#p1202006##k. I'm a bit worried because I think he may be struggling with bouts of Alzheimer's, but he spent a long time researching skills to help you. I'm sure you'll learn a thing or two from him.");
    }

    @Script("q21703s")
    public static void q21703s(ScriptManager sm) {
        // Train or Die! 3 (21703 - start)
        sm.sayNext("Your abilities are really beginning to take shape. I am surprised that an old man like me was able to help you. I'm tearing up just thinking about how happy it makes me to have been of assistance to you. *Sniff sniff*");
        sm.sayBoth("#b(You didn't even train that long with him... Why is he crying?)#k", ScriptMessageParam.PLAYER_AS_SPEAKER);
        sm.sayBoth("Alright, here's the third and the final stage of training. Your last opponent is... #r#o9300343#s#k! Do you know anything about #o1210100#s?");
        sm.sayBoth("Well, a little bit...", ScriptMessageParam.PLAYER_AS_SPEAKER);
        sm.sayBoth("They are natural warriors! They're born with a voracious appetite for food. They devour any food that's visible the moment they sweep by. Terrifying, isn't it?");
        sm.sayBoth("#b(Is that really true?)#k", ScriptMessageParam.PLAYER_AS_SPEAKER);
        if (!sm.askAccept("Okay, now... #bEnter the Training Center again#k, defeat #r30#k #o9300343#s, and show me what you're made of! You'll have to exert all your energy to defeat them! Go, go, go! Rise above me!")) {
            sm.sayNext("I know it takes an incredible amount of strength and will to outdo your instructor, but you weren't meant to let yourself wither away. You must move on to bigger and better things! You must do everything you can to embrace your heroic nature!");
            return;
        }
        sm.forceStartQuest(21703);
        sm.sayOk("Now go and take on those monstrous #o9300343#s!");
    }

    @Script("q21703e")
    public static void q21703e(ScriptManager sm) {
        // Train or Die! 3 (21703 - end)
        sm.sayNext("Ah, you've come back after defeating all 30 #o9300343#s. I knew you had it in you... Even though you have no memories and few abilities, I could see that you were different! How? Because you're carrying around a Polearm, obviously!");
        sm.sayBoth("#b(Is he pulling your leg?)#k", ScriptMessageParam.PLAYER_AS_SPEAKER);
        if (!sm.askYesNo("I have nothing more to teach you, as you've surpassed my level of skill. Go now! Don't look back! This old man is happy to have served as your instructor.")) {
            sm.sayNext("Are you reluctant to leave your instructor? *Sniff sniff* I'm so moved, but you can't stop here. You are destined for bigger and better things!");
            return;
        }
        sm.addSkill(21000000, 0, 10); // Combo Ability
        sm.addExp(2000);
        sm.forceCompleteQuest(21703);
        sm.sayNext("(You remembered the #bCombo Ability#k skill! You were skeptical of the training at first, since the old man suffers from Alzheimer's and all, but boy, was it effective!)", ScriptMessageParam.PLAYER_AS_SPEAKER);
        sm.sayPrev("Now report back to #p1201000#. I know she'll be ecstatic when she sees the progress you've made!");
    }

    @Script("q21704s")
    public static void q21704s(ScriptManager sm) {
        // Baby Steps (21704 - start)
        sm.sayNext("How did the training go? The Penguin Teacher #p1202006# likes to exaggerate and it worried me knowing that he has bouts of Alzheimer's, but I'm sure he helped you. He's been studying the skills of heroes for a very long time.");
        sm.sayBoth("#b(You tell her that you were able to remember the Combo Ability skill.)#k", ScriptMessageParam.PLAYER_AS_SPEAKER);
        if (!sm.askAccept("That's great! Honestly, though, I think it has less to do with the method of #p1202006#'s training and more to do with your body remembering its old abilities. #bI'm sure your body will remember more skills as you continue to train#k!  \r\n\r\n#fUI/UIWindow2.img/QuestIcon/8/0# 500 exp")) {
            return;
        }
        sm.addExp(500);
        sm.forceCompleteQuest(21704);
    }

    @Script("q21200s")
    public static void q21200s(ScriptManager sm) {
        // In Search of Its Rightful Owner (21200 - start)
        sm.setSpeakerId(1201000);
        if (sm.askAccept("How is your training going? Wow, you've reached such a high level! That's amazing. I knew you would do just fine on Victorial Island... Oh, look at me. I'm wasting your time. I know you're busy, but you'll have to return to the island for a bit.")) {
            sm.forceStartQuest(21200);
            sm.sayNext("Your " + blue(npcName(1201001)) + " in " + blue(mapName(140000000)) + " is acting strange all of a sudden. According to the records, the Polearm acts this way when it is calling for its master. #bPerhaps it's calling for you#k. Please return to the island and check things out.");
        }
        sm.dispose();
    }

    @Script("q21200e")
    public static void q21200e(ScriptManager sm) {
        sm.sayNext("Hmmmmmm mmmm mmmmm....");
        sm.setPlayerAsSpeaker(true);
        sm.sayBoth(blue("(Giant Pole Arm is buzzing, but who's that boy standing there?)"));
        sm.sayBoth(blue("(I've never met him before. He doesn't look human.)"));
        sm.setPlayerAsSpeaker(false);
        sm.sayBoth("Hey Aran! Do you still not hear me? Seriously, can't you hear me? Ahhh, this is frustrating!");
        sm.setPlayerAsSpeaker(true);
        sm.sayBoth(blue("(Whoa, who was that? Sounds like an angry boy...)"));
        sm.setPlayerAsSpeaker(false);
        sm.sayBoth("Seriously, the one master I had turned out to be trapped in ice for hundreds of years, abandoning the weapon, and now the 'master' can't even hear me?");
        sm.sayBoth("Who are you?");
        sm.sayBoth("Aran? Do you hear me now? It's me, it's me! I'm your weapon " + blue("Maha the pole arm!") + "!");
        sm.setPlayerAsSpeaker(true);
        sm.sayBoth(blue("(...Maha? Giant pole Arm actually talks?)"));
        sm.setPlayerAsSpeaker(false);
        sm.sayBoth("Why do you have that look on your face like you can't believe it? I see that you have lost all your memories, but... did you also forget about me? How can you do that to me??");
        sm.setPlayerAsSpeaker(true);
        sm.sayBoth("I'm sorry, but seriously... I don't remember a thing.");
        sm.setPlayerAsSpeaker(false);
        if (sm.askYesNo("Is that all you can say after all those years? I'm sorry? Do you understand how bored I was all by myself for hundreds of years? Bring it out if you can. Bring your memories out! Bring them all out! Dig them up if you need to!")) {
            sm.setPlayerAsSpeaker(true);
            sm.sayBoth(blue("(The voice that claims to be Maha the Giant Pole Arm seem quite perturbed. This conversation is going nowhere. I better talk to Lirin first.)"));
            sm.setPlayerAsSpeaker(false);
            sm.forceCompleteQuest(21200);
            sm.forceStartQuest(21202);
            sm.forceStartQuest(21203);

            if (!sm.askYesNo("Would you like to skip the video clip?  Even if you skip the scene, game play will not be affected.")) {
                sm.warp(914090200);
            }
        } else {
            sm.sayOk("Hey, at least you tell me you tried!");
        }
    }

    @Script("q29924s")
    public static void q29924s(ScriptManager sm) {
        if (sm.hasItem(1142129, 1) || sm.addItem(1142129, 1)) {
            sm.forceStartQuest(29924);
            sm.forceCompleteQuest(29924);
        }
    }

    @Script("q21714s")
    public static void q21714s(ScriptManager sm) {
        //
        sm.sayNext("I don't know how you knew this, but I guessed it right away. Not long ago, the "  + mobName(1110100) +" in the southern part of the Magic Forest suddenly became violent. Many " + mobName(1110100) + " became very strange and gloomy.");
        sm.sayBoth("I heard that this phenomenon seems to have occurred in many places, so I asked around and it seems that all the abnormal phenomena are related to some kind of doll. Dolls... are really very strange.");
        sm.sayBoth("I don't know if the rumors are true, but maybe the incident with " + mobName(1110100) + " is also related to the doll. I don't know why you want to know why " + mobName(1110100) + " became violent, but if you want to know, you can investigate with me. How about it? Are you willing?");
        if (sm.askYesNo("I don't know if it's true as the rumors say, that the reason for the change in " + mobName(1110100) + " is the puppet... Please go hunting " + red("25 " + mobName(1110130)) + " and find " + blue(mobName(1110130) + "'s puppet."))) {
            sm.forceStartQuest(21714);
        }
    }

    @Script("q21716s")
    public static void q21716s(ScriptManager sm) {
        sm.sayNext("What did " + npcName(1032112) + " say?");
        sm.setPlayerAsSpeaker(true);
        sm.sayBoth(blue("(You tell her what " + npcName(1032112) + " observed.)"));
        sm.setPlayerAsSpeaker(false);
        if (!sm.askAccept("A kid with a puppet? That seems very suspicious. I am sure that kid is the reason the Green Mushrooms have suddenly turned violent.")) {
            sm.sayOk("What? I don't think there are any suspects besides that kid. Please think again.");
            return;
        }

        sm.sayNext("How dare this kid wreak havoc in the South Forest. Who knows how long it will take to restore the forest... I'll have to devote most of my time cleaning up the mess.");
        sm.forceStartQuest(21716);
        sm.setPlayerAsSpeaker(true);
        sm.sayBoth(blue("(You were able to find out what caused the changes in the Green Mushrooms. You should report " + npcName(1002104) + " and deliver the information you've collected.)"));
        sm.setPlayerAsSpeaker(false);
    }

    @Script("q21719s")
    public static void q21719s(ScriptManager sm) {
        sm.sayNext("Aren't you the one that used to be in " + mapName(101000000) + " until not too long ago? I finally found you! Do you know how long it took for me to finally find you?");
        sm.sayBoth("Who are you?");
        if (sm.askAccept("Me? If you want to know, stop by my cave. I'll even send you an invitation. You'll be directly sent to my cave as soon as you accept. Look forward to seeing you there.")) {
            sm.forceCompleteQuest(21719);
            sm.warp(910510200);
        }
    }

    @Script("dollMaster00")
    public static void dollMaster00(ScriptManager sm) {
        sm.sayNext("I'm Francis, the puppeteer of the Black Wings. How dare you disturb my puppets. It really upsets me, but i'll let it slide this time. I'll catch you doing it again though, I swear in the name of the Black Wings, I will make you pay for it.");
        sm.setPlayerAsSpeaker(true);
        sm.sayBoth(blue("(The Black Wings? Huh? Who are they? And how is all this related to the Black Mage? Hm, maybe you should report this info to Tru.)"));
        sm.setPlayerAsSpeaker(false);
        sm.setQRValue(QuestRecordType.PuppeteersWarning, "0");
        sm.warp(104000004);
    }

    @Script("q21720e")
    public static void q21720e(ScriptManager sm) {
        sm.sayNext("What can I do for you? Tru sent me a message saying that you've been training diligently in Victoria Island while helping him with his work. What is it? What? The Black Wings?");
        sm.setPlayerAsSpeaker(true);
        sm.sayBoth(blue("(You tell her about the Puppeteer and the Black Wings, and about their mission.)"));
        sm.setPlayerAsSpeaker(false);
        sm.sayBoth("I see... I didn''t know there was a group called the Black Wings... They must be fools if they're trying to revive the Black Mage, knowing how dangerous he is.");
        sm.setPlayerAsSpeaker(true);
        sm.sayBoth("That... That's true... " + blue("(She's definitely not afraid to speak her mind.)"));
        sm.setPlayerAsSpeaker(false);
        sm.sayBoth("The Book of Prophecy states that the hero will revive and fight against the Black Mage. I wasn't sure if that was true, but this confirms that the Black Mage is still around.");
        sm.setPlayerAsSpeaker(true);
        sm.sayBoth("Aren't you scared?");
        sm.setPlayerAsSpeaker(false);
        if (sm.askYesNo("Scared? Pfft. Who cares if the Black Mage appears. You'll be here to protect us. If anything, this makes me want to prepare you for the big battle. Ah, that reminds me, I found a #bskill#k. Would you like to see it?")) {
            sm.forceStartQuest(21720);
            sm.forceCompleteQuest(21720);
            sm.addSkill(21001003, 0, 20);
            sm.getUser().addQuestExp(3900);
            sm.reservedEffect("Effect/BasicEff.img/AranGetSkill");
            sm.setPlayerAsSpeaker(true);
            sm.sayBoth(blue("(You remembered the Polearm Booster skill!)"));
            sm.setPlayerAsSpeaker(false);
            sm.sayBoth("This skill was found in an ancient incomprehensible script. I had a hunch it might be a skill you used in the past, and I think I was right. You're not as strong as you used to be, but you'll get there, in time.");
            sm.sayBoth("You are steadily becoming more powerful, and I''ll be here to keep motivating you. You have nothing to be afraid of. You will not lose the battle. You didn't emerge from ice only to lose to the Black Mage, did you? This time, you''ll finish him, once and for all!");
            sm.sayBoth("To do so, there''s only one thing you can do. Train, train, train. Head to Victoria Island and continue training. Let''s make sure you become so powerful that the Black Mage doesn't stand a chance!");
        }
    }

    @Script("rienCaveEnter")
    public static void rienCaveEnter(ScriptManager sm) {
        if (sm.hasQuestStarted(21201) || sm.hasQuestStarted(21302)) {
            sm.playPortalSE();
            sm.warp(140030000);
        } else {
            sm.message("Something seems to be blocking this portal!");
        }
    }

    @Script("enterMCave")
    public static void enterMCave(ScriptManager sm) {
        if (sm.hasQuestStarted(21201)) {
            sm.playPortalSE();
            sm.warpInstance(914021000, "out00", 140030000, 60 * 30);
            sm.setQRValue(QuestRecordType.SecondJobAran, "0");
        } else if (sm.hasQuestStarted(21302) && !sm.hasQuestCompleted(21302)) {
            // 914022100 - sharp
            // 914022000 - black crow
            sm.playPortalSE();
            sm.setQRValue(QuestRecordType.SecondJobAran, "1");
            sm.warpInstance(914022100, "out00", 140030000, 60 * 30);
        } else {
            sm.sayOk("You have already passed your test, there is no need to access the mirror again.");
        }
    }

    @Script("moveNext")
    public static void moveNext(ScriptManager sm) {
        sm.playPortalSE();
        sm.warp(sm.getFieldId() + 10, "east00");
    }

    @Script("moveBefore")
    public static void moveBefore(ScriptManager sm) {
        sm.playPortalSE();
        sm.warp(sm.getFieldId() - 10, "west00");
    }

    @Script("q21202e")
    public static void q21202e(ScriptManager sm) {
        sm.sayNext("Hah! You have proven your worth.. and you shall get what you want; the best pole arm possible!");

        if (sm.getUser().getCharacterStat().getSp().getNonExtendSp() > (sm.getLevel() - 30) * 3) {
            sm.sayOk("You still have way too much #bSP#k with you. You can't earn a new title like that. I strongly urge you to use more SP on your 1st and second level skills.");
            return;
        }

        sm.setPlayerAsSpeaker(true);
        sm.sayNext("My memories are returning...");
        sm.setPlayerAsSpeaker(false);

        sm.setJob(Job.ARAN_2);
        sm.removeItem(4032311, 30);
        sm.forceCompleteQuest(21201);
        sm.forceCompleteQuest(21202);

        sm.sayOk("Haha! You've got what you want, now leave!");
    }

    @Script("q29925s")
    public static void q29925s(ScriptManager sm) {
        if (sm.hasItem(1142130, 1)) {
            sm.addItem(1142130, 1);
        }
        sm.forceStartQuest(29925);
        sm.forceCompleteQuest(29925);
    }

    @Script("q21300s")
    public static void q21300s(ScriptManager sm) {
        sm.setSpeakerId(1201000);
        sm.sayNext("How is the training going? Hm, Lv. 60? You still have a long way to go, but it's definitely praiseworthy compared to the first time I met you. Continue to train diligently, and I'm sure you'll regain your strength soon!");
        if (sm.askYesNo("But first, you must head to " + blue(mapName(140000000)) + " your " + blue(npcName(1201001)) + " is acting weird again. I think it has something to tell you. It might be able to restore your abilities, so please hurry.")) {
            sm.forceStartQuest(21300);
            sm.sayOk("Anyway, I thought it was really something that a weapon had its own identity, but this weapon gets extremely annoying. It cries, saying that I'm not paying attention to its needs, and now... Oh, please keep this a secret from the Polearm. I don't think it's a good idea to upset the weapon any more than I already have.");
        }
    }

    @Script("periItem0")
    public static void periItem0(ScriptManager sm) {
        sm.dropRewards(List.of(
                Reward.item(4032319, 1, 1, 0.6)
        ));
    }

    @Script("q21717s")
    public static void q21717s(ScriptManager sm) {
        sm.message("Quest not implemented, please let GM know.");
    }

    @Script("q21600s")
    public static void q21600s(ScriptManager sm) {
        sm.sayNext("Hey, Aran. You seem pretty strong, since that time from when you got freed from the glacier. Suitable enough to #bride a wolf#k, if you ask me.");
        if (sm.askAccept("Picked your interest, huh? Very well, first you must make your way to #bAqua#k, there is a person there who makes #rfood for wolf cubs#k. Bring one portion to me, and I shall deem you able to tame and take care of one. What do you say, will you try for it?")) {
            sm.forceStartQuest(21600);
            sm.sayBoth("Alright. The one you must meet is #bNanuke#k, she is on top of a #rsnowy whale#k, somewhere in the ocean. Good luck!");
        }
    }
}

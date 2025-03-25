package mapleglory.script.quest;

import mapleglory.script.common.Script;
import mapleglory.script.common.ScriptHandler;
import mapleglory.script.common.ScriptManager;
import mapleglory.util.Tuple;
import mapleglory.world.field.mob.MobAppearType;
import mapleglory.world.item.InventoryType;
import mapleglory.world.job.Job;
import mapleglory.world.quest.QuestRecordType;

import java.util.List;
import java.util.Map;

public final class ExplorerQuest extends ScriptHandler {
    public static final int CLERIC = 230;
    public static final int WIZARD_FP = 210;
    public static final int WIZARD_IL = 220;
    @Script("enter_archer")
    public static void enter_archer(ScriptManager sm) {
        //  Power B. Fore : Entrance to Bowman Training Center (1012119)
        //  Singing Mushroom Forest : Spore Hill (100020000)
        if (sm.hasQuestStarted(22518)) {
            sm.warpInstance(910060100, "start", 100020000, 60 * 30);
            return;
        }
        sm.warp(910060000); // Victoria Road : Bowman Training Center
    }

    @Script("enter_thief")
    public static void enter_thief(ScriptManager sm) {
        if (sm.hasQuestStarted(22515) || sm.hasQuestStarted(22516) || sm.hasQuestStarted(22517) || sm.hasQuestStarted(22518)) {
            sm.warpInstance(910310000, "start", 103010000, 60 * 30);
            return;
        }
        sm.warp(910310000);
    }

    @Script("enter_warrior")
    public static void enter_warrior(ScriptManager sm) {
        if (sm.hasQuestStarted(22515) || sm.hasQuestStarted(22516) || sm.hasQuestStarted(22517) || sm.hasQuestStarted(22518)) {
            sm.warpInstance(910220000, "start", 103010000, 60 * 30);
            return;
        }
        sm.warp(910220000);
    }

    @Script("enter_magicion")
    public static void enter_magicion(ScriptManager sm) {
        if (sm.hasQuestStarted(22515) || sm.hasQuestStarted(22516) || sm.hasQuestStarted(22517) || sm.hasQuestStarted(22518)) {
            sm.warpInstance(910120000, "start", 101000000, 60 * 30);
            return;
        }
        sm.warp(910120000);
    }

    @Script("rogue")
    public static void rogue(ScriptManager sm) {
        // Dark Lord : Thief Job Advancement
        if(sm.getUser().getJob() == 0) {
            sm.sayNext("Want to be a #rthief#k? There are some standards to meet. because we can't just accept EVERYONE in... #bYour level should be at least 10, with at least 25 DEX#k. Let's see.");
            if(sm.getUser().getLevel() >= 10) {
                if(sm.askYesNo("Oh...! You look like someone that can definitely be a part of us... all you need is a little sinister mind, and... yeah... so, what do you think? Wanna be the Rogue?")) {
                    if (!sm.addItems(List.of(
                            Tuple.of(2070000, 500),
                            Tuple.of(1472061, 1),
                            Tuple.of(1332063, 1)
                    ))) {
                        sm.sayOk("Make some room in your inventory and talk back to me.");
                        return;
                    }
                    sm.addInventorySlots(InventoryType.EQUIP, 4);
                    sm.addInventorySlots(InventoryType.ETC, 4);
                    sm.setJob(Job.ROGUE);
                    if(sm.hasQuestStarted(2351)) {
                        sm.setQRValue(QuestRecordType.DualBladeInfiltration, "1");
                    }
                    sm.sayNext("Alright, from here out, you are a part of us! You'll be living the life of a wanderer at ..., but just be patient as soon, you'll be living the high life. Alright, it ain't much, but I'll give you some of my abilities... HAAAHHH!!!");
                    sm.sayBoth("You've gotten much stronger now. Plus every single one of your inventories have added slots. A whole row, to be exact. Go see for it yourself. I just gave you a little bit of #bSP#k. When you open up the #bSkill#k menu on the lower left corner of the screen, there are skills you can learn by using SP's. One warning, though: You can't raise it all together all at once. There are also skills you can acquire only after having learned a couple of skills first.");
                    sm.sayBoth("Now a reminder. Once you have chosen, you cannot change up your mind and try to pick another path. Go now, and live as a proud Thief.");
                }
            } else {
                sm.sayOk("Train a bit more until you reach the base requirements and I can show you the way of the #rThief#k.");
            }
        } else if(sm.getLevel() >= 30 && sm.getJob() == Job.ROGUE) {
            if(!(sm.hasItem(4031011, 1) || sm.hasItem(4031012, 1))) {
                if(!sm.askYesNo("Hmmm...you seem to have gotten a whole lot stronger. You got rid of the old, weak self and and look much more like a thief now. Well, what do you think? Don't you want to get even more powerful than that? Pass a simple test and I'll do just that for you. Do you want to do it?")) {
                    sm.sayNext("Really? It will help you out a great deal on your journey if you get stronger fast...if you choose to change your mind in the future, please feel free to come back. Know that I'll make you much more powerful than you are right now.");
                    return;
                }

                sm.sayNext("Good thinking. But, I need to make sure you are as strong as you look. It's not a hard test, one that should be easy for you to pass. First, take this letter...make sure you don't lose it.");
                if(!sm.canAddItem(4031011, 1)) { // Dark Lord's Letter
                    sm.sayOk("Please make room in your inventory.");
                    return;
                }

                sm.addItem(4031011, 1);
                sm.sayBoth("Please take this letter to #b#p1072003##k at #b#m103030400##k near Kerning City. He's doing the job of an instructor in place of me. Give him the letter and he'll give you the test for me. If you want more details, hear it straight from him. I'll be wishing you good luck.");
            } else if (!sm.hasItem(4031012, 1) && sm.hasItem(4031011, 1)) {
                sm.sayNext("Still haven't met the person yet? Find #b#p1072003##k who's around #b#m103030400##k near Kerning City. Give the letter to him and he may let you know what to do.");
            } else if (sm.hasItem(4031012, 1) && !sm.hasItem(4031011, 1)) {
                sm.sayNext("Hmmm...so you got back here safely. I knew that test would be too easy for you. I admit, you are a great great thief. Now...I'll make you even more powerful than you already are. But, before all that...you need to choose one of two ways. It'll be a difficult decision for you to make, but...if you have any questions, please ask.");
                final int answer = sm.askMenu("I'll choose my occupation!", Map.of(
                        0, "Assassin",
                        1, "Bandit"
                ));

                if(answer == 0) {
                    // Assassin
                    if (!sm.askYesNo("So you want to make the 2nd job advancement as the #bAssassin#? Once you have made the decision, you can't go back and change your mind. You ARE sure about this, right?")) {
                        sm.sayNext("Really? Have to give more thought to it, huh? Take your time, take your time. This is not something you should take lightly ... come talk to me once you have made your decision.");
                        return;
                    }

                    if(sm.getUser().getCharacterStat().getSp().getNonExtendSp() > (sm.getLevel() - 30) * 3) {
                        sm.sayNext("Hmmm...you have too much SP...you can't make the 2nd job advancement with that many SP in store. Use more SP on the skills on the 1st level and then come back.");
                        return;
                    }

                    sm.setJob(Job.ASSASSIN);
                    sm.removeItem(4031012);
                    sm.addInventorySlots(InventoryType.EQUIP, 4);
                    sm.addInventorySlots(InventoryType.ETC, 4);
                    sm.sayNext("Alright, from here on out you are the #bAssassin#. Assassins revel in shadows and darkness, waiting until the right time comes for them to stick a dagger through the enemy's heart, suddenly and swiftly... please keep training. I'll make you even more powerful than you are right now!");
                    sm.sayBoth("I have just given you a book that gives you the the list of skills you can acquire as an assassin. I have also added a whole row to your use inventory, along with boosting up your max HP and MP...go see for it yourself.");
                    sm.sayBoth("I have also given you a little bit of #bSP#. Open the #bSkill Menu# located at the bottom left corner. You'll be able to boost up the newly-acquired 2nd level skills. A word of warning though: You can't boost them up all at once. Some of the skills are only available after you have learned other skills. Make sure to remember that.");
                    sm.sayBoth("Assassins have to be strong. But remember that you can't abuse that power and use it on a weakling. Please use your enormous power the right way, because...for you to use that the right way, that is much harder than just getting stronger. Find me after you have advanced much further.");
                } else if(answer == 1) {
                    // Bandit
                    if (!sm.askYesNo("So you want to make the 2nd job advancement as the #bBandit#? Once you have made the decision, you can't go back and change your mind. You ARE sure about this, right?")) {
                        sm.sayNext("Really? Have to give more thought to it, huh? Take your time, take your time. This is not something you should take lightly ... come talk to me once you have made your decision.");
                        return;
                    }

                    if(sm.getUser().getCharacterStat().getSp().getNonExtendSp() > (sm.getLevel() - 30) * 3) {
                        sm.sayNext("Hmmm...you have too much SP...you can't make the 2nd job advancement with that many SP in store. Use more SP on the skills on the 1st level and then come back.");
                        return;
                    }

                    sm.setJob(Job.BANDIT);
                    sm.removeItem(4031012);
                    sm.addInventorySlots(InventoryType.EQUIP, 4);
                    sm.addInventorySlots(InventoryType.ETC, 4);
                    sm.sayNext("Alright, from here on out you are the #bBandit#k. Bandits have quick hands and quicker feet to dominate the enemies... please keep training. I'll make you even more powerful than you are right now!");
                    sm.sayBoth("I have just given you a book that gives you the the list of skills you can acquire as an assassin. I have also added a whole row to your use inventory, along with boosting up your max HP and MP...go see for it yourself.");
                    sm.sayBoth("I have also given you a little bit of #bSP#k. Open the #bSkill Menu#k located at the bottom left corner. You'll be able to boost up the newly-acquired 2nd level skills. A word of warning though: You can't boost them up all at once. Some of the skills are only available after you have learned other skills. Make sure to remember that.");
                    sm.sayBoth("Bandits have to be strong. But remember that you can't abuse that power and use it on a weakling. Please use your enormous power the right way, because...for you to use that the right way, that is much harder than just getting stronger. Find me after you have advanced much further.");
                }
            }
        } else {
            sm.sayNext("Exploring is good, and getting stronger is good and all... but don't you want to enjoy living the life as you know it? How about becoming a Rouge like us and really LIVE the life? Sounds fun, isn't it?");
        }
    }

    @Script("change_rogue")
    public static void change_rogue(ScriptManager sm) {
        // 2nd Job Advancement Job Instructor
        if(sm.hasItem(4031011, 1) && !sm.hasItem(4031013, 1)) {
            sm.sayNext("Hmmm...it is definitely the letter from #b#p1052001##k... so you came all the way here to take the test and make the 2nd job advancement as the rogue. Alright, I'll explain the test to you. Don't sweat it much, though; it's not that complicated.");
            sm.sayBoth("I'll send you to a hidden map. You'll see monsters not normally seen in normal fields. They look the same like the regular ones, but with a totally different attitude. They neither boost your experience level nor provide you with item.");
            sm.sayBoth("You'll be able to acquire a marble called #b#t4031013##k while knocking down those monsters. It is a special marble made out of their sinister, evil minds. Collect 30 of those, then go talk to a colleague of mine in there. That's how you pass the test.");
            if(!sm.askYesNo("Once you go inside, you can't leave until you take care of your mission. If you die, your experience level will decrease...so you better really buckle up and get ready...well, do you want to go for it now?")) {
                sm.sayNext("You don't seem too prepared for this. Find me when you ARE ready. There are neither portals or stores inside, so you better get 100% ready for it.");
                return;
            }

            sm.sayNext("Alright! I'll let you in! Defeat the monster inside to earn 30 Dark Marble and then talk to my colleague inside; he'll give you #b#t4031012##k as a proof that you've passed the test. Best of luck to you. ");
            sm.warp(910370000);
        } else if (sm.hasItem(4031011, 1) && sm.hasItem(4031013)) {
            if(!sm.askYesNo("So you've given up in the middle of this before. Don't worry about it, because you can always retake the test. Now...do you want to go back in and try again?")) {
                sm.sayNext("You don't seem too prepared for this. Find me when you ARE ready. There are neither portals or stores inside, so you better get 100% ready for it.");
                return;
            }

            sm.sayNext("Alright! I'll let you in! Sorry to say this, but I have to take away all your marbles beforehand. Defeat the monsters inside, collect 30 Dark Marbles, then strike up a conversation with a colleague of mine inside. He'll give you the #b#t4031012##k, the proof that you've passed the test. Best of luck to you.");
            sm.removeItem(4031013);
            sm.warp(910370000);
        }
    }

    @Script("inside_rogue")
    public static void inside_rogue(ScriptManager sm) {
        if(sm.hasItem(4031013, 30)) {
            sm.sayNext("Ohhhhh...you collected all 30 Dark Marbles!! Wasn't it difficult?? Alright. You've passed the test and for that, I'll reward you #b#t4031012##k. Take that item and go back to Kerning City.");
            sm.removeItem(4031013);
            sm.removeItem(4031011);
            sm.addItem(4031012, 1);
            sm.warp(103030400);
        } else {
            if(sm.askYesNo("What's going on? Doesn't look like you have collected 30 #b#t4031013##k yet... If you're having problems with it, then you can leave, come back and try it again. So... wanna give up and get out of here?")) {
                sm.sayNext("Really... alright, I'll let you out. Please don't give up, though. You can always try again, so do not give up. Until then, bye...");
                sm.warp(103030400);
            } else {
                sm.sayNext("That's right! Stop acting weak and start collecting the marbles. Talk to me when you have collected 30 #b#t4031013##k.");
            }
        }
    }

    @Script("bowman")
    public static void bowman(ScriptManager sm) {
        // Athena Pierce : Bowman Job Advancement
        if(sm.getUser().getJob() == 0) {
            sm.sayNext("So you decided to become a #rbowman#k? There are some standards to meet, y'know... #bYour level should be at least 10, with at least DEX 25#k. Let's see.");
            if(sm.getUser().getLevel() >= 10) {
                sm.sayBoth("It is an important and final choice. You will not be able to turn back.");
                if (!sm.addItems(List.of(
                        Tuple.of(1452051, 1),
                        Tuple.of(2060000, 1000)
                ))) {
                    sm.sayOk("Make some room in your inventory and talk back to me.");
                    return;
                }
                sm.addInventorySlots(InventoryType.EQUIP, 4);
                sm.addInventorySlots(InventoryType.ETC, 4);
                sm.setJob(Job.ARCHER);
                sm.sayNext("Alright, from here out, you are a part of us! You'll be living the life of a wanderer at ..., but just be patient as soon, you'll be living the high life. Alright, it ain't much, but I'll give you some of my abilities... HAAAHHH!!!");
                sm.sayBoth("You've gotten much stronger now. Plus every single one of your inventories have added slots. A whole row, to be exact. Go see for it yourself. I just gave you a little bit of #bSP#k. When you open up the #bSkill#k menu on the lower left corner of the screen, there are skills you can learn by using SP's. One warning, though: You can't raise it all together all at once. There are also skills you can acquire only after having learned a couple of skills first.");
                sm.sayBoth("Now a reminder. Once you have chosen, you cannot change up your mind and try to pick another path. Go now, and live as a proud Bowman.");
            } else {
                sm.sayOk("Train a bit more until you reach the base requirements and I can show you the way of the #rBowman#k.");
            }
        } else if(sm.getJob() == Job.ARCHER && sm.getLevel() >= 30) {
            // 2nd Job Advancement
            if(!(sm.hasItem(4031010, 1) || sm.hasItem(4031012, 1))) {
                if(!sm.askYesNo("Whoa, you have definitely grown up! You don't look small and weak anymore...rather, now I can feel your presence as the Bowman! Impressive..so, what do you think? Do you want to get even stronger than you are right now? Pass a simple test and I'll do just that! Wanna do it?")) {
                    sm.sayNext("Really? It will help you out a great deal on your journey if you get stronger fast...if you choose to change your mind in the future, please feel free to come back. Know that I'll make you much more powerful than you are right now.");
                    return;
                }

                sm.sayNext("Good thinking. You look strong, don't get me wrong, but there's still a need to test your strength and see if your are for real. The test isn't too difficult, so you'll do just fine... Here, take this letter first. Make sure you don't lose it.");
                if(!sm.canAddItem(4031010, 1)) { // Athena Pierce's Letter
                    sm.sayOk("Please make room in your inventory.");
                    return;
                }

                sm.addItem(4031010, 1);
                sm.sayBoth("Go and see the #rBowman Job Instructor#k somewhere around East Henesys... You'll surely find her.");
            } else if (!sm.hasItem(4031012, 1) && sm.hasItem(4031010, 1)) {
                sm.sayNext("Go and see the #rBowman Job Instructor#k somewhere around East Henesys... You'll surely find her.");
            } else if (sm.hasItem(4031012, 1) && !sm.hasItem(4031010, 1)) {
                sm.sayNext("Well look who's here!...you came back safe! I knew you'd breeze through...I'll admit you are a strong, formidable Bowman...alright, I'll make you an even stronger Bowman than you already are right now... Before THAT! you need to choose one of the two paths that you'll be given.. it isn't going to be easy, so if you have any questions, feel free to ask.");
                final int answer = sm.askMenu("I'll choose my occupation!", Map.of(
                        0, "#bHunter",
                        1, "Crossbow Man#k"
                ));

                if(answer == 0) {
                    // Hunter
                    if (!sm.askYesNo("So you want to make the 2nd job advancement as the #bHunter#k? Once you make the decision, you won't be able to make a job advancement with any other job. Are you sure about this?")) {
                        sm.sayNext("Really? Have to give more thought to it, huh? Take your time, take your time. This is not something you should take lightly ... come talk to me once you have made your decision.");
                        return;
                    }

                    if(sm.getUser().getCharacterStat().getSp().getNonExtendSp() > (sm.getLevel() - 30) * 3) {
                        sm.sayNext("Hmmm...you have too much SP...you can't make the 2nd job advancement with that many SP in store. Use more SP on the skills on the 1st level and then come back.");
                        return;
                    }

                    sm.setJob(Job.HUNTER);
                    sm.removeItem(4031012);
                    sm.addInventorySlots(InventoryType.EQUIP, 4);
                    sm.addInventorySlots(InventoryType.ETC, 4);
                    sm.sayNext("Alright, you're the #bHunter#k from here on out. Hunters are the intelligent bunch with incredible vision, able to pierce the arrow through the heart of the monsters with ease...please train yourself each and everyday. We'll help you become even stronger than you already are.");
                    sm.sayBoth("I have just given you a book that gives you the the list of skills you can acquire as a hunter. I have also added a whole row to your use inventory, along with boosting up your max HP and MP...go see for it yourself.");
                    sm.sayBoth("I have also given you a little bit of #bSP#. Open the #bSkill Menu# located at the bottom left corner. You'll be able to boost up the newly-acquired 2nd level skills. A word of warning though: You can't boost them up all at once. Some of the skills are only available after you have learned other skills. Make sure to remember that.");
                    sm.sayBoth("Hunter needs to be strong. But remember that you can't abuse that power and use it on a weakling. Please use your enormous power the right way, because...for you to use that the right way, that is much harder than just getting stronger. Find me after you have advanced much further. I'll be waiting for you.");
                } else if(answer == 1) {
                    // Crossbow Man
                    if (!sm.askYesNo("So you want to make the 2nd job advancement as the #bCrossbow Man#k? Once you make the decision, you won't be able to make a job advancement with any other job. Are you sure about this?")) {
                        sm.sayNext("Really? Have to give more thought to it, huh? Take your time, take your time. This is not something you should take lightly ... come talk to me once you have made your decision.");
                        return;
                    }

                    if(sm.getUser().getCharacterStat().getSp().getNonExtendSp() > (sm.getLevel() - 30) * 3) {
                        sm.sayNext("Hmmm...you have too much SP...you can't make the 2nd job advancement with that many SP in store. Use more SP on the skills on the 1st level and then come back.");
                        return;
                    }

                    sm.setJob(Job.CROSSBOWMAN);
                    sm.removeItem(4031012);
                    sm.addInventorySlots(InventoryType.EQUIP, 4);
                    sm.addInventorySlots(InventoryType.ETC, 4);
                    sm.sayNext("Alright! You have now become the #bCrossbow Man#k! A Crossbow Man fights tactically, and uses special skills to strike each and every monster's weak spot! Always know your enemy's weakness, or else you will be weak!");
                    sm.sayBoth("I have just given you a book that gives you the the list of skills you can acquire as a crossbow man. I have also added a whole row to your use inventory, along with boosting up your max HP and MP...go see for it yourself.");
                    sm.sayBoth("I have also given you a little bit of #bSP#k. Open the #bSkill Menu#k located at the bottom left corner. You'll be able to boost up the newly-acquired 2nd level skills. A word of warning though: You can't boost them up all at once. Some of the skills are only available after you have learned other skills. Make sure to remember that.");
                    sm.sayBoth("Crossbow Man needs to be strong. But remember that you can't abuse that power and use it on a weakling. Please use your enormous power the right way, because...for you to use that the right way, that is much harder than just getting stronger. Find me after you have advanced much further. I'll be waiting for you.");
                }
            }
        } else {
            sm.sayOk("Those who want to become a bowman... Please come...");
        }
    }

    @Script("fighter")
    public static void fighter(ScriptManager sm) {
        // Dances with Balrog : Warrior Job Instructor
        if(sm.getUser().getJob() == 0) {
            // 1st Job
            sm.sayNext("Do you want to become a #rwarrior#k? You need to meet some criteria in order to do so.#bYou should be at least in level 10, and at least 35 STR#k. Let's see...");
            if(sm.getUser().getLevel() >= 10) {
                sm.sayBoth("It is an important and final choice. You will not be able to turn back.");
                if (!sm.addItem(1302077, 1)) {
                    sm.sayOk("Make some room in your inventory and talk back to me.");
                    return;
                }
                sm.addInventorySlots(InventoryType.EQUIP, 4);
                sm.addInventorySlots(InventoryType.ETC, 4);
                sm.setJob(Job.WARRIOR);
                sm.sayNext("From here on out, you are going to the Warrior path. This is not an easy job, but if you have discipline and confidence in your own body and skills, you will overcome any difficulties in your path. Go, young Warrior!");
                sm.sayBoth("You've gotten much stronger now. Plus every single one of your inventories have added slots. A whole row, to be exact. Go see for it yourself. I just gave you a little bit of #bSP#k. When you open up the #bSkill#k menu on the lower left corner of the screen, there are skills you can learn by using SP's. One warning, though: You can't raise it all together all at once. There are also skills you can acquire only after having learned a couple of skills first.");
                sm.sayBoth("Now a reminder. Once you have chosen, you cannot change up your mind and try to pick another path. Go now, and live as a proud Bowman.");
            } else {
                sm.sayOk("Train a bit more until you reach the base requirements and I can show you the way of the #rWarrior#k.");
            }
        } else if (sm.getJob() == Job.WARRIOR && sm.getLevel() >= 30) {
            // 2nd Job Advancement
            if(!(sm.hasItem(4031008, 1) || sm.hasItem(4031012, 1))) {
                if(!sm.askYesNo("Whoa, you have definitely grown up! You don't look small and weak anymore...rather, now I can feel your presence as the Warrior! Impressive..so, what do you think? Do you want to get even stronger than you are right now? Pass a simple test and I'll do just that! Wanna do it?")) {
                    sm.sayNext("Really? It will help you out a great deal on your journey if you get stronger fast...if you choose to change your mind in the future, please feel free to come back. Know that I'll make you much more powerful than you are right now.");
                    return;
                }

                sm.sayNext("Good thinking. You look strong, don't get me wrong, but there's still a need to test your strength and see if your are for real. The test isn't too difficult, so you'll do just fine... Here, take this letter first. Make sure you don't lose it.");
                if(!sm.canAddItem(4031008, 1)) { // Dances With Barlog's Letter
                    sm.sayOk("Please make room in your inventory.");
                    return;
                }

                sm.addItem(4031008, 1);
                sm.sayBoth("Please get this letter to #bWarrior Job Instructor#k who may be around the highlands here in Perion. He's the one being the instructor now in place of me, as I am busy here. Get him the letter and he'll give you the test in place of me. For more details, hear it straight from him. Best of luck to you.");
            } else if (!sm.hasItem(4031012, 1) && sm.hasItem(4031008, 1)) {
                sm.sayNext("Please get this letter to #bWarrior Job Instructor#k who may be around the highlands here in Perion. He's the one being the instructor now in place of me, as I am busy here. Get him the letter and he'll give you the test in place of me. For more details, hear it straight from him. Best of luck to you.");
            } else if (sm.hasItem(4031012, 1) && !sm.hasItem(4031008, 1)) {
                sm.sayNext("Well look who's here!...you came back safe! I knew you'd breeze through...I'll admit you are a strong, formidable Warrior...alright, I'll make you an even stronger Warrior than you already are right now... Before THAT! you need to choose one of the three paths that you'll be given.. it isn't going to be easy, so if you have any questions, feel free to ask.");
                final int answer = sm.askMenu("I'll choose my occupation!", Map.of(
                        0, "#bFighter",
                        1, "Page",
                        2, "Spearman#k"
                ));

                if(answer == 0) {
                    // Fighter
                    if (!sm.askYesNo("So you want to make the 2nd job advancement as the #bFighter#k? Once you make the decision, you won't be able to make a job advancement with any other job. Are you sure about this?")) {
                        sm.sayNext("Really? Have to give more thought to it, huh? Take your time, take your time. This is not something you should take lightly ... come talk to me once you have made your decision.");
                        return;
                    }

                    if(sm.getUser().getCharacterStat().getSp().getNonExtendSp() > (sm.getLevel() - 30) * 3) {
                        sm.sayNext("Hmmm...you have too much SP...you can't make the 2nd job advancement with that many SP in store. Use more SP on the skills on the 1st level and then come back.");
                        return;
                    }

                    sm.setJob(Job.FIGHTER);
                    sm.removeItem(4031012);
                    sm.addInventorySlots(InventoryType.EQUIP, 4);
                    sm.addInventorySlots(InventoryType.ETC, 4);
                    sm.sayNext("Alright, you're the #bFighter#k from here on out. A fighter strives to become the strongest of the strong, and never stops fighting. Don't ever lose that will to fight, and push forward no matter what. I'll help you become even stronger than you already are.");
                    sm.sayBoth("I have just given you a book that gives you the the list of skills you can acquire as a fighter. I have also added a whole row to your use inventory, along with boosting up your max HP and MP...go see for it yourself.");
                    sm.sayBoth("I have also given you a little bit of #bSP#. Open the #bSkill Menu# located at the bottom left corner. You'll be able to boost up the newly-acquired 2nd level skills. A word of warning though: You can't boost them up all at once. Some of the skills are only available after you have learned other skills. Make sure to remember that.");
                    sm.sayBoth("A Fighter needs to be strong. But remember that you can't abuse that power and use it on a weakling. Please use your enormous power the right way, because...for you to use that the right way, that is much harder than just getting stronger. Find me after you have advanced much further. I'll be waiting for you.");
                } else if(answer == 1) {
                    // Page
                    if (!sm.askYesNo("So you want to make the 2nd job advancement as the #bPage#k? Once you make the decision, you won't be able to make a job advancement with any other job. Are you sure about this?")) {
                        sm.sayNext("Really? Have to give more thought to it, huh? Take your time, take your time. This is not something you should take lightly ... come talk to me once you have made your decision.");
                        return;
                    }

                    if(sm.getUser().getCharacterStat().getSp().getNonExtendSp() > (sm.getLevel() - 30) * 3) {
                        sm.sayNext("Hmmm...you have too much SP...you can't make the 2nd job advancement with that many SP in store. Use more SP on the skills on the 1st level and then come back.");
                        return;
                    }

                    sm.setJob(Job.PAGE);
                    sm.removeItem(4031012);
                    sm.addInventorySlots(InventoryType.EQUIP, 4);
                    sm.addInventorySlots(InventoryType.ETC, 4);
                    sm.sayNext("Alright! You have now become the #bPage#k! A Page fights tactically, and uses special skills to strike each and every monster's weak spot! Always know your enemy's weakness, or else you will be weak!");
                    sm.sayBoth("I have just given you a book that gives you the the list of skills you can acquire as a page. I have also added a whole row to your use inventory, along with boosting up your max HP and MP...go see for it yourself.");
                    sm.sayBoth("I have also given you a little bit of #bSP#k. Open the #bSkill Menu#k located at the bottom left corner. You'll be able to boost up the newly-acquired 2nd level skills. A word of warning though: You can't boost them up all at once. Some of the skills are only available after you have learned other skills. Make sure to remember that.");
                    sm.sayBoth("A Page needs to be strong. But remember that you can't abuse that power and use it on a weakling. Please use your enormous power the right way, because...for you to use that the right way, that is much harder than just getting stronger. Find me after you have advanced much further. I'll be waiting for you.");
                } else if(answer == 2) {
                    // Spearman
                    if (!sm.askYesNo("So you want to make the 2nd job advancement as the #bSpearman#k? Once you make the decision, you won't be able to make a job advancement with any other job. Are you sure about this?")) {
                        sm.sayNext("Really? Have to give more thought to it, huh? Take your time, take your time. This is not something you should take lightly ... come talk to me once you have made your decision.");
                        return;
                    }

                    if(sm.getUser().getCharacterStat().getSp().getNonExtendSp() > (sm.getLevel() - 30) * 3) {
                        sm.sayNext("Hmmm...you have too much SP...you can't make the 2nd job advancement with that many SP in store. Use more SP on the skills on the 1st level and then come back.");
                        return;
                    }

                    sm.setJob(Job.SPEARMAN);
                    sm.removeItem(4031012);
                    sm.addInventorySlots(InventoryType.EQUIP, 4);
                    sm.addInventorySlots(InventoryType.ETC, 4);
                    sm.sayNext("Alright! You have now become the #bSpearman#k! A Spearman fights with all of his might and soul! Using long-range weapons, they can attack monsters from a farther distance than other Warriors! Not to mention Spearman have the most powerful weapons!");
                    sm.sayBoth("I have just given you a book that gives you the the list of skills you can acquire as a spearman. I have also added a whole row to your use inventory, along with boosting up your max HP and MP...go see for it yourself.");
                    sm.sayBoth("I have also given you a little bit of #bSP#k. Open the #bSkill Menu#k located at the bottom left corner. You'll be able to boost up the newly-acquired 2nd level skills. A word of warning though: You can't boost them up all at once. Some of the skills are only available after you have learned other skills. Make sure to remember that.");
                    sm.sayBoth("A Spearman needs to be strong. But remember that you can't abuse that power and use it on a weakling. Please use your enormous power the right way, because...for you to use that the right way, that is much harder than just getting stronger. Find me after you have advanced much further. I'll be waiting for you.");
                }
            }
        } else if (sm.getLevel() >= 120 && sm.getUser().is3rdJob()) {

        }
    }

    @Script("inside_swordman")
    public static void inside_fighter(ScriptManager sm) {
        if (!sm.hasItem(4031013, 30)) {
            if(sm.askMenu("You will have to collect me #b30 #t4031013##k. Good luck.", Map.of(0, "#bI would like to leave.")) == 0) {
                sm.warp(102000000);
            }
        } else {
            sm.sayNext("Ohhhhh.. you collected all 30 Dark Marbles!! It should have been difficult... just incredible! Alright. You've passed the test and for that, I'll reward you #bThe Proof of a Hero#k. Take that and go back to Perion.");
            sm.removeItem(4031008);
            sm.removeItem(4031013);
            sm.forceCompleteQuest(1000011);
            if(!sm.canAddItem(4031012, 1)) {
                sm.sayOk("Make some space in your inventory and talk back to me.");
                return;
            }
            sm.addItem(4031012, 1);
            sm.warp(102000000);
        }
    }

    @Script("change_swordman")
    public static void change_fighter(ScriptManager sm) {
        if (sm.hasQuestCompleted(100010)) {
            sm.sayOk("You're truly a hero!");
        } else if (sm.hasItem(4031008, 1)) {
            sm.sayNext("Hmmm...it is definitely the letter from #bDances with Balrog#k...so you came all the way here to take the test and make the 2nd job advancement as the warrior. Alright, I'll explain the test to you. Don't sweat it too much, it's not that complicated.");
            sm.sayBoth("I'll send you to a hidden map. You'll see monsters you don't normally see. They look the same like the regular ones, but with a totally different attitude. They neither boost your experience level nor provide you with item.");
            sm.sayBoth("You'll be able to acquire a marble called #b#t4031013##k while knocking down those monsters. It is a special marble made out of their sinister, evil minds. Collect 30 of those, and then go talk to a colleague of mine in there. That's how you pass the test.");
            if(sm.askYesNo("Once you go inside, you can't leave until you take care of your mission. If you die, your experience level will decrease..so you better really buckle up and get ready...well, do you want to go for it now?")) {
                sm.forceStartQuest(100010);
                sm.sayOk("You will have to collect me #b30 #t4031013##k. Good luck.");
                sm.warp(910230000);
            }
        }
    }

    @Script("inside_pirate")
    public static void inside_pirate(ScriptManager sm) {
        boolean brawler = sm.hasQuestStarted(2191);
        boolean gunslinger = sm.hasQuestStarted(2192);

        int item;
        if (brawler) {
            item = 4031856;
        } else if (gunslinger) {
            item = 4031857;
        } else {
            item = 0;
        }

        if (sm.hasItem(item, 15)) {
            sm.sayNext("Ohhh... So you managed to gather up 15 " + itemName(item) + "! Wasn't it tough? That's amazing... alright then, now let's talk about The Nautilus.");
            sm.sayBoth("These crystals can only be used here, so I'll just take them back.");
            sm.warp(120000101);
        } else {
            if (sm.askYesNo("Hmmm... What is it? I don't think you have been able to gather up all #b15 " + itemName(item) + "#k yet... If it's too hard for you, then you can step out and try again later. Do you want to give up and step outside right now?")) {
                sm.sayOk("Good. You're showing me you don't want to give up this great opportunity. When you collect #b15 " + itemName(item) + "#k, then talk to me.");
                return;
            }

            sm.sayOk("Really? Ok, I'll take you outside right now. Please don't give up, though. You'll get the opportunity to try this again. Hopefully by then, you'll be ready to handle this with ease...");
            sm.warp(120000101);
        }
    }

    @Script("inside_magician")
    public static void inside_magician(ScriptManager sm) {
        if (!sm.hasItem(4031013, 30)) {
            if(sm.askMenu("You will have to collect me #b30 #t4031013##k. Good luck.", Map.of(0, "#bI would like to leave.")) == 0) {
                sm.warp(101040300);
            }
        } else {
            sm.sayNext("Ohhhhh.. you collected all 30 Dark Marbles!! It should have been difficult... just incredible! Alright. You've passed the test and for that, I'll reward you #bThe Proof of a Hero#k. Take that and go back to Ellinia.");
            sm.removeItem(4031009);
            sm.removeItem(4031013);
            sm.forceCompleteQuest(100007);
            if(!sm.canAddItem(4031012, 1)) {
                sm.sayOk("Make some space in your inventory and talk back to me.");
                return;
            }
            sm.addItem(4031012, 1);
            sm.warp(101040300);
        }
    }

    @Script("change_archer")
    public static void change_archer(ScriptManager sm) {
        if (sm.hasQuestCompleted(100001)) {
            sm.sayOk("You're truly a hero!");
        } else if (sm.hasItem(4031010, 1)) {
            sm.sayNext("Oh, isn't this a letter from #bAthena#k?");
            sm.sayNext("So you want to prove your skills? Very well...");
            if(sm.askYesNo("I will give you a chance if you're ready.")) {
                sm.forceStartQuest(100001);
                sm.sayOk("You will have to collect me #b30 #t4031013##k. Good luck.");
                sm.warp(910070000);
            }
        }
    }

    @Script("inside_archer")
    public static void inside_archer(ScriptManager sm) {
        if(sm.hasItem(4031013, 30)) {
            sm.sayNext("Ohhhhh.. you collected all 30 Dark Marbles!! It should have been difficult.. just incredible! Alright. You've passed the test and for that, I'll reward you #bThe Proof of a Hero#k. Take that and go back to Henesys.");
            sm.removeItem(4031013);
            sm.removeItem(4031010);
            sm.addItem(4031012, 1);
            sm.warp(100040400);
        } else {
            if(sm.askYesNo("What's going on? Doesn't look like you have collected 30 #b#t4031013##k yet... If you're having problems with it, then you can leave, come back and try it again. So... wanna give up and get out of here?")) {
                sm.sayNext("Really... alright, I'll let you out. Please don't give up, though. You can always try again, so do not give up. Until then, bye...");
                sm.warp(100040400);
            } else {
                sm.sayNext("That's right! Stop acting weak and start collecting the marbles. Talk to me when you have collected 30 #b#t4031013##k.");
            }
        }
    }

    @Script("change_magician")
    public static void change_magician(ScriptManager sm) {
        if (sm.hasQuestCompleted(100007)) {
            sm.sayOk("You're truly a hero!");
        } else if (sm.hasQuestCompleted(100006)) {
            sm.sayNext("Alright, I'll let you in! Defeat the monsters inside, collect 30 Dark Marbles, then talk to a colleague of mine inside. " +
                    "He'll give you #bThe Proof of a Hero#k, the proof that you've passed the test. Best of luck to you.");

            // Send the player to the test map
            sm.warp(910140000);
        } else if (sm.hasQuestStarted(100006)) {
            sm.sayNext("Hmmm... it is definitely the letter from #bGrendel the Really Old#k... " +
                    "so you came all the way here to take the test and make the 2nd job advancement as a magician. " +
                    "Alright, I'll explain the test to you. Don't sweat it too much, it's not that complicated.");

            sm.sayNext("I'll send you to a hidden map. You'll see monsters you don't normally see. " +
                    "They look similar to the regular ones but with a totally different attitude. " +
                    "They neither boost your experience level nor provide you with items.");

            sm.sayNext("You'll be able to acquire a marble called #b#t4031013##k while knocking down those monsters. " +
                    "It is a special marble made out of their sinister, evil minds. Collect 30 of those, " +
                    "and then go talk to a colleague of mine in there. That's how you pass the test.");

            if (sm.askYesNo("Once you go inside, you can't leave until you complete your mission. " +
                    "If you die, your experience level will decrease. So you better really buckle up and get ready... " +
                    "Well, do you want to go for it now?")) {
                sm.sayNext("Alright, I'll let you in! Defeat the monsters inside, collect 30 Dark Marbles, " +
                        "then talk to a colleague of mine inside. He'll give you #bThe Proof of a Hero#k, " +
                        "the proof that you've passed the test. Best of luck to you.");
                sm.forceCompleteQuest(100006);
                sm.forceStartQuest(100007);
                sm.removeItem(4031009, 1);
                sm.warp(910140000);
            } else {
                sm.sayOk("Come back when you are ready.");
            }
        } else {
            sm.sayOk("I can show you the way once you're ready for it.");
        }
    }

    @Script("magician")
    public static void magician(ScriptManager sm) {
        // Grendel the Really Old : Magician Job Advancement
        if(sm.getUser().getJob() == Job.BEGINNER.getJobId()) {
            sm.sayNext("Want to be a #rmagician#k? There are some standards to meet. because we can't just accept EVERYONE in... #bYour level should be at least 8, with getting 20 INT#k as your top priority. Let's see...");
            if(sm.getUser().getLevel() >= 8) {
                sm.sayBoth("Oh...! You look like someone that can definitely be a part of us... all you need is a little sinister mind, and... yeah... so, what do you think? Wanna be the Magician?");
                if (!sm.addItem(1372043, 1)) {
                    sm.sayOk("Make some room in your inventory and talk back to me.");
                    return;
                }
                sm.addInventorySlots(InventoryType.EQUIP, 4);
                sm.addInventorySlots(InventoryType.ETC, 4);
                sm.setJob(Job.MAGICIAN);
                sm.sayNext("Alright, from here out, you are a part of us! You'll be living the life of a wanderer at ..., but just be patient as soon, you'll be living the high life. Alright, it ain't much, but I'll give you some of my abilities... HAAAHHH!!!");
                sm.sayBoth("You've gotten much stronger now. Plus every single one of your inventories have added slots. A whole row, to be exact. Go see for it yourself. I just gave you a little bit of #bSP#k. When you open up the #bSkill#k menu on the lower left corner of the screen, there are skills you can learn by using SP's. One warning, though: You can't raise it all together all at once. There are also skills you can acquire only after having learned a couple of skills first.");
                sm.sayBoth("But remember, skills aren't everything. Your stats should support your skills as a Magician, also. Magicians use INT as their main stat, and LUK as their secondary stat. If raising stats is difficult, just use #bAuto-Assign#k");
                sm.sayBoth("Now, one more word of warning to you. If you fail in battle from this point on, you will lose a portion of your total EXP. Be extra mindful of this, since you have less HP than most.");
                sm.sayBoth("This is all I can teach you. Good luck on your journey, young Magician.");
            } else {
                sm.sayOk("Train a bit more until you reach the base requirements and I can show you the way of the #rMagician#k.");
            }
        } else if (sm.getUser().getJob() == Job.MAGICIAN.getJobId() && sm.getLevel() >= 30) {
            if (sm.hasItem(4031012)) { // Player has Proof of a Hero
                sm.sayNext("I see you have done well. I will allow you to take the next step on your long road.");

                final int choice = sm.askMenu("Now... have you made up your mind? Please choose the job you'd like for your 2nd job advancement:", Map.of(
                        0, "Wizard (Fire / Poison)",
                        1, "Wizard (Ice / Lightning)",
                        2, "Cleric"
                ));

                Job newJob;
                if (choice == 0) {
                    newJob = Job.WIZARD_FP;
                    sm.sayNext("Magicians that master #rFire/Poison-based magic#k.\r\n\r\n" +
                            "#bWizards#k are an active class that deal magical, elemental damage. " +
                            "With skills like #rMeditation#k and #rSlow#k, #bWizards#k can increase magic attack and reduce enemy mobility. " +
                            "Fire/Poison Wizards use powerful flame and poison attacks.");
                } else if (choice == 1) {
                    newJob = Job.WIZARD_IL;
                    sm.sayNext("Magicians that master #rIce/Lightning-based magic#k.\r\n\r\n" +
                            "#bWizards#k are an active class that deal magical, elemental damage. " +
                            "With skills like #rMeditation#k and #rSlow#k, #bWizards#k can increase magic attack and reduce enemy mobility. " +
                            "Ice/Lightning Wizards use freezing ice and striking lightning attacks.");
                } else {
                    newJob = Job.CLERIC;
                    sm.sayNext("Magicians that master #rHoly magic#k.\r\n\r\n" +
                            "#bClerics#k are a powerful supportive class, welcomed in any party. " +
                            "They have the power to #rHeal#k themselves and their allies, and can buff stats with #rBless#k. " +
                            "Clerics are particularly effective against undead monsters.");
                }

                final boolean confirm = sm.askYesNo("So you want to make the second job advancement as a " +
                        (newJob == Job.WIZARD_FP ? "#bWizard (Fire / Poison)#k?" :
                                newJob == Job.WIZARD_IL ? "#bWizard (Ice / Lightning)#k?" : "#bCleric#k?") +
                        " You know you won’t be able to choose a different job after this, right?");

                if (confirm) {
                    sm.removeItem(4031012, 1); // Remove Proof of a Hero
                    sm.setJob(newJob);
                    sm.sayNext("Alright, you're now a " +
                            (newJob == Job.WIZARD_FP ? "#bWizard (Fire / Poison)#k!" :
                                    newJob == Job.WIZARD_IL ? "#bWizard (Ice / Lightning)#k!" : "#bCleric#k!") +
                            " Magicians and wizards have incredible magical prowess, able to pierce the minds of monsters with ease... Train yourself daily.");
                    sm.sayBoth("I have given you a book listing the skills you can acquire as a " +
                            (newJob == Job.WIZARD_FP ? "#bWizard (Fire / Poison)#k." :
                                    newJob == Job.WIZARD_IL ? "#bWizard (Ice / Lightning)#k." : "#bCleric#k.") +
                            " Also, your inventory has expanded and your max HP/MP have increased. Check them out.");
                    sm.sayBoth("I have also given you a little bit of #bSP#k. Open the #bSkill Menu#k and start boosting your new 2nd-level skills.");
                    sm.sayBoth("This is all I can teach you. Use your new powers wisely, and continue training. Good luck!");
                } else {
                    sm.sayOk("Take your time and come back when you're ready.");
                }
            } else {
                if (sm.hasItem(4031009)) {
                    sm.sayOk("Go and see the #b#p1072001##k at #b#m101020000##k near Ellinia.");
                } else {
                    sm.sayNext("Good decision. You look strong, but I need to test you first. Take my letter and bring it to the instructor near Ellinia.");
                    if (!sm.hasQuestStarted(100006)) {
                        sm.forceStartQuest(100006);
                    }
                    if (!sm.addItem(4031009, 1)) {
                        sm.sayOk("Make some space in your inventory and talk back to me.");
                    } else {
                        sm.sayNext("Take this letter to #b#p1072001##k near Ellinia. He will test you in my place. Best of luck.");
                    }
                }
            }
        } else if(sm.getLevel() >= 70) {
            final int jobId = sm.getUser().getJob();
            switch (jobId) {
                // go to priest
                case CLERIC -> {
                    log.debug("hey");
                }
                // go to arch_mage_fp
                case WIZARD_FP -> {

                }
                // go to arch_mage_il
                case WIZARD_IL -> {

                }
            }
        }
    }

    @Script("thief3")
    public static void thief3(ScriptManager sm) {
        boolean qualifyForJob3 = sm.getJob() == Job.ASSASSIN || sm.getJob() == Job.BANDIT || sm.getJob() == Job.BLADE_SPECIALIST;
        if(!qualifyForJob3) {
            sm.sayOk("May the gods be with you!");
            return;
        }
        if(sm.getLevel() >= 70) {
            if(sm.getUser().getCharacterStat().getSp().getNonExtendSp() > (sm.getLevel() - 70) * 3) {
                sm.sayOk("Hmm...You have too many #bSP#k. You can't make the job advancement with too many SP left.");
                return;
            }

            sm.sayNext("You are indeed a strong one.");
            if(!(sm.getUser().getCharacterStat().getSp().getNonExtendSp() > (sm.getLevel() - 70) * 3)) {
                if (sm.getJob() == Job.BANDIT) {
                    sm.setJob(Job.CHIEF_BANDIT);
                    sm.sayOk("You are now a #bChief Bandit#k.");
                } else if (sm.getJob() == Job.ASSASSIN) {
                    sm.setJob(Job.HERMIT);
                    sm.sayOk("You are now an #bHermit#k.");
                } else if(sm.getJob() == Job.BLADE_SPECIALIST) {
                    sm.setJob(Job.BLADE_LORD);
                    sm.sayOk("You are not a #bBlade Lord#k.");
                }
            }
        }
    }

    @Script("warrior3")
    public static void warrior3(ScriptManager sm) {
        boolean qualifyForJob3 = sm.getJob() == Job.FIGHTER || sm.getJob() == Job.PAGE || sm.getJob() == Job.SPEARMAN;
        if(!qualifyForJob3) {
            sm.sayOk("May the gods be with you!");
            return;
        }
        if(sm.getLevel() >= 70) {
            if(sm.getUser().getCharacterStat().getSp().getNonExtendSp() > (sm.getLevel() - 70) * 3) {
                sm.sayOk("Hmm...You have too many #bSP#k. You can't make the job advancement with too many SP left.");
                return;
            }

            sm.sayNext("You are indeed a strong one.");
            if(!(sm.getUser().getCharacterStat().getSp().getNonExtendSp() > (sm.getLevel() - 70) * 3)) {
                if (sm.getJob() == Job.FIGHTER) {
                    sm.setJob(Job.CRUSADER);
                    sm.sayOk("You are now a #bCrusader#k.");
                } else if (sm.getJob() == Job.PAGE) {
                    sm.setJob(Job.WHITE_KNIGHT);
                    sm.sayOk("You are now a #bWhite Knight#k.");
                } else if (sm.getJob() == Job.SPEARMAN) {
                    sm.setJob(Job.DRAGON_KNIGHT);
                    sm.sayOk("You are now an #bDragon Knight#k.");
                }
            }
        }
    }

    @Script("bowman3")
    public static void bowman3(ScriptManager sm) {
        boolean qualifyForJob3 = sm.getJob() == Job.HUNTER || sm.getJob() == Job.CROSSBOWMAN;
        if(!qualifyForJob3) {
            sm.sayOk("May the gods be with you!");
            return;
        }
        if(sm.getLevel() >= 70) {
            if(sm.getUser().getCharacterStat().getSp().getNonExtendSp() > (sm.getLevel() - 70) * 3) {
                sm.sayOk("Hmm...You have too many #bSP#k. You can't make the job advancement with too many SP left.");
                return;
            }

            sm.sayNext("You are indeed a strong one.");
            if(!(sm.getUser().getCharacterStat().getSp().getNonExtendSp() > (sm.getLevel() - 70) * 3)) {
                if (sm.getJob() == Job.HUNTER) {
                    sm.setJob(Job.RANGER);
                    sm.sayOk("You are now a #bRanger#k.");
                } else if (sm.getJob() == Job.CROSSBOWMAN) {
                    sm.setJob(Job.SNIPER);
                    sm.sayOk("You are now a #bSniper#k.");
                }
            }
        }
    }

    @Script("wizard3")
    public static void wizard3(ScriptManager sm) {
        boolean qualifyForJob3 = sm.getJob() == Job.WIZARD_FP || sm.getJob() == Job.WIZARD_IL || sm.getJob() == Job.CLERIC;
        if(!(qualifyForJob3)) {
            sm.sayOk("May the Gods be with you!");
            return;
        }
        if(sm.getLevel() >= 70) {
           if(sm.getUser().getCharacterStat().getSp().getNonExtendSp() > (sm.getLevel() - 70) * 3) {
               // add check if totalSP > lvl * 3
               sm.sayOk("Hmm...You have too many #bSP#k. You can't make the job advancement with too many SP left.");
               return;
           }
            sm.sayNext("You are indeed a strong one.");
            if(!(sm.getUser().getCharacterStat().getSp().getNonExtendSp() > (sm.getLevel() - 70) * 3)) {
                if (sm.getJob() == Job.WIZARD_FP) {
                    sm.setJob(Job.MAGE_FP);
                    sm.sayOk("You are now a #bFire/Poison Mage.#k");
                } else if (sm.getJob() == Job.WIZARD_IL) {
                    sm.setJob(Job.MAGE_IL);
                    sm.sayOk("You are now an #bIce/Lightning Mage#k.");
                } else if (sm.getJob() == Job.CLERIC) {
                    sm.setJob(Job.PRIEST);
                    sm.sayOk("You are now a #bPriest#k.");
                }
            }
        } else {
            sm.sayOk("Please make sure that you are eligible for the job advancement. (level 70+)");
        }
    }

    @Script("q2230e")
    public static void q2230e(ScriptManager sm) {
        // Mar the fairy
        // A Mysterious Small Egg - End
        if(sm.askMenu("Hello, traveler... You have finally come to see me. Have you fulfilled your duties?", Map.of(0, "What duties? Who are you?")) == 0) {
            sm.sayNext("Have you found a small egg in your pocket? That egg is your duty, your responsibility. Life is hard when you're all by yourself. In times like this, there's nothing quite like having a friend that will be there for you at all times. Have you heard of a #bpet#k?\r\nPeople raise pets to ease the burden, sorrow, and loneliness, because knowing that you have someone, or something in this matter, on your side will really bring a peace of mind. But everything has consequences, and with it comes responsibility...");
            sm.sayBoth("Raising a pet requires a huge amount of responsibility. Remember a pet is a form of life, as well, so you'll need to feed it, name it, share your thoughts with it, and ultimately form a bond. That's how the owners get attached to these pets.");
            sm.sayBoth("I wanted to instill this in you, and that's why I sent you a baby that I cherish. The egg you have brought is #bRune Snail#k, a creature that is born through the power of Mana. Since you took great care of it as you brought the egg here, the egg will hatch soon.");
            sm.sayBoth("Rune Snail is a pet of many skills. It'll pick up items, feed you with potions, and do other things that will astound you. The downside is that since Rune Snail was born out of power of Mana, it's lifespan is very short. Once it turns into a doll, it'll never be able to be revived.");
            if(sm.askYesNo("Now do you understand? Every action comes with consequences, and pets are no exception. The egg of the snail shall hatch soon.")) {
                if(!sm.canAddItem(5000054, 1)) {
                    sm.sayOk("Please free a slot in your CASH inventory before you try to receive the pet...");
                    return;
                }
                sm.sayNext("This snail will only be alive for #b5 hours#k. Shower it with love. Your love will be reciprocated in the end.");
                sm.removeItem(4032086);
                sm.forceCompleteQuest(2230);
                sm.addItem(5000054, 1, 5);
            }
        }
    }

    public static boolean check2ndJobAdvancement(ScriptManager sm) {
        boolean brawler = sm.hasQuestStarted(2191);
        boolean gunslinger = sm.hasQuestStarted(2192);

        if (brawler || gunslinger) {
            int item, destination;
            String adj, job, skill;

            if (brawler) {
                item = 4031856;
                adj = "strong";
                job = "Brawler";
                skill = "Flash Fist";
                destination = 108000500;
            } else {
                item = 4031857;
                adj = "quick";
                job = "Gunslinger";
                skill = "Double Shot";
                destination = 108000502;
            }

            sm.sayNext("Okay, now I'll take you to the test room. Here are the instructions: defeat the Octopirates and gather #b15 " + itemName(item) + "#k. The Octopirates you'll see here are highly trained and are very " + adj + ", so I suggest you really buckle down and get ready for this.");
            sm.sayNext("Oh, and for the sake of training " + job + "s, those Octos will not be affected unless hit with " + blue(skill) + ". And one more thing, when you enter the test room, I'll remove all the " + itemName(item) + "s you have. Yes, you'll be starting off from scratch.");
            sm.removeItem(item, 1);
            sm.warpInstance(destination, "sp", 120000101, 60 * 10);
            return true;
        } else if(sm.hasQuestCompleted(2191)) {
            sm.sayOk("Okay, as promised, you will now become a " + blue("Brawler"));
            sm.setJob(Job.BRAWLER);
            sm.addInventorySlots(InventoryType.CONSUME, 4);
            sm.sayNext("Okay, from here on out, you are a #bBrawler#k. Brawlers rule the world with the power of their bare fists... which means they need to train their body more than others. If you have any trouble training, I'll be more than happy to help.");
            sm.sayBoth("I have just given you a skill book that entails Brawler skills, you'll find it very helpful. You have also gained additional slots for Use items, a full row in fact. I also boosted your MaxHP and MaxMP. Check it out for yourself.");
            sm.sayBoth("Brawlers need to be a powerful force, but that doesn't mean they have the right to bully the weak. True Brawlers use their immense power in positive ways, which is much harder than just training to gain strength. I hope you follow this creed as you leave your mark in this world as a Brawler. I will see you when you have accomplished everything you can as a Brawler. I'll be waiting for you here.");
            sm.sayBoth("I have given you a little bit of #bSP#k, so I suggest you open the #bskill menu#k right now. You'll be able to enhance your newly-acquired 2nd Job skills. Beware that not all skills can be enhanced from the get go. There are some skills that you can only acquire after mastering basic skills.");
            return true;
        } else if(sm.hasQuestCompleted(2192)) {
            sm.sayOk("Okay, as promised, you will now become a " + blue("Gunslinger"));
            sm.setJob(Job.GUNSLINGER);
            sm.addInventorySlots(InventoryType.CONSUME, 4);
            sm.sayNext("From here on out, you are a #bGunslinger#k. Gunslingers are notable for their long-range attacks with sniper-like accuracy and, of course, using Guns as their primary weapon. You should continue training to truly master your skills. If you are having trouble, I'll be there to help.");
            sm.sayBoth("I have just given you a skill book that entails Gunslinger skills, you'll find it very helpful. You have also gained additional slots for Use items, a full row in fact. I also boosted your MaxHP and MaxMP. Check it out for yourself.");
            sm.sayBoth("Gunslingers are deadly at ranged combat, but that doesn't mean they have the right to bully the weak. Gunslingers will need to use their immense power in positive ways, which is actually hard than just training to gain strength. I hope you follow this creed as you leave your mark in this world as a Gunslinger. I will see you when you have accomplished everything you can as a Gunslinger. I'll be waiting for you here.");
            sm.sayBoth("I have given you a little bit of #bSP#k, so I suggest you open the #bskill menu#k right now. You'll be able to enhance your newly-acquired 2nd Job skills. Beware that not all skills can be enhanced from the get go. There are some skills that you can only acquire after mastering basic skills.");
            return true;
        }
        return false;
    }

    @Script("kairinT")
    public static void kairinT(ScriptManager sm) {
        if (sm.hasQuestStarted(7500)) {
            sm.forceCompleteQuest(7500);
            sm.forceStartQuest(7501);
            sm.sayNext("I've been waiting for you ever since I heard your name from #bPedro#k of Ossyria. Now, I need to test your strength. You will find a Door of Dimension deep inside the Cursed Temple in the heart of Victoria Island. Most people can't enter, but I'll let you. Once inside, you'll face my clone. Your task is to defeat my evil twin and bring me the " + blue(itemName(4031059)) + ".");
            sm.sayBoth("Since it's my shadow, it'll be unlike any opponent you've ever encountered. It'll use various skills against you, and not only that, but since you're fighting in another dimension, it's probably not a good idea to stay there too long. I highly advise you to defeat the shadow as quickly as possible and leave. Remember, you'll need to fully prepare yourself for the battle. Otherwise, you'll have no chance. I'll be waiting here for you... Good luck!");
        } else if (sm.hasQuestStarted(7501)) {
            if (!sm.removeItem(4031059, 1)) {
                sm.sayOk("You will find a Door of Dimension deep inside the Cursed Temple in the heart of Victoria Island. Nobody but you can go into that passage. If you go into the passage, you will meet my clone. Beat him and bring " + blue(itemName(4031059)) + " to me.");
                return;
            }

            sm.addItem(4031057, 1);
            sm.forceCompleteQuest(7501);
            sm.forceStartQuest(7502);
            sm.sayOk("I can't believe this... You were able to defeat the shadow and brought back #b#t4031059##k...? Wow, you've definitely proven your strength. I think you are ready to make the 3rd job advancement. As promised, I'll give you " + blue(itemName(4031057)) + " for your work. Take this necklace to #bPedro#k in Ossyria to take the second test. I'll be praying for you to make the final leap to the 3rd job advancement.");
        } else if (sm.getJob().getJobId() != 500 || sm.getLevel() < 30 || !check2ndJobAdvancement(sm)) {
            final int selection = sm.askMenu("Have you got something to say?", Map.of(
                    0, "I would like to learn more about pirates."
            ));

            if (selection == 0) {
                if (sm.getJob() != Job.BEGINNER) {
                    sm.sayOk("Don't you want to feel the freedom emanating from the sea? Dont you want the power, the fame, and everything else that comes with it? Then you should join us and enjoy it yourself.");
                    return;
                }

                sm.sayNext("Do you wish to become a Pirate? You'll need to meet our set of standard if you are to become one of us. I need you to be #bat least at Level 10#k. Let's see...");
                if (sm.getLevel() < 10) {
                    sm.sayOk("Hmm...I don't think you have trained enough, yet. See me when you get stronger.");
                    return;
                }

                if(!sm.askYesNo("You seem more than qualified! Great, are you ready to become one of us?")) {
                    sm.sayOk("I see... Well, selecting a new job is a very important decision to make. If you are ready, then let me know!");
                    return;
                }

                sm.sayNext("Welcome to the band of Pirates! You may have to spend some time as a wanderer at first, but better days will certainly dawn upon you, sooner than you think! In the mean time, let me share some of my abilities with you.");

                List<Tuple<Integer, Integer>> pirateItems = List.of(
                        Tuple.of(1482014, 1),
                        Tuple.of(1492014, 1),
                        Tuple.of(2330006, 600),
                        Tuple.of(2330006, 600),
                        Tuple.of(2330006, 600)
                );
                if (!sm.canAddItems(pirateItems)) {
                    sm.sayOk("Make sure you have enough space in your EQP and USE inventories.");
                    return;
                }

                sm.setJob(Job.PIRATE);
                sm.addItems(pirateItems);
                sm.addInventorySlots(InventoryType.EQUIP, 4);
                sm.addInventorySlots(InventoryType.ETC, 4);

                sm.sayNext("I have just increased the number of slots for your equipment and etc. inventory. You have also gotten a bit stronger. Can you feel it? Now that you can officially call yourself a Pirate, join us in our quest for adventure and freedom!");
                sm.sayBoth("I have just given you a little bit of #bSP#k. Look at the #bSkill menu#k to find some skills, and use your SP to learn the skills. Beware that not all skills can be enhanced from the get go. There are some skills that you can only acquire after mastering basic skills.");
                sm.sayBoth("One more thing. Now that you have graduated from the ranks of a Beginner into a Pirate, you'll have to make sure not to die prematurely. If you do lose all your health, you'll lose valuable EXP that you have earned. Wouldn't it stink to lose hard-earned EXP by dying?");
                sm.sayBoth("This is all I can teach you. I have also given you some useful weapons to work with, so it's up to you now to train with them. The world is yours for the taking, so use your resources wisely, and when you feel like you've reached the top, let me know. I'll have something better for you in store...");
                sm.sayBoth("Oh, and... your stats should accurately reflect your new occupation as a Pirate. Click on #bAuto Assign#k on your stat window to make yourself into an even more formidable pirate.");
            }
        }
    }

    @Script("q3108s")
    public static void q3108s(ScriptManager sm) {
        sm.sayOk("Ah, a clue! Let's go back to Scadur.");
        sm.forceCompleteQuest(3108);
    }

    private static void notReady4thJob(ScriptManager sm) {
        sm.sayOk("You don't have to hesitate.... Whenever you decide, talk to me. If you're ready, I'll let you make the 4th job advancement.");
        sm.dispose();
    }

    public static boolean validateBasicRequirements(ScriptManager sm, int[] validJobIds, String jobTypeName) {
        int jobId = sm.getJob().getJobId();

        // Check job class
        boolean validJob = false;
        for (int id : validJobIds) {
            if (jobId == id) {
                validJob = true;
                break;
            }
        }

        if (!validJob) {
            sm.sayOk("Why do you want to see me? There is nothing you want to ask me.");
            sm.dispose();
            return false;
        }

        // Check level
        if (sm.getLevel() < 120) {
            sm.sayOk("You're still weak to go to " + jobTypeName + " extreme road. If you get stronger, come back to me.");
            sm.dispose();
            return false;
        }

        return true;
    }

    public static boolean handleAdvancementDialog(ScriptManager sm, String jobTypeName, String targetJobName) {
        final int answer = sm.askMenu("You're qualified to be a true " + jobTypeName + ". \r\nDo you want job advancement?", Map.of(
                0, "I want to advance to " + targetJobName + ".",
                1, "Let me think for a while."
        ));

        if (answer == 1) {
            notReady4thJob(sm);
            return false;
        }

        return true;
    }

    @Script("thief4")
    public static void thief4(ScriptManager sm) {
        int jobId = sm.getJob().getJobId();
        int[] validJobIds = {411, 421, 433};

        // Basic requirements check
        if (!validateBasicRequirements(sm, validJobIds, "thief")) {
            return;
        }

        // Check if player is qualified for advancement
        boolean isQualified = sm.hasQuestCompleted(6934) || jobId == 433;
        if (!isQualified) {
            sm.sayOk("You're not ready to make 4th job advancement. When you're ready, talk to me.");
            sm.dispose();
            return;
        }

        // Special case for Blade Master requiring an item
        if (jobId == 433 && !sm.hasQuestCompleted(6934) && !sm.hasItem(4031348, 1)) {
            sm.sayOk("You need the Secret Scroll for 10 million meso.");
            sm.dispose();
            return;
        }

        // Job advancement dialog
        String jobName = "";
        if (jobId == 411) {
            jobName = "Night Lord";
        } else if (jobId == 421) {
            jobName = "Shadower";
        } else { // jobId == 433
            jobName = "Blade Master";
        }

        if (!handleAdvancementDialog(sm, "thief", jobName)) {
            return;
        }

        // TODO: Fix logic here
        // SP check logic commented out in original

        // Set job and show confirmation
        if (jobId == 411) {
            sm.setJob(Job.NIGHT_LORD);
        } else if (jobId == 421) {
            sm.setJob(Job.SHADOWER);
        } else { // jobId == 433
            if (!sm.hasQuestCompleted(6934)) {
                sm.removeItem(4031348, 1);
            }
            sm.setJob(Job.BLADE_MASTER);
        }

        sm.sayNext("You became the best thief #b" + jobName + "#k.");
        sm.sayOk("Don't forget that it all depends on how much you train.");
        sm.dispose();
    }

    @Script("magician4")
    public static void magician4(ScriptManager sm) {
        int jobId = sm.getJob().getJobId();
        int[] validJobIds = {211, 221, 231};

        // Basic requirements check
        if (!validateBasicRequirements(sm, validJobIds, "magician")) {
            return;
        }

        if (sm.getLevel() < 120) {
            sm.sayOk("You're still weak to go to magician extreme road. If you get stronger, come back to me.");
            sm.dispose();
            return;
        }

        // Check if player is qualified for advancement
        boolean isQualified = sm.hasQuestCompleted(6914);
        if (!isQualified) {
            sm.sayOk("You're not ready to make 4th job advancement. When you're ready, talk to me.");
            sm.dispose();
            return;
        }

        // Job advancement dialog
        String jobName = "";
        if (jobId == 211) {
            jobName = "Arch Mage (F/P)";
        } else if (jobId == 221) {
            jobName = "Arch Mage (I/L)";
        } else { // jobId == 231
            jobName = "Bishop";
        }

        if (!handleAdvancementDialog(sm, "magician", jobName)) {
            return;
        }

        // TODO: Fix logic here
        // SP check logic commented out in original

        // Set job and show confirmation
        if (jobId == 211) {
            sm.setJob(Job.ARCH_MAGE_FP);
        } else if (jobId == 221) {
            sm.setJob(Job.ARCH_MAGE_IL);
        } else { // jobId == 231
            sm.setJob(Job.BISHOP);
        }

        // Base job type for display message (without F/P or I/L specifics)
        String baseJobName = jobId == 231 ? "Bishop" : "Arch Mage";

        sm.sayNext("You became the best magician #b" + jobName + "#k. " +
                baseJobName + " can use its own power as well as Mana of nature just like \n#bInfinity#k or #bBig Bang#k");

        if (jobId == 211) {
            sm.sayNext("This is not all about Arch Mage. Arch Mage is good at fire and poison element-based. It may change not only extreme element-based but also element-based of its own or enemies if you train.");
        } else if (jobId == 221) {
            sm.sayNext("This is not all about Arch Mage. Arch Mage is good at ice and lightning element-based. It may change not only extreme element-based but also element-based of its own or enemies if you train.");
        } else {
            sm.sayNext("This is not all about Bishop. Bishop can borrow God's power. It may make strong castle element-based magic and even make the dead alive.");
        }

        sm.sayOk("Don't forget that it all depends on how much you train.");
        sm.dispose();
    }

    @Script("warrior4")
    public static void warrior4(ScriptManager sm) {
        int jobId = sm.getJob().getJobId();
        int[] validJobIds = {111, 121, 131}; // Hero, Paladin, Dark Knight

        // Basic requirements check
        if (!validateBasicRequirements(sm, validJobIds, "warrior")) {
            return;
        }

        // Check if player is qualified for advancement
        boolean isQualified = sm.hasQuestCompleted(6904) || jobId == 2111;
        if (!isQualified) {
            sm.sayOk("You're not ready to make 4th job advancement. When you're ready, talk to me.");
            sm.dispose();
            return;
        }

        // Special case for Aran requiring an item
        if (jobId == 2111 && !sm.hasQuestCompleted(6904) && !sm.hasItem(4031348, 1)) {
            sm.sayOk("You need the Secret Scroll for 10 million meso.");
            sm.dispose();
            return;
        }

        // Determine job name
        String jobName;
        if (jobId == 111) {
            jobName = "Hero";
        } else if (jobId == 121) {
            jobName = "Paladin";
        } else if (jobId == 131) {
            jobName = "Dark Knight";
        } else { // 2111
            jobName = "Aran";
        }

        // Advancement dialog
        if (!handleAdvancementDialog(sm, "warrior", jobName)) {
            return;
        }

        // Set job
        if (jobId == 111) {
            sm.setJob(Job.HERO);
        } else if (jobId == 121) {
            sm.setJob(Job.PALADIN);
        } else if (jobId == 131) {
            sm.setJob(Job.DARK_KNIGHT);
        } else { // 2111
            sm.removeItem(4031348, 1);
            sm.setJob(Job.ARAN_4);
            if (sm.canAddItem(1142132, 1)) {
                sm.forceCompleteQuest(29927);
                sm.addItem(1142132, 1);
            }
        }

        // Class-specific dialog here
        sm.sayOk("You became the best warrior #b" + jobName + "#k.");
        sm.sayOk("Don't forget that it all depends on how much you train.");
        sm.dispose();
    }

    @Script("bowman4")
    public static void bowman4(ScriptManager sm) {
        int jobId = sm.getJob().getJobId();
        int[] validJobIds = {311, 321}; // Bowmaster, Marksman

        // Basic requirements check
        if (!validateBasicRequirements(sm, validJobIds, "bowman")) {
            return;
        }

        // Quest completion check - use appropriate quest ID
        if (!sm.hasQuestCompleted(6924)) { // Assuming quest ID for bowman
            sm.sayOk("You're not ready to make 4th job advancement. When you're ready, talk to me.");
            sm.dispose();
            return;
        }

        // Determine job name
        String jobName;
        if (jobId == 311) {
            jobName = "Bowmaster";
        } else { // jobId == 321
            jobName = "Marksman";
        }

        // Advancement dialog
        if (!handleAdvancementDialog(sm, "bowman", jobName)) {
            return;
        }

        // Set job
        if (jobId == 311) {
            sm.setJob(Job.BOWMASTER);
        } else { // jobId == 321
            sm.setJob(Job.MARKSMAN);
        }

        // Class-specific dialog here
        sm.sayOk("You became the best bowman #b" + jobName + "#k.");

        if (jobId == 312) {
            sm.sayNext("This is not all about Bow Master. Bow Master is good at a fast battle. It can attack enemies with enormously fast speed and even have great attack power.");
        } else {
            sm.sayNext("This is not all about Marksman. Each shot of a Marksman is very strong. It can attack many enemies with strong power and may beat off them at once.");
        }

        sm.sayOk("Don't forget that it all depends on how much you train.");
        sm.dispose();
    }

    @Script("pirate4")
    public static void pirate4(ScriptManager sm) {
        int jobId = sm.getJob().getJobId();
        int[] validJobIds = {511, 521}; // Buccaneer, Corsair

        // Basic requirements check
        if (!validateBasicRequirements(sm, validJobIds, "pirate")) {
            return;
        }

        // Quest completion check - use appropriate quest ID
        if (!sm.hasQuestCompleted(6944)) { // Assuming quest ID for pirate
            sm.sayOk("You're not ready to make 4th job advancement. When you're ready, talk to me.");
            sm.dispose();
            return;
        }

        // Determine job name
        String jobName;
        if (jobId == 511) {
            jobName = "Buccaneer";
        } else { // jobId == 521
            jobName = "Corsair";
        }

        // Advancement dialog
        if (!handleAdvancementDialog(sm, "pirate", jobName)) {
            return;
        }

        // Set job
        if (jobId == 511) {
            sm.setJob(Job.BUCCANEER);
        } else { // jobId == 521
            sm.setJob(Job.CORSAIR);
        }

        // Class-specific dialog here
        sm.sayOk("You became the best pirate #b" + jobName + "#k.");
        sm.sayOk("Don't forget that it all depends on how much you train.");
        sm.dispose();
    }
}
package mapleglory.script.quest;

import mapleglory.script.common.Script;
import mapleglory.script.common.ScriptHandler;
import mapleglory.script.common.ScriptManager;
import mapleglory.world.field.Field;
import mapleglory.world.field.mob.MobAppearType;
import mapleglory.world.quest.QuestRecordType;

import java.time.Instant;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class JobQuest extends ScriptHandler {
    final static int CROCELL = 9400611;
    final static int CROCELL_PRE_FIELD = 677000006;
    final static int CROCELL_FIELD = 677000007;
    final static int AMDUSIAS = 9400610;
    final static int AMDUSIAS_PRE_FIELD = 677000002;
    final static int AMDUSIAS_FIELD = 677000003;
    final static int VALEFOR_PRE_FIELD = 677000008;
    final static int VALEFOR_FIELD = 677000009;
    final static int VALEFOR = 9400613;
    final static int ANDRAS_PRE_FIELD = 677000004;
    final static int ANDRAS_FIELD = 677000005;
    final static int ANDRAS = 9400609;
    final static int MARBAS_PRE_FIELD = 677000000;
    final static int MARBAS_FIELD = 677000001;
    public static final int MARBAS = 9400612;
    final static int ASTAROTH_PRE_FIELD = 677000011;
    final static int ASTAROTH_FIELD = 677000012;
    final static int ASTAROTH = 9400633;


    @Script("Enter_Darkportal_P")
    public static void enter_darkportal_p(ScriptManager sm) {
        // Demon's Doorway
        // Victoria Road - The Forest East of Henesys
        if(!sm.hasQuestStarted(28256)) {
            sm.sayOk("Demon's Doorway is closed right now.");
            return;
        }
        final Field newField;
        final Optional<Field> tryField = sm.getField().getFieldStorage().getFieldById(CROCELL_FIELD);
        if (tryField.isPresent()) {
            newField = tryField.get();
            if (newField.getUserPool().getCount() > 0) {
                sm.sayNext("Someone is already in that map.");
                return;
            }
            sm.sayNext("You are permitted to enter the Demon's Doorway.");
            newField.getMobPool().forEach((mob) -> {
                try (var lockedMob = mob.acquire()) {
                    mob.remove(Instant.now());
                }
            });
            newField.setMobSpawn(false);
            sm.warp(CROCELL_PRE_FIELD);
            sm.spawnMobInMap(CROCELL, MobAppearType.NORMAL, 342, 75, true, newField);
            sm.message("Kill Crocell!");
        }
    }

    @Script("Enter_Darkportal_M")
    public static void enter_darkportal_m(ScriptManager sm) {
        // Demon's Doorway
        if (!sm.hasQuestStarted(28198)) {
            sm.sayOk("Demon's Doorway is closed right now.");
            return;
        }
        final Field newField;
        final Optional<Field> tryField = sm.getField().getFieldStorage().getFieldById(MARBAS_FIELD);
        if (tryField.isPresent()) {
            newField = tryField.get();
            if (newField.getUserPool().getCount() > 0) {
                sm.sayNext("Someone is already in that map.");
                return;
            }
            sm.sayNext("You are permitted to enter the Demon's Doorway.");
            newField.getMobPool().forEach((mob) -> {
                try (var lockedMob = mob.acquire()) {
                    mob.remove(Instant.now());
                }
            });
            newField.setMobSpawn(false);
            sm.warp(MARBAS_PRE_FIELD);
            sm.spawnMobInMap(MARBAS, MobAppearType.NORMAL, 174, 70, true, newField);
            sm.message("Kill Marbas!");
        }
    }

    @Script("Enter_Darkportal_W")
    public static void enter_darkportal_w(ScriptManager sm) {
        // Demon's Doorway
        // North Rocky Mountain : Big Rocky Road
        if(!sm.hasQuestStarted(28179)) {
            sm.sayOk("Demon's Doorway is closed right now.");
            return;
        }
        final Field newField;
        final Optional<Field> tryField = sm.getField().getFieldStorage().getFieldById(ANDRAS_FIELD);
        if (tryField.isPresent()) {
            newField = tryField.get();
            if (newField.getUserPool().getCount() > 0) {
                sm.sayNext("Someone is already in that map.");
                return;
            }
            sm.sayNext("You are permitted to enter the Demon's Doorway.");
            newField.getMobPool().forEach((mob) -> {
                try (var lockedMob = mob.acquire()) {
                    mob.remove(Instant.now());
                }
            });
            newField.setMobSpawn(false);
            sm.warp(ANDRAS_PRE_FIELD);
            sm.spawnMobInMap(ANDRAS, MobAppearType.NORMAL, 294, 96, true, newField);
            sm.message("Kill Andras!");
        }
    }

    @Script("Enter_Darkportal_T")
    public static void enterDarkportalT(ScriptManager sm) {
        // Demon's Doorway
        // Swamp Region - Dangerous Croco
        if(!sm.hasQuestStarted(28219)) {
            sm.sayOk("Demon's Doorway is closed right now.");
            return;
        }
        final Field newField;
        final Optional<Field> tryField = sm.getField().getFieldStorage().getFieldById(VALEFOR_FIELD);
        if (tryField.isPresent()) {
            newField = tryField.get();
            if (newField.getUserPool().getCount() > 0) {
                sm.sayNext("Someone is already in that map.");
                return;
            }
            sm.sayNext("You are permitted to enter the Demon's Doorway.");
            newField.getMobPool().forEach((mob) -> {
                try (var lockedMob = mob.acquire()) {
                    mob.remove(Instant.now());
                }
            });
            newField.setMobSpawn(false);
            sm.warp(VALEFOR_PRE_FIELD);
            sm.spawnMobInMap(VALEFOR, MobAppearType.NORMAL, 359, 66, true, newField);
            sm.message("Kill Valefor!");
        }
    }

    @Script("Enter_Darkportal_H")
    // Demon's Doorway
    // Singing Mushroom Forest - Ghost Mushroom Forest
    public static void enter_darkportal_h(ScriptManager sm) {
        if(!sm.hasQuestStarted(28238)) {
            sm.sayOk("Demon's Doorway is closed right now.");
            return;
        }
        final Field newField;
        final Optional<Field> tryField = sm.getField().getFieldStorage().getFieldById(AMDUSIAS_FIELD);
        if (tryField.isPresent()) {
            newField = tryField.get();
            if (newField.getUserPool().getCount() > 0) {
                sm.sayNext("Someone is already in that map.");
                return;
            }
            sm.sayNext("You are permitted to enter the Demon's Doorway.");
            newField.getMobPool().forEach((mob) -> {
                try (var lockedMob = mob.acquire()) {
                    mob.remove(Instant.now());
                }
            });
            newField.setMobSpawn(false);
            sm.warp(AMDUSIAS_PRE_FIELD);
            sm.spawnMobInMap(AMDUSIAS, MobAppearType.NORMAL, 511, 35, true, newField);
            sm.message("Kill Amdusias!");
        }
    }

    @Script("Astaroth_door")
    public static void astarothDoor(ScriptManager sm) {
        final int mapId = sm.getFieldId();
        if (mapId == 105050400) {
            // Dark Cave
            if (!sm.getUser().isPartyLeader()) {
                sm.sayOk("If you'd like to enter here, the leader of your party will have to talk to me. Talk to your party leader about this.");
                return;
            }
            if (!sm.checkParty(3, 25)) {
                sm.sayOk("You cannot enter because your party doesn't have 3 members. You need 3 party members at Lv. 25 or higher to enter, so double-check and talk to me again.");
                return;
            }
            sm.partyWarp(ASTAROTH_PRE_FIELD, "sp");
        } else if (mapId == 677000012) {
            // Hiding Place
            if (!sm.getUser().isPartyLeader()) {
                sm.sayOk("If you'd like to exit from here, the leader of your party will have to talk to me. Talk to your party leader about this.");
                return;
            }
            sm.partyWarp(ASTAROTH_PRE_FIELD, "sp");
        } else if (mapId == ASTAROTH_PRE_FIELD) {
            final Field newField;
            final Optional<Field> tryField = sm.getField().getFieldStorage().getFieldById(ASTAROTH_FIELD);
            if (tryField.isPresent()) {
                newField = tryField.get();
                if (newField.getUserPool().getCount() > 0) {
                    sm.sayNext("Someone is already in that map.");
                    return;
                }
                newField.getMobPool().forEach((mob) -> {
                    try (var lockedMob = mob.acquire()) {
                        mob.remove(Instant.now());
                    }
                });
                newField.setMobSpawn(false);
                sm.partyWarpInstance(ASTAROTH_FIELD, "sp", ASTAROTH_PRE_FIELD, 30 * 60);
                sm.spawnMobInMap(ASTAROTH, MobAppearType.NORMAL, 565, 45, true, newField);
            }
        }
    }

    @Script("dual_wallpaper")
    public static void dual_wallpaper(ScriptManager sm) {
        if(sm.hasQuestStarted(2358)) {
            final int mapId = sm.getFieldId();
            String adding = getLocationKey(mapId);

            if(sm.askYesNo("There is an empty space here for you to put up the poster. Do you wish to attach the poster here?")) {
                Set<Character> posterSet = getQuestProgress(sm.getQRValue(QuestRecordType.DualBladeDualWallpaper));
                posterSet.add(adding.charAt(0));
                if (posterSet.contains('1') && posterSet.contains('2') && posterSet.contains('3')) {
                    sm.setQRValue(QuestRecordType.DualBladeDualWallpaper, "211"); // Mark quest as complete
                } else {
                    sm.setQRValue(QuestRecordType.DualBladeDualWallpaper, setToString(posterSet)); // Update progress
                }

                sm.sayOk("The poster has been attached.");
            }
        }
    }

    @Script("dual_blueAlcohol")
    public static void dualBlueAlcohol(ScriptManager sm) {
        final int mapId = sm.getFieldId();
        String adding = getLocationKey(mapId);

        if(sm.hasQuestStarted(2358)) {
            if(sm.askYesNo("It's a half-filled blue bottle... Do you wish to install the bomb?")) {
                Set<Character> posterSet = getQuestProgress(sm.getQRValue(QuestRecordType.DualBladeDualWallpaper));
                posterSet.add(adding.charAt(0));
                if (posterSet.contains('1') && posterSet.contains('2') && posterSet.contains('3')) {
                    sm.setQRValue(QuestRecordType.DualBladeDualWallpaper, "211"); // Mark quest as complete
                } else {
                    sm.setQRValue(QuestRecordType.DualBladeDualWallpaper, setToString(posterSet)); // Update progress
                }

                sm.sayOk("The bomb has been installed.");
            }
        }
    }

    private static String setToString(Set<Character> posterSet) {
        StringBuilder sb = new StringBuilder();
        for (char c : posterSet) {
            sb.append(c);
        }
        return sb.toString();
    }

    private static String getLocationKey(int mapId) {
        return switch (mapId) {
            case 103010100 -> "2";
            case 103000003 -> "3";
            default -> "1";
        };
    }

    private static Set<Character> getQuestProgress(String questData) {
        Set<Character> posterSet = new HashSet<>();
        if (questData != null) {
            for (char c : questData.toCharArray()) {
                posterSet.add(c);
            }
        }
        return posterSet;
    }
}

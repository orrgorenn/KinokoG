package mapleglory.script.quest;

import mapleglory.script.common.Script;
import mapleglory.script.common.ScriptHandler;
import mapleglory.script.common.ScriptManager;
import mapleglory.world.field.Field;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Optional;

public final class NeoCity extends ScriptHandler {
    @Script("TD_NC_title")
    public static void TD_NC_title(ScriptManager sm) {
        // Tera Forest   : Tera Forest Time Gate (240070000)
        // Neo City : <Year 2021> Average Town Entrance (240070100)
        // Neo City : <Year 2099> Midnight Harbor Entrance (240070200)
        // Neo City : <Year 2215> Bombed City Center Retail District (240070300)
        // Neo City : <Year 2216> Ruined City Intersection (240070400)
        // Neo City : <Year 2230> Dangerous Tower Lobby (240070500)
        // Neo City : <Year 2503> Air Battleship Bow (240070600)
        if (sm.getFieldId() == 240070000) {
            // Tera Forest : Tera Forest Time Gate
            sm.screenEffect("temaD/enter/teraForest");
        } else if (sm.getFieldId() == 240070100) {
            // Neo City : <Year 2012> Average Town Entrance
            sm.screenEffect("temaD/enter/neoCity1");
        } else if (sm.getFieldId() == 240070200) {
            // Neo City : <Year 2099> Midnight Harbor Entrance
            sm.screenEffect("temaD/enter/neoCity2");
        } else if (sm.getFieldId() == 240070300) {
            // Neo City : <Year 2215> Bombed City Center Retail District
            sm.screenEffect("temaD/enter/neoCity3");
        } else if (sm.getFieldId() == 240070400) {
            // Neo City : <Year 2216> Ruined City Intersection
            sm.screenEffect("temaD/enter/neoCity4");
        } else if (sm.getFieldId() == 240070500) {
            // Neo City : <Year 2230> Dangerous Tower Lobby
            sm.screenEffect("temaD/enter/neoCity5");
        } else if (sm.getFieldId() == 240070600) {
            // Neo City : <Year 2503> Air Battleship Bow
            sm.screenEffect("temaD/enter/neoCity6");
        }
    }

    @Script("TD_neoCity_enter")
    public static void TD_neoCity_enter(ScriptManager sm) {
        // Time Gate (2083006)
        //   Tera Forest   : Tera Forest Time Gate (240070000)
        final List<Integer> destinations = List.of(
                240070100, // Neo City : <Year 2012> Average Town Entrance
                240070200, // Neo City : <Year 2099> Midnight Harbor Entrance
                240070300, // Neo City : <Year 2215> Bombed City Center Retail District
                240070400, // Neo City : <Year 2216> Ruined City Intersection
                240070500, // Neo City : <Year 2230> Dangerous Tower Lobby
                240070600 // Neo City : <Year 2503> Air Battleship Bow
        );
        final Map<Integer, String> options = createOptions(destinations, ScriptHandler::mapName);
        final int answer = sm.askSlideMenu(1, options);
        if (answer >= 0 && answer < destinations.size()) {
            sm.warp(destinations.get(answer), "left00");
        }
    }

    @Script("TD_chat_enter")
    public static void TD_chat_enter(ScriptManager sm) {
        // Tera Forest   : Tera Forest Time Gate (240070000)
        //   TD_neo (491, 151)
        TD_neoCity_enter(sm);
    }

    @Script("TD_neo_inTree")
    public static void TD_neo_inTree(ScriptManager sm) {
        final List<Integer> destinations = List.of(
                240070010, // Tera Forest : Old Tree In Tera Forest [1]
                240070020, // Tera Forest : Old Tree In Tera Forest [2]
                240070030, // Tera Forest : Old Tree In Tera Forest [3]
                240070040, // Tera Forest : Old Tree In Tera Forest [4]
                240070050, // Tera Forest : Old Tree In Tera Forest [5]
                240070060 // Tera Forest : Old Tree In Tera Forest [6]
        );

        List<Field> maps = destinations.stream()
                .map(mapId -> sm.getField().getFieldStorage().getFieldById(mapId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        maps.sort((map1, map2) -> {
            if (map1.getUserPool().getCount() < 1 && map1.getMobPool().getCount() > 0) {
                return -1;
            }
            if (map2.getUserPool().getCount() < 1 && map2.getMobPool().getCount() > 0) {
                return 1;
            }
            if (map1.getUserPool().getCount() < 1) {
                return -1;
            }
            if (map2.getUserPool().getCount() < 1) {
                return 1;
            }
            if (map1.getMobPool().getCount() > 0) {
                return -1;
            }
            if (map2.getMobPool().getCount() > 0) {
                return 1;
            }
            return 0;
        });
        sm.playPortalSE();
        sm.warp(maps.getFirst().getFieldId(), "out00");
    }
}

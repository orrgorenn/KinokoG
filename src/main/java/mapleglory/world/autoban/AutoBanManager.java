package mapleglory.world.autoban;

import mapleglory.database.DatabaseManager;
import mapleglory.server.ServerConfig;
import mapleglory.world.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AutoBanManager {
    private static final Logger log = LoggerFactory.getLogger(AutoBanManager.class);
    private User user;
    private final Map<AutoBanFactory, Integer> points = new HashMap<>();
    private final Map<AutoBanFactory, Long> lastTime = new HashMap<>();
    private int misses = 0;
    private int lastMisses = 0;
    private int sameMissCount = 0;
    private final long[] spam = new long[20];
    private final long[] timestamp = new long[20];
    private final byte[] timestampCounter = new byte[20];

    public AutoBanManager(User user) {
        this.user = user;
    }

    public void addPoint(AutoBanFactory abFactory, String reason) {
        if (ServerConfig.USE_AUTOBAN) {
            if (user.getAccount().isGM() || user.isBanned()) {
                return;
            }

            if (lastTime.containsKey(abFactory)) {
                if (lastTime.get(abFactory) < (System.currentTimeMillis() - abFactory.getExpireTime())) {
                    points.put(abFactory, points.get(abFactory) / 2); //So the points are not completely gone.
                }
            }
            if (abFactory.getExpireTime() != -1) {
                lastTime.put(abFactory, System.currentTimeMillis());
            }

            if (points.containsKey(abFactory)) {
                points.put(abFactory, points.get(abFactory) + 1);
            } else {
                points.put(abFactory, 1);
            }

            if (points.get(abFactory) >= abFactory.getMaximum()) {
                user.autoban(reason);
            }
        }
        log.info("AutoBan - User {} caused {} {}", user, abFactory.name(), reason);
    }

    public void addMiss() {
        this.misses++;
    }

    public void resetMisses() {
        if (lastMisses == misses && misses > 6) {
            sameMissCount++;
        }

        if (sameMissCount > 4) {
            log.warn("sendPolice miss godmode.");
        } else if (sameMissCount > 0) {
            this.lastMisses = misses;
        }

        this.misses = 0;
    }

    public void spam(int type) {
        this.spam[type] = System.currentTimeMillis();
    }

    public void spam(int type, int timestamp) {
        this.spam[type] = timestamp;
    }

    public long getLastSpam(int type) {
        return spam[type];
    }

    public void setTimestamp(int type, long time, int times) {
        if (this.timestamp[type] == time) {
            this.timestampCounter[type]++;
            if (this.timestampCounter[type] >= times) {
                if (ServerConfig.USE_AUTOBAN) {
                    user.logout(true);
                    DatabaseManager.accountAccessor().setLoggedStatus(user.getAccount(), false);
                    DatabaseManager.activeMachineAccessor().removeInstance(user.getAccount().getId());
                    DatabaseManager.accountAccessor().saveAccount(user.getAccount());
                    DatabaseManager.characterAccessor().saveCharacter(user.getCharacterData());
                }

                log.info("AutoBan - User {} was caught spamming TYPE {} and has been disconnected", user, type);
            }
        } else {
            this.timestamp[type] = time;
            this.timestampCounter[type] = 0;
        }
    }
}

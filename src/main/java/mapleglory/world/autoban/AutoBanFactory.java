package mapleglory.world.autoban;

import mapleglory.server.ServerConfig;
import mapleglory.world.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

public enum AutoBanFactory {
    MOB_COUNT,
    GENERAL,
    FIX_DAMAGE,
    DAMAGE_HACK(15, MINUTES.toMillis(1)),
    DISTANCE_HACK(10, MINUTES.toMillis(2)),
    PORTAL_DISTANCE(5, SECONDS.toMillis(30)),
    PACKET_EDIT,
    ACC_HACK,
    CREATION_GENERATOR,
    HIGH_HP_HEALING,
    FAST_HP_HEALING(15),
    FAST_MP_HEALING(20, SECONDS.toMillis(30)),
    GACHA_EXP,
    TUBI(20, SECONDS.toMillis(15)),
    SHORT_ITEM_VAC,
    ITEM_VAC,
    FAST_ITEM_PICKUP(5, SECONDS.toMillis(30)),
    FAST_ATTACK(10, SECONDS.toMillis(30)),
    MPCON(25, SECONDS.toMillis(30));

    private static final Logger log = LogManager.getLogger(AutoBanFactory.class);
    private static final Set<Integer> ignoredUserIds = new HashSet<>();

    private final int points;
    private final long expireTime;

    AutoBanFactory() {
        this(1, -1);
    }

    AutoBanFactory(int points) {
        this.points = points;
        this.expireTime = -1;
    }

    AutoBanFactory(int points, long expire) {
        this.points = points;
        this.expireTime = expire;
    }

    public int getMaximum() {
        return points;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void addPoint(AutoBanManager banManager, String reason) {
        banManager.addPoint(this, reason);
    }

    public void alert(User user, String reason) {
        if (ServerConfig.USE_AUTOBAN) {
            if (user != null && isIgnored(user.getCharacterId())) {
                return;
            }
            // user.getClient().broadcastGMMessage((chr != null ? chr.getWorld() : 0), PacketCreator.sendYellowTip((chr != null ? Character.makeMapleReadable(chr.getName()) : "") + " caused " + this.name() + " " + reason));
        }
        log.warn("AutoBan Alert - User {} caused {}-{}", user, this.name(), reason);
    }

    public void autoban(User user, String value) {
        if (ServerConfig.USE_AUTOBAN) {
            user.autoban("AutoBanned for (" + this.name() + ": " + value + ")");
        }
    }

    // HELPER FUNCTIONS

    public static boolean toggleIgnored(int userId) {
        if (ignoredUserIds.contains(userId)) {
            ignoredUserIds.remove(userId);
            return false;
        } else {
            ignoredUserIds.add(userId);
            return true;
        }
    }

    private static boolean isIgnored(int userId) {
        return ignoredUserIds.contains(userId);
    }

    public static Collection<Integer> getIgnoredUserIds() {
        return ignoredUserIds;
    }
}

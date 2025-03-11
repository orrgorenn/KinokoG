package mapleglory.provider;

import mapleglory.provider.reward.Reward;
import mapleglory.server.ServerConfig;
import org.snakeyaml.engine.v2.api.Load;
import org.snakeyaml.engine.v2.api.LoadSettings;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public final class GachaponProvider {
    public static final Path GACHAPON_DATA = Path.of(ServerConfig.DATA_DIRECTORY, "gachapon");
    private static final Map<String, List<Reward>> gachaponRewards = new HashMap<>(); // gachaponName -> rewards

    public static void initialize() {
        final Load yamlLoader = new Load(LoadSettings.builder().build());
        try (final Stream<Path> paths = Files.list(GACHAPON_DATA)) {
            for (Path path : paths.toList()) {
                final String fileName = path.getFileName().toString();
                if (!fileName.endsWith(".yaml")) {
                    continue;
                }
                final String gachaponName = fileName.replace(".yaml", "");
                try (final InputStream is = Files.newInputStream(path)) {
                    loadGachaponRewards(gachaponName, yamlLoader.loadFromInputStream(is));
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Exception caught while loading Gachapon Data", e);
        }
    }

    public static List<Reward> getGachaponRewards(String gachaponName) {
        return gachaponRewards.getOrDefault(gachaponName, List.of());
    }

    private static void loadGachaponRewards(String gachaponName, Object yamlObject) {
        if (!(yamlObject instanceof Map<?, ?> rewardData)) {
            throw new IllegalArgumentException("Could not resolve reward data for Gachapon: " + gachaponName);
        }
        if (!(rewardData.get("rewards") instanceof List<?> rewardList)) {
            return;
        }
        final List<Reward> rewards = new ArrayList<>();
        for (Object rewardObject : rewardList) {
            if (!(rewardObject instanceof List<?> rewardInfo)) {
                throw new IllegalArgumentException("Invalid reward format for Gachapon: " + gachaponName);
            }
            final int itemId = ((Number) rewardInfo.get(0)).intValue();
            final int min = ((Number) rewardInfo.get(1)).intValue();
            final int max = ((Number) rewardInfo.get(2)).intValue();
            final double prob = ((Number) rewardInfo.get(3)).doubleValue();
            rewards.add(Reward.item(itemId, min, max, prob, 0));
        }
        gachaponRewards.put(gachaponName, Collections.unmodifiableList(rewards));
    }
}
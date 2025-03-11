package mapleglory.handler.stage;

import mapleglory.provider.GachaponProvider;
import mapleglory.provider.ItemProvider;
import mapleglory.provider.item.ItemInfo;
import mapleglory.provider.reward.Reward;
import mapleglory.util.Tuple;
import mapleglory.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public final class GachaponHandler {
    private static final Logger log = LogManager.getLogger(GachaponHandler.class);

    public static Tuple<Integer, Integer> rollGachapon(String gachaponName) {
        List<Reward> rewards = GachaponProvider.getGachaponRewards(gachaponName);
        if (rewards.isEmpty()) {
            throw new IllegalArgumentException("No rewards available for Gachapon: " + gachaponName);
        }
        for (Reward reward : rewards) {
            // Drop probability
            if (!Util.succeedDouble(reward.getProb())) {
                continue;
            }
            final Optional<ItemInfo> itemInfoResult = ItemProvider.getItemInfo(reward.getItemId());
            if (itemInfoResult.isEmpty()) {
                continue;
            }
            final int quantity = Util.getRandom(reward.getMin(), reward.getMax());
            return Tuple.of(reward.getItemId(), quantity);
        }
        Reward mostProbableReward = rewards.stream()
                .max(Comparator.comparingDouble(Reward::getProb))
                .orElse(rewards.getFirst());
        return Tuple.of(mostProbableReward.getItemId(), Util.getRandom(mostProbableReward.getMin(), mostProbableReward.getMax()));
    }
}
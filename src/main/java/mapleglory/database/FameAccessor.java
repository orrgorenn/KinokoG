package mapleglory.database;

import mapleglory.util.Tuple;

import java.time.Instant;
import java.util.List;

public interface FameAccessor {
    List<Tuple<Instant, Integer>> lastMonthFames(int characterId);
    boolean newFame(int characterId, int targetId);
}

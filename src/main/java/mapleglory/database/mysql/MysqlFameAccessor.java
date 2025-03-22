package mapleglory.database.mysql;

import mapleglory.database.DatabaseConnection;
import mapleglory.database.FameAccessor;
import mapleglory.database.table.FameTable;
import mapleglory.util.Tuple;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MysqlFameAccessor implements FameAccessor {
    @Override
    public List<Tuple<Instant, Integer>> lastMonthFames(int characterId) {
        List<Tuple<Instant, Integer>> fames = new ArrayList<>();
        String selectSQL = "SELECT " + FameTable.TARGET_ID + ", " + FameTable.DATE_GIVEN + " FROM " + FameTable.getTableName() +
                " WHERE " + FameTable.CHARACTER_ID + " = ? AND " + FameTable.DATE_GIVEN + " >= DATE_SUB(NOW(), INTERVAL 30 DAY)"
                + " ORDER BY " + FameTable.DATE_GIVEN + " DESC";;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(selectSQL)) {

            ps.setInt(1, characterId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp timestamp = rs.getTimestamp(FameTable.DATE_GIVEN);
                    if (timestamp != null) {
                        fames.add(Tuple.of(timestamp.toInstant(), rs.getInt(FameTable.TARGET_ID)));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fames;
    }

    @Override
    public boolean newFame(int characterId, int targetId) {
        String insertSQL = "INSERT INTO " + FameTable.getTableName() + " (" +
                FameTable.CHARACTER_ID + ", " +
                FameTable.TARGET_ID + ") " +
                "VALUES (?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(insertSQL)) {
            ps.setInt(1, characterId);
            ps.setInt(2, targetId);

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

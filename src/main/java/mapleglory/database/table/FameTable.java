package mapleglory.database.table;

import mapleglory.database.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class FameTable {
    private static final Logger log = LoggerFactory.getLogger(FameTable.class);
    public static final String FAME_ID = "id";
    public static final String CHARACTER_ID = "character_id";
    public static final String TARGET_ID = "target_id";
    public static final String DATE_GIVEN = "date_given";
    private static final String tableName = "fame";

    public static String getTableName() {
        return tableName;
    }

    public static void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + getTableName() + " (" +
                FAME_ID + " INT AUTO_INCREMENT, " +
                CHARACTER_ID + " INT, " +
                TARGET_ID + " INT, " +
                DATE_GIVEN + " TIMESTAMP DEFAULT NOW(), " +
                "PRIMARY KEY (" + FAME_ID + ")" +
                ")";

        String createIndexSQL = "CREATE INDEX idx_character_id ON " + getTableName() + " (" + CHARACTER_ID + ")";

        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(createTableSQL);

            String checkIndexSQL = "SELECT COUNT(1) FROM information_schema.statistics " +
                    "WHERE table_schema = DATABASE() AND table_name = ? AND index_name = 'idx_character_id'";

            try (PreparedStatement checkPs = con.prepareStatement(checkIndexSQL)) {
                checkPs.setString(1, getTableName());
                try (ResultSet rs = checkPs.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        stmt.execute(createIndexSQL);
                    }
                }
            }
        } catch (SQLException e) {
            log.error("Error creating table or index in MySQL: {}", e.getMessage());
        }
    }
}

package personalprojects.cardgames.dbutil;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DbUtil {

    private final static BasicDataSource ds = new BasicDataSource();
    private static Connection conn;

    private DbUtil() {}

    static {
        ds.setUrl("jdbc:mysql://localhost:3306/cardgamesdb");
        ds.setUsername("card-games-user");
        ds.setPassword("user_password");
        ds.setInitialSize(8);
        ds.setMaxTotal(32);
        ds.setMinIdle(8);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        conn = ds.getConnection();
        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

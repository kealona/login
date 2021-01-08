package Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.DriverManager.getConnection;

public class MySqlDriver {
    private static Connection DriverAndConnect() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = getConnection("jdbc:mysql://localhost:3306/chat_project", "root", "123456");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Statement GetStatement() throws SQLException {
        return DriverAndConnect().createStatement();
    }

    public static PreparedStatement GetPreparedStatement(String Sql) throws SQLException {
        return DriverAndConnect().prepareStatement(Sql);
    }

}

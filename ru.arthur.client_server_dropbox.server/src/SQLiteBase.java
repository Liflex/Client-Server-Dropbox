import java.sql.*;

/**
 * Created by Tom on 30.11.2017.
 */
public class SQLiteBase {
    private static Connection connection;
    private static Statement statement;

    public  static void initSQLSecurityManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:chat_db.sqlite");
            System.out.println("База данных подсоединена");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getNick(String login, String password) {
        System.out.println("выполняем проверку логина");
        String request = "SELECT nickname FROM users WHERE login='" +
                login + "' AND password='" + password + "'";
        try (ResultSet resultSet = statement.executeQuery(request)) {
            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dispose() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

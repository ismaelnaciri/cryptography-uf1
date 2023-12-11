package cat.insvidreres.inf.cryptography.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class Start {

    private static Connection connection;

    public static void main(String[] args) throws SQLException {

        connection = MySQLConnection.getConnection();

        try {
            System.out.printf("Conected to database %s" + " successfully.%n", connection.getCatalog());

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection.isClosed())
                System.out.println("Connection closed");
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}

package cat.insvidreres.inf.cryptography.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnection {

    static String projectRoute = System.getProperty("user.dir");
    static String separator = File.separator;

    public static Connection getConnection() throws SQLException {

        String connectionPropertiesPath = projectRoute + separator + "src" + separator + "connection.properties";

        Connection connection = null;

        try (FileInputStream fis = new FileInputStream(connectionPropertiesPath)) {

            Properties properties = new Properties();
            properties.load(fis);

            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            String url = properties.getProperty("url");

            connection = DriverManager.getConnection(url, user, password);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        return connection;
    }

}

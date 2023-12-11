package cat.insvidreres.inf.cryptography.ex7;

import cat.insvidreres.inf.cryptography.connection.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ex7 {

    private static String user, password, salt, hashedHexPw;

    private static Connection connection;
    private static String SQLSentence;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    private static String dbHash, dbSalt;

    public static void main(String[] args) {
        try {
            connection = MySQLConnection.getConnection();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }


        SQLSentence = "SELECT user_password, salt FROM user_credentials WHERE (username = ?) AND (user_password = ?)";

        try {
            preparedStatement = connection.prepareStatement(SQLSentence);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }





        try {
            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error disposing of SQL objects: " + e.getMessage());
            e.printStackTrace();
        }
    }


}

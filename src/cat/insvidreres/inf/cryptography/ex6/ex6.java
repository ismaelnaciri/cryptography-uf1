package cat.insvidreres.inf.cryptography.ex6;

import cat.insvidreres.inf.cryptography.connection.MySQLConnection;
import cat.insvidreres.inf.cryptography.connection.Start;
import cat.insvidreres.inf.cryptography.utils.IsmaUtils;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;


public class ex6 {

    private static String user;
    private static String password, passwordAndSalt;
    private static String salt, hashString;
    private static SecureRandom secureRandom;
    private static Connection connection;
    private static String SQLSentence;
    private static PreparedStatement statement;


    public static void main(String[] args) {
        try {
            connection = MySQLConnection.getConnection();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }


        Scanner keyboard = new Scanner(System.in);

        System.out.println("Desired user? ");
        user = keyboard.next();

        System.out.println("Desired password? ");
        password = keyboard.next();

        generateAddSalt();
        hashString = IsmaUtils.stringToHash(passwordAndSalt);
        hashString = IsmaUtils.convertStringToHex(hashString);
        System.out.println("Hash to hex String: " + hashString);

        try {
            SQLSentence = "INSERT INTO user_credentials VALUES(?, ?, ?)";

            statement = connection.prepareStatement(SQLSentence);
            statement.setString(1, user);
            statement.setString(2, hashString);
            statement.setString(3, salt);

            int queryResult = statement.executeUpdate();

            if (queryResult > 0)
                System.out.println("Insert done successfully");
            else
                System.out.println("Error in the insert?");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error closing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void generateAddSalt() {
        secureRandom = new SecureRandom();
        int addOn;

        do {
            addOn = secureRandom.nextInt();
        } while(addOn < 0);

        salt = String.valueOf(addOn);
        IsmaUtils.convertStringToHex(salt);
        System.out.println("Salt: " + salt);

        passwordAndSalt = password + salt;
    }


}

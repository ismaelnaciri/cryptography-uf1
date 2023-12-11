package cat.insvidreres.inf.cryptography.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Properties;

public class IsmaUtils {

    public static String bytesToHex(byte[] bytes) {
        BigInteger bigInt = new BigInteger(1, bytes);
        String hex = bigInt.toString(16);
        // Add leading zeros if needed to ensure the string has an even length
        int paddingLength = (bytes.length * 2) - hex.length();
        if (paddingLength > 0) {
            return "0".repeat(paddingLength) + hex;
        }
        return hex;
    }

    public static void sha512HashSalt(String name, String surname1, String surname2) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            SecureRandom secureRandom = new SecureRandom();
            byte[] salt = new byte[32];
            secureRandom.nextBytes(salt);

            byte[] data = concatenateBytes(salt, name.getBytes());
            byte[] hash = md.digest(data);
            System.out.println("Name hash : " + bytesToHex(hash));

            data = concatenateBytes(salt, surname1.getBytes());
            hash = md.digest(data);
            System.out.println("First surname hash : " + bytesToHex(hash));

            data = concatenateBytes(salt, surname2.getBytes());
            hash = md.digest(data);
            System.out.println("Last Surname hash : " + bytesToHex(hash));

        } catch (NoSuchAlgorithmException ex) {
            System.err.println("Error al generar la clau: " + ex);
        }
    }

    public static void sha512Hash(String name, String surname1, String surname2) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            byte[] data = name.getBytes(StandardCharsets.UTF_8);
            byte[] hash = md.digest(data);
            System.out.println("Name hash: " + bytesToHex(hash));

            data = surname1.getBytes(StandardCharsets.UTF_8);
            hash = md.digest(data);
            System.out.println("First Surname hash: " + bytesToHex(hash));

            data = surname2.getBytes(StandardCharsets.UTF_8);
            hash = md.digest(data);
            System.out.println("Last Surname hash: " + bytesToHex(hash));

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private static byte[] concatenateBytes(byte[] first, byte[] second) {
        byte[] result = new byte[first.length + second.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }


    public static SecretKey keyGenerationFromHash(byte[] hashBytes, int keyLength, String algorithm) {
        SecretKey sKey = null;

        try {
            SecureRandom secureRandom = new SecureRandom();
            byte[] randomBytes = new byte[keyLength / 8];
            secureRandom.nextBytes(randomBytes);

            byte[] keyBytes = new byte[keyLength / 8];
            for (int i = 0; i < keyBytes.length; i++) {
                keyBytes[i] = (byte) (hashBytes[i] ^ randomBytes[i]);
            }

            sKey = new SecretKeySpec(keyBytes, algorithm);
        } catch (IllegalArgumentException ex) {
            System.err.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }

        return sKey;
    }

    public static String stringToHash(String text) {
        String hashString = "";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            byte[] data = text.getBytes(StandardCharsets.UTF_8);
            byte[] hash = messageDigest.digest(data);

            hashString = new String(hash, StandardCharsets.UTF_8);

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Aquest algoritme no existeix.  " + e.getMessage());
            e.printStackTrace();
        }

        return hashString;
    }

    public static String convertStringToHex(String str) {

        StringBuffer result = new StringBuffer();

        // loop chars one by one
        for (char temp : str.toCharArray()) {

            int decimal = (int) temp;

            result.append(Integer.toHexString(decimal));
        }

        return result.toString();
    }

//    public static Key generateKey(int keySize) {
//      try {
//          KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//            keyGenerator.init(256);
//
//
//      } catch (NoSuchAlgorithmException e) {
//          System.out.println("Error: " + e.getMessage());
//          e.printStackTrace();
//      }
//    }

    public static void showKeytoolKeys(String path, String passw, String alias) {
        try {
            Process process = Runtime.getRuntime()
                    .exec("keytool -list -v -keystore "
                                    + path + " -storetype JCEKS -alias " + alias);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            process.waitFor();

        } catch (IOException
                | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static KeyPair keyPairGenerator(int keyLength) {
        KeyPair keyPair = null;

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(keyLength);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return keyPair;
    }

    public static byte[] hashData(String data) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            return messageDigest.digest(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] encryptWithPrivateKey(byte[] data, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decryptWithPublicKey(byte[] encryptedData, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return cipher.doFinal(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getKeyListCommand() {

        String propertiesPath = getPropertiesPath();

        try (FileInputStream fis = new FileInputStream(propertiesPath)){

            Properties properties = new Properties();
            properties.load(fis);

            String name = "C:\\Users\\Usuario\\" + properties.getProperty("storeName");
            String pw = properties.getProperty("password");

            String cmdLine = "keytool -list -keystore " + name
                    + " -storepass " + pw;

            return cmdLine;

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();

            return null;
        }
    }

    public static String createKeyCmd(String keyName) {

        String propertiesPath = getPropertiesPath();

        try (FileInputStream fis = new FileInputStream(propertiesPath)) {

            Properties properties = new Properties();
            properties.load(fis);

            String name = "C:\\Users\\Usuario\\" + properties.getProperty("storeName");
            String pw = properties.getProperty("password");

            String cmdLine = "keytool -genseckey -alias " + keyName
                            + " -keyalg AES -keysize 128 -storetype PKCS12 -keystore " + name
                            + " -storepass " + pw;

            return cmdLine;

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();

            return null;
        }
    }

    public static String printSpecificKeyCmd(String keyName) {

        String propertiesPath = getPropertiesPath();

        try (FileInputStream fis = new FileInputStream(propertiesPath)) {

            Properties properties = new Properties();
            properties.load(fis);

            String storeName = "C:\\Users\\Usuario\\" + properties.getProperty("storeName");
            String storePassword = properties.getProperty("password");

            String cmdLine = "keytool -list -keystore " + storeName
                            + " -storepass " + storePassword
                            + " -alias " + keyName;



            return cmdLine;

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();

            return null;
        }
    }

    public static String getPropertiesPath() {
        String projectRoute = System.getProperty("user.dir");
        String separator = File.separator;

        String propertiesPath = projectRoute + separator
                + "src" + separator
                + "keytool-store.properties";

        return propertiesPath;
    }



}

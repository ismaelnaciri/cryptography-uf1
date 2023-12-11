package cat.insvidreres.inf.cryptography.ex1;


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ex1 {

    private static SecretKey keyGeneration(String algorythm, int keySize) {
        SecretKey sKey = null;

        try {
            KeyGenerator kgen = KeyGenerator.getInstance(algorythm);
            kgen.init(keySize);
            sKey = kgen.generateKey();

        } catch (NoSuchAlgorithmException ex) {
            System.err.println("Error al generar clau: " + ex.getMessage());
        }

        return sKey;
    }

    private static String base64encode(SecretKey skey) {
        return Base64.getEncoder().encodeToString(skey.getEncoded());
    }

    public static void main(String[] args) {
        String algorythm = "AES";
        int keySize = 128;
        SecretKey skey = keyGeneration(algorythm, keySize);
        System.out.println("Clau "+ algorythm + " de " + keySize + " bits generada: " + base64encode(skey));
    }
}

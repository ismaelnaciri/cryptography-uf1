package cat.insvidreres.inf.cryptography.ex11;

import cat.insvidreres.inf.cryptography.utils.IsmaUtils;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import static cat.insvidreres.inf.cryptography.utils.IsmaUtils.*;

public class ex11 {

    public static void main(String[] args) {
        String pw = "pep1234";
        KeyPair keyPair = keyPairGenerator(2048);
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        byte[] hashedData = hashData(pw);
        byte[] encryptedData = encryptWithPrivateKey(hashedData, privateKey);
        byte[] decryptedData = decryptWithPublicKey(encryptedData, publicKey);

        if (decryptedData != null) {
            System.out.println("Hash: " + Arrays.toString(hashedData));
            String decryptedText = new String(decryptedData);
            System.out.println("Original Text: " + pw);
            System.out.println("Decrypted Text: " + decryptedText); //Retorna el hash desencriptat
        } else {
            System.out.println("Decryption failed.");
        }
    }
}

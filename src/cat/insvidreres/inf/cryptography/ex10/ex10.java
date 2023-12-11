package cat.insvidreres.inf.cryptography.ex10;

import cat.insvidreres.inf.cryptography.utils.IsmaUtils;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import static cat.insvidreres.inf.cryptography.utils.IsmaUtils.*;

public class ex10 {

    public static void main(String[] args) {
        String pw = "pep1234";
        KeyPair keyPair = keyPairGenerator(2048);
        PrivateKey privateKey = keyPair.getPrivate();

        byte[] hashedPassword = hashData(pw);
        System.out.println("Hash: " + Arrays.toString(hashedPassword));

        if (hashedPassword != null) {
            try {
                byte[] encryptedHash = encryptWithPrivateKey(hashedPassword, privateKey);

                System.out.println("Hash encriptat: " + Arrays.toString(encryptedHash));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}

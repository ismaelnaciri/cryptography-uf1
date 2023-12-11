package cat.insvidreres.inf.cryptography.ex9;

import cat.insvidreres.inf.cryptography.utils.IsmaUtils;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Arrays;

import static cat.insvidreres.inf.cryptography.utils.IsmaUtils.*;


public class ex9 {

    public static void main(String[] args) {
        String pw = "Test1234";

        KeyPair keyPair = keyPairGenerator(2048);
        PrivateKey privateKey = keyPair.getPrivate();

        byte[] pwEncrypted = encryptWithPrivateKey(pw.getBytes(StandardCharsets.UTF_8), privateKey);
        String pwEncryptedString = new String(pwEncrypted, StandardCharsets.UTF_8);

        System.out.println(Arrays.toString(pwEncrypted));
        System.out.println(pwEncryptedString);
    }
}

package cat.insvidreres.inf.cryptography.ex3;

import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static cat.insvidreres.inf.cryptography.utils.IsmaUtils.*;

public class ex3 {

    private static byte[] hashData;

    private static void sha256Hash(String randomText) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] data = randomText.getBytes(StandardCharsets.UTF_8);
            byte[] hash = md.digest(data);
            hashData = hash;

            System.out.println("Random text hash in hexadecimal: " + bytesToHex(hash));
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("No existeix aquest algoritme: " + ex.getMessage());
        }
    }


    public static void main(String[] args) {
        String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In sed tempus ligula. Maecenas aliquet felis rhoncus, scelerisque massa nec, sollicitudin libero. Aenean eros sem, accumsan vitae aliquet non, placerat in dolor. Nam mattis urna libero, a euismod massa sollicitudin in. Phasellus lobortis pretium est. Donec et tellus vitae mi suscipit malesuada at eu risus. Integer non enim viverra sapien molestie viverra eu quis lectus. Donec porta dolor turpis, ut sagittis libero convallis eu. Nullam ac varius dui. Aenean ullamcorper nibh eu est blandit, at pellentesque mi tempor.";

        sha256Hash(loremIpsum);
        SecretKey sKey = keyGenerationFromHash(hashData, 256, "AES");
        String encodedKey = Base64.getEncoder().encodeToString(sKey.getEncoded());

        System.out.println("Clau generada: " + encodedKey);
    }
}

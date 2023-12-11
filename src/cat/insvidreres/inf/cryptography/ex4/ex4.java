package cat.insvidreres.inf.cryptography.ex4;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static cat.insvidreres.inf.cryptography.utils.IsmaUtils.*;

public class ex4 {

    public static void main(String[] args) {
        sha512HashSalt("isma", "naciri", "fernandez");

    }
}

package chapter1;

import java.util.Random;

public class UnbreakableEncription {

    public static void main(String[] args) {
        KeyPair kp = encrypt("One time pad!");
        System.out.println(decrypt(kp));
    }

    private static byte[] randomKey(int length) {
        byte[] dummy = new byte[length];
        Random random = new Random();
        random.nextBytes(dummy);

        return dummy;
    }

    public static KeyPair encrypt(String original) {
        byte[] originalBytes = original.getBytes();
        byte[] dummyKey = randomKey(originalBytes.length);
        byte[] encriptedKey = new byte[originalBytes.length];

        // XOR every byte
        for (int i = 0; i < originalBytes.length; i++) {
            encriptedKey[i] = (byte) (originalBytes[i] ^ dummyKey[i]);
        }

        return new KeyPair(dummyKey, encriptedKey);
    }

    public static String decrypt(KeyPair kp) {
        byte[] decrypted = new byte[kp.key1.length];

        // XOR every byte
        for (int i = 0; i < decrypted.length; i++) {
            decrypted[i] = (byte) (kp.key1[i] ^ kp.key2[i]);
        }

        return new String(decrypted);
    }
}
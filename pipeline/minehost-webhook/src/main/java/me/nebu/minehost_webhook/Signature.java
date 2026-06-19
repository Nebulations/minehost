package me.nebu.minehost_webhook;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Scanner;

public class Signature {

    private static String secret;

    public static boolean load(File signatureFile) {
        try (Scanner scanner = new Scanner(signatureFile)) {
            secret = scanner.nextLine().trim();
        } catch (Exception e) {
            System.err.println("An error occurred");
            return false;
        }

        return true;
    }

    public static boolean isValid(String githubSignature, byte[] payload) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(key);

            StringBuilder hex = new StringBuilder();
            for (byte b : mac.doFinal(payload)) {
                hex.append(String.format("%02x", b & 0xff));
            }

            return MessageDigest.isEqual(
                    hex.toString().getBytes(StandardCharsets.UTF_8),
                    githubSignature.getBytes(StandardCharsets.UTF_8)
            );
        } catch (Exception e) {
            return false;
        }
    }

}

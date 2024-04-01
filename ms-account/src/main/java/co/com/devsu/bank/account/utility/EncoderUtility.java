package co.com.devsu.bank.account.utility;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Slf4j
public class EncoderUtility {

    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH_64 = 64;
    private static final int KEY_LENGTH_256 = 256;
    private static final String ALGORITHM_AES = "AES";
    private static final String SALT = "12345678";


    private static EncoderUtility encoderUtility;

    public static EncoderUtility getInstance() {
        if (encoderUtility == null)
            encoderUtility = new EncoderUtility();
        return encoderUtility;
    }

    public String encryptWithAES(String text, String secret) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(this.generateSecretKey(secret, SALT), ALGORITHM_AES);
            Cipher cipher = Cipher.getInstance(ALGORITHM_AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptedBytes = cipher.doFinal(text.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public String decryptWithAES(String text, String secret) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(this.generateSecretKey(secret, SALT), ALGORITHM_AES);
            Cipher cipher = Cipher.getInstance(ALGORITHM_AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decodedBytes = Base64.getDecoder().decode(text);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private byte[] generateSecretKey(String password, String salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATIONS, KEY_LENGTH_256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return factory.generateSecret(spec).getEncoded();
    }

    public String generateMD5Hash(String text) {
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Add input string bytes to digest
            md.update(text.getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest();

            // Convert bytes to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

}

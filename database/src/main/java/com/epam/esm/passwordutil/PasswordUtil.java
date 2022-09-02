package com.epam.esm.passwordutil;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Database's password encryptor and decoder.
 */
public class PasswordUtil {

    private static final String ENCRYPTOR_KEY = "top secret key:)";
    private static final String TRANSFORMATION = "AES";

    /**
     * Database's password encryptor.
     *
     * @param password to be encrypted
     * @return encrypted password
     */
    public static String encrypt(String password) throws InvalidKeyException, NoSuchPaddingException,
            NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {

        byte[] bytes = ENCRYPTOR_KEY.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, TRANSFORMATION);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(1, secretKeySpec);
        byte[] encrypted = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * Database's password decoder.
     *
     * @param password to be decrypted
     * @return decrypted password
     */
    public static String decrypt(String password) throws InvalidKeyException, NoSuchPaddingException,
            NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {

        byte[] bytes = ENCRYPTOR_KEY.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, TRANSFORMATION);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(2, secretKeySpec);
        byte[] decryptedValue = Base64.getDecoder().decode(password);
        byte[] decrypted = cipher.doFinal(decryptedValue);
        return new String(decrypted);
    }
}

package com.my;

import com.my.command.LoginCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 *  {@ code ServiceUtil} class represents the implementation of the encryption, decription password.
 * <br>
 * @author Tenisheva N.I.
 * @version 1.0

 */
public class Security {

    private static final Logger log = LogManager.getLogger(LoginCommand.class);

    private static SecretKeySpec secretKeySpec;
    private static String SECRET_KEY = "S!@#hghjkhOKj";
    private static byte[] key;

    public static void setKey(String myKey) {

        MessageDigest sha;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKeySpec = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
    }

    public static String encrypt(String strToEncrypt) {
        try {
            setKey(SECRET_KEY);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            log.error("Error while encrypting: {}", e.getMessage());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        try {
            setKey(SECRET_KEY);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            log.error("Error while encrypting: {}", e.getMessage());
        }
        return null;
    }
}

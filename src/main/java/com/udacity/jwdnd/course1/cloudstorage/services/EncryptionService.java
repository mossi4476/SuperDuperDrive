package com.udacity.jwdnd.course1.cloudstorage.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class EncryptionService {
    private final Logger logger = LoggerFactory.getLogger(EncryptionService.class);

    public String encryptValue(String data, String key) {
        try {
            Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedValue = cipher.doFinal(data.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encryptedValue);
        } catch (Exception e) {
            logger.error("Encryption error: ", e);
            return null;
        }
    }

    public String decryptValue(String data, String key) {
        try {
            Cipher cipher = initCipher(Cipher.DECRYPT_MODE, key);
            byte[] decryptedValue = cipher.doFinal(Base64.getDecoder().decode(data));
            return new String(decryptedValue);
        } catch (Exception e) {
            logger.error("Decryption error: ", e);
            return null;
        }
    }

    // New method to initialize cipher
    private Cipher initCipher(int mode, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(mode, secretKey);
        return cipher;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.killswitch;

import java.awt.RenderingHints.Key;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author q8r9e4
 */
public class crypt {

    byte[] root = new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09,
        0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17};
    
    Random random = new Random();
    public byte[] key  = new byte[24];
    
    private final Cipher cipher;
    private final SecretKeySpec RootKey;

    public int lengthPriv;
    public int lengthRoot;
    private final SecretKeySpec Key;
    
    crypt(byte[] keyBytes)  throws NoSuchAlgorithmException, NoSuchPaddingException {
        key = keyBytes;
        RootKey = new SecretKeySpec(root, "AES");
        Key = new SecretKeySpec(key, "AES");

        cipher = Cipher.getInstance("AES");
    }
    
    crypt() throws NoSuchAlgorithmException, NoSuchPaddingException {

        System.out.println(root.length);
        random.nextBytes(key);
        RootKey = new SecretKeySpec(root, "AES");
        Key = new SecretKeySpec(key, "AES");

        cipher = Cipher.getInstance("AES");
    }

    public byte[] encryptRoot(byte[] input) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, RootKey);
        byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
        int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
        ctLength += cipher.doFinal(cipherText, ctLength);
        lengthRoot = ctLength;

        return cipherText;
    }
    
    public byte[] decryptRoot(byte[] cipherText,int ctLength) throws InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.DECRYPT_MODE, RootKey);
        byte[] plainText = new byte[cipher.getOutputSize(ctLength)];
        int ptLength = cipher.update(cipherText, 0, ctLength, plainText, 0);
        ptLength += cipher.doFinal(plainText, ptLength);
        return plainText;
    }
    
    public byte[] encryptPriv(byte[] input) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        cipher.init(Cipher.ENCRYPT_MODE, Key);
        byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
        int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
        ctLength += cipher.doFinal(cipherText, ctLength);
        lengthPriv = ctLength;

        return cipherText;
    }

    public byte[] decryptPriv(byte[] cipherText,int ctLength) throws InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.DECRYPT_MODE, Key);
        byte[] plainText = new byte[cipher.getOutputSize(ctLength)];
        int ptLength = cipher.update(cipherText, 0, ctLength, plainText, 0);
        ptLength += cipher.doFinal(plainText, ptLength);
        return plainText;
    }
    
    public byte[] decrypt(byte[] cipherText,int lengthRoot,int lengthPriv) throws InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        byte[] rootDec = decryptRoot(cipherText, lengthRoot);
        return decryptPriv(rootDec, lengthPriv);
        
    }
    
    public byte[] encrypt(String cipherText) throws InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        byte[] PrivEnc = encryptPriv(cipherText.getBytes());
        return encryptRoot(PrivEnc);
        
    }
}

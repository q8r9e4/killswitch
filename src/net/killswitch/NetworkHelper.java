/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.killswitch;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

/**
 *
 * @author q8r9e4
 */
public class NetworkHelper {

    private final crypt crypter;

    public NetworkHelper() throws NoSuchAlgorithmException, NoSuchPaddingException {
        crypter = new crypt();
    }

    public NetworkHelper(crypt inst){
        crypter = inst;
    }
    
    public void sendEnc(DataOutputStream outStream, String str) throws InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] encrypt = crypter.encrypt(str);
        byte[] Key = crypter.key;
        System.out.println(Key.toString());
        int lengthRoot = crypter.lengthRoot;
        System.out.println(lengthRoot);
        int lengthPriv = crypter.lengthPriv;
        System.out.println(lengthPriv);

        outStream.write(Key);
        outStream.writeInt(lengthRoot);
        outStream.writeInt(lengthPriv);
        outStream.write(encrypt);
    }

    public byte[] readEnc(DataInputStream inpStream) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        byte[] key = new byte[24];
            inpStream.read(key);
            int LengthRoot = inpStream.readInt();
            int LengthPriv = inpStream.readInt();
            byte[] encrypt = new byte[LengthRoot];
            inpStream.read(encrypt);
            
             crypt testNewCrypt = new crypt(key);

            byte[] decrypt = testNewCrypt.decrypt(encrypt, LengthRoot, LengthPriv);
            //test.decrypt(encrypt,test.lengthRoot,test.lengthPriv);
            System.out.println("decrypt: " + new String(decrypt));
            
            return decrypt;
    }
}

package net.killswitch;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.util.Vector;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author q8r9e4
 */
public class Killswitch {

    private Vector<String> staticHosts = new Vector<String>();

    /**
     * @param args
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, IOException, NoSuchProviderException, ShortBufferException {
        String test2 = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

        boolean server = false;
        for (String arg: args){
            if (arg.equalsIgnoreCase("--server")){
                server = true;
            }
        }
        
        if (server){
            crypt test = new crypt();
            byte[] encrypt = test.encrypt("teeeeest :>");
            ServerSocket serverSocket = new ServerSocket(1337);
            System.out.println("started server");
            byte[] Key = test.key;
            System.out.println(Key.toString());
            int lengthRoot = test.lengthRoot;
            System.out.println(lengthRoot);
            int lengthPriv = test.lengthPriv;
            System.out.println(lengthPriv);
            Socket socket = serverSocket.accept();
            System.out.println("Connect! "+socket.toString());
            DataInputStream inpStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
            
            String getKey = inpStream.readUTF();
            if (getKey.equals("me wantz encrypted!")){
                outStream.write(Key);
                outStream.writeInt(lengthRoot);
                outStream.writeInt(lengthPriv);
                outStream.write(encrypt);
            }
            
        } else {
            Socket socket = new Socket("localhost", 1337);
            DataInputStream inpStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
            
            outStream.writeUTF("me wantz encrypted!");
            
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
        }
//        crypt test = new crypt();
//        byte[] encrypt = test.encrypt(test2);
//        System.out.println("encrypt: "+new String(encrypt));
//        
//        //send test.lengthRoot,test.lengthPriv and test.key
//        crypt testNewCrypt = new crypt(test.key);
//        byte[] decrypt =  testNewCrypt.decrypt(encrypt,test.lengthRoot,test.lengthPriv);
//        //test.decrypt(encrypt,test.lengthRoot,test.lengthPriv);
//        System.out.println("decrypt: "+new String(decrypt));

    }

    public void run() {
        this.staticHosts.add("http://google.com");
        this.staticHosts.add("http://en.wikipedia.org");


        //start local server
        Vector<String> FoundHosts = new Vector<String>();
        for (String host : this.staticHosts) {
            //Try to connect (to lazy to write that now)
            //if found
            //FoundHosts.add(host);
            System.out.println(host);
        }

        if (FoundHosts.size() == 0) {
            //geo IP
        }

        if (FoundHosts.size() == 0) {
            //found no hosts/other clients
            //we are fucked
            System.out.println("QUICK! HIDE YOURSELF! CIA MIGHT BE LOOKING FOR YOU!");
            System.out.println("(can't connect to nodes or other clients. Are you connected to the internet?)");
        } else {
            // anounce client's IP
            // fetch neighbour IP's
        }
    }

    public Vector<String> GeoIPLookup() {
        Vector<String> FoundHosts = new Vector<String>();




        return FoundHosts;

    }
}

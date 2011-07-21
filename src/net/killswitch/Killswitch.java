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
        /*
         * please god, hear my prayer
         * I will never ask you for anything anymore
         * if you let run this programm correctly
         */
        
        String test2 = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

        boolean server = false;
        for (String arg: args){
            if (arg.equalsIgnoreCase("--server")){
                server = true;
            }
        }
        
        if (server){
            //starting test server
            ServerSocket serverSocket = new ServerSocket(1337);
            System.out.println("started server");
            Socket socket = serverSocket.accept();
            System.out.println("Connect! "+socket.toString());
            DataInputStream inpStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());

            
            NetworkHelper helper = new NetworkHelper();
            
            //once the network is set up, this is the only function to send data over the wire
            helper.sendEnc(outStream, "asd");
        } else {
            Socket socket = new Socket("localhost", 1337);
            DataInputStream inpStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
            
            NetworkHelper helper = new NetworkHelper();
            
            //same here, only func to read, once its set up
            System.out.println(helper.readEnc(inpStream));

        }
        
        /*
         * Thank you God
         */

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

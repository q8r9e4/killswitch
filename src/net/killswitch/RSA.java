
package net.killswitch;

/**
 *
 * @author q8r9e4
 */

import java.math.BigInteger;
import java.security.SecureRandom;
    

public class RSA {
   private final static BigInteger one      = new BigInteger("1");
   private final static SecureRandom random = new SecureRandom();

   private BigInteger privateKey;
   public BigInteger publicKey;
   private BigInteger modulus;

   // generate an N-bit (roughly) public and private key
   RSA(int N) {
      BigInteger p = BigInteger.probablePrime(N/2, random);
      BigInteger q = BigInteger.probablePrime(N/2, random);
      BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

      modulus    = p.multiply(q);                                  
      publicKey  = new BigInteger("65537");     // common value in practice = 2^16 + 1
      privateKey = publicKey.modInverse(phi);
   }


   BigInteger encrypt(BigInteger message) {
      return message.modPow(publicKey, modulus);
   }

   BigInteger decrypt(BigInteger encrypted) {
      return encrypted.modPow(privateKey, modulus);
   }

   public String toString() {
      String s = "";
      s += "public  = " + publicKey  + "\n";
      s += "private = " + privateKey + "\n";
      s += "modulus = " + modulus;
      return s;
   }
 
   public static BigInteger StringToBI(String input){
      byte[] bytes = input.getBytes();
      BigInteger message = new BigInteger(bytes);
      return message;
   }
   
   public static String BIToString(BigInteger input){
       String ret = "";
      for (byte b:input.toByteArray()){
          ret += (char)b;
      }
      return ret;
   }
   public static void main(String[] args) {
      int N = Integer.parseInt(args[0]);
      RSA key = new RSA(N);
      System.out.println(key);
 
      BigInteger message = StringToBI("Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

      BigInteger encrypt = key.encrypt(message);
      BigInteger decrypt = key.decrypt(encrypt);
      System.out.println("message    = " + message);
      System.out.println("encrpyted  = " + encrypt);
      System.out.println("decrypted  = " + decrypt);
      System.out.println("decrypted2 = " + BIToString(decrypt));
      
       message = StringToBI("test");

       encrypt = key.encrypt(message);
       decrypt = key.decrypt(encrypt);
      System.out.println("message    = " + message);
      System.out.println("encrpyted  = " + encrypt);
      System.out.println("decrypted  = " + decrypt);
      System.out.println("decrypted2 = " + BIToString(decrypt));
   }
}
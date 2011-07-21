package back;

/**
 *
 * @author q8r9e4
 */
import java.io.DataInputStream;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import net.killswitch.util;

public class RSA {

    private final static BigInteger one = new BigInteger("1");
    private final static SecureRandom random = new SecureRandom();
    private BigInteger privateKey;
    public BigInteger publicKey;
    private BigInteger modulus;
    private final int len;

    // generate an N-bit (roughly) public and private key
    RSA(int N) {
        BigInteger p = BigInteger.probablePrime(N / 2, random);
        BigInteger q = BigInteger.probablePrime(N / 2, random);
        BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

        modulus = p.multiply(q);
        publicKey = new BigInteger("65537");     // common value in practice = 2^16 + 1
        privateKey = publicKey.modInverse(phi);
        len = privateKey.bitLength();
    }

    String encrypt(String message) throws IOException {
        String[] slices = util.slice(message, len/4);
        for (String str:slices){
            System.out.println(str);
        }
        String output = "";
        for (String work : slices) {
            BigInteger encrypt = this.encryptShort(StringToBI(work));
            BigInteger decrypt = this.decryptShort(encrypt);
            System.out.println("enc-dec: " + BIToString(decrypt));

            output += encrypt.toString();

        }
        return output;
    }

    public String decrypt(String cypher) {
        String output = "";
        for (String work : util.slice(cypher, len/4)) {
            BigInteger decrypt = this.decryptShort(StringToBI(work));
            output += BIToString(decrypt);
        }
//        return BIToString(new BigInteger(output));
        return output;
    }

    BigInteger encryptShort(BigInteger message) {

        return message.modPow(publicKey, modulus);
    }

    BigInteger decryptShort(BigInteger encrypted) {
        return encrypted.modPow(privateKey, modulus);
    }

    public String toString() {
        String s = "";
        s += "public  = " + publicKey + "\n";
        s += "private = " + privateKey + "\n";
        s += "modulus = " + modulus;
        return s;
    }

    public static BigInteger StringToBI(String input) {
        byte[] bytes = input.getBytes();
        BigInteger message = new BigInteger(bytes);
        return message;
    }

    public static String BIToString(BigInteger input) {
        String ret = "";
        for (byte b : input.toByteArray()) {
            ret += (char) b;
        }
        return ret;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        RSA key = new RSA(N);
        System.out.println(key);

        BigInteger message = StringToBI("Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

        BigInteger encrypt = key.encryptShort(message);
        BigInteger decrypt = key.decryptShort(encrypt);
        System.out.println("message    = " + message);
        System.out.println("encrpyted  = " + encrypt);
        System.out.println("decrypted  = " + decrypt);
        System.out.println("decrypted2 = " + BIToString(decrypt));

        message = StringToBI("test");

        encrypt = key.encryptShort(message);
        decrypt = key.decryptShort(encrypt);
        System.out.println("message    = " + message);
        System.out.println("encrpyted  = " + encrypt);
        System.out.println("decrypted  = " + decrypt);
        System.out.println("decrypted2 = " + BIToString(decrypt));
    }
}
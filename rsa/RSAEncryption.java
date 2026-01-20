import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAEncryption {
    static BigInteger p;
    static BigInteger q;
    static BigInteger N;
    static BigInteger phi;
    static BigInteger e;
    static BigInteger d;
    public static void generateKeys () {
        // step 1 -> fixed prime numbers
        p = BigInteger.valueOf(17);
        q = BigInteger.valueOf(23);
        // calculate n and phi(n)
        N = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // step 2 -> select random e
        SecureRandom random = new SecureRandom();
        // loop until valid e is found
        while (true) {
            // let e be a random number between 0 and phi
            e = new BigInteger(phi.bitLength(), random);
            // connstraints: 1 < e < phi; gcd(e, phi) == 1
            if (e.compareTo(BigInteger.ONE) > 0 &&
                e.compareTo(phi) < 0 &&
                e.gcd(phi).equals(BigInteger.ONE)) {
                    break;
            }
        }
        // step 3 -> calculate matching d
        d = e.modInverse(phi);
    }
    public static BigInteger[] getPublicKey() {
        BigInteger[] publicKey = {e, N};
        return publicKey;
    }
    public static BigInteger[] getPrivateKey() {
        BigInteger[] privateKey = {d, p, q};
        return privateKey;
    }
    public static void main(String[] args) {
        generateKeys();
        System.out.println("Selected prime numbers are " + p + " and " + q);
        System.out.println("Modulus, N = " + N);
        System.out.println("Phi(N) = " + phi);
        System.out.println("Randomly Selected e: " + e);
        System.out.println("Calculated d: " + d);
        System.out.println("Check: (e * d) mod phi = (" + e + " * " + d + ") mod " + phi + " = " + e.multiply(d).mod(phi));
    }
}
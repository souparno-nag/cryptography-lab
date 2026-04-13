import java.math.BigInteger;

public class El_Gamal {
    static BigInteger generatePublicKey (BigInteger g, BigInteger x, BigInteger p) {
        return g.modPow(x, p);
    }
    static BigInteger[] encrypt (BigInteger m, BigInteger y, BigInteger g, BigInteger p, BigInteger k) {
        BigInteger C1 = g.modPow(k, p);
        BigInteger C2 = m.multiply(y.modPow(k, p)).mod(p);
        return new BigInteger[]{C1, C2};
    }
    static BigInteger decrypt (BigInteger C1, BigInteger C2, BigInteger x, BigInteger p) {
        BigInteger s = C1.modPow(x, p);
        BigInteger inv_s = s.modInverse(p);
        return C2.multiply(inv_s).mod(p);
    }
    public static void main(String[] args) {
        BigInteger p = BigInteger.valueOf(19);
        BigInteger g = BigInteger.valueOf(10);
        BigInteger x = BigInteger.valueOf(5);
        BigInteger m = BigInteger.valueOf(8);
        BigInteger k = BigInteger.valueOf(6);

        BigInteger y = generatePublicKey(g, x, p);
        BigInteger[] cipher = encrypt(m, y, g, p, k);
        System.out.println(cipher[0] + " " + cipher[1]);
        BigInteger plain = decrypt(cipher[0], cipher[1], x, p);
        System.out.println(plain);
    }
}

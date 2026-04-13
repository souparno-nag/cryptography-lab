import java.math.BigInteger;

public class RSA_Encryption {
    static BigInteger calculatePrivateKey(BigInteger e, BigInteger p, BigInteger q) {
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        return e.modInverse(phi);
    }
    static BigInteger encryption(BigInteger M, BigInteger e, BigInteger N) {
        return M.modPow(e, N);
    }
    static BigInteger decryption(BigInteger C, BigInteger d, BigInteger N) {
        return C.modPow(d, N);
    }
    public static void main(String[] args) {
        BigInteger p = BigInteger.valueOf(17);
        BigInteger q = BigInteger.valueOf(11);
        BigInteger e = BigInteger.valueOf(7);

        BigInteger N = p.multiply(q);

        BigInteger d = calculatePrivateKey(e, p, q);
        System.out.println(d);

        BigInteger M = BigInteger.valueOf(88);
        BigInteger C = encryption(M, e, N);
        System.out.println(C);

        BigInteger P = decryption(C, d, N);
        System.out.println(P);
    }
}

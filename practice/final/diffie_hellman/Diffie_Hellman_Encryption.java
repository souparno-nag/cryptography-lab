import java.math.BigInteger;

public class Diffie_Hellman_Encryption {
    public static BigInteger calculatePublicKey(BigInteger X, BigInteger q, BigInteger alpha) {
        return alpha.modPow(X, q);
    }
    public static BigInteger calculateSharedSecretKey(BigInteger Y, BigInteger X, BigInteger q) {
        return Y.modPow(X, q);
    }
}

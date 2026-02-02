import java.math.BigInteger;
public class DiffieHellmanEncryption {
    public static BigInteger generatePublicKey (BigInteger q, BigInteger alpha, BigInteger X) {
        BigInteger Y = alpha.modPow(X, q);
        return Y;
    }
    public static BigInteger calculateSharedSecretKey (BigInteger q, BigInteger X, BigInteger Y) {
        return Y.modPow(X, q);
    }
}

import java.math.BigInteger;

public class rsa_encryption {
    static BigInteger generatePrivateKey(BigInteger p, BigInteger q, BigInteger publicKey) {
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        BigInteger privateKey = publicKey.modInverse(phi);
        return privateKey;
    }
    static BigInteger encryption(BigInteger message, BigInteger publicKey, BigInteger N) {
        BigInteger encrypted = message.modPow(publicKey, N);
        return encrypted;
    }
    static BigInteger decryption(BigInteger encryptedMessage, BigInteger privateKey, BigInteger N) {
        BigInteger decrypted = encryptedMessage.modPow(privateKey, N);
        return decrypted;
    }
}

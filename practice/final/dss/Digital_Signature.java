import java.math.BigInteger;

public class Digital_Signature {
    BigInteger calcHash(String message, BigInteger q) {
        int hash = 0;
        for (char ch : message.toCharArray()) hash += ch;
        return BigInteger.valueOf(hash).mod(q);
    }
    BigInteger calcPublicKey(BigInteger g, BigInteger x, BigInteger p) {
        return g.modPow(x, p);
    }
    BigInteger[] createSignature(String message, BigInteger g, BigInteger k, BigInteger p, BigInteger q, BigInteger x) {
        BigInteger r = g.modPow(k, p).mod(q);
        BigInteger inv_k = k.modInverse(q);
        BigInteger hash = calcHash(message, q);
        BigInteger s = inv_k.multiply(hash.add(x.multiply(r))).mod(q);
        return new BigInteger[]{r, s};
    }
    boolean verifySignature(String message, BigInteger r, BigInteger s, BigInteger q, BigInteger p, BigInteger y, BigInteger g) {
        BigInteger w = s.modInverse(q);
        BigInteger hash = calcHash(message, q);
        BigInteger u1 = hash.multiply(w).mod(q);
        BigInteger u2 = r.multiply(w).mod(q);
        BigInteger v = g.modPow(u1, p).multiply(y.modPow(u2, p)).mod(p).mod(q);
        if (v.equals(r)) return true;
        return false;
    }
}

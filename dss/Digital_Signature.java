import java.math.BigInteger;

public class Digital_Signature {

    // Hash function (simple)
    public static BigInteger hash(String message, BigInteger q) {
        int hash = 0;
        for (char c : message.toCharArray()) {
            hash += c;
        }
        return BigInteger.valueOf(hash).mod(q);
    }

    // Public key
    public static BigInteger generatePublicKey(BigInteger g, BigInteger x, BigInteger p) {
        return g.modPow(x, p);
    }

    // Signing
    public static BigInteger[] sign(String message, BigInteger x,
                                    BigInteger p, BigInteger q, BigInteger g,
                                    BigInteger k) {

        BigInteger r = g.modPow(k, p).mod(q);
        BigInteger kInv = k.modInverse(q);
        BigInteger h = hash(message, q);

        BigInteger s = kInv.multiply(h.add(x.multiply(r))).mod(q);

        return new BigInteger[]{r, s};
    }

    // Verification
    public static boolean verify(String message, BigInteger r, BigInteger s,
                                BigInteger y,
                                BigInteger p, BigInteger q, BigInteger g) {

        if (r.compareTo(BigInteger.ZERO) <= 0 || r.compareTo(q) >= 0)
            return false;
        if (s.compareTo(BigInteger.ZERO) <= 0 || s.compareTo(q) >= 0)
            return false;

        BigInteger w = s.modInverse(q);
        BigInteger h = hash(message, q);

        BigInteger u1 = h.multiply(w).mod(q);
        BigInteger u2 = r.multiply(w).mod(q);

        BigInteger v = (g.modPow(u1, p).multiply(y.modPow(u2, p))).mod(p).mod(q);

        return v.equals(r);
    }
}
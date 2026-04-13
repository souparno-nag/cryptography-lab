package practice.dsa;

import java.math.BigInteger;

public class Digital_Signature {
    private static BigInteger hash(String message, BigInteger q) {
        int hash = 0;
        for (char ch : message.toCharArray()) {
            hash += ch;
        }
        return BigInteger.valueOf(hash).mod(q);
    }
    public static BigInteger generatePublicKey (BigInteger g, BigInteger x, BigInteger p) {
        return g.modPow(x, p);
    }
    public static BigInteger[] createSignature (String message, BigInteger q, BigInteger p, BigInteger g, BigInteger x, BigInteger k) {
        BigInteger h = hash(message, q);

        BigInteger r = g.modPow(k, p).mod(q);
        BigInteger invK = k.modInverse(q);
        BigInteger s = invK.multiply(h.add(x.multiply(r))).mod(q);

        return new BigInteger[]{r, s};
    }
    public static boolean verifySignature (String message, BigInteger r, BigInteger s, BigInteger y, BigInteger q, BigInteger p, BigInteger g) {
        if (r.compareTo(BigInteger.ZERO) <= 0 || r.compareTo(q) >= 0) return false;
        if (s.compareTo(BigInteger.ZERO) <= 0 || s.compareTo(q) >= 0) return false;

        BigInteger w = s.modInverse(q);
        BigInteger h = hash(message, q);

        BigInteger u1 = h.multiply(w).mod(q);
        BigInteger u2 = r.multiply(w).mod(q);

        BigInteger v = g.modPow(u1, p).multiply(y.modPow(u2, p)).mod(p).mod(q);

        if (v.equals(r)) return true;
        return false;
    }
    public static void main(String[] args) {
        BigInteger p = BigInteger.valueOf(23);
        BigInteger q = BigInteger.valueOf(11);

        BigInteger h = BigInteger.valueOf(2);
        BigInteger g = h.modPow(p.subtract(BigInteger.ONE).divide(q), p);

        BigInteger x = BigInteger.valueOf(5);
        BigInteger k = BigInteger.valueOf(7);

        BigInteger[] signature = createSignature("Hello", q, p, g, x, k);
        BigInteger r = signature[0];
        BigInteger s = signature[1];

        BigInteger y = generatePublicKey(g, x, p);

        if (verifySignature("Hello", r, s, y, q, p, g)) {
            System.out.println("Verified");
        } else {
            System.out.println("Not verified");
        }
    }
}

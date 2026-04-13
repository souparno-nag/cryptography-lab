import java.util.Scanner;
import java.math.BigInteger;

public class diffie_hellman_encryption {
    static BigInteger calculatePublicKey (BigInteger q, BigInteger alpha, BigInteger X) {
        return alpha.modPow(X, q);
    }
    static BigInteger calculateSharedSecretKey(BigInteger q, BigInteger X, BigInteger Y) {
        return Y.modPow(X, q);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the prime number: ");
        BigInteger q = BigInteger.valueOf(sc.nextInt());
        System.out.print("Enter the primitive root: ");
        BigInteger alpha = BigInteger.valueOf(sc.nextInt());
        System.out.print("Enter the first private key: ");
        BigInteger X1 = BigInteger.valueOf(sc.nextInt());
        System.out.print("Enter the second private key: ");
        BigInteger X2 = BigInteger.valueOf(sc.nextInt());
        BigInteger Y1 = calculatePublicKey(q, alpha, X1);
        BigInteger Y2 = calculatePublicKey(q, alpha, X2);

        System.out.println("\nPublic Key of User A (Y1): " + Y1);
        System.out.println("Public Key of User B (Y2): " + Y2);
        System.out.println();

        BigInteger ss1 = calculateSharedSecretKey(q, X1, Y2);
        BigInteger ss2 = calculateSharedSecretKey(q, X2, Y1);
        if (ss1.equals(ss2)) {
            System.out.println("The secret keys are the same: " + ss1);
        } else {
            System.out.println("The keys are not the same");
        }
        sc.close();
    }
}

import java.util.Scanner;

public class RC4 {
    private int[] S = new int[256];
    int i = 0, j = 0;

    // helper funtion - swap
    void swap(int a, int b) {
        int temp = S[a];
        S[a] = S[b];
        S[b] = temp;
    } 

    // KSA
    public RC4(byte[] key) {
        for (i = 0; i < 256; i++) S[i] = i;
        for (i = 0; i < 256; i++) {
            j = (j + S[i] + (key[i%key.length] & 0xFF))%256;
            swap(i, j);
        }
        i = 0; j = 0;
    }

    // PRGA
    public int nextByte() {
        i = (i + 1)%256;
        j = (j+S[i])%256;
        swap(i, j);
        return S[(S[i] + S[j])%256];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the size of the key: ");
        int n = sc.nextInt();
        byte[] key = new byte[n];
        System.out.println("Enter the key: ");
        for (int i = 0; i < n; i++) {
            key[i] = sc.nextByte();
        }
        RC4 rc4 = new RC4(key);
        // first 20 streams
        for (int i = 0; i < 20; i++) {
            int keyStreamByte = rc4.nextByte();
            System.out.printf("%d\t%d\t0x%02X\n", i+1, keyStreamByte, keyStreamByte);
        }
        sc.close();
    }
}

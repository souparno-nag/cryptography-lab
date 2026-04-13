import java.util.*;

public class des_encryption {
    static int[] initialPerm = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };

    static int[] expD = {
            32, 1, 2, 3, 4, 5, 4, 5,
            6, 7, 8, 9, 8, 9, 10, 11,
            12, 13, 12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21, 20, 21,
            22, 23, 24, 25, 24, 25, 26, 27,
            28, 29, 28, 29, 30, 31, 32, 1
    };

    static int[] per = {
            16, 7, 20, 21,
            29, 12, 28, 17,
            1, 15, 23, 26,
            5, 18, 31, 10,
            2, 8, 24, 14,
            32, 27, 3, 9,
            19, 13, 30, 6,
            22, 11, 4, 25
    };

    static int[][][] sbox = {
            {
                    { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
                    { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
                    { 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
                    { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 }
            },
            {
                    { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
                    { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
                    { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
                    { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 }
            },
            {
                    { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
                    { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
                    { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
                    { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 }
            },
            {
                    { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
                    { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
                    { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
                    { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 }
            },
            {
                    { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
                    { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
                    { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
                    { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 }
            },
            {
                    { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
                    { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
                    { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
                    { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 }
            },
            {
                    { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
                    { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
                    { 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
                    { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 }
            },
            {
                    { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
                    { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
                    { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
                    { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 }
            }
    };

    static int[] finalPerm = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };

    static int[] shiftTable = {
            1, 1, 2, 2,
            2, 2, 2, 2,
            1, 2, 2, 2,
            2, 2, 2, 1
    };
    static int[] keyComp = {
            14, 17, 11, 24, 1, 5,
            3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8,
            16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };

    static int[] keyp = {
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4
    };

    static String hex2bin (String s) {
        Map<Character, String> map = new HashMap<>();
        map.put('0', "0000");
        map.put('1', "0001");
        map.put('2', "0010");
        map.put('3', "0011");
        map.put('4', "0100");
        map.put('5', "0101");
        map.put('6', "0110");
        map.put('7', "0111");
        map.put('8', "1000");
        map.put('9', "1001");
        map.put('A', "1010");
        map.put('B', "1011");
        map.put('C', "1100");
        map.put('D', "1101");
        map.put('E', "1110");
        map.put('F', "1111");
        String bin = "";
        for (char ch : s.toCharArray()) {
            bin += map.get(ch);
        }
        return bin;
    }

    static String bin2hex (String bin) {
        Map<String, Character> map = new HashMap<>();
        map.put("0000", '0');
        map.put("0001", '1');
        map.put("0010", '2');
        map.put("0011", '3');
        map.put("0100", '4');
        map.put("0101", '5');
        map.put("0110", '6');
        map.put("0111", '7');
        map.put("1000", '8');
        map.put("1001", '9');
        map.put("1010", 'A');
        map.put("1011", 'B');
        map.put("1100", 'C');
        map.put("1101", 'D');
        map.put("1110", 'E');
        map.put("1111", 'F');
        String hex = "";
        for (int i = 0; i < bin.length(); i += 4) {
            String sub = bin.substring(i, i+4);
            hex += map.get(sub);
        }
        return hex;
    }

    static int bin2dec (int binary) {
        int dec = 0;
        int i = 0;
        while (binary > 0) {
            int temp = binary%10;
            dec += temp * Math.pow(2, i);
            binary /= 10;
            i++;
        }
        return dec;
    }

    static String dec2bin (int dec) {
        String binary = Integer.toBinaryString(dec);
        while (binary.length() % 4 != 0) binary = '0' + binary;
        return binary;
    }

    static String permute (String k, int[] arr, int n) {
        String result = "";
        for (int i = 0; i < n; i++) {
            result += k.charAt(arr[i] - 1);
        }
        return result;
    }

    static String XOR (String a, String b) {
        String result = "";
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == b.charAt(i)) {
                result += '0';
            } else {
                result += '1';
            }
        }
        return result;
    }

    static String shiftLeft (String s, int n) {
        for (int i = 0; i < n; i++) {
            s = s.substring(1) + s.charAt(0);
        }
        return s;
    }

    static String encrypt (String plaintext, List<String> rkb) {
        String pt = hex2bin(plaintext);
        pt = permute(pt, initialPerm, 64);
        String left = pt.substring(0, 32);
        String right = pt.substring(32);
        for (int i = 0; i < 16; i++) {
            String right_exp = permute(right, expD, 48);
            String right_xor = XOR(right_exp, rkb.get(i));
            // sbox
            String sboxString = "";
            for (int j = 0; j < 8; j++) {
                int row = bin2dec(Integer.parseInt(String.valueOf(right_xor.charAt(j*6)) + String.valueOf(right_xor.charAt(j*6+5))));
                int col = bin2dec(Integer.parseInt(String.valueOf(right_xor.charAt(j*6+1)) + String.valueOf(right_xor.charAt(j*6+2)) + String.valueOf(right_xor.charAt(j*6+3)) + String.valueOf(right_xor.charAt(j*6+4))));
                sboxString += dec2bin(sbox[j][row][col]);
            }
            String sbox_perm = permute(sboxString, per, 32);
            left = XOR(left, sbox_perm);
            
            // swap
            String temp = left;
            left = right;
            right = temp;
        }
        String combine = right + left;
        String ciphertext = permute(combine, finalPerm, 64);
        return ciphertext;
    }

    static String encryptionFinal(String plaintext, String key) {
        String keyPerm = hex2bin(key);
        keyPerm = permute(keyPerm, keyp, 56);
        String left = keyPerm.substring(0, 28);
        String right = keyPerm.substring(28);
        List<String> rkb = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            left = shiftLeft(left, shiftTable[i]);
            right = shiftLeft(right, shiftTable[i]);
            String currKey = left + right;
            currKey = permute(currKey, keyComp, 48);
            rkb.add(currKey);
        }
        String ciphertext = bin2hex(encrypt(plaintext, rkb));
        return ciphertext;
    }

    static String decryptionFinal(String plaintext, String key) {
        String keyPerm = hex2bin(key);
        keyPerm = permute(keyPerm, keyp, 56);
        String left = keyPerm.substring(0, 28);
        String right = keyPerm.substring(28);
        List<String> rkb = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            left = shiftLeft(left, shiftTable[i]);
            right = shiftLeft(right, shiftTable[i]);
            String currKey = left + right;
            currKey = permute(currKey, keyComp, 48);
            rkb.add(currKey);
        }
        Collections.reverse(rkb);
        String ciphertext = bin2hex(encrypt(plaintext, rkb));
        return ciphertext;
    }

    public static void main(String[] args) {
        String key = "AABB09182736CCDD";
        String message = "123456ABCD132536";

        String ciphertext = encryptionFinal(message, key);
        System.out.println(ciphertext);

        String plaintext = decryptionFinal(ciphertext, key);
        System.out.println(plaintext);
    }
}
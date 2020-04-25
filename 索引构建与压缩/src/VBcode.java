import java.math.BigInteger;
import java.util.LinkedList;

public class VBcode {
    static LinkedList<String> VBencode(int n) {
        LinkedList<String> BinaryStream = new LinkedList<>();
        LinkedList<Integer> number = new LinkedList<>();

        while (true) {
            number.add(0, n % 128);
            if (n < 128) {
                break;
            } else {
                n = n / 128;
            }
        }
        number.set(number.size() - 1, number.getLast() + 128);
        for (int i = 0; i < number.size(); i++) {
            BinaryStream.add(Binary(number.get(i)));
        }
        System.out.println();
        return BinaryStream;
    }

    static String Binary(int n) {
        String str = Integer.toBinaryString(n);
        if ((int) str.length() < 8) {
            int length = 8 - (int) str.length();
            for (int i = 0; i < length; i++) {
                str = "0" + str;
            }
        }
        System.out.print(str);
        System.out.print(" ");
        return str;
    }

    static void VBdecode(LinkedList<String> stream) {
        int number;
        int n = 0;
        for (int i = 0; i < stream.size(); i++) {
            if (Integer.parseInt(stream.get(i), 2) < 128) {
                n = 128 * n + Integer.parseInt(stream.get(i), 2);
            } else {
                n = 128 * n + (Integer.parseInt(stream.get(i), 2) - 128);
            }
        }
        System.out.println(n);
    }


    public static void main(String[] args) {
        int n = 13;
        LinkedList<String> BianryCode = VBencode(n);
        VBdecode(BianryCode);
    }
}

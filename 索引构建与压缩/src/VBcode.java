import java.math.BigInteger;
import java.util.LinkedList;

public class VBcode {
    //使用链表来表示数据流
    static LinkedList<String> VBencode(int n) {
        LinkedList<String> BinaryStream = new LinkedList<>();//存放二进制数据流
        LinkedList<Integer> number = new LinkedList<>();//存放数字

        while (true) {//因为采用7位二进制的编码方式，每一个数据段的基数为下一数据段的128倍
            //所以循环除以128以获得更高数据段的七位二进制数
            number.add(0, n % 128);
            if (n < 128) {
                break;
            } else {
                n = n / 128;
            }
        }
        System.out.print("VB编码为：");
        number.set(number.size() - 1, number.getLast() + 128);//把最后一个二进制数据段的最高位设为1标识结束
        for (int i = 0; i < number.size(); i++) {
            BinaryStream.add(Binary(number.get(i)));
        }//将获得的所有二进制数据都存入二进制流，模拟编码
        System.out.println();
        return BinaryStream;
    }

    static String Binary(int n) {//将十进制转换为7位二进制数
        String str = Integer.toBinaryString(n);
        if ((int) str.length() < 8) {//不足8位情况下补零操作
            int length = 8 - (int) str.length();
            for (int i = 0; i < length; i++) {
                str = "0" + str;
            }
        }
        System.out.print(str);
        System.out.print(" ");
        return str;
    }

    static void VBdecode(LinkedList<String> stream) {//对二进制数据流进行解码操作
        int number;
        int n = 0;
        for (int i = 0; i < stream.size(); i++) {//循环对二进制数据流中的每个数据段转换为十进制
            if (Integer.parseInt(stream.get(i), 2) < 128) {
                n = 128 * n + Integer.parseInt(stream.get(i), 2);
            } else {
                n = 128 * n + (Integer.parseInt(stream.get(i), 2) - 128);//当到数据流最后一个时
                //要减去之前设置的数据最高位的结束标识，即减去128
            }
        }
        System.out.println("VB解码为："+n);
    }


    public static void main(String[] args) {
        int n = 513;
        LinkedList<String> BianryCode = VBencode(n);
        VBdecode(BianryCode);
    }
}

public class GammaCode {

    static String GammaEncode(int n) {//对整数进行编码的函数
        String Binary = Integer.toBinaryString(n);//先将整数转为对应的二进制编码
        String offset = "", OffsetLength = "";//定义空字符串分别用于保存偏移和偏移的长度
        String GammaCode;
        if (Binary.length() == 1) {//特殊的情况如0，1这些二进制编码只有一位的数字需要特殊处理
            offset = "";
        } else {//非0，1数字将二进制编码开头的1去除
            Binary = Binary.substring(1);
        }

        for (int i = 0; i < Binary.length(); i++) {//使用n个1来代表长度，n为二进制编码的长度
            OffsetLength += "1";
        }
        OffsetLength += "0";//使用0作为结束标识

        GammaCode = OffsetLength + Binary;
        return GammaCode;
    }

    static int GammaDecode(String GammaCode) {
        int OffsetLength=0,n;
        String offset="";//定义空字符串存放偏移
        for (int i = 0; i < GammaCode.length(); i++) {//循环处理
            //当gammacode【i】为非结束位“0”时，继续检查下一位并记录长度
            if (GammaCode.charAt(i)=='0'){
                OffsetLength=i+1;
                break;
                //当遇到结束位0时，停止循环
            }
        }

        int flag=OffsetLength;
        for (int i = 0; i < OffsetLength-1; i++) {
            offset+=GammaCode.charAt(i+flag);
        }

        offset="1"+offset;//加回之前删除的开头的“1”
        n=Integer.parseInt(offset,2);
        return n;
    }

    public static void main(String[] args) {

        String GammaCode=GammaEncode(13);
        System.out.print("编码后的数字为：");
        System.out.println(GammaCode);

        System.out.print("解码后的编码为：");
        int n=GammaDecode(GammaCode);
        System.out.println(n);
    }
}

public class GammaCode {

    static String GammaEncode(int n) {
        String Binary = Integer.toBinaryString(n);
        String offset = "", OffsetLength = "";
        String GammaCode;
        if (Binary.length() == 1) {
            offset = "";
        } else {
            Binary = Binary.substring(1);
        }

        for (int i = 0; i < Binary.length(); i++) {
            OffsetLength += "1";
        }
        OffsetLength += "0";

        GammaCode = OffsetLength + Binary;
        return GammaCode;
    }

    static int GammaDecode(String GammaCode) {
        int OffsetLength,n;
        String offset="";
        for (int i = 0; i < GammaCode.length(); i++) {
            if (GammaCode.charAt(i)=='0'){
                offset=GammaCode.substring(i+1);
                OffsetLength=i+1;
                break;
            }
        }
        offset="1"+offset;
        n=Integer.parseInt(offset,2);
        return n;
    }

    public static void main(String[] args) {
        String GammaCode=GammaEncode(13);
        System.out.println(GammaCode);

        int n=GammaDecode(GammaCode);
        System.out.println(n);
    }
}

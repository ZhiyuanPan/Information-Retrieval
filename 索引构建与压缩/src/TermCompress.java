import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class TermCompress {
    static HashMap index = new HashMap<String, Integer>();//使用hashmap存放token，键为单词，值为其出现的次数
    static HashSet stopword = new HashSet<String>();

    static void Indexing(String str) {
        String word = new String("");
        int flag = -1;
        for (int i = 0; i < str.length(); i++) {
            //在已经获取review部分遍历，进行句子拆分时（已经转换小写）
            //设置标志变量，当当前字符为一个单词中的首个小写字母，即flag=0且str【i】为小写字符
            //将之后连续的小写字母都加入单词组成并保存
            //在英文处理中，应该注意 ' （分隔符号）应该被算入一个单词中
            //最终将所得的单词存入haspmap中
            if ((int) str.charAt(i) >= 97 && (int) str.charAt(i) <= 122 || str.charAt(i)=='\'') {
                word += str.charAt(i);
                flag = 1;
            } else if (flag == 1 && (str.charAt(i) < 97 || str.charAt(i) > 122)) {
                flag = -1;
                if (index.containsKey(word)) {
                    index.put(word, (int) index.get(word) + 1);
                }
                else {//每次加入新单词时，先检测该单词时候存在于stopword中
                    //如果存在，则取消加入
                    if(!stopword.contains(word)){
                        index.put(word, 1);
                    }
                }
                word = "";
            }
        }

    }

    static void getStopWord() {
        try {//按行读取stopword文档并存入stopword的hashset中
            String filename = new String("C:\\Users\\49141\\Desktop\\IR\\Information-Retrieval\\索引构建与压缩\\stopwords.txt");
            FileReader f = new FileReader(filename);
            BufferedReader in = new BufferedReader(f);
            String str;
            while ((str = in.readLine()) != null) {
                stopword.add(str);
            }
        } catch (IOException e) {
            System.out.println("读取错误");
        }
    }

    static void getReviewAndBulid() {
        try {
            //该函数用于获取review标识字段
            //首先通过java读入文档
            String filename = new String("C:\\Users\\49141\\Desktop\\IR\\Information-Retrieval\\索引构建与压缩\\Software.txt");
            FileReader f = new FileReader(filename);
            BufferedReader in = new BufferedReader(f);

            //依次按行读入字符串，设置标识变量
            //当字符串的前12个字符为review/text时，改变标识变量，开始记录，直到遇到空行，再次修改标识变量
            //在读取每行的过程中，需要将句子转为小写，每换行都要与前几行所得句子相连
            String str;
            int flag = -1;
            String review = new String("");
            while ((str = in.readLine()) != null) {
                if (str.length() == 0) {
                    flag = -1;
                    review = review.substring(13).toLowerCase();
                    Indexing(review);
                    review = "";
                    continue;
                }
                if (str.substring(0, 11).equals("review/text")) {
                    flag = 1;
                }
                if (flag == 1) {
                    review += str;
                }
            }
        } catch (IOException e) {
            System.out.println("读取错误");
        }
    }

    static void IfHeapsLaws(int token){
        //通过Heap's Laws由当前获得的token的数量估算term的数量
        double min=30*Math.sqrt(token);
        System.out.println("当k取30，b取0.5时，根据HEAP'S LAWS估计得term的数目："+min);

        double max=100*Math.sqrt(token);
        System.out.println("当k取100，b取0.5时，根据HEAP'S LAWS估计得term的数目："+max);
    }

    static void IfZipfLaws(){//使用遍历先找出最大值，然后对排名2-10的值依次进行屏蔽
        // 每次获取屏蔽最大i个值之后的局部最大值
        int max=0;
        for(int i=0;i<10;i++){
            String str=new String();
            if(i==0){
                Iterator iter = index.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object val = entry.getValue();
                    if((int)val>max){
                        max=(int)val;
                        str=(String)entry.getKey();
                    }
                }
                System.out.println(max+" "+str);
            }
            else{
                int maxStr=0;
                Iterator iter = index.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object val = entry.getValue();
                    if((int)val<max && (int)val>maxStr){
                        maxStr=(int)val;
                        str=(String)entry.getKey();
                    }
                }
                max=maxStr;
                System.out.println(max+" "+str);
            }
        }
    }

    public static void main(String[] args) {

        getStopWord();

        getReviewAndBulid();

        Iterator iter = index.entrySet().iterator();

        int i = 0, j=0;
        while (iter.hasNext()) {
            i++;
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            j=j+(int)val;
            System.out.println(key + "," + val);
        }
        System.out.println("得到的token个数是："+j);
        System.out.println("得到的term个数是："+i);


        IfHeapsLaws(j);

        IfZipfLaws();
    }
}

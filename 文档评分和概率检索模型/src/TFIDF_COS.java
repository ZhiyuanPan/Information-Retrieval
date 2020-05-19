import java.io.*;
import java.util.*;
public class TFIDF_COS {
    static LinkedList<String> words=new LinkedList<>();
    public static void main(String[] args) {
        String fileName="C:\\Users\\49141\\Desktop\\IR\\文档评分和概率检索模型\\Doc";
        for(int i=1;i<=10;i++) {
            String s = fileName + i + ".txt";
            Build(s,i);
        }
    }
    static void Build(String s, int i){
        try {
            String Filename=new String(s);    //得到的文件名
            FileReader f=new FileReader(Filename);    //使用filereader指定文件
            BufferedReader in=new BufferedReader(f);    //读取文件
            String str;
            while((str=in.readLine())!=null){
                String database[];
                database=str.split(" ");    //split方法返回的数组存入自定义string数组中
                for(String term:database){
                    System.out.println(term);
                }
            }
        }catch (IOException e){
            System.out.println("Error");
        }
    }
}

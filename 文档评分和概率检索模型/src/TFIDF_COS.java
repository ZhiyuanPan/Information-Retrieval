import java.io.*;
import java.util.*;

public class TFIDF_COS {
    static HashMap<String,int []>words=new HashMap<>();
    static int [] NumberOFWords=new int[11];
    public static void main(String[] args) {
        String fileName="C:\\Users\\49141\\Desktop\\IR\\Information-Retrieval\\文档评分和概率检索模型\\Doc";
        for(int i=1;i<=10;i++) {
            NumberOFWords[i]=0;
            String s = fileName + i + ".txt";
            Build(s,i);
        }
        Scanner in=new Scanner(System.in);
        System.out.println("请输入需要计算的两个文档的编号：");
        int fir=in.nextInt();
        int sec=in.nextInt();

        cosine_similarity(fir,sec);
    }
    static public void cosine_similarity(int a1,int a2){
        String fileName="C:\\Users\\49141\\Desktop\\IR\\Information-Retrieval\\文档评分和概率检索模型\\Doc";
        String s1=fileName + a1 + ".txt";
        String s2=fileName + a2 + ".txt";
        String[] artical1=ReadFile(s1);
        String[] artical2=ReadFile(s2);

        LinkedList<String> s=new LinkedList<>();


        for (int i = 0; i < artical1.length; i++) {
            if(!s.contains(artical1[i])){
                s.add(artical1[i].toLowerCase());
            }
        }
        for (int i = 0; i < artical2.length; i++) {
            if(!s.contains(artical2[i])){
                s.add(artical2[i].toLowerCase());
            }
        }
        double [] D1=new double[s.size()];
        double [] D2=new double[s.size()];

        for (int i = 0; i < s.size(); i++) {
            D1[i]=TF_IDF(a1,s.get(i));
            D2[i]=TF_IDF(a2,s.get(i));
        }

        double sum=0;
        for (int i = 0; i < s.size(); i++) {
            sum+=(D1[i]*D2[i]);
        }
        double valueCOS=sum/LengthOfVector(D1)/LengthOfVector(D2);
        System.out.println("余弦相似度为："+String.format("%.2f", valueCOS));
    }

    static double LengthOfVector(double []a){
        double sum=0;
        for (int i = 0; i < a.length; i++) {
            sum+=(a[i]*a[i]);
        }
        return Math.sqrt(sum);
    }

    static String[] ReadFile(String s){
        String[] database=new String[20];
        try {
            String Filename=new String(s);    //得到的文件名
            FileReader f=new FileReader(Filename);    //使用filereader指定文件
            BufferedReader in=new BufferedReader(f);    //读取文件
            String str;
            while((str=in.readLine())!=null){
                database=str.split(" ");    //split方法返回的数组存入自定义string数组中
            }
        }catch (IOException e){
            System.out.println("Error!");
        }
        return database;
    }

    static public double TF_IDF(int i,String w){
        float frequency=words.get(w)[i];
        float tf=frequency/NumberOFWords[i];

        int []array=words.get(w);
        int present=0;
        for(int j=1;j<=10;j++){
            if(array[j]!=0){
                present++;
            }
        }
        double idf=Math.log10(10/present);
        return tf*idf;
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
                    NumberOFWords[i]++;
                    String w=term.toLowerCase();
                    if(words.containsKey(w)){
                        words.get(w)[i]++;
                    }
                    else{
                        int []array=new int[11];
                        words.put(w,array);
                        words.get(w)[i]++;
                    }
                }
            }
        }catch (IOException e){
            System.out.println("Error!");
        }
    }
}

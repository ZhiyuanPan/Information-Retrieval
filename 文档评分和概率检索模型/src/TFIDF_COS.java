import java.io.*;
import java.util.*;

public class TFIDF_COS {
    static HashMap<String,int []>words=new HashMap<>();
    static int [] NumberOFWords=new int[11];
    public static void main(String[] args) {
        String fileName="C:\\Users\\49141\\Desktop\\IR\\Information-Retrieval\\文档评分和概率检索模型\\Doc";//定义保存路径
        for(int i=1;i<=10;i++) {//使用循环依次将doc1-doc10读入
            NumberOFWords[i]=0;//初始化数组计算每一个文档的单词的数量
            String s = fileName + i + ".txt";
            Build(s,i);
        }
        Scanner in=new Scanner(System.in);//输入要比较的两个文档的编号
        System.out.println("请输入需要比较的两个文档的编号：");
        int fir=in.nextInt();
        int sec=in.nextInt();

        cosine_similarity(fir,sec);//使用cosine_similarity函数对两个文档进行计算
    }
    static public void cosine_similarity(int a1,int a2){
        String fileName="C:\\Users\\49141\\Desktop\\IR\\Information-Retrieval\\文档评分和概率检索模型\\Doc";
        String s1=fileName + a1 + ".txt";
        String s2=fileName + a2 + ".txt";
        String[] artical1=ReadFile(s1);
        String[] artical2=ReadFile(s2);//再次读取文档并分别保存到string数组Artical1与artical2中。

        LinkedList<String> s=new LinkedList<>();//创建链表，该链表用于将两个文档中出现过的单词提取作为一个集合用于后续计算


        for (int i = 0; i < artical1.length; i++) {//将两个文档中的单词不重复的加入链表建立有序集合
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
        double [] D2=new double[s.size()];//定义两个double数组，作为保存分量TF-IDF值的数据结构

        for (int i = 0; i < s.size(); i++) {//计算TF-IDF值
            D1[i]=TF_IDF(a1,s.get(i));
            D2[i]=TF_IDF(a2,s.get(i));
        }

        double sum=0;
        for (int i = 0; i < s.size(); i++) {//计算其分子，分子为每一个同位置分量相乘得到的总和
            sum+=(D1[i]*D2[i]);
        }
        double valueCOS=sum/(LengthOfVector(D1)*LengthOfVector(D2));
        System.out.println("余弦相似度为："+String.format("%.2f", valueCOS));//输出
    }

    static double LengthOfVector(double []a){
        double sum=0;
        for (int i = 0; i < a.length; i++) {//得到每一个分量平方的总和
            sum+=(a[i]*a[i]);
        }
        return Math.sqrt(sum);//对其进行开方操作并返回
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
        float frequency=words.get(w)[i];//首先获取该单词在对应文档中出现过的次数
        float tf=frequency/NumberOFWords[i];//然后将其除以该文档中单词的总个数得到TF值

        int []array=words.get(w);//获取单词相应文档的数组
        int present=0;
        for(int j=1;j<=10;j++){//使用循环统计单词在所有的文档中出现过的次数
            if(array[j]!=0){
                present++;
            }
        }
        double idf=Math.log10(10/present+1);//将其被加一10除以并取对数，获得其IDF值
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
                    NumberOFWords[i]++;//对应的文档单词数量加一
                    String w=term.toLowerCase();//将单词转换为小写
                    if(words.containsKey(w)){//如果非新单词，则将对应的数组中的文档出现次数加一
                        words.get(w)[i]++;
                    }
                    else{
                        int []array=new int[11];//否则为其创建新数组并将对应文档中单词频率设置为1
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

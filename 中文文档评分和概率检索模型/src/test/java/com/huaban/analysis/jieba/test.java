package com.huaban.analysis.jieba;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

import junit.framework.TestCase;

import org.junit.Test;

import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
public class test {
    static HashMap<String,int []>words=new HashMap<>();
    static int [] NumberOFWords=new int[11];
    static public JiebaSegmenter segmenter = new JiebaSegmenter();
    static String []Doc= {
            "",
            "信息检索（Information Retrieval）是用户进行信息查询和获取的主要方式，是查找信息的方法和手段。",
            "面对日益纷繁复杂的网络信息，你是否感觉检索和获取信息有些力不从心?",
            "文献检索（Information Retrieval）是指根据学习和工作的需要获取文献的过程",
            "信息检索（Information Retrieval）指信息按一定的方式组织起来,并根据信息用户的需要找出有关的信息的过程和技术。",
            "通过人工智能为您提供个性化内容推荐，智能push推送服务。精准推荐信息流和产品页，提升用户粘性和转化率。",
            "推荐系统通过分析用户的行为，找到用户的个性化需求，从而将一些长尾商品个性化推荐给相应的用户。",
            "推荐系统是利用电子商务网站向客户提供商品信息和建议，帮助用户决定应该购买什么产品，模拟销售人员帮助客户完成购买过程。",
            "推荐系统是信息过滤系统的一个子类,它根据用户的偏好和行为,来向用户呈现他（或她）可能感兴趣的物品。",
            "机器学习是一门多领域交叉学科，涉及概率论、统计学、逼近论、凸分析、算法复杂度理论等多门学科。",
            "机器学习是人工智能的一个分支。人工智能的研究历史有着一条从以“推理”为重点，到以“知识”为重点，再到以“学习”为重点的自然、清晰的脉络。"
    };

    public static void main(String[] args) {
        for (int i = 1; i <= 10 ; i++) {
            Build(i);
        }
        Scanner in=new Scanner(System.in);
        System.out.println("请输入需要计算的两个文档的编号：");
        int fir=in.nextInt();
        int sec=in.nextInt();

        cosine_similarity(fir,sec);
    }

    static String[] ReadFile(int index){
        String str=segmenter.sentenceProcess(Doc[index]).toString();
        str=str.substring(1,str.length()-1);
        str=str.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
        String[] database=str.split("\\s+");
        return database;
    }

    static public void cosine_similarity(int a1,int a2){
        LinkedList<String> s=new LinkedList<>();
        String[] artical1=ReadFile(a1);
        String[] artical2=ReadFile(a2);

        for (int i = 0; i < artical1.length; i++) {
            if(!s.contains(artical1[i])){
                s.add(artical1[i]);
            }
        }
        for (int i = 0; i < artical2.length; i++) {
            if(!s.contains(artical2[i])){
                s.add(artical2[i]);
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

    static void Build(int index){
        String str=segmenter.sentenceProcess(Doc[index]).toString();
        str=str.substring(1,str.length()-1);
        str=str.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");

        String[] database=str.split("\\s+");
        for(String term:database){
            NumberOFWords[index]++;
            String w=term.toLowerCase();
            if(words.containsKey(w)){
                words.get(w)[index]++;
            }
            else{
                int []array=new int[11];
                words.put(w,array);
                words.get(w)[index]++;
            }
        }
    }
}

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
        for (int i = 1; i <= 10 ; i++) {//使用循环依次将string 1-10读入
            Build(i);
        }
        System.out.println();
        Scanner in=new Scanner(System.in);
        System.out.println("请输入需要计算的两个文档的编号：");//输入要比较的两个文档的编号
        int fir=in.nextInt();
        int sec=in.nextInt();

        cosine_similarity(fir,sec);//使用cosine_similarity函数对两个String进行计算
    }

    static String[] ReadFile(int index){
        String str=segmenter.sentenceProcess(Doc[index]).toString();//使用结巴分词得到拆分后文档中单词
        str=str.substring(1,str.length()-1);//去除结巴分词加入的[]符号
        //过滤一切标点符号
        str=str.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
        String[] database=str.split("\\s+");//以一个或多个空格拆分
        return database;
    }

    static public void cosine_similarity(int a1,int a2){
        LinkedList<String> s=new LinkedList<>();
        String[] artical1=ReadFile(a1);
        String[] artical2=ReadFile(a2);//再次读取文档并分别保存到string数组Artical1与artical2中。

        for (int i = 0; i < artical1.length; i++) {//将两个文档中的单词不重复的加入链表建立有序集合
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
        double [] D2=new double[s.size()];//定义两个double数组，作为保存分量TF-IDF值的数据结构

        for (int i = 0; i < s.size(); i++) {//计算TF-IDF值
            D1[i]=TF_IDF(a1,s.get(i));
            D2[i]=TF_IDF(a2,s.get(i));
        }

        double sum=0;
        for (int i = 0; i < s.size(); i++) {//计算其分子，分子为每一个同位置分量相乘得到的总和
            sum+=(D1[i]*D2[i]);
        }
        double valueCOS=sum/LengthOfVector(D1)/LengthOfVector(D2);
        System.out.println("余弦相似度为："+String.format("%.2f", valueCOS));//输出
    }

    static double LengthOfVector(double []a){
        double sum=0;
        for (int i = 0; i < a.length; i++) {//得到每一个分量平方的总和
            sum+=(a[i]*a[i]);
        }
        return Math.sqrt(sum);//对其进行开方操作并返回
    }

    static public double TF_IDF(int i,String w){
        w=w.toLowerCase();//遇到英文单词时需要转换成小写
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

    static void Build(int index){
        String str=segmenter.sentenceProcess(Doc[index]).toString();//使用结巴分词得到拆分后文档中单词
        str=str.substring(1,str.length()-1);//去除结巴分词加入的[]符号
        //过滤一切标点符号
        str=str.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");

        String[] database=str.split("\\s+");//split方法返回的数组存入自定义string数组中
        for(String term:database){
            NumberOFWords[index]++;//对应的文档单词数量加一
            String w=term.toLowerCase();//如果是英文单词，将单词转换为小写，中文时函数默认不处理
            if(words.containsKey(w)){//如果非新单词，则将对应的数组中的文档出现次数加一
                words.get(w)[index]++;
            }
            else{
                int []array=new int[11];//否则为其创建新数组并将对应文档中单词频率设置为1
                words.put(w,array);
                words.get(w)[index]++;
            }
        }
    }
}

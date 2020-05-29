package com.huaban.analysis.jieba;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

import junit.framework.TestCase;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;

import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

public class main {
    static public JiebaSegmenter segmenter = new JiebaSegmenter();
    static public HashMap<String, Double> words = new HashMap<String, Double>();
    static public HashSet<String> Computer = new HashSet<>();
    static public HashSet<String> Internation = new HashSet<>();
    static public HashSet<String> Science = new HashSet<>();
    static public HashSet<String> Student = new HashSet<>();
    static public HashSet<String> Teaching = new HashSet<>();

    public static void main(String[] args) {
        BulidSet();
        
    }

    static void BulidSet() {
        for (int i = 1; i <= 30; i++) {
            String internation = "C:\\Users\\49141\\Desktop\\IR\\Information-Retrieval\\分类和聚类的实验\\国际交流与合作部\\doc";
            internation += (i + ".txt");
            try {
                FileReader f = new FileReader(internation);    //使用filereader指定文件
                BufferedReader in = new BufferedReader(f);    //读取文件

                String str;
                while ((str = in.readLine()) != null) {
                    str = segmenter.sentenceProcess(str).toString();//使用结巴分词得到拆分后文档中单词
                    str = str.substring(1, str.length() - 1);//去除结巴分词加入的[]符号
                    str = str.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
                    String[] database = str.split("\\s+");//split方法返回的数组存入自定义string数组中
                    for (String term : database) {
                        if (term.equals("")) {
                            continue;
                        }
                        if (!Internation.contains(term)) {
                            Internation.add(term);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error!");
            }
        }

        for (int i = 1; i <= 30; i++) {
            String computer = "C:\\Users\\49141\\Desktop\\IR\\Information-Retrieval\\分类和聚类的实验\\计算机与软件学院\\doc";
            computer += i + ".txt";
            try {
                FileReader f = new FileReader(computer);    //使用filereader指定文件
                BufferedReader in = new BufferedReader(f);    //读取文件
                String str;
                while ((str = in.readLine()) != null) {
                    str = segmenter.sentenceProcess(str).toString();//使用结巴分词得到拆分后文档中单词
                    str = str.substring(1, str.length() - 1);//去除结巴分词加入的[]符号
                    str = str.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
                    String[] database = str.split("\\s+");//split方法返回的数组存入自定义string数组中
                    for (String term : database) {
                        if (term.equals("")) {
                            continue;
                        }
                        if (!Computer.contains(term)) {
                            Computer.add(term);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error!");
            }
        }

        for (int i = 1; i <= 30; i++) {
            String student = "C:\\Users\\49141\\Desktop\\IR\\Information-Retrieval\\分类和聚类的实验\\学生部\\doc";
            student += i + ".txt";
            try {
                FileReader f = new FileReader(student);    //使用filereader指定文件
                BufferedReader in = new BufferedReader(f);    //读取文件
                String str;
                while ((str = in.readLine()) != null) {
                    str = segmenter.sentenceProcess(str).toString();//使用结巴分词得到拆分后文档中单词
                    str = str.substring(1, str.length() - 1);//去除结巴分词加入的[]符号
                    str = str.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
                    String[] database = str.split("\\s+");//split方法返回的数组存入自定义string数组中
                    for (String term : database) {
                        if (term.equals("")) {
                            continue;
                        }
                        if (!Student.contains(term)) {
                            Student.add(term);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error!");
            }
        }

        for (int i = 1; i <= 30; i++) {
            String science = "C:\\Users\\49141\\Desktop\\IR\\Information-Retrieval\\分类和聚类的实验\\科学技术部\\doc";
            science += i + ".txt";
            try {
                FileReader f = new FileReader(science);    //使用filereader指定文件
                BufferedReader in = new BufferedReader(f);    //读取文件
                String str;
                while ((str = in.readLine()) != null) {
                    str = segmenter.sentenceProcess(str).toString();//使用结巴分词得到拆分后文档中单词
                    str = str.substring(1, str.length() - 1);//去除结巴分词加入的[]符号
                    str = str.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
                    String[] database = str.split("\\s+");//split方法返回的数组存入自定义string数组中
                    for (String term : database) {
                        if (term.equals("")) {
                            continue;
                        }
                        if (!Science.contains(term)) {
                            Science.add(term);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error!");
            }
        }

        for (int i = 1; i <= 30; i++) {
            String teaching = "C:\\Users\\49141\\Desktop\\IR\\Information-Retrieval\\分类和聚类的实验\\教务部\\doc";
            teaching += i + ".txt";
            try {
                FileReader f = new FileReader(teaching);    //使用filereader指定文件
                BufferedReader in = new BufferedReader(f);    //读取文件
                String str;
                while ((str = in.readLine()) != null) {
                    str = segmenter.sentenceProcess(str).toString();//使用结巴分词得到拆分后文档中单词
                    str = str.substring(1, str.length() - 1);//去除结巴分词加入的[]符号
                    str = str.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
                    String[] database = str.split("\\s+");//split方法返回的数组存入自定义string数组中
                    for (String term : database) {
                        if (term.equals("")) {
                            continue;
                        }
                        if (!Teaching.contains(term)) {
                            Teaching.add(term);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error!");
            }
        }
    }

    static int IfInDOCUMENT(String word, String s) {
        HashSet<String> WordsSet = new HashSet<>();
        try {
            FileReader f = new FileReader(s);    //使用filereader指定文件
            BufferedReader in = new BufferedReader(f);    //读取文件
            String str;
            while ((str = in.readLine()) != null) {
                str = segmenter.sentenceProcess(str).toString();//使用结巴分词得到拆分后文档中单词
                str = str.substring(1, str.length() - 1);//去除结巴分词加入的[]符号
                str = str.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
                String[] database = str.split("\\s+");//split方法返回的数组存入自定义string数组中
                for (String term : database) {
                    if (term.equals("")) {
                        continue;
                    }
                    WordsSet.add(term);
                }
            }
        } catch (IOException e) {
            System.out.println("Error!");
        }
        if (WordsSet.contains(word)) {
            return 1;
        }
        return 0;
    }
}

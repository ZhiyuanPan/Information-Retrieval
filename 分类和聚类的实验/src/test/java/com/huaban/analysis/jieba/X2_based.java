package com.huaban.analysis.jieba;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class X2_based {
    static public JiebaSegmenter segmenter = new JiebaSegmenter();
    static public HashMap<String, Double> Computerwords = new HashMap<String, Double>();
    static public HashMap<String, Double> Internationwords = new HashMap<String, Double>();
    static public HashMap<String, Double> Sciencewords = new HashMap<String, Double>();
    static public HashMap<String, Double> Studentwords = new HashMap<String, Double>();
    static public HashMap<String, Double> Teachingwords = new HashMap<String, Double>();

    static public HashSet<String>[] Computer = new HashSet[31];
    static public HashSet<String>[] Internation = new HashSet[31];
    static public HashSet<String>[] Science = new HashSet[31];
    static public HashSet<String>[] Student = new HashSet[31];
    static public HashSet<String>[] Teaching = new HashSet[31];


    public static class record {
        public String word;
        public double f;

        public record(String w, double ff) {
            word = w;
            f = ff;
        }
    }

    public static void main(String[] args) {
        BulidSet();
        ClassComputer();
        System.out.println();
        ClassIntenation();
        System.out.println();
        ClassStudent();
        System.out.println();
        ClassTeaching();
        System.out.println();
        ClassScience();
    }

    static double Log2(double a) {
        return Math.log(a) / Math.log(2);
    }

    static void ClassComputer() {
        double N1 = 0, N2 = 0, N3 = 0, N4 = 0, N5 = 0, N = 150;
        HashSet<String> ALLComputer = new HashSet<>();
        for (int i = 1; i <= 30; i++) {
            for (String str : Computer[i]) {
                if (!ALLComputer.contains(str)) {
                    ALLComputer.add(str);
                }
            }
        }
        for (String str : ALLComputer) {
            N1 = 0;
            N2 = 0;
            N3 = 0;
            N4 = 0;
            N5 = 0;
            N = 150;
            for (int i = 1; i <= 30; i++) {
                if (Computer[i].contains(str)) {
                    N1++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Internation[i].contains(str)) {
                    N2++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Student[i].contains(str)) {
                    N3++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Science[i].contains(str)) {
                    N4++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Teaching[i].contains(str)) {
                    N5++;
                }
            }
            double N11 = N1, N01 = 30 - N1, N10 = N2 + N3 + N4 + N5, N00 = 120 - (N2 + N3 + N4 + N5);
            double X2 = (N11+N10+N01+N00)*(N11*N00-N10*N01)*(N11*N00-N10*N01)/((N11+N01)*(N11+N10)*(N10+N00)*(N01+N00));
            Computerwords.put(str, X2);
        }

        record[] a = new record[15];

        for (int i = 0; i < 15; i++) {
            double max = 0;
            String maxword = "";
            for (String word : ALLComputer) {
                if (Computerwords.containsKey(word) && (double) Computerwords.get(word) > max) {
                    max = (double) Computerwords.get(word);
                    Computerwords.remove(word);
                    maxword = word;
                }
            }
            record b = new record(maxword, max);
            a[i] = b;
        }
        for (int i = 0; i < 15; i++) {
            System.out.println(a[i].word + " " + a[i].f);
        }
    }

    static void ClassIntenation() {
        double N1 = 0, N2 = 0, N3 = 0, N4 = 0, N5 = 0, N = 150;
        HashSet<String> ALLInternation = new HashSet<>();
        for (int i = 1; i <= 30; i++) {
            for (String str : Internation[i]) {
                if (!ALLInternation.contains(str)) {
                    ALLInternation.add(str);
                }
            }
        }
        for (String str : ALLInternation) {
            N1 = 0;
            N2 = 0;
            N3 = 0;
            N4 = 0;
            N5 = 0;
            N = 150;
            for (int i = 1; i <= 30; i++) {
                if (Internation[i].contains(str)) {
                    N1++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Computer[i].contains(str)) {
                    N2++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Student[i].contains(str)) {
                    N3++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Science[i].contains(str)) {
                    N4++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Teaching[i].contains(str)) {
                    N5++;
                }
            }
            double N11 = N1, N01 = 30 - N1, N10 = N2 + N3 + N4 + N5, N00 = 120 - (N2 + N3 + N4 + N5);
            double X2 = (N11+N10+N01+N00)*(N11*N00-N10*N01)*(N11*N00-N10*N01)/((N11+N01)*(N11+N10)*(N10+N00)*(N01+N00));
            Internationwords.put(str, X2);
        }

        record[] a = new record[15];

        for (int i = 0; i < 15; i++) {
            double max = 0;
            String maxword = "";
            for (String word : ALLInternation) {
                if (Internationwords.containsKey(word) && (double) Internationwords.get(word) > max) {
                    max = (double) Internationwords.get(word);
                    Internationwords.remove(word);
                    maxword = word;
                }
            }
            record b = new record(maxword, max);
            a[i] = b;
        }
        for (int i = 0; i < 15; i++) {
            System.out.println(a[i].word + " " + a[i].f);
        }
    }

    static void ClassStudent() {
        double N1 = 0, N2 = 0, N3 = 0, N4 = 0, N5 = 0, N = 150;
        HashSet<String> ALLStudent = new HashSet<>();
        for (int i = 1; i <= 30; i++) {
            for (String str : Student[i]) {
                if (!ALLStudent.contains(str)) {
                    ALLStudent.add(str);
                }
            }
        }
        for (String str : ALLStudent) {
            N1 = 0;
            N2 = 0;
            N3 = 0;
            N4 = 0;
            N5 = 0;
            N = 150;
            for (int i = 1; i <= 30; i++) {
                if (Student[i].contains(str)) {
                    N1++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Computer[i].contains(str)) {
                    N2++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Internation[i].contains(str)) {
                    N3++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Science[i].contains(str)) {
                    N4++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Teaching[i].contains(str)) {
                    N5++;
                }
            }
            double N11 = N1, N01 = 30 - N1, N10 = N2 + N3 + N4 + N5, N00 = 120 - (N2 + N3 + N4 + N5);
            double X2 = (N11+N10+N01+N00)*(N11*N00-N10*N01)*(N11*N00-N10*N01)/((N11+N01)*(N11+N10)*(N10+N00)*(N01+N00));
            Studentwords.put(str, X2);
        }

        record[] a = new record[15];

        for (int i = 0; i < 15; i++) {
            double max = 0;
            String maxword = "";
            for (String word : ALLStudent) {
                if (Studentwords.containsKey(word) && (double) Studentwords.get(word) > max) {
                    max = (double) Studentwords.get(word);
                    Studentwords.remove(word);
                    maxword = word;
                }
            }
            record b = new record(maxword, max);
            a[i] = b;
        }
        for (int i = 0; i < 15; i++) {
            System.out.println(a[i].word + " " + a[i].f);
        }
    }

    static void ClassTeaching() {
        double N1 = 0, N2 = 0, N3 = 0, N4 = 0, N5 = 0, N = 150;
        HashSet<String> ALLTeaching = new HashSet<>();
        for (int i = 1; i <= 30; i++) {
            for (String str : Teaching[i]) {
                if (!ALLTeaching.contains(str)) {
                    ALLTeaching.add(str);
                }
            }
        }
        for (String str : ALLTeaching) {
            N1 = 0;
            N2 = 0;
            N3 = 0;
            N4 = 0;
            N5 = 0;
            N = 150;
            for (int i = 1; i <= 30; i++) {
                if (Teaching[i].contains(str)) {
                    N1++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Computer[i].contains(str)) {
                    N2++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Internation[i].contains(str)) {
                    N3++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Science[i].contains(str)) {
                    N4++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Student[i].contains(str)) {
                    N5++;
                }
            }
            double N11 = N1, N01 = 30 - N1, N10 = N2 + N3 + N4 + N5, N00 = 120 - (N2 + N3 + N4 + N5);
            double X2 = (N11+N10+N01+N00)*(N11*N00-N10*N01)*(N11*N00-N10*N01)/((N11+N01)*(N11+N10)*(N10+N00)*(N01+N00));
            Teachingwords.put(str, X2);
        }

        record[] a = new record[15];

        for (int i = 0; i < 15; i++) {
            double max = 0;
            String maxword = "";
            for (String word : ALLTeaching) {
                if (Teachingwords.containsKey(word) && (double) Teachingwords.get(word) > max) {
                    max = (double) Teachingwords.get(word);
                    Teachingwords.remove(word);
                    maxword = word;
                }
            }
            record b = new record(maxword, max);
            a[i] = b;
        }
        for (int i = 0; i < 15; i++) {
            System.out.println(a[i].word + " " + a[i].f);
        }
    }

    static void ClassScience() {
        double N1 = 0, N2 = 0, N3 = 0, N4 = 0, N5 = 0, N = 150;
        HashSet<String> ALLScience = new HashSet<>();
        for (int i = 1; i <= 30; i++) {
            for (String str : Science[i]) {
                if (!ALLScience.contains(str)) {
                    ALLScience.add(str);
                }
            }
        }
        for (String str : ALLScience) {
            N1 = 0;
            N2 = 0;
            N3 = 0;
            N4 = 0;
            N5 = 0;
            N = 150;
            for (int i = 1; i <= 30; i++) {
                if (Science[i].contains(str)) {
                    N1++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Computer[i].contains(str)) {
                    N2++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Internation[i].contains(str)) {
                    N3++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Teaching[i].contains(str)) {
                    N4++;
                }
            }
            for (int i = 1; i <= 30; i++) {
                if (Student[i].contains(str)) {
                    N5++;
                }
            }
            double N11 = N1, N01 = 30 - N1, N10 = N2 + N3 + N4 + N5, N00 = 120 - (N2 + N3 + N4 + N5);
            double X2 = (N11+N10+N01+N00)*(N11*N00-N10*N01)*(N11*N00-N10*N01)/((N11+N01)*(N11+N10)*(N10+N00)*(N01+N00));
            Sciencewords.put(str, X2);
        }

        record[] a = new record[15];

        for (int i = 0; i < 15; i++) {
            double max = 0;
            String maxword = "";
            for (String word : ALLScience) {
                if (Sciencewords.containsKey(word) && (double) Sciencewords.get(word) > max) {
                    max = (double) Sciencewords.get(word);
                    Sciencewords.remove(word);
                    maxword = word;
                }
            }
            record b = new record(maxword, max);
            a[i] = b;
        }
        for (int i = 0; i < 15; i++) {
            System.out.println(a[i].word + " " + a[i].f);
        }
    }

    static void BulidSet() {
        for (int i = 1; i <= 30; i++) {
            Computer[i] = new HashSet<>();
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
                        if (!Computer[i].contains(term)) {
                            Computer[i].add(term);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error!");
            }
        }

        for (int i = 1; i <= 30; i++) {
            String internation = "C:\\Users\\49141\\Desktop\\IR\\Information-Retrieval\\分类和聚类的实验\\国际交流与合作部\\doc";
            Internation[i] = new HashSet<>();
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
                        if (!Internation[i].contains(term)) {
                            Internation[i].add(term);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error!");
            }
        }

        for (int i = 1; i <= 30; i++) {
            String student = "C:\\Users\\49141\\Desktop\\IR\\Information-Retrieval\\分类和聚类的实验\\学生部\\doc";
            Student[i] = new HashSet<>();
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
                        if (!Student[i].contains(term)) {
                            Student[i].add(term);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error!");
            }
        }

        for (int i = 1; i <= 30; i++) {
            String science = "C:\\Users\\49141\\Desktop\\IR\\Information-Retrieval\\分类和聚类的实验\\科学技术部\\doc";
            Science[i] = new HashSet<>();
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
                        if (!Science[i].contains(term)) {
                            Science[i].add(term);
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
            Teaching[i] = new HashSet<>();
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
                        if (!Teaching[i].contains(term)) {
                            Teaching[i].add(term);
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

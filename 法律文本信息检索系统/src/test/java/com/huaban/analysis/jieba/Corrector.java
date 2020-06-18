package com.huaban.analysis.jieba;

import java.util.HashMap;
import java.util.HashSet;

public class Corrector {
    public JiebaSegmenter segmenter = new JiebaSegmenter();
    public HashSet<String> WordsSet = new HashSet<>();
    public HashMap<String, Integer> WordFrequencyInAll = new HashMap<>();


    public int Distance(String word1, String word2) {
        int len1 = word1.length(), len2 = word2.length();
        int[][] matrix = new int[len1 + 1][len2 + 1];//定义数组存放每一个片段的距离并动态规划
        for (int j = 0; j <= len2; j++) {
            matrix[0][j] = j;
        }
        for (int i = 0; i <= len1; i++) {
            matrix[i][0] = i;
        }//格式控制

        for (int i = 1; i <= len1; i++) {//动态规划算法，循环遍历i，j
            for (int j = 1; j <= len2; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    matrix[i][j] = Math.min(Math.min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1), matrix[i - 1][j - 1]);
                }//当当前位置的两个字符数据相等时，计算达到当前位置的最小值
                //当左上角值为当前元素的最小值时，且当前位置与左上角数据对应字符串相等，可以直接使用左上角值
                //否则从左方数据或上方数据取最小值
                // 进行一步操作得到当前字符串，即加一
                else {
                    matrix[i][j] = Math.min(Math.min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1), matrix[i - 1][j - 1] + 1);
                }//当当前位置的两个字符数据不相等时，计算达到当前位置的最小值
                //因为左上方的值对应的字符串与当前字符串不相等，所以从左方数据，左上方数据或上方数据取最小值
                // 进行一步操作
                // 得到当前字符串，即加一
            }
        }
        return matrix[word1.length()][word2.length()];
        //矩阵最右下角的值即为两个单词的最小距离，return
    }

    public String[] StrProcess(String S) {
        String str = segmenter.sentenceProcess(S).toString();//使用结巴分词得到拆分后文档中单词
        str = str.substring(1, str.length() - 1);//去除结巴分词加入的[]符号
        //过滤一切标点符号
        str = str.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
        String[] database = str.split("\\s+");//以一个或多个空格拆分
        return database;
    }

    public String[] InputCorrect(String input, HashMap<String, Integer> wordFrequencyInAll_Reset, HashSet<String> wordsSet) {
        WordFrequencyInAll = wordFrequencyInAll_Reset;
        WordsSet = wordsSet;
        String[] database = StrProcess(input);
        for (int i = 0; i < database.length; i++) {
            if (database[i].length() >= 2 && WordFrequencyInAll.containsKey(database[i])) {
                continue;
            }
            int MaxFrequency = 0;
            String change = "";
            for (String str : WordsSet) {
                if (str.equals("") || str.equals("\"") || str.equals("\u0001") || str.length() <= 1) {
                    continue;
                }
                //不对长度为1的词纠错
                if (database[i].length() <= 1) {
                    continue;
                }
                //选出频率最高的编辑距离为1的词进行替换。
                if (Distance(database[i], str) == 1 && WordFrequencyInAll.get(str) > MaxFrequency) {
                    change = str;
                }
            }
            System.out.print("纠错：" + database[i] + "--->" + change + "   ");
            database[i] = change;
        }
        System.out.println("");
        return database;
    }

    public void InputRecommand(String input, HashMap<String, HashSet<Integer>> DF_t, HashSet<String> wordsSet) {
        //主要使用编辑距离算法与集合取交集的算法。
        String[] database = StrProcess(input);
        for (int i = 0; i < database.length; i++) {
            if (database[i].length() <= 1) {
                continue;
            }
            for (String str : WordsSet) {
                if (str.equals("") || str.equals("\"") || str.equals("\u0001") || str.length() <= 1) {
                    continue;
                }
                if (WordFrequencyInAll.get(str) >= 3 && Distance(database[i], str) <= 3) {
                //对两个词的出现过的集合取交集，即计算两个词出现在过几个相同的文档。
                    if (DF_t.containsKey(database[i]) && DF_t.containsKey(str)) {
                        HashSet t1 = DF_t.get(database[i]);
                        HashSet t2 = DF_t.get(str);
                        t1.retainAll(t2);
                        if (t1.size() < 3) {
                            continue;
                        }
                    }
                    System.out.print("根据：" + database[i] + "，为您推荐:" + str + "   ");
                }
            }
        }
        System.out.println("");
    }
}

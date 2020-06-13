package com.huaban.analysis.jieba;

import java.util.*;

public class GlobalDocumentSearch {
    public JiebaSegmenter segmenter = new JiebaSegmenter();
    public String input;
    public HashMap<Integer, Case> CaseMap = new HashMap<>();
    public HashMap<Integer, String> CaseList = new HashMap<>();
    public HashMap<String, Integer> WordFrequencyInAll = new HashMap<>();
    public HashMap<Integer, HashMap<String, Integer>> TF_td = new HashMap<>();
    public HashMap<String, HashSet<Integer>> DF_t = new HashMap<>();
    public int DocumentSize;

    public void GlobalSearch(String str, HashMap<Integer, Case> C) {
        input = str;
        CaseMap = C;
        DocumentSize = C.size();
        getDocument();
        Construct();
    }

    public void Construct() {
        for (int i = 0; i <= DocumentSize; i++) {
            String str = CaseList.get(i);
            String[] database = StrProcess(str);
            for (String s : database) {
                if (WordFrequencyInAll.containsKey(s)) {
                    WordFrequencyInAll.put(s, WordFrequencyInAll.get(s) + 1);

                    if (TF_td.containsKey(i)) {
                        if (TF_td.get(i).containsKey(s)) {
                            TF_td.get(i).put(s, TF_td.get(i).get(s) + 1);
                        } else {
                            TF_td.get(i).put(s, 1);
                        }
                    } else {
                        HashMap<String, Integer> t = new HashMap<>();
                        t.put(s,1);
                        TF_td.put(i,t);
                    }
                    
                    if (DF_t.containsKey(s)) {
                        DF_t.get(s).add(i);
                    } else {
                        HashSet<Integer> t = new HashSet<>();
                        t.add(i);
                        DF_t.put(s, t);
                    }
                } else {
                    WordFrequencyInAll.put(s, 1);

                    HashMap<String, Integer> k = new HashMap<>();
                    k.put(s, 1);
                    TF_td.put(i, k);

                    HashSet<Integer> t = new HashSet<>();
                    t.add(i);
                }
            }
        }
    }

    public String[] StrProcess(String S) {
        String str = segmenter.sentenceProcess(S).toString();//使用结巴分词得到拆分后文档中单词
        str = str.substring(1, str.length() - 1);//去除结巴分词加入的[]符号
        //过滤一切标点符号
        str = str.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
        String[] database = str.split("\\s+");//以一个或多个空格拆分
        return database;
    }

    public void getDocument() {
        CaseList.put(0, input);
        for (int i = 1; i <= DocumentSize; i++) {
            CaseList.put(i, CaseMap.get(i).toString());
        }
    }
}

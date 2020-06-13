package com.huaban.analysis.jieba;

import java.util.*;

public class GlobalDocumentSearch {
    public JiebaSegmenter segmenter = new JiebaSegmenter();
    public HashMap<Integer, Case> CaseMap = new HashMap<>();
    public HashMap<Integer, String> CaseList = new HashMap<>();
    public HashMap<String, Integer> WordFrequencyInAll = new HashMap<>();
    public HashMap<Integer, HashMap<String, Integer>> TF_td = new HashMap<>();
    public HashMap<String, HashSet<Integer>> DF_t = new HashMap<>();
    public HashMap<Integer, LinkedList<Double>> Doc_TFIDF = new HashMap<>();
    public HashMap<Integer, Double> VectorLength = new HashMap<>();
    public LinkedList<String> InputString_terms = new LinkedList<>();
    public int DocumentSize;

    public void HashMapConstruct(HashMap<Integer, Case> C) {
        CaseMap = C;
        DocumentSize = C.size();
        getDocument();
        Construct();
        LengthCaculator();
    }

    public void GlobalSearch(String input) {
        StringAdd(input);
        System.out.println(cosine_similarity(0));
    }

    public double cosine_similarity(int DocID) {
        LinkedList<String> t = new LinkedList();
        for (int i = 0; i < InputString_terms.size(); i++) {
            if (TF_td.get(DocID).containsKey(InputString_terms.get(i))) {
                t.add(InputString_terms.get(i));
            }
        }
        double cos = 0;
        for (int i = 0; i < t.size(); i++) {
            cos += TF_IDF(0, t.get(i)) * TF_IDF(DocID, t.get(i));
        }
        double CosValue=cos / (VectorLength.get(0) * VectorLength.get(DocID));
        if(CosValue>1){
            CosValue=1;
        }
        return CosValue;
    }

    public void StringAdd(String input) {
        double vectorLength = 0;
        CaseList.put(0, input);
        String[] database = StrProcess(input);
        for (String s : database) {
            InputString_terms.add(s);
            if (WordFrequencyInAll.containsKey(s)) {
                WordFrequencyInAll.put(s, WordFrequencyInAll.get(s) + 1);
            } else {
                WordFrequencyInAll.put(s, 1);
            }

            if (TF_td.containsKey(0)) {
                if (TF_td.get(0).containsKey(s)) {
                    TF_td.get(0).put(s, TF_td.get(0).get(s) + 1);
                } else {
                    TF_td.get(0).put(s, 1);
                }
            } else {
                HashMap<String, Integer> t = new HashMap<>();
                t.put(s, 1);
                TF_td.put(0, t);
            }

            if (DF_t.containsKey(s)) {
                DF_t.get(s).add(0);
            } else {
                HashSet<Integer> t = new HashSet<>();
                t.add(0);
                DF_t.put(s, t);
            }

            double tf_idf = TF_IDF(0, s);
            vectorLength += (tf_idf * tf_idf);
        }
        VectorLength.put(0, Math.sqrt(vectorLength));
    }

    public void LengthCaculator() {
        for (int i = 1; i < DocumentSize; i++) {
            double vectorLength = 0;
            String S = CaseList.get(i);
            String[] database = StrProcess(S);
            for (String s : database) {
                double tf_idf = TF_IDF(i, s);
                vectorLength += (tf_idf * tf_idf);
            }
            VectorLength.put(i, Math.sqrt(vectorLength));
        }
    }

    public double TF_IDF(int DocID, String term) {
        double TF = 1 + (Math.log10(TF_td.get(DocID).get(term)));
        double IDF = Math.log10(DocumentSize / (DF_t.get(term).size()));
        return TF * IDF;
    }

    public void Construct() {
        for (int i = 1; i <= DocumentSize; i++) {
            String str = CaseList.get(i);
            String[] database = StrProcess(str);
            for (String s : database) {
                if (WordFrequencyInAll.containsKey(s)) {
                    WordFrequencyInAll.put(s, WordFrequencyInAll.get(s) + 1);
                } else {
                    WordFrequencyInAll.put(s, 1);
                }

                if (TF_td.containsKey(i)) {
                    if (TF_td.get(i).containsKey(s)) {
                        TF_td.get(i).put(s, TF_td.get(i).get(s) + 1);
                    } else {
                        HashMap<String, Integer> t = TF_td.get(i);
                        t.put(s, 1);
                        TF_td.put(i, t);
                    }
                } else {
                    HashMap<String, Integer> t = new HashMap<>();
                    t.put(s, 1);
                    TF_td.put(i, t);
                }
                if (DF_t.containsKey(s)) {
                    DF_t.get(s).add(i);
                } else {
                    HashSet<Integer> t = new HashSet<>();
                    t.add(i);
                    DF_t.put(s, t);
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
        for (int i = 1; i <= DocumentSize; i++) {
            CaseList.put(i, CaseMap.get(i).toString());
        }
    }
}

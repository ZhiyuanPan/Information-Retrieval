package com.huaban.analysis.jieba;

import java.util.*;

public class DocumentSearch {
    //WordSet：所有文档中出现过的每一个单词
    //WordFrequencyInAll：所有文档中出现过的任意单词、其在所有文档中出现的总次数
    //TF_td：文档编号、每一个文档中出现过的所有单词、在该文档中出现的次数
    //DF_t：每一个单词、该单词在所有文档中出现过的文档的编号
    //建立三个变量ifGlobalSearch，ifFacesSearch，ifAccusationsSearch用于标识全局搜索，按犯罪事实搜索和按罪名搜索。
    public boolean ifGlobalSearch = false;
    public boolean ifFactsSearch = false;
    public boolean ifAccusaationsSearch = false;
    public HashSet<String> WordsSet = new HashSet<>();
    public JiebaSegmenter segmenter = new JiebaSegmenter();
    public HashMap<Integer, Case> CaseMap = new HashMap<>();
    public HashMap<Integer, String> CaseList = new HashMap<>();
    public HashMap<String, Integer> WordFrequencyInAll = new HashMap<>();
    public HashMap<String, Integer> WordFrequencyInAll_Reset = new HashMap<>();
    public HashMap<Integer, HashMap<String, Integer>> TF_td = new HashMap<>();
    public HashMap<Integer, HashMap<String, Integer>> TF_td_Reset = new HashMap<>();
    public HashMap<String, HashSet<Integer>> DF_t = new HashMap<>();
    public HashMap<String, HashSet<Integer>> DF_t_Reset = new HashMap<>();
    public HashMap<Integer, Double> VectorLength = new HashMap<>();
    public LinkedList<String> InputString_terms = new LinkedList<>();
    public HashMap<Integer, Double> result = new HashMap<>();
    public int DocumentSize;

    public void System_Reset() {
        ifGlobalSearch = false;
        ifFactsSearch = false;
        ifAccusaationsSearch = false;
        WordFrequencyInAll = WordFrequencyInAll_Reset;
        TF_td = TF_td_Reset;
        DF_t = DF_t_Reset;
        InputString_terms.clear();
    }

    public void HashMapConstruct(HashMap<Integer, Case> C) {
        CaseMap = C;
        DocumentSize = C.size();
        if (ifGlobalSearch) {
            getDocuments();
        } else if (ifFactsSearch) {
            getFacts();
        } else if (ifAccusaationsSearch) {
            getAccusations();
        }
        Construct();
        LengthCaculator();
    }

    public void Search(String input) {
        StringAdd(input);
        for (int i = 1; i < DocumentSize; i++) {
            result.put(i, cosine_similarity(i));
        }
        for (int i = 0; i < 10; i++) {
            double max = 0;
            int flag = 0;
            for (int j = 1; j < DocumentSize; j++) {
                if (max < result.get(j)) {
                    max = result.get(j);
                    flag = j;
                }
            }
            result.put(flag, (double) 0);
            //当i=0即第一次循环获得的max值就为0时，说明没有返回结果，此时直接结束程序并输出信息
            if(max==0 && i==0){
                System.out.println("您输入的查询没有结果，请尝试推荐词条或重新输入。");
                return;
            }
            if (max == 0) {
                System.out.println();
                System.out.println("----------以上为所有搜索结果----------");
                return;
            }
            Print(i, flag);
        }
        System_Reset();
        System.out.println();
        System.out.println();
    }

    public void Print(int i, int flag) {
        System.out.println("结果" + (i + 1) + "： 第" + flag + "号文档");
        System.out.println("   犯罪事实：" + CaseMap.get(flag).fact);
        System.out.println("   相关法律条文：" + CaseMap.get(flag).relevant_articles + "   罪名：" + CaseMap.get(flag).accusation
                + "   罚金：" + CaseMap.get(flag).punish_of_money + "   罪犯：" + CaseMap.get(flag).criminals);
        System.out.println("   死刑：" + CaseMap.get(flag).death_penalty + "   监禁：" + CaseMap.get(flag).imprisonment
                + "   终身监禁：" + CaseMap.get(flag).life_imprisonment);
        System.out.println();
    }

    public double cosine_similarity(int DocID) {
        LinkedList<String> t = new LinkedList();
        for (int i = 0; i < InputString_terms.size(); i++) {
            if (TF_td.get(DocID).containsKey(InputString_terms.get(i))) {
                t.add(InputString_terms.get(i));
            }
        }
        if (t.isEmpty()) {
            return 0;
        }
        double cos = 0;
        for (int i = 0; i < t.size(); i++) {
            cos += TF_IDF(0, t.get(i)) * TF_IDF(DocID, t.get(i));
        }
        double CosValue = cos / (VectorLength.get(0) * VectorLength.get(DocID));
        if (CosValue > 1) {
            CosValue = 1;
        }
        return CosValue;
    }

    public void StringAdd(String input) {//将输入的搜索文本加入各张表中
        double vectorLength = 0;
        CaseList.put(0, input);
        Corrector corrector = new Corrector();
        String[] database = corrector.InputCorrect(input, WordFrequencyInAll_Reset, WordsSet);
        corrector.InputRecommand(input, DF_t, WordsSet);
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
                vectorLength += Math.sqrt(tf_idf * tf_idf);
            }
            VectorLength.put(i, Math.sqrt(vectorLength));
        }
    }

    public double TF_IDF(int DocID, String term) {//计算获得特定某个词的TF-IDF值
        double TF = 1 + (Math.log10(TF_td.get(DocID).get(term)));
        double IDF = Math.log10(DocumentSize / (DF_t.get(term).size()));
        return TF * IDF;
    }

    public void Construct() {
        for (int i = 1; i <= DocumentSize; i++) {
            //先获取第i个文档中的文本，分此后对于得到的每一个单词进行加入表的操作
            String str = CaseList.get(i);
            String[] database = StrProcess(str);
            for (String s : database) {
                WordsSet.add(s);//加入WordsSet
                //如果WordFrequencyInAll以及包含该单词，则将该单词的频率次数加一；
                // 否则直接将单词加入表中并将出现频率设为1
                if (WordFrequencyInAll.containsKey(s)) {
                    WordFrequencyInAll.put(s, WordFrequencyInAll.get(s) + 1);
                } else {
                    WordFrequencyInAll.put(s, 1);
                }

                if (TF_td.containsKey(i)) {
                    //如果该单词已经包含在TF_td内且单词以及出现在第i号文档，则将其所在文档编号的出现次数加一；
                    if (TF_td.get(i).containsKey(s)) {
                        TF_td.get(i).put(s, TF_td.get(i).get(s) + 1);
                    } else {//如果该单词已经包含在TF_td内但没在第i号文档中出现过，则为其建立新Hashmap并加入；
                        HashMap<String, Integer> t = TF_td.get(i);
                        t.put(s, 1);
                        TF_td.put(i, t);
                    }
                } else {//如果该单词不包含在TF_td内，为其创建HashMap保存单词字符串并将出现次数设为1加入TF_td中：
                    HashMap<String, Integer> t = new HashMap<>();
                    t.put(s, 1);
                    TF_td.put(i, t);
                }

                //如果单词在总文档中出现过，将其出现的文档编号加入到已有Set
                if (DF_t.containsKey(s)) {
                    DF_t.get(s).add(i);
                } else {//否则建立新Set保存单词与出现的文档编号。
                    HashSet<Integer> t = new HashSet<>();
                    t.add(i);
                    DF_t.put(s, t);
                }
            }
        }
        //结束时，需要将得到的结果保存，用于搜索时重置表时无需重新建表。
        WordFrequencyInAll_Reset = WordFrequencyInAll;
        TF_td_Reset = TF_td;
        DF_t_Reset = DF_t;
    }

    public String[] StrProcess(String S) {
        String str = segmenter.sentenceProcess(S).toString();//使用结巴分词得到拆分后文档中单词
        str = str.substring(1, str.length() - 1);//去除结巴分词加入的[]符号
        //过滤一切标点符号
        str = str.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
        String[] database = str.split("\\s+");//以一个或多个空格拆分
        return database;
    }

    public void getDocuments() {//使用全部文本进行建表，最终得到的结果也是犯罪事实中的文本
        for (int i = 1; i <= DocumentSize; i++) {
            CaseList.put(i, CaseMap.get(i).toString());
        }
    }

    public void getFacts() {//只使用犯罪事实进行建表，最终得到的结果也是犯罪事实中的文本
        for (int i = 1; i <= DocumentSize; i++) {
            CaseList.put(i, CaseMap.get(i).fact);
        }
    }

    public void getAccusations() {//只使用罪名进行建表，最终得到的结果也是犯罪事实中的文本
        for (int i = 1; i <= DocumentSize; i++) {
            CaseList.put(i, CaseMap.get(i).accusation);
        }
    }
}

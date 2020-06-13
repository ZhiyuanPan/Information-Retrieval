package com.huaban.analysis.jieba;

import java.util.*;

import junit.framework.TestCase;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

public class Accusation_statistics {
    public JiebaSegmenter segmenter = new JiebaSegmenter();
    public HashMap<String, String[]> Accusation_data = new HashMap<>();
    public HashMap<String, Integer> Accusation_number = new HashMap<>();
    public int NumberOFToken=0;
    public int NumberOFTerm=0;
    public void Statistics(HashMap<Integer, Case> CaseMap, HashSet<String> AccusationSet) {
        HashMap<String, HashSet<String>> Accusation = new HashMap<>();
        for (int i = 1; i <= CaseMap.size(); i++) {
            if (!Accusation.containsKey(CaseMap.get(i).accusation)) {
                NumberOFTerm++;NumberOFToken++;
                HashSet<String> Set = new HashSet<>();
                Set.add(CaseMap.get(i).fact);
                Accusation.put(CaseMap.get(i).accusation, Set);
            } else {
                NumberOFToken++;
                Accusation.get(CaseMap.get(i).accusation).add(CaseMap.get(i).fact);
            }
        }

        for (String accusation : AccusationSet) {
            HashSet<String> FactsSet = Accusation.get(accusation);
            HashMap<String, Integer> TermMap = new HashMap<>();

            Accusation_number.put(accusation, FactsSet.size());

            for (String str : FactsSet) {
                str = segmenter.sentenceProcess(str).toString();//使用结巴分词得到拆分后文档中单词
                str = str.substring(1, str.length() - 1);//去除结巴分词加入的[]符号
                str = str.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
                String[] database = str.split("\\s+");//split方法返回的数组存入自定义string数组中
                for (String term : database) {
                    if (term.equals("")) {
                        continue;
                    }
                    if (!TermMap.containsKey(term)) {
                        TermMap.put(term, 1);
                    } else {
                        TermMap.put(term, TermMap.get(term) + 1);
                    }
                }
            }
            String biggest10[] = getBiggest10(TermMap);
            Accusation_data.put(accusation, biggest10);
        }
        print(AccusationSet);
    }

    public String[] getBiggest10(HashMap TermMap) {
        String[] biggest10 = new String[10];
        List<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(TermMap.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return ( o1.getValue()-o2.getValue());
            }
        });
        for (int i = 0; i < 10; i++) {
            Map.Entry<String,Integer> ent=infoIds.get(i);
            biggest10[i]=ent.getKey();
        }
        return biggest10;
    }

    public void print(HashSet<String> AccusationSet) {
        for (String accusation : AccusationSet) {
            System.out.println("罪名：" + accusation + "       文档个数：" +Accusation_number.get(accusation));
            System.out.println("token数量："+NumberOFToken+"   term数量："+NumberOFTerm);
            System.out.print("Top10高频词汇：");
            String[] f=Accusation_data.get(accusation);
            for (int i = 0; i <10 ; i++) {
                System.out.print(i+1+". "+f[i]+"  ");
            }
            System.out.println();
            System.out.println();
        }
    }
}

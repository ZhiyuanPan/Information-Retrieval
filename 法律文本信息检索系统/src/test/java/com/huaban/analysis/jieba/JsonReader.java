package com.huaban.analysis.jieba;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class JsonReader {
    public HashSet<String> Accusation_Set=new HashSet<>();

    public HashSet<String> getAccusation_Set(){
        return Accusation_Set;
    }

    public HashMap<Integer,Case> ReadJson(){
        HashMap<Integer,Case> CaseMap=new HashMap<>();
        try {
            int MapSize = 1;
            String Filename = new String("data1.json");    //得到的文件名
            FileReader f = new FileReader(Filename);    //使用filereader指定文件
            BufferedReader in = new BufferedReader(f);    //读取文件
            String str;
            while ((str = in.readLine()) != null) {
                JSONObject jsonObject = JSON.parseObject(str);
                String fact = (String) jsonObject.get("fact");
                String relevant_articles = jsonObject.getJSONObject("meta").get("relevant_articles").toString();
                String accusation = jsonObject.getJSONObject("meta").get("accusation").toString();
                int punish_of_money = (int) jsonObject.getJSONObject("meta").get("punish_of_money");
                String criminals = jsonObject.getJSONObject("meta").get("criminals").toString();
                boolean death_penalty = (boolean)jsonObject.getJSONObject("meta").getJSONObject("term_of_imprisonment").get("death_penalty");
                int imprisonment = (int) jsonObject.getJSONObject("meta").getJSONObject("term_of_imprisonment").get("imprisonment");
                boolean life_imprisonment = (boolean) jsonObject.getJSONObject("meta").getJSONObject("term_of_imprisonment").get("life_imprisonment");

                Case A=new Case(MapSize, fact, relevant_articles, accusation, punish_of_money, criminals, death_penalty, imprisonment, life_imprisonment);
                Accusation_Set.add(accusation);
                CaseMap.put(MapSize,A);
                MapSize++;
            }
        } catch (IOException e) {
            System.out.println("Error");
            return CaseMap;
        }
        return CaseMap;
    }
}

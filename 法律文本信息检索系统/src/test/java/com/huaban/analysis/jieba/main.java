package com.huaban.analysis.jieba;

import java.io.*;
import java.util.*;

public class main {
    public static HashMap<Integer,Case> CaseMap=new HashMap<>();
    public static void main(String[] args) throws IOException {
        JsonReader Json=new JsonReader();
        CaseMap=Json.ReadJson();
//        Accusation_statistics acc=new Accusation_statistics();
//        acc.Statistics(CaseMap,Json.getAccusation_Set());

        GlobalDocumentSearch GlobalSearch=new GlobalDocumentSearch();
        GlobalSearch.HashMapConstruct(CaseMap);
        Scanner in=new Scanner(System.in);
        System.out.print("请输入：");
        String input=in.nextLine();
        GlobalSearch.GlobalSearch(input);
    }
}

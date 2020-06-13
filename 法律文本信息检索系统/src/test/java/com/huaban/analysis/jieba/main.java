package com.huaban.analysis.jieba;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import junit.framework.TestCase;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

import static java.lang.Double.NaN;
import static java.lang.Double.isNaN;

import org.apache.commons.io.FileUtils;

public class main {
    public static HashMap<Integer,Case> CaseMap=new HashMap<>();
    public static void main(String[] args) throws IOException {
        JsonReader Json=new JsonReader();
        CaseMap=Json.ReadJson();
//        Accusation_statistics acc=new Accusation_statistics();
//        acc.Statistics(CaseMap,Json.getAccusation_Set());
        Scanner in=new Scanner(System.in);
        System.out.println("请输入：");
        String str=in.nextLine();
        GlobalDocumentSearch GlobalSearch=new GlobalDocumentSearch();
        GlobalSearch.GlobalSearch(str,CaseMap);
    }
}

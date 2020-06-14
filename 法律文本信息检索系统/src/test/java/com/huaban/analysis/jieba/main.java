package com.huaban.analysis.jieba;

import java.io.*;
import java.util.*;

public class main {
    public static HashMap<Integer, Case> CaseMap = new HashMap<>();
    static JsonReader Json = new JsonReader();

    public static void main(String[] args) throws IOException {
        System.out.println("----------系统数据初始化中...----------");
        System_Data_Initialize();

        while (true) {
            System.out.println("欢迎使用法律文本检索系统！");
            System.out.println("   1.统计");
            System.out.println("   2.搜索");
            System.out.println("   0.退出");
            System.out.print("请选择所需要使用的功能：");
            Scanner Function = new Scanner(System.in);
            int c = Function.nextInt();
            if (c == 0) {
                return;
            } else if (c == 1) {
                Statistics_System();
            } else if (c == 2) {
                Search_System();
            } else {
                System.out.println("输入错误！请尝试重新输入或退出程序。");
            }
        }
    }

    static public void System_Data_Initialize() {
        System.out.println();
        CaseMap = Json.ReadJson();
    }

    static public void Statistics_System() {
        Accusation_statistics acc = new Accusation_statistics();
        acc.Statistics(CaseMap, Json.getAccusation_Set());
        System.out.println("请按回车键以返回主菜单");
        Scanner Function = new Scanner(System.in);
        String c = Function.nextLine();
        System.out.println("----------------------------------------------------------");
    }

    static public void Search_System() {
        DocumentSearch Search = new DocumentSearch();
        Search.HashMapConstruct(CaseMap);
        while (true) {
            System.out.println("----------------------------------------------------------");
            System.out.println("   1.全局搜索");
            System.out.println("   2.犯罪事实搜索");
            System.out.println("   3.罪名搜索");
            System.out.println("   0.返回上一级菜单");

            System.out.print("请选择所需要使用的搜索方式：");
            Scanner function = new Scanner(System.in);
            int c = function.nextInt();
            if (c == 0) {
                return;
            } else if (c == 1) {
                Search.ifGlobalSearch = true;
                Scanner in = new Scanner(System.in);
                System.out.print("请输入：");
                String input = in.nextLine();
                Search.Search(input);
                Search.System_Reset();
            } else if (c == 2) {
                Search.ifFactsSearch = true;
                Scanner in = new Scanner(System.in);
                System.out.print("请输入：");
                String input = in.nextLine();
                Search.Search(input);
                Search.System_Reset();
            } else if (c == 3) {
                Search.ifAccusaationslSearch = true;
                Scanner in = new Scanner(System.in);
                System.out.print("请输入：");
                String input = in.nextLine();
                Search.Search(input);
                Search.System_Reset();
            }
        }
    }
}

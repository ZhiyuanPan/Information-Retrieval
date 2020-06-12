package com.huaban.analysis.jieba;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class Case {
    int DocID;
    String fact;
    String relevant_articles;
    String accusation;
    int punish_of_money;
    String criminals;
    boolean death_penalty;
    int imprisonment;
    boolean life_imprisonment;

    public Case(int id, String f, String ra, String acc, int pom, String cri, boolean dp, int imp, boolean li) {
        DocID = id;
        fact = f;
        relevant_articles = ra;
        accusation = acc;
        punish_of_money = pom;
        criminals = cri;
        death_penalty = dp;
        imprisonment = imp;
        life_imprisonment = li;
    }

    public int getDocID(){
        return DocID;
    }

    public String getFact() {
        return fact;
    }

    public String getRelevant_articles() {
        return relevant_articles;
    }

    public String getAccusation() {
        return accusation;
    }

    public int getPunish_of_money() {
        return punish_of_money;
    }

    public String getCriminals() {
        return criminals;
    }

    public boolean getDeath_penalty() {
        return death_penalty;
    }

    public int getImprisonment() {
        return imprisonment;
    }

    public boolean getLife_imprisonment() {
        return life_imprisonment;
    }
}

import com.sun.source.tree.Tree;

import java.io.*;
import java.util.*;

public class main2 {
    static TreeMap Keyword=new TreeMap<Integer,TreeMap>();

    static class AnswerList{
        int DocID;
        int position1;
        int position2;
        AnswerList(){}
        AnswerList(int a,int b,int c){
            DocID=a;position1=b;position2=c;
        }
        public int getDocID(){
            return DocID;
        }

        public int getPosition1() {
            return position1;
        }

        public int getPosition2() {
            return position2;
        }

    }

    public static void main(String[] args) {
        String fileName="C:\\Users\\49141\\Desktop\\IR\\Information-Retrieval\\基于跳表指针的倒排记录表合并算法和邻近搜索\\doc";
        for(int i=1;i<=10;i++) {
            String s = fileName + i + ".txt";
            SplitAndBulid(s, i);
        }
        ShowTreemap();
        for(int i=0;i<3;i++){
            ProximitySearch();
        }
    }
    public static void SplitAndBulid(String s,int i){
        try {
            String Filename= s;    //得到的文件名
            FileReader f=new FileReader(Filename);    //使用filereader指定文件
            BufferedReader in=new BufferedReader(f);    //读取文件

            String str;

            while((str=in.readLine())!=null){
                String[] database;
                database=str.split(" ");
                for(int j=0;j<database.length;j++){
                    if(!Keyword.containsKey(database[j])){
                        LinkedList Position=new LinkedList<Integer>();
                        Position.add(j);
                        TreeMap PositionList=new TreeMap<Integer,LinkedList<Integer>>();
                        PositionList.put(i,Position);
                        Keyword.put(database[j],PositionList);
                    }
                    else{
                        TreeMap temp= (TreeMap) Keyword.get(database[j]);

                        if(!temp.containsKey(i)){
                            LinkedList Position=new LinkedList();
                            Position.add(j);
                            temp.put(i,Position);
                        }
                        else{
                            LinkedList Position=new LinkedList<Integer>();
                            Position= (LinkedList) temp.get(i);
                            System.out.println(Position);
                            Position.add(j);
                        }
                    }
                }
            }
        }catch (IOException e){
            System.out.println("Split/Built Error");
        }
    }
    public static void ShowTreemap(){
        Iterator iter=Keyword.keySet().iterator();

        while(iter.hasNext()){
            String key=(String)iter.next();
            System.out.println("单词: "+key +" 出现在: ");

            TreeMap temp= (TreeMap) Keyword.get(key);
            Iterator iter1=temp.keySet().iterator();
            while(iter1.hasNext()){
                int key1=(int)iter1.next();
                LinkedList list= (LinkedList) temp.get(key1);
                int len=list.size();
                System.out.print("DOC"+key1+"中"+ len +"次，"+"分布于第: ");

                for(int i=0;i<list.size();i++){
                    int p=(int)list.get(i)+1;
                    System.out.print(p+" ");
                }
                System.out.println("位");

            }
            System.out.println();
        }
    }

    public static void ProximitySearch(){
        Scanner input=new Scanner(System.in);
        String word1,word2,word3;
        int distance;
        System.out.print("请输入要进行邻近搜索的两个单词与距离：");
        word1=input.next();
        word2=input.next();
        distance=input.nextInt();

        TreeMap data1= (TreeMap) Keyword.get(word1);
        TreeMap data2= (TreeMap) Keyword.get(word2);
        LinkedList Answer=new LinkedList<AnswerList>();

        for(int i=1;i<=10;i++){
            if(data1.containsKey(i) && data2.containsKey(i)){
                LinkedList WordData1= (LinkedList) data1.get(i);
                LinkedList WordData2= (LinkedList) data2.get(i);

                for(int point1=0;point1<WordData1.size();point1++){
                    for(int point2=0;point2<WordData2.size();point2++){
                        if(Math.abs((int)WordData2.get(point2)-(int)WordData1.get(point1))<=Math.abs(distance)){
                            if(distance>0 && (int)WordData1.get(point1)<(int)WordData2.get(point2)){
                                AnswerList a=new AnswerList(i,(int)WordData1.get(point1)+1,(int)WordData2.get(point2)+1);
                                Answer.add(a);
                            }
                            else if(distance<0 && (int)WordData1.get(point1)>(int)WordData2.get(point2)){
                                AnswerList a=new AnswerList(i,(int)WordData1.get(point1)+1,(int)WordData2.get(point2)+1);
                                Answer.add(a);
                            }
                        }
                    }
                }
            }
        }

        if(Answer.isEmpty()){
            System.out.println("符合条件的查找不存在，或请检查输入单词的大小写是否符合。");
            System.out.println();
            return;
        }
        else{
            for(int i=0;i<Answer.size();i++){
                AnswerList output= (AnswerList) Answer.get(i);
                System.out.println("DocID="+output.getDocID()+ " 第一个单词的位置是："+output.getPosition1()+", 第二个单词的位置是："+output.getPosition2());
            }
            System.out.println();
        }

    }


}

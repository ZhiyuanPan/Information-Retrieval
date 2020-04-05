import com.sun.source.tree.Tree;

import java.io.*;
import java.util.*;

public class main2 {
    static TreeMap Keyword=new TreeMap<Integer,TreeMap>();//多层数据结构，详见实验报告中数据结构图

    static class AnswerList{
        //结果需要用到一个数据结构保存三元数值，三个数据中，DocID对应文档编号，
        // Position1对应单词一在该文档编号下的位置，Position2对应单词二在该文档编号下的位置
        int DocID;
        int position1;
        int position2;
        AnswerList(){}//无参构造函数
        AnswerList(int a,int b,int c){
            DocID=a;position1=b;position2=c;
        }//构造函数
        public int getDocID(){
            return DocID;
        }

        public int getPosition1() {
            return position1;
        }

        public int getPosition2() {
            return position2;
        }
        //三个get方法用于获取类中的三个数据
    }

    public static void main(String[] args) {
        String fileName="C:\\Users\\49141\\Desktop\\IR\\Information-Retrieval\\基于跳表指针的倒排记录表合并算法和邻近搜索\\doc";
        for(int i=1;i<=10;i++) {//拆分文档中的每一个单词并构建倒排索引，主要代码段在SplitAndBulid方法中
            String s = fileName + i + ".txt";
            SplitAndBulid(s, i);
        }
        ShowTreemap();
        for(int i=0;i<3;i++){
            ProximitySearch();//该函数为建立完成含位置信息的索引表后，邻近搜索算法函数
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
                //创建倒排索引表时，文档中第一个单词编号为0，输出倒排索引表时，第一个单词编号为1
                //单词拆分完成存入Treemap结构时存在三种情况
                //1.单词在Keyword的键中找不到，为第一次检索到的单词
                //2.单词在Keyword的键中可以找到，但是在当前文档中第一次检索到该单词
                //3.单词在Keyword的键中可以找到，在当前文档中已经检索到过该单词
                for(int j=0;j<database.length;j++){
                    //单词在Keyword的键中找不到，为第一次检索到的单词；
                    //为当前检索到的新单词创建文档编号链表，并记录在文档中的位置并添加当文档编号链表
                    //将文档标号与位置构成的数据结构加入到倒排索引表中
                    if(!Keyword.containsKey(database[j])){
                        LinkedList Position=new LinkedList<Integer>();
                        Position.add(j);
                        TreeMap PositionList=new TreeMap<Integer,LinkedList<Integer>>();
                        PositionList.put(i,Position);
                        Keyword.put(database[j],PositionList);
                    }

                    //单词在Keyword的键中可以找到
                    else{
                        TreeMap temp= (TreeMap) Keyword.get(database[j]);
                        //在当前文档中第一次检索到该单词
                        //为当前文档创建链表，并记录在文档中的位置并添加当文档编号链表
                        if(!temp.containsKey(i)){
                            LinkedList Position=new LinkedList();
                            Position.add(j);
                            temp.put(i,Position);
                        }
                        //当前文档中已经检索到过该单词，直接将位置信息加入到对应链表即可
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
    public static void ShowTreemap(){//对每个检索到的单词的出现次数，其中的文档编号和位置进行输出
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
        TreeMap data2= (TreeMap) Keyword.get(word2);//获取输入的单词中对应的出现位置集合
        LinkedList Answer=new LinkedList<AnswerList>();

        for(int i=1;i<=10;i++){//对文档1-10中每一个进行比对，查看是否存在单词一、二都出现过的文档
            if(data1.containsKey(i) && data2.containsKey(i)){//当单词一、二都出现在i号文档时
                LinkedList WordData1= (LinkedList) data1.get(i);
                LinkedList WordData2= (LinkedList) data2.get(i);//分别获取两个单词出现的文档中具体位置

                //使用双层循环分别将单词一在该文档中出现的位置与单词二在文档中出现的位置进行遍历比对
                //单词一的位置1与单词二位置1比对，单词一的位置1与单词二位置2比对...直到超出长度
                for(int point1=0;point1<WordData1.size();point1++){
                    for(int point2=0;point2<WordData2.size();point2++){
                        if(Math.abs((int)WordData2.get(point2)-(int)WordData1.get(point1))<=Math.abs(distance)){
                            if(distance>0 && (int)WordData1.get(point1)<(int)WordData2.get(point2)){
                                AnswerList a=new AnswerList(i,(int)WordData1.get(point1)+1,(int)WordData2.get(point2)+1);
                                Answer.add(a);
                                if((int)WordData2.get(point2)-(int)WordData1.get(point1)>distance){
                                    break;
                                }//当指针二中的值已经大于指针一的值，且超出距离时，不必在向右寻找，直接进行下一轮循环，
                                // 因为加大指针二的值只会离指针一越来越远
                            }

                            else if(distance<0 && (int)WordData1.get(point1)>(int)WordData2.get(point2)){
                                AnswerList a=new AnswerList(i,(int)WordData1.get(point1)+1,(int)WordData2.get(point2)+1);
                                Answer.add(a);
                                if((int)WordData2.get(point2)-(int)WordData1.get(point1)>distance){
                                    break;
                                }//当指针二中的值已经大于指针一的值，且超出距离时，不必在向右寻找，直接进行下一轮循环
                                // 因为加大指针二的值只会离指针一越来越远
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

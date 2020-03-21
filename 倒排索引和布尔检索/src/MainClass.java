import com.sun.source.tree.Tree;

import java.io.*;
import java.util.*;
public class MainClass {
    static TreeMap Keyword=new TreeMap<String,LinkedList<Integer>>();

    public static void main(String[] args) {
        String fileName="C:\\Users\\49141\\Desktop\\IR\\code&data\\倒排索引和布尔检索\\doc";
        for(int i=1;i<=10;i++) {
            String s = fileName + i + ".txt";
            SplitAndBulid(s, i);
        }
        ShowTreemap();  //打印建立完成的倒排索引表
        BoolSearch();  //进行布尔检索
    }
    public static void SplitAndBulid(String s, int i){//拆分单词并建立索倒排引
        try {
            String Filename=new String(s);    //得到的文件名
            FileReader f=new FileReader(Filename);    //使用filereader指定文件
            BufferedReader in=new BufferedReader(f);    //读取文件
            String str;
            while((str=in.readLine())!=null){
                String database[];
                database=str.split(" ");    //split方法返回的数组存入自定义string数组中
                for(String term:database){
                    if(!Keyword.containsKey(term)){    //如果得到的是新单词，即treemap键集中不含该单词
                        LinkedList list=new LinkedList<Integer>();
                        list.add(i);
                        Keyword.put(term,list);    //创建链表，加入文件序号，存入treemap
                    }
                    else{//如果得到的不是新单词，即treemap键集中已含有该单词
                        LinkedList list=(LinkedList) Keyword.get(term);
                        list.add(i);    //直接将文件序号存入对应链表
                    }
                }
            }
        }catch (IOException e){
            System.out.println("Error");
        }
    }
    public static void ShowTreemap(){
        Iterator iter=Keyword.keySet().iterator();

        while(iter.hasNext()){
            LinkedList list;
            String key=(String)iter.next();
            System.out.print("单词: "+key+", ");
            list=(LinkedList) Keyword.get(key);
            System.out.print("出现频率"+list.size()+"次, 位置在第 ");
            for (int i=0;i<list.size();i++){
                System.out.print(list.get(i)+" ");
            }
            System.out.println("个文档");
        }
    }
    public static void BoolSearch(){
        Scanner input=new Scanner(System.in);
        String word1,word2,word3;

        System.out.print("请输入要进行A and B 布尔检索的两个单词：");
        word1=input.next();
        word2=input.next();
        AandB(word1,word2);

        System.out.print("请输入要进行A and B and C布尔检索的三个单词：");
        word1=input.next();
        word2=input.next();
        word3=input.next();
        AandBandC(word1,word2,word3);

        System.out.print("请输入要进行A or B布尔检索的两个单词：");
        word1=input.next();
        word2=input.next();
        AorB(word1,word2);

        System.out.print("请输入要进行A and NOT (B or C)布尔检索的三个单词：");
        word1=input.next();
        word2=input.next();
        word3=input.next();
        Anot_BorC(word1,word2,word3);
    }
    static public void AandB(String word1,String word2){
        LinkedList list1=(LinkedList) Keyword.get(word1);  //第一个单词对应的链表
        LinkedList list2=(LinkedList) Keyword.get(word2);  //第二个单词对应的链表
        LinkedList answer=new LinkedList<Integer>();  //结果链表
        int i=0,j=0;

        //A AND B操作
        while(i<list1.size() && j<list2.size()){
            if((int)(list1.get(i))==(int)(list2.get(j))){  //当得到的值相等时，将其加入结果链表
                answer.add((int)(list1.get(i)));
                i++;j++;
            }
            else if((int)(list1.get(i))<(int)(list2.get(j))){//当得到的第一个值小于第二个值时，将第一个后延一位
                i++;
            }
            else if((int)(list1.get(i))>(int)(list2.get(j))){//当得到的第一个值大于第二个值时，将第二个后延一位
                j++;
            }
        }
        for (i=0;i<answer.size();i++){  //对结果链表进行输出
            System.out.print(answer.get(i)+" ");
        }
        System.out.println();
    }

    static public void AandBandC(String word1,String word2,String word3){
        if(!Keyword.containsKey(word1)){
            System.out.println("您输入的单词"+word1+"不存在");
            return;
        }
        if(!Keyword.containsKey(word2)){
            System.out.println("您输入的单词"+word2+"不存在");
            return;
        }
        LinkedList list1=(LinkedList) Keyword.get(word1);
        LinkedList list2=(LinkedList) Keyword.get(word2);
        LinkedList list3=(LinkedList) Keyword.get(word3);
        LinkedList answer=new LinkedList<Integer>();
        int i=0,j=0,k=0;
        while(i<list1.size() && j<list2.size() && k<list3.size()){
            if(!Keyword.containsKey(word1)){
                System.out.println("您输入的单词"+word1+"不存在");
                return;
            }
            if(!Keyword.containsKey(word2)){
                System.out.println("您输入的单词"+word2+"不存在");
                return;
            }
            if(!Keyword.containsKey(word3)){
                System.out.println("您输入的单词"+word3+"不存在");
                return;
            }
            //情况一：
            if((int)(list1.get(i))==(int)(list2.get(j)) && (int)(list1.get(i))==(int)(list3.get(k))){
                answer.add((int)(list1.get(i)));
                i++;j++;k++;
            }
            //情况二*3 ：
            else if((int)(list1.get(i))==(int)(list2.get(j))&& (int)(list1.get(i))<(int)(list3.get(k))){
                i++;j++;
            }
            else if((int)(list1.get(i))==(int)(list3.get(k)) && (int)(list1.get(i))<(int)(list2.get(j))){
                i++;k++;
            }
            else if((int)(list2.get(j))==(int)(list3.get(k))&& (int)(list2.get(j))<(int)(list1.get(i))){
                j++;k++;
            }
            //情况三*3 ：
            else if((int)(list1.get(i))<(int)(list2.get(j)) && (int)(list1.get(i))<(int)(list3.get(k))){
                i++;
            }
            else if((int)(list2.get(j))<(int)(list1.get(i)) && (int)(list2.get(j))<(int)(list3.get(k))){
                j++;
            }
            else if((int)(list3.get(k))<(int)(list1.get(i)) && (int)(list3.get(k))<(int)(list2.get(j))){
                k++;
            }
        }
        //打印结果链表：
        for (i=0;i<answer.size();i++){
            System.out.print(answer.get(i)+" ");
        }
        System.out.println();
    }

    static public void AorB(String word1,String word2){
        LinkedList list1=(LinkedList) Keyword.get(word1);
        LinkedList list2=(LinkedList) Keyword.get(word2);
        LinkedList answer=new LinkedList<Integer>();
        int i=0,j=0;

        while(i<list1.size() && j<list2.size()){
            if((int)(list1.get(i))==(int)(list2.get(j))){  //情况一
                answer.add((int)(list1.get(i)));
                i++;j++;
            }
            else if((int)(list1.get(i))<(int)(list2.get(j))){ //情况二
                answer.add((int)(list1.get(i)));
                i++;
            }
            else if((int)(list1.get(i))>(int)(list2.get(j))){  //情况三
                answer.add((int)(list2.get(j)));
                j++;
            }
        }
        //遍历未完成链表中的值，此时未完成的链表可能是链表一，也可能是链表二
        while(i<list1.size()){
            answer.add((int)(list1.get(i)));
            i++;
        }
        while(j<list2.size()){
            answer.add((int)(list2.get(j)));
            j++;
        }
        for (i=0;i<answer.size();i++){
            System.out.print(answer.get(i)+" ");
        }
        System.out.println();
    }

    static public void Anot_BorC(String word1,String word2,String word3){
        //BorC
        LinkedList list1=(LinkedList) Keyword.get(word1);
        LinkedList list2=(LinkedList) Keyword.get(word2);
        LinkedList list3=(LinkedList) Keyword.get(word3);
        LinkedList answer=new LinkedList<Integer>();
        int i=0,j=0;

        while(i<list2.size() && j<list3.size()){  //OR操作，主要思路同上
            if((int)(list2.get(i))==(int)(list3.get(j))){
                answer.add((int)(list2.get(i)));
                i++;j++;
            }
            else if((int)(list2.get(i))<(int)(list3.get(j))){
                answer.add((int)(list2.get(i)));
                i++;
            }
            else if((int)(list2.get(i))>(int)(list3.get(j))){
                answer.add((int)(list3.get(j)));
                j++;
            }
        }
        while(i<list2.size()){
            answer.add((int)(list2.get(i)));
            i++;
        }
        while(j<list3.size()){
            answer.add((int)(list3.get(j)));
            j++;
        }

        //not(BorC)
        LinkedList notBorC=new LinkedList<Integer>();
        i=1;j=0;
        while(i<=10 && j<answer.size()){  //从1到10对被取反链表一一检测
            if(i<(int)answer.get(j)){  //当检测值小于被检查值是，检测值被添加到结果链表并加一
                notBorC.add(i);
                i++;
            }
            else if(i==(int)answer.get(j)){  //当检测值存在于被检测链表中时，检测值加一，被检测链表后推
                i++;j++;
            }
        }

        //A NOT (B OR C)
        answer.clear();
        i=0;j=0;
        while(i<list1.size() && j<notBorC.size()){  //AND操作，主要思路同上
            if((int)(list1.get(i))==(int)(notBorC.get(j))){
                answer.add((int)(list1.get(i)));
                i++;j++;
            }
            else if((int)(list1.get(i))<(int)(notBorC.get(j))){
                i++;
            }
            else if((int)(list1.get(i))>(int)(notBorC.get(j))){
                j++;
            }
        }
        for (i=0;i<answer.size();i++){
            System.out.print(answer.get(i)+" ");
        }
        System.out.println();
    }
}

/*数据通过更改代码段输入程序中
采用的数据结构为Treemap+Linkedlist，与实验一类似
实验中没有更改对应的单词（BRUTUS/CAESAR）
结果保存在链表中并输出*/

import java.lang.Math;//开平方用到MATH包
import java.util.*;//TREEMAP用到UTIL包

public class main1 {
    static int[] TestData1;
    static int[] TestData2;
    static TreeMap Keyword=new TreeMap<String,LinkedList<Integer>>();//采用Treemap键值对保存字符串与字符串出现的文档编号

    public static void main(String[] args) {
        TestData1=new int[101];
        TestData2=new int[101];
        for(int i=0;i<=100;i++){
            TestData1[i]=i*2;
        }
        for(int i=0;i<=100;i++){
            TestData2[i]=i*2+1;
        }
        LinkedList<Integer> Data1=new LinkedList<>();
        for(int i=0;i<TestData1.length;i++){
            Data1.add(TestData1[i]);
        }//将单词BRUTUS出现的文档编号输入到链表中

        LinkedList<Integer> Data2=new LinkedList<>();
        for(int i=0;i<TestData2.length;i++){
            Data2.add(TestData2[i]);
        }//将单词CAESAR出现的文档编号输入到链表中


        Keyword.put("BRUTUS",Data1);
        Keyword.put("CAESAR",Data2);//构建简单的Treemap结构的倒排索引

        int i=0,j=0,count=0;

        int step1= (int) Math.sqrt(TestData1.length);
        int step2= (int) Math.sqrt(TestData2.length);//根据教材，把跳表的步长设定为各自长度的开根号后整数
        LinkedList<Integer> answer=new LinkedList<>();
        LinkedList list1=(LinkedList) Keyword.get("BRUTUS");
        LinkedList list2=(LinkedList) Keyword.get("CAESAR");

        while(i<TestData1.length && j<TestData2.length){//不断比对两条链表中的文档编号，直到任意一条超出其长度为止
            if(list1.get(i)==list2.get(j)){
                answer.add((int)list1.get(i));
                i++;j++;
            }//当比对的两值正好相等时，说明两个单词都在同一个文档中出现，这时将他加入结果列表中并把两链表各后推一位
            else if((int)list1.get(i)<(int)list2.get(j)){
                if(i+step1<TestData1.length && (int)list1.get(i+step1)<=(int)list2.get(j)){
                   while(i+step1<TestData1.length && (int)list1.get(i+step1)<=(int)list2.get(j)){
                       count++;
                       i+=step1;
                   }
                }//当当前比对的链表一值小于链表二的值时，尝试使链表一的指针跳表p^1/2个节点，并比较跳表后两个值，
                // 若仍满足链表一值小于链表二的值时，保存跳表操作并继续循环检测下一个值
                else{
                    i++;//跳表后链表一值大于链表二的值，其中可能跳过了共同的值，所以撤销跳表，比对其之间的值
                }
            }
            else if((int)list1.get(i)>(int)list2.get(j)){
                if(j+step2<TestData2.length && (int)list1.get(i)>(int)list2.get(j+step2)){
                    while(j+step2<TestData2.length && (int)list1.get(i)>(int)list2.get(j+step2)){
                        j+=step2;
                        count++;
                    }//当当前比对的链表一值大于链表二的值时，尝试使链表一的指针跳表p^1/2个节点，并比较跳表后两个值，
                    // 若仍满足链表一值大于链表二的值时，保存跳表操作并继续循环检测下一个值
                }
                else{
                    j++;//跳表后链表一值小于链表二的值，其中可能跳过了共同的值，所以撤销跳表，比对其之间的值
                }
            }
        }

        System.out.print("两个倒排记录表合并的结果为：");
        for(int k=0;k<answer.size();k++){
            System.out.print(answer.get(k)+" ");
        }//对结果链表的值进行输出检验
        System.out.println("跳表操作的次数为："+count);
    }
}

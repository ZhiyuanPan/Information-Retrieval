import java.lang.Math;
import java.util.*;

public class main1 {
    static int[] TestData1={2,4,8,34,35,64,128};
    static int[] TestData2={1,2,3,5,8,17,21,31,75,81,84,89,92};
    static TreeMap Keyword=new TreeMap<String,LinkedList<Integer>>();

    public static void main(String[] args) {
        LinkedList<Integer> Data1=new LinkedList<>();
        for(int i=0;i<TestData1.length;i++){
            Data1.add(TestData1[i]);
        }

        LinkedList<Integer> Data2=new LinkedList<>();
        for(int i=0;i<TestData2.length;i++){
            Data2.add(TestData2[i]);
        }

        Keyword.put("BRUTUS",Data1);
        Keyword.put("CAESAR",Data2);

        int i=0,j=0,count=0;

        int step1= (int) Math.sqrt(TestData1.length);
        int step2= (int) Math.sqrt(TestData2.length);

        LinkedList<Integer> answer=new LinkedList<>();
        LinkedList list1=(LinkedList) Keyword.get("BRUTUS");
        LinkedList list2=(LinkedList) Keyword.get("CAESAR");

        while(i<TestData1.length && j<TestData2.length){
            if(list1.get(i)==list2.get(j)){
                answer.add((int)list1.get(i));
                i++;j++;count++;
            }
            else if((int)list1.get(i)<(int)list2.get(j)){
                if(i+step1<TestData1.length && (int)list1.get(i+step1)<(int)list2.get(j)){
                    i+=step1;
                    count++;
                }
                else{
                    i++;
                }
            }
            else if((int)list1.get(i)>(int)list2.get(j)){
                if(j+step2<TestData2.length && (int)list1.get(i)>(int)list2.get(j+step2)){
                    j+=step2;
                    count++;
                }
                else{
                    j++;
                }
            }
        }

        System.out.print("两个倒排记录表合并的结果为：");
        for(int k=0;k<answer.size();k++){
            System.out.print(answer.get(k)+" ");
        }
        System.out.println();
    }
}

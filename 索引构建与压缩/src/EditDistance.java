import java.util.*;
import java.lang.String;
import java.math.*;
public class EditDistance {
    static void Distance(String word1, String word2){
        int len1=word1.length(),len2=word2.length();

        int [][]matrix=new int[len1+1][len2+1];//定义数组存放每一个片段的距离并动态规划

        System.out.print("    ");
        for (int i = 0; i < len2; i++) {
            System.out.print(word2.charAt(i)+" ");
        }
        System.out.println();
        for(int j=0;j<=len2;j++){
            matrix[0][j]=j;
        }
        for(int i=0;i<=len1;i++){
            matrix[i][0]=i;
        }//格式控制

        for(int i=1;i<=len1;i++){//动态规划算法，循环遍历i，j
            for(int j=1;j<=len2;j++){
                if(word1.charAt(i-1)==word2.charAt(j-1)){
                    matrix[i][j]= Math.min(Math.min(matrix[i-1][j]+1,matrix[i][j-1]+1),matrix[i-1][j-1]);
                }//当当前位置的两个字符数据相等时，计算达到当前位置的最小值
                //当左上角值为当前元素的最小值时，且当前位置与左上角数据对应字符串相等，可以直接使用左上角值
                //否则从左方数据或上方数据取最小值
                // 进行一步操作得到当前字符串，即加一
                else {
                    matrix[i][j]=Math.min(Math.min(matrix[i-1][j]+1,matrix[i][j-1]+1),matrix[i-1][j-1]+1);
                }//当当前位置的两个字符数据不相等时，计算达到当前位置的最小值
                //因为左上方的值对应的字符串与当前字符串不相等，所以从左方数据，左上方数据或上方数据取最小值
                // 进行一步操作
                // 得到当前字符串，即加一
            }
        }

        for(int i=0;i<=len1;i++){
            for(int j=0;j<=len2;j++){
                if(i==0 && j==0){
                    System.out.print("  ");
                }
                if(i>0 && j==0){
                    System.out.print(word1.charAt(i-1)+" ");
                }
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();//格式化输出矩阵

        System.out.println("两个单词的最短距离为"+matrix[word1.length()][word2.length()]);
        //矩阵最右下角的值即为两个单词的最小距离，输出
    }

    public static void main(String[] args) {
        String word1, word2;
        Scanner input=new Scanner(System.in);
        System.out.println("请输入两个单词：");
        word1=input.next();
        word2=input.next();//获取两个需要计算距离的的单词

        Distance(word1,word2);//放入核心函数中进行处理
    }
}

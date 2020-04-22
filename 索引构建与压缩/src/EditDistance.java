import java.util.*;
import java.lang.String;
import java.math.*;
public class EditDistance {
    static void Distance(String word1, String word2){
        int len1=word1.length(),len2=word2.length();

        int [][]matrix=new int[len1+1][len2+1];
        for(int j=0;j<=len2;j++){
            matrix[0][j]=j;
        }
        for(int i=0;i<=len1;i++){
            matrix[i][0]=i;
        }

        for(int i=1;i<=len1;i++){
            for(int j=1;j<=len2;j++){
                if(word1.charAt(i-1)==word2.charAt(j-1)){
                    matrix[i][j]=Math.min(Math.min(matrix[i-1][j]+1,matrix[i][j-1]+1),matrix[i-1][j-1]);
                }
                else {
                    matrix[i][j]=Math.min(Math.min(matrix[i-1][j]+1,matrix[i][j-1]+1),matrix[i-1][j-1]+1);
                }
            }
        }

        for(int i=0;i<=len1;i++){
            for(int j=0;j<=len2;j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        String word1, word2;
        Scanner input=new Scanner(System.in);
        word1=input.next();
        word2=input.next();

        Distance(word1,word2);
    }
}

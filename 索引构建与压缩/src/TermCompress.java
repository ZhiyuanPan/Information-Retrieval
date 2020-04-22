import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class TermCompress {
    static HashMap index = new HashMap<String, Integer>();
    static HashSet stopword = new HashSet<String>();

    static void Indexing(String str) {
        String word = new String("");
        int flag = -1;
        for (int i = 0; i < str.length(); i++) {
            if ((int) str.charAt(i) >= 97 && (int) str.charAt(i) <= 122) {
                word += str.charAt(i);
                flag = 1;
            } else if (flag == 1 && (str.charAt(i) < 97 || str.charAt(i) > 122)) {
                flag = -1;
                if (index.containsKey(word)) {
                    index.put(word, (int) index.get(word) + 1);
                }
                else {
                    if(!stopword.contains(word)){
                        index.put(word, 1);
                    }
                }
                word = "";
            }
        }

    }

    static void getStopWord() {
        try {
            String filename = new String("C:\\Users\\49141\\Desktop\\IR\\Information-Retrieval\\索引构建与压缩\\stopwords.txt");
            FileReader f = new FileReader(filename);
            BufferedReader in = new BufferedReader(f);
            String str;
            while ((str = in.readLine()) != null) {
                stopword.add(str);
            }
        } catch (IOException e) {
            System.out.println("读取错误");
        }
    }

    static void getReviewAndBulid() {
        try {
            String filename = new String("C:\\Users\\49141\\Desktop\\IR\\Information-Retrieval\\索引构建与压缩\\Software.txt");
            FileReader f = new FileReader(filename);
            BufferedReader in = new BufferedReader(f);
            String str;
            int flag = -1;
            String review = new String("");
            while ((str = in.readLine()) != null) {
                if (str.length() == 0) {
                    flag = -1;
                    review = review.substring(13).toLowerCase();
                    Indexing(review);
                    review = "";
                    continue;
                }
                if (str.substring(0, 11).equals("review/text")) {
                    flag = 1;
                }
                if (flag == 1) {
                    review += str;
                }
            }
        } catch (IOException e) {
            System.out.println("读取错误");
        }
    }

    public static void main(String[] args) {

        getStopWord();

        getReviewAndBulid();

        Iterator iter = index.entrySet().iterator();

        int i = 0;
        while (iter.hasNext()) {
            i++;
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            System.out.println(key + "," + val);
        }
        System.out.println(i);
    }
}

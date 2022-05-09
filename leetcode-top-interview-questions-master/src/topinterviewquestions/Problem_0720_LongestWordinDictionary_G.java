package topinterviewquestions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Problem_0720_LongestWordinDictionary_G {
    //https://leetcode.com/problems/longest-word-in-dictionary/discuss/109114/JavaC%2B%2B-Clean-Code\
    //Sort the words alphabetically, therefore shorter words always comes before longer words;
    //Along the sorted list, populate the words that can be built;
    //Any prefix of a word must comes before that word.
    class Solution {
        public String longestWord(String[] words) {
            Arrays.sort(words);
            Set<String> built = new HashSet<String>();
            String res = "";
            for (String w : words) {
                if (w.length() == 1 || built.contains(w.substring(0, w.length() - 1))) {
                    res = w.length() > res.length() ? w : res;//update res成w和wes中更长的那个
                    built.add(w);
                }
            }
            return res;
        }
    }



    public static void main(String[] args) {
        String test = "Hello";
        System.out.println(test.substring(0, 4));
    }
}

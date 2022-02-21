package topinterviewquestions;

import java.util.Arrays;
/*
You are given a string s and an array of strings words. You should add a closed pair of bold tag <b> and </b> to wrap the substrings in s that exist in words. If two such substrings overlap, you should wrap them together with only one pair of closed bold-tag. If two substrings wrapped by bold tags are consecutive, you should combine them.

Return s after adding the bold tags.



Example 1:

Input: s = "abcxyz123", words = ["abc","123"]
Output: "<b>abc</b>xyz<b>123</b>"
Example 2:

Input: s = "aaabbcc", words = ["aaa","aab","bc"]
Output: "<b>aaabbc</b>c"


Constraints:

1 <= s.length <= 1000
0 <= words.length <= 100
1 <= words[i].length <= 1000
s and words[i] consist of English letters and digits.
All the values of words are unique.


Note: This question is the same as 758: https://leetcode.com/problems/bold-words-in-string/
 */
public class Problem_0616_AddBoldTaginString_G {

        //Use a boolean array to mark if character at each position is bold or not. After that, things will become simple.
        public static String addBoldTag(String s, String[] dict) {
            int N = s.length();
            boolean[] bold = new boolean[N];
            int end = 0;//这个是标记如果用dict的单词，目前最远能走到哪的后面一位，即最近的不在dict的位置
            for (int i = 0; i < N; i++) {
                for (String word : dict) {
                    if (s.startsWith(word, i)) {
                        end = Math.max(end, i + word.length());
                    }
                }
                bold[i] = end > i;
            }

            StringBuilder res = new StringBuilder();
            for (int i = 0; i < N; i++) {
                if (!bold[i]) {
                    res.append(s.charAt(i));
                    continue;
                }
                int j = i;//第一个在dict的位置
                while (j < N && bold[j]) {
                    j++;//找到第一个不在dict的位置
                }
                res.append("<b>" + s.substring(i, j) + "</b>");
                i = j - 1;
            }
//            System.out.println(Arrays.toString(bold));
            return res.toString();
        }



    public static void main(String[] args) {
        String test = "abcxyz123";
        String[] arr = new String[]{"abc","123"};
        System.out.println(addBoldTag(test, arr));
    }
}

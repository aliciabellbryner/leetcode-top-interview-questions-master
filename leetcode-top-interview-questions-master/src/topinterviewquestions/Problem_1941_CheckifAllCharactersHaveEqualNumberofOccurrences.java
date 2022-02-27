package topinterviewquestions;

import java.util.Stack;

public class Problem_1941_CheckifAllCharactersHaveEqualNumberofOccurrences {
    //https://leetcode.com/problems/check-if-all-characters-have-equal-number-of-occurrences/discuss/1407800/C-C%2B%2B-Java-JS-Python-(1-Liner)-Simple-and-Short-Solutions-Faster-than-100
    class Solution {
        public boolean areOccurrencesEqual(String s) {
            int[] freq = new int[26];

            for (int i = 0; i < s.length(); i++) {
                freq[s.charAt(i)-'a']++;
            }

            int val = freq[s.charAt(0) - 'a'];
            for (int i = 0; i < 26; i++) {
                if (freq[i] != 0 && freq[i] != val) {
                    return false;
                }
            }
            return true;
        }
    }
}

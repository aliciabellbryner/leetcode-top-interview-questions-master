package topinterviewquestions;
/*
Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise.

In other words, return true if one of s1's permutations is the substring of s2.

Example 1:

Input: s1 = "ab", s2 = "eidbaooo"
Output: true
Explanation: s2 contains one permutation of s1 ("ba").
Example 2:

Input: s1 = "ab", s2 = "eidboaoo"
Output: false


Constraints:

1 <= s1.length, s2.length <= 104
s1 and s2 consist of lowercase English letters.
 */
public class Problem_0567_PermutationInString {
    //Approach #5 Sliding Window [Accepted]:
    //Time complexity: O(l_1+26*(l_2-l_1)) where l_1 is the length of string l_1 and l_2 is the length of string l_2
    //Space complexity: O(1). Constant space is used.
    public class Solution {
        public boolean checkInclusion(String s1, String s2) {
            if (s1.length() > s2.length()) {
                return false;
            }
            int[] map_s1 = new int[26];
            int[] map_s2 = new int[26];
            for (int i = 0; i < s1.length(); i++) {//先把s1中的每个char放到map中去
                map_s1[s1.charAt(i) - 'a']++;
                map_s2[s2.charAt(i) - 'a']++;
            }
            for (int i = 0; i < s2.length() - s1.length(); i++) {
                if (matches(map_s1, map_s2)) {
                    return true;
                }
                map_s2[s2.charAt(i + s1.length()) - 'a']++;
                map_s2[s2.charAt(i) - 'a']--;
            }
            return matches(map_s1, map_s2);
        }

        public boolean matches(int[] map_s1, int[] map_s2) {
            for (int i = 0; i < 26; i++) {
                if (map_s1[i] != map_s2[i])
                    return false;
            }
            return true;
        }
    }
}

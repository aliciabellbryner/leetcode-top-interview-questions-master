package topinterviewquestions;

public class Problem_0567_PermutationInString {
    //Approach #5 Sliding Window [Accepted]:
    //Time complexity: O(l_1+26*(l_2-l_1)) where l_1 is the length of string l_1 and l_2 is the length of string l_2
    //Space complexity: O(1). Constant space is used.
    public class Solution {
        public boolean checkInclusion(String s1, String s2) {
            if (s1.length() > s2.length()) {
                return false;
            }
            int[] s1map = new int[26];
            int[] s2map = new int[26];
            for (int i = 0; i < s1.length(); i++) {
                s1map[s1.charAt(i) - 'a']++;
                s2map[s2.charAt(i) - 'a']++;
            }
            for (int i = 0; i < s2.length() - s1.length(); i++) {
                if (matches(s1map, s2map)) {
                    return true;
                }
                s2map[s2.charAt(i + s1.length()) - 'a']++;
                s2map[s2.charAt(i) - 'a']--;
            }
            return matches(s1map, s2map);
        }

        public boolean matches(int[] s1map, int[] s2map) {
            for (int i = 0; i < 26; i++) {
                if (s1map[i] != s2map[i])
                    return false;
            }
            return true;
        }
    }
}

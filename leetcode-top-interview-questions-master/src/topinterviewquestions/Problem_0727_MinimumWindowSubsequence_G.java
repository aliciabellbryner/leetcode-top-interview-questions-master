package topinterviewquestions;

public class Problem_0727_MinimumWindowSubsequence_G {
    /*.*/
    //https://leetcode.com/problems/minimum-window-subsequence/discuss/109356/JAVA-two-pointer-solution-(12ms-beat-100)-with-explaination/235584
    //two pointers
    public String minWindow(String s1, String s2) {
        if (s1.length() == 0 || s2.length() == 0) {
            return "";
        }

        /**
         * we can conduct two steps by using two pointers for this probelm:
         * 1. check feasibility from left to right
         * 2. check optimization from right to left
         * we can traverse from left to right, find a possible candidate until reach the first ending character of s2
         * eg: for the string s = abcdebdde and t = bde, we should traverse s string until we find first e,
         * i.e. abcde, then traverse back from current "e" to find if we have other combination of bde with smaller
         * length.
         * @param right: fast pointer that always points the last character of s2 in s1
         * @param left: slow pointer that used to traverse back when right pointer find the last character of s2 in s1
         * @param tIndex: third pointer used to scan string s2
         * @param minLen: current minimum length of subsequence
         * */
        int right = 0;
        int minLen = Integer.MAX_VALUE;
        String result = "";

        while (right < s1.length()) {
            int tIndex = 0;
            // use fast pointer to find the last character of s2 in s1
            while (right < s1.length()) {
                if (s1.charAt(right) == s2.charAt(tIndex)) {
                    tIndex++;
                }
                if (tIndex == s2.length()) {
                    break;
                }
                right++;
            }

            // if right pointer is over than boundary
            if (right == s1.length()) {
                break;
            }

            // use another slow pointer to traverse from right to left until find first character of s2 in s1
            int left = right;
            tIndex = s2.length() - 1;
            while (left >= 0) {
                if (s1.charAt(left) == s2.charAt(tIndex)) {
                    tIndex--;
                }
                if (tIndex < 0) {
                    break;
                }
                left--;
            }
            // if we found another subsequence with smaller length, update result
            if (right - left + 1 < minLen) {
                minLen = right - left + 1;
                result = s1.substring(left, right + 1);
            }
            // WARNING: we have to move right pointer to the next position of left pointer, NOT the next position
            // of right pointer
            right = left + 1;
        }
        return result;
    }
}

package topinterviewquestions;

public class Problem_0844_BackspaceStringCompare_G {

    //https://leetcode.com/problems/backspace-string-compare/discuss/135603/JavaC++Python-O(N)-time-and-O(1)-space/201661
    class Solution {
        public boolean backspaceCompare(String S, String T) {
            if (S == null || T == null) return S == T;
            int m = S.length(), n = T.length();
            int i = m - 1, j = n - 1;
            int cnt1 = 0, cnt2 = 0;//number of '#';
            while (i >= 0 || j >= 0) {
                while (i >= 0 && (S.charAt(i) == '#' || cnt1 > 0)) {
                    if (S.charAt(i) == '#') cnt1++;
                    else cnt1--;
                    i--;
                }
                while (j >= 0 && (T.charAt(j) == '#' || cnt2 > 0)) {
                    if (T.charAt(j) == '#') cnt2++;
                    else cnt2--;
                    j--;
                }
                if (i >= 0 && j >= 0 && S.charAt(i) == T.charAt(j)) {
                    i--;
                    j--;
                } else {
                    return i == -1 && j == -1;
                }
            }
            return true;
        }
    }


    //Approach #2: Two Pointer [Accepted]
    //https://leetcode.com/problems/backspace-string-compare/solution/
    class Solution2 {
        public boolean backspaceCompare(String S, String T) {
            int i = S.length() - 1, j = T.length() - 1;
            int skipS = 0, skipT = 0;

            while (i >= 0 || j >= 0) { // While there may be chars in build(S) or build (T)
                while (i >= 0) { // Find position of next possible char in build(S)
                    if (S.charAt(i) == '#') {skipS++; i--;}
                    else if (skipS > 0) {skipS--; i--;}
                    else break;
                }
                while (j >= 0) { // Find position of next possible char in build(T)
                    if (T.charAt(j) == '#') {skipT++; j--;}
                    else if (skipT > 0) {skipT--; j--;}
                    else break;
                }
                // If two actual characters are different
                if (i >= 0 && j >= 0 && S.charAt(i) != T.charAt(j))
                    return false;
                // If expecting to compare char vs nothing
                if ((i >= 0) != (j >= 0))
                    return false;
                i--; j--;
            }
            return true;
        }
    }


}

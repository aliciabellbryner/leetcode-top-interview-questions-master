package topinterviewquestions;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

/*
Constraints:

1 <= s1.length, s2.length <= 40
s1 and s2 consist of digits 1-9 (inclusive), and lowercase English letters only.
The number of consecutive digits in s1 and s2 does not exceed 3.
 */
public class Problem_2060_CheckifanOriginalStringExistsGivenTwoEncodedStrings {
    //consider the easy case, they all character, we compare s1.charAt(i) == s2.charAt(j)
    //digit case, we get a number from s1, we can calculate the number s1 has, (descripton said less than 1000), we can pass this value compare with number from s2 name it diff
    //character case if we still has remaing diff to spend passed from our parents, so we can use one dollor a day, one diff one position dfs(i + 1, j, diff - 1
    //terminating condition, if both reach the end and diff == 0
    class Solution {
        //112ms
        public boolean possiblyEquals(String s1, String s2) {
            int l1 = s1.length();
            int l2 = s2.length();

            // dp[i][j][diff] means if s1[i:] truncated by <diff> characters if diff > 0
            // and s2[j:] truncated by <-diff> characters if diff < 0 are equal
            Boolean[][][] dp = new Boolean[l1 + 1][l2 + 1][2000];
            return dfs(0, 0, 0, s1.toCharArray(), s2.toCharArray(), dp);
        }

        private boolean dfs(int i, int j, int diff, char[] s1, char[] s2, Boolean[][][] dp) {
            if (i == s1.length && j == s2.length) {
                return diff == 0;
            }

            if (dp[i][j][diff + 1000] != null) {
                return dp[i][j][diff + 1000];
            }

            // Literal matching on s1[i] and s2[j]
            if (i < s1.length && j < s2.length && diff == 0 && s1[i] == s2[j]) {
                if (dfs(i + 1, j + 1, 0, s1, s2, dp)) {
                    return dp[i][j][1000] = true;
                }
            }

            // Literal matching on s1[i]
            if (i < s1.length && !isDigit(s1[i]) && diff > 0 && dfs(i + 1, j, diff - 1, s1, s2, dp)) {
                return dp[i][j][diff + 1000] = true;
            }

            // Literal matching on s2[j]
            if (j < s2.length && !isDigit(s2[j]) && diff < 0 && dfs(i, j + 1, diff + 1, s1, s2, dp)) {
                return dp[i][j][diff + 1000] = true;
            }

            // Wildcard matching on s1[i]
            for (int k = i, val = 0; k < s1.length && isDigit(s1[k]); ++k) {
                val = val * 10 + (s1[k] - '0');
                if (dfs(k + 1, j, diff - val, s1, s2, dp)) {
                    return dp[i][j][diff + 1000] = true;
                }
            }

            // Wildcard matching on s2[j]
            for (int k = j, val = 0; k < s2.length && isDigit(s2[k]); ++k) {
                val = val * 10 + (s2[k] - '0');
                if (dfs(i, k + 1, diff + val, s1, s2, dp)) {
                    return dp[i][j][diff + 1000] = true;
                }
            }

            return dp[i][j][diff + 1000] = false;
        }

        private boolean isDigit(char c) {
            return c >= '0' && c <= '9';
        }
    }
}

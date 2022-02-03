package topinterviewquestions;

public class Problem_0664_StrangePrinter_G {
    //https://leetcode.com/problems/strange-printer/discuss/152758/Clear-Logical-Thinking-with-Code
    //dp: dp[i][j] turns needed to print s[i .. j] (both inclusive)
    //aim dp: state[0][n - 1] (n = s.length() - 1)
    //dp[i][i] = 1;
    //dp[i][i + 1] = 1 if s[i] == s[i + 1]
    //dp[i][i + 1] = 2 if s[i] != s[i + 1]
    //dp[i][j] = min(dp[i][k] + dp[k + 1][j]) for i <= k <= j - 1
    //	please note that, if s[i] equals to s[j] , state[i][j] should -1
    public int strangePrinter(String s) {

        if (s == null || s.length() == 0) {
            return 0;
        }

        int N = s.length();
        int[][] dp = new int[N][N];//dp[i][j]: how many turns needed to print s[i .. j] (both inclusive)

        for (int i = 0; i < N; i++) {
            dp[i][i] = 1;
        }

        for (int i = N - 1; i >= 0; i--) {
            for (int distance = 1; distance + i < N; distance++) {
                int j = distance + i;
                if (distance == 1) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j)) ? 1 : 2;
                    continue;
                }
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i; k + 1 <= j; k++) {//意思就是把i..j分别分成不同长度的，然后找到两个和最小的位置
                    int tmp = dp[i][k] + dp[k + 1][j];
                    dp[i][j] = Math.min(dp[i][j], tmp);
                }
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j]--;
                }
            }
        }

        return dp[0][N - 1];
    }
}

package topinterviewquestions;

public class Problem_0375_GuessNumberHigherorLowerII_G {
    //https://leetcode.com/problems/guess-number-higher-or-lower-ii/discuss/84764/Simple-DP-solution-with-explanation~~/89362
    //This is a typical AI MinMax problem, https://en.wikipedia.org/wiki/Minimax illustrates it really well. And I think the recursive way is more straightforward:
    int[][] dp;
    public int getMoneyAmount(int n) {
        dp = new int[n+1][n+1];
        return getMoneyAmount(1, n);
    }

    private int getMoneyAmount(int lower, int upper) {
        if (lower >= upper) {
            return 0;
        }
        if (dp[lower][upper] != 0) {
            return dp[lower][upper];
        }

        int maximum = Integer.MAX_VALUE;
        for (int i = lower; i <= upper; i++) {
            maximum = Math.min(maximum, Math.max(getMoneyAmount(lower, i-1), getMoneyAmount(i+1, upper)) + i);
        }
        dp[lower][upper] = maximum;

        return maximum;
    }
}

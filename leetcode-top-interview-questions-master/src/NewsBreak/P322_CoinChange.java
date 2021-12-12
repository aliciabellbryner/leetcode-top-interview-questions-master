package NewsBreak;

public class P322_CoinChange {
    public int coinChange(int[] coins, int amount) {
        if (coins == null || coins.length == 0) {
            return -1;
        }
        int N = coins.length;
        int[][] dp = new int[N][amount + 1];//dp[i][j] means when you iterate from idx = 0  to idx = i, no matter you choose i or not, that minimum coins you choose to form target amount
        for (int i = 1; i < amount + 1; i++) {
            if (i % coins[0] != 0) {
                dp[0][i] = -1;
            } else {
                dp[0][i] = i / coins[0];
            }
        }

        for (int i = 1; i < N; i++) {
            for (int j = 1; j < amount + 1; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                if (dp[i-1][j] != -1) {
                    dp[i][j] = dp[i-1][j];//don't choose coins[i] in the combination
                }
                if (j - coins[i] >= 0 && dp[i][j-coins[i]] != -1) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][j-coins[i]] + 1);//choose coins[i] in the combination
                }
                if (dp[i][j] == Integer.MAX_VALUE) {
                    dp[i][j] = -1;
                }
            }
        }

        return dp[N-1][amount];
    }
}

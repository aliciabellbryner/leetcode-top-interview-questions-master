package topinterviewquestions;

public class Problem_0322_CoinChange {

	//讲解在视频23中
	//coinChange2更好懂一些
	public static int coinChange2(int[] coins, int amount) {
		if (coins == null || coins.length == 0 || amount < 0) {
			return -1;
		}
		int N = coins.length;
		int[][] dp = new int[N][amount + 1];//dp[i][j]代表coins从0到i位置的钱自由使用，凑到j块钱需要的最小钞票数(aim+1是因为钱数如果是10，则需要11个数0-10来代表它
		// dp[i][0] = 0 0列不需要填
		// dp[0][1...] = arr[0]的整数倍，有张数，倍数，其他的格子-1（表示无方案）
		for (int j = 1; j <= amount; j++) {
			if (j % coins[0] != 0) {
				dp[0][j] = -1;
			} else {
				dp[0][j] = j / coins[0];
			}
		}

		for (int i = 1; i < N; i++) {
			for (int j = 1; j <= amount; j++) {
				dp[i][j] = Integer.MAX_VALUE;
				if (dp[i - 1][j] != -1) {//i位置的钱不使用
					dp[i][j] = dp[i - 1][j];
				}
				if (j - coins[i] >= 0 && dp[i][j - coins[i]] != -1) {//使用i位置的钱
					dp[i][j] = Math.min(dp[i][j], dp[i][j - coins[i]] + 1);
				}
				if (dp[i][j] == Integer.MAX_VALUE) {
					dp[i][j] = -1;
				}
			}
		}
		return dp[N - 1][amount];
	}
	public static int coinChange1(int[] coins, int amount) {
		if (coins == null || coins.length == 0 || amount < 0) {
			return -1;
		}
		int N = coins.length;
		int[][] dp = new int[N + 1][amount + 1];//dp[i][j]代表coins从0到i位置的钱自由使用，凑到j块钱需要的最小钞票数
		for (int col = 1; col <= amount; col++) {
			dp[N][col] = -1;
		}
		for (int i = N - 1; i >= 0; i--) {
			for (int rest = 0; rest <= amount; rest++) {
				dp[i][rest] = -1;
				if (dp[i + 1][rest] != -1) {
					dp[i][rest] = dp[i + 1][rest];
				}
				if (rest - coins[i] >= 0 && dp[i][rest - coins[i]] != -1) {
					if (dp[i][rest] == -1) {
						dp[i][rest] = dp[i][rest - coins[i]] + 1;
					} else {
						dp[i][rest] = Math.min(dp[i][rest], dp[i][rest - coins[i]] + 1);
					}
				}
			}
		}
		return dp[0][amount];
	}

}

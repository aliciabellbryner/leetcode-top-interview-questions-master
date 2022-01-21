package topinterviewquestions;

import java.util.Arrays;

public class Problem_0322_CoinChange {


	//https://leetcode.com/problems/coin-change/solution/
	//Approach #2 (Dynamic programming - Top down) [Accepted]
	//Time complexity : O(S∗n). where S is the amount, n is denomination count. In the worst case the recursive tree
	// of the algorithm has height of S and the algorithm solves only S subproblems because it caches precalculated solutions in a table.
	// Each subproblem is computed with n iterations, one by coin denomination.Therefore there is O(S∗n) time complexity.
	//Space complexity : O(S), where S is the amount to change We use extra space for the memoization table.
	public int coinChange(int[] coins, int amount) {
		if (amount < 1) {
			return 0;
		}
		return coinChange(coins, amount, new int[amount]);//长度是amount，所以每个dp[i]代表的是组成i+1总额的方法数
	}

	private int coinChange(int[] coins, int rest, int[] dp) {
		if (rest < 0) {
			return -1;
		}
		if (rest == 0) {
			return 0;
		}
		if (dp[rest - 1] != 0) {//说明已经算过了
			return dp[rest - 1];
		}
		int min = Integer.MAX_VALUE;
		for (int coin : coins) {
			int res = coinChange(coins, rest - coin, dp);
			if (res >= 0 && res < min) {//
				min = 1 + res;//因为还要加上本次的coin，所以一共是1+res个硬币
			}
		}
		dp[rest - 1] = (min == Integer.MAX_VALUE) ? -1 : min;
		return dp[rest - 1];
	}


	//Approach #3 (Dynamic programming - Bottom up) [Accepted]
	//Time complexity : O(S∗n). On each step the algorithm finds the next F(i) in nn iterations, where 1≤i≤S. Therefore in total the iterations are S*n
	//Space complexity : O(S). We use extra space for the memoization table.
	public int coinChange3(int[] coins, int amount) {
		int max = amount + 1;
		int[] dp = new int[amount + 1];
		Arrays.fill(dp, max);
		dp[0] = 0;
		for (int i = 1; i <= amount; i++) {
			for (int j = 0; j < coins.length; j++) {
				if (coins[j] <= i) {
					dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
				}
			}
		}
		return dp[amount] > amount ? -1 : dp[amount];
	}


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

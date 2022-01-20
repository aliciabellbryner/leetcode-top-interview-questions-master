package topinterviewquestions;

public class Problem_0188_BestTimeToBuyAndSellStockIV {

	//time O(NK), space O(NK)
	public static int maxProfit(int K, int[] prices) {
		if (prices == null || prices.length == 0) {
			return 0;
		}
		int N = prices.length;
		if (K >= N / 2) {
			return allTrans(prices);
		}
		int[][] dp = new int[K + 1][N];//dp[i][j]: the max profit you can get from arr[0, j] and the transaction total number not exceed j times

		int ans = 0;
		for (int j = 1; j <= K; j++) {
			int pre = dp[j][0];
			int best = pre - prices[0];
			for (int i = 1; i < N; i++) {
				pre = dp[j - 1][i];
				dp[j][i] = Math.max(dp[j][i - 1], prices[i] + best);
				best = Math.max(best, pre - prices[i]);
				ans = Math.max(dp[j][i], ans);
			}
		}
		return ans;
	}

	public static int allTrans(int[] prices) {//the max profit you can get if you can do all the transactions no total number limit
		int ans = 0;
		for (int i = 1; i < prices.length; i++) {
			ans += Math.max(prices[i] - prices[i - 1], 0);
		}
		return ans;
	}


	public static void main(String[] args) {
		int[] test = new int[]{3,2,6,5,0,3};
		System.out.println(maxProfit(2, test));
	}

//time too much as for the three for loop
	public static int maxProfit1(int K, int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		if (K >= N / 2) {
			return allTrans(arr);
		}
		int[][] dp = new int[N][K + 1];//dp[i][j]: the max profit you can get from arr[0, j] and the transaction total number not exceed j times
		for (int i = 1; i < N; i++) {
			for (int j = 1; j <= K; j++) {
				dp[i][j] = dp[i - 1][j];
				for (int p = 0; p <= i; p++) {
					dp[i][j] = Math.max(dp[p][j - 1] + arr[i] - arr[p], dp[i][j]);
				}
			}
		}
		return dp[N - 1][K];
	}


}

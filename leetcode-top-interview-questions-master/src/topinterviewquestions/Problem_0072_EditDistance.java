package topinterviewquestions;

public class Problem_0072_EditDistance {


	//Time complexity : O(mn) as it follows quite straightforward for the inserted loops.
	//Space complexity : O(mn) since at each step we keep the results of all previous computations.
	public int minDistance(String word1, String word2) {
		int M = word1.length(), N = word2.length();
		int[][] dp = new int[M+1][N+1];
		for(int i = 0;i<=M;i++) {
			dp[i][0] = i;
		}//change word1[0...i] to "" by deleting
		for(int j = 0;j<=N;j++) {
			dp[0][j] = j;
		}//change "" to word2[0...j] by adding
		for(int i = 0;i<M;i++){
			for(int j = 0;j<N;j++){
				if(word1.charAt(i) == word2.charAt(j)) {
					dp[i+1][j+1] = dp[i][j];
				}
				else {
					dp[i+1][j+1] = Math.min(dp[i][j],Math.min(dp[i+1][j],dp[i][j+1])) + 1;//+1 means adding the current element
				}
			}
		}
		return dp[M][N];
	}




	public int minDistance2(String word1, String word2) {
		return minDistance(word1, word2, 1, 1, 1);
	}

	public static int minDistance(String str1, String str2, int ic, int dc, int rc) {
		if (str1 == null || str2 == null) {
			return 0;
		}
		char[] chs1 = str1.toCharArray();
		char[] chs2 = str2.toCharArray();
		char[] longs = chs1.length >= chs2.length ? chs1 : chs2;
		char[] shorts = chs1.length < chs2.length ? chs1 : chs2;
		if (chs1.length < chs2.length) {
			int tmp = ic;
			ic = dc;
			dc = tmp;
		}
		int[] dp = new int[shorts.length + 1];
		for (int i = 1; i <= shorts.length; i++) {
			dp[i] = ic * i;
		}
		for (int i = 1; i <= longs.length; i++) {
			int pre = dp[0];
			dp[0] = dc * i;
			for (int j = 1; j <= shorts.length; j++) {
				int tmp = dp[j];
				if (longs[i - 1] == shorts[j - 1]) {
					dp[j] = pre;
				} else {
					dp[j] = pre + rc;
				}
				dp[j] = Math.min(dp[j], dp[j - 1] + ic);
				dp[j] = Math.min(dp[j], tmp + dc);
				pre = tmp;
			}
		}
		return dp[shorts.length];
	}

}

package topinterviewquestions;

public class Problem_0221_MaximalSquare {

	//注意，该题是求最大正方形，并不是长方形
	//solution1, 2D dp
	// time O(MN), space O(MN)
	public int maximalSquare1(char[][] matrix) {
		int M = matrix.length;
		int N = matrix[0].length;
		int[][] dp = new int[M+1][N+1];//dp[i][j]代表从matrix[-infinity][-infinity] 到matrix[i-1][j-1](必须要经过）可以构成的长方形面积，所以dp[o][0] = 0
		int res = 0;
		for (int i = 1; i <= M; i++) {
			for (int j = 1; j <= N; j++) {
				if (matrix[i-1][j-1] == '1') {//如果matrix[i-1][j-1]=0那要经过这个0点必定不可能构成正方形，所以肯定是0，不用计算
					dp[i][j] = Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1])) + 1;
					res = Math.max(res, dp[i][j]);
				}
			}
		}
		return res * res;
	}


	//solution2, 1D dp, better space complexity
	// time O(MN), space O(N)
	//https://leetcode.com/problems/maximal-square/solution/
	//Initially the dp array contains all 0's. As we scan the elements of the original matrix across a row,
	// we keep on updating the dp array as per the equation dp[j]=min(dp[j-1],dp[j],prev),
	// where prev refers to the old dp[j-1]. For every row, we repeat the same process and update in the same dp array.
	public int maximalSquare2(char[][] matrix) {
		int rows = matrix.length, cols = rows > 0 ? matrix[0].length : 0;
		int[] dp = new int[cols + 1];
		int maxSquareLength = 0, prev = 0;
		for (int i = 1; i <= rows; i++) {
			for (int j = 1; j <= cols; j++) {
				int temp = dp[j];
				if (matrix[i - 1][j - 1] == '1') {
					dp[j] = Math.min(Math.min(dp[j - 1], prev), dp[j]) + 1;
					maxSquareLength = Math.max(maxSquareLength, dp[j]);
				} else {
					dp[j] = 0;
				}
				prev = temp;
			}
		}
		return maxSquareLength * maxSquareLength;
	}



	//zuo‘s solution, no good
	public static int maximalSquare3(char[][] m) {
		if (m == null || m.length == 0 || m[0].length == 0) {
			return 0;
		}
		int N = m.length;
		int M = m[0].length;
		int[][] dp = new int[N + 1][M + 1];
		int max = 0;
		for (int i = 0; i < N; i++) {
			if (m[i][0] == '1') {
				dp[i][0] = 1;
				max = 1;
			}
		}
		for (int j = 1; j < M; j++) {
			if (m[0][j] == '1') {
				dp[0][j] = 1;
				max = 1;
			}
		}
		for (int i = 1; i < N; i++) {
			for (int j = 1; j < M; j++) {
				if (m[i][j] == '1') {
					dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
					max = Math.max(max, dp[i][j]);
				}
			}
		}
		return max * max;
	}

}

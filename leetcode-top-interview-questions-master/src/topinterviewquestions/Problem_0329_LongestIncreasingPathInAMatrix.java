package topinterviewquestions;

public class Problem_0329_LongestIncreasingPathInAMatrix {

	//leetcode solution: Approach #2 (DFS + Memoization) [Accepted]
	//https://leetcode.com/problems/longest-increasing-path-in-a-matrix/solution/
	//Time complexity : O(mn). Each vertex/cell will be calculated once and only once,
	// and each edge will be visited once and only once. The total time complexity is then O(V+E).
	// V is the total number of vertices and E is the total number of edges. In our problem,
	// O(V)=O(mn), O(E) = O(4V) = O(mn).
	//Space complexity : O(mn). The cache dominates the space complexity.
	private static final int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
	private int M, N;

	public int longestIncreasingPath(int[][] matrix) {
		if (matrix.length == 0) {
			return 0;
		}
		M = matrix.length;
		N = matrix[0].length;
		int[][] cache = new int[M][N];
		int res = 0;
		for (int i = 0; i < M; ++i)
			for (int j = 0; j < N; ++j)
				res = Math.max(res, dfs(matrix, i, j, cache));
		return res;
	}

	private int dfs(int[][] matrix, int i, int j, int[][] cache) {
		if (cache[i][j] != 0) {//说明走过了ij，所以直接返回
			return cache[i][j];
		}
		for (int[] d : dirs) {
			int x = i + d[0], y = j + d[1];
			if (0 <= x && x < M && 0 <= y && y < N && matrix[x][y] > matrix[i][j])
				cache[i][j] = Math.max(cache[i][j], dfs(matrix, x, y, cache));
		}
		return ++cache[i][j];//说明第一次走ij,所以要再包括本身
	}


	//Zuo's solution: too long!
	//一定要写出dp不然用brutal force肯定会超时
	public static int longest(int[][] matrix) {
		int longest = 0;
		int[][] dp = new int[matrix.length][matrix[0].length];
		// dp[i][j] == 0 process(i,j)
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				longest = Math.max(longest, process(matrix, i, j, dp));
			}
		}
		return longest;
	}

	// 从matrix[i][j]出发，可以走上下左右四个方向，走出的最长递增链是多长，返回
	public static int process(int[][] matrix, int i, int j, int[][] dp) {
		if (dp[i][j] != 0) {
			return dp[i][j];
		}
		// 不越界
		int next = 0; // 往左右上下四个方向，能走出最长的后续是多少？
		// i, j i-1,j
		if (i > 0 && matrix[i - 1][j] > matrix[i][j]) {
			next = process(matrix, i - 1, j, dp);
		}
		// i + 1
		if (i + 1 < matrix.length && matrix[i + 1][j] > matrix[i][j]) {
			next = Math.max(next, process(matrix, i + 1, j, dp));
		}
		if (j > 0 && matrix[i][j - 1] > matrix[i][j]) {
			next = Math.max(next, process(matrix, i, j - 1, dp));
		}
		if (j + 1 < matrix[0].length && matrix[i][j + 1] > matrix[i][j]) {
			next = Math.max(next, process(matrix, i, j + 1, dp));
		}
		dp[i][j] = 1 + next;
		return 1 + next;
	}

	public static int longestIncreasingPath1(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
			return 0;
		}
		int ans = 0;
		int N = matrix.length;
		int M = matrix[0].length;
		int[][] dp = new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				ans = Math.max(ans, lip(matrix, i, j, dp));
			}
		}
		return ans;
	}

	public static int lip(int[][] matrix, int i, int j, int[][] dp) {
		if (dp[i][j] != 0) {
			return dp[i][j];
		}
		int next = 0;
		if (canWalk(matrix, i, j, i - 1, j)) {
			next = Math.max(next, lip(matrix, i - 1, j, dp));
		}
		if (canWalk(matrix, i, j, i + 1, j)) {
			next = Math.max(next, lip(matrix, i + 1, j, dp));
		}
		if (canWalk(matrix, i, j, i, j - 1)) {
			next = Math.max(next, lip(matrix, i, j - 1, dp));
		}
		if (canWalk(matrix, i, j, i, j + 1)) {
			next = Math.max(next, lip(matrix, i, j + 1, dp));
		}
		dp[i][j] = 1 + next;
		return dp[i][j];
	}

	public static boolean canWalk(int[][] matrix, int i1, int j1, int i2, int j2) {
		return i2 >= 0 && i2 < matrix.length && j2 >= 0 && j2 < matrix[0].length && matrix[i1][j1] < matrix[i2][j2];
	}

	public int longestIncreasingPath_j(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return -1;
		}
		int M = matrix.length;
		int N = matrix[0].length;
		int res = 0;
		int[][] dp = new int[M+1][N+1];
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				res = Math.max(res, process(matrix, i, j, dp));
			}
		}
		return res;
	}
	public int process_j(int[][] matrix, int i, int j, int[][] dp) {
		if (dp[i][j] != 0) {
			return dp[i][j];
		}
		int M = matrix.length;
		int N = matrix[0].length;
		int next = 0;
		if (i+1 >= 0 && i+1 < M && matrix[i+1][j] > matrix[i][j]) {
			next = process_j(matrix, i+1, j, dp);
		}
		if (i-1 >= 0 && i-1 < M && matrix[i-1][j] > matrix[i][j]) {
			next = Math.max(next, process_j(matrix, i-1, j, dp));
		}
		if (j+1 >= 0 && j+1 < N && matrix[i][j+1] > matrix[i][j]) {
			next = Math.max(next, process_j(matrix, i, j+1, dp));
		}
		if (j-1 >= 0 && j-1 < N && matrix[i][j-1] > matrix[i][j]) {
			next = Math.max(next, process_j(matrix, i, j-1, dp));
		}
		dp[i][j] = next+1;
		return dp[i][j];
	}

}

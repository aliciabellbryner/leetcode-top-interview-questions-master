package topinterviewquestions;

//indextree问题，followup里有一个一维的indextree问题，就是从那个里修改过来的
// 提交时把类名和构造函数名从Problem_0308_RangeSumQuery2DMutable改成NumMatrix
public class Problem_0308_RangeSumQuery2DMutable {
	private int[][] tree;
	private int[][] nums;
	private int N;
	private int M;

	public Problem_0308_RangeSumQuery2DMutable(int[][] matrix) {
		if (matrix.length == 0 || matrix[0].length == 0) {
			return;
		}
		N = matrix.length;
		M = matrix[0].length;
		tree = new int[N + 1][M + 1];
		nums = new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				update(i, j, matrix[i][j]);
			}
		}
	}

	// 用户给我的row，col不能越界
	private int sum(int row, int col) {
		int sum = 0;
		for (int i = row + 1; i > 0; i -= i & (-i)) {
			for (int j = col + 1; j > 0; j -= j & (-j)) {
				sum += tree[i][j];
			}
		}
		return sum;
	}

	// 用户给我的row，col不能越界
	public void update(int row, int col, int val) {
		if (N == 0 || M == 0) {
			return;
		}
		int add = val - nums[row][col];
		nums[row][col] = val;
		for (int i = row + 1; i <= N; i += i & (-i)) {
			for (int j = col + 1; j <= M; j += j & (-j)) {
				tree[i][j] += add;
			}
		}
	}

	public int sumRegion(int row1, int col1, int row2, int col2) {
		if (N == 0 || M == 0) {
			return 0;
		}
		return sum(row2, col2) + sum(row1 - 1, col1 - 1) - sum(row1 - 1, col2) - sum(row2, col1 - 1);
	}

	//solution from leetcode discussion
	public class NumMatrix {

		int[][] tree;
		int[][] nums;
		int m;
		int n;

		public NumMatrix(int[][] matrix) {
			if (matrix.length == 0 || matrix[0].length == 0) return;
			m = matrix.length;
			n = matrix[0].length;
			tree = new int[m+1][n+1];
			nums = new int[m][n];
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					update(i, j, matrix[i][j]);
				}
			}
		}

		public void update(int row, int col, int val) {
			if (m == 0 || n == 0) return;
			int delta = val - nums[row][col];
			nums[row][col] = val;
			for (int i = row + 1; i <= m; i += i & (-i)) {
				for (int j = col + 1; j <= n; j += j & (-j)) {
					tree[i][j] += delta;
				}
			}
		}

		public int sumRegion(int row1, int col1, int row2, int col2) {
			if (m == 0 || n == 0) return 0;
			return sum(row2+1, col2+1) + sum(row1, col1) - sum(row1, col2+1) - sum(row2+1, col1);
		}

		public int sum(int row, int col) {
			int sum = 0;
			for (int i = row; i > 0; i -= i & (-i)) {
				for (int j = col; j > 0; j -= j & (-j)) {
					sum += tree[i][j];
				}
			}
			return sum;
		}
	}
// time should be O(log(m) * log(n))

}

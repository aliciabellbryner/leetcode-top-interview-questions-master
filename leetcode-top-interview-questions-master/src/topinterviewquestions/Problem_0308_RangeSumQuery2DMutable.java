package topinterviewquestions;
/*
Given a 2D matrix matrix, handle multiple queries of the following types:

Update the value of a cell in matrix.
Calculate the sum of the elements of matrix inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
Implement the NumMatrix class:

NumMatrix(int[][] matrix) Initializes the object with the integer matrix matrix.
void update(int row, int col, int val) Updates the value of matrix[row][col] to be val.
int sumRegion(int row1, int col1, int row2, int col2) Returns the sum of the elements of matrix inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).


Example 1:


Input
["NumMatrix", "sumRegion", "update", "sumRegion"]
[[[[3, 0, 1, 4, 2], [5, 6, 3, 2, 1], [1, 2, 0, 1, 5], [4, 1, 0, 1, 7], [1, 0, 3, 0, 5]]], [2, 1, 4, 3], [3, 2, 2], [2, 1, 4, 3]]
Output
[null, 8, null, 10]

Explanation
NumMatrix numMatrix = new NumMatrix([[3, 0, 1, 4, 2], [5, 6, 3, 2, 1], [1, 2, 0, 1, 5], [4, 1, 0, 1, 7], [1, 0, 3, 0, 5]]);
numMatrix.sumRegion(2, 1, 4, 3); // return 8 (i.e. sum of the left red rectangle)
numMatrix.update(3, 2, 2);       // matrix changes from left image to right image
numMatrix.sumRegion(2, 1, 4, 3); // return 10 (i.e. sum of the right red rectangle)


Constraints:

m == matrix.length
n == matrix[i].length
1 <= m, n <= 200
-105 <= matrix[i][j] <= 105
0 <= row < m
0 <= col < n
-105 <= val <= 105
0 <= row1 <= row2 < m
0 <= col1 <= col2 < n
At most 104 calls will be made to sumRegion and update.
 */
//indextree问题，followup里有一个一维的indextree问题，就是从那个里修改过来的
// 提交时把类名和构造函数名从Problem_0308_RangeSumQuery2DMutable改成NumMatrix
public class Problem_0308_RangeSumQuery2DMutable {
	private int[][] tree;//这个的目的是保存matrix从【0，0】到【i，j】位置整个长方形区域的和，// bit tree, sumNums(0->i) will be stored at tree(i+1), tree is reference by Length
	private int[][] nums;// a deep clone of the input matrix. otherwise matrix might be updated by other process
	private int N;
	private int M;

	//https://leetcode.com/problems/range-sum-query-2d-mutable/discuss/75870/Java-2D-Binary-Indexed-Tree-Solution-clean-and-short-17ms/79038
	public Problem_0308_RangeSumQuery2DMutable(int[][] matrix) {
		if (matrix.length == 0 || matrix[0].length == 0) {
			return;
		}
		N = matrix.length;
		M = matrix[0].length;
		tree = new int[N + 1][M + 1];//tree is indexed by rLen & cLen, off-by-one index
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
		for (int i = row + 1; i > 0; i -= i & (-i)) {//in index tree, index of parent of i can be obtained using: parent(i) = i - i & (-i)
			for (int j = col + 1; j > 0; j -= j & (-j)) {
				sum += tree[i][j];//return the sum of tree[i] and all ancestor of i
			}
		}
		return sum;
	}

	// 用户给我的row，col不能越界
	// Function similar to Map.put(Key, Val), key is (row, col), new value is (val)
	public void update(int row, int col, int val) {
		if (N == 0 || M == 0) {
			return;
		}
		int delta = val - nums[row][col];
		nums[row][col] = val;
		for (int i = row + 1; i <= N; i += i & (-i)) {
			for (int j = col + 1; j <= M; j += j & (-j)) {
				tree[i][j] += delta;
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

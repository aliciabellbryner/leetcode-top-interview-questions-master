package topinterviewquestions;

public class Problem_0240_SearchA2DMatrixII {


	//先用这个，虽然时间复杂度没有下面的用二分法的好，但是简单易懂
	//Approach 4: Search Space Reduction,从右上角开始搜
	//time O(M+N), M, N分别是行数列数
	//space O(1)
	public static boolean searchMatrix2(int[][] matrix, int target) {
		if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
			return false;
		}
		int N = matrix.length;
		int M = matrix[0].length;
		int row = 0;
		int col = M - 1;
		while (row < N && col >= 0) {
			if (matrix[row][col] > target) {
				col--;
			} else if (matrix[row][col] < target) {
				row++;
			} else {
				return true;
			}
		}
		return false;
	}

	//https://leetcode.com/problems/search-a-2d-matrix-ii/solution/
	//Approach 2: Binary Search
	//Time complexity : O(log(n!)), n denotes the number of columns., or we can just say O(nlogn).
	// as there are n terms, each no greater than log(n).
	// Therefore, the asymptotic runtime is certainly no worse than that of an O(nlogn) algorithm.
	// space O(1)
	public boolean searchMatrix(int[][] matrix, int target) {
		// an empty matrix obviously does not contain `target`
		if (matrix == null || matrix.length == 0) {
			return false;
		}
		// iterate over matrix diagonals
		int shorterDim = Math.min(matrix.length, matrix[0].length);
		for (int i = 0; i < shorterDim; i++) {
			boolean verticalFound = binarySearch(matrix, target, i, true);
			boolean horizontalFound = binarySearch(matrix, target, i, false);
			if (verticalFound || horizontalFound) {
				return true;
			}
		}
		return false;
	}
	private boolean binarySearch(int[][] matrix, int target, int start, boolean vertical) {
		int l = start;
		int r = vertical ? matrix[0].length-1 : matrix.length-1;
		while (r >= l) {
			int mid = (l + r)/2;
			if (vertical) { // searching a column
				if (matrix[start][mid] < target) {
					l = mid + 1;
				} else if (matrix[start][mid] > target) {
					r = mid - 1;
				} else {
					return true;
				}
			} else { // searching a row
				if (matrix[mid][start] < target) {
					l = mid + 1;
				} else if (matrix[mid][start] > target) {
					r = mid - 1;
				} else {
					return true;
				}
			}
		}
		return false;
	}




}

package topinterviewquestions;

public class Problem_0074_SearchA2DMatrix {
	//Time complexity : O(log(mn)) since it's a standard binary search.
	//Space complexity : O(1).
	public boolean searchMatrix(int[][] matrix, int target) {
		int M = matrix.length, N = matrix[0].length;
		if (M == 0) {
			return false;
		}
		// binary search
		int l = 0, r = M * N - 1;
		int mid;
		while (l <= r) {
			mid = (l + r) / 2;
			if (target == matrix[mid / N][mid % N]) {
				return true;
			} else if (target < matrix[mid / N][mid % N]) {
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}
		return false;
	}

}

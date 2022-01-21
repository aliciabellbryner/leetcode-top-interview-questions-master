package topinterviewquestions;

public class Problem_0251_Flatten2DVector {


	//https://leetcode.com/problems/flatten-2d-vector/discuss/275940/Java-simple-solution
	//leetcode discussion
	class Vector2D {
		int[][] vec;
		int row = 0, col = 0;
		public Vector2D(int[][] vec) {
			this.vec = vec;
		}

		public int next() {
			if (hasNext()) {
				return vec[row][col++];
			} else {
				return -1;
			}
		}

		public boolean hasNext() {
			while (row < vec.length && col == vec[row].length) {  // Move to next available vector，
				// 不能用if因为可能出现比如[[[1],[]]]这样的v，这样如果用if只能跳到row=1, col=0, return true,但实际上这个位置没有元素应该return false
				row++;
				col = 0;
			}
			return row < vec.length;
		}
	}


	//too long!!!
	//zuo's solution: good way to reduce the space complexity. don't use the queue or linkedlist to store the data
	// as it will cost much more space than 2D int array
	public static class Vector2D_2 {
		private int[][] matrix;
		private int row;
		private int col;
		private boolean curUsed;//代表目前的row col用过了没有

		public Vector2D_2(int[][] v) {
			matrix = v;
			row = 0;
			col = -1;
			curUsed = true;
			hasNext();
		}

		public int next() {
			int ans = matrix[row][col];
			curUsed = true;
			hasNext();
			return ans;
		}

		public boolean hasNext() {
			if (row == matrix.length) {
				return false;
			}
			if (!curUsed) {
				return true;
			}
			// (row，col)用过了
			if (col < matrix[row].length - 1) {
				col++;
			} else {
				col = 0;
				do {
					row++;
				} while (row < matrix.length && matrix[row].length == 0);//防止是空的array
			}
			// 新的(row，col)
			if (row != matrix.length) {
				curUsed = false;
				return true;
			} else {
				return false;
			}
		}

	}

}

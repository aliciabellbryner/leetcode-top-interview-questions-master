package topinterviewquestions;

public class Problem_0251_Flatten2DVector {

	//zuo's solution: good way to reduce the space complexity. don't use the queue or linkedlist to store the data as it will cost much more space than this
	public static class Vector2D {
		private int[][] matrix;
		private int row;
		private int col;
		private boolean curUsed;//代表目前的row col用过了没有

		public Vector2D(int[][] v) {
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

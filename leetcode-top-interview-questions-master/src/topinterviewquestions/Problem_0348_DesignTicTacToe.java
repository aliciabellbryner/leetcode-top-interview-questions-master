package topinterviewquestions;

public class Problem_0348_DesignTicTacToe {

	class TicTacToe {
		private int[][] rows;
		private int[][] cols;
		private int[] leftUp;
		private int[] rightUp;
		private boolean[][] matrix;
		private int N;

		public TicTacToe(int n) {
			rows = new int[n][3]; //0 1 2 0列不用，1，2代表1玩家2玩家
			cols = new int[n][3];
			leftUp = new int[3]; //  1 2
			rightUp = new int[3]; // 1 2
			matrix = new boolean[n][n];
			N = n;
		}

		public int move(int row, int col, int player) {
			if (matrix[row][col]) {//下过了，所以没人赢返回0，而且这次下无效
				return 0;
			}
			matrix[row][col] = true;
			rows[row][player]++;
			cols[col][player]++;
			if (row == col) {
				leftUp[player]++;
			}
			if (row + col == N - 1) {
				rightUp[player]++;
			}
			if (rows[row][player] == N || cols[col][player] == N || leftUp[player] == N || rightUp[player] == N) {
				return player;
			}
			return 0;
		}

	}

}

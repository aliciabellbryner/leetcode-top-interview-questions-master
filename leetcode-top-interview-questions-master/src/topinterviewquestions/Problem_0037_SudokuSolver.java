package topinterviewquestions;

public class Problem_0037_SudokuSolver {

	public static void solveSudoku(char[][] board) {
		boolean[][] row = new boolean[9][10];
		boolean[][] col = new boolean[9][10];
		boolean[][] bucket = new boolean[9][10];
		initMaps(board, row, col, bucket);
		process(board, 0, 0, row, col, bucket);
	}

	public static void initMaps(char[][] board, boolean[][] row, boolean[][] col, boolean[][] bucket) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int bid = 3 * (i / 3) + (j / 3);//这一步是关键，它把每一个3*3独立出来到bucket的每一行中
				if (board[i][j] != '.') {
					int num = board[i][j] - '0';
					row[i][num] = true;
					col[j][num] = true;
					bucket[bid][num] = true;
				}
			}
		}
	}

	public static boolean process(char[][] board, int i, int j, boolean[][] row, boolean[][] col, boolean[][] bucket) {
		if (i == 9) {
			return true;
		}
		//填表顺序是从第一行从左到右，然后再第2，3，4.。。。行直到最右下角
		int nexti = j != 8 ? i : i + 1;
		int nextj = j != 8 ? j + 1 : 0;
		if (board[i][j] != '.') {
			return process(board, nexti, nextj, row, col, bucket);
		} else {
			int bid = 3 * (i / 3) + (j / 3);
			for (int num = 1; num <= 9; num++) {
				if ((!row[i][num]) && (!col[j][num]) && (!bucket[bid][num])) {//剪枝！！！减少时间，非常关键！
					row[i][num] = true;
					col[j][num] = true;
					bucket[bid][num] = true;
					board[i][j] = (char) (num + '0');
					if (process(board, nexti, nextj, row, col, bucket)) {//dfs
						return true;
					}
					//接下来一定要做深度优先遍历的必须一步：清理现场！！！
					row[i][num] = false;
					col[j][num] = false;
					bucket[bid][num] = false;
					board[i][j] = '.';
				}
			}
			return false;
		}
	}

}

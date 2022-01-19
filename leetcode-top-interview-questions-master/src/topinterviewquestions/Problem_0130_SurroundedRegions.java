package topinterviewquestions;

public class Problem_0130_SurroundedRegions {

	// 从边界开始感染的方法，比第一种方法更好
	//time O(MN)
	//space O(MN): recursion depth, if all the element is 0, then dfs need to recurse to all the MN element
	public static void solve(char[][] board) {
		int M = board.length, N = board[0].length;
		if (board == null || M == 0 || board[0] == null || N == 0) {
			return;
		}
		for (int j = 0; j < N; j++) {
			if (board[0][j] == 'O') {
				free(board, 0, j);
			}
			if (board[M - 1][j] == 'O') {
				free(board, M - 1, j);
			}
		}
		for (int i = 1; i < M - 1; i++) {
			if (board[i][0] == 'O') {
				free(board, i, 0);
			}
			if (board[i][N - 1] == 'O') {
				free(board, i, N - 1);
			}
		}
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] == 'O') {//千万不能把下面的判断F的调换次序！！！一定要先判断这个！！！
					board[i][j] = 'X';
				}
				if (board[i][j] == 'F') {
					board[i][j] = 'O';
				}
			}
		}
	}

	public static void free(char[][] board, int i, int j) {
		if (i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] != 'O') {
			return;
		}
		board[i][j] = 'F';
		free(board, i + 1, j);
		free(board, i - 1, j);
		free(board, i, j + 1);
		free(board, i, j - 1);
	}


	//not as good as the one above
	public static void solve1(char[][] board) {
		boolean[] ans = new boolean[1];
		int M = board.length, N = board[0].length;
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] == 'O') {
					ans[0] = true;
					can(board, i, j, ans);
					board[i][j] = ans[0] ? 'T' : 'F';
				}
			}
		}
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				char can = board[i][j];
				if (can == 'T' || can == 'F') {
					board[i][j] = '.';
					change(board, i, j, can);
				}
			}
		}

	}

	public static void can(char[][] board, int i, int j, boolean[] ans) {
		int M = board.length, N = board[0].length;
		if (i < 0 || i == M || j < 0 || j == N) {
			ans[0] = false;
			return;
		}
		if (board[i][j] == 'O') {
			board[i][j] = '.';
			can(board, i - 1, j, ans);
			can(board, i + 1, j, ans);
			can(board, i, j - 1, ans);
			can(board, i, j + 1, ans);
		}
	}

	public static void change(char[][] board, int i, int j, char can) {
		int M = board.length, N = board[0].length;
		if (i < 0 || i == M || j < 0 || j == N) {
			return;
		}
		if (board[i][j] == '.') {
			board[i][j] = can == 'T' ? 'X' : 'O';
			change(board, i - 1, j, can);
			change(board, i + 1, j, can);
			change(board, i, j - 1, can);
			change(board, i, j + 1, can);
		}
	}

}

package topinterviewquestions;

// 有关这个游戏更有意思、更完整的内容：
// https://www.bilibili.com/video/BV1rJ411n7ri
// 也推荐这个up主
public class Problem_0289_GameOfLife {

	//time O(MN) as the two for loop
	// space O(1)
	public static void gameOfLife(int[][] board) {
		int N = board.length;
		int M = board[0].length;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				int neighbors = neighbors(board, i, j);
				if (neighbors == 3 || (board[i][j] == 1 && neighbors == 2)) {
					//neighbors == 3： Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
					//(board[i][j] == 1 && neighbors == 2)： Any live cell with two or three live neighbors lives on to the next generation.
					set(board, i, j);
				}
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				board[i][j] = get(board, i, j);
			}
		}
	}

	public static int neighbors(int[][] board, int i, int j) {
		int count = 0;
		count += isValidLive(board, i - 1, j - 1) ? 1 : 0;
		count += isValidLive(board, i - 1, j) ? 1 : 0;
		count += isValidLive(board, i - 1, j + 1) ? 1 : 0;
		count += isValidLive(board, i, j - 1) ? 1 : 0;
		count += isValidLive(board, i, j + 1) ? 1 : 0;
		count += isValidLive(board, i + 1, j - 1) ? 1 : 0;
		count += isValidLive(board, i + 1, j) ? 1 : 0;
		count += isValidLive(board, i + 1, j + 1) ? 1 : 0;
		return count;
	}

	public static boolean isValidLive(int[][] board, int i, int j) {
		return i >= 0 && i < board.length && j >= 0 && j < board[0].length && (board[i][j] & 1) == 1;
		//这个地方一定不能写成board[i][j] == 1，因为是要算周边的之前活着的cell数量，因为之前是死的cell如果满足条件变成了活的，那么他们会从00 -> 10
		//之前活的如果还是活的，会从01 -> 11,所以我们必须看最后一位是1还是0，如果只判断是不是1那会错过之前是活的并且活下去的cell
	}

	public static void set(int[][] board, int i, int j) {//意味着可以延续到下一代的，就把他变成2，也就是二进制的‘10’
		board[i][j] |= 2;
		//live -> live: 01 -> 11
		//dead -> live: 00 -> 10
	}

	public static int get(int[][] board, int i, int j) {//所有能延续到下一代的之前是live是‘11’，所有之前死的能延续到下一代的是10，右移一位都变成1，所有不能延续到下一代的1或者0都变成0
		return board[i][j] >> 1;
	}

}

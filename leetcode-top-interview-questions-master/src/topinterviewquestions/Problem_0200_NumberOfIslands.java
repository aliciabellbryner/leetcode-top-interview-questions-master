package topinterviewquestions;

import java.util.LinkedList;
import java.util.Queue;

public class Problem_0200_NumberOfIslands {

	//DFS：
	// Time complexity : O(M×N) where M is the number of rows and N is the number of columns.
	//Space complexity : worst case O(M×N) in case that the grid map is filled with lands where DFS goes by M×N deep.
	public static int numIslands(char[][] m) {
		if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
			return 0;
		}
		int N = m.length;
		int M = m[0].length;
		int res = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (m[i][j] == '1') {
					res++;
					infect(m, i, j, N, M);
				}
			}
		}
		return res;
	}

	// 目前来到m[i][j], 经历上下左右的感染过程
	public static void infect(char[][] m, int i, int j, int N, int M) {
		if (i < 0 || i >= N || j < 0 || j >= M || m[i][j] != '1') {
			return;
		}
		m[i][j] = '2';//改成'0'也行
		infect(m, i + 1, j, N, M);
		infect(m, i - 1, j, N, M);
		infect(m, i, j + 1, N, M);
		infect(m, i, j - 1, N, M);
	}


	//BFS
	//Time complexity : O(M×N) where MM is the number of rows and NN is the number of columns.
	//Space complexity : O(min(M,N)) because in worst case where the grid is filled with lands,
	// the size of queue can grow up to min(M,N). https://imgur.com/gallery/M58OKvB
	public int numIslands2(char[][] grid) {
		if (grid == null || grid.length == 0) {
			return 0;
		}

		int M = grid.length;
		int N = grid[0].length;
		int res = 0;

		for (int i = 0; i < M; ++i) {
			for (int j = 0; j < N; ++j) {
				if (grid[i][j] == '1') {
					++res;
					grid[i][j] = '2'; // mark as visited
					Queue<Integer> queue = new LinkedList<>();
					//the queue is to store grid[i][j]'s neighbors's 1D index who have not been visited and == '1'
					queue.add(i * N + j);
					while (!queue.isEmpty()) {
						int idx = queue.remove();
						int row = idx / N;
						int col = idx % N;
						if (row - 1 >= 0 && grid[row-1][col] == '1') {
							queue.add((row-1) * N + col);
							grid[row-1][col] = '0';
						}
						if (row + 1 < M && grid[row+1][col] == '1') {
							queue.add((row+1) * N + col);
							grid[row+1][col] = '0';
						}
						if (col - 1 >= 0 && grid[row][col-1] == '1') {
							queue.add(row * N + col-1);
							grid[row][col-1] = '0';
						}
						if (col + 1 < N && grid[row][col+1] == '1') {
							queue.add(row * N + col+1);
							grid[row][col+1] = '0';
						}
					}
				}
			}
		}

		return res;
	}

}

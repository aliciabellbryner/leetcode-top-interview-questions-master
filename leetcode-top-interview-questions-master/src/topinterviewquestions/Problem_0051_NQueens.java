package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Problem_0051_NQueens {

	//题意先搞清，需要每行、每列、每个对角线、反对角线都只有一个queen
	//Complexity Analysis
	//Given N as the number of queens (which is the same as the width and height of the board),
	//Time complexity: O(N!)
	//Unlike the brute force approach, we will only place queens on squares that aren't under attack.
	// For the first queen, we have N options. For the next queen, we won't attempt to place it in the
	// same column as the first queen, and there must be at least one square attacked diagonally by the first queen as well.
	// Thus, the maximum number of squares we can consider for the second queen is N - 2N−2.
	// For the third queen, we won't attempt to place it in 2 columns already occupied by the first 2 queens, and there must
	// be at least two squares attacked diagonally from the first 2 queens. Thus, the maximum number of squares we can consider
	// for the third queen is N - 4N−4. This pattern continues, resulting in an approximate time complexity of N!.
	//While it costs O(N^2) to build each valid solution, the amount of valid solutions S(N) does not grow nearly as fast as N!, so O(N! + S(N) * N^2) = O(N!)O(N!+S(N)∗N^2)=O(N!)
	//Space complexity: O(N^2)
	//Extra memory used includes the 3 sets used to store board state, as well as the recursion call stack. All of this scales linearly with the number of queens. However, to keep the board state costs O(N^2)
	//since the board is of size N * N. Space used for the output does not count towards space complexity.
	class Solution {
		private int size;
		private List<List<String>> res = new ArrayList<List<String>>();

		public List<List<String>> solveNQueens(int n) {
			size = n;
			char emptyBoard[][] = new char[size][size];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					emptyBoard[i][j] = '.';
				}
			}

			backtrack(0, new HashSet<>(), new HashSet<>(), new HashSet<>(), emptyBoard);
			return res;
		}

		// Making use of a helper function to get the
		// solutions in the correct output format
		// 产生每一行的String放到res中去
		private List<String> createBoard(char[][] state) {
			List<String> board = new ArrayList<String>();
			for (int row = 0; row < size; row++) {
				String current_row = new String(state[row]);
				board.add(current_row);
			}

			return board;
		}

		private void backtrack(int row, Set<Integer> diagonals, Set<Integer> antiDiagonals, Set<Integer> cols, char[][] state) {
			// Base case - N queens have been placed
			if (row == size) {
				res.add(createBoard(state));
				return;
			}

			for (int col = 0; col < size; col++) {
				int currDiagonal = row - col;
				int currAntiDiagonal = row + col;
				// If the queen is not placeable
				if (cols.contains(col) || diagonals.contains(currDiagonal) || antiDiagonals.contains(currAntiDiagonal)) {
					continue;
				}

				// "Add" the queen to the board
				cols.add(col);
				diagonals.add(currDiagonal);
				antiDiagonals.add(currAntiDiagonal);
				state[row][col] = 'Q';

				// Move on to the next row with the updated board state
				backtrack(row + 1, diagonals, antiDiagonals, cols, state);

				// "Remove" the queen from the board since we have already
				// explored all valid paths using the above function call
				// recover恢复现场
				cols.remove(col);
				diagonals.remove(currDiagonal);
				antiDiagonals.remove(currAntiDiagonal);
				state[row][col] = '.';
			}
		}
	}

}

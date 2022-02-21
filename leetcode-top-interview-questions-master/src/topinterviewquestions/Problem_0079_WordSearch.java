package topinterviewquestions;
/*
Given an m x n grid of characters board and a string word, return true if word exists in the grid.

The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.

Example 1:


Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
Output: true
Example 2:


Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
Output: true
Example 3:


Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
Output: false

Constraints:

m == board.length
n = board[i].length
1 <= m, n <= 6
1 <= word.length <= 15
board and word consists of only lowercase and uppercase English letters.

 */
public class Problem_0079_WordSearch {

	//Time Complexity:O(MN3^L) where MN is the number of row and col in the board and L is the length of the word to be matched.
	//For the backtracking function, initially we could have at most 4 directions to explore, but further the choices are reduced into 3 (since we won't go back to where we come from).
	//space The main consumption of the memory lies in the recursion call of the backtracking function. The maximum length of the call stack would be the length of the word. Therefore, the space complexity of the algorithm is O(L).
	public boolean exist(char[][] board, String word) {
		if (board == null || board.length == 0 || board[0].length == 0) {
			return false;
		}
		int M = board.length;
		int N = board[0].length;
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] == word.charAt(0)) {
					if (process(board, i, j, 0, word)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	private boolean process(char[][] board, int i, int j, int index, String word) {
		int M = board.length;
		int N = board[0].length;
		if (index == word.length()) {
			return true;
		} else {
			if (i < 0 || i >= M || j < 0 || j >= N || word.charAt(index) != board[i][j]) {
				return false;
			}
			board[i][j] ^= 256;//use 256 as all the letter is in the range of [0, 256), so the 9th is 0, by doing a xor ^异或，the 9th will definitely change to 1, which will never be similiar with other elements
			boolean res = process(board, i+1, j, index+1, word) || process(board, i-1, j, index+1, word) || process(board, i, j+1, index+1, word) || process(board, i, j-1, index+1, word);
			board[i][j] ^= 256;//doing this second to recover the element to its original value
			return res;
		}
	}


	//不要用这个办法，time cost too much because of the line 33-36 recursion cost too much, use the prefix tree method in problem 212
	public static boolean exist1(char[][] board, String word) {
		char[] w = word.toCharArray();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (process(board, i, j, w, 0)) {
					return true;
				}
			}
		}
		return false;
	}

	// 目前到达了b[i][j]，word[k....]
	// 从b[i][j]出发，能不能搞定word[k....]  true  false
	public static boolean process(char[][] b, int i, int j, char[] w, int k) {
        if(k == w.length) {
			return true;
		}
        // k 有字符
		if (i < 0 || i == b.length || j < 0 || j == b[0].length) {
			return false;
		}
		if (b[i][j] != w[k]) {
			return false;
		}
		char tmp = b[i][j];
		b[i][j] = 0;//一定要改，不然会重复走，重复利用元素
		boolean ans =  process(b, i - 1, j, w, k + 1) 
				|| process(b, i + 1, j, w, k + 1) 
				|| process(b, i, j - 1, w, k + 1)
				|| process(b, i, j + 1, w, k + 1);
		b[i][j] = tmp;
		return ans;
	}

//一样正确的方法
public static boolean exist_j(char[][] board, String word) {
	for (int i = 0; i < board.length; i++) {
		for (int j = 0; j < board[0].length; j++) {
			if (process(board, i, j, word, 0)) {
				return true;
			}
		}
	}
	return false;
}

	public static boolean process(char[][] b, int i, int j, String word, int index) {
		if(index == word.length()) {
			return true;
		} else if (i >= 0 && i < b.length && j >= 0 && j < b[0].length && b[i][j] == word.charAt(index)) {
			char tmp = b[i][j];
			b[i][j] = 0;//一定要改，不然会重复走，重复利用元素
			boolean ans =  process(b, i - 1, j, word, index + 1)
					|| process(b, i + 1, j, word, index + 1)
					|| process(b, i, j - 1, word, index + 1)
					|| process(b, i, j + 1, word, index + 1);
			b[i][j] = tmp;
			return ans;
		} else {
			return false;
		}
	}

}

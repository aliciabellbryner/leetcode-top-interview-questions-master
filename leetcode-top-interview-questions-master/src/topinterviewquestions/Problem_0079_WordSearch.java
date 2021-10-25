package topinterviewquestions;

public class Problem_0079_WordSearch {

	//不要用这个办法，time cost too much because of the line 33-36 recursion cost too much, use the prefix tree method in problem 212
	public static boolean exist(char[][] board, String word) {
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

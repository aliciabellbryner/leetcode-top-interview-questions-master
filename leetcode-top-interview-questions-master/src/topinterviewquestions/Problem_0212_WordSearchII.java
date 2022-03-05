package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Problem_0212_WordSearchII {

	//leetcode diss
	//https://leetcode.com/problems/word-search-ii/discuss/59780/Java-15ms-Easiest-Solution-(100.00)
	//Intuitively, start from every cell and try to build a word in the dictionary. Backtracking (dfs) is the powerful way to exhaust every possible ways. Apparently, we need to do pruning when current character is not in any word.
	//
	//How do we instantly know the current character is invalid? HashMap?
	//How do we instantly know what's the next valid character? LinkedList?
	//But the next character can be chosen from a list of characters. "Mutil-LinkedList"?
	//Combing them, Trie is the natural choice. Notice that:
	//
	//TrieNode is all we need. search and startsWith are useless.
	//No need to store character at TrieNode. c.next[i] != null is enough.
	//Never use c1 + c2 + c3. Use StringBuilder.
	//No need to use O(n^2) extra space visited[m][n].
	//No need to use StringBuilder. Storing word itself at leaf node is enough.
	//No need to use HashSet to de-duplicate. Use "one time search" trie.
	//For more explanations, check out dietpepsi's blog.
	class Solution {
		public List<String> findWords(char[][] board, String[] words) {
			List<String> res = new ArrayList<>();
			TrieNode root = buildTrie(words);
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					dfs (board, i, j, root, res);
				}
			}
			return res;
		}

		public void dfs(char[][] board, int i, int j, TrieNode p, List<String> res) {
			char c = board[i][j];
			if (c == '#' || p.next[c - 'a'] == null) return;
			p = p.next[c - 'a'];
			if (p.end != null) {   // found one
				res.add(p.end);
				p.end = null;     // de-duplicate
			}

			board[i][j] = '#';
			if (i > 0) dfs(board, i - 1, j ,p, res);
			if (j > 0) dfs(board, i, j - 1, p, res);
			if (i < board.length - 1) dfs(board, i + 1, j, p, res);
			if (j < board[0].length - 1) dfs(board, i, j + 1, p, res);
			board[i][j] = c;
		}

		public TrieNode buildTrie(String[] words) {
			TrieNode root = new TrieNode();
			for (String w : words) {
				TrieNode p = root;
				for (char c : w.toCharArray()) {
					int i = c - 'a';
					if (p.next[i] == null) p.next[i] = new TrieNode();
					p = p.next[i];
				}
				p.end = w;
			}
			return root;
		}

		class TrieNode {
			TrieNode[] next = new TrieNode[26];
			String end;//means it has the word end here, word exists in this trie tree
		}
	}


	//zuo's solution
	class Solution2 {


		public static class TrieNode {
			public TrieNode[] nexts;
			public int pass;
			public int end;

			public TrieNode() {
				nexts = new TrieNode[26];
				pass = 0;
				end = 0;
			}
		}

		public static List<String> findWords(char[][] board, String[] words) {
			TrieNode head = new TrieNode(); // 前缀树最顶端的头
			HashSet<String> set = new HashSet<>();//to avoid duplicate
			for (String word : words) {
				if (!set.contains(word)) {
					fillWord(head, word);
					set.add(word);
				}
			}
			// 答案
			List<String> res = new ArrayList<>();
			// 沿途走过的字符，收集起来，存在path里
			List<Character> path = new ArrayList<>();
			for (int row = 0; row < board.length; row++) {
				for (int col = 0; col < board[0].length; col++) {
					// 枚举在board中的所有位置
					// 每一个位置出发的情况下，答案都收集
					process(board, row, col, path, head, res);
				}
			}
			return res;
		}

		public static void fillWord(TrieNode node, String word) {
			node.pass++;
			int index = 0;
			for (int i = 0; i < word.length(); i++) {
				index = word.charAt(i) - 'a';
				if (node.nexts[index] == null) {
					node.nexts[index] = new TrieNode();
				}
				node = node.nexts[index];
				node.pass++;
			}
			node.end++;
		}

		// 从board[row][col]位置的字符出发，
		// 之前的路径上，走过的字符，记录在path里
		// cur还没有登上，有待检查能不能登上去的前缀树的节点
		// 如果找到words中的某个str，就记录在 res里
		// 返回值，从row,col 出发，一共找到了多少个str
		public static int process(char[][] board, int row, int col, List<Character> path, TrieNode cur, List<String> res) {
			char cha = board[row][col];
			if (cha == 0) { // 这个row col位置是之前走过的位置
				return 0;
			}
			// (row,col) 不是回头路
			int index = cha - 'a';
			// 如果没路，或者这条路上最终的字符串之前加入过结果里
			if (cur.nexts[index] == null || cur.nexts[index].pass == 0) {//非常重要！这个就是为什么process返回值fix要设为从row， col出发一共找到的多少个str的数量int了
				return 0;
			}
			// 没有走回头路且能登上去
			cur = cur.nexts[index];
			path.add(cha);// 当前位置的字符加到路径里去
			int fix = 0; // 从row和col位置出发，后续一共搞定了多少答案
			// 当我来到row col位置，如果决定不往后走了。是不是已经搞定了某个字符串了
			if (cur.end > 0) {
				res.add(generatePath(path));
				cur.end--;
				fix++;
			}
			// 往上、下、左、右，四个方向尝试
			board[row][col] = 0;//把它变成其他的值
			if (row > 0) {
				fix += process(board, row - 1, col, path, cur, res);
			}
			if (row < board.length - 1) {
				fix += process(board, row + 1, col, path, cur, res);
			}
			if (col > 0) {
				fix += process(board, row, col - 1, path, cur, res);
			}
			if (col < board[0].length - 1) {
				fix += process(board, row, col + 1, path, cur, res);
			}
			board[row][col] = cha;//backtracking recovering//把它变回原先的值
			path.remove(path.size() - 1);
			cur.pass -= fix;//这个非常重要，把fix减去之后然后在上面line72判断pass是不是等于0才可以判断前面的string串是否以及用完，如果pass=0那么就直接返回0
			return fix;
		}

		public static String generatePath(List<Character> path) {
			char[] str = new char[path.size()];
			int index = 0;
			for (Character cha : path) {
				str[index++] = cha;
			}
			return String.valueOf(str);
		}

	}
}

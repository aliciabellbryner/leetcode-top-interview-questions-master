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
			TrieNode head = new TrieNode(); // ????????????????????????
			HashSet<String> set = new HashSet<>();//to avoid duplicate
			for (String word : words) {
				if (!set.contains(word)) {
					fillWord(head, word);
					set.add(word);
				}
			}
			// ??????
			List<String> res = new ArrayList<>();
			// ?????????????????????????????????????????????path???
			List<Character> path = new ArrayList<>();
			for (int row = 0; row < board.length; row++) {
				for (int col = 0; col < board[0].length; col++) {
					// ?????????board??????????????????
					// ???????????????????????????????????????????????????
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

		// ???board[row][col]????????????????????????
		// ????????????????????????????????????????????????path???
		// cur?????????????????????????????????????????????????????????????????????
		// ????????????words????????????str??????????????? res???
		// ???????????????row,col ?????????????????????????????????str
		public static int process(char[][] board, int row, int col, List<Character> path, TrieNode cur, List<String> res) {
			char cha = board[row][col];
			if (cha == 0) { // ??????row col??????????????????????????????
				return 0;
			}
			// (row,col) ???????????????
			int index = cha - 'a';
			// ???????????????????????????????????????????????????????????????????????????
			if (cur.nexts[index] == null || cur.nexts[index].pass == 0) {//????????????????????????????????????process?????????fix????????????row??? col??????????????????????????????str?????????int???
				return 0;
			}
			// ?????????????????????????????????
			cur = cur.nexts[index];
			path.add(cha);// ???????????????????????????????????????
			int fix = 0; // ???row???col????????????????????????????????????????????????
			// ????????????row col?????????????????????????????????????????????????????????????????????????????????
			if (cur.end > 0) {
				res.add(generatePath(path));
				cur.end--;
				fix++;
			}
			// ?????????????????????????????????????????????
			board[row][col] = 0;//????????????????????????
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
			board[row][col] = cha;//backtracking recovering//????????????????????????
			path.remove(path.size() - 1);
			cur.pass -= fix;//????????????????????????fix???????????????????????????line72??????pass???????????????0????????????????????????string??????????????????????????????pass=0?????????????????????0
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

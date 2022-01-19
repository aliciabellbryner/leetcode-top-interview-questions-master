package topinterviewquestions;

import java.util.*;

public class Problem_0139_WordBreak {

	//this is the best solution O(N^2) 用的是int[] dp, trie tree + dp
	// Space complexity : O(N). The depth of recursion tree can go up to N.
	public static class Node {//前缀树的经典写法
		public boolean end;//代表是不是一个string的结束
		public Node[] nexts;

		public Node() {
			end = false;
			nexts = new Node[26];
		}
	}
	//利用前缀树Trie tree来做，time O(N^2), space O(N)
	public static boolean wordBreak4(String s, List<String> wordDict) {
		Node root = new Node();
		for (String str : wordDict) {
			char[] chs = str.toCharArray();
			Node node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					node.nexts[index] = new Node();
				}
				node = node.nexts[index];
			}
			node.end = true;
		}
		int N = s.length();
		int[] dp = new int[N + 1];//dp[i] means how many ways we can use all the word in wordDict to form s[N...](从第N个元素开始（包括N）到s最后）
		dp[N] = 1;//不选wordDict中任何word则就可以构成空字符串
		for (int index = N - 1; index >= 0; index--) {
			Node cur = root;
			for (int end = index; end < N; end++) {
				cur = cur.nexts[s.charAt(end) - 'a'];
				if (cur == null) {
					break;
				}
				if (cur.end) {
					dp[index] += dp[end + 1];
				}
			}
		}
		return dp[0] != 0;
	}


	//用的是boolean[] dp
	public static boolean wordBreak(String s, List<String> wordDict) {
		Node root = new Node();
		for (String str : wordDict) {
			char[] chs = str.toCharArray();
			Node node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					node.nexts[index] = new Node();
				}
				node = node.nexts[index];
			}
			node.end = true;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		boolean[] dp = new boolean[N + 1];
		dp[N] = true;
		for (int index = N - 1; index >= 0; index--) {
			Node cur = root;
			for (int end = index; end < N; end++) {
				int path = str[end] - 'a';
				if (cur.nexts[path] == null) {//this step cost O(1), this is the place that is much better than solution2 and 3 as it reduce the total time to O(N^2) from O(N^3)
					break;
				}
				cur = cur.nexts[path];
				if (cur.end && dp[end + 1]) {
					dp[index] = true;
					break;
				}
			}
		}
		return dp[0];
	}

	//time complexity: O(N^3): as there are three steps cost O(N),
	// space O(N)
	//wordBreak2's dp solution
	public static boolean wordBreak3(String s, List<String> wordDict) {
		HashSet<String> set = new HashSet<>(wordDict);
		int N = s.length();
		int[] dp = new int[N + 1];
		// dp[i] = process(s, i, set)的返回值
		dp[N] = 1;
		for (int index = N - 1; index >= 0; index--) {//this loop cost O(N)
			int ways = 0;
			for (int end = index; end < s.length(); end++) {//this loop cost O(N)
				// s[index....end]
				String pre = s.substring(index, end + 1);
				if (set.contains(pre)) {//this check cost O(N),因为如果对于查询的个体是长度很小的object，那可以认为每一次的查询是O（1）的时间复杂度，但是如果长度很长，那因为每次都要遍历一遍，所以变成O（N）（N是单样本长度）
					ways += dp[end + 1];
				}
			}
			dp[index] = ways;
		}
		return dp[0] != 0;
	}


	//solution BFS
	//Time complexity : O(n^3) For every starting index, the search can continue till the end of the given string.
	//Space complexity : O(n). Queue of at most nn size is needed.
	public boolean wordBreak_bfs(String s, List<String> wordDict) {
		Set<String> wordDictSet = new HashSet<>(wordDict);
		Queue<Integer> queue = new LinkedList<>();
		boolean[] visited = new boolean[s.length()];
		queue.add(0);
		while (!queue.isEmpty()) {
			int start = queue.poll();
			if (visited[start]) {
				continue;
			}
			for (int end = start + 1; end <= s.length(); end++) {
				if (wordDictSet.contains(s.substring(start, end))) {
					queue.add(end);
					if (end == s.length()) {
						return true;
					}
				}
			}
			visited[start] = true;
		}
		return false;
	}


}

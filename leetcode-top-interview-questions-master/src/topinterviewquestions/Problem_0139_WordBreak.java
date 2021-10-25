package topinterviewquestions;

import java.util.HashSet;
import java.util.List;

public class Problem_0139_WordBreak {

	//this is the best solution O(N^2) 用的是int[] dp
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
		char[] str = s.toCharArray();
		int N = str.length;
		int[] dp = new int[N + 1];
		dp[N] = 1;
		for (int index = N - 1; index >= 0; index--) {
			Node cur = root;
			for (int end = index; end < N; end++) {
				cur = cur.nexts[str[end] - 'a'];
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

	//time limit exceeded!!!
	public static boolean wordBreak2(String s, List<String> wordDict) {
		return process(s, 0, new HashSet<>(wordDict)) != 0;
	}

	// s[0......index-1]这一段，已经分解过了，不用在操心
	// s[index.....] 这一段字符串，能够被分解的方法数，返回
	public static int process(String s, int index, HashSet<String> wordDict) {
		if (index == s.length()) {
			return 1;
		}
		// index没到最后
		// index...index
		// index...index+1
		// index....index+2
		// index....end
		int ways = 0;
		for (int end = index; end < s.length(); end++) {
			// s[index....end]
			String pre = s.substring(index, end + 1);
			if (wordDict.contains(pre)) {
				ways += process(s, end + 1, wordDict);
			}
		}
		return ways;
	}

	//time complexity: O(N^3): as there are three steps cost O(N)
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

	public static class Node {//前缀树的经典写法
		public boolean end;//代表是不是一个string的结束
		public Node[] nexts;

		public Node() {
			end = false;
			nexts = new Node[26];
		}
	}



}

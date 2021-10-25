package topinterviewquestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Problem_0131_PalindromePartitioning {

	//Zuo's solution: use dp to store the palindrome info to save a lot of time!
	public static List<List<String>> partition(String s) {
		// dp[L][R] -> 是不是回文
		boolean[][] dp = getdp(s.toCharArray());
		LinkedList<String> path = new LinkedList<>();
		List<List<String>> ans = new ArrayList<>();
		process(s, 0, path, dp, ans);
		return ans;
	}

	public static boolean[][] getdp(char[] str) {
		int N = str.length;
		boolean[][] dp = new boolean[N][N];
		for (int i = 0; i < N - 1; i++) {
			dp[i][i] = true;
			dp[i][i + 1] = str[i] == str[i + 1];
		}
		dp[N - 1][N - 1] = true;
		for (int j = 2; j < N; j++) {
			int row = 0;
			int col = j;
			while (col < N) {
				dp[row][col] = str[row] == str[col] && dp[row + 1][col - 1];
				row++;
				col++;
			}
		}
		return dp;
	}

	// s 字符串
	// s[0...index-1] 已经做过的决定，放入了path中
	// 在index开始做属于这个位置的决定，
	// index == s.len  path之前做的决定（一种分割方法），放进总答案ans里
	public static void process(String s, int index, LinkedList<String> path, 
			boolean[][] dp, List<List<String>> ans) {
		if (index == s.length()) {
			ans.add(new ArrayList(path));
		} else {
			for (int end = index; end < s.length(); end++) {
				// index..index   
				// index..index+1
				// index..index+2
				// index..end
				if (dp[index][end]) {
					path.add(s.substring(index, end + 1));
					process(s, end + 1, path, dp, ans);
					path.remove(path.size()-1);
				}
			}
		}
	}

	public static List<String> copy(List<String> path) {
		List<String> ans = new ArrayList<>();
		for (String p : path) {
			ans.add(p);
		}
		return ans;
	}


	//my solution: didn't use dp, so it will cost much more time to run the ispalindrome everytime
	public List<List<String>> partition_j(String s) {
		List<List<String>> res = new ArrayList<>();
		if (s == null || s.length() == 0) {
			return res;
		}
		// boolean[][] dp = getdp(s.toCharArray());
		LinkedList<String> path = new LinkedList<>();
		List<List<String>> ans = new ArrayList<>();
		process(s, 0, path, ans);
		return ans;
	}
	public void process(String s, int index, LinkedList<String> path,
						List<List<String>> ans) {
		if (index == s.length()) {
			ans.add(new ArrayList(path));
		} else {
			for (int end = index; end < s.length(); end++) {
				if (isPalindrome(s.substring(index, end+1))) {
					path.addLast(s.substring(index, end + 1));
					process(s, end + 1, path, ans);
					path.pollLast();
				}
			}
		}
	}

	public boolean isPalindrome(String s) {
		if (s == null || s.length() == 0) {
			return true;
		}
		int l = 0;
		int r = s.length() - 1;
		while (l < r) {
			if (s.charAt(l) != s.charAt(r)) {
				return false;
			}
			l++;
			r--;
		}
		return true;
	}
}

package topinterviewquestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/*
Given a string s, partition s such that every substring of the partition is a palindrome. Return all possible palindrome partitioning of s.

A palindrome string is a string that reads the same backward as forward.



Example 1:

Input: s = "aab"
Output: [["a","a","b"],["aa","b"]]
Example 2:

Input: s = "a"
Output: [["a"]]


Constraints:

1 <= s.length <= 16
s contains only lowercase English letters.
 */
public class Problem_0131_PalindromePartitioning {


	//Approach 1: Backtracking
	//https://leetcode.com/problems/palindrome-partitioning/solution/
	//Time Complexity : O(N*2^N) where N is the length of string ss. This is the worst-case time complexity when all the possible substrings are palindrome.
	//Hence, there could be 2^N possible substrings in the worst case. For each substring, it takes O(N) time to generate substring and determine if it a palindrome or not.
	// This gives us time complexity as O(N*2^N)
	//Space Complexity: O(N), where N is the length of the string ss. This space will be used to store the recursion stack.
	// For s = aaa, the maximum depth of the recursive call stack is 3 which is equivalent to N.
	public List<List<String>> partition(String s) {
		List<List<String>> res = new ArrayList<>();
		dfs(0, res, new ArrayList<>(), s);
		return res;
	}

	void dfs(int start, List<List<String>> result, List<String> path, String s) {
		if (start == s.length()) {
			result.add(new ArrayList<>(path));
		}
		for (int end = start; end < s.length(); end++) {
			if (isPalindrome(s, start, end)) {
				// add current substring in the path
				path.add(s.substring(start, end + 1));
				dfs(end + 1, result, path, s);
				// backtrack and remove the current substring from path
				path.remove(path.size() - 1);
			}
		}
	}

	boolean isPalindrome(String s, int low, int high) {
		while (low < high) {
			if (s.charAt(low++) != s.charAt(high--)) return false;
		}
		return true;
	}



	//time same as 1, space worse than 1
	//Approach 2: Backtracking with Dynamic Programming
	//leetcode solution: https://leetcode.com/problems/palindrome-partitioning/solution/
	//Time Complexity : O(N*2^N) where N is the length of string s. In the worst case,
	// there could be 2^N possible substrings and it will take O(N) to generate each substring using substring().
	// However, we are eliminating one additional iteration to check if substring is a palindrome or not.
	//Space Complexity: )O(N*N), where N is the length of the string s. The recursive call stack would require N space.
	//Additionally we also use 2 dimensional array dp of size N*N . This gives us total space complexity as O(N*N) + O(N) = O(N*N)
	public List<List<String>> partition22(String s) {
		int len = s.length();
		boolean[][] dp = new boolean[len][len];
		List<List<String>> res = new ArrayList<>();
		dfs22(res, s, 0, new ArrayList<>(), dp);
		return res;
	}

	void dfs22(List<List<String>> res, String s, int start, List<String> path, boolean[][] dp) {
		if (start >= s.length()) {
			res.add(new ArrayList<>(path));
		}
		for (int end = start; end < s.length(); end++) {
			if (s.charAt(start) == s.charAt(end) && (end - start <= 2 || dp[start + 1][end - 1])) {
				dp[start][end] = true;
				path.add(s.substring(start, end + 1));//put s[start, end] into path
				dfs22(res, s, end + 1, path, dp);
				path.remove(path.size() - 1);
			}
		}
	}



	//Zuo's solution: use dp to store the palindrome info to save a lot of time!
	public static List<List<String>> partition2(String s) {
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

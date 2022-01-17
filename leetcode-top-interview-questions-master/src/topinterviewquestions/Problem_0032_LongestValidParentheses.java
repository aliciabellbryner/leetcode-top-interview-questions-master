package topinterviewquestions;

public class Problem_0032_LongestValidParentheses {

	public static int longestValidParentheses(String str) {
		if (str == null || str.equals("")) {
			return 0;
		}
		int[] dp = new int[str.length()];
		int pre = 0;
		int res = 0;
		for (int i = 1; i < str.length(); i++) {//starting from 1 as dp[0] = 0 as one single symbol cannot form valid parentheses pair
			if (str.charAt(i) == ')') {
				// pre是，当前i位置的), 应该找哪个位置的左括号
				pre = i - dp[i - 1] - 1;
				if (pre >= 0 && str.charAt(pre) == '(') {
					dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
					// if pre == 0, then there is no way to extend left, but if pre > 0 we can still extend left based on dp[pre-1]
				}
			}
			res = Math.max(res, dp[i]);
		}
		return res;
	}

}

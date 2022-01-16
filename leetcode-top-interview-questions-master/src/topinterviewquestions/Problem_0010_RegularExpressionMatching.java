package topinterviewquestions;

public class Problem_0010_RegularExpressionMatching {

	public boolean isMatch(String s, String p) {
		// corner case
		if(s == null || p == null) return false;

		int m = s.length();
		int n = p.length();

		// M[i][j] represents if the 1st i characters in s can match the 1st j characters in p
		boolean[][] M = new boolean[m + 1][n + 1];

		// initialization:
		// 1. M[0][0] = true, since empty string matches empty pattern
		M[0][0] = true;

		// 2. M[i][0] = false(which is default value of the boolean array) since empty pattern cannot match non-empty string
		// 3. M[0][j]: what pattern matches empty string ""? It should be #*#*#*#*..., or (#*)* if allow me to represent regex using regex :P,
		// and for this case we need to check manually:
		// as we can see, the length of pattern should be even && the character at the even position should be *,
		// thus for odd length, M[0][j] = false which is default. So we can just skip the odd position, i.e. j starts from 2, the interval of j is also 2.
		// and notice that the length of repeat sub-pattern #* is only 2, we can just make use of M[0][j - 2] rather than scanning j length each time
		// for checking if it matches #*#*#*#*.
		for(int j = 2; j < n + 1; j +=2){
			if(p.charAt(j - 1) == '*' && M[0][j - 2]){
				M[0][j] = true;
			}
		}

		// Induction rule is very similar to edit distance, where we also consider from the end. And it is based on what character in the pattern we meet.
		// 1. if p.charAt(j) == s.charAt(i), M[i][j] = M[i - 1][j - 1]
		//    ######a(i)
		//    ####a(j)
		// 2. if p.charAt(j) == '.', M[i][j] = M[i - 1][j - 1]
		// 	  #######a(i)
		//    ####.(j)
		// 3. if p.charAt(j) == '*':
		//    1. if p.charAt(j - 1) != '.' && p.charAt(j - 1) != s.charAt(i), then b* is counted as empty. M[i][j] = M[i][j - 2]
		//       #####a(i)
		//       ####b*(j)
		//    2.if p.charAt(j - 1) == '.' || p.charAt(j - 1) == s.charAt(i):
		//       ######a(i)
		//       ####.*(j)
		//
		// 	  	 #####a(i)
		//    	 ###a*(j)
		//      2.1 if p.charAt(j - 1) is counted as empty, then M[i][j] = M[i][j - 2]
		//      2.2 if counted as one, then M[i][j] = M[i - 1][j - 2]
		//      2.3 if counted as multiple, then M[i][j] = M[i - 1][j]

		// recap:
		// M[i][j] = M[i - 1][j - 1]
		// M[i][j] = M[i - 1][j - 1]
		// M[i][j] = M[i][j - 2]
		// M[i][j] = M[i][j - 2]
		// M[i][j] = M[i - 1][j - 2]
		// M[i][j] = M[i - 1][j]
		// Observation: from above, we can see to get M[i][j], we need to know previous elements in M, i.e. we need to compute them first.
		// which determines i goes from 1 to m - 1, j goes from 1 to n + 1

		for(int i = 1; i < m + 1; i++){
			for(int j = 1; j < n + 1; j++){
				char curS = s.charAt(i - 1);
				char curP = p.charAt(j - 1);
				if(curS == curP || curP == '.'){
					M[i][j] = M[i - 1][j - 1];
				}else if(curP == '*'){
					char preP = p.charAt(j - 2);
					if(preP != '.' && preP != curS){
						M[i][j] = M[i][j - 2];
					}else{
						M[i][j] = (M[i][j - 2] || M[i - 1][j - 2] || M[i - 1][j]);
					}
				}
			}
		}

		return M[m][n];
	}


	public static boolean isValid(char[] str, char[] pattern) {
		for (char cha : str) {
			if (cha == '.' || cha == '*') {
				return false;
			}
		}
		for (int i = 0; i < pattern.length; i++) {
			if (pattern[i] == '*' && (i == 0 || pattern[i - 1] == '*')) {// i == 0 means 第一位是*， 后面那个代表两个连续的*
				return false;
			}
		}
		return true;
	}

	// 课堂现场写
	public static boolean isMatch1(String s, String p) {
		if (s == null || p == null) {
			return false;
		}
		char[] str = s.toCharArray();
		char[] pattern = p.toCharArray();
		return isValid(str, pattern) && process1(str, pattern, 0, 0);
	}

	// 课堂现场写
	// str[si.....] 能否被 pattern[pi...] 变出来
	// 潜台词：pi位置，pattern[pi] != '*'
	public static boolean process1(char[] str, char[] pattern, int si, int pi) {
		if (si == str.length) { // si越界了
			if (pi == pattern.length) {
				return true;
			}
			if (pi + 1 < pattern.length && pattern[pi + 1] == '*') {
				return process1(str, pattern, si, pi + 2);
			}
			return false;
		}
		// si 没越界
		if (pi == pattern.length) {
			return si == str.length;
		}
		// si 没越界 pi 没越界
		if (pi + 1 >= pattern.length || pattern[pi + 1] != '*') {
			return ((str[si] == pattern[pi]) || (pattern[pi] == '.')) && process1(str, pattern, si + 1, pi + 1);
		}
		// si 没越界 pi 没越界 pi+1 *
		if (pattern[pi] != '.' && str[si] != pattern[pi]) {
			return process1(str, pattern, si, pi + 2);
		}
		// si 没越界 pi 没越界 pi+1 * [pi]可配[si]
		if (process1(str, pattern, si, pi + 2)) {
			return true;
		}
		while (si < str.length && (str[si] == pattern[pi] || pattern[pi] == '.')) {
			if (process1(str, pattern, si + 1, pi + 2)) {
				return true;
			}
			si++;
		}
		return false;
	}

	// 课堂现场写
	public static boolean isMatch2(String s, String p) {
		if (s == null || p == null) {
			return false;
		}
		char[] str = s.toCharArray();
		char[] pattern = p.toCharArray();
		int[][] dp = new int[str.length + 1][pattern.length + 1];
		for (int si = 0; si <= str.length; si++) {
			for (int pi = 0; pi <= pattern.length; pi++) {
				dp[si][pi] = -1;
			}
		}
		// dp[si][pi] == -1
		// dp[si][pi] == 0 si pi false
		// dp[si][pi] == 1 si pi true
		return isValid(str, pattern) && process2(str, pattern, 0, 0, dp);
	}

	// 课堂现场写
	// str[si.....] 能否被 pattern[pi...] 变出来
	// 潜台词：pi位置，pattern[pi] != '*'
	public static boolean process2(char[] str, char[] pattern, int si, int pi, int[][] dp) {
		if (dp[si][pi] != -1) {
			return dp[si][pi] == 1;
		}
		// si pi 这个参数组合第一次算

		if (si == str.length) { // si越界了
			if (pi == pattern.length) {
				dp[si][pi] = 1;
				return true;
			}
			// (pi pi+1) pi+2 ....
			if (pi + 1 < pattern.length && pattern[pi + 1] == '*') {
				boolean ans = process2(str, pattern, si, pi + 2, dp);
				dp[si][pi] = ans ? 1 : 0;
				return ans;
			}
			dp[si][pi] = 0;
			return false;
		}
		// si 没越界
		if (pi == pattern.length) {
			boolean ans = si == str.length;
			dp[si][pi] = ans ? 1 : 0;
			return ans;
		}
		// si 没越界 pi 没越界
		if (pi + 1 >= pattern.length || pattern[pi + 1] != '*') {
			boolean ans = ((str[si] == pattern[pi]) || (pattern[pi] == '.'))
					&& process2(str, pattern, si + 1, pi + 1, dp);
			dp[si][pi] = ans ? 1 : 0;
			return ans;
		}
		// si 没越界 pi 没越界 pi+1 *
		if (pattern[pi] != '.' && str[si] != pattern[pi]) {
			boolean ans = process2(str, pattern, si, pi + 2, dp);
			dp[si][pi] = ans ? 1 : 0;
			return ans;
		}
		if (process2(str, pattern, si, pi + 2, dp)) {
			dp[si][pi] = 1;
			return true;
		}
		while (si < str.length && (str[si] == pattern[pi] || pattern[pi] == '.')) {
			if (process2(str, pattern, si + 1, pi + 2, dp)) {
				dp[si][pi] = 1;
				return true;
			}
			si++;
		}
		dp[si][pi] = 0;
		return false;
	}

	// 以下的代码是课后的优化
	// 有代码逻辑精简的优化，还包含一个重要的枚举行为优化
	// 请先理解课上的内容
	public static boolean isMatch3(String s, String p) {
		if (s == null || p == null) {
			return false;
		}
		char[] str = s.toCharArray();
		char[] pattern = p.toCharArray();
		return isValid(str, pattern) && process3(str, pattern, 0, 0);
	}

	// 举例说明枚举行为优化
	// 求状态(si = 3, pi = 7)时，假设状况如下
	// str : a a a b ...
	// si  : 3 4 5 6 ...
	// pat : a * ? ...
	// pi  : 7 8 9 ...
	// 状态(si = 3, pi = 7)会依赖：
	//   状态(si = 3, pi = 9)
	//   状态(si = 4, pi = 9)
	//   状态(si = 5, pi = 9)
	//   状态(si = 6, pi = 9)
	//
	// 求状态(si = 2, pi = 7)时，假设状况如下
	// str : a a a a b ...
	// si  : 2 3 4 5 6 ...
	// pat : a * ? ...
	// pi  : 7 8 9 ...
	// 状态(si = 2, pi = 7)会依赖：
	//   状态(si = 2, pi = 9)
	//   状态(si = 3, pi = 9)
	//   状态(si = 4, pi = 9)
	//   状态(si = 5, pi = 9)
	//   状态(si = 6, pi = 9)
	//
	// 注意看状态(si = 2, pi = 7)依赖的后4个，其实就是状态(si = 3, pi = 7)
	// 所以状态(si = 2, pi = 7)的依赖可以化简为：
	//   状态(si = 2, pi = 9)
	//   状态(si = 3, pi = 7)
	// 这样枚举行为就被化简成了有限两个状态，详细情况看代码
	public static boolean process3(char[] str, char[] pattern, int si, int pi) {
		if (si == str.length && pi == pattern.length) {
			return true;
		}
		if (si == str.length) {
			return (pi + 1 < pattern.length && pattern[pi + 1] == '*') && process3(str, pattern, si, pi + 2);
		}
		if (pi == pattern.length) {
			return false;
		}
		if (pi + 1 >= pattern.length || pattern[pi + 1] != '*') {
			return ((str[si] == pattern[pi]) || (pattern[pi] == '.')) && process3(str, pattern, si + 1, pi + 1);
		}
		// 此处为枚举行为优化，含义看函数注释
		if ((str[si] == pattern[pi] || pattern[pi] == '.') && process3(str, pattern, si + 1, pi)) {
			return true;
		}
		return process3(str, pattern, si, pi + 2);
	}

	// 以下的代码是枚举行为优化后的尝试函数，改成动态规划的解
	// 请先理解基础班中"暴力递归改动态规划"的内容
	// 如果str长度为N，pattern长度为M，最终时间复杂度为O(N*M)
	public static boolean isMatch4(String str, String pattern) {
		if (str == null || pattern == null) {
			return false;
		}
		char[] s = str.toCharArray();
		char[] p = pattern.toCharArray();
		if (!isValid(s, p)) {
			return false;
		}
		int N = s.length;
		int M = p.length;
		boolean[][] dp = new boolean[N + 1][M + 1];
		dp[N][M] = true;
		for (int j = M - 1; j >= 0; j--) {
			dp[N][j] = (j + 1 < M && p[j + 1] == '*') && dp[N][j + 2];
		}
		// dp[0..N-2][M-1]都等于false，只有dp[N-1][M-1]需要讨论
		if (N > 0 && M > 0) {
			dp[N - 1][M - 1] = (s[N - 1] == p[M - 1] || p[M - 1] == '.');
		}
		for (int i = N - 1; i >= 0; i--) {
			for (int j = M - 2; j >= 0; j--) {
				if (p[j + 1] != '*') {
					dp[i][j] = ((s[i] == p[j]) || (p[j] == '.')) && dp[i + 1][j + 1];
				} else {
					if ((s[i] == p[j] || p[j] == '.') && dp[i + 1][j]) {
						dp[i][j] = true;
					} else {
						dp[i][j] = dp[i][j + 2];
					}
				}
			}
		}
		return dp[0][0];
	}

}

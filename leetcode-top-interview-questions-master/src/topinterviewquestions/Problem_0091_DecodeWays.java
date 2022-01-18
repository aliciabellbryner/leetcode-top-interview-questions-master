package topinterviewquestions;

public class Problem_0091_DecodeWays {



	public int numDecodings(String s) {
		if (s == null || s.length() == 0 || s.charAt(0) == '0') {
			return 0;
		}
		int[] dp = new int[s.length()+1];//dp[i] 代表用s中的s前0个元素能构成的新decode的string的种类
		dp[0] = 1;//dp[0]则是s中前0个元素能构成1个
		dp[1] = s.charAt(0) == '0' ? 0 : 1;
		for (int i = 2; i <= s.length(); i++) {
			int lastOne = Integer.valueOf(s.substring(i-1,i));//cannot use charAt as char transfer to int will be its ASCII code
			int lastTwo = Integer.valueOf(s.substring(i-2,i));
			if (lastOne != 0) {
				dp[i] += dp[i-1];
			}
			if (lastTwo >= 10 && lastTwo <= 26) {
				dp[i] += dp[i-2];
			}
		}
		return dp[s.length()];
	}


	//use dp to solve
	public static int numDecodings2(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		// dp[i] -> process(str, index)返回值 index 0 ~ N
		int[] dp = new int[N + 1];//dp[i] 代表用s中的s.charAt(i)开始（包括i）知道s的最后能构成的新decode的string的种类
		dp[N] = 1;

		// dp依次填好 dp[i] dp[i+1] dp[i+2]
		for (int i = N - 1; i >= 0; i--) {
			if (str[i] != '0') {
				dp[i] = dp[i + 1];
				if (i + 1 == N) {
					continue;
				}
				int num = (str[i] - '0') * 10 + str[i + 1] - '0';//if str[i] and str[i+1] is <= 26, means it can construct a letter,
				// then we need to add the dp[i + 2] into dp[i]
				if (num <= 26) {
					dp[i] += dp[i + 2];
				}
			}
		}
		return dp[0];
	}

	public static int numDecodings3(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		int[] dp = new int[N + 1];
		dp[N] = 1;
		for (int i = N - 1; i >= 0; i--) {
			if (str[i] == '0') {
				dp[i] = 0;
			} else if (str[i] == '1') {
				dp[i] = dp[i + 1];
				if (i + 1 < N) {
					dp[i] += dp[i + 2];
				}
			} else if (str[i] == '2') {
				dp[i] = dp[i + 1];
				if (i + 1 < str.length && (str[i + 1] >= '0' && str[i + 1] <= '6')) {
					dp[i] += dp[i + 2];
				}
			} else {
				dp[i] = dp[i + 1];
			}
		}
		return dp[0];
	}

}

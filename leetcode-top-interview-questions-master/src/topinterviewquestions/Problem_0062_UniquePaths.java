package topinterviewquestions;

public class Problem_0062_UniquePaths {
	//zuo's solution, using math, it is like you can walk m+n-2 steps, and then you only need to choose m-1 to walk down or n-1 to walk right
	//so choose k from n is (Cn k) = n!/(k!*(n-k)!)
	//time O(min(M, N)), space O(1)
	public static int uniquePaths(int m, int n) {
		int part = n - 1;
		int all = m + n - 2;
		long o1 = 1;
		long o2 = 1;
		for (int i = part + 1, j = 1; i <= all || j <= all - part; i++, j++) {
			o1 *= i;
			o2 *= j;
			long gcd = gcd(o1,o2);//this step is to avoid the number is too large the stack overflow
			o1 /= gcd;
			o2 /= gcd;
		}
		return (int)o1;
	}


	//my solution, dp, but seems not necessary
	//time O(MN), space O(MN)
	public int uniquePaths_j(int m, int n) {
		if (m <= 1 || n <= 1) {
			return Math.min(m, n);
		}
		int[][] dp = new int[m][n];
		dp[0][0] = 0;
		for (int i = 1; i < m; i++) {
			dp[i][0] = 1;
			for (int j = 1; j < n; j++) {
				dp[0][j] = 1;
				dp[i][j] = dp[i-1][j] + dp[i][j-1];
			}
		}
		return dp[m-1][n-1];
	}


	
	// 调用的时候，请保证初次调用时，m和n都不为0
	public static long gcd(long m, long n) {
		return n == 0 ? m : gcd(n, m % n);
	}

	public static void main(String[] args) {
		System.out.println(uniquePaths(3,4));
	}

}

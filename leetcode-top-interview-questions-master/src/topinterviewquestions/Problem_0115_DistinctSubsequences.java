package topinterviewquestions;

import java.util.LinkedList;
import java.util.Queue;

public class Problem_0115_DistinctSubsequences {

	//2D dp https://leetcode.com/problems/distinct-subsequences/solution/
	//Time Complexity: we have two for loops with clearly defined executions.
	//Space Complexity: O(M×N) which is occupied by the 2D dp array that we create.
	public int numDistinct1(String s, String t) {

		int M = s.length();
		int N = t.length();

		int[][] dp = new int[M + 1][N + 1];//dp[i][j] means the number of distinct subsequences in string s[i⋯M] that equals the string t[j⋯N]

		// Base case initialization, dp[M][j] is 0 (you cannot use an empty string to form a non-empty string)except j == N
		for (int j = 0; j <= N; j++) {
			dp[M][j] = 0;
		}

		// Base case initialization， dp[i][N] is always 1 as every string has an empty subsequence
		for (int i = 0; i <= M; i++) {
			dp[i][N] = 1;
		}
		//the cornor case dp[M][N] = 1 after those two loop

		// Iterate over the strings in reverse so as to
		// satisfy the way we've modeled our recursive solution
		for (int i = M - 1; i >= 0; i--) {
			for (int j = N - 1; j >= 0; j--) {

				// Remember, we always need this result
				dp[i][j] = dp[i + 1][j];

				// If the characters match, we add the
				// result of the next recursion call (in this
				// case, the value of a cell in the dp table
				if (s.charAt(i) == t.charAt(j)) {
					dp[i][j] += dp[i + 1][j + 1];
				}
			}
		}

		return dp[0][0];
	}


	//1D dp
	//Time Complexity: O(M×N)
	//Space Complexity: O(N) since we are using a single array which is the size of the string T.
	// This is a major size reduction over the previous solution and this is a much more elegant solution than the initial recursive solution we saw earlier on.
	public int numDistinct2(String s, String t) {

		int M = s.length();
		int N = t.length();

		int[] dp = new int[N];

		int prev = 1;

		// Iterate over the strings in reverse so as to
		// satisfy the way we've modeled our recursive solution
		for (int i = M - 1; i >= 0; i--) {

			// At each step we start with the last value in the row which is always 1.
			// Notice how we are starting the loop from N - 1 instead of N like in the previous solution.
			prev = 1;

			for (int j = N - 1; j >= 0; j--) {

				// Record the current value in this cell so that we can use it to calculate the value of dp[j - 1]
				int old_dpj = dp[j];

				// If the characters match, we add the result of the next recursion call (in this  case, the value of a cell in the dp table
				if (s.charAt(i) == t.charAt(j)) {
					dp[j] += prev;
				}

				// Update the prev variable
				prev = old_dpj;
			}
		}

		return dp[0];
	}

}

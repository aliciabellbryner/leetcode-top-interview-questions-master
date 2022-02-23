package topinterviewquestions;
/*
Imagine you have a special keyboard with the following keys:

A: Print one 'A' on the screen.
Ctrl-A: Select the whole screen.
Ctrl-C: Copy selection to buffer.
Ctrl-V: Print buffer on screen appending it after what has already been printed.
Given an integer n, return the maximum number of 'A' you can print on the screen with at most n presses on the keys.



Example 1:

Input: n = 3
Output: 3
Explanation: We can at most get 3 A's on screen by pressing the following key sequence:
A, A, A
Example 2:

Input: n = 7
Output: 9
Explanation: We can at most get 9 A's on screen by pressing following key sequence:
A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V


Constraints:

1 <= n <= 50
 */
public class Problem_0651_4KeysKeyboard {
    //diss
    //Java 4 lines recursion, with step-by-step explanation to derive DP
    /*
    We use i steps to reach maxA(i) then use the remaining n - i steps to reach n - i - 1 copies of maxA(i)

For example:
A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V
Here we have n = 7 and we used i = 3 steps to reach AAA
Then we use the remaining n - i = 4 steps: Ctrl A, Ctrl C, Ctrl V, Ctrl V, to reach n - i - 1 = 3 copies of AAA

We either don't make copies at all, in which case the answer is just n, or if we want to make copies, we need to have 3 steps reserved for Ctrl A, Ctrl C, Ctrl V so i can be at most n - 3

    public int maxA(int n) {
        int max = n;
        for (int i = 1; i <= n - 3; i++)
            max = Math.max(max, maxA(i) * (n - i - 1));
        return max;
    }
Now making it a DP where dp[i] is the solution to sub-problem maxA(i)

    public int maxA(int n) {
        int[] dp = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            dp[i] = i;
            for (int j = 1; j <= i - 3; j++)
                dp[i] = Math.max(dp[i], dp[j] * (i - j - 1));
        }
        return dp[n];
    }
     */


    //The inner loop of the bottom-up DP can be truncated to at most four terms, that is, for dp[i], we only need to choose the largest one from dp[i - 3] * 2, dp[i - 4] * 3, dp[i - 5] * 4, dp[i - 6] * 5, while all the rest terms like dp[i - 7] * 6, dp[i - 8] * 7, ... can be ignored. The reason is as follows: when computing dp[i - 3], we've already chosen its value to be the maximum of terms like dp[i - 6] * 2, dp[i - 7] * 3, dp[i - 8] * 4, ..., which implies dp[i - 3] >= dp[i - k] * (k - 4) for k >= 6. This further leads to dp[i - 3] * 2 >= dp[i - k] * (2k - 8) >= dp[i - k] * (k - 1) if k >= 7. So we always have dp[i - 3] * 2 >= dp[i - 7] * 6, dp[i - 3] * 2 >= dp[i - 8] * 7, .... Therefore terms like dp[i - 7] * 6, dp[i - 8] * 7, ... won't contribute when evaluating dp[i].
    //
    //The truncation will make the DP solution run effectively in O(n) time. Also the space complexity can be reduced to O(1) since now we only need to maintain a constant number of variables (seven, I would say, dp[i], dp[i-1], dp[i-2], dp[i-3], dp[i-4], dp[i-5], dp[i-6]). The following is the modified code:
    public int maxA(int N) {
        int[] dp = new int[7];

        for (int i = 1; i <= N; i++) {
            dp[0] = i;

            for (int k = 6; k > 2; k--) {
                dp[0] = Math.max(dp[0], dp[k] * (k - 1));
            }

            for (int k = 6; k > 0; k--) {
                dp[k] = dp[k - 1];
            }
        }

        return dp[0];
    }


    /*
    dp[i] = max(dp[i], dp[i-j]*(j-1)) j in [3, i)

public int maxA(int N) {
        int[] dp = new int[N+1];
        for(int i=1;i<=N;i++){
            dp[i] = i;
            for(int j=3;j<i;j++){
                dp[i] = Math.max(dp[i], dp[i-j] * (j-1));
            }
        }
        return dp[N];
    }
This one is O(n), inspired by paulalexis58. We don't have to run the second loop between [3,i). Instead, we only need to recalculate the last two steps. It's interesting to observe that dp[i - 4] * 3 and dp[i - 5] * 4 always the largest number in the series. Welcome to add your mathematics proof here.

public int maxA(int N) {
    if (N <= 6)  return N;
    int[] dp = new int[N + 1];
    for (int i = 1; i <= 6; i++) {
      dp[i] = i;
    }
    for (int i = 7; i <= N; i++) {
      dp[i] = Math.max(dp[i - 4] * 3, dp[i - 5] * 4);
      // dp[i] = Math.max(dp[i - 4] * 3, Math.max(dp[i - 5] * 4, dp[i - 6] * 5));
    }
    return dp[N];
  }
     */
}

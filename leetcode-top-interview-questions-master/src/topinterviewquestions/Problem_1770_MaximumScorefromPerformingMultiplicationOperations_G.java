package topinterviewquestions;

public class Problem_1770_MaximumScorefromPerformingMultiplicationOperations_G {
    //Classic DP problem
    //i is left pointer, j is right pointer, each time we have 2 choices, take either left element or right element, and return the max.
    //The hardest part is to manage your space (cache) efficiently
    //N: 10^5, M: 10^3. dp[N][N] will make us out of memory.
    //Observation: the range of i is at most [0, M), the range of j is at most [N - M, N). So that, we could use dp[M][M] rather than dp[N][N] to save a lot of space (just need to "normalize" the right pointer a little bit).
    //https://leetcode.com/problems/maximum-score-from-performing-multiplication-operations/discuss/1075492/Java-Detailed-Explanation-DP-Optimize-Space
    public int maximumScore(int[] nums, int[] multipliers) {

        int N = nums.length, M = multipliers.length;

        return helper(nums, multipliers, 0, N - 1, new Integer[M][M]);
    }

    private int helper(int[] nums, int[] multipliers, int i, int j, Integer[][] dp) {

        int N = nums.length, M = multipliers.length;
        int index = (i - 0) + (N - 1 - j);
        if (index == M) return 0;

        if (dp[i][j - (N - M)] != null) return dp[i][j - (N - M)];

        int res = Math.max(nums[i] * multipliers[index] + helper(nums, multipliers, i + 1, j, dp),
                nums[j] * multipliers[index] + helper(nums, multipliers, i, j - 1, dp));

        return dp[i][j - (N - M)] = res;
    }
}

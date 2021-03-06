package topinterviewquestions;

public class Problem_0312_BurstBalloons_G {

    //Java D&C with Memoization
    //https://leetcode.com/problems/burst-balloons/discuss/76228/Share-some-analysis-and-explanations
    //The algorithm runs in O(n^3) which can be easily seen from the 3 loops in dp solution.
    public class Solution1 {

        public int maxCoins(int[] iNums) {
            int[] nums = new int[iNums.length + 2];
            int n = 1;
            for (int x : iNums) if (x > 0) nums[n++] = x;
            nums[0] = nums[n++] = 1;


            int[][] memo = new int[n][n];
            return burst(memo, nums, 0, n - 1);
        }

        public int burst(int[][] memo, int[] nums, int left, int right) {
            if (left + 1 == right) return 0;
            if (memo[left][right] > 0) return memo[left][right];
            int ans = 0;
            for (int i = left + 1; i < right; ++i)
                ans = Math.max(ans, nums[left] * nums[i] * nums[right]
                        + burst(memo, nums, left, i) + burst(memo, nums, i, right));
            memo[left][right] = ans;
            return ans;
        }
    }

    //Java DP
    public class Solution2 {
        public int maxCoins(int[] iNums) {
            int[] nums = new int[iNums.length + 2];
            int n = 1;
            for (int x : iNums) if (x > 0) nums[n++] = x;
            nums[0] = nums[n++] = 1;


            int[][] dp = new int[n][n];
            for (int k = 2; k < n; ++k)
                for (int left = 0; left < n - k; ++left) {
                    int right = left + k;
                    for (int i = left + 1; i < right; ++i)
                        dp[left][right] = Math.max(dp[left][right],
                                nums[left] * nums[i] * nums[right] + dp[left][i] + dp[i][right]);
                }

            return dp[0][n - 1];
        }
    }
}

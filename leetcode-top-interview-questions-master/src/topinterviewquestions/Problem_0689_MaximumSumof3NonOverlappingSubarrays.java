package topinterviewquestions;
/*
Given an integer array nums and an integer k, find three non-overlapping subarrays of length k with maximum sum and return them.

Return the result as a list of indices representing the starting position of each interval (0-indexed). If there are multiple answers, return the lexicographically smallest one.



Example 1:

Input: nums = [1,2,1,2,6,7,5,1], k = 2
Output: [0,3,5]
Explanation: Subarrays [1, 2], [2, 6], [7, 5] correspond to the starting indices [0, 3, 5].
We could have also taken [2, 1], but an answer of [1, 3, 5] would be lexicographically larger.
Example 2:

Input: nums = [1,2,1,2,1,2,1,2,1], k = 2
Output: [0,2,4]


Constraints:

1 <= nums.length <= 2 * 104
1 <= nums[i] < 216
1 <= k <= floor(nums.length / 3)
 */
public class Problem_0689_MaximumSumof3NonOverlappingSubarrays {
    //https://leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays/discuss/108230/Clean-Java-DP-O(n)-Solution.-Easy-extend-to-Sum-of-K-Non-Overlapping-SubArrays.
    //This is a more general DP solution, and it is similar to that buy and sell stock problem.
    //
    //dp[i][j] stands for in i th sum, the max non-overlap sum we can have from 0 to j
    //id[i][j] stands for in i th sum, the first starting index for that sum.
    class Solution {
        public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
            int[] ans = new int[3];
            int[][] dp = new int[4][nums.length + 1];
            int[][] pi = new int[4][nums.length + 1];
            for (int i = 0; i <= nums.length; i++) {
                dp[0][i] = 0;
            }
            int[] preSum = new int[nums.length + 1];
            preSum[0] = 0;
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
                preSum[i + 1] = sum;
            }
            for (int i = 1; i < 4; i++) {
                for (int j = k; j <= nums.length; j++) { // j: interval end, 1-index based due to sequence dp
                    //do not choose j as one of the intervals end
                    if (j > 0) {
                        dp[i][j] = dp[i][j - 1];
                        pi[i][j] = pi[i][j - 1];
                    }
                    int temp = preSum[j] - preSum[j - k] + dp[i - 1][j - k];
                    if (temp > dp[i][j]) {
                        dp[i][j] = temp;
                        pi[i][j] = j - k + 1;// interval starting point, 1-index based
                    }
                }
            }
            ans[2] = pi[3][nums.length] - 1;
            ans[1] = pi[2][ans[2]] - 1;//ans[2] 0-index based next interval starting point, last interval end point should be up to ans[2] - 1, change to 1-index based ans[2] - 1 + 1
            ans[0] = pi[1][ans[1]] - 1;
            return ans;
        }
    }
}

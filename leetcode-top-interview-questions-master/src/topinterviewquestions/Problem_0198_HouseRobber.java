package topinterviewquestions;
/*
You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security systems connected and it will automatically contact the police if two adjacent houses were broken into on the same night.

Given an integer array nums representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.



Example 1:

Input: nums = [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
Total amount you can rob = 1 + 3 = 4.
Example 2:

Input: nums = [2,7,9,3,1]
Output: 12
Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
Total amount you can rob = 2 + 9 + 1 = 12.


Constraints:

1 <= nums.length <= 100
0 <= nums[i] <= 400
 */
public class Problem_0198_HouseRobber {

	public static int rob1(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		if (nums.length == 1) {
			return nums[0];
		}
		int N = nums.length;
		int[] dp = new int[N];//dp[i] means if the robber goes to nums[i],
		// the maximum he can get no matter he rob nums[i] or not
		dp[0] = nums[0];
		dp[1] = Math.max(nums[0], nums[1]);
		for (int i = 2; i < N; i++) {
			dp[i] = Math.max(dp[i - 1], nums[i] + dp[i - 2]);
			//dp[i - 1] means he didn't rob nums[i]
			//nums[i] + dp[i - 2] means he rob nums[i] and didn't rob nums[i-1]
		}
		return dp[N - 1];
	}

	public static int rob2(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		if (nums.length == 1) {
			return nums[0];
		}
		int pre2 = nums[0];
		int pre1 = Math.max(nums[0], nums[1]);
		for (int i = 2; i < nums.length; i++) {
			int cur = Math.max(pre1, nums[i] + pre2);
			pre2 = pre1;
			pre1 = cur;
		}
		return pre1;
	}

}

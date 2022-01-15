package topinterviewquestions;

import java.util.HashMap;

public class Problem_0494_TargetSum {

	/** solution 2: DP (0/1 knapsack) - Time: O(n^2), Space: O(n^2) */
	/**
	 * sub-problem: dp[i][j] represents number of possible ways to reach sum j by using first ith items
	 * base case: dp[0][sum], position sum represents sum 0
	 * recurrence relation:
	 * dp[i][j] += dp[i - 1][j + nums[i - 1]] if j + nums[i - 1] <= sum * 2
	 * dp[i][j] += dp[i - 1][j - nums[i - 1]] if j - nums[i - 1] >= 0
	 *
	 * explanation: if j + nums[i - 1] or j - nums[i - 1] is in correct range, we can use the number nums[i - 1]
	 * to generate next state of dp array
	 * */
	public static int findTargetSumWays_leetcode(int[] nums, int target) {
		if (nums.length == 0) {
			return 0;
		}

		int sum = 0;
		for (int num : nums) {
			sum += num;
		}

		// corner case: when target is out of range [-sum, sum]
		if (target < -sum || target > sum) {
			return 0;
		}

		int[][] dp = new int[nums.length + 1][sum * 2 + 1];//dp[i][j]是代表组合使用前i位（idx=0，1，2...i,包括i)的元素，能构成和为j-sum的path总数
		dp[0][sum] = 1;//我们把[-sum, sum]这个区间平移到了[0, 2*sum]上，所以之前的dp[0][0]就相当于dp[0][sum]
		int leftBound = 0;
		int rightBound = sum * 2;
		for (int i = 1; i <= nums.length; i++) {
			for (int j = leftBound; j < rightBound + 1; j++) {
				// try all possible sum of (previous sum j + current number nums[i - 1]) and all possible difference of
				// (previous sum j - current number nums[i - 1])
				if (j + nums[i - 1] <= rightBound) {
					dp[i][j] += dp[i - 1][j + nums[i - 1]];
				}
				if (j - nums[i - 1] >= leftBound) {
					dp[i][j] += dp[i - 1][j - nums[i - 1]];
				}
			}
		}
		return dp[nums.length][sum + target];
	}

	public static void main(String[] args) {
		int[] test = new int[]{1,1,1,1,1};
		System.out.println(findTargetSumWays_leetcode(test, 3));
	}


	public static int findTargetSumWays1(int[] arr, int s) {
		return process1(arr, 0, s);
	}

	public static int process1(int[] arr, int index, int rest) {
		if (index == arr.length) {
			return rest == 0 ? 1 : 0;
		}
		return process1(arr, index + 1, rest - arr[index]) + process1(arr, index + 1, rest + arr[index]);
	}

	public static int findTargetSumWays2(int[] arr, int s) {
		return process2(arr, 0, s, new HashMap<>());
	}

	public static int process2(int[] arr, int index, int rest, HashMap<Integer, HashMap<Integer, Integer>> dp) {
		if (dp.containsKey(index) && dp.get(index).containsKey(rest)) {
			return dp.get(index).get(rest);
		}
		int ans = 0;
		if (index == arr.length) {
			ans = rest == 0 ? 1 : 0;
		} else {
			ans = process2(arr, index + 1, rest - arr[index], dp) + process2(arr, index + 1, rest + arr[index], dp);
		}
		if (!dp.containsKey(index)) {
			dp.put(index, new HashMap<>());
		}
		dp.get(index).put(rest, ans);
		return ans;
	}

	// 优化点一 :
	// 你可以认为arr中都是非负数
	// 因为即便是arr中有负数，比如[3,-4,2]
	// 因为你能在每个数前面用+或者-号
	// 所以[3,-4,2]其实和[3,4,2]达成一样的效果
	// 那么我们就全把arr变成非负数，不会影响结果的
	// 优化点二 :
	// 如果arr都是非负数，并且所有数的累加和是sum
	// 那么如果target<sum，很明显没有任何方法可以达到target，可以直接返回0
	// 优化点三 :
	// arr内部的数组，不管怎么+和-，最终的结果都一定不会改变奇偶性
	// 所以，如果所有数的累加和是sum，
	// 并且与target的奇偶性不一样，没有任何方法可以达到target，可以直接返回0
	// 优化点四 :
	// 比如说给定一个数组, arr = [1, 2, 3, 4, 5] 并且 target = 3
	// 其中一个方案是 : +1 -2 +3 -4 +5 = 3
	// 该方案中取了正的集合为P = {1，3，5}
	// 该方案中取了负的集合为N = {2，4}
	// 所以任何一种方案，都一定有 sum(P) - sum(N) = target
	// 现在我们来处理一下这个等式，把左右两边都加上sum(P) + sum(N)，那么就会变成如下：
	// sum(P) - sum(N) + sum(P) + sum(N) = target + sum(P) + sum(N)
	// 2 * sum(P) = target + 数组所有数的累加和
	// sum(P) = (target + 数组所有数的累加和) / 2
	// 也就是说，任何一个集合，只要累加和是(target + 数组所有数的累加和) / 2
	// 那么就一定对应一种target的方式
	// 也就是说，比如非负数组arr，target = 7, 而所有数累加和是11
	// 求有多少方法组成7，其实就是求有多少种达到累加和(7+11)/2=9的方法
	// 优化点五 :
	// 二维动态规划的空间压缩技巧
	public static int findTargetSumWays3(int[] arr, int s) {
		int sum = 0;
		for (int n : arr) {
			sum += n;
		}
		return sum < s || ((s & 1) ^ (sum & 1)) != 0 ? 0 : subset(arr, (s + sum) >> 1);
	}

	public static int subset(int[] nums, int s) {
		int[] dp = new int[s + 1];
		dp[0] = 1;
		for (int n : nums) {
			for (int i = s; i >= n; i--) {
				dp[i] += dp[i - n];
			}
		}
		return dp[s];
	}

}

package topinterviewquestions;

import java.util.HashMap;
/*
Given an array of integers nums and an integer k, return the total number of subarrays whose sum equals to k.

Example 1:

Input: nums = [1,1,1], k = 2
Output: 2
Example 2:

Input: nums = [1,2,3], k = 3
Output: 2


Constraints:

1 <= nums.length <= 2 * 104
-1000 <= nums[i] <= 1000
-107 <= k <= 107
 */
public class Problem_0560_SubarraySumEqualsK {

	public static int subarraySum(int[] nums, int k) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		HashMap<Integer, Integer> preSumTimesMap = new HashMap<>();//key是presum的值，val是这个presum出现的次数
		preSumTimesMap.put(0, 1);//比如k=10，nums第一个数是10，那么后面res必定要加1，而不是加0
		int all = 0; // 0..i
		int res = 0;
		for (int i = 0; i < nums.length; i++) {
			all += nums[i]; // 0....i 整体的前缀和
			if (preSumTimesMap.containsKey(all - k)) {
				res += preSumTimesMap.get(all - k);
			}
			if (!preSumTimesMap.containsKey(all)) {
				preSumTimesMap.put(all, 1);
			} else {
				preSumTimesMap.put(all, preSumTimesMap.get(all) + 1);
			}
		}
		return res;
	}

}

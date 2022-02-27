package topinterviewquestions;

import java.util.HashMap;
import java.util.Map;

/*
Given an array nums and an integer target, return the maximum number of non-empty non-overlapping subarrays such that the sum of values in each subarray is equal to target.



Example 1:

Input: nums = [1,1,1,1,1], target = 2
Output: 2
Explanation: There are 2 non-overlapping subarrays [1,1,1,1,1] with sum equals to target(2).
Example 2:

Input: nums = [-1,3,5,1,4,2,-9], target = 6
Output: 2
Explanation: There are 3 subarrays with sum equal to 6.
([5,1], [4,2], [3,5,1,4,2,-9]) but only the first 2 are non-overlapping.


Constraints:

1 <= nums.length <= 105
-104 <= nums[i] <= 104
0 <= target <= 106
 */
public class Problem_1546_MaximumNumberofNonOverlappingSubarraysWithSumEqualsTarget {
    //diss
    //https://leetcode.com/problems/maximum-number-of-non-overlapping-subarrays-with-sum-equals-target/discuss/780887/Java-Detailed-Explanation-DPMapPrefix-O(N)
    //time O(N)
    //space O(N)
    class Solution {
        public int maxNonOverlapping(int[] nums, int target) {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(0, 0);

            int res = 0;
            int sum = 0;

            for (int i = 0; i < nums.length; ++i) {
                sum += nums[i];
                if (map.containsKey(sum - target)) {
                    res = Math.max(res, map.get(sum - target) + 1);
                }
                map.put(sum, res);
            }

            return res;
        }
    }
}

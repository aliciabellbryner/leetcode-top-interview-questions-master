package topinterviewquestions;

/*
Given an integer array nums and an integer k, return true if nums has a continuous subarray of size at least two whose elements sum up to a multiple of k, or false otherwise.

An integer x is a multiple of k if there exists an integer n such that x = n * k. 0 is always a multiple of k.

Example 1:

Input: nums = [23,2,4,6,7], k = 6
Output: true
Explanation: [2, 4] is a continuous subarray of size 2 whose elements sum up to 6.
Example 2:

Input: nums = [23,2,6,4,7], k = 6
Output: true
Explanation: [23, 2, 6, 4, 7] is an continuous subarray of size 5 whose elements sum up to 42.
42 is a multiple of 6 because 42 = 7 * 6 and 7 is an integer.
Example 3:

Input: nums = [23,2,6,4,7], k = 13
Output: false


Constraints:

1 <= nums.length <= 105
0 <= nums[i] <= 109
0 <= sum(nums[i]) <= 231 - 1
1 <= k <= 231 - 1
 */

import java.util.HashMap;
import java.util.Map;

public class Problem_0523_ContinuousSubarraySum {
    //https://leetcode.com/problems/continuous-subarray-sum/discuss/99499/Java-O(n)-time-O(k)-space
    /** Key point: if we can find any two subarray of prefix sum have same mod value, then their difference MUST be
     * divisible by k. So we can use a map to store mod value of each prefix sum in map, with its index. Then check
     * if map contains the same mod value with size > 2 when we have new mod value in every iteration */
    public boolean checkSubarraySum(int[] nums, int k) {
        if (nums.length < 2) {//因为题目说了at least two
            return false;
        }

        Map<Integer, Integer> map = new HashMap<>();
        // corner case: if the very first subarray with first two numbers in array could form the result, we need to
        // put mod value 0 and index -1 to make it as a true case
        map.put(0, -1);
        int preSum = 0;
        for (int i = 0; i < nums.length; i++) {
            preSum += nums[i];

            // corner case: k CANNOT be 0 when we use a number mod k
            if (k != 0) {
                preSum = preSum % k;
            }
            if (map.containsKey(preSum)) {//说明之前存在一样的presum，
                // 比如i=10时presum = 23, k = 10, 那么preSum % k = 3，如果3已经被存过了，而且3对应的i是3，那么从4-10这段就能组成10的倍数
                if (i - map.get(preSum) > 1) {
                    return true;
                }
            }
            else {
                map.put(preSum, i);
            }
        }
        return false;
    }
}

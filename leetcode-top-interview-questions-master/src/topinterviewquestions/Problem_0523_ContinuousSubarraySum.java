package topinterviewquestions;

/*
Given an integer array nums and an integer k, return true if nums has a continuous subarray of size at least two whose elements sum up to a multiple of k, or false otherwise.

An integer x is a multiple of k if there exists an integer n such that x = n * k. 0 is always a multiple of k.
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
        int curSum = 0;
        for (int i = 0; i < nums.length; i++) {
            curSum += nums[i];

            // corner case: k CANNOT be 0 when we use a number mod k
            if (k != 0) {
                curSum = curSum % k;
            }
            if (map.containsKey(curSum)) {
                if (i - map.get(curSum) > 1) {
                    return true;
                }
            }
            else {
                map.put(curSum, i);
            }
        }
        return false;
    }
}

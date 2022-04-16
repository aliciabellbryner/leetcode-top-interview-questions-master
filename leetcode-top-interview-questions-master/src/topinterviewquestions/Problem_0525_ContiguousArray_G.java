package topinterviewquestions;

import java.util.HashMap;
import java.util.Map;

public class Problem_0525_ContiguousArray_G {
    //Easy Java O(n) Solution, PreSum + HashMap
    //https://leetcode.com/problems/contiguous-array/discuss/99646/Easy-Java-O(n)-Solution-PreSum-%2B-HashMap
    //The idea is to change 0 in the original array to -1. Thus, if we find SUM[i, j] == 0 then we
    // know there are even number of -1 and 1 between index i and j.
    // Also put the sum to index mapping to a HashMap to make search faster.
    public class Solution {
        public int findMaxLength(int[] nums) {
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == 0) nums[i] = -1;
            }

            Map<Integer, Integer> sumToIndex = new HashMap<>();//key是对应的presum， value是这个presum第一次出现的index
            sumToIndex.put(0, -1);
            int sum = 0, res = 0;

            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
                if (sumToIndex.containsKey(sum)) {
                    res = Math.max(res, i - sumToIndex.get(sum));
                } else {
                    sumToIndex.put(sum, i);
                }
            }

            return res;
        }
    }
}

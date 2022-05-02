package topinterviewquestions;

import java.util.Arrays;

public class Problem_1877_MinimizeMaximumPairSuminArray_G {
    //https://leetcode-cn.com/problems/minimize-maximum-pair-sum-in-array/solution/shu-zu-zhong-zui-da-shu-dui-he-de-zui-xi-cvll/
    class Solution {
        public int minPairSum(int[] nums) {
            int n = nums.length;
            int res = 0;
            Arrays.sort(nums);
            for (int i = 0; i < n / 2; ++i) {
                res = Math.max(res, nums[i] + nums[n - 1 - i]);
            }
            return res;
        }
    }

}

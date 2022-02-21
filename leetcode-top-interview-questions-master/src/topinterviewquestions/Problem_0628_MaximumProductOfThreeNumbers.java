package topinterviewquestions;

import java.util.Arrays;
/*
Given an integer array nums, find three numbers whose product is maximum and return the maximum product.

Example 1:

Input: nums = [1,2,3]
Output: 6
Example 2:

Input: nums = [1,2,3,4]
Output: 24
Example 3:

Input: nums = [-1,-2,-3]
Output: -6


Constraints:

3 <= nums.length <= 104
-1000 <= nums[i] <= 1000
 */
public class Problem_0628_MaximumProductOfThreeNumbers {

	//Approach 3: Single Scan
	//Time complexity : O(n). Only one iteration over the nums array of length nn is required.
	//Space complexity : O(1). Constant extra space is used.
	public class Solution {
		public int maximumProduct(int[] nums) {
			int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
			//min1是最小的数，min2是次小的数
			int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE;
			//max1最大，max2次大，max3第三大
			for (int n : nums) {
				if (n <= min1) {
					min2 = min1;
					min1 = n;
				} else if (n <= min2) {     // n lies between min1 and min2
					min2 = n;
				}
				if (n >= max1) {            // n is greater than max1, max2 and max3
					max3 = max2;
					max2 = max1;
					max1 = n;
				} else if (n >= max2) {     // n lies betweeen max1 and max2
					max3 = max2;
					max2 = n;
				} else if (n >= max3) {     // n lies betwen max2 and max3
					max3 = n;
				}
			}
			return Math.max(min1 * min2 * max1, max1 * max2 * max3);
		}
	}


	//Approach 2: Using Sorting
	//Time complexity : O(nlogn). Sorting the numsnums array takes nlogn time.
	//Space complexity : O(logn). Sorting takes O(logn) space.
	public class Solution1 {
		public int maximumProduct(int[] nums) {
			Arrays.sort(nums);
			return Math.max(nums[0] * nums[1] * nums[nums.length - 1], nums[nums.length - 1] * nums[nums.length - 2] * nums[nums.length - 3]);
		}
	}


}

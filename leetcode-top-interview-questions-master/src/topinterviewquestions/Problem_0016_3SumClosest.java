package topinterviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem_0016_3SumClosest {
	//https://leetcode.com/problems/3sum-closest/discuss/7872/Java-solution-with-O(n2)-for-reference/222152
	//Time Complexity: O(n^2 + nlogn) = O(n^2). nlogn: quicksort, n^2: the loop
	//Space Complexity: quick sort O(logn)
	public int threeSumClosest(int[] nums, int target) {
		Arrays.sort(nums);
		int res = nums[0]+nums[1]+nums[2];
		for(int i=0; i<nums.length; i++)
		{
			int l = i+1;
			int r = nums.length-1;
			while(l<r) {
				int sum = nums[l]+nums[r]+nums[i];
				if(Math.abs(sum-target)<Math.abs(res-target)) {
					res = sum;
				}
				if(sum>target) {
					r--;
				} else if(sum<target) {
					l++;
				} else {
					return sum;
				}
			}
		}
		return res;
	}

}

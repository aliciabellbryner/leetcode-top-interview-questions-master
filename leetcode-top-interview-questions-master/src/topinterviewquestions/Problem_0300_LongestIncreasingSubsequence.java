package topinterviewquestions;

import java.util.ArrayList;
/*
Given an integer array nums, return the length of the longest strictly increasing subsequence.

A subsequence is a sequence that can be derived from an array by deleting some or no elements without changing the order of the remaining elements. For example, [3,6,2,7] is a subsequence of the array [0,3,1,6,2,2,7].



Example 1:

Input: nums = [10,9,2,5,3,7,101,18]
Output: 4
Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
Example 2:

Input: nums = [0,1,0,3,2,3]
Output: 4
Example 3:

Input: nums = [7,7,7,7,7,7,7]
Output: 1


Constraints:

1 <= nums.length <= 2500
-104 <= nums[i] <= 104
 */
public class Problem_0300_LongestIncreasingSubsequence {



	//time O(NlogN) space O(N)
	//Approach 3: Improve With Binary Search
	public int lengthOfLIS(int[] nums) {
		ArrayList<Integer> res = new ArrayList<>();//用来存放longest increasing sequence
		//比如[1,6,8,2]:
		//i = 0, res: {1}
		//i = 1, res: {1,6}
		//i = 2, res: {1,6,8}
		//i = 3, res: {1,2,8}
		//所以return res.size() = 3
		res.add(nums[0]);

		for (int i = 1; i < nums.length; i++) {
			if (nums[i] > res.get(res.size() - 1)) {
				res.add(nums[i]);
			} else {
				int j = binarySearch(res, nums[i]);
				res.set(j, nums[i]);
			}
		}

		return res.size();
	}

	private int binarySearch(ArrayList<Integer> res, int target) {//找到target应该放在res arraylist中的位置
		int l = 0, r = res.size() - 1, m;
		while (l < r) {
			m = r + ((l - r) >> 1);
			if (res.get(m) == target) {
				return m;
			}
			if (res.get(m) < target) {
				l = m + 1;
			} else {
				r = m;
			}
		}

		return l;
	}

	//time O(N^2) space O(N)
	//这个是上面方法的慢一点版本，没用二分法
	//Approach 2: Intelligently Build a Subsequence
	public int lengthOfLIS1(int[] nums) {
		ArrayList<Integer> sub = new ArrayList<>();
		sub.add(nums[0]);

		for (int i = 1; i < nums.length; i++) {
			int num = nums[i];
			if (num > sub.get(sub.size() - 1)) {
				sub.add(num);
			} else {
				// Find the first element in sub that is greater than or equal to num
				int j = 0;
				while (sub.get(j) < num) {
					j++;
				}

				sub.set(j, num);
			}
		}

		return sub.size();
	}


	//Zuo's solution, too long
	//进阶课leetcode 20讲里有详细讲解
	//time O(NlogN), space O(N)
	public static int lengthOfLIS3(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[] ends = new int[arr.length];//代表所以长度为i+1的递增子序列中，最小结尾是啥，它一定是递增的
		ends[0] = arr[0];
		int right = 0;
		int l = 0;
		int r = 0;
		int m = 0;
		int max = 1;
		for (int i = 1; i < arr.length; i++) {
			l = 0;
			r = right;
			while (l <= r) {//二分法，目的就是求出第一个让ends[i]大于等于arr[i]最左的位置,
				// 如果没找到，则根据l=m+1, l最终会落在最左边还没被初始化的ends位置，就相当于用arr[i]的值扩充了一位的ends
				m = (l + r) / 2;
				if (ends[m] < arr[i]) {
					l = m + 1;
				} else {
					r = m - 1;
				}
			}
			right = Math.max(right, l);
			ends[l] = arr[i];//把end[l]大于等于arr[i]最左的位置改成arr[i]
			max = Math.max(max, l + 1);//每到一次i位置，则可以知道如果以i结尾的最长升序列的长度是l+1
		}
		return max;
	}

	//follow up: longest decreasing subsequence
	static int lds(int arr[], int n)
	{
		int lds[] = new int[n];
		int i, j, max = 0;

		// Initialize LDS with 1
		// for all index. The minimum
		// LDS starting with any
		// element is always 1
		for (i = 0; i < n; i++)
			lds[i] = 1;

		// Compute LDS from every
		// index in bottom up manner
		for (i = 1; i < n; i++)
			for (j = 0; j < i; j++)
				if (arr[i] < arr[j] && lds[i] < lds[j] + 1) {
					lds[i] = lds[j] + 1;
				}

		// Select the maximum
		// of all the LDS values
		for (i = 0; i < n; i++)
			if (max < lds[i]) {
				max = lds[i];
			}
		// returns the length
		// of the LDS
		return max;
	}


}

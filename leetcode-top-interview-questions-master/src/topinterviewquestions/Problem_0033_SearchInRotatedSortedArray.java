package topinterviewquestions;
/*
There is an integer array nums sorted in ascending order (with distinct values).

Prior to being passed to your function, nums is possibly rotated at an unknown pivot index k (1 <= k < nums.length) such that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed). For example, [0,1,2,4,5,6,7] might be rotated at pivot index 3 and become [4,5,6,7,0,1,2].

Given the array nums after the possible rotation and an integer target, return the index of target if it is in nums, or -1 if it is not in nums.

You must write an algorithm with O(log n) runtime complexity.

Example 1:

Input: nums = [4,5,6,7,0,1,2], target = 0
Output: 4
Example 2:

Input: nums = [4,5,6,7,0,1,2], target = 3
Output: -1
Example 3:

Input: nums = [1], target = 0
Output: -1

Constraints:

1 <= nums.length <= 5000
-104 <= nums[i] <= 104
All values of nums are unique.
nums is an ascending array that is possibly rotated.
-104 <= target <= 104
 */
public class Problem_0033_SearchInRotatedSortedArray {


	//if nums has distinctive values
	public static int search(int[] nums, int target) {
		int l = 0;
		int r = nums.length - 1;
		int m = -1;
		while (l <= r) {
			m = l + ((r - l) >> 1);
			if (nums[m] == target) {
				return m;
			}
			if (nums[m] >= nums[l]) {
				if (target >= nums[l] && target < nums[m]) {
					r = m - 1;
				} else {
					l = m + 1;
				}
			} else {
				if (target > nums[m] && target <= nums[r]) {
					l = m + 1;
				} else {
					r = m - 1;
				}
			}
		}
		return -1;
	}

	//if nums has duplicates, just a little change in the nums[m] vs nums[l] relations
	public static boolean search_3(int[] nums, int target) {
		int l = 0;
		int r = nums.length - 1;
		int m = -1;
		while (l <= r) {
			m = l + ((r - l) >> 1);
			if (nums[m] == target) {
				return true;
			}
			if (nums[m] > nums[l]) {
				if (target >= nums[l] && target < nums[m]) {
					r = m - 1;
				} else {
					l = m + 1;
				}
			} else if (nums[m] < nums[l]){
				if (target > nums[m] && target <= nums[r]) {
					l = m + 1;
				} else {
					r = m - 1;
				}
			} else {
				l++;
			}
		}
		return false;
	}


	//if nums has same values, Zuo's solution, hard to remember, don't use
	public static int search7777(int[] nums, int target) {
		int l = 0;
		int r = nums.length - 1;
		int m = 0;
		while (l <= r) {
			m = (l + r) / 2;
			if (nums[m] == target) {
				return m;
			}
			// nums[M] != target
			if (nums[l] == nums[m] && nums[m] == nums[r]) {//这一题其实可以不用这一部分，因为题目说了arr中每个元素都不一样
				while (l != m && nums[l] == nums[m]) {
					l++;
				}
				// L和M没撞上，[l]!=[M] l,.....M
				if (l == m) {
					l = m + 1;
					continue;
				}
			}
			// nums[M] != target
			// [l] [M] [r] 不都一样的情况
			if (nums[l] != nums[m]) { //[l] != [M] ,  [M]==[r]
				if (nums[m] > nums[l]) {
					if (target >= nums[l] && target < nums[m]) {
						r = m - 1;
					} else {
						l = m + 1;
					}
				} else { //  [l]  >  [M]
					if (target > nums[m] && target <= nums[r]) {
						l = m + 1;
					} else {
						r = m - 1;
					}
				}
			} else { // [l] === [M] ->  [M]!=[r]
				if (nums[m] < nums[r]) {
					if (target > nums[m] && target <= nums[r]) {
						l = m + 1;
					} else {
						r = m - 1;
					}
				} else {
					if (target >= nums[l] && target < nums[m]) {
						r = m - 1;
					} else {
						l = m + 1;
					}
				}
			}
		}
		return -1;
	}


}

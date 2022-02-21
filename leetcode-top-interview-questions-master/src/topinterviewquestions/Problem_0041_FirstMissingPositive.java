package topinterviewquestions;
/*
Given an unsorted integer array nums, return the smallest missing positive integer.

You must implement an algorithm that runs in O(n) time and uses constant extra space.

Example 1:

Input: nums = [1,2,0]
Output: 3
Example 2:

Input: nums = [3,4,-1,1]
Output: 2
Example 3:

Input: nums = [7,8,9,11,12]
Output: 1


Constraints:

1 <= nums.length <= 5 * 105
-231 <= nums[i] <= 231 - 1
 */
public class Problem_0041_FirstMissingPositive {
    public static int firstMissingPositive(int[] arr) {
		int l = 0;//means the [0,l-1] all meet the requirement, arr[i] = i+1;
		int r = arr.length;//means the smallest value that will not meet the requirement
		while (l < r) {
			if (arr[l] == l + 1) {
				l++;
			} else if (arr[l] < l + 1 || arr[l] >= r + 1 || arr[arr[l] - 1] == arr[l]) {
				//arr[arr[l] - 1] == arr[l] means that the arr[l] - 1 position has already got what he needs, so just put the l value into the last
				swap(arr,l,--r);
			} else {
				swap(arr, l, arr[l] - 1);
			}
		}
		return l + 1;
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
}

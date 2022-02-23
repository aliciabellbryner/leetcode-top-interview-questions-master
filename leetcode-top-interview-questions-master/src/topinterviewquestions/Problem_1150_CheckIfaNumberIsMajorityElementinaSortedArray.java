package topinterviewquestions;
/*
Given an integer array nums sorted in non-decreasing order and an integer target, return true if target is a majority element, or false otherwise.

A majority element in an array nums is an element that appears more than nums.length / 2 times in the array.



Example 1:

Input: nums = [2,4,5,5,5,5,5,6,6], target = 5
Output: true
Explanation: The value 5 appears 5 times and the length of the array is 9.
Thus, 5 is a majority element because 5 > 9/2 is true.
Example 2:

Input: nums = [10,100,101,101], target = 101
Output: false
Explanation: The value 101 appears 2 times and the length of the array is 4.
Thus, 101 is not a majority element because 2 > 4/2 is false.


Constraints:

1 <= nums.length <= 1000
1 <= nums[i], target <= 109
nums is sorted in non-decreasing order.
 */
public class Problem_1150_CheckIfaNumberIsMajorityElementinaSortedArray {

    //diss
    /*

     */
    class Solution {
        public boolean isMajorityElement(int[] nums, int target) {
            int n = nums.length;
            int lo = 0, hi = n - 1;
            while (lo < hi) {
                int mid = lo + ((hi-lo)>>1);
                if (nums[mid] < target)
                    lo = mid + 1;
                else hi = mid;
            }
            return nums[lo] == target && lo + n/2 < n && nums[lo + n / 2] == target;
        }
    }


    //Clean Java O(log(n)) Solution (Binary Search)
    public boolean isMajorityElement(int[] nums, int target) {
        int n = nums.length;
        int first = search(nums, target);
        int last = search(nums, target + 1);
        return last - first > n / 2;
    }

    private int search(int[] nums, int target) {
        int lo = 0, hi = nums.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }
}

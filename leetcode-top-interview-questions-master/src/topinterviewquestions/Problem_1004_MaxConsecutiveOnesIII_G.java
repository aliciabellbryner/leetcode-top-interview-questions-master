package topinterviewquestions;

/*
Given a binary array nums and an integer k, return the maximum number of consecutive 1's in the array if you can flip at most k 0's.

Example 1:

Input: nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
Output: 6
Explanation: [1,1,1,0,0,1,1,1,1,1,1]
Bolded numbers were flipped from 0 to 1. The longest subarray is underlined.
Example 2:

Input: nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], k = 3
Output: 10
Explanation: [0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1]
Bolded numbers were flipped from 0 to 1. The longest subarray is underlined.


Constraints:

1 <= nums.length <= 105
nums[i] is either 0 or 1.
0 <= k <= nums.length
 */
public class Problem_1004_MaxConsecutiveOnesIII_G {
    //https://leetcode.com/problems/max-consecutive-ones-iii/discuss/247564/JavaC%2B%2BPython-Sliding-Window
    //Intuition
    //Translation:
    //Find the longest subarray with at most K zeros.
    //Explanation
    //For each nums[right], try to find the longest subarray.
    //If nums[i] ~ nums[j] has zeros <= K, we continue to increment right.
    //If nums[i] ~ nums[j] has zeros > K, we increment left (as well as right).
    public int longestOnes(int[] nums, int K) {
        int left = 0, right;
        for (right = 0; right < nums.length; ++right) {
            if (nums[right] == 0) {
                K--;
            }
            if (K < 0 && nums[left++] == 0) {
                K++;
            }
        }
        return right - left;
    }

}

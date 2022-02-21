package topinterviewquestions;
/*
Given an array nums which consists of non-negative integers and an integer m, you can split the array into m non-empty continuous subarrays.

Write an algorithm to minimize the largest sum among these m subarrays.



Example 1:

Input: nums = [7,2,5,10,8], m = 2
Output: 18
Explanation:
There are four ways to split nums into two subarrays.
The best way is to split it into [7,2,5] and [10,8],
where the largest sum among the two subarrays is only 18.
Example 2:

Input: nums = [1,2,3,4,5], m = 2
Output: 9
Example 3:

Input: nums = [1,4,4], m = 3
Output: 4


Constraints:

1 <= nums.length <= 1000
0 <= nums[i] <= 106
1 <= m <= min(50, nums.length)
 */
public class Problem_0410_SplitArrayLargestSum_G {
    //Approach 3: Binary Search
    //Here N is the length of the array and SS is the sum of integers in the array.
    //Time complexity: O(N⋅log(S))
    //The total number of iterations in the binary search is log(S), and for each such iteration,
    // we call minimumSubarraysRequired which takes O(N) time. Hence, the time complexity is equal to O(N⋅log(S)).
    //Space complexity: O(1) We do not use any data structures that require more than constant extra space.
    class Solution {

        public int splitArray(int[] nums, int m) {
            // Find the sum of all elements and the maximum element
            int sum = 0;
            int max = Integer.MIN_VALUE;//找到nums中最大的元素
            for (int element : nums) {
                sum += element;
                max = Math.max(max, element);
            }

            // Define the left and right boundary of binary search
            int left = max;
            int right = sum;
            int res = 0;
            while (left <= right) {
                // Find the mid value
                int mid = left + (right - left) / 2;

                // Find the minimum splits. If splitsRequired is less than
                // or equal to m move towards left i.e., smaller values
                if (minimumSubarraysRequired(nums, mid) <= m) {
                    right = mid - 1;
                    res = mid;
                } else {
                    // Move towards right if splitsRequired is more than m
                    left = mid + 1;
                }
            }

            return res;
        }
    }

    private int minimumSubarraysRequired(int[] nums, int val) {
        //这个是如果要达到val是最小的每个subarray的最大值的情况下，最小需要几个subarray
        int currentSum = 0;
        int splits = 0;

        for (int element : nums) {
            // Add element only if the sum doesn't exceed maxSumAllowed
            if (currentSum + element <= val) {
                currentSum += element;
            } else {
                // If the element addition makes sum more than maxSumAllowed
                // Increment the splits required and reset sum
                currentSum = element;
                splits++;
            }
        }

        // Return the number of subarrays, which is the number of splits + 1
        return splits + 1;
    }
}

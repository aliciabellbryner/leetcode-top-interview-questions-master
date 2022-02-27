package topinterviewquestions;
/*
Given a 0-indexed integer array nums, return true if it can be made strictly increasing after removing exactly one element, or false otherwise. If the array is already strictly increasing, return true.

The array nums is strictly increasing if nums[i - 1] < nums[i] for each index (1 <= i < nums.length).



Example 1:

Input: nums = [1,2,10,5,7]
Output: true
Explanation: By removing 10 at index 2 from nums, it becomes [1,2,5,7].
[1,2,5,7] is strictly increasing, so return true.
Example 2:

Input: nums = [2,3,1,2]
Output: false
Explanation:
[3,1,2] is the result of removing the element at index 0.
[2,1,2] is the result of removing the element at index 1.
[2,3,2] is the result of removing the element at index 2.
[2,3,1] is the result of removing the element at index 3.
No resulting array is strictly increasing, so return false.
Example 3:

Input: nums = [1,1,1]
Output: false
Explanation: The result of removing any element is [1,1].
[1,1] is not strictly increasing, so return false.


Constraints:

2 <= nums.length <= 1000
1 <= nums[i] <= 1000
 */
public class Problem_1909_RemoveOneElementtoMaketheArrayStrictlyIncreasing {
    //https://leetcode.com/problems/remove-one-element-to-make-the-array-strictly-increasing/discuss/1298443/C%2B%2B-O(n)-one-pass-explained-multiple-solutions
    public boolean canBeIncreasing(int[] nums) {
        int count = 0;
        for (int i = 1; i < nums.length; ++i) {
            if (nums[i] <= nums[i - 1]) {//当找到了递减的一对
                if (count == 1) {//说明之前已经找到过一对递减的了了
                    return false;
                }
                count++;
                if (i > 1 && nums[i] <= nums[i - 2] ) {
                    nums[i] = nums[i - 1];//因为如果说nums[i] <= nums[i - 2]并且nums[i] <= nums[i - 1]，那肯定是删掉nums[i]，所以后面一个数应该是和nums[i - 1]比较
                }
            }
        }
        return true;
    }

    //https://leetcode.com/problems/remove-one-element-to-make-the-array-strictly-increasing/discuss/1298443/C++-O(n)-one-pass-explained-multiple-solutions/989396
    //this is another leetcode 300 to me:
    public boolean canBeIncreasing2(int[] nums) {
        int len = 0;
        int[] arr = new int[nums.length];
        for(int n : nums){
            int left = 0, right = len;
            while(left < right){
                int mid = left + (right - left) / 2;
                if(arr[mid] < n){
                    left = mid + 1;
                }else{
                    right = mid;
                }
            }
            arr[left] = n;
            if(left == len){
                len++;
            }
        }
        return len >= nums.length - 1;
    }
}

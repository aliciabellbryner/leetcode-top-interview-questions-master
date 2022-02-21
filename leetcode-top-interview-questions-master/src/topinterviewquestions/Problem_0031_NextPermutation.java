package topinterviewquestions;
/*
A permutation of an array of integers is an arrangement of its members into a sequence or linear order.

For example, for arr = [1,2,3], the following are considered permutations of arr: [1,2,3], [1,3,2], [3,1,2], [2,3,1].
The next permutation of an array of integers is the next lexicographically greater permutation of its integer. More formally, if all the permutations of the array are sorted in one container according to their lexicographical order, then the next permutation of that array is the permutation that follows it in the sorted container. If such arrangement is not possible, the array must be rearranged as the lowest possible order (i.e., sorted in ascending order).

For example, the next permutation of arr = [1,2,3] is [1,3,2].
Similarly, the next permutation of arr = [2,3,1] is [3,1,2].
While the next permutation of arr = [3,2,1] is [1,2,3] because [3,2,1] does not have a lexicographical larger rearrangement.
Given an array of integers nums, find the next permutation of nums.

The replacement must be in place and use only constant extra memory.

Example 1:

Input: nums = [1,2,3]
Output: [1,3,2]
Example 2:

Input: nums = [3,2,1]
Output: [1,2,3]
Example 3:

Input: nums = [1,1,5]
Output: [1,5,1]

Constraints:

1 <= nums.length <= 100
0 <= nums[i] <= 100
 */
public class Problem_0031_NextPermutation {
    //首先找到从右往左第一个值变小的index firstReducingR, 如果没找到即firstReducingR 依旧等于初始值-1则把他们全部reverse，
    // 找到了的话，找到从右往左第一个比nums[firstReducingR]大的数nums[closestLargerR]，然后先把他们俩调换swap，再把【firstReducingR+1, N-1】区间的reverse
    //1433 -> firstReducingR = 0, closestLargerR = 3, so 1433 -> 3431 -> 3134
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int N = nums.length;
        int firstReducingR = -1;
        for (int i = N - 2; i >= 0; i--) {
            if (nums[i] < nums[i+1]) {
                firstReducingR = i;
                break;
            }
        }
        if (firstReducingR < 0) {
            reverse(nums, 0, N-1);
        } else {
            int closestLargerR = -1;
            for (int i = N - 1; i > firstReducingR; i--) {
                if (nums[i] > nums[firstReducingR]) {
                    closestLargerR = i;
                    break;
                }
            }
            swap(nums, firstReducingR, closestLargerR);
            reverse(nums, firstReducingR+1, N-1);
        }

    }
    public void reverse(int[] nums, int l, int r) {
        while (l < r) {
            swap(nums, l++, r--);
        }
    }
    public void swap(int[] nums, int l, int r) {
        int temp = nums[l];
        nums[l] = nums[r];
        nums[r] = temp;
    }
}

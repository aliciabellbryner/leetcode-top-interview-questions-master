package topinterviewquestions;
/*
Given an integer array nums which is sorted in ascending order and all of its elements are unique and given also an integer k, return the kth missing number starting from the leftmost number of the array.



Example 1:

Input: nums = [4,7,9,10], k = 1
Output: 5
Explanation: The first missing number is 5.
Example 2:

Input: nums = [4,7,9,10], k = 3
Output: 8
Explanation: The missing numbers are [5,6,8,...], hence the third missing number is 8.
Example 3:

Input: nums = [1,2,4], k = 3
Output: 6
Explanation: The missing numbers are [3,5,6,7,...], hence the third missing number is 6.


Constraints:

1 <= nums.length <= 5 * 104
1 <= nums[i] <= 107
nums is sorted in ascending order, and all the elements are unique.
1 <= k <= 108


Follow up: Can you find a logarithmic time complexity (i.e., O(log(n))) solution?
 */
public class Problem_1060_MissingElementInSortedArray {
    /*
    Approach 1: One Pass
Intuition

The problem is similar to First Missing Positive and the naive idea would be to solve it in a similar way by one pass approach.

Let's first assume that one has a function missing(idx) that returns how many numbers are missing until the element at index idx.

fig

With the help of such a function the solution is straightforward :

Find an index such that missing(idx - 1) < k <= missing(idx). In other words, that means that kth missing number is in-between nums[idx - 1] and nums[idx].

One even could compute a difference between kth missing number and nums[idx - 1]. First, there are missing(idx - 1) missing numbers until nums[idx - 1]. Second, all k - missing(idx - 1) missing numbers from nums[idx - 1] to kth missing are consecutive ones, because all of them are less than nums[idx] and hence there is nothing to separate them. Together that means that kth smallest is larger than nums[idx - 1] by k - missing(idx - 1).

Return kth smallest nums[idx - 1] + k - missing(idx - 1).

fic

The last thing to discuss is how to implement missing(idx) function.

Let's consider an array element at index idx. If there is no numbers missing, the element should be equal to nums[idx] = nums[0] + idx. If k numbers are missing, the element should be equal to nums[idx] = nums[0] + idx + k. Hence the number of missing elements is equal to nums[idx] - nums[0] - idx.

pic

Algorithm

Implement missing(idx) function that returns how many numbers are missing until array element with index idx. Function returns nums[idx] - nums[0] - idx.

Find an index such that missing(idx - 1) < k <= missing(idx) by a linear search.

Return kth smallest nums[idx - 1] + k - missing(idx - 1).

Implementation


Complexity Analysis

Time complexity: \mathcal{O}(N)O(N) since in the worst case it's one pass along the array.

Space complexity: \mathcal{O}(1)O(1) since it's a constant space solution.


Approach 2: Binary Search
Intuition

Approach 1 uses the linear search and doesn't profit from the fact that array is sorted. One could replace the linear search by a binary one and reduce the time complexity from \mathcal{O}(N)O(N) down to \mathcal{O}(\log N)O(logN).

The idea is to find the leftmost element such that the number of missing numbers until this element is less or equal to k.

fif

Algorithm

Implement missing(idx) function that returns how many numbers are missing until array element with index idx. Function returns nums[idx] - nums[0] - idx.

Find an index such that missing(idx - 1) < k <= missing(idx) by a binary search.

Return kth smallest nums[idx - 1] + k - missing(idx - 1).

Implementation


Complexity Analysis

Time complexity: \mathcal{O}(\log N)O(logN) since it's a binary search algorithm in the worst case when the missing number is less than the last element of the array.

Space complexity : \mathcal{O}(1)O(1) since it's a constant space solution.
     */

    class Solution {
        // Return how many numbers are missing until nums[idx]
        int missing(int idx, int[] nums) {
            return nums[idx] - nums[0] - idx;
        }

        public int missingElement(int[] nums, int k) {
            int n = nums.length;
            // If kth missing number is larger than
            // the last element of the array
            if (k > missing(n - 1, nums))
                return nums[n - 1] + k - missing(n - 1, nums);

            int idx = 1;
            // find idx such that
            // missing(idx - 1) < k <= missing(idx)
            while (missing(idx, nums) < k) idx++;

            // kth missing number is greater than nums[idx - 1]
            // and less than nums[idx]
            return nums[idx - 1] + k - missing(idx - 1, nums);
        }
    }

    class Solution2 {
        // Return how many numbers are missing until nums[idx]
        int missing(int idx, int[] nums) {
            return nums[idx] - nums[0] - idx;
        }

        public int missingElement(int[] nums, int k) {
            int n = nums.length;
            // If kth missing number is larger than
            // the last element of the array
            if (k > missing(n - 1, nums))
                return nums[n - 1] + k - missing(n - 1, nums);

            int left = 0, right = n - 1, pivot;
            // find left = right index such that
            // missing(left - 1) < k <= missing(left)
            while (left != right) {
                pivot = left + (right - left) / 2;

                if (missing(pivot, nums) < k) left = pivot + 1;
                else right = pivot;
            }

            // kth missing number is greater than nums[idx - 1]
            // and less than nums[idx]
            return nums[left - 1] + k - missing(left - 1, nums);
        }
    }


    //dis
    class Solution3 {
        public int missingElement(int[] nums, int k) {
            int left = 0, right = nums.length - 1;
            int missing = nums[right] - nums[left] - (right - left);

            if (k > missing) {
                return nums[right] + k - missing;
            }

            while (left < right - 1) {
                int mid = left + (right - left) / 2;
                missing = nums[mid] - nums[left] - (mid - left);

                if (k > missing) {
                    k -= missing;
                    left = mid;
                }
                else {
                    right = mid;
                }
            }

            return nums[left] + k;
        }
    }


    //To understand this problem we need to work through an example: [4,7,9,10]
    //In this array, we have three missing numbers: 5,6,8, if the k is bigger than 3, we don't need to look into the array.
    //Another thing we can derive is, the missing count between 4 and 10 is: 10 - 4 - 3 = 3, so it's nums[i] - nums[0] - i
    //
    //If k < than total missing in the array, we could use binary search to find it:
    //What we are trying to find by binary search is a index where its missing number less than k, but at its next index, the missing number more than k
    //For example, k is 2, the missing number is 6, then we need to make sure we find 4 at index 0
    //
    //After we find index of 4 which is 0. We can know what is the k-th missing number: 4 + (k - 0), 0 here is the missing numbers before 4, in this example it's 0.
    class Solution4 {
        public int missingElement(int[] nums, int k) {
            int totalMissInArray = countMissing(nums, nums.length - 1);
            if(k > totalMissInArray) {
                return nums[nums.length - 1] + (k - totalMissInArray);
            }

            int s = 0, e = nums.length;
            while(s < e) {
                int m = (s + e) / 2;
                int missing = countMissing(nums,m);
                if(missing >= k) {
                    e = m;
                } else {
                    s = m + 1;
                }
            }
            s--;
            return nums[s] + k - countMissing(nums,s);
        }

        private int countMissing(int[] nums, int i) {
            return nums[i] - nums[0] - i;
        }
    }



}

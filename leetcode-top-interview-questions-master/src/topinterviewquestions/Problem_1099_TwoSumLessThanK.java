package topinterviewquestions;

import java.util.Arrays;

/*
Given an array nums of integers and integer k, return the maximum sum such that there exists i < j with nums[i] + nums[j] = sum and sum < k. If no i, j exist satisfying this equation, return -1.



Example 1:

Input: nums = [34,23,1,24,75,33,54,8], k = 60
Output: 58
Explanation: We can use 34 and 24 to sum 58 which is less than 60.
Example 2:

Input: nums = [10,20,30], k = 15
Output: -1
Explanation: In this case it is not possible to get a pair sum less that 15.


Constraints:

1 <= nums.length <= 100
1 <= nums[i] <= 1000
1 <= k <= 2000
 */
public class Problem_1099_TwoSumLessThanK {
    /*
    Solution Article
This problem is a variation of Two Sum. The main difference is that we are not searching for the exact target here. Instead, our sum is in some relation with the target. For this problem, we are looking for a maximum sum that is smaller than the target.

First, let's check solutions for the similar problems:

Two Sum uses a hashmap to find complement values, and therefore achieves \mathcal{O}(N)O(N) time complexity.
Two Sum II uses the two pointers pattern and also has \mathcal{O}(N)O(N) time complexity for a sorted array. We can use this approach for any array if we sort it first, which bumps the time complexity to \mathcal{O}(n\log{n})O(nlogn).
Since our sum can be any value smaller than the target, we cannot use a hashmap. We do not know which value to look up! Instead, we need to sort the array and use a binary search or the two pointers pattern, like in Two Sum II. In a sorted array, it is easy to find elements that are close to a given value.

Approach 1: Brute Force
It is important to understand the input constraints to choose the most appropriate approach. For this problem, the size of our array is limited to 100. So, a brute force solution could be a reasonable option. It's simple and does not require any additional memory.

Algorithm

For each index i in nums:

For each index j > i in nums:
If nums[i] + nums[j] is less than k:
Track maximum nums[i] + nums[j] in the result answer.
Return the result answer.


Complexity Analysis

Time Complexity: \mathcal{O}(n^2)O(n
2
 ). We have 2 nested loops.

Space Complexity: \mathcal{O}(1)O(1).

Approach 2: Two Pointers
We will follow the same two pointers approach as in Two Sum II. It requires the array to be sorted, so we'll do that first.

As a quick refresher, the pointers are initially set to the first and the last element respectively. We compare the sum of these two elements with the target. If it is smaller than the target, we increment the lower pointer left. Otherwise, we decrement the higher pointer right. Thus, the sum always moves toward the target, and we "prune" pairs that would move it further away. Again, this works only if the array is sorted. Head to the Two Sum II solution for the detailed explanation.

Since the sum here must be smaller than the target, we don't stop when we find a pair that sums exactly to the target. We decrement the higher pointer and continue until our pointers collide. For each iteration, we track the maximum sum - if it's smaller than the target.

Current
1 / 6
Algorithm

Sort the array.

Set the left pointer to zero, and right - to the last index.

While left is smaller than right:

If nums[left] + nums[right] is less than k:
Track maximum nums[left] + nums[right] in the result answer.
Increment left.
Else:
Decrement right.
Return the result answer.


Optimizations

We can break from the loop as soon as nums[left] > k / 2. In the sorted array, nums[left] is the smallest of the remaining elements, so nums[right] > k / 2 for any right. Therefore, nums[left] + nums[right] will be equal or greater than k for the remaining elements.

Complexity Analysis

Time Complexity: \mathcal{O}(n\log{n})O(nlogn) to sort the array. The two pointers approach itself is \mathcal{O}(n)O(n), so the time complexity would be linear if the input is sorted.

Space Complexity: from \mathcal{O}(\log{n})O(logn) to \mathcal{O}(n)O(n), depending on the implementation of the sorting algorithm.

Approach 3: Binary Search
Instead of moving two pointers towards the target, we can iterate through each element nums[i], and binary-search for a complement value k - nums[i]. This approach is less efficient than the two pointers one, however, it can be more intuitive to come up with. Same as above, we need to sort the array first for this to work.

Note that the binary search returns the "insertion point" for the searched value, i.e. the position where that value would be inserted to keep the array sorted. So, the binary search result points to the first element that is equal or greater than the complement value. Since our sum must be smaller than k, we consider the element immediately before the found element.

Algorithm

Sort the array.

For each index i in nums:

Binary search for k - nums[i] starting from i + 1.
Set j to the position before the found element.
If j is less than i:
Track maximum nums[i] + nums[j] in the result answer.
Return the result answer.

Note that the binary search function in Java works a bit differently. If there are multiple elements that match the search value, it does not guarantee to point to the first one. That's why in the Java solution below we search for k - nums[i] - 1. Note that we decrement the pointer only if the value we found is greater than k - nums[i] - 1.


Complexity Analysis

Time Complexity: \mathcal{O}(n\log{n})O(nlogn) to sort the array and do the binary search for each element.

Space Complexity: from \mathcal{O}(\log{n})O(logn) to \mathcal{O}(n)O(n), depending on the implementation of the sorting algorithm.

Approach 4: Counting Sort
We can leverage the fact that the input number range is limited to [1..1000] and use a counting sort. Then, we can use the two pointers pattern to enumerate pairs in the [1..1000] range.

Note that the result can be a sum of two identical numbers, and that means that lo can be equal to hi. In this case, we need to check if the count for that number is greater than one.

Algorithm

Count each element using the array count.

Set the lo number to zero, and hi - to 1000.

While lo is smaller than, or equals hi:

If lo + hi is greater than k, or count[hi] == 0:
Decrement hi.
Else:
If count[lo] is greater than 0 (when lo < hi), or 1 (when lo == hi):
Track maximum lo + hi in the result answer.
Increment lo.
Return the result answer.


Optimizations

We can set hi to either maximum number, or k - 1, whichever is smaller.
We can ignore numbers greater than k - 1.
We can use a boolean array (e.g. seen) instead of count. In the first loop, we will check if i is a duplicate (seen[i] is already true) and set answer to the highest i + i < k. Note that the two pointers loop will run while lo < hi, not while lo <= hi.
We can break from the two pointers loop as soon as nums[lo] > k / 2.
Complexity Analysis

Time Complexity: \mathcal{O}(n + m)O(n+m), where mm corresponds to the range of values in the input array.

Space Complexity: \mathcal{O}(m)O(m) to count each value.

Further Thoughts
Always clarify the problem constraints and inputs during an interview. This would help you choose the right approach.

The Two Pointers approach is a good choice when the number of elements is large, and the range of possible values is not constrained. Also, if the input array is already sorted, this approach provides a linear time complexity and does not require additional memory.
     */
    class Solution2 {
        public int twoSumLessThanK(int[] nums, int k) {
            Arrays.sort(nums);
            int answer = -1;
            int left = 0;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum < k) {
                    answer = Math.max(answer, sum);
                    left++;
                } else {
                    right--;
                }
            }
            return answer;
        }
    }

    class Solution3 {
        public int twoSumLessThanK(int[] nums, int k) {
            int answer = -1;
            Arrays.sort(nums);
            for (int i = 0; i < nums.length; ++i) {
                int idx = Arrays.binarySearch(nums, i + 1, nums.length, k - nums[i] - 1);
                int j = (idx >= 0 ? idx : ~idx);
                if (j == nums.length || nums[j] > k - nums[i] - 1) {
                    j--;
                }
                if (j > i) {
                    answer = Math.max(answer, nums[i] + nums[j]);
                }
            }
            return answer;
        }
    }


    class Solution4 {
        public int twoSumLessThanK(int[] nums, int k) {
            int answer = -1;
            int[] count = new int[1001];
            for (int num : nums) {
                count[num]++;
            }
            int lo = 1;
            int hi = 1000;
            while (lo <= hi) {
                if (lo + hi >= k || count[hi] == 0) {
                    hi--;
                } else {
                    if (count[lo] > (lo < hi ? 0 : 1)) {
                        answer = Math.max(answer, lo + hi);
                    }
                    lo++;
                }
            }
            return answer;
        }
    }


    //dis
    //Note: 1) I have no premium subscription and can NO longer read comments under locked problems, though I can still edit my post now. 2) Sorry that I can NOT answer your questions.
    //Sort the input A;
    //Push from the two ends and attempt to find any addition of the two elements < K; if the addition >= K, then decrease the high bound and hence tentatively get a smaller addition; otherwise, increase low bound to find a bigger addition;
    //repeat 2 till the end.
    //Time: O(nlogn), space: O(1), where n = A.length.
    public int twoSumLessThanK(int[] A, int K) {
        Arrays.sort(A); // Time cost O(nlogn).
        int max = -1, i = 0, j = A.length - 1;
        while (i < j) {
            int sum = A[i] + A[j];
            if (sum < K) { // find one candidate.
                max = Math.max(max, sum);
                ++i; // increase the smaller element.
            }else { // >= sum.
                --j; // decrease the bigger element.
            }
        }
        return max;
    }

    //Be prepared when the interviewer changes the constraints or inputs or ask you to change your approach:
    public int twoSumLessThanK3(int[] nums, int k) {
        // return bruteForce_approach(nums, k);
        // return sort_approach(nums, k);
        // return sortBinarySearch_approach(nums, k);
        return bucket_approach(nums, k);
    }

    // O(n^2)
    private int bruteForce_approach(int[] nums, int k) {

        int answer = -1;
        int n = nums.length;
        for (int i = 0; i < n-1; i++) {
            for (int j = i+1; j < n; j++) {
                int sum = nums[i] + nums[j];
                if (sum >= k) continue;
                answer = Math.max(answer, sum);
            }
        }
        return answer;
    }

    // O(nlogn)
    private int sort_approach(int[] nums, int k) {

        int answer = -1;
        Arrays.sort(nums);

        int lo = 0, hi = nums.length-1;
        while (lo < hi) {

            int sum = nums[lo] + nums[hi];
            if (sum >= k) hi--;
            else {
                lo++;
                answer = Math.max(answer, sum);
            }
        }

        return answer;
    }

    // O(nlogn)
    private int sortBinarySearch_approach(int[] nums, int k) {

        int answer = -1;
        Arrays.sort(nums);

        int n = nums.length;
        for (int i = 0; i < n-1; i++) {

            // nums[j] >= nums[i] --> k > sum >= 2*nums[i] --> nums[i] < k/2
            if (nums[i] >= k/2) break;

            // find the maximum index j such that nums[j] < k-nums[i]
            int j = binarySearch(nums, i+1, n-1, k-nums[i]);

            // no found means all values of j: nums[j] >= k-nums[i] ==> j not in range [i+1, n-1]
            // j will be on the left of the range ==> j = i
            if (j > i) {
                answer = Math.max(answer, nums[i] + nums[j]);
            }
        }

        return answer;
    }

    // find the maximum index such that nums[index] < value
    private int binarySearch(int[] nums, int lo, int hi, int value) {
        while (lo <= hi) {
            int mid = lo + (hi-lo)/2;
            if (nums[mid] >= value) hi = mid-1;
            else lo = mid+1;
        }
        return hi;
    }

    // O(n + 1000)
    private int bucket_approach(int[] nums, int k) {
        int answer = -1;
        int[] bucket = new int[1001];
        for (int num: nums) {
            bucket[num]++;
        }

        int lo = 1, hi = 1000;
        while (lo <= hi) {
            while (lo <= hi && bucket[lo] == 0) lo++;
            while (hi >= lo && bucket[hi] == 0) hi--;

            if (lo == hi && bucket[lo] == 1) break;

            int sum = lo+hi;
            if (sum >= k) {
                hi--;
            }
            else {
                answer = Math.max(answer, sum);
                lo++;
            }
        }

        return answer;
    }

}

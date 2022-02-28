package topinterviewquestions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
Given two integer arrays nums1 and nums2, return an array of their intersection. Each element in the result must be unique and you may return the result in any order.



Example 1:

Input: nums1 = [1,2,2,1], nums2 = [2,2]
Output: [2]
Example 2:

Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
Output: [9,4]
Explanation: [4,9] is also accepted.


Constraints:

1 <= nums1.length, nums2.length <= 1000
0 <= nums1[i], nums2[i] <= 1000
 */
public class Problem_0349_IntersectionofTwoArrays_G {
    //https://leetcode.com/problems/intersection-of-two-arrays/solution/
    class Solution {
        public int[] set_intersection(HashSet<Integer> shortSet, HashSet<Integer> longSet) {
            int [] output = new int[shortSet.size()];
            int idx = 0;
            for (Integer s : shortSet)
                if (longSet.contains(s)) output[idx++] = s;

            return Arrays.copyOf(output, idx);
        }

        public int[] intersection(int[] nums1, int[] nums2) {
            HashSet<Integer> set1 = new HashSet<Integer>();
            for (Integer n : nums1) set1.add(n);
            HashSet<Integer> set2 = new HashSet<Integer>();
            for (Integer n : nums2) set2.add(n);

            if (set1.size() < set2.size()) {
                return set_intersection(set1, set2);
            } else {
                return set_intersection(set2, set1);
            }
        }
    }

    //https://leetcode.com/problems/intersection-of-two-arrays/discuss/81969/Three-Java-Solutions
    //Sort both arrays, use two pointers
    //
    //Time complexity: O(nlogn)
    public class Solution2 {
        public int[] intersection(int[] nums1, int[] nums2) {
            Set<Integer> set = new HashSet<>();
            Arrays.sort(nums1);
            Arrays.sort(nums2);
            int i = 0;
            int j = 0;
            while (i < nums1.length && j < nums2.length) {
                if (nums1[i] < nums2[j]) {
                    i++;
                } else if (nums1[i] > nums2[j]) {
                    j++;
                } else {
                    set.add(nums1[i]);
                    i++;
                    j++;
                }
            }
            int[] result = new int[set.size()];
            int k = 0;
            for (Integer num : set) {
                result[k++] = num;
            }
            return result;
        }
    }


    //binary search
    //Time complexity: O(nlogn)
    public class Solution3 {
        public int[] intersection(int[] nums1, int[] nums2) {
            Set<Integer> set = new HashSet<>();
            Arrays.sort(nums2);
            for (Integer num : nums1) {
                if (binarySearch(nums2, num)) {
                    set.add(num);
                }
            }
            int i = 0;
            int[] result = new int[set.size()];
            for (Integer num : set) {
                result[i++] = num;
            }
            return result;
        }

        public boolean binarySearch(int[] nums, int target) {
            int low = 0;
            int high = nums.length - 1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (nums[mid] == target) {
                    return true;
                }
                if (nums[mid] > target) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            return false;
        }
    }
}

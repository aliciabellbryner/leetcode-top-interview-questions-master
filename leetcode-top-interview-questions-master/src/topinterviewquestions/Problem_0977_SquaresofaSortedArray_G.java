package topinterviewquestions;

public class Problem_0977_SquaresofaSortedArray_G {
    //Approach 2: Two Pointer
    //Time Complexity: O(N), where N is the length of A.
    //Space Complexity: O(N) if you take output into account and O(1) otherwise.
    class Solution {
        public int[] sortedSquares(int[] nums) {
            int n = nums.length;
            int[] res = new int[n];
            int left = 0;
            int right = n - 1;

            for (int i = n - 1; i >= 0; i--) {
                int next;
                if (Math.abs(nums[left]) < Math.abs(nums[right])) {
                    next = nums[right];
                    right--;
                } else {
                    next = nums[left];
                    left++;
                }
                res[i] = next * next;
            }
            return res;
        }
    }
}

package topinterviewquestions;

import java.util.Random;

public class Problem_0528_RandomPickwithWeight_G {
    //https://leetcode.com/problems/random-pick-with-weight/solution/
    //Time Complexity
    //For the constructor function, the time complexity would be O(N), which is due to the construction of the prefix sums.
    //For the pickIndex() function, this time its time complexity would be O(logN), since we did a binary search on the prefix sums.
    //Space Complexity
    //For the constructor function, the space complexity remains O(N), which is again due to the construction of the prefix sums.
    //For the pickIndex() function, its space complexity would be O(1), since it uses constant memory. Note, here we consider the prefix sums that it operates on, as the input of the function.
    class Solution {

        int[] preSum;
        public Solution(int[] w) {
            preSum = new int[w.length];
            preSum[0] = w[0];
            for (int i = 1; i < w.length; i++) {
                preSum[i] = preSum[i-1] + w[i];
            }
        }

        public int pickIndex() {
            int len = preSum.length;
            double index = Math.random()*(preSum[len-1]);//Random() : [0,1),
            int left = 0;
            int right = len - 1;
            while (left < right) { // run a binary search to find the target zone
                int mid = left + (right - left) / 2;
                if (preSum[mid] == index) {
                    return mid;
                } else if (preSum[mid] > index) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            return left;
        }
    }

    //https://leetcode.com/problems/random-pick-with-weight/solution/
    class Solution2 {
        private int[] prefixSums;
        private int totalSum;

        public Solution2(int[] w) {
            this.prefixSums = new int[w.length];

            int prefixSum = 0;
            for (int i = 0; i < w.length; ++i) {
                prefixSum += w[i];
                prefixSums[i] = prefixSum;
            }
            this.totalSum = prefixSum;
        }

        public int pickIndex() {
            double target = prefixSums[prefixSums.length-1] * Math.random();

            // run a binary search to find the target zone
            int low = 0, high = this.prefixSums.length;
            while (low < high) {
                // better to avoid the overflow
                int mid = low + (high - low) / 2;
                if (target > this.prefixSums[mid])
                    low = mid + 1;
                else
                    high = mid;
            }
            return low;
        }
    }
}

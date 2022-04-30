package topinterviewquestions;

public class Problem_0852_PeakIndexinaMountainArray_G {
    //Approach 2: Binary Search
    //https://leetcode.com/problems/peak-index-in-a-mountain-array/solution/
    //Time Complexity: O(\log N)O(logN), where NN is the length of A.
    //Space Complexity: O(1)O(1).
    class Solution {
        public int peakIndexInMountainArray(int[] A) {
            int lo = 0, hi = A.length - 1;
            while (lo < hi) {
                int mi = lo + (hi - lo) / 2;
                if (A[mi] < A[mi + 1])
                    lo = mi + 1;
                else
                    hi = mi;
            }
            return lo;
        }
    }
}

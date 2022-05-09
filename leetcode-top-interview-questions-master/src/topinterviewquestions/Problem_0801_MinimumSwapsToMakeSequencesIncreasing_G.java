package topinterviewquestions;

public class Problem_0801_MinimumSwapsToMakeSequencesIncreasing_G {
    //https://leetcode.com/problems/minimum-swaps-to-make-sequences-increasing/discuss/119835/Java-O(n)-DP-Solution
    //swapRecord means for the ith element in A and B, the minimum swaps if we swap A[i] and B[i]
    //fixRecord means for the ith element in A and B, the minimum swaps if we DONOT swap A[i] and B[i]
    class Solution {
        public int minSwap(int[] A, int[] B) {
            int swapRecord = 1, fixRecord = 0;
            for (int i = 1; i < A.length; i++) {
                if (A[i - 1] >= B[i] || B[i - 1] >= A[i]) {
                    // In this case, the ith manipulation should be same as the i-1th manipulation
                    // fixRecord = fixRecord;
                    swapRecord++;
                } else if (A[i - 1] >= A[i] || B[i - 1] >= B[i]) {
                    // In this case, the ith manipulation should be the opposite of the i-1th manipulation
                    int temp = swapRecord;
                    swapRecord = fixRecord + 1;
                    fixRecord = temp;
                } else {
                    // Either swap or fix is OK. Let's keep the minimum one
                    int min = Math.min(swapRecord, fixRecord);
                    swapRecord = min + 1;
                    fixRecord = min;
                }
            }
            return Math.min(swapRecord, fixRecord);
        }
    }
}

package topinterviewquestions;

import java.util.Arrays;

public class Problem_1937_MaximumNumberofPointswithCost_G {
    // idea - we first go left-to-right, and track the running maximum value (run_max).
    // For each step, we decrement递减 it to account for the distance, and compare with the value right above.
    // Then, we do the same right-to-left, and add the maximum of two running values to each cell.
    //Time: O(m * n)
    //Memory: O(n)
    //https://leetcode.com/problems/maximum-number-of-points-with-cost/discuss/1344893/Similar-to-931.-Minimum-Falling-Path-Sum
    public long maxPoints(int[][] points) {
        int N = points[0].length;
        long[] curRow = new long[N], prevRow = new long[N];
        for (int[] point : points) {
            long run_max = 0;
            for (int j = 0; j < point.length; ++j) {
                run_max = Math.max(run_max - 1, prevRow[j]);
                curRow[j] = run_max;
            }
            for (int j = point.length - 1; j >= 0; --j) {
                run_max = Math.max(run_max - 1, prevRow[j]);
                curRow[j] = Math.max(curRow[j], run_max) + point[j];
            }
            prevRow = curRow;
        }
        long res = 0;
        for (long i : prevRow) {
            res = Math.max(i, res);
        }
        return res;
    }
}

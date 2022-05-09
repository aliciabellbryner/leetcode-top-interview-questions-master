package topinterviewquestions;

import java.util.Arrays;
import java.util.Comparator;

public class Problem_1691_MaximumHeightbyStackingCuboids_G {
    //https://leetcode.com/problems/maximum-height-by-stacking-cuboids/discuss/970293/JavaC%2B%2BPython-DP-Prove-with-Explanation
    public int maxHeight(int[][] A) {
        for (int[] a : A)
            Arrays.sort(a);
        Arrays.sort(A, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                if (a[0] != b[0])
                    return b[0] - a[0];
                if (a[1] != b[1])
                    return b[1] - a[1];
                return b[2] - a[2];
            }
        });
        int n = A.length, res = 0, dp[] = new int[n];
        for (int j = 0; j < n; ++j) {
            dp[j] = A[j][2];
            for (int i = 0; i < j; ++i) {
                if (A[i][0] >= A[j][0] && A[i][1] >= A[j][1] && A[i][2] >= A[j][2]) {
                    dp[j] = Math.max(dp[j], dp[i] + A[j][2]);
                }
            }
            res = Math.max(res, dp[j]);
        }
        return res;
    }

    //https://leetcode.com/problems/maximum-height-by-stacking-cuboids/discuss/970394/Clean-Java
    //first we place the box at the height as the maximum, so all the box will be at the height position.
    //because the validation here is to check all 3 dimentional all non-decreasing, so we can use the same logic as LIS, for here, instead of just compare nums[i] >= num[j], we need to check 3 ways nums[length-i, width-i, height-i] >= nums[length-j, width-j, height-k]
    public int maxHeight2(int[][] cuboids) {
        for (int[] cube : cuboids) Arrays.sort(cube);
        Arrays.sort(cuboids, (a, b) -> (a[0] + a[1] + a[2]) - (b[0] + b[1] + b[2]));
        int N = cuboids.length, res = 0;
        int[] dp = new int[N];
        for (int i = 0; i < N; i++) {
            dp[i] = cuboids[i][2];
            res = Math.max(res, dp[i]);
        }
        for (int i = 1; i < N; i++)
            for (int j = 0; j < i; j++)
                if (cuboids[j][0] <= cuboids[i][0] &&
                        cuboids[j][1] <= cuboids[i][1] &&
                        cuboids[j][2] <= cuboids[i][2]) {
                    dp[i] = Math.max(dp[i], dp[j] + cuboids[i][2]);
                    res = Math.max(res, dp[i]);
                }
        return res;
    }
}

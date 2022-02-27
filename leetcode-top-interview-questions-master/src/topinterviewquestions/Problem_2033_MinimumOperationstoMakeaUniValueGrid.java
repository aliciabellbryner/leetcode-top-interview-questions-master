package topinterviewquestions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
/*
Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 105
1 <= m * n <= 105
1 <= x, grid[i][j] <= 104
 */
public class Problem_2033_MinimumOperationstoMakeaUniValueGrid {
//diss


    //https://leetcode.com/problems/minimum-operations-to-make-a-uni-value-grid/discuss/1515856/Java-Efficient-%2B-Interview-solution-%2B-Easy-to-understand
    class Solution2 {
        public int minOperations(int[][] grid, int x) {
            int[] arr = new int[grid.length * grid[0].length];
            int index = 0;

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    arr[index++] = grid[i][j];
                }
            }

            Arrays.sort(arr);
            int median = arr[(arr.length - 1) / 2];
            int steps = 0;

            for (int num : arr) {
                if (num == median) {
                    continue;
                }

                if (Math.abs(num - median) % x != 0) {
                    return -1;
                }

                steps += (Math.abs(num - median) / x);
            }

            return steps;
        }
    }


    public int minOperations(int[][] grid, int x) {
        int m = grid.length, n = grid[0].length;
        int[] arr = new int[m * n];

        for(int i = 0; i < m * n; i++) {
            arr[i] = grid[i / n][i % n];
        }

        Arrays.sort(arr);

        int[] dp = new int[m * n];

        for(int i = 0; i < dp.length - 1; i++){
            if((arr[i + 1] - arr[i]) % x != 0) {//前后两个数之差不是x的倍数，那么肯定不可能可以变成一样的值，所以直接返回-1
                return -1;
            }
            dp[i + 1] = dp[i] + (arr[i + 1] - arr[i]) / x * (i + 1);
        }

        int ops = 0;
        int result = dp[dp.length - 1];

        for(int i = dp.length - 1; i > 0; i--){
            ops += (arr[i] - arr[i - 1]) / x * (dp.length - i);
            result = Math.min(result, ops + dp[i - 1]);
        }

        return result;
    }


}

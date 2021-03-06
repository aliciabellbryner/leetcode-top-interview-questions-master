package topinterviewquestions;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Problem_1368_MinimumCosttoMakeatLeastOneValidPathinaGrid_G {
    //https://leetcode.com/problems/minimum-cost-to-make-at-least-one-valid-path-in-a-grid/discuss/524886/JavaC%2B%2BPython-BFS-and-DFS
    class Solution {
        int INF = (int) 1e9;
        int[][] DIR = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        public int minCost(int[][] grid) {
            int m = grid.length, n = grid[0].length, cost = 0;
            int[][] dp = new int[m][n];
            for (int i = 0; i < m; i++) Arrays.fill(dp[i], INF);
            Queue<int[]> bfs = new LinkedList<>();
            dfs(grid, 0, 0, dp, cost, bfs);
            while (!bfs.isEmpty()) {
                cost++;
                for (int size = bfs.size(); size > 0; size--) {
                    int[] top = bfs.poll();
                    int r = top[0], c = top[1];
                    for (int i = 0; i < 4; i++) dfs(grid, r + DIR[i][0], c + DIR[i][1], dp, cost, bfs);
                }
            }
            return dp[m - 1][n - 1];
        }

        void dfs(int[][] grid, int r, int c, int[][] dp, int cost, Queue<int[]> bfs) {
            int m = grid.length; int n = grid[0].length;
            if (r < 0 || r >= m || c < 0 || c >= n || dp[r][c] != INF) return;
            dp[r][c] = cost;
            bfs.offer(new int[]{r, c}); // add to try change direction later
            int nextDir = grid[r][c] - 1;
            dfs(grid, r + DIR[nextDir][0], c + DIR[nextDir][1], dp, cost, bfs);
        }
    }
}

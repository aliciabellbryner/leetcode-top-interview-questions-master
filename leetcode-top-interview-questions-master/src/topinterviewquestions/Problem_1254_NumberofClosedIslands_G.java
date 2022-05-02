package topinterviewquestions;

public class Problem_1254_NumberofClosedIslands_G {
    //https://leetcode.com/problems/number-of-closed-islands/discuss/425150/JavaC%2B%2B-with-picture-Number-of-Enclaves
    class Solution {
        int fill(int[][] g, int i, int j) {
            if (i < 0 || j < 0 || i >= g.length || j >= g[i].length || g[i][j] != 0)
                return 0;
            return (g[i][j] = 1) + fill(g, i + 1, j) + fill(g, i, j + 1)
                    + fill(g, i - 1, j) + fill(g, i, j - 1);
        }

        public int closedIsland(int[][] g) {
            for (int i = 0; i < g.length; ++i)
                for (int j = 0; j < g[i].length; ++j)
                    if (i * j * (i - g.length + 1) * (j - g[i].length + 1) == 0)
                        fill(g, i, j);
            int res = 0;
            for (int i = 0; i < g.length; ++i)
                for (int j = 0; j < g[i].length; ++j)
                    res += fill(g, i, j) > 0 ? 1 : 0;
            return res;
        }
    }


    //https://leetcode.com/problems/number-of-closed-islands/discuss/425135/Java-Very-Simple-DFS-Solution
    class Solution2 {


        public int closedIsland(int[][] grid) {
            int ans = 0;
            for (int i = 1; i < grid.length - 1; i++) {
                for (int j = 1; j < grid[0].length - 1; j++) {
                    if (grid[i][j] == 0) {
                        if (isClosedIsland(grid, i, j)) ans++;
                    }
                }
            }
            return ans;
        }

        private boolean isClosedIsland(int[][] grid, int i, int j) {
            if (i == 0 || i == grid.length - 1 || j == 0 || j == grid[0].length - 1) return false;
            if (grid[i][j] == 1 || grid[i][j] == 2) return true;
            grid[i][j] = 2;
            int[][] dirs = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
            boolean isClosed = true;
            for (int[] dir : dirs) {
                int m = i + dir[0];
                int n = j + dir[1];
                if (grid[m][n] == 0) {
                    isClosed = isClosedIsland(grid, m, n) && isClosed; // take care, I spend 1.5 hours and ask my friend weidong6686
                    //if isClosed = isClosed && dfs(grid,m,n), your island will be partially filled,
                    //and even worse, the another part may form a 'island' which is wrong.
                }
            }
            return isClosed;
        }
    }
}

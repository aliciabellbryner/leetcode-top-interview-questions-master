package topinterviewquestions;

public class Problem_0695_MaxAreaofIsland_G {
    //DFS
    //https://leetcode.com/problems/max-area-of-island/discuss/108533/JavaC++-Straightforward-dfs-solution/265245
    class Solution {
        private final static int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        public int maxAreaOfIsland(int[][] grid) {
            int rows = grid.length, cols = grid[0].length;

            int maxArea = 0;

            // iterate through every grid in the current array
            for (int r = 0; r < rows; ++r) {
                for (int c = 0; c < cols; ++c) {
                    // in case the current grid is a land and is not visited yet
                    if (grid[r][c] == 1) {
                        maxArea = Math.max(maxArea, dfs(grid, r, c));
                    }
                }
            }

            return maxArea;
        }

        // NOTE: learn the way that how we use the return value of DFS
        private int dfs(int[][] grid, int r, int c) {
            // mark current grid as visited (2)
            grid[r][c] = 2;

            int currArea = 1;

            // try out each possible direction
            for (int[] direction : directions) {
                int nr = r + direction[0], nc = c + direction[1];
                if (isValid(grid, nr, nc)) {
                    currArea += dfs(grid, nr, nc);
                }
            }

            return currArea;
        }


        private boolean isValid(int[][] grid, int r, int c) {
            return r >= 0 && r < grid.length && c >= 0 && c < grid[0].length && grid[r][c] == 1;
        }
    }
}

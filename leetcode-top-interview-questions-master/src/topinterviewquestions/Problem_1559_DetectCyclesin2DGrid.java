package topinterviewquestions;

import java.util.LinkedList;
import java.util.Queue;

/*
Given a 2D array of characters grid of size m x n, you need to find if there exists any cycle consisting of the same value in grid.

A cycle is a path of length 4 or more in the grid that starts and ends at the same cell. From a given cell, you can move to one of the cells adjacent to it - in one of the four directions (up, down, left, or right), if it has the same value of the current cell.

Also, you cannot move to the cell that you visited in your last move. For example, the cycle (1, 1) -> (1, 2) -> (1, 1) is invalid because from (1, 2) we visited (1, 1) which was the last visited cell.

Return true if any cycle of the same value exists in grid, otherwise, return false.



Example 1:



Input: grid = [["a","a","a","a"],["a","b","b","a"],["a","b","b","a"],["a","a","a","a"]]
Output: true
Explanation: There are two valid cycles shown in different colors in the image below:

Example 2:



Input: grid = [["c","c","c","a"],["c","d","c","c"],["c","c","e","c"],["f","c","c","c"]]
Output: true
Explanation: There is only one valid cycle highlighted in the image below:

Example 3:



Input: grid = [["a","b","b"],["b","z","b"],["b","b","a"]]
Output: false


Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 500
grid consists only of lowercase English letters.
 */
public class Problem_1559_DetectCyclesin2DGrid {
    /*
    diss
     */
    //https://leetcode.com/problems/detect-cycles-in-2d-grid/discuss/805677/DFS-or-Simple-Explanation/667492
    class Solution {
        public static final int[][] dirs = {{0,-1},{-1,0},{0,1},{1,0}};
        private char[][] mat;
        private boolean[][] visited;
        private int n;
        private int m;

        public boolean containsCycle(char[][] grid) {
            mat = grid;
            n = grid.length;
            m = grid[0].length;
            visited = new boolean[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (visited[i][j]) {
                        continue;
                    }
                    if (dfs(i, j, -1, -1, mat[i][j])) {//(dfs(i, j, i, j, mat[i][j]))也可以
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean dfs(int x, int y, int lastx, int lasty, char ch) {
            visited[x][y] = true;
            for (int[] dir : dirs) {
                int newX = x + dir[0];
                int newY = y + dir[1];
                if (newX == lastx && newY == lasty) {
                    continue;
                }
                if (newX < 0 || newX >= n || newY < 0 || newY >= m) {
                    continue;
                }
                if (mat[newX][newY] != ch) {
                    continue;
                }
                if (visited[newX][newY]) {
                    return true;
                }
                if (dfs(newX, newY, x, y, ch)) {
                    return true;
                }
            }
            return false;
        }
    }



    //https://leetcode.com/problems/detect-cycles-in-2d-grid/discuss/812038/Java-Simple-BFS
    //has circle iff:
    //met visited points, and point is different than the last point;
    //
    //say, point a, b, c :
    //it has circle iff "a connect b, b connect c, c != a";
    //Note that, in a new BFS you will never meet a point from another finished BFS.
    //
    //BFS is iterative other than recursive, offerring higher readability and maintenability, which is alwats preferable to DFS.
    class Solution2 {
        int m, n;
        boolean[][] mat;
        int[][] dir = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        public boolean containsCycle(char[][] grid) {
            m = grid.length; n = grid[0].length; mat = new boolean[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (!mat[i][j] && bfs(grid, i, j, -1, -1)) return true;
                }
            }
            return false;
        }

        private boolean bfs(char[][] grid, int i, int j, int x, int y) {  // point curr [i, j], point prev [x, y];
            char c = grid[i][j];
            Queue<int[]> q = new LinkedList<>();
            mat[i][j] = true;
            q.offer(new int[]{i, j, x, y});
            while (!q.isEmpty()) {
                for (int k = 0, l = q.size(); k < l; k++) {
                    int curr[] = q.poll();
                    for (int[] d : dir) {
                        int row = curr[0] + d[0], col = curr[1] + d[1];
                        if (row < 0 || row >= m || col < 0 || col >= n || grid[row][col] != c) continue;
                        if (row == curr[2] && col == curr[3]) continue;  // a == c
                        if (mat[row][col]) return true;
                        q.offer(new int[]{row, col, curr[0], curr[1]});
                        mat[row][col] = true;
                    }
                }
            }
            return false;
        }
    }
}

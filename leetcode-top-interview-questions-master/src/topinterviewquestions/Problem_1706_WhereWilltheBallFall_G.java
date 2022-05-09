package topinterviewquestions;

public class Problem_1706_WhereWilltheBallFall_G {



    //https://leetcode.com/problems/where-will-the-ball-fall/discuss/988244/C-++-Easy-Solution-oror-DFS/905309
    //Easy iterative solution. We can just simulate how the actual ball is moving:
    // O(grid.length * grid[0].length) time, because for every column we iterate at most all rows (top to bottom).
// O(1) additional space. O(grid[0].length) space to store the result.
    public int[] findBall2(int[][] grid) {
        if (grid == null || grid.length == 0) return new int[0];
        int[] result = new int[grid[0].length];
        // Each loop computes the result for when be drop a ball in column i.
        for (int i = 0; i < grid[0].length; ++i) {
            int currRow = 0, currCol = i;
            while (currRow < grid.length) {
                // We go to the right if the current value and the value to the right are both equal to 1.
                if (grid[currRow][currCol] == 1 && currCol + 1 < grid[0].length && grid[currRow][currCol + 1] == 1) {
                    currCol++; currRow++;
                    // We go to the left if the current value and the value to the left are both equal to -1.
                } else if (grid[currRow][currCol] == -1 && currCol - 1 >= 0 && grid[currRow][currCol - 1] == -1) {
                    currCol--; currRow++;
                } else {
                    break;  // If we can't move to the left, and we can't move to the right, then the ball is stuck because there is no other way to move.
                }
            }
            result[i] = currRow == grid.length ? currCol : -1;   // Store -1 if the ball got stuck.
        }
        return result;
    }


    //We drop the ball at grid[0][i]
    //and track ball position i1, which initlized as i.
    //
    //An observation is that,
    //if the ball wants to move from i1 to i2,
    //we must have the board direction grid[j][i1] == grid[j][i2]
    //
    //
    //Complexity
    //Time O(mn)
    //Space O(n)
    //https://leetcode.com/problems/where-will-the-ball-fall/discuss/988576/JavaC%2B%2BPython-Solution-with-Explanation
    public int[] findBall(int[][] grid) {
        int m = grid.length, n = grid[0].length, res[] = new int[n];
        for (int i = 0; i < n; ++i) {
            int i1 = i, i2;
            for (int j = 0; j < m; ++j) {
                i2 = i1 + grid[j][i1];
                if (i2 < 0 || i2 >= n || grid[j][i2] != grid[j][i1]) {
                    i1 = -1;
                    break;
                }
                i1 = i2;
            }
            res[i] = i1;
        }
        return res;
    }



}

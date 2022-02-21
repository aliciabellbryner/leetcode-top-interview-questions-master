package topinterviewquestions;

import java.util.HashSet;
import java.util.Set;
/*
You are given an m x n binary matrix grid. An island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.

An island is considered to be the same as another if and only if one island can be translated (and not rotated or reflected) to equal the other.

Return the number of distinct islands.

Example 1:


Input: grid = [[1,1,0,0,0],[1,1,0,0,0],[0,0,0,1,1],[0,0,0,1,1]]
Output: 1
Example 2:


Input: grid = [[1,1,0,1,1],[1,0,0,0,0],[0,0,0,0,1],[1,1,0,1,1]]
Output: 3


Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 50
grid[i][j] is either 0 or 1.
 */
public class Problem_0694_NumberOfDistinctIslands {
    //use direction string to store in hashset
    //Approach 3: Hash By Path Signature
    //Intuition
    //Remember how we didn't need to sort islands in Approach 1? When we start a depth-first search on the top-left square of some island, the path taken by our depth-first search will be the same if, and only if, the shape is the same. We can exploit this by using the path as a hash.
    //Algorithm
    //Each time we discover the first cell in a new island, we initialize an empty string. Then each time dfs is called for that island, we firstly determine whether or not the cell being entered is un-visited land, in the same way as before. If it is, then we append the direction we entered it from to the string. For example, this is the path that our algorithm would follow to explore the following island.
    //The solution to this is that we also need to record where we backtracked. This occurs each time we exit a call to the dfs(...) function. We'll do this by appending a 0 to the string.

    //Let M be the number of rows, and N be the number of columns.
    //Time Complexity : O(M⋅N).
    //Space complexity : O(M⋅N).

    class Solution {
        private int[][] grid;
        private boolean[][] visited;
        private StringBuilder currentIsland;

        public int numDistinctIslands(int[][] grid) {
            this.grid = grid;
            this.visited = new boolean[grid.length][grid[0].length];
            Set<String> islands = new HashSet<>();
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length; col++) {
                    currentIsland = new StringBuilder();
                    dfs(row, col, '0');
                    if (currentIsland.length() == 0) {
                        continue;
                    }
                    islands.add(currentIsland.toString());
                }
            }
            return islands.size();
        }

        private void dfs(int row, int col, char dir) {
            if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length) {
                return;
            }
            if (visited[row][col] || grid[row][col] == 0) {
                return;
            }
            visited[row][col] = true;
            currentIsland.append(dir);
            dfs(row + 1, col, 'D');
            dfs(row - 1, col, 'U');
            dfs(row, col + 1, 'R');
            dfs(row, col - 1, 'L');
            currentIsland.append('0');
            //The solution to this is that we also need to record where we backtracked.
            // This occurs each time we exit a call to the dfs(...) function.
            // We'll do this by appending a 0 to the string.
        }
    }
}

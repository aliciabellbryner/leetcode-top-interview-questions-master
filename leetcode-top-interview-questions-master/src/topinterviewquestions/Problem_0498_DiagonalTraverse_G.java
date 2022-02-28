package topinterviewquestions;
/*
Given an m x n matrix mat, return an array of all the elements of the array in a diagonal order.



Example 1:


Input: mat = [[1,2,3],[4,5,6],[7,8,9]]
Output: [1,2,4,7,5,3,6,8,9]
Example 2:

Input: mat = [[1,2],[3,4]]
Output: [1,2,3,4]


Constraints:

m == mat.length
n == mat[i].length
1 <= m, n <= 104
1 <= m * n <= 104
-105 <= mat[i][j] <= 105
 */
public class Problem_0498_DiagonalTraverse_G {
    //https://leetcode.com/problems/diagonal-traverse/discuss/97711/Java-15-lines-without-using-boolean
    public int[] findDiagonalOrder(int[][] matrix) {
        if (matrix.length == 0) return new int[0];
        int r = 0, c = 0, m = matrix.length, n = matrix[0].length, res[] = new int[m * n];
        for (int i = 0; i < res.length; i++) {
            res[i] = matrix[r][c];
            if ((r + c) % 2 == 0) { // moving up
                if      (c == n - 1) { r++; }
                else if (r == 0)     { c++; }
                else            { r--; c++; }
            } else {                // moving down
                if      (r == m - 1) { c++; }
                else if (c == 0)     { r++; }
                else            { r++; c--; }
            }
        }
        return res;
    }

    //https://leetcode.com/problems/diagonal-traverse/discuss/97712/Concise-Java-Solution
    //If out of bottom border (row >= m) then row = m - 1; col += 2; change walk direction.
    //if out of right border (col >= n) then col = n - 1; row += 2; change walk direction.
    //if out of top border (row < 0) then row = 0; change walk direction.
    //if out of left border (col < 0) then col = 0; change walk direction.
    //Otherwise, just go along with the current direction.
    //Time complexity: O(m * n), m = number of rows, n = number of columns.
    //Space complexity: O(1).
    public class Solution {
        public int[] findDiagonalOrder(int[][] matrix) {
            if (matrix == null || matrix.length == 0) return new int[0];
            int m = matrix.length, n = matrix[0].length;

            int[] result = new int[m * n];
            int row = 0, col = 0, d = 1;

            for (int i = 0; i < m * n; i++) {
                result[i] = matrix[row][col];
                row -= d;
                col += d;

                if (row >= m) { row = m - 1; col += 2; d = -d;}
                if (col >= n) { col = n - 1; row += 2; d = -d;}
                if (row < 0)  { row = 0; d = -d;}
                if (col < 0)  { col = 0; d = -d;}
            }

            return result;
        }
    }
}

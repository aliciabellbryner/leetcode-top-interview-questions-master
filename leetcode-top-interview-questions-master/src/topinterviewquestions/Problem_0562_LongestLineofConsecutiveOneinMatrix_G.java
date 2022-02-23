package topinterviewquestions;
/*
Given an m x n binary matrix mat, return the length of the longest line of consecutive one in the matrix.

The line could be horizontal, vertical, diagonal, or anti-diagonal.



Example 1:


Input: mat = [[0,1,1,0],[0,1,1,0],[0,0,0,1]]
Output: 3
Example 2:


Input: mat = [[1,1,1,1],[0,1,1,0],[0,0,0,1]]
Output: 4


Constraints:

m == mat.length
n == mat[i].length
1 <= m, n <= 104
1 <= m * n <= 104
mat[i][j] is either 0 or 1.
 */
public class Problem_0562_LongestLineofConsecutiveOneinMatrix_G {
    /*
    Solution
Approach 1: Brute Force
Algorithm

The brute force approach is really simple. We directly traverse along every valid line in the given matrix: i.e. Horizontal, Vertical, Diagonal aline above and below the middle diagonal, Anti-diagonal line above and below the middle anti-diagonal. Each time during the traversal, we keep on incrementing the countcount if we encounter continuous 1's. We reset the countcount for any discontinuity encountered. While doing this, we also keep a track of the maximum countcount found so far.


Complexity Analysis

Let mm be the length of the matrix and nn be the width of the matrix. As a result, mnmn would be the total number of cells in the matrix.

Time complexity : O(mn)O(mn). We traverse along the entire matrix 4 times.
Space complexity : O(1)O(1). Constant space is used.
Approach 2: Using 3D Dynamic Programming
Algorithm

Instead of traversing over the same matrix multiple times, we can keep a track of the 1' along all the lines possible while traversing the matrix once only. In order to do so, we make use of a 4mn4mn sized dpdp array. Here, dp[0]dp[0], dp[1]dp[1], dp[2]dp[2] ,dp[3]dp[3] are used to store the maximum number of continuous 1's found so far along the Horizontal, Vertical, Diagonal and Anti-diagonal lines respectively. e.g. dp[i][j][0]dp[i][j][0] is used to store the number of continuous 1's found so far(till we reach the element M[i][j]M[i][j]), along the horizontal lines only.

Thus, we traverse the matrix MM in a row-wise fashion only but, keep updating the entries for every dpdp appropriately.

The following image shows the filled dpdp values for this matrix:

 0 1 1 0

 0 1 1 0

 0 0 1 1

Longest_Line

While filling up the dpdp, we can keep a track of the length of the longest consecutive line of 1's.

Watch this animation for complete process:

Current
1 / 14

Complexity Analysis

Time complexity : O(mn)O(mn). We traverse the entire matrix once only.

Space complexity : O(mn)O(mn). dpdp array of size 4mn4mn is used, where mm and nn are the number of rows ans coloumns of the matrix.

Approach 3: Using 2D Dynamic Programming
Algorithm

In the previous approach, we can observe that the current dpdp entry is dependent only on the entries of the just previous corresponding dpdp row. Thus, instead of maintaining a 2-D dpdp matrix for each kind of line of 1's possible, we can use a 1-d array for each one of them, and update the corresponding entries in the same row during each row's traversal. Taking this into account, the previous 3-D dpdp matrix shrinks to a 2-D dpdp matrix now. The rest of the procedure remains same as the previous approach.


Complexity Analysis

Time complexity : O(mn)O(mn). The entire matrix is traversed once only.

Space complexity : O(n)O(n). dpdp array of size 4n4n is used, where nn is the number of columns of the matrix.
     */

    class Solution2 {
        public int longestLine(int[][] M) {
            if (M.length == 0) return 0;
            int ones = 0;
            int[][][] dp = new int[M.length][M[0].length][4];
            for (int i = 0; i < M.length; i++) {
                for (int j = 0; j < M[0].length; j++) {
                    if (M[i][j] == 1) {
                        dp[i][j][0] = j > 0 ? dp[i][j - 1][0] + 1 : 1;
                        dp[i][j][1] = i > 0 ? dp[i - 1][j][1] + 1 : 1;
                        dp[i][j][2] = (i > 0 && j > 0) ? dp[i - 1][j - 1][2] + 1 : 1;
                        dp[i][j][3] = (i > 0 && j < M[0].length - 1) ? dp[i - 1][j + 1][3] + 1 : 1;
                        ones =
                                Math.max(
                                        ones,
                                        Math.max(Math.max(dp[i][j][0], dp[i][j][1]), Math.max(dp[i][j][2], dp[i][j][3])));
                    }
                }
            }
            return ones;
        }
    }

    class Solution3 {
        public int longestLine(int[][] M) {
            if (M.length == 0) return 0;
            int ones = 0;
            int[][] dp = new int[M[0].length][4];
            for (int i = 0; i < M.length; i++) {
                int old = 0;
                for (int j = 0; j < M[0].length; j++) {
                    if (M[i][j] == 1) {
                        dp[j][0] = j > 0 ? dp[j - 1][0] + 1 : 1;
                        dp[j][1] = i > 0 ? dp[j][1] + 1 : 1;
                        int prev = dp[j][2];
                        dp[j][2] = (i > 0 && j > 0) ? old + 1 : 1;
                        old = prev;
                        dp[j][3] = (i > 0 && j < M[0].length - 1) ? dp[j + 1][3] + 1 : 1;
                        ones =
                                Math.max(ones, Math.max(Math.max(dp[j][0], dp[j][1]), Math.max(dp[j][2], dp[j][3])));
                    } else {
                        old = dp[j][2];
                        dp[j][0] = dp[j][1] = dp[j][2] = dp[j][3] = 0;
                    }
                }
            }
            return ones;
        }
    }


    //diss
    //Java O(nm) Time DP Solution
    //Note that each cell of the DP table only depends on the current row or previous row so you can easily optimize the above algorithm to use only O(m) space.
    public int longestLine(int[][] M) {
        int n = M.length, max = 0;
        if (n == 0) return max;
        int m = M[0].length;
        int[][][] dp = new int[n][m][4];
        for (int i=0;i<n;i++)
            for (int j=0;j<m;j++) {
                if (M[i][j] == 0) continue;
                for (int k=0;k<4;k++) dp[i][j][k] = 1;
                if (j > 0) dp[i][j][0] += dp[i][j-1][0]; // horizontal line
                if (j > 0 && i > 0) dp[i][j][1] += dp[i-1][j-1][1]; // anti-diagonal line
                if (i > 0) dp[i][j][2] += dp[i-1][j][2]; // vertical line
                if (j < m-1 && i > 0) dp[i][j][3] += dp[i-1][j+1][3]; // diagonal line
                max = Math.max(max, Math.max(dp[i][j][0], dp[i][j][1]));
                max = Math.max(max, Math.max(dp[i][j][2], dp[i][j][3]));
            }
        return max;
    }



    //another solution
    class Solution5 {
        public int longestLine(int[][] M) {
            if (M.length==0) return 0;
            int m=M.length, n=M[0].length, res=0;
            int[][][] dp= new int[m+2][n+2][4];
            for (int i=0; i<m; i++)
                for (int j=0; j<n; j++)
                    if (M[i][j]==1){
                        res=Math.max(res, dp[i+1][j+1][0]=dp[i][j+1][0]+1);//update row
                        res=Math.max(res, dp[i+1][j+1][1]=dp[i+1][j][1]+1);//update column
                        res=Math.max(res, dp[i+1][j+1][2]=dp[i]  [j][2]+1);//update diagnoal
                        res=Math.max(res, dp[i+1][j+1][3]=dp[i][j+2][3]+1);//update antidiagonal
                    }
            return res;
        }
    }
}

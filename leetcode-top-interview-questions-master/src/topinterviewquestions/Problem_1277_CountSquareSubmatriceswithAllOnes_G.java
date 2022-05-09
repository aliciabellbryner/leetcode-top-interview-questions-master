package topinterviewquestions;

public class Problem_1277_CountSquareSubmatriceswithAllOnes_G {

    //https://leetcode.com/problems/count-square-submatrices-with-all-ones/discuss/643380/Java-3-Lines-algorithm-solution-%2B-easy-explanation!-O(N*M)-DP
    class Solution {
        public int countSquares(int[][] matrix) {
            int mat[][] = new int[matrix.length + 1][matrix[0].length + 1];
            int sum = 0;

        /*  We can do Dynamic Programming by saving how many
            Squares can be formed using the bottom right corner
            element.
        */

            for(int i = 1; i <= matrix.length; i++)
                for(int j = 1; j <= matrix[0].length; j++)
                    if(matrix[i - 1][j - 1] != 0)
                        sum += (mat[i][j] = Math.min(Math.min(mat[i - 1][j], mat[i][j - 1]), mat[i - 1][j - 1]) + 1);


        /*
        Workin on the first example:
        ===========================
        Matrix =
        [0,1,1,1],
        [1,1,1,1],
        [0,1,1,1]
        ===========================
        mat after algorithm =
        [0,0,0,0,0],
        [0,0,1,1,1],
        [0,1,1,2,2],
        [0,0,1,2,3]
        ===========================
        After summing all indicies, now we get the correct answer!
        */

            return sum;
        }
    }


    //https://leetcode.com/problems/count-square-submatrices-with-all-ones/discuss/441306/JavaC%2B%2BPython-DP-solution
    //Explanation
    //dp[i][j] means the size of biggest square with A[i][j] as bottom-right corner.
    //dp[i][j] also means the number of squares with A[i][j] as bottom-right corner.
    //If A[i][j] == 0, no possible square.
    //If A[i][j] == 1,
    //we compare the size of square dp[i-1][j-1], dp[i-1][j] and dp[i][j-1].
    //min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) + 1 is the maximum size of square that we can find.
    //
    //
    //Complexity
    //Time O(MN)
    //Space O(1)
    public int countSquares(int[][] A) {
        int res = 0;
        for (int i = 0; i < A.length; ++i) {
            for (int j = 0; j < A[0].length; ++j) {
                if (A[i][j] > 0 && i > 0 && j > 0) {
                    A[i][j] = Math.min(A[i - 1][j - 1], Math.min(A[i - 1][j], A[i][j - 1])) + 1;
                }
                res += A[i][j];
            }
        }
        return res;
    }
}

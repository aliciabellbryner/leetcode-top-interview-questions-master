package topinterviewquestions;

public class Problem_1252_CellswithOddValuesinaMatrix_G {
    //Count the rows and columns that appear odd times;
    //Traverse all cells to get the answer.
    //https://leetcode.com/problems/cells-with-odd-values-in-a-matrix/discuss/425100/JavaPython-3-2-methods%3A-time-O(m-*-n-%2B-L)-and-O(L)-codes-w-comment-and-analysis.
    public int oddCells(int n, int m, int[][] indices) {
        boolean[] oddRows = new boolean[n], oddCols = new boolean[m];
        for (int[] idx : indices) {
            oddRows[idx[0]] ^= true; // if row idx[0] appears odd times, it will correspond to true.
            oddCols[idx[1]] ^= true; // if column idx[1] appears odd times, it will correspond to true.
        }
        int cnt = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                cnt += oddRows[i] ^ oddCols[j] ? 1 : 0; // only cell (i, j) with odd times count of row + column would get odd values.
            }
        }
        return cnt;
    }
}

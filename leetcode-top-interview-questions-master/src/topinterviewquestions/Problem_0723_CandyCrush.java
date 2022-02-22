package topinterviewquestions;
/*
This question is about implementing a basic elimination algorithm for Candy Crush.

Given an m x n integer array board representing the grid of candy where board[i][j] represents the type of candy. A value of board[i][j] == 0 represents that the cell is empty.

The given board represents the state of the game following the player's move. Now, you need to restore the board to a stable state by crushing candies according to the following rules:

If three or more candies of the same type are adjacent vertically or horizontally, crush them all at the same time - these positions become empty.
After crushing all candies simultaneously, if an empty space on the board has candies on top of itself, then these candies will drop until they hit a candy or bottom at the same time. No new candies will drop outside the top boundary.
After the above steps, there may exist more candies that can be crushed. If so, you need to repeat the above steps.
If there does not exist more candies that can be crushed (i.e., the board is stable), then return the current board.
You need to perform the above rules until the board becomes stable, then return the stable board.



Example 1:


Input: board = [[110,5,112,113,114],[210,211,5,213,214],[310,311,3,313,314],[410,411,412,5,414],[5,1,512,3,3],[610,4,1,613,614],[710,1,2,713,714],[810,1,2,1,1],[1,1,2,2,2],[4,1,4,4,1014]]
Output: [[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[110,0,0,0,114],[210,0,0,0,214],[310,0,0,113,314],[410,0,0,213,414],[610,211,112,313,614],[710,311,412,613,714],[810,411,512,713,1014]]
Example 2:

Input: board = [[1,3,5,5,2],[3,4,3,3,1],[3,2,4,5,2],[2,4,4,5,5],[1,4,4,1,1]]
Output: [[1,3,0,0,0],[3,4,0,5,2],[3,2,0,3,1],[2,4,0,5,2],[1,4,3,1,1]]


Constraints:

m == board.length
n == board[i].length
3 <= m, n <= 50
1 <= board[i][j] <= 2000
 */
public class Problem_0723_CandyCrush {
    /*
    Approach #1: Ad-Hoc
Intuition

We need to simply perform the algorithm as described. It consists of two major steps: a crush step, and a gravity step. We work through each step individually.

Algorithm

Crushing Step

When crushing, one difficulty is that we might accidentally crush candy that is part of another row. For example, if the board is:

123
145
111
and we crush the vertical row of 1s early, we may not see there was also a horizontal row.

To remedy this, we should flag candy that should be crushed first. We could use an auxillary toCrush boolean array, or we could mark it directly on the board by making the entry negative (ie. board[i][j] = -Math.abs(board[i][j]))

As for how to scan the board, we have two approaches. Let's call a line any row or column of the board.

For each line, we could use a sliding window (or itertools.groupby in Python) to find contiguous segments of the same character. If any of these segments have length 3 or more, we should flag them.

Alternatively, for each line, we could look at each width-3 slice of the line: if they are all the same, then we should flag those 3.

After, we can crush the candy by setting all flagged board cells to zero.

Gravity Step

For each column, we want all the candy to go to the bottom. One way is to iterate through and keep a stack of the (uncrushed) candy, popping and setting as we iterate through the column in reverse order.

Alternatively, we could use a sliding window approach, maintaining a read and write head. As the read head iterates through the column in reverse order, when the read head sees candy, the write head will write it down and move one place. Then, the write head will write zeroes to the remainder of the column.

We showcase the simplest approaches to these steps in the solutions below.

 **Complexity Analysis**
Time Complexity: O((R*C)^2)O((R∗C)
2
 ), where R, CR,C is the number of rows and columns in board. We need O(R*C)O(R∗C) to scan the board, and we might crush only 3 candies repeatedly.

Space Complexity: O(1)O(1) additional complexity, as we edit the board in place.


     */
    class Solution {
        public int[][] candyCrush(int[][] board) {
            int R = board.length, C = board[0].length;
            boolean todo = false;
            for (int r = 0; r < R; ++r) {
                for (int c = 0; c + 2 < C; ++c) {
                    int v = Math.abs(board[r][c]);
                    if (v != 0 && v == Math.abs(board[r][c+1]) && v == Math.abs(board[r][c+2])) {
                        board[r][c] = board[r][c+1] = board[r][c+2] = -v;
                        todo = true;
                    }
                }
            }
            for (int r = 0; r + 2 < R; ++r) {
                for (int c = 0; c < C; ++c) {
                    int v = Math.abs(board[r][c]);
                    if (v != 0 && v == Math.abs(board[r+1][c]) && v == Math.abs(board[r+2][c])) {
                        board[r][c] = board[r+1][c] = board[r+2][c] = -v;
                        todo = true;
                    }
                }
            }

            for (int c = 0; c < C; ++c) {
                int wr = R - 1;
                for (int r = R-1; r >= 0; --r)
                    if (board[r][c] > 0)
                        board[wr--][c] = board[r][c];
                while (wr >= 0)
                    board[wr--][c] = 0;
            }

            return todo ? candyCrush(board) : board;
        }
    }


    //discussion
    //I found this solution from http://storypku.com/2017/11/leetcode-question-723-candy-crush/, and I convert it to Java. It's not the shortest, but the logic is clear to me.
    //
    //The idea is that we try to crush the candy horizontally, then vertically, and drop them vertically (because we have to fill the empty spots). The way we mark whether a candy needs to be crushed(set to 0) is to set an opposite value to it, so that we don't have to maintain another data structure.
    class Solution1 {
        public int[][] candyCrush(int[][] board) {
            int m = board.length;
            int n = board[0].length;

            boolean shouldContinue = false;

            // Crush horizontally
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n - 2; j++) {
                    int v = Math.abs(board[i][j]);
                    if (v > 0 && v == Math.abs(board[i][j + 1]) && v == Math.abs(board[i][j + 2])) {
                        board[i][j] = board[i][j + 1] = board[i][j + 2] = -v;
                        shouldContinue = true;
                    }
                }
            }

            // Crush vertically
            for (int i = 0; i < m - 2; i++) {
                for (int j = 0; j < n; j++) {
                    int v = Math.abs(board[i][j]);
                    if (v > 0 && v == Math.abs(board[i + 1][j]) && v == Math.abs(board[i + 2][j])) {
                        board[i][j] = board[i + 1][j] = board[i + 2][j] = -v;
                        shouldContinue = true;
                    }
                }
            }

            // Drop vertically
            for (int j = 0; j < n; j++) {
                int r = m - 1;
                for (int i = m - 1; i >= 0; i--) {
                    if (board[i][j] >= 0) {
                        board[r--][j] = board[i][j];
                    }
                }
                for (int i = r; i >= 0; i--) {
                    board[i][j] = 0;
                }
            }

            return shouldContinue ? candyCrush(board) : board;
        }
    }


    //The idea is to traverse the entire matrix again and again to remove crush until no crush can be found.
    //
    //For each traversal of the matrix, we only check two directions, rightward and downward. There is no need to check upward and leftward because they would have been checked from previous cells.
    //
    //For each cell, if there are at least three candies of the same type rightward or downward, set them all to its negative value to mark them.
    //
    //After each traversal, we need to remove all those negative values by setting them to 0, then let the rest drop down to their correct position. A easy way is to iterate through each column, for each column, move positive values to the bottom then set the rest to 0.
    class Solution2 {
        public int[][] candyCrush(int[][] board) {
            int N = board.length, M = board[0].length;
            boolean found = true;
            while (found) {
                found = false;
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < M; j++) {
                        int val = Math.abs(board[i][j]);
                        if (val == 0) continue;
                        if (j < M - 2 && Math.abs(board[i][j + 1]) == val && Math.abs(board[i][j + 2]) == val) {
                            found = true;
                            int ind = j;
                            while (ind < M && Math.abs(board[i][ind]) == val) board[i][ind++] = -val;
                        }
                        if (i < N - 2 && Math.abs(board[i + 1][j]) == val && Math.abs(board[i + 2][j]) == val) {
                            found = true;
                            int ind = i;
                            while (ind < N && Math.abs(board[ind][j]) == val) board[ind++][j] = -val;
                        }
                    }
                }
                if (found) { // move positive values to the bottom, then set the rest to 0
                    for (int j = 0; j < M; j++) {
                        int storeInd = N - 1;
                        for (int i = N - 1; i >= 0; i--) {
                            if (board[i][j] > 0) {
                                board[storeInd--][j] = board[i][j];
                            }
                        }
                        for (int k = storeInd; k >= 0; k--) board[k][j] = 0;
                    }
                }
            }
            return board;
        }
    }

}

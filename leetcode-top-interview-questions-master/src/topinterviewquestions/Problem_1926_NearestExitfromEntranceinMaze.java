package topinterviewquestions;

import java.util.LinkedList;
import java.util.Queue;

/*
You are given an m x n matrix maze (0-indexed) with empty cells (represented as '.') and walls (represented as '+'). You are also given the entrance of the maze, where entrance = [entrancerow, entrancecol] denotes the row and column of the cell you are initially standing at.

In one step, you can move one cell up, down, left, or right. You cannot step into a cell with a wall, and you cannot step outside the maze. Your goal is to find the nearest exit from the entrance. An exit is defined as an empty cell that is at the border of the maze. The entrance does not count as an exit.

Return the number of steps in the shortest path from the entrance to the nearest exit, or -1 if no such path exists.



Example 1:


Input: maze = [["+","+",".","+"],[".",".",".","+"],["+","+","+","."]], entrance = [1,2]
Output: 1
Explanation: There are 3 exits in this maze at [1,0], [0,2], and [2,3].
Initially, you are at the entrance cell [1,2].
- You can reach [1,0] by moving 2 steps left.
- You can reach [0,2] by moving 1 step up.
It is impossible to reach [2,3] from the entrance.
Thus, the nearest exit is [0,2], which is 1 step away.
Example 2:


Input: maze = [["+","+","+"],[".",".","."],["+","+","+"]], entrance = [1,0]
Output: 2
Explanation: There is 1 exit in this maze at [1,2].
[1,0] does not count as an exit since it is the entrance cell.
Initially, you are at the entrance cell [1,0].
- You can reach [1,2] by moving 2 steps right.
Thus, the nearest exit is [1,2], which is 2 steps away.
Example 3:


Input: maze = [[".","+"]], entrance = [0,0]
Output: -1
Explanation: There are no exits in this maze.


Constraints:

maze.length == m
maze[i].length == n
1 <= m, n <= 100
maze[i][j] is either '.' or '+'.
entrance.length == 2
0 <= entrancerow < m
0 <= entrancecol < n
entrance will always be an empty cell.
 */
public class Problem_1926_NearestExitfromEntranceinMaze {
    //bfs
    //https://leetcode.com/problems/nearest-exit-from-entrance-in-maze/discuss/1329078/C++-BFS-solution-oror-commented/1008816
    public static int nearestExit(char[][] maze, int[] entrance) {
        int m = maze.length, n = maze[0].length, min = 0;
        int[] x = new int[]{-1,1,0,0};
        int[] y = new int[]{0,0,-1,1};
        Queue<int[]> q = new LinkedList<int[]>();
        q.add(entrance);
        maze[entrance[0]][entrance[1]] = '+';

        while(!q.isEmpty()) {
            int s = q.size();

            while(s-- > 0) {
                int[] cur = q.poll();

                for(int i=0; i<4; i++) {
                    int c1 = cur[0] + x[i];
                    int c2 = cur[1] + y[i];

                    if(c1 < 0 || c2 < 0 || c1 >= m || c2 >= n || maze[c1][c2] == '+') {
                        continue;
                    }

                    if(c1 == 0 || c2 == 0 || c1+1 == m || c2+1 == n) {//说明已经走到了边缘不是wall的位置
                        return min+1;
                    }

                    q.add(new int[]{c1,c2});
                    maze[c1][c2] = '+';
                }
            }
            min++;
        }
        return -1;
    }
}

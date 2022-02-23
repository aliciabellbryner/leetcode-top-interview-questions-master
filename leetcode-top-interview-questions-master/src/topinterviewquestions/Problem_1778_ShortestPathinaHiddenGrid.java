package topinterviewquestions;
/*
This is an interactive problem.

There is a robot in a hidden grid, and you are trying to get it from its starting cell to the target cell in this grid. The grid is of size m x n, and each cell in the grid is either empty or blocked. It is guaranteed that the starting cell and the target cell are different, and neither of them is blocked.

You want to find the minimum distance to the target cell. However, you do not know the grid's dimensions, the starting cell, nor the target cell. You are only allowed to ask queries to the GridMaster object.

Thr GridMaster class has the following functions:

boolean canMove(char direction) Returns true if the robot can move in that direction. Otherwise, it returns false.
void move(char direction) Moves the robot in that direction. If this move would move the robot to a blocked cell or off the grid, the move will be ignored, and the robot will remain in the same position.
boolean isTarget() Returns true if the robot is currently on the target cell. Otherwise, it returns false.
Note that direction in the above functions should be a character from {'U','D','L','R'}, representing the directions up, down, left, and right, respectively.

Return the minimum distance between the robot's initial starting cell and the target cell. If there is no valid path between the cells, return -1.

Custom testing:

The test input is read as a 2D matrix grid of size m x n where:

grid[i][j] == -1 indicates that the robot is in cell (i, j) (the starting cell).
grid[i][j] == 0 indicates that the cell (i, j) is blocked.
grid[i][j] == 1 indicates that the cell (i, j) is empty.
grid[i][j] == 2 indicates that the cell (i, j) is the target cell.
There is exactly one -1 and 2 in grid. Remember that you will not have this information in your code.



Example 1:

Input: grid = [[1,2],[-1,0]]
Output: 2
Explanation: One possible interaction is described below:
The robot is initially standing on cell (1, 0), denoted by the -1.
- master.canMove('U') returns true.
- master.canMove('D') returns false.
- master.canMove('L') returns false.
- master.canMove('R') returns false.
- master.move('U') moves the robot to the cell (0, 0).
- master.isTarget() returns false.
- master.canMove('U') returns false.
- master.canMove('D') returns true.
- master.canMove('L') returns false.
- master.canMove('R') returns true.
- master.move('R') moves the robot to the cell (0, 1).
- master.isTarget() returns true.
We now know that the target is the cell (0, 1), and the shortest path to the target cell is 2.
Example 2:

Input: grid = [[0,0,-1],[1,1,1],[2,0,0]]
Output: 4
Explanation: The minimum distance between the robot and the target cell is 4.
Example 3:

Input: grid = [[-1,0],[0,2]]
Output: -1
Explanation: There is no path from the robot to the target cell.


Constraints:

1 <= n, m <= 500
m == grid.length
n == grid[i].length
grid[i][j] is either -1, 0, 1, or 2.
There is exactly one -1 in grid.
There is exactly one 2 in grid.
 */

import java.util.Deque;
import java.util.LinkedList;

/**
 * // This is the GridMaster's API interface.
 * // You should not implement it, or speculate about its implementation
 * class GridMaster {
 *     boolean canMove(char direction);
 *     void move(char direction);
 *     boolean isTarget();
 * }
 */
public class Problem_1778_ShortestPathinaHiddenGrid {
    //diss
    /*
    class Solution {
        private static final int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        private static final char[] FORWARD = {'U', 'D', 'L', 'R'};
        private static final char[] BACKWARD = {'D', 'U', 'R', 'L'};

        private static final int UNVISITED = 0;
        private static final int PATH = 1;
        private static final int TARGET = 2;
        private static final int BLOCK = -1;

        public int findShortestPath(GridMaster master) {
            int N = 500;
            int[][] grid = new int[2 * N][2 * N];

            // [N, N] is start, mark it as PATH in dfs
            dfs(N, N, master, grid);

            Deque<int[]> queue = new LinkedList<>();
            queue.offer(new int[]{N, N});
            grid[N][N] = BLOCK; // here use BLOCK to mark visited in BFS
            int dis = 0;

            while (!queue.isEmpty()) {
                int size = queue.size();
                while (size-- > 0) {
                    int[] cur = queue.poll();
                    for (int i = 0; i < 4; i++) {
                        int p = cur[0] + DIRS[i][0];
                        int q = cur[1] + DIRS[i][1];
                        if (grid[p][q] == TARGET) return dis + 1;
                        if (grid[p][q] == BLOCK) continue;
                        queue.offer(new int[]{p, q});
                        grid[p][q] = BLOCK;
                    }
                }
                dis++;
            }

            return -1;
        }

        private void dfs(int r, int c, GridMaster master, int[][] grid) {
            if (grid[r][c] != UNVISITED) return;
            if (master.isTarget())
                grid[r][c] = TARGET;
            else
                grid[r][c] = PATH;

            for (int i = 0; i < 4; i++) {
                char forward = FORWARD[i];
                char backward = BACKWARD[i];
                int p = r + DIRS[i][0];
                int q = c + DIRS[i][1];

                if (!master.canMove(forward)) {
                    grid[p][q] = BLOCK;
                } else {
                    master.move(forward);
                    dfs(p, q, master, grid);
                    master.move(backward);
                }
            }
        }
    }

     */

    /*Java O(M*N) DFS + BFS

Generate grid using dfs, then find the shortest path in grid using bfs
in grid I use below labels:
-1: start, 3:block, 2:target, 1: empty, 0 :unvisited


    static int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};

    private int sln1(gridMaster m){
        int len = 1005;
        int[][] grid = new int[len][len];
        int startX = len/2;
        int startY = len/2;
        Map<Integer, Character> map = new HashMap<>();
        map.put(0, 'R');map.put(1, 'D');map.put(2, 'L');map.put(3, 'U');
        dfs(startX, startY, m, grid, map);

        Deque<int[]> que = new ArrayDeque<>();
        que.addLast(new int[]{startX, startY});
        grid[startX][startY] = 3;
        int steps= 1;
        while(que.size()>0){
            for(int l = que.size(); l>0;l--){
                int[] curr = que.removeFirst();
                for(int[] d: dirs){
                    int nx = curr[0]+d[0];
                    int ny = curr[1]+d[1];
                    if(grid[nx][ny]==2) return steps;
                    if(grid[nx][ny]==3) continue;
                    grid[nx][ny]=3;
                    que.addLast(new int[]{nx, ny});
                }
            }
            steps++;
        }
        return -1;
    }

    // -1: start, 3:block, 2:target, 1 empty, 0 unvisited
    private void dfs(int x, int y, gridMaster m, int[][] grid, Map<Integer, Character> map){
        if(grid[x][y]!=0) return;
        if(m.isTarget()) grid[x][y] = 2;
        else  grid[x][y] = 1;
        for(int i = 0;i< dirs.length;i++){
            char d = map.get(i);
            char ud = map.get((i+2)%4);
            int nx = x+dirs[i][0];
            int ny = y+dirs[i][1];
            if(m.canMove(d)){
                m.move(d);
                dfs(nx,ny, m, grid, map);
                m.move(ud);
            }else{
                grid[nx][ny] = 3;   //no need to check border
            }
        }
    }


     */
}

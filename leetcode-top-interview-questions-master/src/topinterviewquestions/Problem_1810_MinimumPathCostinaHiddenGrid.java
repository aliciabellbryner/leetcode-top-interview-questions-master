package topinterviewquestions;
/*
This is an interactive problem.

There is a robot in a hidden grid, and you are trying to get it from its starting cell to the target cell in this grid. The grid is of size m x n, and each cell in the grid is either empty or blocked. It is guaranteed that the starting cell and the target cell are different, and neither of them is blocked.

Each cell has a cost that you need to pay each time you move to the cell. The starting cell's cost is not applied before the robot moves.

You want to find the minimum total cost to move the robot to the target cell. However, you do not know the grid's dimensions, the starting cell, nor the target cell. You are only allowed to ask queries to the GridMaster object.

The GridMaster class has the following functions:

boolean canMove(char direction) Returns true if the robot can move in that direction. Otherwise, it returns false.
int move(char direction) Moves the robot in that direction and returns the cost of moving to that cell. If this move would move the robot to a blocked cell or off the grid, the move will be ignored, the robot will remain in the same position, and the function will return -1.
boolean isTarget() Returns true if the robot is currently on the target cell. Otherwise, it returns false.
Note that direction in the above functions should be a character from {'U','D','L','R'}, representing the directions up, down, left, and right, respectively.

Return the minimum total cost to get the robot from its initial starting cell to the target cell. If there is no valid path between the cells, return -1.

Custom testing:

The test input is read as a 2D matrix grid of size m x n and four integers r1, c1, r2, and c2 where:

grid[i][j] == 0 indicates that the cell (i, j) is blocked.
grid[i][j] >= 1 indicates that the cell (i, j) is empty and grid[i][j] is the cost to move to that cell.
(r1, c1) is the starting cell of the robot.
(r2, c2) is the target cell of the robot.
Remember that you will not have this information in your code.



Example 1:

Input: grid = [[2,3],[1,1]], r1 = 0, c1 = 1, r2 = 1, c2 = 0
Output: 2
Explanation: One possible interaction is described below:
The robot is initially standing on cell (0, 1), denoted by the 3.
- master.canMove('U') returns false.
- master.canMove('D') returns true.
- master.canMove('L') returns true.
- master.canMove('R') returns false.
- master.move('L') moves the robot to the cell (0, 0) and returns 2.
- master.isTarget() returns false.
- master.canMove('U') returns false.
- master.canMove('D') returns true.
- master.canMove('L') returns false.
- master.canMove('R') returns true.
- master.move('D') moves the robot to the cell (1, 0) and returns 1.
- master.isTarget() returns true.
- master.move('L') doesn't move the robot and returns -1.
- master.move('R') moves the robot to the cell (1, 1) and returns 1.
We now know that the target is the cell (1, 0), and the minimum total cost to reach it is 2.
Example 2:

Input: grid = [[0,3,1],[3,4,2],[1,2,0]], r1 = 2, c1 = 0, r2 = 0, c2 = 2
Output: 9
Explanation: The minimum cost path is (2,0) -> (2,1) -> (1,1) -> (1,2) -> (0,2).
Example 3:

Input: grid = [[1,0],[0,1]], r1 = 0, c1 = 0, r2 = 1, c2 = 1
Output: -1
Explanation: There is no path from the robot to the target cell.


Constraints:

1 <= n, m <= 100
m == grid.length
n == grid[i].length
0 <= grid[i][j] <= 100
 */

import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * // This is the GridMaster's API interface.
 * // You should not implement it, or speculate about its implementation
 * class GridMaster {
 *     boolean canMove(char direction);
 *     int move(char direction);
 *     boolean isTarget();
 * }
 */
public class Problem_1810_MinimumPathCostinaHiddenGrid {
//[JAVA] backtrack + Dijkstra
    /*
class Solution {
    final static char[] DIR = {'U', 'R', 'D', 'L'};
    final static int[][] STEPS = {{-1,0}, {0, 1}, {1,0}, {0,-1}};
    Map<Pair<Integer, Integer>, Integer> map;
    Pair<Integer, Integer> target = null;

    public int findShortestPath(GridMaster master) {
        map = new HashMap<>();

        dfs(master, 0, 0);

        if (target == null) return -1;

        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> a[2]-b[2]);
        pq.offer(new int[]{0,0,0});
        while(!pq.isEmpty()) {
            int[] cur = pq.poll();
            if (cur[0] == target.getKey() && cur[1] == target.getValue())
                return cur[2];
            for(int[] s: STEPS) {
                int x1 = cur[0] + s[0], y1 = cur[1] + s[1];
                if (!map.containsKey(new Pair(x1, y1))) continue;
                int val = map.remove(new Pair(x1, y1));
                pq.offer(new int[]{x1, y1, val + cur[2]});
            }
        }
        return -1;
    }

    private void dfs(GridMaster master, int x, int y) {
        if (master.isTarget()) {
            target = new Pair(x, y);
        }
        for(int i = 0; i < 4; i++) {
            int x1 = x + STEPS[i][0], y1 = y + STEPS[i][1];
            if (!map.containsKey(new Pair(x1, y1)) && master.canMove(DIR[i])) {
                int val = master.move(DIR[i]);
                map.put(new Pair(x1, y1), val);
                dfs(master, x1, y1);
                master.move(DIR[(i+2) % 4]);
            }
        }
    }
}

     */


    /*
//JAVA O(m*n) DFS + O((m*n)log(m*n)) Dijkstra

        private int sln1(GridMaster m){

        Integer[][] grid = new Integer[201][201];
        int[] t = new int[2];
        dfs(m, 100, 100, grid, t);
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[2]-b[2]);
        pq.offer(new int[]{100,100, 0});
        while(pq.size()>0){
            int[] curr = pq.poll();
            if(curr[0] == t[0] && curr[1] == t[1]) return curr[2];
            if(grid[curr[0]][curr[1]] == null) continue;
            grid[curr[0]][curr[1]] = null;
            for(int[] d: dirs){
                int nx = curr[0]+d[0];
                int ny =curr[1]+d[1];
                if(nx<0 || ny<0 || nx==grid.length || ny==grid.length) continue;
                if(grid[nx][ny] == null) continue;
                pq.offer(new int[]{nx, ny, grid[nx][ny]+ curr[2]});
            }
        }
        return -1;
    }

    static int[][] dirs = {{-1,0,'U'},{0,1,'R'},{1,0,'D'},{0,-1,'L'}};
    //O(m*n)
    private void dfs(GridMaster m, int x, int y, Integer[][] grid, int[] t){
        if(m.isTarget()){
            t[0] = x;
            t[1] = y;
        }
        for(int i= 0;i< dirs.length;i++){
            int[] d = dirs[i];
            int nx = x+d[0];
            int ny = y+d[1];
            if(nx<0 || ny<0 || nx==grid.length || ny==grid.length) continue;
            if(grid[nx][ny] == null && m.canMove((char)d[2])){
                grid[nx][ny] = m.move((char)d[2]);
                dfs(m, nx, ny, grid, t);
                m.move((char)dirs[(i+2)%dirs.length][2]);
            }
        }
    }
     */
}

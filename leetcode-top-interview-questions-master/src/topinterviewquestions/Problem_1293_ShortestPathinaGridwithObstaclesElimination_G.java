package topinterviewquestions;

import java.util.*;

public class Problem_1293_ShortestPathinaGridwithObstaclesElimination_G {

    // BFS https://leetcode.com/problems/shortest-path-in-a-grid-with-obstacles-elimination/discuss/451787/Python-O(m*n*k)-BFS-Solution-with-Explanation/407012
    //Time Complexity
    //O(m*n*k) time complexity
    //This is because for every cell (m*n), in the worst case we have to put that cell into the queue/bfs k times.
    //Space Complexity O(m*n*k) space complexity
    //This is because for every cell (m*n),in the worst case we have to put that cell into the queue/bfs k times which means we need to worst case store all of those steps/paths in the visited set.
    public int shortestPath(int[][] grid, int k) {
        int step = 0;
        int m = grid.length;
        int n = grid[0].length;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int[][] seen = new int[m][n]; // minimum obstacles elimination from (0,0) to (x, y)
        for (int i = 0; i < m; i++) {
            Arrays.fill(seen[i], Integer.MAX_VALUE);
        }
        Queue<int[]> q = new LinkedList<>(); // [row, col, number of eliminations left]
        q.offer(new int[]{0, 0, 0});
        seen[0][0] = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int[] cur = q.poll();
                if (cur[0] == m - 1 && cur[1] == n - 1) {// reach the down right element
                    return step;
                }
                for (int[] dir : dirs) {
                    int x = dir[0] + cur[0];
                    int y = dir[1] + cur[1];
                    if (x < 0 || x >= m || y < 0 || y >= n) {
                        continue;
                    }
                    int o = grid[x][y] + cur[2];//表示从左上角到x, y需要消除的砖块数目
                    if (o >= seen[x][y] || o > k) {//o >= seen[x][y]表示之前已经有路径可以用更少的消除达到x,y点，所以不用更新seen
                        //o > k说明已经用完了最多的消除次数
                        continue;
                    }
                    seen[x][y] = o;
                    q.offer(new int[]{x, y, o});
                }
            }
            ++step;
        }
        return -1;// There is no path from grid[0][0] to grid[grid.length - 1][grid[0].length - 1] with <= k eliminations.
    }
}

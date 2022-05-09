package topinterviewquestions;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Problem_0778_SwiminRisingWater_G {
    //https://leetcode.com/problems/swim-in-rising-water/discuss/113770/C++Python-PriorityQueue/274918
    class Solution1 {
        public void spread(int[][] grid, Queue<int[]> queue, int i, int j) {
            if (i < 0 || i >= grid.length || j < 0 || j >= grid.length) {
                return; // out of the border
            }
            if (grid[i][j] == -1) {
                return; // already visited
            }
            queue.add(new int[] {i,j, grid[i][j]});
            grid[i][j] = -1; // mark visited as -1
        }

        public int swimInWater(int[][] grid) {
            int N = grid.length, start[] = {0,0, grid[0][0]}, end[] = {N-1, N-1, grid[N-1][N-1]};
            Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));
            queue.add(end);
            grid[end[0]][end[1]] = -1;

            int res = 0;
            while (!queue.isEmpty()) {
                int node[] = queue.poll(), i = node[0], j = node[1], value = node[2];
                if (res < value) {
                    res = value; // shorter path
                }
                if (i == start[0] && j == start[1]) {
                    return res; // reached
                }
                spread(grid,queue, i-1, j); // top
                spread(grid,queue, i, j-1); // left
                spread(grid,queue, i, j+1); // right
                spread(grid,queue, i+1, j); // bottom
            }
            return res;
        }
    }

    //time complexity: O(n^2*logn)
    //
    //pq contains at most n^2 elements, pop time complexity each time is is O(logn^2) = O(2*logn)
    //At most we will pop n^2 times
    //O(n^2*2*logn) = O(n^2*logn)
    //https://leetcode.com/problems/swim-in-rising-water/discuss/113770/C++Python-PriorityQueue/119339
    class Solution2 {
        public int swimInWater(int[][] grid) {
            int n = grid.length;
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
            boolean[][] visited = new boolean[n][n];
            int[][] dirs = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};

            visited[0][0] = true;
            pq.offer(new int[]{0, 0, grid[0][0]});
            while(!pq.isEmpty()){
                int[] info = pq.poll();
                int i = info[0], j = info[1], max = info[2];
                for(int[] dir : dirs){
                    int newI = dir[0] + i, newJ = dir[1] + j;
                    if(newI < 0 || newI >= n || newJ < 0 || newJ >= n)  continue;
                    if(!visited[newI][newJ]){
                        visited[newI][newJ] = true;
                        int newmax = Math.max(max, grid[newI][newJ]);
                        if(newI == n - 1 && newJ == n - 1)  return newmax;
                        pq.offer(new int[]{newI, newJ, newmax});
                    }
                }
            }

            return 0;
        }
    }
}

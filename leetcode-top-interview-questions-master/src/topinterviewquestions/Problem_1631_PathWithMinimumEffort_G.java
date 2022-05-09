package topinterviewquestions;

import java.util.*;

public class Problem_1631_PathWithMinimumEffort_G {

    //Approach 1: Dijkstra's algorithm
    //https://leetcode.com/problems/path-with-minimum-effort/discuss/1036518/Java-3-clean-codes%3A-Dijkstra's-algo-Union-Find-Binary-search
    // 36 ms. 97.51%
    class Solution {
        private static final int[][] dir = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
        public int minimumEffortPath(int[][] heights) {
            int m = heights.length, n = heights[0].length;
            int[][] dist = new int[m][n];
            for(int i = 0; i < m; i++) Arrays.fill(dist[i], Integer.MAX_VALUE);
            dist[0][0] = 0;
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
            pq.add(new int[] {0, 0, 0});
            while(!pq.isEmpty()) {
                int[] p = pq.poll();
                int i = p[0], j = p[1];
                if(i == m - 1 && j == n - 1) break;
                for(int[] d: dir) {
                    int x = i + d[0], y = j + d[1];
                    if(x < 0 || x >= m || y < 0 || y >= n) continue;
                    int alt = Math.max(p[2], Math.abs(heights[i][j] - heights[x][y]));
                    if(alt < dist[x][y]) {
                        pq.add(new int[] {x, y, dist[x][y] = alt});
                    }
                }
            }
            return dist[m - 1][n - 1];
        }
    }


    //Method1: Binary Search and BFS
    //https://leetcode.com/problems/path-with-minimum-effort/discuss/909002/JavaPython-3-3-codes%3A-Binary-Search-Bellman-Ford-and-Dijkstra-w-brief-explanation-and-analysis.
    //Time: O(m * n * log(Max)), space: O(m * n), where m, n and Max are the dimensions and the max absolute difference among the matrix.
    class Solution2 {
        private int[] d = {0, 1, 0, -1, 0};
        public int minimumEffortPath(int[][] heights) {
            int lo = 0, hi = 1_000_000;
            while (lo < hi) {
                int effort = lo + (hi - lo) / 2;
                if (isPath(heights, effort)) {
                    hi = effort;
                }else {
                    lo = effort + 1;
                }
            }
            return lo;
        }
        private boolean isPath(int[][] h, int effort) {
            int m = h.length, n = h[0].length;
            Queue<int[]> q = new LinkedList<>();
            q.offer(new int[2]);
            Set<Integer> seen = new HashSet<>();
            seen.add(0);
            while (!q.isEmpty()) {
                int[] cur = q.poll();
                int x = cur[0], y = cur[1];
                if (x == m - 1 && y == n - 1) {
                    return true;
                }
                for (int k = 0; k < 4; ++k) {
                    int r = x + d[k], c = y + d[k + 1];
                    if (0 <= r && r < m && 0 <= c && c < n && effort >= Math.abs(h[r][c] - h[x][y]) && seen.add(r * n + c)) {
                        q.offer(new int[]{r, c});
                    }
                }
            }
            return false;
        }
    }
    //https://leetcode.com/problems/path-with-minimum-effort/discuss/909017/JavaPython-Dijikstra-Binary-search-Clean-and-Concise/744940
    class Solution3 {
        public static final int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        public int minimumEffortPath(int[][] heights) {
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[2], b[2]));
            int n = heights.length;
            int m = heights[0].length;
            Integer[][] minDist = new Integer[n][m];
            minDist[0][0] = 0;
            pq.offer(new int[]{0, 0, 0});
            while (!pq.isEmpty()) {
                int[] cur = pq.poll();
                if (cur[0] == n - 1 && cur[1] == m - 1) {
                    return cur[2];
                }
                for (int[] dir : dirs) {
                    int nx = cur[0] + dir[0];
                    int ny = cur[1] + dir[1];
                    if (nx < 0 || nx >= n || ny < 0 || ny >= m) {
                        continue;
                    }
                    int effort = Math.max(cur[2], Math.abs(heights[cur[0]][cur[1]] - heights[nx][ny]));
                    if (minDist[nx][ny] == null || minDist[nx][ny] > effort) {
                        minDist[nx][ny] = effort;
                        pq.offer(new int[]{nx, ny, minDist[nx][ny]});
                    }
                }
            }
            return -1;
        }
    }
}

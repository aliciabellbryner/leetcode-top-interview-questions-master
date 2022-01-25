package topinterviewquestions;

import java.util.*;

public class Problem_1284_MinimumNumberofFlipstoConvertBinaryMatrixtoZeroMatrix_G {
    //https://leetcode.com/problems/minimum-number-of-flips-to-convert-binary-matrix-to-zero-matrix/discuss/446342/JavaPython-3-Convert-matrix-to-int%3A-BFS-and-DFS-codes-w-explanation-comments-and-analysis.
    //Time: O(m * n * 2 ^ (m * n)), Space: O(2 ^ (m * n)).
    //BFS
    class Solution1 {
        private static final int[][] dirs = {{0,0}, {0,1}, {0,-1}, {1, 0}, {-1, 0}};//加上{0,0}的原因是由于不仅它的邻居要取反，它自己也得取反
        public int minFlips(int[][] mat) {
            int start = 0, M = mat.length, N = mat[0].length;
            for (int i = 0; i < M; ++i) {
                for (int j = 0; j < N; ++j) {
                    start |= mat[i][j] << (i * N + j); // convert the matrix to an int.
                }
            }
            Queue<Integer> queue = new LinkedList<>(Arrays.asList(start));
            Set<Integer> visited = new HashSet<>(queue);
            for (int step = 0; !queue.isEmpty(); ++step) {
                for (int size = queue.size(); size > 0; --size) {
                    int cur = queue.poll();
                    if (cur == 0) // All 0s matrix found.
                        return step;
                    for (int i = 0; i < M; ++i) { // traverse all M * N bits of cur.
                        for (int j = 0; j < N; ++j) {
                            int next = cur;
                            for (int[] dir : dirs) { // flip the cell (i, j) and its neighbors.
                                int r = i + dir[0], c = j + dir[1];
                                if (r >= 0 && r < M && c >= 0 && c < N)
                                    next ^= 1 << (r * N + c);//"^": xor异或，就相当于把r*N+c位置取反
                            }
                            if (visited.add(next)) // visited it before ?
                                queue.offer(next); // no, put it into the Queue.
                        }
                    }
                }
            }
            return -1; // impossible to get all 0s matrix.
        }
    }

    //DFS
    //Time: O(m * n * 2 ^ (m * n)), Space: O(2 ^ (m * n)).
    class Solution2 {
        private static final int[] dirs = {0, 0, 1, 0, -1, 0};

        public int minFlips(int[][] mat) {
            int start = 0, M = mat.length, N = mat[0].length;
            for (int i = 0; i < M; ++i) {
                for (int j = 0; j < N; ++j) {
                    start |= mat[i][j] << i * N + j;
                }
            }
            Deque<int[]> deque = new ArrayDeque<>();
            deque.push(new int[]{start, 0});
            Map<Integer, Integer> visited = new HashMap<>();
            visited.put(start, 0);
            while (!deque.isEmpty()) {
                int[] a = deque.pop();
                int cur = a[0], step = a[1];
                for (int i = 0; i < M; ++i) {
                    for (int j = 0; j < N; ++j) {
                        int next = cur;
                        for (int k = 0; k < 5; ++k) {
                            int r = i + dirs[k], c = j + dirs[k + 1];
                            if (r >= 0 && r < M && c >= 0 && c < N) {
                                next ^= 1 << r * N + c;
                            }
                        }
                        if (visited.getOrDefault(next, Integer.MAX_VALUE) > step + 1) {
                            visited.put(next, step + 1);
                            deque.push(new int[]{next, step + 1});
                        }
                    }
                }
            }
            return visited.getOrDefault(0, -1);
        }
    }
}

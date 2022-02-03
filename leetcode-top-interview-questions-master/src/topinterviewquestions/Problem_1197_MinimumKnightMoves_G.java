package topinterviewquestions;

import java.util.*;

public class Problem_1197_MinimumKnightMoves_G {
    //https://leetcode.com/problems/minimum-knight-moves/solution/
    //Approach 3: DFS (Depth-First Search) with Memoization
    //Time and Space Complexity: O(∣x⋅y∣)
    class Solution {
        private Map<String, Integer> memo = new HashMap<>();

        private int dfs(int x, int y) {
            String key = x + "," + y;
            if (memo.containsKey(key)) {
                return memo.get(key);
            }

            if (x + y == 0) {//说明就在那一点
                return 0;
            } else if (x + y == 2) {//从(0,0)到(1,1)或者（0，2）都需要两步
                return 2;
            } else {
                Integer res = Math.min(dfs(Math.abs(x - 1), Math.abs(y - 2)),
                        dfs(Math.abs(x - 2), Math.abs(y - 1))) + 1;
                memo.put(key, res);
                return res;
            }
        }

        public int minKnightMoves(int x, int y) {
            return dfs(Math.abs(x), Math.abs(y));
        }
    }

    //https://leetcode.com/problems/minimum-knight-moves/discuss/401580/Clean-Java-BFS-solution
    //Time and Space Complexity: O((max(∣x∣,∣y∣))^2)
    class Solution1 {
        private final int[][] DIRECTIONS = new int[][] {{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};

        public int minKnightMoves(int x, int y) {
            x = Math.abs(x);
            y = Math.abs(y);

            Queue<int[]> queue = new LinkedList<>();
            queue.add(new int[] {0, 0});

            Set<String> visited = new HashSet<>();
            visited.add("0,0");

            int result = 0;
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    int[] cur = queue.remove();
                    int curX = cur[0];
                    int curY = cur[1];
                    if (curX == x && curY == y) {
                        return result;
                    }

                    for (int[] d : DIRECTIONS) {
                        int newX = curX + d[0];
                        int newY = curY + d[1];
                        if (!visited.contains(newX + "," + newY) && newX >= -1 && newY >= -1) {
                            queue.add(new int[] {newX, newY});
                            visited.add(newX + "," + newY);
                        }
                    }
                }
                result++;
            }
            return -1;
        }
    }
}
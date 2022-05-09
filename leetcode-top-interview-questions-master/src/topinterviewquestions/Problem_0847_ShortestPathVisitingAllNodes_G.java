package topinterviewquestions;

import java.util.ArrayList;

public class Problem_0847_ShortestPathVisitingAllNodes_G {
    //Approach 2: Breadth-First Search (BFS)
    //https://leetcode.com/problems/shortest-path-visiting-all-nodes/solution/
    /*
    Given NN as the number of nodes in the graph:

Time complexity: O(2 ^ N * N ^ 2)
The total number of possible states that can be put in our queue is O(2 ^ N \cdot N), because there are 2 ^ N
  possibilities for mask, each of which can be paired with one of NN nodes.
At each state, we use a for loop to loop through all the edges the given node has. In the worst case, when the graph is fully connected, each node will have N - 1N−1 neighbors, so this step costs O(N)O(N) as the work done inside the for-loop is O(1)O(1).
Despite having the same time complexity as the first approach, in most cases, this algorithm will outperform the first one for the reasons we talked about in the intuition section, particularly because this algorithm will exit early as soon as it finds a solution.
Space complexity: O(2 ^ N \cdot N)

By the end of the algorithm, most of our extra space will be occupied by seen. Same as in the previous approach, depending on the implementation, seen will either be the same size as the number of states when it is initialized or it will eventually grow to that size by the end of the algorithm in the worst-case scenario.
     */
    class Solution {
        public int shortestPathLength(int[][] graph) {
            if (graph.length == 1) {
                return 0;
            }

            int n = graph.length;
            int endingMask = (1 << n) - 1;
            boolean[][] seen = new boolean[n][endingMask];
            ArrayList<int[]> queue = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                queue.add(new int[] {i, 1 << i});
                seen[i][1 << i] = true;
            }

            int steps = 0;
            while (!queue.isEmpty()) {
                ArrayList<int[]> nextQueue = new ArrayList<>();
                for (int i = 0; i < queue.size(); i++) {
                    int[] currentPair = queue.get(i);
                    int node = currentPair[0];
                    int mask = currentPair[1];
                    for (int neighbor : graph[node]) {
                        int nextMask = mask | (1 << neighbor);
                        if (nextMask == endingMask) {
                            return 1 + steps;
                        }

                        if (!seen[neighbor][nextMask]) {
                            seen[neighbor][nextMask] = true;
                            nextQueue.add(new int[] {neighbor, nextMask});
                        }
                    }
                }
                steps++;
                queue = nextQueue;
            }

            return -1;
        }
    }
}

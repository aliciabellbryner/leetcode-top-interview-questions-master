package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Problem_0310_MinimumHeightTrees_G {
    //https://leetcode.com/problems/minimum-height-trees/solution/
    /*
    Let |V| be the number of nodes in the graph, then the number of edges would be |V| - 1∣V∣−1 as specified in the problem.

Time Complexity: O(∣V∣)

First, it takes ∣V∣−1 iterations for us to construct a graph, given the edges.

With the constructed graph, we retrieve the initial leaf nodes, which takes ∣V∣ steps.

During the BFS trimming process, we will trim out almost all the nodes (|V|) and edges (|V|-1∣V∣−1) from the edges. Therefore, it would take us around ∣V∣+∣V∣−1 operations to reach the centroids.

To sum up, the overall time complexity of the algorithm is O(∣V∣).

Space Complexity: O(∣V∣)

We construct the graph with adjacency list, which has ∣V∣ nodes and ∣V∣−1 edges. Therefore, we would need ∣V∣+∣V∣−1 space for the representation of the graph.

In addition, we use a queue to keep track of the leaf nodes. In the worst case, the nodes form a star shape, with one centroid and the rest of the nodes as leaf nodes. In this case, we would need |V|-1∣V∣−1 space for the queue.

To sum up, the overall space complexity of the algorithm is also O(∣V∣).
     */
    //思路就是把它看成是找这个图的中心点，把最外一层也就是neighbor只有一个的node放进leaf的集合里，然后再逐层剥离，直到最后剩一个或两个的时候就是我们要的结果了
    class Solution {
        public List<Integer> findMinHeightTrees(int n, int[][] edges) {

            // edge cases
            if (n < 2) {
                ArrayList<Integer> res = new ArrayList<>();
                for (int i = 0; i < n; i++)
                    res.add(i);
                return res;
            }

            // Build the graph with the adjacency list
            ArrayList<Set<Integer>> neighbors = new ArrayList<>();//index表示哪个位置的node，值list代表它的neighbors
            for (int i = 0; i < n; i++) {
                neighbors.add(new HashSet<Integer>());
            }

            for (int[] edge : edges) {
                Integer start = edge[0], end = edge[1];
                neighbors.get(start).add(end);
                neighbors.get(end).add(start);
            }

            // Initialize the first layer of res
            ArrayList<Integer> res = new ArrayList<>();
            for (int i = 0; i < n; i++)
                if (neighbors.get(i).size() == 1)
                    res.add(i);

            // Trim the res until reaching the centroids
            int remainingNodes = n;
            while (remainingNodes > 2) {//The iteration terminates when there are no more than two nodes left in the graph, which are the desired centroids nodes.
                remainingNodes -= res.size();
                ArrayList<Integer> newLeaves = new ArrayList<>();

                // remove the current res along with the edges
                for (Integer leaf : res) {
                    // the only neighbor left for the leaf node
                    for (Integer neighbor : neighbors.get(leaf)) {
//                    Integer neighbor = neighbors.get(leaf).iterator().next();
                        // remove the edge along with the leaf node
                        neighbors.get(neighbor).remove(leaf);
                        if (neighbors.get(neighbor).size() == 1)
                            newLeaves.add(neighbor);
                    }
                }

                // prepare for the next round
                res = newLeaves;
            }

            // The remaining nodes are the centroids of the graph
            return res;
        }
    }
}

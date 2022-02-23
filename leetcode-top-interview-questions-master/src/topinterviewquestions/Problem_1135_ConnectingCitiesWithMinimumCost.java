package topinterviewquestions;

import java.util.*;

/*
There are n cities labeled from 1 to n. You are given the integer n and an array connections where connections[i] = [xi, yi, costi] indicates that the cost of connecting city xi and city yi (bidirectional connection) is costi.

Return the minimum cost to connect all the n cities such that there is at least one path between each pair of cities. If it is impossible to connect all the n cities, return -1,

The cost is the sum of the connections' costs used.



Example 1:


Input: n = 3, connections = [[1,2,5],[1,3,6],[2,3,1]]
Output: 6
Explanation: Choosing any 2 edges will connect all cities so we choose the minimum 2.
Example 2:


Input: n = 4, connections = [[1,2,3],[3,4,4]]
Output: -1
Explanation: There is no way to connect all cities even if all edges are used.


Constraints:

1 <= n <= 104
1 <= connections.length <= 104
connections[i].length == 3
1 <= xi, yi <= n
xi != yi
0 <= costi <= 105
 */
public class Problem_1135_ConnectingCitiesWithMinimumCost {
    /*
    Approach 1: Minimum Spanning Tree (Using Kruskal's algorithm)
Intuition

If we model the cities and connections as a graph, each connection is an edge (undirected) and each city is a node of the graph. We need to find a subset of edges which connects all the nodes of the graph with the minimum possible total weight. This is by definition the Minimum Spanning Tree or MST of a graph.

Algorithm

There are a variety of algorithms that we can use to obtain the MST of a graph. We will use Kruskal's algorithm here, which is implemented using the Disjoint set Union-Find data structure.

In order to obtain the MST using Kruskal's algorithm, we first sort all the connections (edges) present in the graph based on their weights (in increasing order) and will iterate over them one by one. The objective here is to greedily pick edges that will help us to connect more nodes in the graph. Each time we find a new edge which does not result in a cycle with the edges selected so far, we add that edge in the final MST. We keep doing this till we have obtained the MST which connects all the nodes in the graph (i.e. connects all the cities using the connections).

Disjoint-set union find can be implemented in a couple of ways. A plain union find is shown below which keeps the track of the parent of each node (initially parent of i is set to itself, i.e. i) and performs the union and find using a helper method getRoot.


The above implementation can be made faster by incorporating Path compression. Here, while obtaining the root, we compress the path by assigning the grandparent of the node as the parent (thereby skipping connections and moving nodes closer to the root). We modify the Find method to implement path compression.


This can be made even faster using a technique known as Weighted Union. In this technique, in addition to the parent nodes, we also keep the weights of each of the nodes. Every time we take union, the root node with more weight (i.e. having more elements in the corresponding set) is used as the parent node of the other node. We initialize the weight corresponding to each node as 1 initially, as each element belongs to it's own set in the beginning. Below is the implementation of this idea (we modify Union method).


If we combine both Path compression and Weighted Union, it takes \log^{\ast} Nlog
∗
 N for the union and find operations in case of Disjoint-set union link. Here \log^{\ast} Nlog
∗
 N is an extremely slow-growing inverse Ackermann function a.k.a Iterated logarithm and practically does not exceed 5. Hence it can be treated as a constant for implementation purposes.

We can combine all the concepts we have seen above in order to implement Kruskal's algorithm for obtaining MST of a graph. Below is the implementation of this.


Complexity Analysis

Time complexity: Assuming NN to be the total number of nodes (cities) and MM to be the total number of edges (connections). Sorting all the MM connections will take O(M \cdot \log M)O(M⋅logM). Performing union find each time will take \log^{\ast} Nlog
∗
 N (Iterated logarithm). Hence for M edges, it's O(M \cdot \log^{\ast} N)O(M⋅log
∗
 N) which is practically O(M)O(M) as the value of iterated logarithm, \log^{\ast} Nlog
∗
 N never exceeds 5.

Space complexity: O(N), space required by parents and weights.
     */

    class DisjointSet {
        private int[] weights; // Used to store weights of each nodes
        private int[] parents;

        public void Union(int a, int b) {
            int rootA = Find(a);
            int rootB = Find(b);
            // If both a and b have same root, i.e. they both belong to the same set, hence we don't need to take union
            if (rootA == rootB) return;

            // Weighted union
            if (this.weights[rootA] > this.weights[rootB]) {
                // In case rootA is having more weight
                // 1. Make rootA as the parent of rootB
                // 2. Increment the weight of rootA by rootB's weight
                this.parents[rootB] = rootA;
                this.weights[rootA] += this.weights[rootB];
            } else {
                // Otherwise
                // 1. Make rootB as the parent of rootA
                // 2. Increment the weight of rootB by rootA's weight
                this.parents[rootA] = rootB;
                this.weights[rootB] += this.weights[rootA];
            }
        }

        public int Find(int a) {
            // Traverse all the way to the top (root) going through the parent nodes
            while (a != this.parents[a]) {
                // Path compression
                // a's grandparent is now a's parent
                this.parents[a] = this.parents[parents[a]];
                a = this.parents[a];
            }
            return a;
        }

        public boolean isInSameGroup(int a, int b) {
            // Return true if both a and b belong to the same set, otherwise return false
            return Find(a) == Find(b);
        }

        // Initialize weight for each node to be 1
        public DisjointSet(int N) {
            this.parents = new int[N + 1];
            this.weights = new int[N + 1];
            // Set the initial parent node to itself
            for (int i = 1; i <= N; ++i) {
                this.parents[i] = i;
                this.weights[i] = 1;
            }
        }
    }

    class Solution {
        public int minimumCost(int N, int[][] connections) {
            DisjointSet disjointset = new DisjointSet(N);
            // Sort connections based on their weights (in increasing order)
            Arrays.sort(connections, (a, b) -> a[2] - b[2]);
            // Keep track of total edges added in the MST
            int total = 0;
            // Keep track of the total cost of adding all those edges
            int cost = 0;
            for (int i = 0; i < connections.length; ++i) {
                int a = connections[i][0];
                int b = connections[i][1];
                // Do not add the edge from a to b if it is already connected
                if (disjointset.isInSameGroup(a, b)) continue;
                // If a and b are not connected, take union
                disjointset.Union(a, b);
                // increment cost
                cost += connections[i][2];
                // increment number of edges added in the MST
                total++;
            }
            // If all N nodes are connected, the MST will have a total of N - 1 edges
            if (total == N - 1) {
                return cost;
            } else {
                return -1;
            }
        }
    }



    //diss
    //We use Kruskal’s algorithm to generate a minimum spanning tree for the graph. Use Union-Find to detect cycle.
    //
    //Idea is simple:
    //
    //Sort edges to no-descresing order
    //Pick the smallest edge that does not form a cycle
    //Repeat until MST is formed and every node is connected.
    //Implemented Union-Find with path comression to improve efficiency.
    //
    //There are tons of materials online about the proof of correctness and analysis of this algorithm. Feel free to check them around.
    //
    //Hope this helps.
    class Solution2 {

        int[] parent;
        int n;

        private void union(int x, int y) {
            int px = find(x);
            int py = find(y);

            if (px != py) {
                parent[px] = py;
                n--;
            }
        }

        private int find(int x) {
            if (parent[x] == x) {
                return parent[x];
            }
            parent[x] = find(parent[x]); // path compression
            return parent[x];
        }

        public int minimumCost(int N, int[][] connections) {
            parent = new int[N + 1];
            n = N;
            for (int i = 0; i <= N; i++) {
                parent[i] = i;
            }

            Arrays.sort(connections, (a, b) -> (a[2] - b[2]));

            int res = 0;

            for (int[] c : connections) {
                int x = c[0], y = c[1];
                if (find(x) != find(y)) {
                    res += c[2];
                    union(x, y);
                }
            }

            return n == 1 ? res : -1;
        }
    }


    class Solution3 {
        public int minimumCost(int n, int[][] connections) {
            if (n == 1) return 0;
            List<List<int[]>> graph = getGraph(n, connections);
            boolean[] visited = new boolean[n + 1];
            PriorityQueue<int[]> queue = new PriorityQueue<>(new CostComparator());
            int cost = 0, numberOfCitiesVisited = 0;

            queue.offer(new int[] {1, 0});
            while (!queue.isEmpty()) {
                int[] u = queue.poll();
                if (visited[u[0]]) continue;
                visited[u[0]] = true;
                cost += u[1]; //first time visit so cumulate the cost
                numberOfCitiesVisited++;

                for (int[] v : graph.get(u[0])) {
                    if (!visited[v[0]]) queue.offer(new int[] {v[0], v[1]});
                }
            }

            return numberOfCitiesVisited == n ? cost : -1;
        }

        private List<List<int[]>> getGraph(int n, int[][] connections) {
            List<List<int[]>> graph = new ArrayList<>();
            for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
            for (int[] conn : connections) {
                int c1 = conn[0], c2 = conn[1], price = conn[2];
                graph.get(c1).add(new int[] {c2, price});
                graph.get(c2).add(new int[] {c1, price});
            }
            return graph;
        }

        private static final class CostComparator implements Comparator<int[]> {
            @Override
            public int compare(int[] x, int[] y) {
                return x[1] - y[1];
            }
        }
    }


}

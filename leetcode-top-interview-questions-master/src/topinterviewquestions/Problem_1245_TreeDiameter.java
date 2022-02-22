package topinterviewquestions;

import java.util.*;

/*
The diameter of a tree is the number of edges in the longest path in that tree.

There is an undirected tree of n nodes labeled from 0 to n - 1. You are given a 2D array edges where edges.length == n - 1 and edges[i] = [ai, bi] indicates that there is an undirected edge between nodes ai and bi in the tree.

Return the diameter of the tree.



Example 1:


Input: edges = [[0,1],[0,2]]
Output: 2
Explanation: The longest path of the tree is the path 1 - 0 - 2.
Example 2:


Input: edges = [[0,1],[1,2],[2,3],[1,4],[4,5]]
Output: 4
Explanation: The longest path of the tree is the path 3 - 2 - 1 - 4 - 5.


Constraints:

n == edges.length + 1
1 <= n <= 104
0 <= ai, bi < n
ai != bi
 */
public class Problem_1245_TreeDiameter {
    /*
    Solution
Overview
In this article, we will present three approaches: two are in BFS and one in DFS.

As a spoiler alert, if one has solved any of the following problems, one would have a head start to solve this problem. On the other hand, solving this problem would also help one to solve the following problems.

Minium Height Tree

Diameter of N-ary Tree

Approach 1: Farthest Nodes via BFS
Intuition

In this first approach, let us follow the hints of the problem, which suggest us to run BFS (Breadth-First Search) for two rounds. For those of you who are not familiar with the concept of BFS, we would recommend one to check out our Explore card about BFS.

In the problem, we are asked to find the diameter of the graph, which by definition is the distance between two nodes that are the farthest apart.

According to the definition, the problem could be solved if we could identify the two nodes that have the longest distance among all. Let us refer to these two nodes as the extreme peripheral nodes.

First of all, we assert that starting from any node in the graph, if we run a BFS traversal, the last node that we visit would be one of the extreme nodes. An intuition that supports the above assertion is that as an extreme peripheral node, it should be the one that is far away from any of the other nodes in the graph. Given any node, the longest distance that starts from this node must end with one of the extreme peripheral nodes.

example of peripheral nodes

Once we identify one of the extreme peripheral nodes, we then could apply again the BFS traversal. But this time, we would start from the identified extreme peripheral node. At the end of the second BFS traversal, we would land on another extreme peripheral node. The distance that we traverse would be the diameter of the graph, according to the definition.

Algorithm

Following the above intuition, the main algorithm is to find two extreme peripheral nodes via BFS traversal.

Let us define a function called bfs(start) which returns two results: 1). the farthest node starting from the start node; 2). the distance between the start and the farthest nodes.

As the name suggests, we could apply the BFS (Breadth-First Search) traversal to implement the above function.

Once the bfs(start) is implemented, we simply invoke it twice to solve the problem. In the first invocation, we would obtain one of the extreme peripheral nodes. With the obtained peripheral node, we then invoke the function again to obtain the other extreme peripheral node and more importantly the distance between the two extreme peripheral nodes.


Complexity Analysis

Let NN be the number of nodes in the graph, then the number of edges in the graph would be N-1N−1 as specified in the problem.

Time Complexity: \mathcal{O}(N)O(N)

First we iterate through all edges to build an adjacency list representation of the graph. The time complexity of this step would be \mathcal{O}(N)O(N).

In the main algorithm, we perform the BFS traversal twice on the graph. Each traversal will take \mathcal{O}(N)O(N) time, where we visit each node once and only once.

To sum up, the overall time complexity of the algorithm is \mathcal{O}(N) + 2 \cdot \mathcal{O}(N) = \mathcal{O}(N)O(N)+2⋅O(N)=O(N).

Space Complexity: \mathcal{O}(N)O(N)

We used an adjacency list representation for the graph, whose space would be proportional to the total number of nodes and edges in the graph. Since the graph is undirected (i.e. the edge is bi-directional), the number of neighbors in the adjacency list would be twice the number of edges. Therefore, the space needed for the graph would be \mathcal{O}(N + 2 \cdot N) = \mathcal{O}(N)O(N+2⋅N)=O(N).

During the BFS traversal, we used an array to indicate the state of each node (whether it is visited or not). This array would consume \mathcal{O}(N)O(N) space.

During the BFS traversal, in addition, we used some queues to keep track of the nodes to be visited at each level (i.e. hop). At any given moment, we will keep no more than two levels of nodes in the queues. In the worst case, the queue could cover almost all nodes in the graph. As a result, the space complexity of the queues would be \mathcal{O}(N)O(N).

To sum up, the overall space complexity of the algorithm is \mathcal{O}(N) + \mathcal{O}(N) + \mathcal{O}(N) = \mathcal{O}(N)O(N)+O(N)+O(N)=O(N).

Approach 2: Centroids of Graph via BFS
Intuition

Another concept that is closely related to the concept of diameter is called centroid. Intuitively, the centroid nodes are the ones that are situated in the center of a graph. More precisely, the distance from the centroid to other nodes in the graph should be overall minimal, which is the opposite of the extreme peripheral nodes that we defined before.

Indeed, if we could identify the centroid of a graph, then the distance from this centroid to any of its extreme peripheral nodes would be half of the diameter of the graph.

By identifying the centroids nodes in a tree-alike graph, we could solve another similar problem called Minimum Height Tree.

There would be either one or two centroids in a tree-alike graph, and one can find a detailed proof in the solution of the Minimum Height Tree.

one centroid

In the above example where there is only one centroid, the diameter would be exactly twice of the distance between the centroid and any of extreme peripheral nodes (i.e. node 3 and node 5).
two centroids

In the above example where there are two centroids, the diameter would be one plus the distance between a pair of centroid and extreme peripheral node, by taking into account the distance between the two centroids.
Algorithm

In order to identify the centroids, we could apply the topological sorting algorithm here. The main idea is that we start from the peripheral nodes (which are also known as leaf nodes), then we trim the nodes off layer by layer, as if we are peel an "onion" till we reach its core (i.e. centroids).

Again, one can find more details in the solution of Minium Height Tree. Here we present a sample implementation.

Note that, one particularity of our topological sorting algorithm here is that while we trim off the nodes layer by layer, we count the number of steps for us to reach the centroids. The number of steps is also the distance between the centroids and the peripheral nodes, which is essential for the calculation of diameter.


In certain sense, we could also consider the above topological sorting as a sort of BFS Traversal, where we traverse the graph from the outer most layer of the graph (i.e. leaf nodes), level by level, into its inner most layer (i.e. centroids).

Complexity Analysis

Let NN be the number of nodes in the graph, then the number of edges in the graph would be N-1N−1 as specified in the problem.

Time Complexity: \mathcal{O}(N)O(N)

First we iterate through all edges to build an adjacency list representation of the graph. The time complexity of this step would be \mathcal{O}(N)O(N).

In the main algorithm, we peel the graph until there are only centroids left. During the traversal, we visit each node once and only once. As a result, it will take \mathcal{O}(N)O(N) time.

To sum up, the overall time complexity of the algorithm is \mathcal{O}(N) + \mathcal{O}(N) = \mathcal{O}(N)O(N)+O(N)=O(N).

Space Complexity: \mathcal{O}(N)O(N)

Similar to the previous approach, we used an adjacency list to keep the graph, whose space complexity is \mathcal{O}(N)O(N) as we discussed before.

During the topological sorting, we used some queues to keep track of the leaf nodes to be visited at each layer. At any given moment, we will keep no more than two levels of nodes in the queues. In the worst case, the queue could cover almost all nodes in the graph. As a result, the space complexity of the queues would be \mathcal{O}(N)O(N).

To sum up, the overall space complexity of the algorithm is \mathcal{O}(N) + \mathcal{O}(N) = \mathcal{O}(N)O(N)+O(N)=O(N).

Approach 3: DFS (Depth-First Search)
Intuition

We applied the BFS strategy in the first approach to solve the problem. As is often the case, we could also apply another common strategy called DFS (Depth-First Search). This happens to be the case for this problem as well.

One can take the inspiration from another similar problem called Diameter of N-ary Tree. At the first glance, one might even consider them as the same problem.

Actually, the only difference between them lies in the input. In this problem, our input is a list of bi-directional edges, which we could convert into a representation of Graph; While for the problem of Diameter of N-ary Tree, the input is a Tree data structure, where the edges are uni-directional.

First of all, we will use the concepts of leaf nodes and parent nodes as in a Tree data structure. For a parent node, if we could obtain two longest distances (denoted as t1 and t2) starting from this parent node to any of its descendant leaf nodes, then the longest path that traverse this parent node would be t1 + t2.

Since any node in the graph has the potential to be part of the path that forms the diameter of the graph, we can iterate through each node to obtain all the longest paths as we defined shortly before. The diameter of the graph would be the maximum among all the longest paths that traverse each node.

Here we show an example where we assume the node 0 in the graph as the root node.

longest distance

As shown in the above example, we also indicate two longest distances for each node. Note that, if a parent node has only one child node, then it can only have one longest distance to the leaf nodes. The second longest distance for this parent node would be zero. By adding the two longest distances together for each node, we would know that the node 1 has the longest path (i.e. with distance of 4) among all.

Algorithm

Given the above intuition, we could apply the DFS (Depth-First Search) strategy to obtain the longest path that traverse each node.

During the DFS traversal, we would also update the diameter with the longest path that we obtain at each node.

First of all, we could convert the input edges into the adjacency list, which this time we would treat as Tree, rather than Graph. We assume the node with the index of 0 as the root node.

We then define a function named dfs(curr, visited) which returns the maximal distance starting from the curr node to any of its descendant leaf nodes. The visited parameter is used to keep track of the nodes that we've visited so far.

Within the function of dfs(curr, visited), we will obtain the top two maximal distances starting from the curr node. With these top two distances, we can then update the global diameter that we've seen so far.

Once we traverse the entire tree once and only once, we will obtain the diameter of the tree/graph.


Complexity Analysis

Let NN be the number of nodes in the graph, then the number of edges in the graph would be N-1N−1 as specified in the problem.

Time Complexity: \mathcal{O}(N)O(N)

First we iterate through all edges to build an adjacency list representation of the graph, which we will treat as a tree with node 0 as the root node. The time complexity of this step would be \mathcal{O}(N)O(N).

In the main algorithm, we traverse the tree/graph via DFS. During the traversal, we visit each node once and only once. As a result, it will take \mathcal{O}(N)O(N) time.

To sum up, the overall time complexity of the algorithm is \mathcal{O}(N) + \mathcal{O}(N) = \mathcal{O}(N)O(N)+O(N)=O(N).

Space Complexity: \mathcal{O}(N)O(N)

Similar to the previous approach, we used an adjacency list to keep the graph, whose space complexity is \mathcal{O}(N)O(N) as we discussed before.

During the DFS traversal, we used an array (visited) to keep track of the nodes we visited so far. The space complexity of the array is \mathcal{O}(N)O(N).

Since we apply recursion in the DFS traversal, which will incur additional memory consumption in the function call stack. In the worst case where all the nodes are chained up as a line, starting from the root node, the memory consumption for the call stack would be \mathcal{O}(N)O(N).

To sum up, the overall space complexity of the algorithm is \mathcal{O}(N) + \mathcal{O}(N) + \mathcal{O}(N) = \mathcal{O}(N)O(N)+O(N)+O(N)=O(N).
     */

    class Solution1 {

        public int treeDiameter(int[][] edges) {

            List<Set<Integer>> graph = new ArrayList<Set<Integer>>();
            for (int i = 0; i < edges.length + 1; ++i) {
                graph.add(new HashSet<Integer>());
            }

            for (int[] edge : edges) {
                Integer u = edge[0], v = edge[1];
                graph.get(u).add(v);
                graph.get(v).add(u);
            }

            // 1). find one of the farthest nodes
            // farthest_node, distance_1 = bfs(0)
            int[] nodeDistance = bfs(0, graph);
            // 2). find the other farthest node
            // and the distance between two farthest nodes
            // another_farthest_node, distance_2 = bfs(farthest_node)
            nodeDistance = bfs(nodeDistance[0], graph);

            return nodeDistance[1];
        }

        /**
         * return the farthest node from the 'start' node and the distance between them.
         */
        private int[] bfs(int start, List<Set<Integer>> graph) {
            boolean[] visited = new boolean[graph.size()];
            visited[start] = true;

            LinkedList<Integer> queue = new LinkedList<Integer>();
            queue.addLast(start);
            Integer lastNode = start, distance = -1;

            while (queue.size() > 0) {
                LinkedList<Integer> nextQueue = new LinkedList<Integer>();

                while (queue.size() > 0) {
                    int nextNode = queue.removeFirst();
                    for (Integer neighbor : graph.get(nextNode)) {
                        if (!visited[neighbor]) {
                            visited[neighbor] = true;
                            nextQueue.addLast(neighbor);
                            lastNode = neighbor;
                        }
                    }
                }
                // increase the distance for each level of BFS traversal.
                distance += 1;
                queue = nextQueue;
            }

            return new int[] {lastNode, distance};
        }

    }


    class Solution2 {

        public int treeDiameter(int[][] edges) {

            // build the adjacency list representation of the graph.
            List<Set<Integer>> graph = new ArrayList<Set<Integer>>();
            for (int i = 0; i < edges.length + 1; ++i) {
                graph.add(new HashSet<Integer>());
            }
            for (int[] edge : edges) {
                Integer u = edge[0], v = edge[1];
                graph.get(u).add(v);
                graph.get(v).add(u);
            }

            // find the outer most nodes, i.e. leaf nodes
            LinkedList<Integer> leaves = new LinkedList<Integer>();
            for (int vertex = 0; vertex < graph.size(); ++vertex) {
                if (graph.get(vertex).size() == 1)
                    leaves.add(vertex);
            }

            // "peel" the graph layer by layer,
            // until we have the centroids left.
            int layers = 0;
            int vertexLeft = edges.length + 1;
            while (vertexLeft > 2) {
                vertexLeft -= leaves.size();

                LinkedList<Integer> nextLeaves = new LinkedList<Integer>();

                for (int leaf : leaves) {
                    // the only neighbor left on the leaf node.
                    int neighbor = graph.get(leaf).iterator().next();
                    graph.get(neighbor).remove(leaf);
                    if (graph.get(neighbor).size() == 1)
                        nextLeaves.add(neighbor);
                }

                layers += 1;
                leaves = nextLeaves;
            }

            if (vertexLeft == 1)
                return layers * 2;
            else
                return layers * 2 + 1;
        }
    }


    class Solution3 {
        private List<List<Integer>> graph;
        private Integer diameter = 0;

        public int treeDiameter(int[][] edges) {

            // build the adjacency list representation of the graph.
            this.graph = new ArrayList<List<Integer>>();
            boolean[] visited = new boolean[edges.length + 1];
            for (int i = 0; i < edges.length + 1; ++i) {
                this.graph.add(new ArrayList<Integer>());
                visited[i] = false;
            }
            for (int[] edge : edges) {
                Integer u = edge[0], v = edge[1];
                this.graph.get(u).add(v);
                this.graph.get(v).add(u);
            }

            dfs(0, visited);

            return this.diameter;
        }

        /**
         * return the max distance
         *   starting from the 'curr' node to its the leaf nodes
         */
        private int dfs(int curr, boolean[] visited) {
            // the top 2 distance starting from this node
            Integer topDistance1 = 0, topDistance2 = 0;

            visited[curr] = true;
            for (Integer neighbor : graph.get(curr)) {
                int distance = 0;
                if (!visited[neighbor])
                    distance = 1 + this.dfs(neighbor, visited);

                if (distance > topDistance1) {
                    topDistance2 = topDistance1;
                    topDistance1 = distance;
                } else if (distance > topDistance2) {
                    topDistance2 = distance;
                }
            }

            // with the top 2 distance, we can update the current diameter
            this.diameter = Math.max(this.diameter, topDistance1 + topDistance2);

            return topDistance1;
        }
    }


    //diss
    //[Java] Depth of the Tree solution - Time O(N) - Easy to understand
    //Similar problem: https://leetcode.com/problems/diameter-of-binary-tree/
    //Travese all the nodes of the tree. The diameter of the tree is maximum of the longest path through each node.
    //Longest path through a node is sum of top 2 depths of children's tree.
    //Time: O(n), where n is the number of edges in the tree
    //Space: O(n)
    class Solution {
        int diameter = 0;
        public int treeDiameter(int[][] edges) {
            int n = edges.length + 1;
            List<Integer>[] graph = new List[n];
            for (int i = 0; i < n; ++i) graph[i] = new LinkedList<>();
            for (int[] e : edges) {
                graph[e[0]].add(e[1]);
                graph[e[1]].add(e[0]);
            }
            diameter = 0;
            depth(0, -1, graph);
            return diameter;
        }
        // Depth of the tree is the number of nodes along the longest path from the root node down to the farthest leaf node.
        int depth(int root, int parent, List<Integer>[] graph) {
            int maxDepth1st = 0, maxDepth2nd = 0;
            for (int child : graph[root]) {
                if (child == parent) continue; // Only one way from root node to child node, don't allow child node go to root node again!
                int childDepth = depth(child, root, graph);
                if (childDepth > maxDepth1st) {
                    maxDepth2nd = maxDepth1st;
                    maxDepth1st = childDepth;
                } else if (childDepth > maxDepth2nd) {
                    maxDepth2nd = childDepth;
                }
            }
            int longestPathThroughRoot = maxDepth1st + maxDepth2nd + 1; // the number of nodes in the longest path
            diameter = Math.max(diameter, longestPathThroughRoot - 1); // diameter = number of edges = number of nodes - 1
            return maxDepth1st + 1;
        }
    }

}

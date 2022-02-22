package topinterviewquestions;

import java.util.*;

/*
here are n houses in a village. We want to supply water for all the houses by building wells and laying pipes.

For each house i, we can either build a well inside it directly with cost wells[i - 1] (note the -1 due to 0-indexing), or pipe in water from another well to it. The costs to lay pipes between houses are given by the array pipes where each pipes[j] = [house1j, house2j, costj] represents the cost to connect house1j and house2j together using a pipe. Connections are bidirectional, and there could be multiple valid connections between the same two houses with different costs.

Return the minimum total cost to supply water to all houses.



Example 1:


Input: n = 3, wells = [1,2,2], pipes = [[1,2,1],[2,3,1]]
Output: 3
Explanation: The image shows the costs of connecting houses using pipes.
The best strategy is to build a well in the first house with cost 1 and connect the other houses to it with cost 2 so the total cost is 3.
Example 2:

Input: n = 2, wells = [1,1], pipes = [[1,2,1],[1,2,2]]
Output: 2
Explanation: We can supply water with cost two using one of the three options:
Option 1:
  - Build a well inside house 1 with cost 1.
  - Build a well inside house 2 with cost 1.
The total cost will be 2.
Option 2:
  - Build a well inside house 1 with cost 1.
  - Connect house 2 with house 1 with cost 1.
The total cost will be 2.
Option 3:
  - Build a well inside house 2 with cost 1.
  - Connect house 1 with house 2 with cost 1.
The total cost will be 2.
Note that we can connect houses 1 and 2 with cost 1 or with cost 2 but we will always choose the cheapest option.


Constraints:

2 <= n <= 104
wells.length == n
0 <= wells[i] <= 105
1 <= pipes.length <= 104
pipes[j].length == 3
1 <= house1j, house2j <= n
0 <= costj <= 105
house1j != house2j
 */
public class Problem_1168_OptimizeWaterDistributionInAVillage {
    /*
    Overview
Since the problem description involves connecting houses (vertices) using pipes (edges), we can tell that this problem is a variant of graph problems. More precisely, we can convert it into a standard minimum spanning tree (MST) problem, which we will discuss in detail how to do so in this article.

Concerning the MST problem, there exist several classic algorithms. In particular, we will demonstrate two of them, namely Prim's algorithm and Kruskal's algorithm, which are arguably the most popular ones and feasible to implement during an interview.

Intuition

First of all, let us introduce the problem of the minimum spanning tree.

Given a connected, edge-weighted and undirected graph, a minimum spanning tree is a subset of edges that connect all vertices while the total weights of these edges are minimum among all possible subsets.

One can draw some similarities between the above definition and our problem here. Specifically, we can consider each house as a vertex in a graph, and the pipes between the houses as edges in the graph.

However, there is one major difference between them. In our problem, every vertex and every edge comes with a cost. While in the setting of MST, only the edges are associated with the costs.

To bridge the gap, as suggested in the hints, the trick is to add one virtual vertex to the existing graph. Along with the addition of vertex, we also add edges between the virtual vertex and the rest of the vertices. Finally, we reassign the cost of each vertex to the corresponding newly-added edge.

Here is an illustration showing how we convert the graph in the example with the above trick.

graph conversion

With the converted graph, we then can take into account the costs from the vertex, via the additional edges. We can focus entirely on selecting the appropriate edges to create an MST. Thus, our problem is simplified to creating an MST from a list of weighted edges.

MST solution

In the above graph, we demonstrate the solution that we will find after solving the MST problem, which we can translate as "to minimize the cost, we should dig a well in the house indexed with 1 (denoted by the edge between indices 1 and 0), and then supply the water to the rest of the houses."

Approach 1: Prim's Algorithm with Heap
Intuition

Prim's (also known as Jarník's) algorithm is a greedy algorithm used to find the minimum spanning tree in a weighted and undirected graph.

The algorithm operates by building the tree one vertex at a time, from an arbitrary starting vertex, at each step adding the cheapest possible connection from any vertex in the tree to a vertex that is not in the tree.

Prim Demo

The above illustration demonstrates how Prim's algorithm works. Starting from an arbitrary vertex, Prim's algorithm grows the minimum spanning tree by adding one vertex at a time to the tree. The choice of a vertex is based on the greedy strategy, i.e. the addition of the new vertex incurs the minimum cost.

Algorithm

To implement Prim's algorithm, essentially we will need the following three data structures:

adjacency list: we need this to represent the graph, i.e. vertices and edges. The adjacency list can be a list of lists or a dictionary of lists.

set: we need a set to maintain all the vertices that we have added to the final minimum spanning tree, during the construction of the tree. With the help of set, we can determine whether a vertex has been added or not.

heap: due to the nature of the greedy strategy, at each step, we can determine the best edge to be added based on the cost it will add to the tree. Heap (also known as a priority queue) is a data structure that allows us to retrieve the minimum element in constant time and to remove the minimum element in logarithmic time. This fits our need to repeatedly find the lowest cost edge perfectly.

Implementation

By applying the above three data structures, the following steps can be used to implement Prim's algorithm.

First of all, given the input, we need to build a graph representation with the adjacency list.

Note that, since the graph is undirected (i.e. bidirectional), for each pipe, we need to add two entries in the adjacency list, with each end of the pipe as a starting vertex.
Also, to convert our problem into the MST problem, we need to add a virtual vertex (we index it as 0) together with the additional n edges to each house.
Starting from the virtual vertex, we build the MST by iteratively adding one vertex at a time.

Note, when using Prim's algorithm, we can use any vertex as a starting point. Here, for the sake of convenience, we start from the newly-added virtual vertex.
The process of building MST consists of a loop with the following substeps:

Each iteration, we pop an element from the heap. This element contains a vertex along with the cost that is associated with the edge that connecting the vertex to the tree. The vertex is chosen if it is not already in the tree. We know that the cost of this vertex is minimal among all choices because it was popped from the heap.

Once the vertex is added, we then examine its neighboring vertices. Specifically, we add these vertices along with their edges into the heap as the candidates for the next round of selection.

The loop terminates when we have added all the vertices from the graph into the MST.


Complexity Analysis

Let NN be the number of houses, and MM be the number of pipes from the input.

Time Complexity: O\big( (N+M) \cdot \log(N+M) \big)O((N+M)⋅log(N+M))

To build the graph, we iterate through the houses and pipes in the input, which takes O(N + M)O(N+M) time.

While building the MST, we might need to iterate through all the edges in the graph in the worst case, which amounts to N + MN+M in total. For each edge, it would enter and exit the heap data structure at most once. The enter of edge into heap (i.e. push operation) takes \log(N+M)log(N+M) time, while the exit of edge (i.e. pop operation) takes a constant time. Therefore, the time complexity of the MST construction process is O\big( (N+M) \cdot \log(N+M) \big)O((N+M)⋅log(N+M)).

To sum up, the overall time complexity of the algorithm is O\big( (N+M) \cdot \log(N+M) \big)O((N+M)⋅log(N+M)).

Space Complexity: O(N+M)O(N+M)

We break down the analysis accordingly into the three major data structures that we used in the algorithm.

The graph that we built consists of N+1N+1 vertices and 2 \cdot M2⋅M edges (i.e. pipes are bidirectional). Therefore, the space complexity of graph is O(N + 1 + 2 \cdot M) = O(N + M)O(N+1+2⋅M)=O(N+M).

The space complexity of the set that is used to hold the vertices in MST is O(N)O(N).

Finally, in the worst case, the heap we used might hold all the edges in the graph which is (N+M)(N+M).

To summarize, the overall space complexity of the algorithm is O(N+M)O(N+M).

Approach 2: Kruskal's Algorithm with Union-Find
Intuition

Another classical algorithm to solve the MST problem is called Kruskal's algorithm.

Similiar to Prim's algorithm, Kruskal's algorithm applies the greedy strategy to incrementally add new edges to the final solution.

Kruskal Demo

The above animation shows how Kruskal's algorithm grows the minimum spanning tree.

A major difference between them is that in Prim's algorithm the MST (minimal spanning tree) remains connected as a whole throughout the entire process, while in Kruskal's algorithm, the tree is formed by merging the disjoint components together.

Algorithm

Rather than adding vertices as in Prim's algorithm, the Kruskal's algorithm focuses on adding edges. Furthermore, in Kruskal's algorithm, we consider all edges at once ranked by their costs, while in Prim's algorithm, although edges are ranked by costs in a heap or priority queue, at each iteration, we only explore edges that are connected to the vertices that are already in the MST.

The overall idea of Kruskal's algorithm is that we iterate through all the edges ordered by their costs. For each edge, we decide whether to add it to the final MST. The decision is based on whether this new addition will help to connect more dots (i.e. vertices).

Union-Find examples

Add or Not to Add ?

The above diagram shows three example scenarios and for each scenario, specifies whether a new edge should be added or not. The solid edges have already been added to the MST, while the dashed edges have yet to be decided.

In the example on the left, we should add the new edge, since the edge bridges the gap between the two disjoint components.
In the middle example, we should also add the new edge, since it connects to an unseen vertex (i.e. connecting more dots).
In the example on the right, we should not add the new edge. Because it does not help us to make the current MST more connected, since all vertices are connected already.
A more concise criteria to determine whether we should add a new edge in Kruskal's algorithm is that whether both ends of the edge belong to the same component (group).

Implementation

In order to determine the membership for a collection of elements, we often apply the data structure called Disjoint Set which is also known as Union-Find data structure.

Essentially, the Union-Find data structure provides two interfaces:

find(a): the function returns the id of the group where the element a belongs to.
union(a, b): the function joins the two groups that the element a and b belong to. If they belong to the same group already, then the function does nothing.
We provide a full-fledged version of the Union-Find data structure with path compression and link-by-rank in the sample implementation.

If one would like to know more about how the Union-Find data structure works, one can refer to the solution for the problem of 323. Number of Connected Components in an Undirected Graph and a tutorial from Princeton University.

Given the Union-Find data structure, we can implement Kruskal's algorithm with the following two steps:

First of all, we sort all the edges based on their costs, including the additional edges that are added with the virtual vertex.

We then iterate through the sorted edges. For each edge, if both ends of the edge belong to different groups, with the help of the Union-Find data structure, we then add this edge into the final MST.


Note: in the above implementation, we tweak the union(a, b) a bit to make the code more efficient and concise.

In most implementations of Union-Find data structure, we do not return anything for the function of union(a, b). However, in our case, we return a flag to indicate whether the joining actually happens within the function. With this tweak, we only need to invoke the union(a,b) function in our iteration, rather than invoking find(a) == find(b) functions in addition.

Complexity Analysis

Since we applied the Union-Find data structure in our algorithm, let's begin with a statement on the time complexity of the data structure:

If KK operations, either Union or Find, are applied to LL elements, the total run time is \mathcal{O}(K \cdot \log^{*}{L})O(K⋅log
∗
 L), where \log^{*}log
∗
  is the iterated logarithm.

One can refer to the proof of Union-Find complexity and the tutorial from Princeton University for more details.

Let NN be the number of houses, and MM be the number of pipes from the input.

Time Complexity: O\big((N+M) \cdot \log(N+M) \big)O((N+M)⋅log(N+M))

First, we build a list of edges, which takes O(N + M)O(N+M) time.

We then sort the list of edges, which takes O\big((N+M) \cdot \log(N+M) \big)O((N+M)⋅log(N+M)) time.

At the end, we iterate through the sorted edges. For each iteration, we invoke a Union-Find operation. Hence, the time complexity for iteration is O\big( (N+M) * \log^{*}(N) \big)O((N+M)∗log
∗
 (N)).

To sum up, the overall time complexity of the algorithm is O\big((N+M) \cdot \log(N+M) \big)O((N+M)⋅log(N+M)) which is dominated by the sorting step.

Space Complexity: O(N+M)O(N+M)

The space complexity of our Union-Find data structure is O(N)O(N).

The space required by the list of edges is O(N+M)O(N+M).

Finally, the space complexity of the sorting algorithm depends on the implementation of each programming language. For instance, the list.sort() function in Python is implemented with the Timsort algorithm whose space complexity is \mathcal{O}(n)O(n) where nn is the number of the elements. While in Java, the Collections.sort() is implemented as a variant of quicksort algorithm whose space complexity is \mathcal{O}(\log{n})O(logn).

To sum up, the overall space complexity of the algorithm is O(N+M)O(N+M) which is dominated by the list of edges.
     */

    /*
    class Solution1 {
        public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
            // min heap to maintain the order of edges to be visited.
            PriorityQueue<Pair<Integer, Integer>> edgesHeap =
                    new PriorityQueue<>(n, (a, b) -> (a.getKey() - b.getKey()));

            // representation of graph in adjacency list
            List<List<Pair<Integer, Integer>>> graph = new ArrayList<>(n + 1);
            for (int i = 0; i < n + 1; ++i) {
                graph.add(new ArrayList<Pair<Integer, Integer>>());
            }

            // add a virtual vertex indexed with 0,
            //   then add an edge to each of the house weighted by the cost
            for (int i = 0; i < wells.length; ++i) {
                Pair<Integer, Integer> virtualEdge = new Pair<>(wells[i], i + 1);
                graph.get(0).add(virtualEdge);
                // initialize the heap with the edges from the virtual vertex.
                edgesHeap.add(virtualEdge);
            }

            // add the bidirectional edges to the graph
            for (int i = 0; i < pipes.length; ++i) {
                int house1 = pipes[i][0];
                int house2 = pipes[i][1];
                int cost = pipes[i][2];
                graph.get(house1).add(new Pair<Integer, Integer>(cost, house2));
                graph.get(house2).add(new Pair<Integer, Integer>(cost, house1));
            }

            // kick off the exploration from the virtual vertex 0
            Set<Integer> mstSet = new HashSet<>();
            mstSet.add(0);

            int totalCost = 0;
            while (mstSet.size() < n + 1) {
                Pair<Integer, Integer> edge = edgesHeap.poll();
                int cost = edge.getKey();
                int nextHouse = edge.getValue();
                if (mstSet.contains(nextHouse)) {
                    continue;
                }

                // adding the new vertex into the set
                mstSet.add(nextHouse);
                totalCost += cost;

                // expanding the candidates of edge to choose from in the next round
                for (Pair<Integer, Integer> neighborEdge : graph.get(nextHouse)) {
                    if (!mstSet.contains(neighborEdge.getValue())) {
                        edgesHeap.add(neighborEdge);
                    }
                }
            }

            return totalCost;
        }
    }

     */


    class UnionFind {
        /**
         * Implementation of UnionFind without load-balancing.
         */
        private int[] group;
        private int[] rank;

        public UnionFind(int size) {
            // container to hold the group id for each member
            // Note: the index of member starts from 1,
            //   thus we add one more element to the container.
            group = new int[size + 1];
            rank = new int[size + 1];
            for (int i = 0; i < size + 1; ++i) {
                group[i] = i;
                rank[i] = 0;
            }
        }

        /**
         * return the group id that the person belongs to.
         */
        public int find(int person) {
            if (group[person] != person) {
                group[person] = find(group[person]);
            }
            return group[person];
        }

        /**
         * Join the groups together.
         * return:
         * false when the two persons belong to the same group already,
         * otherwise true
         */
        public boolean union(int person1, int person2) {
            int group1 = find(person1);
            int group2 = find(person2);
            if (group1 == group2) {
                return false;
            }

            // attach the group of lower rank to the one with higher rank
            if (rank[group1] > rank[group2]) {
                group[group2] = group1;
            } else if (rank[group1] < rank[group2]) {
                group[group1] = group2;
            } else {
                group[group1] = group2;
                rank[group2] += 1;
            }

            return true;
        }
    }


    class Solution2 {
        public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
            List<int[]> orderedEdges = new ArrayList<>(n + 1 + pipes.length);

            // add the virtual vertex (index with 0) along with the new edges.
            for (int i = 0; i < wells.length; ++i) {
                orderedEdges.add(new int[]{0, i + 1, wells[i]});
            }

            // add the existing edges
            for (int i = 0; i < pipes.length; ++i) {
                int[] edge = pipes[i];
                orderedEdges.add(edge);
            }

            // sort the edges based on their cost
            Collections.sort(orderedEdges, (a, b) -> a[2] - b[2]);

            // iterate through the ordered edges
            UnionFind uf = new UnionFind(n);
            int totalCost = 0;
            for (int[] edge : orderedEdges) {
                int house1 = edge[0];
                int house2 = edge[1];
                int cost = edge[2];
                // determine if we should add the new edge to the final MST
                if (uf.union(house1, house2)) {
                    totalCost += cost;
                }
            }

            return totalCost;
        }
    }

    //disscusion
    //Intuition
    //I take it this way:
    //We cannot build any well.
    //There is one and only one hidding well in my house (house 0).
    //The cost to lay pipe between house[i] and my house is wells[i].
    //
    //In order to supply water to the whole village,
    //we need to make the village a coonected graph.
    //
    //
    //Explanation
    //Merge all costs of pipes together and sort by key.
    //Greedily lay the pipes if it can connect two seperate union.
    //Appply union find to record which houses are connected.
    //
    //
    //Complexity
    //Time O(ElogE)
    //Space O(N)

    int[] uf;
    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        uf = new int[n + 1];
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            uf[i + 1] = i + 1;
            edges.add(new int[] {0, i + 1, wells[i]});
        }
        for (int[] p : pipes) {
            edges.add(p);
        }
        Collections.sort(edges, (a, b) -> Integer.compare(a[2], b[2]));

        int res = 0;
        for (int[] e : edges) {
            int x = find(e[0]), y = find(e[1]);
            if (x != y) {
                res += e[2];
                uf[x] = y;
                --n;
            }
        }
        return res;
    }

    private int find(int x) {
        if (x != uf[x]) uf[x] = find(uf[x]);
        return uf[x];
    }



    //Java version. Gosh, this is so lengthy compared to Python. https://leetcode.com/problems/optimize-water-distribution-in-a-village/discuss/365853/C++PythonJava-Hidden-Well-in-House-0/330223
    class Solution {
        class UnionFind {
            int[] parent;

            public UnionFind(int n) {
                parent = new int[n];
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                }
            }

            public int find(int x) {
                if (x == parent[x]) {
                    return x;
                }

                parent[x] = find(parent[x]);
                return parent[x];
            }

            public void union(int x, int y) {
                int px = find(x), py = find(y);
                if (px == py) {
                    return;
                }

                parent[px] = py;
            }
        }

        public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
            UnionFind uf = new UnionFind(n + 1);

            List<int[]> edges = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                edges.add(new int[]{0, i + 1, wells[i]});
            }

            for (int[] pipe : pipes) {
                edges.add(pipe);
            }

            Collections.sort(edges, (a, b) -> Integer.compare(a[2], b[2]));

            int res = 0;
            for (int[] edge : edges) {
                int x = edge[0], y = edge[1];
                if (uf.find(x) == uf.find(y)) {
                    continue;
                }
                uf.union(x, y);
                res += edge[2];
            }

            return res;
        }
    }
}

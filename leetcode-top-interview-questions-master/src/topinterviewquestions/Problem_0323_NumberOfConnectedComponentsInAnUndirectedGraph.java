package topinterviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
You have a graph of n nodes. You are given an integer n and an array edges where edges[i] = [ai, bi] indicates that there is an edge between ai and bi in the graph.

Return the number of connected components in the graph.



Example 1:


Input: n = 5, edges = [[0,1],[1,2],[3,4]]
Output: 2
Example 2:


Input: n = 5, edges = [[0,1],[1,2],[2,3],[3,4]]
Output: 1


Constraints:

1 <= n <= 2000
1 <= edges.length <= 5000
edges[i].length == 2
0 <= ai <= bi < n
ai != bi
There are no repeated edges.
 */
public class Problem_0323_NumberOfConnectedComponentsInAnUndirectedGraph {
/*
Solution Article
Approach 1: Depth-First Search (DFS)
Intuition

If you're not familiar with DFS, check out our Explore Card.

In an undirected graph, a connected component is a subgraph in which each pair of vertices is connected via a path. So essentially, all vertices in a connected component are reachable from one another.

Let's see how we can use DFS to solve the problem. If we run DFS, starting from a particular vertex, it will continue to visit the vertices depth-wise until there are no more adjacent vertices left to visit. Thus, it will visit all of the vertices within the connected component that contains the starting vertex. Each time we finish exploring a connected component, we can find another vertex that has not been visited yet, and start a new DFS from there. The number of times we start a new DFS will be the number of connected components.

Here is an example illustrating this approach.

fig

Figure 1. An example demonstrating the DFS approach.

Algorithm

Create an adjacency list such that adj[v] contains all the adjacent vertices of vertex v.
Initialize a hashmap or array, visited, to track the visited vertices.
Define a counter variable and initialize it to zero.
Iterate over each vertex in edges, and if the vertex is not already in visited, start a DFS from it. Add every vertex visited during the DFS to visited.
Every time a new DFS starts, increment the counter variable by one.
At the end, the counter variable will contain the number of connected components in the undirected graph.

Complexity Analysis

Here EE = Number of edges, VV = Number of vertices.

Time complexity: {O}(E + V)O(E+V).

Building the adjacency list will take {O}(E)O(E) operations, as we iterate over the list of edges once, and insert each edge into two lists.

During the DFS traversal, each vertex will only be visited once. This is because we mark each vertex as visited as soon as we see it, and then we only visit vertices that are not marked as visited. In addition, when we iterate over the edge list of each vertex, we look at each edge once. This has a total cost of {O}(E + V)O(E+V).

Space complexity: {O}(E + V)O(E+V).

Building the adjacency list will take {O}(E)O(E) space. To keep track of visited vertices, an array of size {O}(V)O(V) is required. Also, the run-time stack for DFS will use {O}(V)O(V) space.

Approach 2: Disjoint Set Union (DSU)
Imagine we have a graph with N vertices and 0 edges. The number of connected components will be N in that graph.

fig

Let's now add the edge from vertex 1 to vertex 2. This will decrease the number of components by 1. This is because vertices 1 and 2 are now in the same component.

fig

When we then add the edge from vertex 2 to vertex 3, the number of components will decrease by 1 again.

fig

However, this pattern will not continue when we add the edge from vertex 1 to vertex 3. The number of components will not change because vertices 1, 2, and 3 are already in the same component.

fig

The above observation is the main intuition behind the DSU approach.

Algorithm

Initialize a variable count with the number of vertices in the input.
Traverse all of the edges one by one, performing the union-find method combine on each edge. If the endpoints are already in the same set, then keep traversing. If they are not, then decrement count by 1.
After traversing all of the edges, the variable count will contain the number of components in the graph.

Complexity Analysis

Here EE = Number of edges, VV = Number of vertices.

Time complexity: O(E\cdotα(n))O(E⋅α(n)).

Iterating over every edge requires O(E)O(E) operations, and for every operation, we are performing the combine method which is O(α(n))O(α(n)), where α(n) is the inverse Ackermann function.

Space complexity: O(V)O(V).

Storing the representative/immediate-parent of each vertex takes O(V)O(V) space. Furthermore, storing the size of components also takes O(V)O(V) space.


 */
class Solution1 {

	private void dfs(List<Integer>[] adjList, int[] visited, int startNode) {
		visited[startNode] = 1;

		for (int i = 0; i < adjList[startNode].size(); i++) {
			if (visited[adjList[startNode].get(i)] == 0) {
				dfs(adjList, visited, adjList[startNode].get(i));
			}
		}
	}

	public int countComponents(int n, int[][] edges) {
		int components = 0;
		int[] visited = new int[n];

		List<Integer>[] adjList = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < edges.length; i++) {
			adjList[edges[i][0]].add(edges[i][1]);
			adjList[edges[i][1]].add(edges[i][0]);
		}

		for (int i = 0; i < n; i++) {
			if (visited[i] == 0) {
				components++;
				dfs(adjList, visited, i);
			}
		}
		return components;
	}

}
	public class Solution2 {

		private int find(int[] representative, int vertex) {
			if (vertex == representative[vertex]) {
				return vertex;
			}

			return representative[vertex] = find(representative, representative[vertex]);
		}

		private int combine(int[] representative, int[] size, int vertex1, int vertex2) {
			vertex1 = find(representative, vertex1);
			vertex2 = find(representative, vertex2);

			if (vertex1 == vertex2) {
				return 0;
			} else {
				if (size[vertex1] > size[vertex2]) {
					size[vertex1] += size[vertex2];
					representative[vertex2] = vertex1;
				} else {
					size[vertex2] += size[vertex1];
					representative[vertex1] = vertex2;
				}
				return 1;
			}
		}

		public int countComponents(int n, int[][] edges) {
			int[] representative = new int[n];
			int[] size = new int[n];

			for (int i = 0; i < n; i++) {
				representative[i] = i;
				size[i] = 1;
			}

			int components = n;
			for (int i = 0; i < edges.length; i++) {
				components -= combine(representative, size, edges[i][0], edges[i][1]);
			}

			return components;
		}
	}


	//my submitted solution
	class Solution3 {
		public int countComponents(int n, int[][] edges) {
			UnionFind uf = new UnionFind(n);
			for (int[] edge : edges) {
				uf.union(edge[0], edge[1]);
			}
			return uf.sets;
		}

		public class UnionFind {
			int[] parents;
			int[] size;
			int[] helper;
			int sets;
			public UnionFind(int N) {
				parents = new int[N];
				size = new int[N];
				helper = new int[N];
				sets = N;
				for (int i = 0; i < N; i++) {
					size[i] = 1;
					parents[i] = i;
				}
			}
			public int find(int num) {
				int hi = 0;
				while (parents[num] != num) {
					helper[hi++] = num;
					num = parents[num];
				}
				for (hi--; hi >= 0; hi--) {
					parents[helper[hi]] = num;
				}
				return num;
			}

			public void union(int i, int j) {
				int p1 = find(i);
				int p2 = find(j);
				if (p1 != p2) {
					if (size[p1] > size[p2]) {
						size[p1] += size[p2];
						parents[p2] = p1;
					} else {
						size[p2] += size[p1];
						parents[p1] = p2;
					}
					sets--;
				}
			}
			public int sets() {
				return sets;
			}
		}
	}

	//discussion

	public int countComponents(int n, int[][] edges) {
		int[] roots = new int[n];
		for(int i = 0; i < n; i++) roots[i] = i;

		for(int[] e : edges) {
			int root1 = find(roots, e[0]);
			int root2 = find(roots, e[1]);
			if(root1 != root2) {
				roots[root1] = root2;  // union
				n--;
			}
		}
		return n;
	}

	public int find(int[] roots, int id) {
		while(roots[id] != id) {
			roots[id] = roots[roots[id]];  // optional: path compression
			id = roots[id];
		}
		return id;
	}


	//https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/discuss/516491/Java-Union-Find-DFS-BFS-Solutions-Complexity-Explain-Clean-code
	//[Java] Union-Find, DFS, BFS Solutions - Complexity Explain - Clean code
	//✔️ Solution 1a: Naive Union-Find ~ 3ms
	//Time: O(n*m), where m is the number of connections, n is the number of nodes.
	//Space: O(n)
	class Solution5_1 {
		public int countComponents(int n, int[][] edges) {
			int[] parent = new int[n];
			for (int i = 0; i < n; i++) parent[i] = i;
			int components = n;
			for (int[] e : edges) {
				int p1 = findParent(parent, e[0]);
				int p2 = findParent(parent, e[1]);
				if (p1 != p2) {
					parent[p1] = p2; // Union 2 component
					components--;
				}
			}
			return components;
		}
		private int findParent(int[] parent, int i) {
			while (i != parent[i]) i = parent[i];
			return i; // Without Path Compression
		}
	}

	//✔️ Solution 1b: Union-Find with Path Compression ~ 1ms
	//Complexity:
	//
	//Time: O(n+mlogn), where m is the number of connections, n is the number of nodes.
	//By doing Path Compression, it has been proven to achieve in O(logN) in each operations. Read more under https://cp-algorithms.com/data_structures/disjoint_set_union.html#toc-tgt-2
	//Space: O(n)
	class Solution5_2 {
		public int countComponents(int n, int[][] edges) {
			int[] parent = new int[n];
			for (int i = 0; i < n; i++) parent[i] = i;
			int components = n;
			for (int[] e : edges) {
				int p1 = findParent(parent, e[0]);
				int p2 = findParent(parent, e[1]);
				if (p1 != p2) {
					parent[p1] = p2; // Union 2 component
					components--;
				}
			}
			return components;
		}
		private int findParent(int[] parent, int i) {
			if (i == parent[i]) return i;
			return parent[i] = findParent(parent, parent[i]); // Path compression
		}
	}

	//✔️ Solution 1c: Union-Find with Path Compression and Union by Size ~ 1ms
	//Complexity:
	//
	//Time: O(n + m*α(n)) ≈ O(n + m), where m is the number of connections (union operations), n is the number of nodes.
	//Explanation: Using both path compression and union by size ensures that the amortized time per operation is only α(n), which is optimal, where α(n) is the inverse Ackermann function. This function has a value α(n) < 5 for any value of n that can be written in this physical universe, so the disjoint-set operations take place in essentially constant time.
	//Reference: https://en.wikipedia.org/wiki/Disjoint-set_data_structure or https://www.slideshare.net/WeiLi73/time-complexity-of-union-find-55858534 for more information.
	//
	//Space: O(n)
	class Solution5_3 {
		public int countComponents(int n, int[][] edges) {
			int[] parent = new int[n];
			int[] size = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = i;
				size[i] = 1;
			}
			int components = n;
			for (int[] e : edges) {
				int p1 = findParent(parent, e[0]);
				int p2 = findParent(parent, e[1]);
				if (p1 != p2) {
					if (size[p1] < size[p2]) { // Merge small size to large size
						size[p2] += size[p1];
						parent[p1] = p2;
					} else {
						size[p1] += size[p2];
						parent[p2] = p1;
					}
					components--;
				}
			}
			return components;
		}
		private int findParent(int[] parent, int i) {
			if (i == parent[i]) return i;
			return parent[i] = findParent(parent, parent[i]); // Path compression
		}
	}

/*
✔️ Solution 2: DFS ~ 3ms

class Solution {
    public int countComponents(int n, int[][] edges) {
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }
        int components = 0;
        boolean[] visited = new boolean[n];
        for (int v = 0; v < n; v++) components += dfs(v, graph, visited);
        return components;
    }
    int dfs(int u, List<Integer>[] graph, boolean[] visited) {
        if (visited[u]) return 0;
        visited[u] = true;
        for (int v : graph[u]) dfs(v, graph, visited);
        return 1;
    }
}
Complexity:

Time: O(n+m), where m is the number of connections, n is the number of nodes.
Space: O(n+m),
✔️ Solution 3: BFS ~ 3ms

class Solution {
    public int countComponents(int n, int[][] edges) {
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }
        int components = 0;
        boolean[] visited = new boolean[n];
        for (int v = 0; v < n; v++) components += bfs(v, graph, visited);
        return components;
    }
    int bfs(int src, List<Integer>[] graph, boolean[] visited) {
        if (visited[src]) return 0;
        visited[src] = true;
        Queue<Integer> q = new LinkedList<>();
        q.offer(src);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : graph[u]) {
                if (!visited[v]) {
                    visited[v] = true;
                    q.offer(v);
                }
            }
        }
        return 1;
    }
}
Complexity:

Time: O(n+m), where m is the number of connections, n is the number of nodes.
Space: O(n+m)
 */



}

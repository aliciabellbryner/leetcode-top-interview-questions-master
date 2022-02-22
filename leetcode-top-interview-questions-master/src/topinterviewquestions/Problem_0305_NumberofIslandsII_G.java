package topinterviewquestions;

import java.util.*;

/*
You are given an empty 2D binary grid grid of size m x n. The grid represents a map where 0's represent water and 1's represent land. Initially, all the cells of grid are water cells (i.e., all the cells are 0's).

We may perform an add land operation which turns the water at position into a land. You are given an array positions where positions[i] = [ri, ci] is the position (ri, ci) at which we should operate the ith operation.

Return an array of integers answer where answer[i] is the number of islands after turning the cell (ri, ci) into a land.

An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.



Example 1:


Input: m = 3, n = 3, positions = [[0,0],[0,1],[1,2],[2,1]]
Output: [1,1,2,3]
Explanation:
Initially, the 2d grid is filled with water.
- Operation #1: addLand(0, 0) turns the water at grid[0][0] into a land. We have 1 island.
- Operation #2: addLand(0, 1) turns the water at grid[0][1] into a land. We still have 1 island.
- Operation #3: addLand(1, 2) turns the water at grid[1][2] into a land. We have 2 islands.
- Operation #4: addLand(2, 1) turns the water at grid[2][1] into a land. We have 3 islands.
Example 2:

Input: m = 1, n = 1, positions = [[0,0]]
Output: [1]


Constraints:

1 <= m, n, positions.length <= 104
1 <= m * n <= 104
positions[i].length == 2
0 <= ri < m
0 <= ci < n


Follow up: Could you solve it in time complexity O(k log(mn)), where k == positions.length?
 */
public class Problem_0305_NumberofIslandsII_G {
    /*
    Approach #2: (Ad hoc) [Accepted]
Algorithm

Use a HashMap to map index of a land to its island_ID (starting from 0). For each addLand operation at position (row, col), check if its adjacent neighbors are in the HashMap or not and put the island_ID of identified neighbors into a set (where each element is unique):

if the set is empty, then the new land at position (row, col) forms a new island (monotonically increasing island_ID by 1);

if the set contains only one island_ID, then the new land belongs to an existing island and island_ID remains unchanged;

if the set contains more than one island_ID, then the new land bridges these separate islands into one island, we need to iterate through the HashMap to update this information (time consuming!) and decrease the number of island appropriately.


Complexity Analysis

Time complexity : O(L^2)O(L
2
 ), for each operation, we have to traverse the entire HashMap to update island id and the number of operations is LL.

Space complexity : O(L)O(L) for the HashMap.

P.S. C++ solution was accepted with 1409 ms runtime, but Java solution got an TLE (Time Limit Exceeded).

Approach #3: Union Find (aka Disjoint Set) [Accepted]
Intuition

Treat the 2d grid map as an undirected graph (formatted as adjacency matrix) and there is an edge between two horizontally or vertically adjacent nodes of value 1, then the problem reduces to finding the number of connected components in the graph after each addLand operation.

Algorithm

Make use of a Union Find data structure of size m*n to store all the nodes in the graph and initially each node's parent value is set to -1 to represent an empty graph. Our goal is to update Union Find with lands added by addLand operation and union lands belong to the same island.

For each addLand operation at position (row, col), union it with its adjacent neighbors if they belongs to some islands, if none of its neighbors belong to any islands, then initialize the new position as a new island (set parent value to itself) within Union Find.

For detailed description of Union Find (implemented with path compression and union by rank), you can refer to this article.

Current
1 / 15

Complexity Analysis

Time complexity : O(m \times n + L)O(m×n+L) where LL is the number of operations, mm is the number of rows and nn is the number of columns. it takes O(m \times n)O(m×n) to initialize UnionFind, and O(L)O(L) to process positions. Note that Union operation takes essentially constant time[1] when UnionFind is implemented with both path compression and union by rank.

Space complexity : O(m \times n)O(m×n) as required by UnionFind data structure.
     */



    class Solution2 {
        public List<Integer> numIslands2(int m, int n, int[][] positions) {
            List<Integer> ans = new ArrayList<>();
            HashMap<Integer, Integer> land2id = new HashMap<Integer, Integer>();
            int num_islands = 0;
            int island_id = 0;

            for (int[] pos : positions) {
                int r = pos[0], c = pos[1];
                Set<Integer> overlap = new HashSet<Integer>();

                if (r - 1 >= 0 && land2id.containsKey((r-1) * n + c)) {
                    overlap.add(land2id.get((r-1) * n + c));
                }
                if (r + 1 < m && land2id.containsKey((r+1) * n + c)) {
                    overlap.add(land2id.get((r+1) * n + c));
                }
                if (c - 1 >= 0 && land2id.containsKey(r * n + c - 1)) {
                    overlap.add(land2id.get(r * n + c - 1));
                }
                if (c + 1 < n && land2id.containsKey(r * n + c + 1)) {
                    overlap.add(land2id.get(r * n + c + 1));
                }

                if (overlap.isEmpty()) {
                    ++num_islands;
                    land2id.put(r * n + c, island_id++);
                } else if (overlap.size() == 1) {
                    land2id.put(r * n + c, overlap.iterator().next());
                } else {
                    int root_id = overlap.iterator().next();
                    for (Map.Entry<Integer, Integer> entry : land2id.entrySet()) {
                        int k = entry.getKey();
                        int id = entry.getValue();
                        if (overlap.contains(id)) {
                            land2id.put(k, root_id);
                        }
                    }
                    land2id.put(r * n + c, root_id);
                    num_islands -= (overlap.size() - 1);
                }
                ans.add(num_islands);
            }

            return ans;
        }
    }



    class Solution3 {
        class UnionFind {
            int count; // # of connected components
            int[] parent;
            int[] rank;

            public UnionFind(char[][] grid) { // for problem 200
                count = 0;
                int m = grid.length;
                int n = grid[0].length;
                parent = new int[m * n];
                rank = new int[m * n];
                for (int i = 0; i < m; ++i) {
                    for (int j = 0; j < n; ++j) {
                        if (grid[i][j] == '1') {
                            parent[i * n + j] = i * n + j;
                            ++count;
                        }
                        rank[i * n + j] = 0;
                    }
                }
            }

            public UnionFind(int N) { // for problem 305 and others
                count = 0;
                parent = new int[N];
                rank = new int[N];
                for (int i = 0; i < N; ++i) {
                    parent[i] = -1;
                    rank[i] = 0;
                }
            }

            public boolean isValid(int i) { // for problem 305
                return parent[i] >= 0;
            }

            public void setParent(int i) {
                parent[i] = i;
                ++count;
            }

            public int find(int i) { // path compression
                if (parent[i] != i) parent[i] = find(parent[i]);
                return parent[i];
            }

            public void union(int x, int y) { // union with rank
                int rootx = find(x);
                int rooty = find(y);
                if (rootx != rooty) {
                    if (rank[rootx] > rank[rooty]) {
                        parent[rooty] = rootx;
                    } else if (rank[rootx] < rank[rooty]) {
                        parent[rootx] = rooty;
                    } else {
                        parent[rooty] = rootx; rank[rootx] += 1;
                    }
                    --count;
                }
            }

            public int getCount() {
                return count;
            }
        }

        public List<Integer> numIslands2(int m, int n, int[][] positions) {
            List<Integer> ans = new ArrayList<>();
            UnionFind uf = new UnionFind(m * n);

            for (int[] pos : positions) {
                int r = pos[0], c = pos[1];
                List<Integer> overlap = new ArrayList<>();

                if (r - 1 >= 0 && uf.isValid((r-1) * n + c)) overlap.add((r-1) * n + c);
                if (r + 1 < m && uf.isValid((r+1) * n + c)) overlap.add((r+1) * n + c);
                if (c - 1 >= 0 && uf.isValid(r * n + c - 1)) overlap.add(r * n + c - 1);
                if (c + 1 < n && uf.isValid(r * n + c + 1)) overlap.add(r * n + c + 1);

                int index = r * n + c;
                uf.setParent(index);
                for (int i : overlap) uf.union(i, index);
                ans.add(uf.getCount());
            }

            return ans;
        }
    }

    //discussion
    //This is a basic union-find problem. Given a graph with points being added, we can at least solve:
    //
    //How many islands in total?
    //Which island is pointA belonging to?
    //Are pointA and pointB connected?
    //The idea is simple. To represent a list of islands, we use trees. i.e., a list of roots. This helps us find the identifier of an island faster. If roots[c] = p means the parent of node c is p, we can climb up the parent chain to find out the identifier of an island, i.e., which island this point belongs to:
    //
    //Do root[root[roots[c]]]... until root[c] == c;
    //To transform the two dimension problem into the classic UF, perform a linear mapping:
    //
    //int id = n * x + y;
    //Initially assume every cell are in non-island set {-1}. When point A is added, we create a new root, i.e., a new island. Then, check if any of its 4 neighbors belong to the same island. If not, union the neighbor by setting the root to be the same. Remember to skip non-island cells.
    //
    //UNION operation is only changing the root parent so the running time is O(1).
    //
    //FIND operation is proportional to the depth of the tree. If N is the number of points added, the average running time is O(logN), and a sequence of 4N operations take O(NlogN). If there is no balancing, the worse case could be O(N^2).
    //
    //Remember that one island could have different roots[node] value for each node. Because roots[node] is the parent of the node, not the highest root of the island. To find the actually root, we have to climb up the tree by calling findIsland function.
    //
    //Here I've attached my solution. There can be at least two improvements: union by rank & path compression. However I suggest first finish the basis, then discuss the improvements.
    //
    //Cheers!

    public class Solution4 {
        int[][] dirs = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

        public List<Integer> numIslands2(int m, int n, int[][] positions) {
            List<Integer> result = new ArrayList<>();
            if(m <= 0 || n <= 0) return result;

            int count = 0;                      // number of islands
            int[] roots = new int[m * n];       // one island = one tree
            Arrays.fill(roots, -1);

            for(int[] p : positions) {
                int root = n * p[0] + p[1];     // assume new point is isolated island
                roots[root] = root;             // add new island
                count++;

                for(int[] dir : dirs) {
                    int x = p[0] + dir[0];
                    int y = p[1] + dir[1];
                    int nb = n * x + y;
                    if(x < 0 || x >= m || y < 0 || y >= n || roots[nb] == -1) continue;

                    int rootNb = findIsland(roots, nb);
                    if(root != rootNb) {        // if neighbor is in another island
                        roots[root] = rootNb;   // union two islands
                        root = rootNb;          // current tree root = joined tree root
                        count--;
                    }
                }

                result.add(count);
            }
            return result;
        }

        public int findIsland(int[] roots, int id) {
            while(id != roots[id]) id = roots[id];
            return id;
        }
    }
/*
Path Compression (Bonus)
If you have time, add one line to shorten the tree. The new runtime becomes: 19ms (95.94%).

public int findIsland(int[] roots, int id) {
    while(id != roots[id]) {
        roots[id] = roots[roots[id]];   // only one line added
        id = roots[id];
    }
    return id;
}
 */

}

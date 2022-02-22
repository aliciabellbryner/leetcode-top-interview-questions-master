package topinterviewquestions;

import java.util.LinkedList;
import java.util.Queue;

/*
You are given an m x n grid grid of values 0, 1, or 2, where:

each 0 marks an empty land that you can pass by freely,
each 1 marks a building that you cannot pass through, and
each 2 marks an obstacle that you cannot pass through.
You want to build a house on an empty land that reaches all buildings in the shortest total travel distance. You can only move up, down, left, and right.

Return the shortest travel distance for such a house. If it is not possible to build such a house according to the above rules, return -1.

The total travel distance is the sum of the distances between the houses of the friends and the meeting point.

The distance is calculated using Manhattan Distance, where distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.



Example 1:


Input: grid = [[1,0,2,0,1],[0,0,0,0,0],[0,0,1,0,0]]
Output: 7
Explanation: Given three buildings at (0,0), (0,4), (2,2), and an obstacle at (0,2).
The point (1,2) is an ideal empty land to build a house, as the total travel distance of 3+3+1=7 is minimal.
So return 7.
Example 2:

Input: grid = [[1,0]]
Output: 1
Example 3:

Input: grid = [[1]]
Output: -1


Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 50
grid[i][j] is either 0, 1, or 2.
There will be at least one building in the grid
 */
public class Problem_0317_ShortestDistancefromAllBuildings_G {
    /*
    Solution
Overview
We are asked to build a house with the shortest total travel distance to all other houses. To do so, we must be able to find the shortest distance between two cells (one empty land and a house). This process will then be repeated for every pair of empty land and house. As such, an intuitive first step is to record the location of all houses (1-valued cells) and empty land (0-valued cells) in separate vectors.

Then, for each empty land, traverse all houses, and add the Manhattan Distance:
distance = |x1 - x2| + |y1 - y2|, where (x1, y1) are the coordinates of empty land and (x2, y2) are the coordinates of a house.

But the problem here is that we have some blocked cells. For example, in the below configuration the formula does not give the correct distance between P1 and P2. This is because there are obstacles between the two points.

blocked paths


Since obstacles preclude us from using the formula, we will instead perform a level-wise Breadth-First Search (BFS) for each cell, where each level is 1 distance further away from the starting cell (traversing 4-directionally). As we expand our Breadth-First Search, we will not visit any cell that is blocked or any cell that has already been visited.


Why did we choose to use BFS?

Our graph is not weighted. We can consider each edge to have the same weight of 1. Since the graph is unweighted, BFS can be used to find the shortest path between a starting cell and any other reachable cell.

The actual distance calculation measures grid distance in terms of only horizontal and vertical movements. Since we can only move up, down, left, and right, we can apply BFS to calculate the actual distance. At each iteration in the BFS, we will only consider expanding our search in the horizontal or vertical direction.


Approach 1: BFS from Empty Land to All Houses
Intuition

Our goal is to find the empty land cell with the shortest total distance to all houses, so we must first find the shortest total distance to all houses from each empty land cell. As previously mentioned, this can be accomplished using BFS. For each empty cell (cell value equals 0) in the grid, start a BFS and sum all the distances to houses (cell value equals 1) from this cell. We will also keep track of the number of houses we have reached from this source cell (empty cell). If we cannot reach all the houses from the current empty cell, then it is not a valid empty cell. Furthermore, we can be certain that any cell visited during this BFS also cannot reach all of the houses. So we will mark all cells visited during this BFS as obstacles to ensure that we do not start another BFS from this region.

Algorithm

For each empty cell (grid[i][j] equals 0), start a BFS:
In the BFS, traverse all 4-directionally adjacent cells that are not blocked or visited and keep track of the distance from the start cell. Each iteration adds 1 to the distance.
Every time we reach a house, increment houses reached counter housesReached by 1, and increase the total distance distanceSum by the current distance (i.e., the distance from the start cell to the house).
If housesReached equals totalHouses, then return the total distance.
Otherwise, the starting cell (and every cell visited during this BFS) cannot reach all of the houses. So set every visited empty land cell equal to 2 so that we do not start a new BFS from that cell and return INT_MAX.
Each time a total distance is returned from a BFS call, update the minimum distance (minDistance).
If it is possible to reach all houses from any empty land cell, then return the minimum distance found. Otherwise, return -1.
Current
1 / 30

Implementation


Complexity Analysis

Let NN and MM be the number of rows and columns in grid respectively.

Time Complexity: O(N^2 \cdot M^2)O(N
2
 ⋅M
2
 )

For each empty land, we will traverse to all other houses. This will require O(O(number of zeros \cdot⋅ number of ones)) time and the number of zeros and ones in the matrix is of order N \cdot MN⋅M. Consider that when half of the values in grid are 0 and half of the values are 1, the total elements in grid would be (M \cdot N)(M⋅N) so their counts are (M \cdot N)/2(M⋅N)/2 and (M \cdot N)/2(M⋅N)/2 respectively, thus giving time complexity (M \cdot N)/2 \cdot (M \cdot N)/2(M⋅N)/2⋅(M⋅N)/2, that results in O(N^2 \cdot M^2)O(N
2
 ⋅M
2
 ).

In JavaScript implementation, for simplicity, we have used an array for the queue. Since popping elements from the front of an array is an O(n) operation, which is undesirable, instead of popping from the front of the queue, we will iterate over the queue and record cells to be explored in the next level in next_queue. Once the current queue has been traversed, we simply set queue equal to the next_queue.

Space Complexity: O(N \cdot M)O(N⋅M)

We use an extra matrix to track the visited cells, and the queue will store each matrix element at most once during each BFS call. Hence, O(N \cdot M)O(N⋅M) space is required.


Approach 2: BFS from Houses to Empty Land
Intuition

In the previous approach, to get the minimum distance we started a BFS from each empty land (cell value equal to 0) to all the houses (cell value equal to 1), but another way to look at the problem is starting from a house and finding all reachable empty lands.

If we can reach a house from an empty land, then we can also traverse the other way (i.e., reach empty land from a house).

When there are fewer houses than empty lands, then this approach will require less time than the previous approach and vice versa. While, on average, this approach is not an improvement on the previous approach, it will serve as a mental stepping stone to better understand the third approach.

Previously, we were calculating the total minimum distance sum of one empty cell in one BFS traversal, hence we were only returning the distance sum from the BFS function for each cell. But if we start BFS from a house instead of an empty cell, we will be generating partial distance (i.e., distance from only one house to the current cell and not the sum distance from all the houses to this empty cell), hence we need some extra space to store the sum of the partial distances from each house cell.

We will need to store 2 values at each cell position of empty cells: total distance sum from all houses to this empty land and number of houses that can reach this empty land.

Algorithm

For each house cell (grid[i][j] equals 1), start a BFS:
For each empty cell we reach, increase the cell's sum of distances by the steps taken to reach the cell.
For each empty cell we reach, also increment the cell's house counter by 1.
After traversing all houses, get the minimum distance from all empty cells which have housesReached equal to totalHouses.
If it is possible for all houses to reach a specific empty land cell, then return the minimum distance found. Otherwise, return -1.

Implementation


Complexity Analysis

Let NN and MM be the number of rows and columns in grid respectively.

Time Complexity: O(N^2 \cdot M^2)O(N
2
 ⋅M
2
 )

For each house, we will traverse across all reachable land. This will require O(O(number of zeros \cdot⋅ number of ones)) time and the number of zeros and ones in the matrix is of order N \cdot MN⋅M. Consider that when half of the values in grid are 0 and half of the values are 1, total elements in grid will be (M \cdot N)(M⋅N) so their counts are (M \cdot N)/2(M⋅N)/2 and (M \cdot N)/2(M⋅N)/2 respectively, thus giving time complexity (M \cdot N)/2 \cdot (M \cdot N)/2(M⋅N)/2⋅(M⋅N)/2, which results in O(N^2 \cdot M^2)O(N
2
 ⋅M
2
 ).

In JavaScript implementation, for simplicity, we have used an array for the queue. However, popping elements from the front of an array is an O(n) operation, which is undesirable. So, instead of popping from the front of the queue, we will iterate over the queue and record cells to be explored in the next level in next_queue. Once the current queue has been traversed, we simply set queue equal to the next_queue.

Space Complexity: O(N \cdot M)O(N⋅M)

We use an extra matrix to track the visited cells and another one to store distance sum along with the house counter for each empty cell, and the queue will store each matrix element at most once during each BFS call. Hence, O(N \cdot M)O(N⋅M) space is required.


Approach 3: BFS from Houses to Empty Land (Optimized)
Intuition

Instead of making a new matrix each time to track the visited cells, we can use the input grid matrix for this purpose. During the first BFS we can change the visited empty land values from 0 to -1. Then during the next BFS we will only visit empty lands with a value of -1s (meaning they can reach the first house), then change -1 to -2 and then perform the next BFS only on -2s, and so on...

This approach allows us to avoid visiting any cell that cannot reach all houses.

Can we also use this decrement in empty land value to denote that the cell has been visited?

Imagine we are currently at cell (2, 3) with value -1 and we change its value to -2.
In the queue the next element is (2, 4), its value is also -1 and we change its value to -2. While exploring paths from (2, 4), we see that the cell (2, 3) is adjacent, and has the value -2. However, currently, we are checking for -1 valued cells only. Hence, (2, 3) will not be inserted again in the queue, so this decrease in value can be used as a visited cell check because when a cell has been visited, then its value will be 1 less and it will not satisfy the condition to be inserted in the queue.

If there was an empty land cell that was not reachable in the previous iteration, then its value has not been decreased, hence we will not insert that cell in the queue when we start a BFS from another house cell.
Therefore, this approach prunes many iterations and saves some time since we are not creating a new matrix to track visited cells for each BFS call.

Current
1 / 3

Algorithm

For each grid[i][j] that is equal to 1, start a BFS. During the BFS:
All 4-directionally adjacent grid cells with values equal to emptyLandValue are valid.
Visit them one by one and store the distances of these cells from the source in a total matrix.
Decrease the value of visited cells by 1.
After each BFS traversal, decrement emptyLandValue by 1.
Before we start a BFS call for a house, we set minDist equal to INT_MAX.
Then we will traverse all of the empty land cells with values equal to emptyLandValue
After the last BFS traversal, if the minimum distance equals INT_MAX, then there has not been any cell that can be reached by all the houses, so return -1.
Otherwise, return the minimum distance minDist.
Current
1 / 18

Implementation


Complexity Analysis

Let NN and MM be the number of rows and columns in grid respectively.

Time Complexity: O(N^2 \cdot M^2)O(N
2
 ⋅M
2
 )

For each house, we will traverse across all reachable land. This will require O(O(number of zeros \cdot⋅ number of ones)) time and the number of zeros and ones in the matrix is of order N \cdot MN⋅M. Consider that when half of the values in grid are 0 and half of the values are 1, total elements in grid would be (M \cdot N)(M⋅N) so their counts are (M \cdot N)/2(M⋅N)/2 and (M \cdot N)/2(M⋅N)/2 respectively, thus giving time complexity (M \cdot N)/2 \cdot (M \cdot N)/2(M⋅N)/2⋅(M⋅N)/2, that results in O(N^2 \cdot M^2)O(N
2
 ⋅M
2
 ).

Space Complexity: O(N \cdot M)O(N⋅M)

We use an extra matrix to store distance sums, and the queue will store each matrix element at most once during each BFS call. Hence, O(N \cdot M)O(N⋅M) space is required.
     */
    class Solution1 {
        private int bfs(int[][] grid, int row, int col, int totalHouses) {
            // Next four directions.
            int dirs[][] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            int rows = grid.length;
            int cols = grid[0].length;
            int distanceSum = 0;
            int housesReached = 0;

            // Queue to do a bfs, starting from (row, col) cell.
            Queue<int[]> q = new LinkedList<>();
            q.offer(new int[]{ row, col });

            // Keep track of visited cells.
            boolean[][] vis = new boolean[rows][cols];
            vis[row][col] = true;

            int steps = 0;
            while (!q.isEmpty() && housesReached != totalHouses) {
                for (int i = q.size(); i > 0; --i) {
                    int[] curr = q.poll();
                    row = curr[0];
                    col = curr[1];

                    // If this cell is a house, then add the distance from source to this cell
                    // and we go past from this cell.
                    if (grid[row][col] == 1) {
                        distanceSum += steps;
                        housesReached++;
                        continue;
                    }

                    // This cell was empty cell, hence traverse the next cells which is not a blockage.
                    for (int[] dir : dirs) {
                        int nextRow = row + dir[0];
                        int nextCol = col + dir[1];
                        if (nextRow >= 0 && nextCol >= 0 && nextRow < rows && nextCol < cols) {
                            if (!vis[nextRow][nextCol] && grid[nextRow][nextCol] != 2) {
                                vis[nextRow][nextCol] = true;
                                q.offer(new int[]{ nextRow, nextCol });
                            }
                        }
                    }
                }

                // After traversing one level of cells, increment the steps by 1 to reach to next level.
                steps++;
            }

            // If we did not reach all houses, then any cell visited also cannot reach all houses.
            // Set all cells visted to 2 so we do not check them again and return MAX_VALUE.
            if (housesReached != totalHouses) {
                for (row = 0; row < rows; row++) {
                    for (col = 0; col < cols; col++) {
                        if (grid[row][col] == 0 && vis[row][col]) {
                            grid[row][col] = 2;
                        }
                    }
                }
                return Integer.MAX_VALUE;
            }

            // If we have reached all houses then return the total distance calculated.
            return distanceSum;
        }

        public int shortestDistance(int[][] grid) {
            int minDistance = Integer.MAX_VALUE;
            int rows = grid.length;
            int cols = grid[0].length;
            int totalHouses = 0;

            for (int row = 0; row < rows; ++row) {
                for (int col = 0; col < cols; ++col) {
                    if (grid[row][col] == 1) {
                        totalHouses++;
                    }
                }
            }

            // Find the min distance sum for each empty cell.
            for (int row = 0; row < rows; ++row) {
                for (int col = 0; col < cols; ++col) {
                    if (grid[row][col] == 0) {
                        minDistance = Math.min(minDistance, bfs(grid, row, col, totalHouses));
                    }
                }
            }

            // If it is impossible to reach all houses from any empty cell, then return -1.
            if (minDistance == Integer.MAX_VALUE) {
                return -1;
            }

            return minDistance;
        }
    };




    class Solution2 {
        private void bfs(int[][] grid, int[][][] distances, int row, int col) {
            int dirs[][] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            int rows = grid.length;
            int cols = grid[0].length;

            // Use a queue to do a bfs, starting from each cell located at (row, col).
            Queue<int[]> q = new LinkedList<>();
            q.offer(new int[]{ row, col });

            // Keep track of visited cells.
            boolean[][] vis = new boolean[rows][cols];
            vis[row][col] = true;

            int steps = 0;

            while (!q.isEmpty()) {
                for (int i = q.size(); i > 0; --i) {
                    int[] curr = q.poll();
                    row = curr[0];
                    col = curr[1];

                    // If we reached an empty cell, then add the distance
                    // and increment the count of houses reached at this cell.
                    if (grid[row][col] == 0) {
                        distances[row][col][0] += steps;
                        distances[row][col][1] += 1;
                    }

                    // Traverse the next cells which is not a blockage.
                    for (int[] dir : dirs) {
                        int nextRow = row + dir[0];
                        int nextCol = col + dir[1];

                        if (nextRow >= 0 && nextCol >= 0 && nextRow < rows && nextCol < cols) {
                            if (!vis[nextRow][nextCol] && grid[nextRow][nextCol] == 0) {
                                vis[nextRow][nextCol] = true;
                                q.offer(new int[]{ nextRow, nextCol });
                            }
                        }
                    }
                }

                // After traversing one level cells, increment the steps by 1.
                steps++;
            }
        }

        public int shortestDistance(int[][] grid) {
            int minDistance = Integer.MAX_VALUE;
            int rows = grid.length;
            int cols = grid[0].length;
            int totalHouses = 0;

            // Store { total_dist, houses_count } for each cell.
            int[][][] distances = new int[rows][cols][2];

            // Count houses and start bfs from each house.
            for (int row = 0; row < rows; ++row) {
                for (int col = 0; col < cols; ++col) {
                    if (grid[row][col] == 1) {
                        totalHouses++;
                        bfs(grid, distances, row, col);
                    }
                }
            }

            // Check all empty lands with houses count equal to total houses and find the min ans.
            for (int row = 0; row < rows; ++row) {
                for (int col = 0; col < cols; ++col) {
                    if (distances[row][col][1] == totalHouses) {
                        minDistance = Math.min(minDistance, distances[row][col][0]);
                    }
                }
            }

            // If we haven't found a valid cell return -1.
            if (minDistance == Integer.MAX_VALUE) {
                return -1;
            }
            return minDistance;
        }
    };

    class Solution3 {
        public int shortestDistance(int[][] grid) {
            // Next four directions.
            int dirs[][] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            int rows = grid.length;
            int cols = grid[0].length;

            // Total Mtrix to store total distance sum for each empty cell.
            int[][] total = new int[rows][cols];

            int emptyLandValue = 0;
            int minDist = Integer.MAX_VALUE;

            for (int row = 0; row < rows; ++row) {
                for (int col = 0; col < cols; ++col) {

                    // Start a BFS from each house.
                    if (grid[row][col] == 1) {
                        minDist = Integer.MAX_VALUE;

                        // Use a queue to perform a BFS, starting from the cell at (r, c).
                        Queue<int[]> q = new LinkedList<>();
                        q.offer(new int[]{ row, col });

                        int steps = 0;

                        while (!q.isEmpty()) {
                            steps++;

                            for (int level = q.size(); level > 0; --level) {
                                int[] curr = q.poll();

                                for (int[] dir : dirs) {
                                    int nextRow = curr[0] + dir[0];
                                    int nextCol = curr[1] + dir[1];

                                    // For each cell with the value equal to empty land value
                                    // add distance and decrement the cell value by 1.
                                    if (nextRow >= 0 && nextRow < rows &&
                                            nextCol >= 0 && nextCol < cols &&
                                            grid[nextRow][nextCol] == emptyLandValue) {
                                        grid[nextRow][nextCol]--;
                                        total[nextRow][nextCol] += steps;

                                        q.offer(new int[]{ nextRow, nextCol });
                                        minDist = Math.min(minDist, total[nextRow][nextCol]);
                                    }
                                }
                            }
                        }

                        // Decrement empty land value to be searched in next iteration.
                        emptyLandValue--;
                    }
                }
            }

            return minDist == Integer.MAX_VALUE ? -1 : minDist;
        }
    }



    //discussion
    /**
     * Step 1: start from every point 1 (building point), doing BFS traversal to fill out (or accumulate) distance array
     * (MUST start over and doing every BFS traversal individually)
     *
     * Step 2: find minimum distance from dp array
     *
     * @param dp: store distance sum to all building for every point 0. Update values when we do BFS traversal
     * @param reach: store number of buildings that every point 0 can reach. Used for checking if current point is valid
     *             while we want to find final result
     * @param countBuilding: count total number of point 1
     * */
    final int[][] dir = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public int shortestDistance(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] dp = new int[n][m];
        int[][] reach = new int[n][m];
        int countBuilding = 0;
        Queue<int[]> queue = new LinkedList<>();

        // step 1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    queue.offer(new int[]{i, j});
                    bfs(queue, grid, dp, reach, n, m);
                    countBuilding++;
                }
            }
        }

        // step 2
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // WARNING: DO NOT FORGET to check whether current point is 0 and whether current point can reach all buildings
                if (grid[i][j] == 0 && reach[i][j] == countBuilding) {
                    result = Math.min(result, dp[i][j]);
                }
            }
        }
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    public void bfs(Queue<int[]> queue, int[][] grid, int[][] dp, int[][] reach, int n, int m) {
        int level = 1;
        // DO NOT USE hashset, since hashset cannot determine whether it contains an array (coordinate)
        boolean[][] visited = new boolean[n][m];
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curPoint = queue.poll();
                int x = curPoint[0];
                int y = curPoint[1];
                for (int j = 0; j < 4; j++) {
                    int dx = x + dir[j][0];
                    int dy = y + dir[j][1];
                    if (dx < 0 || dx > n - 1 || dy < 0 || dy > m - 1 || grid[dx][dy] != 0 || visited[dx][dy]) {
                        continue;
                    }
                    queue.offer(new int[]{dx, dy});
                    visited[dx][dy] = true;
                    dp[dx][dy] += level;
                    reach[dx][dy]++;
                }
            }
            level++;
        }
    }


}

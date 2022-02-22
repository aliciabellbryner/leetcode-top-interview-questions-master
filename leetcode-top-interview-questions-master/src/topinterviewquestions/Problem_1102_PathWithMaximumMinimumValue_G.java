package topinterviewquestions;

import java.util.*;

/*
Given an m x n integer matrix grid, return the maximum score of a path starting at (0, 0) and ending at (m - 1, n - 1) moving in the 4 cardinal directions.

The score of a path is the minimum value in that path.

For example, the score of the path 8 → 4 → 5 → 9 is 4.


Example 1:


Input: grid = [[5,4,5],[1,2,6],[7,4,6]]
Output: 4
Explanation: The path with the maximum score is highlighted in yellow.
Example 2:


Input: grid = [[2,2,1,2,2,2],[1,2,2,2,1,2]]
Output: 2
Example 3:


Input: grid = [[3,4,6,3,4],[0,2,1,1,7],[8,8,3,2,7],[3,2,4,9,8],[4,1,2,0,0],[4,6,5,4,3]]
Output: 3


Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 100
0 <= grid[i][j] <= 109
 */
public class Problem_1102_PathWithMaximumMinimumValue_G {
/*
Solution
Overview
In this problem, we are given a 2D integer matrix. Our goal is to pick a path that starts from the top-left cell and ends at the bottom-right cell. As described in the problem, the score of a path is defined by the minimum value in this path.

For example, in the matrix below, the path colored in green has values of [5, 1, 7, 4, 6][5,1,7,4,6], so the score of the green path is the minimum value 11. The path colored in red has values of [5, 4, 5, 6, 6][5,4,5,6,6], so the score of the red path is the minimum value 44. We can tell that the score of the red path is larger than that of the green path.

limits

Here our task is to find the maximum score among all possible paths in the given matrix.

Approach 1: Iteration + BFS
Intuition

Before getting lost in designing a function to find the optimal path, let's try thinking about this problem from another perspective!

Given a number S, can we find a path from the matrix having score equals S?

Recall the previous picture:

limits

Given S = 100, can we find a path with a minimum value that equals 100? The answer is definitely No. Given S = 4, can we find a path with a minimum value that equals 4? The answer is Yes (the red path, for example).

More specifically, we could start with a large number S (large enough so that we can't find a path with a score more than S), then we check if there is a path with a score equal to S. If so, we have found the maximum, minimum value of all the paths. Otherwise, it means that the current S is too large, and we should search for a smaller value. Since we don't want to miss any possible value, we will just decrement S by 1. That is, we continue to check if there is a path with the value of S - 1.

limits

In this way, we can convert the optimization problem into a decision problem: We don't need to worry about how to plan the path, but just verify if such a path exists.

Then this problem is divided into two steps:

We traverse decreasingly from a large starting score S.
For each S, we check if a path with a score equal to Sexists.
How to access the largest score?
As shown in the figure above, in order not to miss any possible minimum value, we will start with a large value SS, which is the largest possible minimum value in any path. For example, we can use the largest value in the matrix and then try to find if there exists a path with a score of maxValmaxVal. If we can't find such a path, we decrease maxValmaxVal by 1 and repeat this process until we find a path with maxValmaxVal as the score.

Given a score S, how can we verify if a path with this score exists?
To verify if such a path exists, we could use breath-first-search (BFS) on the matrix, where we only explore cells with a value greater than or equal to S.

For more information on BFS please refer to: The Breadth-First Search Algorithm

Current
1 / 16
One trick here is that we don't need to start with a huge score S, which might result in many unnecessary calculations before reaching the maximum score. Instead, we can start from the smaller value between the top-left cell and the bottom-right cell. The reason is that our path must include these two points, so the score of a path must be less than or equal to the smaller value of these two points. Therefore, by choosing the smaller value from the above two cells as the starting score S, we can reduce the search space.


Algorithm

Start with the score curScore = min(grid[0][0], grid[R - 1][C - 1])curScore=min(grid[0][0],grid[R−1][C−1]), where RR and CC are the total number of rows and columns of the input grid.
Perform BFS on the matrix and verify if a path exists where all values are greater than or equal to curScorecurScore:
Use a deque to store all unvisited cells with a value greater than or equal to curScorecurScore.
Pop a cell from the front of the deque, check if it has unvisited neighboring cells, and add them to the back of the deque.
If we reach the bottom-right cell successfully, then a path exists.
Otherwise, if we empty the deque before reaching the bottom-right cell, the path doesn't exist.
If there is no such path, meaning curScorecurScore is too large, we decrease it by 1 and repeat from the step 2.
Otherwise return curScorecurScore as the answer.
Implementation

Note: The following implementation is included because it is an intuitive first approach and it helps lay the foundation needed to understand the following optimized approach. However, it is a brute force approach and is not expected to pass all test cases.


Complexity Analysis

Let n, mn,m be the dimensions of the input matrix gridgrid and kk be the largest possible value in the matrix.

Time complexity: O(n\cdot m\cdot k)O(n⋅m⋅k)

For each value, we BFS over the matrix to verify if a valid path exists, which takes O(n \cdot m)O(n⋅m) time.

Before finding the first (the largest) value, we must try every larger value. Suppose in the worst-case scenario (when the answer is close to 11), we have to try every value from kk to 11, that is a total of kk iterations and kk BFS over the matrix.

To sum up, the overall time complexity is O(n\cdot m\cdot k)O(n⋅m⋅k)

Space complexity: O(n \cdot m)O(n⋅m)

In the worst-case scenario, we need to hold n \cdot mn⋅m cells in the deque, which takes O(n \cdot m)O(n⋅m) space for a single BFS.
We also need an auxiliary data structure to keep track of the visited status of n \cdot mn⋅m cells in each BFS.
Therefore, the overall space complexity is O(n \cdot m)O(n⋅m).

Approach 2: Binary Search + Breadth First Search (BFS)
Intuition

In the previous approach using brute force, we checked every larger score before finding out the maximum score, can we find a more efficient way to locate the maximum score?

The answer is Yes!

limits

Recall the defination of the score of a path and we can observe two laws:

If we can find a path with the score of NN, we can also find a path with the score of N - 1N−1. For a path where all values are greater than or equal to NN, these values are also greater than or equal to N - 1N−1.

If we can't find a path with the score of NN, then we can't find a path with the score of N + 1N+1 either. If we can't find a path with the score of NN, meaning all paths contain at least one value less than NN, this value is also less than N + 1N+1, therefore, all paths contain at least one value less than N + 1N+1.

limits

For convenience, let's call a score of a path as workable score and those values being too large in every path as unworkable score.

Given these two laws, we can determine that the distribution of all the scores look like this:

limits

If the current value is a workable score, the maximum workable score should be on its right inclusively. If the current value is an unworkable score, then the maximum workable score should be on its left exclusively.

Therefore, we can use binary search to cut off the search space by half in each step and locate the boundary that separates workable scores and the unworkable scores, which represents the maximum score of all the paths.

There are many other interesting problems that can be solved by performing a binary search to find the optimal value. You can practice using the binary search approach on the following problems! (click to show)

Algorithm

Initialize two boundaries of the binary search,
While left < rightleft<right, let middle = (left + right + 1) / 2middle=(left+right+1)/2.
Perform BFS on the matrix and verify if a path exists where all values are greater than or equal to curScorecurScore:
Use a deque to store all unvisited cells with a value greater than or equal to curScorecurScore.
Pop a cell from the front of the deque, check if it has unvisited neighboring cells, and add them to the back of the deque.
If we reach the bottom-right cell successfully, then a path exists.
Otherwise, if we empty the deque before reaching the bottom-right cell, the path doesn't exist.
If a path exists, let middle = leftmiddle=left. Otherwise, let right = middle - 1right=middle−1.
Repeat steps 2, 3, and 4 until the two boundaries overlap, then return either leftleft or rightright as the answer.
Implementation


Complexity Analysis

Let n, mn,m be the dimensions of the input matrix gridgrid and kk be the largest value in the matrix.

Time complexity: O(n\cdot m\cdot \log k)O(n⋅m⋅logk)

The initial search space is from 11 to kk, it takes \log klogk comparisons to reduce the search space to 1.

For each value, we perform a BFS over the matrix to verify if a valid path exists, which takes O(n \cdot m)O(n⋅m) time.

To sum up, the overall time complexity is O(n\cdot m\cdot \log k)O(n⋅m⋅logk)

Space complexity: O(n \cdot m)O(n⋅m)

We used an array of size O(n\cdot m)O(n⋅m) to save the visited/unvisited status of each cell.
In the worse-case scenario, we need to hold most cells in the deque, which takes O(n \cdot m)O(n⋅m) space for a single BFS.
Therefore, the overall space complexity is O(n \cdot m)O(n⋅m).

Approach 3: Binary Search + Depth First Search (DFS)
Intuition

As we discussed in approach 2, we used binary search to locate the largest minimum value. Here, we inherit part of the previous algorithm: we still use binary search to narrow down the search space and locate the maximum value. However, we will use a different approach to verify if a valid path exists given the target score.

For more information on DFS please refer to: The Depth First Search Algorithm

Current
1 / 9
Algorithm

Initialize two boundaries of the binary search, let left boundary be left = 0left=0, let right boundary be right = min(grid[0][0], grid[R - 1][C - 1])right=min(grid[0][0],grid[R−1][C−1]), where R, CR,C are the number of rows and columns of the input cell.
While left < rightleft<right, let middle = (left + right + 1) / 2middle=(left+right+1)/2.
Perform a DFS on the graph to find if a path where each value is greater than or equal to middlemiddle exists.
If a path exists, let middle = leftmiddle=left. Otherwise, let right = middle - 1right=middle−1.
Repeat steps 2, 3, and 4 until the upper and lower boundary overlap, then return either leftleft or rightright as the answer.
Implementation


Complexity Analysis

Let n, mn,m be the dimensions of the input matrix gridgrid and kk be the largest value in the matrix.

Time complexity: O(n\cdot m\cdot \log k)O(n⋅m⋅logk)

The initial search space is from 11 to kk, it takes \log klogk comparisons to reduce the search space to 1.
For each value, we perform a DFS over the matrix to verify if a valid path exists, which takes O(n \cdot m)O(n⋅m) time.
To sum up, the overall time complexity is O(n\cdot m\cdot \log k)O(n⋅m⋅logk)
Space complexity: O(n \cdot m)O(n⋅m)

We used an array of size O(n\cdot m)O(n⋅m) to save the visited/unvisited status of each cell.
In the worse-case scenario, we need to traverse the entire matrix, which takes O(n \cdot m)O(n⋅m) space for a single DFS.
Therefore, the overall space complexity is O(n \cdot m)O(n⋅m).

Approach 4: BFS + PriorityQueue
Intuition

Suppose we start from the top-left cell, check its two neighbors, and then visit the neighboring cell with the larger value. We can imagine that this newly visited cell will have other neighboring cells. Once again, we can consider all cells that neighbor the two visited cells and then visit the cell with the largest value. We can repeat these steps until we reach the bottom-right cell. Now we have a path of visited cells that connects the top-left cell to the bottom-right cell. Since, at each step, we always picked the unvisited neighbor with the largest value, it is guaranteed that the smallest value seen so far is the largest possible minimum value (the largest score) in a valid path.

However, here's the question: Suppose the input grid has MM rows and NN columns, in the worst-case scenario, we visit O(MN)O(MN) cells, while having O(MN)O(MN) neighboring cells for our next visit. Selecting the cell with the maximum value from O(MN)O(MN) cells takes O(MN)O(MN) time. Thus this approach would take O(M^2 N^2)O(M
2
 N
2
 ) time. Can we find an efficient way to store all the unvisited neighboring cells and then select the cell with the maximum value? The answer is YES!

We can take advantage of a priority queue, which maintains a heap structure and allows us to select the minimum value in logarithmic time.

We store all unvisited neighboring cells in a priority queue. Among all these cells in the path, we always select the cell that the largest value. Once we remove this cell from the priority queue, we label it as visited and check if the cell has any unvisited neighbors. At each step, we will select the cell with the largest value among all unvisited cells, until we form a path that reaches the bottom-right cell.

Take the slides below as an example. Note that while we only visited cells in the optimal path in this example, this would not usually be the case. This algorithm only guarantees that we will not visit any cells with a value lower than the maximum minimum path value and thus that any path created using the visited cells will not have a value lower than the maximum minimum path value.

Current
1 / 11
Looks like Dijkstra's algorithm?

This method could potentially be considered a variant of Dijkstra's algorithm since they do have some similar features, like using a PriorityQueue to greedily select which vertex (cell) to explore next.

However, in Dijkstra's algorithm, our goal is to find the shortest path to each node, by using the optimal sum of all the previous choices, thus we dynamically update the shortest path from the starting node to other nodes during the iteration. In this problem, however, our goal is to maximize the minimum value in the path, there is no need for us to update the minimum value for paths to other destinations. Therefore, considering this core difference, we will refer to this approach as "BFS + PriorityQueue".

For more information about Dijkstra's Algorithm, you can refer to the Graph Explore Card or to Wikipedia.

Algorithm

Initialize:
an empty priority queue pqpq and put the top-left cell in.
the status of all the cells as unvisited.
the minimum value min\_valmin_val as the value of the top-left cell.
Pop the cell with the largest value from the priority queue, mark it as visited, and update the minimum value seen so far.
Check if the current cell has any unvisited neighbors. If so, add them to the priority queue.
Repeat from step 2 until we pop the bottom-right cell from the priority queue. Return the updated minimum value as the answer.
Implementation


Complexity Analysis

Let n, mn,m be the dimensions of the input matrix gridgrid and kk be the largest value in the matrix.

Time complexity: O(n\cdot m\cdot \log (n \cdot m))O(n⋅m⋅log(n⋅m))

Pushing to or popping from the priority queue takes logarithmic time. The size of the priority queue can approach n \cdot mn⋅m, so each add/remove operation will take O(\log (n \cdot m)O(log(n⋅m) time.
In the worst-case scenario, we will traverse every cell in the matrix, which takes O(n \cdot m)O(n⋅m) add/remove operations.
To sum up, the overall time complexity is O(n \cdot m\cdot \log (n \cdot m))O(n⋅m⋅log(n⋅m))
Space complexity: O(n \cdot m)O(n⋅m)

We used an array of size O(n \cdot m)O(n⋅m) to store the visited/unvisited status of each cell.
In the worse-case scenario, we need to traverse the entire matrix, which takes O(n \cdot m)O(n⋅m) space for a single traversal.
Therefore, the overall space complexity is O(n \cdot m)O(n⋅m).

Approach 5: Union Find
Intuition

Take a look at the picture below. Let's say that the cells in colors are the visited cells, and the grey cells are the unvisited cells. We can tell a path has been found when the top-left cell is 4-directionally connected to the bottom-right cell.

limits

We can maximize the score of a path by always picking the unvisited cell with the largest value. To determine what order we should visit the cells, we can sort them by their values. Then we traverse these cells in order from the largest value to the smallest value. Each time we visit a cell, we mark it as visited and use the union-find data structure to connect this cell with its visited neighbors.

After visiting each cell, we check if the top-left cell and the bottom-right cell have been connected, if so, it means that there is at least one 4 directionally connected path between them, and the last cell we visit is the 'last piece of the puzzle' in this path. Since we are traversing the cells by their decreasing values, the value of the last visited cell is the minimum value in this path and, therefore, the maximum minimum score for all valid paths.

Current
1 / 8
Algorithm

Sort all the cells decreasingly by their values.
Iterate over the sorted cells from the largest value, for each visited cell, check if it has any 4-directionally connected visited neighbor cells, if so, we use the union-find data structure to connect it with its visited neighbors.
Check if the top-left cell is connected with the bottom-right cell.
If so, return the value of the last visited cell.
Otherwise, repeat from the step 2.
Implementation


Complexity Analysis

Let n, mn,m be the dimensions of the input matrix gridgrid and kk be the largest value in the matrix.

Time complexity: O(n \cdot m \cdot \log (n \cdot m))O(n⋅m⋅log(n⋅m))

Our first step is to sort all of the elements in the grid, which will take O(n \cdot m \cdot \log (n \cdot m))O(n⋅m⋅log(n⋅m)) time.
Next, in the worst case, we will iterate over every element before creating a path from the top-left corner to the bottom-right corner. For each element, we will union it with each of its visited neighbors. The amortized complexity of this operation is O(\alpha(n \cdot m))O(α(n⋅m)) time where \alphaα is the Inverse Ackermann Function.
To sum up, the overall time complexity is O(n \cdot m \cdot (\log (n \cdot m) + \alpha(n \cdot m)))O(n⋅m⋅(log(n⋅m)+α(n⋅m))) which simplifies to O(n \cdot m \cdot \log (n \cdot m))O(n⋅m⋅log(n⋅m)).
Space complexity: O(n \cdot m)O(n⋅m)

We used an array of size O(n \cdot m)O(n⋅m) to save the visited/unvisited status of each cell as well as arrays of the same size to save the root and rank of each cell in the union-find data structure. Finally, we also used an array of size O(n \cdot m)O(n⋅m) to store each cell in sorted order.
Therefore, the overall space complexity is O(n \cdot m)O(n⋅m).
 */
class Solution1 {
    // 4 directions to a cell's possible neighbors.
    public int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    public int maximumMinimumPath(int[][] grid) {
        int R = grid.length, C = grid[0].length;
        int curScore = Math.min(grid[0][0], grid[R - 1][C - 1]);

        // Start with curScore, check if we can find a path having value equals curScore.
        // If so, return curScore as the maximum score, otherwise, decrease curScore
        // by 1 and repeat these steps.
        while (curScore >= 0) {
            if (pathExists(grid, curScore)) {
                return curScore;
            } else {
                curScore = curScore - 1;
            }
        }
        return -1;
    }

    // Check if we can find a path of score equals curScore.
    private boolean pathExists(int[][] grid, int curScore) {
        int R = grid.length, C = grid[0].length;

        // Maintain the visited status of each cell. Initalize the status of
        // all the cells as false (unvisited).
        boolean[][] visited = new boolean[R][C];
        visited[0][0] = true;

        // Put the starting cell grid[0][0] in the deque and mark it as visited.
        Queue<int[]> deque = new LinkedList<>();
        deque.offer(new int[]{0, 0});

        // While we still have cells of value larger than or equal to curScore.
        while (!deque.isEmpty()) {
            int[] curGrid = deque.poll();
            int curRow = curGrid[0];
            int curCol = curGrid[1];

            // If the current cell is the bottom-right cell, return true.
            if (curRow == R - 1 && curCol == C - 1) {
                return true;
            }

            for (int[] dir : dirs) {
                int newRow = curRow + dir[0];
                int newCol = curCol + dir[1];

                // Check if the current cell has any unvisited neighbors with value larger
                // than or equal to curScore.
                if (0 <= newRow && newRow < R && 0 <= newCol && newCol < C
                        && visited[newRow][newCol] == false && grid[newRow][newCol] >= curScore) {
                    // If so, we put this neighbor to the deque and mark it as visited.
                    visited[newRow][newCol] = true;
                    deque.offer(new int[]{newRow, newCol});
                }
            }
        }

        // If we empty the deque and still haven't reach the bottom-right cell, return false.
        return false;
    }
}

    class Solution2 {
        // 4 directions to a cell's possible neighbors.
        public int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        public int maximumMinimumPath(int[][] grid) {
            int R = grid.length, C = grid[0].length;
            int left = 0, right = Math.min(grid[0][0], grid[R - 1][C - 1]);

            while (left < right) {
                // Get the middle value between left and right boundaries.
                int middle = (right + left + 1) / 2;

                // Check if we can find a path of value equals middle, and cut
                // the search space by half.
                if (pathExists(grid, middle)) {
                    left = middle;
                } else {
                    right = middle - 1;
                }
            }

            // Once the left and right boundaries coincide, we find the target value,
            // that is, the maximum value of a path.
            return left;
        }

        // Check if we can find a path of value equals val.
        private boolean pathExists(int[][] grid, int val) {
            int R = grid.length, C = grid[0].length;

            // Maintain the visited status of each cell. Initialize the status of
            // all the cells as false (unvisited).
            boolean[][] visited = new boolean[R][C];

            // Put the starting cell grid[0][0] in the deque and mark it as visited.
            Queue<int[]> deque = new LinkedList<>();
            deque.offer(new int[]{0, 0});
            visited[0][0] = true;

            // While we still have cells of value larger than or equal to val.
            while (!deque.isEmpty()) {
                int[] curGrid = deque.poll();
                int curRow = curGrid[0];
                int curCol = curGrid[1];

                // If the current cell is the bottom-right cell, return true.
                if (curRow == R - 1 && curCol == C - 1) {
                    return true;
                }
                for (int[] dir : dirs) {
                    int newRow = curRow + dir[0];
                    int newCol = curCol + dir[1];

                    // Check if the current cell has any unvisited neighbors with value larger
                    // than or equal to val.
                    if (0 <= newRow && newRow < R && 0 <= newCol && newCol < C
                            && visited[newRow][newCol] == false && grid[newRow][newCol] >= val) {
                        // If so, we put this neighbor to the deque and mark it as visited.
                        visited[newRow][newCol] = true;
                        deque.offer(new int[]{newRow, newCol});
                    }
                }
            }
            return false;
        }
    }
    class Solution3 {
        private int R, C;

        // 4 directions to a cell's possible neighbors.
        private int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        public int maximumMinimumPath(int[][] grid) {
            R = grid.length;
            C = grid[0].length;
            int left = 0, right = Math.min(grid[0][0], grid[R - 1][C - 1]);
            while (left < right) {
                // Get the middle value between left and right boundaries.
                int middle = (right + left + 1) / 2;
                boolean[][] visited = new boolean[R][C];

                // Check if we can find a path with value = middle, and cut
                // the search space by half.
                if (pathExists(grid, middle, visited, 0, 0)) {
                    left = middle;
                } else {
                    right = middle - 1;
                }
            }

            // Once the left and right boundaries coincide, we find the target value,
            // that is, the maximum value of a path.
            return left;
        }

        // Check if we can find a path of value = val.
        private boolean pathExists(int[][] grid, int val, boolean[][] visited, int curRow, int curCol) {
            // If we reach the bottom-right cell, return true.
            if (curRow == R - 1 && curCol == C - 1) return true;

            // Mark the current cell as visited.
            visited[curRow][curCol] = true;
            for (int[] dir : dirs) {
                int newRow = curRow + dir[0];
                int newCol = curCol + dir[1];

                // Check if the current cell has any unvisited neighbors with value larger
                // than or equal to val.
                if (newRow >= 0 && newRow < R && newCol >= 0 && newCol < C
                        && !visited[newRow][newCol] && grid[newRow][newCol] >= val) {
                    // If so, we continue search on this neighbor.
                    if (pathExists(grid, val, visited, newRow, newCol))
                        return true;
                }
            }
            return false;
        }
    }


    class Solution4 {
        private int R, C;

        // 4 directions to a cell's possible neighbors.
        private int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        public int maximumMinimumPath(int[][] grid) {
            R = grid.length;
            C = grid[0].length;
            Queue<int[]> pq = new PriorityQueue<>(
                    (a, b) -> Integer.compare(grid[b[0]][b[1]], grid[a[0]][a[1]]));

            // Initalize the status of all the cells as false (unvisited).
            boolean[][] visited = new boolean[R][C];

            // Put the top-left cell to the priority queue
            // and mark it as true (visited).
            pq.offer(new int[] {0, 0});
            visited[0][0] = true;

            int ans = grid[0][0];

            // While the priority queue is not empty.
            while (!pq.isEmpty()) {
                // Pop the cell with the largest value.
                int[] curGrid = pq.poll();
                int curRow = curGrid[0], curCol = curGrid[1];

                // Update the minimum value we have visited so far.
                ans = Math.min(ans, grid[curRow][curCol]);

                // If we reach the bottom-right cell, stop the iteration.
                if (curRow == R - 1 && curCol == C - 1) {
                    break;
                }
                for (int[] dir : dirs) {
                    int newRow = curRow + dir[0], newCol = curCol + dir[1];

                    // Check if the current cell has any unvisited neighbors.
                    if (newRow >= 0 && newRow < R && newCol >= 0 && newCol < C
                            && !visited[newRow][newCol]) {
                        // If so, we put this neighbor to the priority queue
                        // and mark it as true (visited).
                        pq.offer(new int[] {newRow, newCol});
                        visited[newRow][newCol] = true;
                    }
                }
            }

            // Return the minimum value we have seen,
            // which is the value of this path.
            return ans;
        }
    }


    class UF {
        // root for recording all the roots.
        private int[] root;
        private int[] rank;
        public UF(int R, int C) {
            rank = new int[R * C];
            root = new int[R * C];
            for (int i = 0; i < root.length; ++i)
                root[i] = i;
        }

        // Find the root of x.
        public int find(int x) {
            if (x != root[x])
                root[x] = find(root[x]);
            return root[x];
        }

        // union the roots of x and y.
        public void union(int x, int y) {
            int rootX = find(x), rootY = find(y);
            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    root[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    root[rootX] = rootY;
                } else {
                    root[rootY] = rootX;
                    rank[rootX] += 1;
                }
            }
        }
    }

    class Solution5 {
        // 4 directions to a cell's possible neighbors.
        private int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        public int maximumMinimumPath(int[][] grid) {
            int R = grid.length, C = grid[0].length;

            // Sort all the cells by their values.
            List<int[]> vals = new ArrayList<>();

            // Intialize the root of all the cells and mark
            // all the cells as false (unvisited).
            boolean[][] visited = new boolean[R][C];

            // Root of all the R * C cells
            UF uf = new UF(R, C);

            // Intialize the root of all the cells.
            for (int row = 0; row < R; ++row)
                for (int col = 0; col < C; ++col)
                    vals.add(new int[]{row, col});

            // Sort all the cells by values from large to small.
            Collections.sort(vals, (gridA, gridB) -> {
                return grid[gridB[0]][gridB[1]] - grid[gridA[0]][gridA[1]];
            });

            // Iteration over the sorted cells.
            for (int[] curGrid : vals) {
                int curRow = curGrid[0], curCol = curGrid[1];
                int curPos = curRow * C + curCol;

                // Mark the current cell as visited.
                visited[curRow][curCol] = true;
                for (int[] dir : dirs) {
                    int newRow = curRow + dir[0];
                    int newCol = curCol + dir[1];
                    int newPos = newRow * C + newCol;

                    // Check if the current cell has any unvisited neighbors
                    // with value larger than or equal to val.
                    if (newRow >= 0 && newRow < R && newCol >= 0
                            && newCol < C && visited[newRow][newCol] == true) {
                        // If so, we connect the current cell with this neighbor.
                        uf.union(curPos, newPos);
                    }
                }

                // Check if the top-left cell is connected with the bottom-right cell.
                if (uf.find(0) == uf.find(R * C - 1)) {
                    // If so, return the value of the current cell.
                    return grid[curRow][curCol];
                }
            }
            return -1;
        }
    }

    //dis


}

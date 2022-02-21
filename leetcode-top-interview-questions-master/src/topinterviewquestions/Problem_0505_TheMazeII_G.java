package topinterviewquestions;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
/*
There is a ball in a maze with empty spaces (represented as 0) and walls (represented as 1). The ball can go through the empty spaces by rolling up, down, left or right, but it won't stop rolling until hitting a wall. When the ball stops, it could choose the next direction.

Given the m x n maze, the ball's start position and the destination, where start = [startrow, startcol] and destination = [destinationrow, destinationcol], return the shortest distance for the ball to stop at the destination. If the ball cannot stop at destination, return -1.

The distance is the number of empty spaces traveled by the ball from the start position (excluded) to the destination (included).

You may assume that the borders of the maze are all walls (see examples).



Example 1:


Input: maze = [[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]], start = [0,4], destination = [4,4]
Output: 12
Explanation: One possible way is : left -> down -> left -> down -> right -> down -> right.
The length of the path is 1 + 1 + 3 + 1 + 2 + 2 + 2 = 12.
Example 2:


Input: maze = [[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]], start = [0,4], destination = [3,2]
Output: -1
Explanation: There is no way for the ball to stop at the destination. Notice that you can pass through the destination but you cannot stop there.
Example 3:

Input: maze = [[0,0,0,0,0],[1,1,0,0,1],[0,0,0,0,0],[0,1,0,0,1],[0,1,0,0,0]], start = [4,3], destination = [0,1]
Output: -1


Constraints:

m == maze.length
n == maze[i].length
1 <= m, n <= 100
maze[i][j] is 0 or 1.
start.length == 2
destination.length == 2
0 <= startrow, destinationrow <= m
0 <= startcol, destinationcol <= n
Both the ball and the destination exist in an empty space, and they will not be in the same position initially.
The maze contains at least 2 empty spaces.
 */
public class Problem_0505_TheMazeII_G {
    //BFS with PriorityQueue
    //Time complexity : O(mn∗log(mn)). Complete traversal of maze will be done in the worst case giving a factor of mn.
    // Further, poll method is a combination of heapifying O(log(n)) and removing the top element(O(1))
    // from the priority queue, and it takes O(n) time for nn elements. In the current case, poll introduces a factor of log(mn).
    //Space complexity : O(mn). distance array of size m*n is used and queue size can grow upto m*n in worst case.
    class Solution {
        class Point {
            int x,y,l;
            public Point(int x, int y, int l) {
                this.x =x;
                this.y =y;
                this.l =l;
            }
        }

        public int shortestDistance(int[][] maze, int[] start, int[] destination) {
            int m=maze.length, n=maze[0].length;
            int[][] distance = new int[m][n]; // record distance
            for (int i=0;i<m*n;i++) {
                distance[i/n][i%n]=Integer.MAX_VALUE;
            }
            int[][] dirs=new int[][] {{-1,0},{0,1},{1,0},{0,-1}};
            PriorityQueue<Point> minHeap = new PriorityQueue<>((o1, o2)->o1.l-o2.l); // using priority queue, minheap
            minHeap.offer(new Point(start[0], start[1], 0));
            while (!minHeap.isEmpty()) {
                Point p = minHeap.poll();
                if (distance[p.x][p.y] <= p.l) {
                    continue; // if we have already found a route shorter
                }
                distance[p.x][p.y]=p.l;
                for (int i=0;i<4;i++) {
                    int newX=p.x, newY=p.y, l=p.l;
                    while (newX>=0 && newX<m && newY>=0 && newY<n && maze[newX][newY]==0) {
                        newX+=dirs[i][0];
                        newY+=dirs[i][1];
                        l++;
                    }
                    newX-=dirs[i][0];//回到上一个有效的点
                    newY-=dirs[i][1];
                    l--;
                    minHeap.offer(new Point(newX, newY, l));
                }
            }
            return distance[destination[0]][destination[1]]==Integer.MAX_VALUE?-1:distance[destination[0]][destination[1]];
        }

        //Approach #1 Depth First Search [Accepted]
        //Time complexity : O(m∗n∗max(m,n)). Complete traversal of maze will be done in the worst case.
        // Here, mm and nn refers to the number of rows and columns of the maze.
        // Further, for every current node chosen, we can travel upto a maximum depth of max(m,n) in any direction.
        //Space complexity : O(mn). distance array of size m*n is used.
        public class Solution1 {
            public int shortestDistance(int[][] maze, int[] start, int[] dest) {
                int[][] distance = new int[maze.length][maze[0].length];
                for (int[] row: distance)
                    Arrays.fill(row, Integer.MAX_VALUE);
                distance[start[0]][start[1]] = 0;
                dfs(maze, start, distance);
                return distance[dest[0]][dest[1]] == Integer.MAX_VALUE ? -1 : distance[dest[0]][dest[1]];
            }

            public void dfs(int[][] maze, int[] start, int[][] distance) {
                int[][] dirs={{0,1}, {0,-1}, {-1,0}, {1,0}};
                for (int[] dir: dirs) {
                    int x = start[0] + dir[0];
                    int y = start[1] + dir[1];
                    int count = 0;
                    while (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0) {
                        x += dir[0];
                        y += dir[1];
                        count++;
                    }

                    if (distance[start[0]][start[1]] + count < distance[x - dir[0]][y - dir[1]]) {
                        distance[x - dir[0]][y - dir[1]] = distance[start[0]][start[1]] + count;
                        dfs(maze, new int[]{x - dir[0],y - dir[1]}, distance);
                    }
                }


            }
        }

        //Approach #2 Using Breadth First Search [Accepted]
        //Time complexity : O(m∗n∗max(m,n)). Complete traversal of maze will be done in the worst case.
        // Here, mm and nn refers to the number of rows and columns of the maze.
        // Further, for every current node chosen, we can travel upto a maximum depth of max(m,n) in any direction.
        //Space complexity : O(mn). distance array of size m*n is used.
        public class Solution2 {
            public int shortestDistance(int[][] maze, int[] start, int[] dest) {
                int[][] distance = new int[maze.length][maze[0].length];
                for (int[] row: distance) {
                    Arrays.fill(row, Integer.MAX_VALUE);
                }
                distance[start[0]][start[1]] = 0;
                int[][] dirs={{0, 1} ,{0, -1}, {-1, 0}, {1, 0}};
                Queue< int[] > queue = new LinkedList< >();
                queue.add(start);
                while (!queue.isEmpty()) {
                    int[] s = queue.remove();
                    for (int[] dir: dirs) {
                        int x = s[0] + dir[0];
                        int y = s[1] + dir[1];
                        int count = 0;
                        while (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0) {
                            x += dir[0];
                            y += dir[1];
                            count++;
                        }
                        if (distance[s[0]][s[1]] + count < distance[x - dir[0]][y - dir[1]]) {
                            distance[x - dir[0]][y - dir[1]] = distance[s[0]][s[1]] + count;
                            queue.add(new int[] {x - dir[0], y - dir[1]});
                        }
                    }
                }
                return distance[dest[0]][dest[1]] == Integer.MAX_VALUE ? -1 : distance[dest[0]][dest[1]];
            }
        }



    }
}

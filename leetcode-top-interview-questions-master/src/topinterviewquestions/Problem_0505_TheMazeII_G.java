package topinterviewquestions;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

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
            int[][] length=new int[m][n]; // record length
            for (int i=0;i<m*n;i++) {
                length[i/n][i%n]=Integer.MAX_VALUE;
            }
            int[][] dir=new int[][] {{-1,0},{0,1},{1,0},{0,-1}};
            PriorityQueue<Point> list=new PriorityQueue<>((o1, o2)->o1.l-o2.l); // using priority queue
            list.offer(new Point(start[0], start[1], 0));
            while (!list.isEmpty()) {
                Point p=list.poll();
                if (length[p.x][p.y]<=p.l) {
                    continue; // if we have already found a route shorter
                }
                length[p.x][p.y]=p.l;
                for (int i=0;i<4;i++) {
                    int newX=p.x, newY=p.y, l=p.l;
                    while (newX>=0 && newX<m && newY>=0 && newY<n && maze[newX][newY]==0) {
                        newX+=dir[i][0];
                        newY+=dir[i][1];
                        l++;
                    }
                    newX-=dir[i][0];
                    newY-=dir[i][1];
                    l--;
                    list.offer(new Point(newX, newY, l));
                }
            }
            return length[destination[0]][destination[1]]==Integer.MAX_VALUE?-1:length[destination[0]][destination[1]];
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

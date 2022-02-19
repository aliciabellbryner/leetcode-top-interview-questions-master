package topinterviewquestions;

import java.util.LinkedList;

public class Problem_0490_TheMaze_G {
    //solution 1: DFS
    //Time complexity :O(mn). Complete traversal of maze will be done in the worst case.
    // Here, m and n refers to the number of rows and coloumns of the maze.
    //Space complexity : O(mn). visited array of size m*n is used.
    public class Solution {
        public boolean hasPath(int[][] maze, int[] start, int[] destination) {
            boolean[][] visited = new boolean[maze.length][maze[0].length];
            return dfs(maze, start, destination, visited);
        }
        public boolean dfs(int[][] maze, int[] start, int[] destination, boolean[][] visited) {
            if (visited[start[0]][start[1]])
                return false;
            if (start[0] == destination[0] && start[1] == destination[1])
                return true;
            visited[start[0]][start[1]] = true;
            int r = start[1] + 1, l = start[1] - 1, u = start[0] - 1, d = start[0] + 1;
            while (r < maze[0].length && maze[start[0]][r] == 0) // right
                r++;
            if (dfs(maze, new int[] {start[0], r - 1}, destination, visited))
                return true;
            while (l >= 0 && maze[start[0]][l] == 0) //left
                l--;
            if (dfs(maze, new int[] {start[0], l + 1}, destination, visited))
                return true;
            while (u >= 0 && maze[u][start[1]] == 0) //up
                u--;
            if (dfs(maze, new int[] {u + 1, start[1]}, destination, visited))
                return true;
            while (d < maze.length && maze[d][start[1]] == 0) //down
                d++;
            if (dfs(maze, new int[] {d - 1, start[1]}, destination, visited))
                return true;
            return false;
        }
    }

    //sApproach 2: Breadth First Search
    //Time complexity :O(mn). Complete traversal of maze will be done in the worst case.
    // Here, m and n refers to the number of rows and coloumns of the maze.
    //Space complexity : O(mn). visited array of size m*n is used.
    class Solution2 {
        class Point {
            int x,y;
            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
        public boolean hasPath(int[][] maze, int[] start, int[] destination) {
            int m = maze.length, n = maze[0].length;
            if (start[0]==destination[0] && start[1]==destination[1]) {
                return true;
            }
            int[][] dirs = new int[][] {{-1,0},{0,1},{1,0},{0,-1}};
            boolean[][] visited=new boolean[m][n];
            LinkedList<Point> list=new LinkedList<>();
            visited[start[0]][start[1]] = true;
            list.offer(new Point(start[0], start[1]));
            while (!list.isEmpty()) {
                Point p = list.poll();
                for (int i=0;i<4;i++) {
                    int newX = p.x, newY = p.y;
                    //走到第一个不是0的位置
                    while (newX>=0 && newX<m && newY>=0 && newY<n && maze[newX][newY]==0) {
                        newX += dirs[i][0];
                        newY += dirs[i][1];
                    }
                    //回到上一个还是0的位置
                    newX -= dirs[i][0];
                    newY -= dirs[i][1];
                    if (visited[newX][newY]) {
                        continue;
                    }
                    visited[newX][newY] = true;
                    if (newX==destination[0] && newY==destination[1]) {
                        return true;
                    }
                    list.offer(new Point(newX, newY));
                }
            }
            return false;
        }
    }
}

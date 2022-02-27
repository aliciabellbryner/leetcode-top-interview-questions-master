package topinterviewquestions;
/*
A room is represented by a 0-indexed 2D binary matrix room where a 0 represents an empty space and a 1 represents a space with an object. The top left corner of the room will be empty in all test cases.

A cleaning robot starts at the top left corner of the room and is facing right. The robot will continue heading straight until it reaches the edge of the room or it hits an object, after which it will turn 90 degrees clockwise and repeat this process. The starting space and all spaces that the robot visits are cleaned by it.

Return the number of clean spaces in the room if the robot runs indefinetely.



Example 1:


Input: room = [[0,0,0],[1,1,0],[0,0,0]]
Output: 7
Explanation:
The robot cleans the spaces at (0, 0), (0, 1), and (0, 2).
The robot is at the edge of the room, so it turns 90 degrees clockwise and now faces down.
The robot cleans the spaces at (1, 2), and (2, 2).
The robot is at the edge of the room, so it turns 90 degrees clockwise and now faces left.
The robot cleans the spaces at (2, 1), and (2, 0).
The robot has cleaned all 7 empty spaces, so return 7.
Example 2:


Input: room = [[0,1,0],[1,0,0],[0,0,0]]
Output: 1
Explanation:
The robot cleans the space at (0, 0).
The robot hits an object, so it turns 90 degrees clockwise and now faces down.
The robot hits an object, so it turns 90 degrees clockwise and now faces left.
The robot is at the edge of the room, so it turns 90 degrees clockwise and now faces up.
The robot is at the edge of the room, so it turns 90 degrees clockwise and now faces right.
The robot is back at its starting position.
The robot has cleaned 1 space, so return 1.


Constraints:

m == room.length
n == room[r].length
1 <= m, n <= 300
room[r][c] is either 0 or 1.
room[0][0] == 0
 */
public class Problem_2061_NumberofSpacesCleaningRobotCleaned {
    /*
    diss
     */


    //Explanation - We run a main loop to keep the robot going until certain criteria is met. We advance one step at a time. If we encounter boundary or a 1, we move back, turn right and continue. We maintain a 2D array visited to figure out if we need to end the simulation. 2D array stores the row, column and direction of the robot where it has already visited.
    // To end the simulation, not only we need to be at an already visited cell but also facing the same direction as when we initially visited the cell. This is because in such a case the robot will enter in an infinite loop and we need to end the simulation. We denote directions by indices 1,2,3,4. Note that array for directions has a dummy direction at index 0.
    // This is so that we don't need to loop through the visited array to initialize it to a non-zero value. Otherwise the dummy direction at index 0 is never used.
    class Solution2 {

        private int m, n, r, c, d = 1;
        private int[][] dirs = new int[][] { {}, {0,1}, {1,0}, {0,-1}, {-1,0}  };

        public int numberOfCleanRooms(int[][] room) {
            this.m = room.length;
            this.n = room[0].length;
            int[][] visited = new int[m][n];//把方向的值作为这个二位数列的值

            int res = 0;
            while (true) {
                if (isOutOfBounds() || room[r][c] == 1) {//当越界或者碰到石头的时候，需要先退一步，然后向右转
                    turnRight();
                    continue;
                }

                if (visited[r][c] == d) {//如果遇到一样的，说明已经走过而且方向也是一样的，说明要开始无限循环了，所以直接返回res
                    return res;
                }

                if (visited[r][c] == 0) {
                    visited[r][c] = d;
                    res++;
                }

                r += dirs[d][0];
                c += dirs[d][1];
            }
        }

        private boolean isOutOfBounds() {
            return r < 0 || r == m || c < 0 || c == n;
        }

        private void turnRight() {
            r -= dirs[d][0];
            c -= dirs[d][1];//先退一步
            d = (d % 4) + 1;//再向右转
        }
    }



    // Keep going until we reach the same position (row and col) with the same direction (up, right, down, or left).
    public int numberOfCleanRooms(int[][] room) {
        if (room == null || room.length == 0) return 0;
        int[][] dirs = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};  // Up, right, down, left.
        boolean[][][] seen = new boolean[room.length][room[0].length][dirs.length];
        int result = 0, currRow = 0, currCol = 0, currDir = 1;//curDir = 1因为一开始是只能向右走
        while (!seen[currRow][currCol][currDir]) {
            if (!seen[currRow][currCol][0] && !seen[currRow][currCol][1] && !seen[currRow][currCol][2] && !seen[currRow][currCol][3]) {
                result++;
            }
            seen[currRow][currCol][currDir] = true;

            int newRow = currRow + dirs[currDir][0];
            int newCol = currCol + dirs[currDir][1];
            if (0 <= newRow && newRow < room.length && 0 <= newCol && newCol < room[0].length && room[newRow][newCol] == 0) {
                currRow = newRow;
                currCol = newCol;
            } else {
                currDir = (currDir + 1) % 4;    // 90 degrees clockwise turn.
            }
        }
        return result;
    }




}

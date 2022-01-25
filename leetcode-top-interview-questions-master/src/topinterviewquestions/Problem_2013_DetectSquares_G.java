package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

public class Problem_2013_DetectSquares_G {
    //Time:
    //add: O(1)
    //count: O(T), where T <= 5000 is total number of points after calling add.
    //Space: O(1000^2) in Java
    //思路是用对角线上面的点来做遍历，如果对角线上有个店正好符合既不是相同点x距离和y距离正好相等的话，
    // 就可以看看以这两点构成的正方形上另外两点是否有点，他们的点数乘积就是用[x,y]和[curx, cury]构成正方形的总共个数
    class DetectSquares { // 128 ms, faster than 60.00%
        int[][] samePoints = new int[1001][1001];
        List<int[]> points = new ArrayList<>();

        public void add(int[] point) {
            samePoints[point[0]][point[1]]++;
            points.add(point);
        }

        public int count(int[] point) {
            int curx = point[0], cury = point[1], res = 0;
            for (int[] p : points) {
                int x = p[0], y = p[1];
                if (Math.abs(curx-x) == 0 || Math.abs(curx-x) != Math.abs(cury-y)) {
                    continue; // Skip empty square or invalid square point!
                }
                res += samePoints[curx][y] * samePoints[x][cury];
            }
            return res;
        }
    }
}

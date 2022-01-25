package topinterviewquestions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Problem_1610_MaximumNumberofVisiblePoints_G {
    //https://leetcode.com/problems/maximum-number-of-visible-points/discuss/877845/JAVA-Sliding-Window/771344
    //time O(NlogN) N: len(points)
    //Space O(N)
    public int visiblePoints(List<List<Integer>> points, int angle, List<Integer> location) {
        List<Double> angles = new ArrayList<>();
        int same = 0;
        for (List<Integer> p : points) {
            int dx = p.get(0) - location.get(0);
            int dy = p.get(1) - location.get(1);
            if (dx == 0 && dy == 0) { // edge case of same point
                same++;
            } else {
                angles.add(Math.atan2(dy, dx) * (180 / Math.PI));
            }
        }
        Collections.sort(angles);
        List<Double> tmp = new ArrayList<>(angles);
        for (double d : tmp) {
            angles.add(d + 360); // concatenate to handle edge case,因为比如是[-160, 0, 160],允许的角度是50度，
            // 那么其实-160和160是符合的因为他们角度是40度，但是按照下面的算法算差是320度不在这个范围，所以我们必须多加一套+360的点
        }

        int res = 0, i = 0;
        for (int j = 0; j < angles.size(); j++) {
            while (angles.get(j) - angles.get(i) > angle) {
                i++;
            }
            res = Math.max(res, j - i + 1);
        }
        return res + same;
    }
}

package topinterviewquestions;

import java.util.HashMap;
import java.util.Map;

public class Problem_0149_MaxPointsOnALine {

	//time O(N^2), N = points.length;
	//space O(N), hashmap space
	public static int maxPoints(int[][] points) {
		if (points == null) {
			return 0;
		}
		if (points.length <= 2) {
			return points.length;
		}
		// Map<String, Integer>   "3_5"   6
		// 3 / 5    4
		// 3 / 7    10
		// 3 / 17   11
		// 5 / 7    9
		// 5 / 9    3
		// 3 :    (  5 , 4    7, 10,      17 ,  11      )
		// 5 :    (  7 , 9    9, 3  )
		Map<Integer, Map<Integer, Integer>> map = new HashMap<Integer, Map<Integer, Integer>>();//first Integer put the deltaX, second integer put deltaY, third integer put times of appearance
		int res = 0;
		for (int i = 0; i < points.length; i++) {
			map.clear();
			//be very careful about the variables' start value!!!
			int samePosition = 1;//you have to start from 1 cannot be 0
			int sameX = 0;
			int sameY = 0;
			int line = 0; // 哪个斜率压中的点最多，把最多的点的数量，赋值给line
			for (int j = i + 1; j < points.length; j++) {
				int x_diff = points[j][0] - points[i][0];
				int y_diff = points[j][1] - points[i][1];
				if (x_diff == 0 && y_diff == 0) {
					samePosition++;
				} else if (x_diff == 0) {
					sameX++;
				} else if (y_diff == 0) {
					sameY++;
				} else { // 有斜率
					int gcd = gcd(x_diff, y_diff);
					x_diff /= gcd;
					y_diff /= gcd;
					if (!map.containsKey(x_diff)) {
						map.put(x_diff, new HashMap<Integer, Integer>());
					}
					if (!map.get(x_diff).containsKey(y_diff)) {
						map.get(x_diff).put(y_diff, 0);
					}
					map.get(x_diff).put(y_diff, map.get(x_diff).get(y_diff) + 1);
					line = Math.max(line, map.get(x_diff).get(y_diff));
				}
			}
			res = Math.max(res, Math.max(Math.max(sameX, sameY), line) + samePosition);
			// note samePosition is added not compared who is max as you are in the i loop,
			// so the sameX, sameY, and line are all counted when you start from the points[i]
		}
		return res;
	}

	//求最大公约数
	// 保证初始调用的时候，a和b不等于0
	// O(1)
	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

}

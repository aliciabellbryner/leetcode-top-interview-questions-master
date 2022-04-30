package topinterviewquestions;

import java.util.*;

public class Problem_0939_MinimumAreaRectangle_G {

    //https://leetcode.com/problems/minimum-area-rectangle/discuss/192025/Java-N2-Hashmap
    class Solution {
        public int minAreaRect(int[][] points) {
            Map<Integer, Set<Integer>> map = new HashMap<>();
            for (int[] p : points) {
                if (!map.containsKey(p[0])) {
                    map.put(p[0], new HashSet<>());
                }
                map.get(p[0]).add(p[1]);
            }
            int min = Integer.MAX_VALUE;
            for (int[] p1 : points) {
                for (int[] p2 : points) {
                    if (p1[0] == p2[0] || p1[1] == p2[1]) { // if have the same x or y
                        continue;
                    }
                    if (map.get(p1[0]).contains(p2[1]) && map.get(p2[0]).contains(p1[1])) { // find other two points
                        min = Math.min(min, Math.abs(p1[0] - p2[0]) * Math.abs(p1[1] - p2[1]));
                    }
                }
            }
            return min == Integer.MAX_VALUE ? 0 : min;
        }
    }


    //方法一：按列排序
    //我们将这些点按照 x 轴（即列）排序，那么对于同一列的两个点 (x, y1) 和 (x, y2)，我们找出它作为右边界的最小的矩形。这可以通过记录下我们之前遇到的所有 (y1, y2) 点对来做到。
    //
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/minimum-area-rectangle/solution/zui-xiao-mian-ji-ju-xing-by-leetcode/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution2 {
        public int minAreaRect(int[][] points) {

            Map<Integer, List<Integer>> rows = new TreeMap();
            for (int[] point: points) {
                int x = point[0], y = point[1];
                rows.computeIfAbsent(x, z-> new ArrayList()).add(y);
            }

            int ans = Integer.MAX_VALUE;
            Map<Integer, Integer> lastx = new HashMap();
            for (int x: rows.keySet()) {
                List<Integer> row = rows.get(x);
                Collections.sort(row);
                for (int i = 0; i < row.size(); ++i)
                    for (int j = i+1; j < row.size(); ++j) {
                        int y1 = row.get(i), y2 = row.get(j);
                        int code = 40001 * y1 + y2;
                        if (lastx.containsKey(code))
                            ans = Math.min(ans, (x - lastx.get(code)) * (y2-y1));
                        lastx.put(code, x);
                    }
            }

            return ans < Integer.MAX_VALUE ? ans : 0;
        }
    }

    //方法二：枚举对角线
    //我们将所有点放入集合中，并枚举矩形对角线上的两个点，并判断另外两个点是否出现在集合中。例如我们在枚举到 (1, 1) 和 (5, 5) 时，我们需要判断 (1, 5) 和 (5, 1) 是否也出现在集合中。
    //
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/minimum-area-rectangle/solution/zui-xiao-mian-ji-ju-xing-by-leetcode/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution3 {
        public int minAreaRect(int[][] points) {
            Set<Integer> pointSet = new HashSet();
            for (int[] point: points)
                pointSet.add(40001 * point[0] + point[1]);

            int ans = Integer.MAX_VALUE;
            for (int i = 0; i < points.length; ++i)
                for (int j = i+1; j < points.length; ++j) {
                    if (points[i][0] != points[j][0] && points[i][1] != points[j][1]) {
                        if (pointSet.contains(40001 * points[i][0] + points[j][1]) &&
                                pointSet.contains(40001 * points[j][0] + points[i][1])) {
                            ans = Math.min(ans, Math.abs(points[j][0] - points[i][0]) *
                                    Math.abs(points[j][1] - points[i][1]));
                        }
                    }
                }

            return ans < Integer.MAX_VALUE ? ans : 0;
        }
    }



}


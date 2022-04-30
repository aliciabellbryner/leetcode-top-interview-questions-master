package topinterviewquestions;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Problem_0850_RectangleAreaII_G {
    //https://leetcode.com/problems/rectangle-area-ii/discuss/137914/JavaC%2B%2BPython-Discretization-and-O(NlogN)
    //We checked every y value, and for every y, we count the coverage of x.
    //So this a O(N^2) solution.
    //In fact I expected the N to be big like 10^4. So only solution at least O(N^2) can get accepted.
    public int rectangleArea(int[][] rectangles) {
        int mod = (int)(1e9 + 7);
        Set<Integer> x = new TreeSet<>();
        List<int[]> L = new ArrayList<>();
        for (int[] r : rectangles) {
            x.add(r[0]);
            x.add(r[2]);
            L.add(new int[]{r[1], r[0], r[2], 1});
            L.add(new int[]{r[3], r[0], r[2], -1});
        }
        List<Integer> unique = new ArrayList<>(x);
        Map<Integer, Integer> xMap = IntStream.range(0, unique.size()).boxed().collect(Collectors.toMap(unique::get, i -> i));
        int[] cnt = new int[unique.size()];
        Collections.sort(L, (a, b) -> a[0] == b[0] ? a[1] == b[1] ? a[2] == b[2] ? Integer.compare(a[3], b[3]) : Integer.compare(a[2], b[2]) : Integer.compare(a[1], b[1]) : Integer.compare(a[0], b[0]));
        long curY = 0;
        long curXSum = 0;
        long area = 0;
        for (int[] l : L) {
            long y = (long)l[0], x1 = (long)l[1], x2 = (long)l[2], sig = (long)l[3];
            area = (area + (y - curY) * curXSum) % mod;
            curY = y;
            for (int i = xMap.get((int)x1); i < xMap.get((int)x2); i++) {
                cnt[i] += (int)sig;
            }
            curXSum = 0;
            for (int i = 0; i < unique.size(); i++) {
                if (cnt[i] > 0) {
                    curXSum += unique.get(i + 1) - unique.get(i);
                }
            }
        }
        return (int)area;
    }


    ///Approach #3: Segment Tree
    //Time Complexity: O(N \log N)O(NlogN), where NN is the number of rectangles
    //Space Complexity: O(N)O(N).
    class Solution {
        public int rectangleArea(int[][] rectangles) {
            int OPEN = 1, CLOSE = -1;
            int[][] events = new int[rectangles.length * 2][];
            Set<Integer> Xvals = new HashSet();
            int t = 0;

            for (int[] rec: rectangles) {
                if ((rec[0] < rec[2]) && (rec[1] < rec[3])) {
                    events[t++] = new int[]{rec[1], OPEN, rec[0], rec[2]};
                    events[t++] = new int[]{rec[3], CLOSE, rec[0], rec[2]};
                    Xvals.add(rec[0]);
                    Xvals.add(rec[2]);
                }
            }

            Arrays.sort(events, 0, t, (a, b) -> Integer.compare(a[0], b[0]));

            Integer[] X = Xvals.toArray(new Integer[0]);
            Arrays.sort(X);
            Map<Integer, Integer> Xi = new HashMap();
            for (int i = 0; i < X.length; ++i)
                Xi.put(X[i], i);

            Node active = new Node(0, X.length - 1, X);
            long ans = 0;
            long cur_x_sum = 0;
            int cur_y = events[0][0];

            for (int[] event: events) {
                if (event == null) break;
                int y = event[0], typ = event[1], x1 = event[2], x2 = event[3];
                ans += cur_x_sum * (y - cur_y);
                cur_x_sum = active.update(Xi.get(x1), Xi.get(x2), typ);
                cur_y = y;
            }

            ans %= 1_000_000_007;
            return (int) ans;
        }
    }

    class Node {
        int start, end;
        Integer[] X;
        Node left, right;
        int count;
        long total;

        public Node(int start, int end, Integer[] X) {
            this.start = start;
            this.end = end;
            this.X = X;
            left = null;
            right = null;
            count = 0;
            total = 0;
        }

        public int getRangeMid() {
            return start + (end - start) / 2;
        }

        public Node getLeft() {
            if (left == null) left = new Node(start, getRangeMid(), X);
            return left;
        }

        public Node getRight() {
            if (right == null) right = new Node(getRangeMid(), end, X);
            return right;
        }

        public long update(int i, int j, int val) {
            if (i >= j) return 0;
            if (start == i && end == j) {
                count += val;
            } else {
                getLeft().update(i, Math.min(getRangeMid(), j), val);
                getRight().update(Math.max(getRangeMid(), i), j, val);
            }

            if (count > 0) total = X[end] - X[start];
            else total = getLeft().total + getRight().total;

            return total;
        }
    }
}

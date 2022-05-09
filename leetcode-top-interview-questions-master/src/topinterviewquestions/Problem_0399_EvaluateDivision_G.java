package topinterviewquestions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Problem_0399_EvaluateDivision_G {
    //using graph, Image a/b = k as a link between node a and b, the weight from a to b is k, the reverse link is 1/k. Query is to find a path between two nodes.
    //https://leetcode.com/problems/evaluate-division/discuss/88169/Java-AC-Solution-using-graph/126499
    public double[] calcEquation(String[][] eq, double[] vals, String[][] q) {
        Map<String, Map<String, Double>> map = new HashMap<>();
        for (int i = 0; i < vals.length; i++) {
            map.putIfAbsent(eq[i][0], new HashMap<>());
            map.putIfAbsent(eq[i][1], new HashMap<>());
            map.get(eq[i][0]).put(eq[i][1], vals[i]);
            map.get(eq[i][1]).put(eq[i][0], 1 / vals[i]);
        }
        double[] res = new double[q.length];
        for (int i = 0; i < q.length; i++)
            res[i] = dfs(q[i][0], q[i][1], 1, map, new HashSet<>());
        return res;
    }

    double dfs(String s, String t, double res, Map<String, Map<String, Double>> map, Set<String> seen) {
        if (!map.containsKey(s) || !seen.add(s)) return -1;//如果s不在map或者seen里那直接return -1
        if (s.equals(t)) return res;
        Map<String, Double> next = map.get(s);
        for (String c : next.keySet()) {
            double result = dfs(c, t, res * next.get(c), map, seen);
            if (result != -1) return result;
        }
        return -1;
    }

}

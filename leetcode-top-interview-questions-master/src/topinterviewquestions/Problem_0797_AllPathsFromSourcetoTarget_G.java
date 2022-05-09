package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

public class Problem_0797_AllPathsFromSourcetoTarget_G {
    //https://leetcode.com/problems/all-paths-from-source-to-target/discuss/118691/C++Python-Backtracking/628216
    class Solution {
        public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
            List<List<Integer>> res = new ArrayList<>();
            List<Integer> path = new ArrayList<>();
            path.add(0);
            backtracking(res, graph, 0, path);
            return res;
        }

        private void backtracking(List<List<Integer>> res, int[][] graph, int start, List<Integer> path) {
            if(graph[start].length==0 || start==graph.length-1) {
                if(start==graph.length-1) {
                    res.add(new ArrayList<>(path));
                }
                return;
            }

            for(int next:graph[start]) {
                path.add(next);
                backtracking(res, graph, next, path);
                path.remove(path.size()-1);
            }
        }
    }
}

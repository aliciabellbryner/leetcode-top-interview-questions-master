package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Problem_1376_TimeNeededtoInformAllEmployees_G {

    //https://leetcode-cn.com/problems/time-needed-to-inform-all-employees/solution/1376-tong-zhi-suo-you-yuan-gong-suo-xu-d-7py4/
    class Solution {
        public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
            // 如果就一个人，就没有消息传递
            if (n == 1) return 0;
            int ans = 0;
            int[] time = new int[n];
            time[headID] = -1;
            // 遍历进行消息获取
            for (int i = 0; i < n; i++){
                if (time[i] == 0){
                    time[i] = getTime(manager, informTime, time, i);
                }
                // 更新最长时间
                if (time[i] > ans)
                    ans = time[i];
            }
            return ans;
        }
        private int getTime(int[] manager, int[] informTime, int[] time, int i){
            // 获取负责人坐标
            int aim = manager[i];
            // 如果是总负责人，就不在意他的时间
            if (time[aim] == -1) return informTime[aim];
            // 如果负责人的时间未获取，则先获取负责人的时间
            if (time[aim] == 0) {
                time[aim] = getTime(manager, informTime, time, aim);
            }
            // 返回负责人时间+负责人传递消息耗时
            return time[aim] + informTime[aim];
        }
    }

    //https://leetcode.com/problems/time-needed-to-inform-all-employees/discuss/532560/JavaC%2B%2BPython-DFS
    public int numOfMinutes(final int n, final int headID, final int[] manager, final int[] informTime) {
        final Map<Integer, List<Integer>> graph = new HashMap<>();
        int total = 0;
        for (int i = 0; i < manager.length; i++) {
            int j = manager[i];
            if (!graph.containsKey(j))
                graph.put(j, new ArrayList<>());
            graph.get(j).add(i);
        }
        return dfs(graph, informTime, headID);
    }

    private int dfs(final Map<Integer, List<Integer>> graph, final int[] informTime, final int cur) {
        int max = 0;
        if (!graph.containsKey(cur))
            return max;
        for (int i = 0; i < graph.get(cur).size(); i++)
            max = Math.max(max, dfs(graph, informTime, graph.get(cur).get(i)));
        return max + informTime[cur];
    }
}

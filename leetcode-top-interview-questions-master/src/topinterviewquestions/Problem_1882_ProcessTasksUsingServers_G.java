package topinterviewquestions;

import java.util.PriorityQueue;

public class Problem_1882_ProcessTasksUsingServers_G {

    //https://leetcode-cn.com/problems/process-tasks-using-servers/solution/process-tasks-using-servers-by-leetcode-rot1m/962974
    class Solution {
        class ServerState {
            int idx;
            int weight;
            int ending;
            public ServerState(int i, int w, int e) {
                idx = i;
                weight = w;
                ending = e;
            }
        }

        public int[] assignTasks(int[] servers, int[] tasks) {
            int n = servers.length, m = tasks.length;
            int[] ans = new int[m];
            PriorityQueue<ServerState> ready = new PriorityQueue<>((a, b) -> {
                return (a.weight == b.weight) ? a.idx - b.idx : a.weight - b.weight;
            });
            for (int i = 0; i < n; i++) {
                ready.offer(new ServerState(i, servers[i], 0));
            }
            PriorityQueue<ServerState> busy = new PriorityQueue<>((a, b) -> {
                return (a.ending == b.ending) ? ((a.weight == b.weight) ? a.idx - b.idx : a.weight - b.weight) : a.ending - b.ending;
            });
            for (int j = 0; j < m; j++) {
                while (!busy.isEmpty() && busy.peek().ending <= j) {
                    ready.offer(busy.poll());
                }
                // 如果暂时没有可用的服务器，就用最先完成服务的那个，也就是busy的堆顶
                // 更新服务结束时间，与下标j无关，因为已经不是从j时刻开始了
                if (ready.isEmpty()) {
                    ServerState top = busy.poll();
                    top.ending += tasks[j];
                    ans[j] = top.idx;
                    busy.offer(top);
                } else {
                    ServerState serv = ready.poll();
                    serv.ending = j + tasks[j];
                    ans[j] = serv.idx;
                    busy.offer(serv);
                }
            }
            return ans;
        }
    }


    //https://leetcode.com/problems/process-tasks-using-servers/discuss/1239780/Java-Two-heaps-time:-O((N+M)*logN).-Detailed-explanation/1028283
    public int[] assignTasks(int[] servers, int[] tasks) {
        int time = 0, len = tasks.length;

        PriorityQueue<Integer> ready = new PriorityQueue<>((a, b) ->
                servers[a] == servers[b] ? a - b : servers[a] - servers[b]);
        PriorityQueue<int[]> running = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        for (int i = 0; i < servers.length; i++) {
            ready.offer(i);
        }

        int index = 0;
        int[] res = new int[len];

        while (index < len) {
            if (ready.isEmpty())
                time = Math.max(time, running.peek()[1]);
            while (!running.isEmpty() && running.peek()[1] == time)
                ready.offer(running.poll()[0]);
            while (!ready.isEmpty() && index < len && time >= index) {
                int serverId = ready.poll();
                running.offer(new int[]{serverId, time + tasks[index]});
                res[index++] = serverId;
            }
            time++;
        }

        return res;
    }
}

package NewsBreak;

import java.util.LinkedList;
import java.util.Queue;

public class Problem_0785_GraphBipartite {
    //DFS
    public boolean isBipartite_DFS(int[][] graph) {
        int N = graph.length;
        int[] colors = new int[N];
        for (int i = 0; i < N; i++) {
            if (colors[i] == 0 && !isValid(graph, colors, 1, i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isValid(int[][] graph, int[] colors, int color, int idx) {
        if (colors[idx] != 0) {
            return color == colors[idx];
        }
        colors[idx] = color;
        for (int nei : graph[idx]) {
            if (!isValid(graph, colors, -color, nei)) {
                return false;
            }
        }
        return true;
    }
    //BFS
    public boolean isBipartite_BFS(int[][] graph) {
        int N = graph.length;
        int[] colors = new int[N];

        for (int i = 0; i < N; i++) {
            if (colors[i] != 0) {
                continue;
            }
            Queue<Integer> q = new LinkedList<>();
            q.add(i);
            colors[i] = 1;
            while (!q.isEmpty()) {
                int cur = q.poll();
                for (int next : graph[cur]) {
                    if (colors[next] == 0) {
                        colors[next] = -colors[cur];
                        q.add(next);
                    } else if (colors[next] != -colors[cur]) {
                        return false;
                    }
                }

            }
        }

        return true;
    }


    //WRONG solution
    //we cannot use this solution as it is possible we can have disconnected graph
    //will get true if let's say a node is disconnected,
    // [[],[2,4,6],[1,4,8,9],[7,8],[1,2,8,9],[6,9],[1,5,7,8,9],[3,6,9],[2,3,4,6,9],[2,4,5,6,7,8]],
    // node 0 is disconnect, so it will return true, but actually it is false;
    public boolean isBipartite_WRONG(int[][] graph) {
        int N = graph.length;
        int[] colors = new int[N];
        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        colors[0] = 1;
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int cur = q.poll();
                for (int next : graph[cur]) {
                    if (colors[next] == 0) {
                        colors[next] = -colors[cur];
                        q.add(next);
                    } else if (colors[next] != -colors[cur]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}

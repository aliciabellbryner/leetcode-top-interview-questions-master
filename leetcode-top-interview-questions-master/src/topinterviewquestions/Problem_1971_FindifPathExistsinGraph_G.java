package topinterviewquestions;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/*
Constraints:

1 <= n <= 2 * 105
0 <= edges.length <= 2 * 105
edges[i].length == 2
0 <= ui, vi <= n - 1
ui != vi
0 <= source, destination <= n - 1
There are no duplicate edges.
There are no self edges.
 */
public class Problem_1971_FindifPathExistsinGraph_G {
    //union find
    class Union {
        private int[] parent;
        private int N;

        public Union(int n){
            this.N = n;
            this.parent = new int[this.N];
            for(int i = 0; i < this.N; i++){
                this.parent[i] = i;
            }
        }

        public boolean areConnected(int u, int v){
            return find(u) == find(v);
        }

        public void union(int u, int v){
            if(u != v){
                int a = find(u);
                int b = find(v);
                parent[a] = b;
            }
        }

        private int find(int u){
            int x = u;
            while(x != this.parent[x]){
                x = this.parent[x];
            }

            this.parent[u] = x;
            return x;
        }
    }

    class Solution {
        public boolean validPath(int n, int[][] edges, int start, int end) {
            Union set = new Union(n);
            for(int[] edge : edges){
                set.union(edge[0], edge[1]);
            }

            return set.areConnected(start, end);
        }
    }


    //DFS
    class Solution2 {
        private boolean seen;

        public boolean validPath(int n, int[][] edges, int start, int end) {
            boolean[] visited = new boolean[n];
            HashSet<Integer>[] graph = new HashSet[n];
            int i, j;

            for(i = 0; i < n; i++){
                graph[i] = new HashSet<Integer>();
            }

            for(int[] edge : edges){
                graph[edge[0]].add(edge[1]);
                graph[edge[1]].add(edge[0]);
            }

            if(graph[start].contains(end)){  // direct connection exists
                return true;
            }

            seen = false;
            dfs(graph, visited, start, end);
            return seen;
        }

        private void dfs(HashSet<Integer>[] graph, boolean[] visited, int start, int end){
            if(!visited[start] && !seen){
                if(start == end){
                    seen = true;
                    return;
                }

                visited[start] = true;
                for(Integer neighbor : graph[start]){
                    dfs(graph, visited, neighbor, end);
                }
            }
        }
    }


    //BFS
    class Solution3 {
        public boolean validPath(int n, int[][] edges, int start, int end) {
            boolean[] visited = new boolean[n];
            HashSet<Integer>[] graph = new HashSet[n];
            int i, j;

            for(i = 0; i < n; i++){
                graph[i] = new HashSet<Integer>();
            }

            for(int[] edge : edges){
                graph[edge[0]].add(edge[1]);
                graph[edge[1]].add(edge[0]);
            }

            if(graph[start].contains(end)){  // direct connection exists
                return true;
            }

            Queue<Integer> queue = new LinkedList<Integer>();
            int N, current;
            queue.offer(start);
            visited[start] = true;

            while(!queue.isEmpty()){
                N = queue.size();
                for(i = 0; i < N; i++){
                    current = queue.poll();
                    if(current == end){
                        return true;
                    }

                    for(Integer neighbor : graph[current]){
                        if(!visited[neighbor]){
                            visited[neighbor] = true;
                            queue.offer(neighbor);
                        }
                    }
                }
            }

            return false;
        }
    }


}

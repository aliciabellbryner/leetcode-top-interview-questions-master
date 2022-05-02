package topinterviewquestions;

import java.util.*;

public class Problem_1377_FrogPositionAfterTSeconds_G {

    //https://leetcode.com/problems/frog-position-after-t-seconds/discuss/532505/Java-Straightforward-BFS-Clean-code-O(N)
    class Solution {
        public double frogPosition(int n, int[][] edges, int t, int target) {
            List<Integer>[] graph = new List[n];
            for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
            for (int[] e : edges) {
                graph[e[0] - 1].add(e[1] - 1);
                graph[e[1] - 1].add(e[0] - 1);
            }
            boolean[] visited = new boolean[n]; visited[0] = true;
            double[] prob = new double[n]; prob[0] = 1f;
            Queue<Integer> q = new LinkedList<>(); q.offer(0);
            while (!q.isEmpty() && t-- > 0) {
                for (int size = q.size(); size > 0; size--) {
                    int u = q.poll(), nextVerticesCount = 0;
                    for (int v : graph[u]) if (!visited[v]) nextVerticesCount++;
                    for (int v : graph[u]) {
                        if (!visited[v]) {
                            visited[v] = true;
                            q.offer(v);
                            prob[v] = prob[u] / nextVerticesCount;
                        }
                    }
                    if (nextVerticesCount > 0) prob[u] = 0; // frog don't stay vertex u, he keeps going to the next vertex
                }
            }
            return prob[target - 1];
        }
    }


    //https://leetcode-cn.com/problems/frog-position-after-t-seconds/solution/bfs-zhe-ge-qing-wa-shi-si-xin-yan-by-joseph1314/
    class Solution2 {
        class  node{
            int id;
            double p;
            int t;
            node(int i,double pp,int tt){
                id=i;
                p=pp;
                t=tt;
            }
        }
        public double frogPosition(int n, int[][] edges, int t, int target) {
            Set<Integer>[] sets=new Set[n+1];
            for(int i=0;i<=n;i++) sets[i]=new HashSet<Integer>();
            for(int[] e:edges){
                sets[e[0]].add(e[1]);
                sets[e[1]].add(e[0]);
            }
            Queue<node> q=new LinkedList<>();
            q.add(new node(1,1.000000,0));
            boolean[] vis=new boolean[n+1];
            vis[1]=true;
            while(!q.isEmpty()){
                node u=q.poll();
                if(u.t==t&&u.id==target){
                    return u.p;
                }
                int sz=0;
                for(int nb:sets[u.id]) if(!vis[nb]) sz++;
                if(u.t<t){
                    boolean find=false;
                    for(int nb:sets[u.id]){
                        if(vis[nb]) continue;
                        find=true;
                        vis[nb]=true;
                        q.add(new node(nb,u.p/sz,u.t+1));
                    }
                    if(find==false){
                        q.add(new node(u.id,u.p,u.t+1));
                    }
                }

            }
            return 0.0;

        }
    }

}

package topinterviewquestions;

import java.util.HashMap;
import java.util.Map;

public class Problem_0684_RedundantConnection_G {
    //https://leetcode.com/problems/redundant-connection/discuss/107984/10-line-Java-solution-Union-Find./222254
    //union find
    public int[] findRedundantConnection(int[][] edges) {
        int[] parents = new int[2001];
        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
        }

        for (int[] edge : edges) {
            int from = edge[0], to = edge[1];
            if(find(from, parents) == find(to, parents)) {
                return edge;
            }

            union(from, to, parents);
        }

        return null;

    }

    private int find(int node, int[] parents) {
        while(node != parents[node]) {
            node = parents[node];
        }
        return node;
    }

    private void union(int a, int b, int[] parents) {
        int aRoot = find(a, parents);
        int bRoot = find(b, parents);
        if(aRoot == bRoot) return;
        parents[aRoot] = bRoot;
    }


    //solution2, concise one
    class Solution {
        public int[] findRedundantConnection(int[][] edges) {
            int[] parent = new int[2001];//3 <= n <= 1000
            for (int i = 0; i < parent.length; i++) parent[i] = i;

            for (int[] edge: edges){
                int f = edge[0], t = edge[1];
                if (find(parent, f) == find(parent, t)) return edge;
                else parent[find(parent, f)] = find(parent, t);
            }

            return new int[2];
        }

        private int find(int[] parent, int f) {
            if (f != parent[f]) {
                parent[f] = find(parent, parent[f]);
            }
            return parent[f];
        }
    }
}

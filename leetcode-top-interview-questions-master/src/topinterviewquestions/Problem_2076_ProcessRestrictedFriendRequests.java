package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

public class Problem_2076_ProcessRestrictedFriendRequests {
    class Solution {
        private int[] parent;
        public void UnionFind(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++)
                parent[i] = i;
        }
        public int find(int p) {
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];
                p = parent[p];
            }
            return p;
        }
        // public void union(int p, int q) {
        //         int rootP = find(p);
        //         int rootQ = find(q);
        //         if (rootP == rootQ) return;
        //         parent[rootP] = rootQ;
        // }

        public boolean[] friendRequests(int n, int[][] restrictions, int[][] requests) {
            int m = requests.length;
            boolean[] res = new boolean[m];
            UnionFind(n);
            for (int i = 0; i < m; ++i) {
                int firstParent = find(requests[i][0]), secondParent = find(requests[i][1]);
                if (firstParent == secondParent) {
                    res[i] = true;
                } else {
                    boolean found = false;//found是true说明firstParent和secondParent发现至少一个conflict
                    for (int[] r: restrictions) {
                        int firstRestriction = find(r[0]), secondResctriction = find(r[1]);
                        //这就意味着他们发现了一个restriction
                        if ((firstParent == firstRestriction && secondParent == secondResctriction) || (secondParent == firstRestriction && firstParent == secondResctriction)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        parent[firstParent] = secondParent;
                        res[i] = true;
                    }
                }
            }

            return res;
        }
    }
}

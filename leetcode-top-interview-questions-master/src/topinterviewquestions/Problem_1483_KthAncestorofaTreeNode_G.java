package topinterviewquestions;

public class Problem_1483_KthAncestorofaTreeNode_G {
    // Binary Lifting https://leetcode.com/problems/kth-ancestor-of-a-tree-node/discuss/686362/JavaC%2B%2BPython-Binary-Lifting
    //A is the parent in 1 step
    //Based on this, we can find the parent in 2 steps.
    //Again, based on this, we can find the parent in 4 steps.
    //And so on.
    //
    //This will help you jump fast on the ancestor tree.
    class TreeAncestor {
        int[][] jump;
        int maxPow;

        public TreeAncestor(int n, int[] parent) {
            // log_base_2(n)
            maxPow = (int) (Math.log(n) / Math.log(2)) + 1;
            jump = new int[maxPow][n];
            jump[0] = parent;
            for (int p = 1; p < maxPow; p++) {
                for (int j = 0; j < n; j++) {
                    int pre = jump[p - 1][j];
                    jump[p][j] = pre == -1 ? -1 : jump[p - 1][pre];
                }
            }
        }

        public int getKthAncestor(int node, int k) {
            int maxPow = this.maxPow;
            while (k > 0 && node > -1) {
                if (k >= 1 << maxPow) {
                    node = jump[maxPow][node];
                    k -= 1 << maxPow;
                } else
                    maxPow -= 1;
            }
            return node;
        }
    }
}

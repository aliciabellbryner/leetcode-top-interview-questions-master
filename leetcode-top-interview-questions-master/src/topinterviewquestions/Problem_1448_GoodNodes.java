package topinterviewquestions;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Problem_1448_GoodNodes {
    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
    }

    //solution2: dfs iterative
    /*
    Time complexity: O(N)

With DFS we visit every node exactly once and do a constant amount of work each time.

Space complexity: O(N)

In the worst case scenario, where every right child has 2 children and every left child has no children (or vice-versa), our stack will contain N / 2N/2 nodes at max depth.
     */
    public class Info {
        int maxSoFar;//从root出发到自己的node，经历的最大值（不包括自己）是多少
        TreeNode node;
        public Info(TreeNode n, int m) {
            node = n;
            maxSoFar = m;
        }
    }
    public int goodNodes_2(TreeNode root) {
        int res = 0;
        Stack<Info> stack = new Stack<>();
        stack.push(new Info(root, Integer.MIN_VALUE));
        while (!stack.isEmpty()){
            Info cur = stack.pop();
            if (cur.node.val >= cur.maxSoFar) {
                res++;
            }
            if (cur.node.left != null) {
                stack.push(new Info(cur.node.left, Math.max(cur.maxSoFar, cur.node.val)));
            }
            if (cur.node.right != null) {
                stack.push(new Info(cur.node.right, Math.max(cur.maxSoFar, cur.node.val)));
            }
        }
        return res;
    }


    //Solution1: dfs recursion: time O(N) as it will iterate every node once
    //Space O(N) the worst scenario is the height of call stack can be N
    public int res = 0;
    public int goodNodes_1(TreeNode root) {
        dfs(root, Integer.MIN_VALUE);
        return res;
    }
    public void dfs(TreeNode node, int maxSoFar) {
        if (node.val >= maxSoFar) {
            res++;
        }
        if (node.left != null) {
            dfs(node.left, Math.max(node.val, maxSoFar));
        }
        if (node.right != null) {
            dfs(node.right, Math.max(node.val, maxSoFar));
        }
    }



    //solution3: bfs iterative
    /*
    Time complexity: O(N)
    With BFS we visit every node exactly once and do a constant amount of work each time.
    Space complexity: O(N)
    The worst case scenario for space with BFS is when the tree is full. In this scenario, the final level contains N / 2N/2 nodes, and our queue will hold all the nodes in the final level at some point.
     */
    public int goodNodes_3(TreeNode root) {
        int res = 0;
        Queue<Info> queue = new LinkedList<>();
        queue.add(new Info(root, Integer.MIN_VALUE));
        while (!queue.isEmpty()){
            Info cur = queue.poll();
            if (cur.node.val >= cur.maxSoFar) {
                res++;
            }
            if (cur.node.left != null) {
                queue.add(new Info(cur.node.left, Math.max(cur.maxSoFar, cur.node.val)));
            }
            if (cur.node.right != null) {
                queue.add(new Info(cur.node.right, Math.max(cur.maxSoFar, cur.node.val)));
            }
        }
        return res;
    }

}

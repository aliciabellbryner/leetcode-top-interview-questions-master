package topinterviewquestions;

import java.util.LinkedList;
import java.util.Queue;

public class Problem_0617_MergeTwoBinaryTrees {

    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }
    //recursive
    //Time complexity : O(m) A total of m nodes need to be traversed. Here, m represents the minimum number of nodes from the two given trees.
    //Space complexity : O(m). The depth of the recursion tree can go upto m in the case of a skewed tree. In average case, depth will be O(logm).
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) {
            return root1 == null ? root2 : root1;
        }
        root1.val += root2.val;
        root1.left = mergeTrees(root1.left, root2.left);
        root1.right = mergeTrees(root1.right, root2.right);
        return root1;
    }

    //iterative, bfs
    //Time complexity : O(m) A total of m nodes need to be traversed. Here, m represents the minimum number of nodes from the two given trees.
    //Space complexity : O(m). The depth of the recursion tree can go upto m in the case of a skewed tree. In average case, depth will be O(logm).
    public TreeNode mergeTrees_iterative(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) {
            return root1 == null ? root2 : root1;
        }
        //use array in the queue to manipulate at the same time
        Queue<TreeNode[]> queue = new LinkedList<>();
        queue.offer(new TreeNode[]{root1, root2});

        while (!queue.isEmpty()) {
            TreeNode[] nodes = queue.poll();
            //merge 2 into 1 when it is not null
            if (nodes[1] == null) {
                continue;
            }
            //nodes[0] must not be null
            nodes[0].val += nodes[1].val;
            if (nodes[0].left == null) {
                nodes[0].left = nodes[1].left;
            } else {
                queue.offer(new TreeNode[]{nodes[0].left, nodes[1].left});
            }
            if (nodes[0].right == null) {
                nodes[0].right = nodes[1].right;
            } else {
                queue.offer(new TreeNode[]{nodes[0].right, nodes[1].right});
            }
        }
        return root1;
    }
}

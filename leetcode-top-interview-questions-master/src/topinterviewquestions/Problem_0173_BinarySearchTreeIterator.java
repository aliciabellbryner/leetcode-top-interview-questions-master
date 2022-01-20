package topinterviewquestions;

import java.util.Stack;

public class Problem_0173_BinarySearchTreeIterator {

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

    //https://leetcode.com/problems/binary-search-tree-iterator/discuss/52525/My-solutions-in-3-languages-with-Stack
    //When next() be called, I just pop one element and process its right child as new root.
    //The code is pretty straightforward.
    //So this can satisfy O(h) memory, hasNext() in O(1) time,
    //The average time complexity of next() function is O(1)
    // As the next function can be called n times at most, and the number of right nodes in
    // self.pushAll(tmpNode.right) function is maximal n in a tree which has n nodes,
    // so the amortized time complexity is O(1).
   private Stack<TreeNode> stack = new Stack<TreeNode>();

    public Problem_0173_BinarySearchTreeIterator(TreeNode root) {
        pushAllLeftNodes(root);
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    /** @return the next smallest number */
    public int next() {
        TreeNode node = stack.pop();
        pushAllLeftNodes(node.right);
        return node.val;
    }

    private void pushAllLeftNodes(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }
}

package topinterviewquestions;

//
//Here, N and H are the number of nodes and the max height of the given tree respectively.
//Time complexity: O(N).
//In largestBSTSubtree function, we traverse all nodes of the given tree,
// and for each node, we find if the subtree rooted at the current node is a BST
// which takes O(1) time and if it is a BST, we calculate the number of nodes in this subtree
// which also takes O(1) time. Thus, for N nodes this algorithm takes O(N) time.
//Space complexity: O(N).
//Recursive stack can take at most O(H) space; in the worst-case scenario, the height of the tree will equal N.
public class Problem_0333_LargestBSTSubtree {
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
    class Info {
        public int maxNode, minNode, maxSize;

        Info(int minVal, int maxVal, int maxSize) {
            this.maxNode = maxVal;
            this.minNode = minVal;
            this.maxSize = maxSize;
        }
    }

    class Solution {
        public int largestBSTSubtree(TreeNode root) {
            return largestBSTSubtreeHelper(root).maxSize;
        }

        public Info largestBSTSubtreeHelper(TreeNode root) {
            // An empty tree is a BST of size 0.
            if (root == null) {
                return new Info(Integer.MAX_VALUE, Integer.MIN_VALUE, 0);
            }

            // Get values from left and right subtree of current tree.
            Info left = largestBSTSubtreeHelper(root.left);
            Info right = largestBSTSubtreeHelper(root.right);

            // Current node is greater than max in left AND smaller than min in right, it is a BST.
            if (left.maxNode < root.val && root.val < right.minNode) {
                // It is a BST.
                return new Info(Math.min(root.val, left.minNode), Math.max(root.val, right.maxNode),
                        left.maxSize + right.maxSize + 1);
            } else {
                // Otherwise, return [-inf, inf] so that parent can't be valid BST
                return new Info(Integer.MIN_VALUE, Integer.MAX_VALUE, Math.max(left.maxSize, right.maxSize));
            }
        }
    }
}

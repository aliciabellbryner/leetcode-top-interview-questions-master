package topinterviewquestions;

public class Problem_0222_CountCompleteTreeNodes_G {
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

  //https://leetcode.com/problems/count-complete-tree-nodes/discuss/61948/Accepted-Easy-Understand-Java-Solution
  public class Solution1 {
      public int countNodes(TreeNode root) {

          int leftDepth = leftDepth(root);
          int rightDepth = rightDepth(root);

          if (leftDepth == rightDepth) {
              return (1 << leftDepth) - 1;
          } else {
              return 1 + countNodes(root.left) + countNodes(root.right);
          }
      }

      private int rightDepth(TreeNode root) {
          // 相当于是以最右的node为基准看能到那个depth
          int dep = 0;
          while (root != null) {
              root = root.right;
              dep++;
          }
          return dep;
      }

      private int leftDepth(TreeNode root) {
          // 相当于是以最左的node为基准看能到那个depth
          int dep = 0;
          while (root != null) {
              root = root.left;
              dep++;
          }
          return dep;
      }
  }

    //Clean Java Solution has the same time complexity.
    //https://leetcode.com/problems/count-complete-tree-nodes/discuss/61958/Concise-Java-solutions-O(log(n)2)/208740
    //The height of a tree can be found by just going left. Let a single node tree have height 0. Find the height h of the whole tree. If the whole tree is empty, i.e., has height -1, there are 0 nodes.
    //
    //Otherwise check whether the height of the right subtree is just one less than that of the whole tree, meaning left and right subtree have the same height.
    //
    //If yes, then the last node on the last tree row is in the right subtree and the left subtree is a full tree of height h-1. So we take the 2^h-1 nodes of the left subtree plus the 1 root node plus recursively the number of nodes in the right subtree.
    //If no, then the last node on the last tree row is in the left subtree and the right subtree is a full tree of height h-2. So we take the 2^(h-1)-1 nodes of the right subtree plus the 1 root node plus recursively the number of nodes in the left subtree.
    //Since I halve the tree in every recursive step, I have O(log(n)) steps. Finding a height costs O(log(n)). So overall O(log(n)^2).
    class Solution {
        public int countNodes(TreeNode root) {
            if (root == null) {
                return 0;
            }

            int totalDepth = getDepth(root);
            int rightDepth = getDepth(root.right);

            if (rightDepth + 1 == totalDepth) {
                return (1 << totalDepth - 1) + countNodes(root.right);
            }
            return (1 << totalDepth - 2) + countNodes(root.left);
        }

        private int getDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            return 1 + getDepth(root.left);
        }
    }

    //https://leetcode.com/problems/count-complete-tree-nodes/discuss/61958/Concise-Java-solutions-O(log(n)2)
    class Solution2 {
        int height(TreeNode root) {
            return root == null ? -1 : 1 + height(root.left);
        }
        public int countNodes(TreeNode root) {
            int h = height(root);
            return h < 0 ? 0 :
                    height(root.right) == h-1 ? (1 << h) + countNodes(root.right)
                            : (1 << h-1) + countNodes(root.left);
        }
    }



}

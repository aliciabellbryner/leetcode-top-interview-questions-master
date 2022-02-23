package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;
/*
Given the root of a binary search tree (BST) and an integer target, split the tree into two subtrees where one subtree has nodes that are all smaller or equal to the target value, while the other subtree has all nodes that are greater than the target value. It Is not necessarily the case that the tree contains a node with the value target.

Additionally, most of the structure of the original tree should remain. Formally, for any child c with parent p in the original tree, if they are both in the same subtree after the split, then node c should still have the parent p.

Return an array of the two roots of the two subtrees.



Example 1:


Input: root = [4,2,6,1,3,5,7], target = 2
Output: [[2,1],[4,3,6,null,null,5,7]]
Example 2:

Input: root = [1], target = 1
Output: [[1],[]]


Constraints:

The number of nodes in the tree is in the range [1, 50].
0 <= Node.val, target <= 1000
 */
public class Problem_0776_SplitBST {
/*
Approach 1: Recursion
Intuition and Algorithm

The root node either belongs to the first half or the second half. Let's say it belongs to the first half.

Then, because the given tree is a binary search tree (BST), the entire subtree at root.left must be in the first half. However, the subtree at root.right may have nodes in either halves, so it needs to be split.


Diagram of tree being split

In the diagram above, the thick lines represent the main child relationships between the nodes, while the thinner colored lines represent the subtrees after the split.

Lets say our secondary answer bns = split(root.right) is the result of such a split. Recall that bns[0] and bns[1] will both be BSTs on either side of the split. The left half of bns must be in the first half, and it must be to the right of root for the first half to remain a BST. The right half of bns is the right half in the final answer.


Diagram of how root tree connects to split of subtree at root.right

The diagram above explains how we merge the two halves of split(root.right) with the main tree, and illustrates the line of code root.right = bns[0] in the implementations.


Complexity Analysis

Time Complexity: O(N)O(N), where NN is the number of nodes in the input tree, as each node is checked once.

Space Complexity: O(N)O(N).
 */

    class Solution {
        public TreeNode[] splitBST(TreeNode root, int V) {
            if (root == null)
                return new TreeNode[]{null, null};
            else if (root.val <= V) {
                TreeNode[] bns = splitBST(root.right, V);
                root.right = bns[0];
                bns[0] = root;
                return bns;
            } else {
                TreeNode[] bns = splitBST(root.left, V);
                root.left = bns[1];
                bns[1] = root;
                return bns;
            }
        }
    }

    //diss
    class Solution2 {
        public TreeNode[] splitBST(TreeNode root, int V) {
            if (root == null) {
                return new TreeNode[2];
            }
            if (root.val > V) {
                TreeNode[] next = splitBST(root.left, V);
                root.left = next[1];
                return new TreeNode[]{next[0], root};
            }
            else {
                TreeNode[] next = splitBST(root.right, V);
                root.right = next[0];
                return new TreeNode[]{root, next[1]};
            }
        }
    }
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

}

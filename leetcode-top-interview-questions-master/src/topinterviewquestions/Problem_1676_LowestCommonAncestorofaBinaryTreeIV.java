package topinterviewquestions;
/*
Given the root of a binary tree and an array of TreeNode objects nodes, return the lowest common ancestor (LCA) of all the nodes in nodes. All the nodes will exist in the tree, and all values of the tree's nodes are unique.

Extending the definition of LCA on Wikipedia: "The lowest common ancestor of n nodes p1, p2, ..., pn in a binary tree T is the lowest node that has every pi as a descendant (where we allow a node to be a descendant of itself) for every valid i". A descendant of a node x is a node y that is on the path from node x to some leaf node.



Example 1:


Input: root = [3,5,1,6,2,0,8,null,null,7,4], nodes = [4,7]
Output: 2
Explanation: The lowest common ancestor of nodes 4 and 7 is node 2.
Example 2:


Input: root = [3,5,1,6,2,0,8,null,null,7,4], nodes = [1]
Output: 1
Explanation: The lowest common ancestor of a single node is the node itself.

Example 3:


Input: root = [3,5,1,6,2,0,8,null,null,7,4], nodes = [7,6,2,4]
Output: 5
Explanation: The lowest common ancestor of the nodes 7, 6, 2, and 4 is node 5.


Constraints:

The number of nodes in the tree is in the range [1, 104].
-109 <= Node.val <= 109
All Node.val are unique.
All nodes[i] will exist in the tree.
All nodes[i] are distinct.
 */

import java.util.HashSet;
import java.util.Set;

public class Problem_1676_LowestCommonAncestorofaBinaryTreeIV {
    /*
    diss
     */
    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode(int x) { val = x; }
     * }
     */

    public class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
          TreeNode(int x) { val = x; }
      }
    class Solution {
        TreeNode lca = null;
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode[] nodes) {
            Set<Integer> targetNodes = new HashSet<>();
            for(TreeNode node : nodes) {
                targetNodes.add(node.val);
            }
            helper(root, targetNodes);
            return lca;
        }

        int helper(TreeNode root, Set<Integer> nodes) {
            if(root == null) return 0;
            int leftCount = helper(root.left, nodes);
            int rightCount = helper(root.right, nodes);
            int foundCount = leftCount + rightCount;
            if(nodes.contains(root.val)) {
                foundCount++;
            }
            if(foundCount == nodes.size() && lca == null) {
                lca = root;
            }

            return foundCount;
        }
    }


    class Solution2 {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode[] nodes) {
            Set<Integer> s = new HashSet<>();
            for (TreeNode n : nodes) s.add(n.val);
            return lcaHelper(root, s);
        }

        private TreeNode lcaHelper(TreeNode root, Set<Integer> s) {
            if (root == null) return null;
            if (s.contains(root.val)) return root;
            TreeNode left = lcaHelper(root.left, s);
            TreeNode right = lcaHelper(root.right, s);
            if (left != null && right != null) return root;
            else return (left != null) ? left : right;
        }
    }


    class Solution3 {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode[] nodes) {
            TreeNode prev = null;
            if (nodes == null || nodes.length == 0) {
                return null;
            }
            if (nodes.length == 1) {
                return nodes[0];
            }
            for (int i = 0; i < nodes.length - 1; i++) {
                if (prev == null) {
                    prev = helper(root, nodes[i], nodes[i + 1]);
                } else {
                    prev = helper(root, prev, nodes[i + 1]);
                }
            }
            return prev;
        }

        private TreeNode helper(TreeNode root, TreeNode a, TreeNode b) {
            if (root == null || a == root || b == root) {
                return root;
            }
            TreeNode left = helper(root.left, a, b), right = helper(root.right, a, b);
            if (left != null && right != null) {
                return root;
            }
            return left == null ? right : left;
        }
    }

}

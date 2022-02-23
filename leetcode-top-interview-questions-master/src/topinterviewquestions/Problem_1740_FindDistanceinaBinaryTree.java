package topinterviewquestions;
/*
Given the root of a binary tree and two integers p and q, return the distance between the nodes of value p and value q in the tree.

The distance between two nodes is the number of edges on the path from one to the other.



Example 1:


Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 0
Output: 3
Explanation: There are 3 edges between 5 and 0: 5-3-1-0.
Example 2:


Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 7
Output: 2
Explanation: There are 2 edges between 5 and 7: 5-2-7.
Example 3:


Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 5
Output: 0
Explanation: The distance between a node and itself is 0.


Constraints:

The number of nodes in the tree is in the range [1, 104].
0 <= Node.val <= 109
All Node.val are unique.
p and q are values in the tree.
 */
public class Problem_1740_FindDistanceinaBinaryTree {
    /*
If you have not solved 236. Lowest Common Ancestor of a Binary Tree yet, I would suggest you doing so first.

Let's recap the solution for finding the LCA using divide and conquer:

class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if (root == p || root == q) return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) return root;
        if (left != null) return left;
        if (right != null) return right;

        return null;
    }
}
A naive solution for this question would be to find the LCA of p and q first and then add up the distances from the LCA node to p and q respectively. The time complexity of this approach would still fall under O(n), however in the worst case scenario, the program could have traversed the entire tree for 3 times which is not optimal.

Actually, by modifying the code for finding the LCA a little bit, we could solve this question in one pass of traversal:
     */

    class Solution1 {

        private int result;

        public int findDistance(TreeNode root, int p, int q) {
            if (p == q) return 0;
            result = -1;
            dfs(root, p, q);
            return result;
        }

        /**
         The return value means the distance from root node to EITHER p OR q. If
         neither p nor q are reachable from the root, return -1.

         It is either p or q but not both, because if the root node can reach both
         p and q, it is a common ancestor of p and q and the answer should already
         be available.
         **/
        private int dfs(TreeNode root, int p, int q) {
            if (root == null) return -1;

            int left = dfs(root.left, p, q);
            int right = dfs(root.right, p, q);

            if (root.val == p || root.val == q) {
                // root is p or q, but none of p or q is a descendent of root.
                // The distance from root to one of p and q is 0 in this case.
                if (left < 0 && right < 0) {
                    return 0;
                }

                // root is p or q, and root is also the LCA of p and q.
                result = 1 + (left >= 0 ? left : right);
                return -1;
            }

            // root is neither p nor q, but it is the LCA of p and q.
            if (left >= 0 && right >= 0) {
                result = left + right + 2;
                return -1;
            }

            if (left >= 0) {
                return left + 1;
            }

            if (right >= 0) {
                return right + 1;
            }

            return -1;
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

    //better solution:
    class Solution {
        boolean flag = false;
        public int findDistance(TreeNode root, int p, int q) {
            if(p == q){
                return 0;
            }
            if(root == null){
                return -1;
            }
            int left = findDistance(root.left, p, q);
            int right = findDistance(root.right, p, q);
            if(root.val == p || root.val == q){
                if(left >= 0 || right >= 0){
                    flag = true;
                    return 1 + Math.max(left, right);
                }
                return 0;
            }
            if(left >= 0 && right >= 0){
                flag = true;
                return left + right + 2;
            }
            if(left >= 0 || right >= 0){
                if(flag){
                    return Math.max(left, right);
                }
                return 1 + Math.max(left, right);
            }
            return -1;
        }
    }
}

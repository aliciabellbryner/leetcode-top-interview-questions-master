package topinterviewquestions;
/*
Given the root of a binary tree, return the number of nodes where the value of the node is equal to the sum of the values of its descendants.

A descendant of a node x is any node that is on the path from node x to some leaf node. The sum is considered to be 0 if the node has no descendants.



Example 1:


Input: root = [10,3,4,2,1]
Output: 2
Explanation:
For the node with value 10: The sum of its descendants is 3+4+2+1 = 10.
For the node with value 3: The sum of its descendants is 2+1 = 3.
Example 2:


Input: root = [2,3,null,2,null]
Output: 0
Explanation:
No node has a value that is equal to the sum of its descendants.
Example 3:


Input: root = [0]
Output: 1
For the node with value 0: The sum of its descendants is 0 since it has no descendants.


Constraints:

The number of nodes in the tree is in the range [1, 105].
0 <= Node.val <= 105
 */
public class Problem_1973_CountNodesEqualToSumOfDescendants {
    /*
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode() {}
     *     TreeNode(int val) { this.val = val; }
     *     TreeNode(int val, TreeNode left, TreeNode right) {
     *         this.val = val;
     *         this.left = left;
     *         this.right = right;
     *     }
     * }
     */

    /*
    	int r = 0;

    public int equalToDescendants(TreeNode root) {
        sum(root);
        return r;
    }

    int sum(TreeNode node) {
        if (node == null)
            return 0;
        int sum = sum(node.left) + sum(node.right);
        if (sum == node.val)
            r++;
        return sum + node.val;
    }
    */

//Or without the global variable

	/*
    public int equalToDescendants(TreeNode root) {return traverse(root)[1];}

    int[] traverse(TreeNode node) {
        if (node == null)
            return new int[]{0, 0};
        int L[] = traverse(node.left), R[] = traverse(node.right), sum = L[0] + R[0];
        return new int[]{sum + node.val, L[1] + R[1] + (sum == node.val ? 1 : 0)};
    }
     */

//Java 8ms beats 100%, DFS strategy
        /*
Runtime: 8 ms, faster than 100.00% of Java online submissions for Count Nodes Equal to Sum of Descendants.
Memory Usage: 69 MB, less than 90.16% of Java online submissions for Count Nodes Equal to Sum of Descendants.
*/
    /*
    class Solution {

        int numberOfNodes = 0; // what we'll return

        public int equalToDescendants(TreeNode root) {
            returnSumOfDescendantNodes(root);
            return numberOfNodes;
        }

        private int returnSumOfDescendantNodes(TreeNode root) { // recursive
            int sum = 0;

            if (root.left != null) {
                sum += root.left.val;
                sum += returnSumOfDescendantNodes(root.left);
            }

            if (root.right != null) {
                sum += root.right.val;
                sum += returnSumOfDescendantNodes(root.right);
            }

            if (sum == root.val) {
                ++numberOfNodes;
            }

            return sum;
        }
    }
     */
}

package topinterviewquestions;
//是求p节点的后一个
/*
Given the root of a binary search tree and a node p in it, return the in-order successor of that node in the BST. If the given node has no in-order successor in the tree, return null.

The successor of a node p is the node with the smallest key greater than p.val.



Example 1:


Input: root = [2,1,3], p = 1
Output: 2
Explanation: 1's in-order successor node is 2. Note that both p and the return value is of TreeNode type.
Example 2:


Input: root = [5,3,6,2,4,null,null,1], p = 6
Output: null
Explanation: There is no in-order successor of the current node, so the answer is null.


Constraints:

The number of nodes in the tree is in the range [1, 104].
-105 <= Node.val <= 105
All Nodes will have unique values.
 */
public class Problem_0285_InorderSuccessorInBST {

	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	//using Morris traverse
	//time O(N)
	//space O(1)
	public static TreeNode inorderSuccessor(TreeNode head, TreeNode p) {
		if (head == null) {
			return null;
		}
		TreeNode cur = head;
		TreeNode mostRight = null;
		TreeNode pre = null;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
				}
			}
			if (pre == p) {
				return cur;
			} else {
				pre = cur;
			}
			cur = cur.right;
		}
		return null;
	}

	//Approach 2: Using BST properties
	//https://leetcode.com/problems/inorder-successor-in-bst/solution/
	//time O(N)
	//space O(1)
	public TreeNode inorderSuccessor2(TreeNode root, TreeNode p) {
		TreeNode res = null;
		while (root != null) {
			if (root.val <= p.val) {
				root = root.right;
			} else {
				res = root;
				root = root.left;
			}
		}

		return res;
	}

}

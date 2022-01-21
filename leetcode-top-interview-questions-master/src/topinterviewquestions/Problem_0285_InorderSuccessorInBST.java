package topinterviewquestions;
//是求p节点的后一个
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

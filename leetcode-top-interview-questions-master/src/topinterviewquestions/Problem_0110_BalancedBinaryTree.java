package topinterviewquestions;

import java.util.Stack;

public class Problem_0110_BalancedBinaryTree {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int val) {
			this.val = val;
		}
	}

	//bottom-up recursion: using an inner-class
	//Time complexity : O(N), where N is number of nodes, since we visit each node not more than 2 times.
	//For every subtree, we compute its height in constant time as well as compare the height of its children.
	//Space complexity : O(n). The recursion stack may go up to O(n) if the tree is unbalanced.
	public static class Info {
		public boolean isBalanced;
		public int height;

		public Info(boolean i, int h) {
			isBalanced = i;
			height = h;
		}
	}

	public static boolean isBalanced(TreeNode root) {
		return process(root).isBalanced;
	}

	public static Info process(TreeNode root) {
		if (root == null) {
			return new Info(true, 0);
		}
		Info leftInfo = process(root.left);
		Info rightInfo = process(root.right);
		int height = Math.max(leftInfo.height, rightInfo.height) + 1;
		boolean isBalanced = leftInfo.isBalanced && rightInfo.isBalanced
				&& Math.abs(leftInfo.height - rightInfo.height) < 2;
		return new Info(isBalanced, height);
	}


	//top-down recursion
	//recursive: https://leetcode.com/problems/balanced-binary-tree/solution/
	//Time complexity : O(nlogn), the height of recursion is logn, so the time is O(nlogN) (not sure!!!)
	//space: O(n),The recursion stack may contain all nodes if the tree is skewed.
	public boolean isBalanced2(TreeNode root) {
		if (root == null) {
			return true;
		}
		if (isBalanced(root.left) && isBalanced(root.right) && Math.abs(getLength(root.left) - getLength(root.right)) < 2) {
			return true;
		} else {
			return false;
		}
	}

	private int getLength(TreeNode root) {
		if (root == null) {
			return 0;
		} else {
			return 1 + Math.max(getLength(root.left), getLength(root.right));
		}
	}

}

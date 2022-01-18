package topinterviewquestions;

import java.util.LinkedList;
import java.util.Queue;

public class Problem_0101_SymmetricTree {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode (int v) {
			this.val = v;
		}
	}

	//recursive
	//Time complexity : O(n). Because we traverse the entire input tree once, the total run time is O(n), where nn is the total number of nodes in the tree.
	//Space complexity : The number of recursive calls is bound by the height of the tree. In the worst case, the tree is linear and the height is in O(n). Therefore, space complexity due to recursive calls on the stack is O(n) in the worst case.
	public boolean isSymmetric(TreeNode root) {
		return isMirror(root, root);
	}

	// 一棵树是原始树  head1
	// 另一棵是翻面树  head2
	public static boolean isMirror(TreeNode head1, TreeNode head2) {
		if (head1 == null && head2 == null) {
			return true;
		}
		if (head1 != null && head2 != null) {
			return head1.val == head2.val 
					&& isMirror(head1.left, head2.right) 
					&& isMirror(head1.right, head2.left);
		}
		// 一个为空，一个不为空  false
		return false;
	}

	//iterative
	//Time complexity : O(n). Because we traverse the entire input tree once, the total run time is O(n), where nn is the total number of nodes in the tree.
	//Space complexity : The number of recursive calls is bound by the height of the tree. In the worst case, the tree is linear and the height is in O(n). Therefore, space complexity due to recursive calls on the stack is O(n) in the worst case.
	public boolean isSymmetric2(TreeNode root) {
		Queue<TreeNode> q = new LinkedList<>();
		q.add(root);
		q.add(root);
		while (!q.isEmpty()) {
			TreeNode t1 = q.poll();
			TreeNode t2 = q.poll();
			if (t1 == null && t2 == null) continue;
			if (t1 == null || t2 == null) return false;
			if (t1.val != t2.val) return false;
			q.add(t1.left);
			q.add(t2.right);
			q.add(t1.right);
			q.add(t2.left);
		}
		return true;
	}

	//my solution
	public boolean isSymmetric_j(TreeNode root) {
		return helper(root, root);
	}

	public boolean helper(TreeNode t1, TreeNode t2) {
		if (t1 == null && t2 == null) {
			return true;
		}
		if (t1 == null || t2 == null || t1.val != t2.val) {
			return false;
		}
		return helper(t1.left, t2.right) && helper(t1.right, t2.left);
	}


}

package topinterviewquestions;

public class Problem_0101_SymmetricTree {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode (int v) {
			this.val = v;
		}
	}

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

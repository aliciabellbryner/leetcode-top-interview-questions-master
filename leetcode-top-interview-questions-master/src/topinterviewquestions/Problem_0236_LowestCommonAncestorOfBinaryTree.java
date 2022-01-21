package topinterviewquestions;

public class Problem_0236_LowestCommonAncestorOfBinaryTree {

	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	//time O(N): we need to visit all the nodes in the worst case
	//space O(H): H is the height of the recursion tree, worst case is O(N), In the worst case the space utilized by stack would be N
	// since the height of a skewed binary tree could be N.
	public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		return process(root, p, q).lca;
	}

	public static class Info {
		public TreeNode lca;
		public boolean findp;
		public boolean findq;

		public Info(TreeNode node, boolean f1, boolean f2) {
			lca = node;
			findp = f1;
			findq = f2;
		}
	}

	public static Info process(TreeNode node, TreeNode p, TreeNode q) {
		if (node == null) {
			return new Info(null, false, false);
		}
		Info leftInfo = process(node.left, p, q);
		Info rightInfo = process(node.right, p, q);
		boolean findp = node == p || leftInfo.findp || rightInfo.findp;
		boolean findq = node == q || leftInfo.findq || rightInfo.findq;
		TreeNode lca = null;
		if (leftInfo.lca != null) {
			lca = leftInfo.lca;
		}
		if (rightInfo.lca != null) {
			lca = rightInfo.lca;
		}
		if (lca == null) {
			if (findp && findq) {
				lca = node;
			}
		}
		return new Info(lca, findp, findq);
	}

}

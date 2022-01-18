package topinterviewquestions;

public class Problem_0098_ValidateBinarySearchTree {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int v) {
			this.val = v;
		}
	}

	//solution1: itervative:
	//Time complexity : O(N) since we visit each node exactly once.
	//Space complexity : O(N) since we keep up to the entire tree.
	public static boolean isValidBST1(TreeNode root) {
		return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
	}
	private static boolean isValidBST(TreeNode root, long minVal, long maxVal) {
		if (root == null) {
			return true;
		}
		if (root.val >= maxVal || root.val <= minVal) {
			return false;
		} else {
			return isValidBST(root.left, minVal, root.val) && isValidBST(root.right, root.val, maxVal);
		}
	}

	//solution2: using an Info class
	//Time complexity : O(N) since we visit each node exactly once.
	//Space complexity : O(N) since we keep up to the entire tree.
	public class Info {
		boolean isBST;
		int max;
		int min;
		public Info(boolean isBST, int max, int min) {
			this.isBST = isBST;
			this.max = max;
			this.min = min;
		}
	}
	public boolean isValidBST2(TreeNode root) {
		return process(root).isBST;
	}
	public Info process(TreeNode root) {
		if (root == null) {
			return null;
		}
		boolean isBST = true;
		int max = root.val;
		int min = root.val;
		Info left = process(root.left);
		Info right = process(root.right);
		if (left != null) {
			max = Math.max(max, left.max);
			min = Math.min(min, left.min);
		}
		if (right != null) {
			max = Math.max(max, right.max);
			min = Math.min(min, right.min);
		}
		if (left != null && !left.isBST) {
			isBST = false;
		}
		if (right != null && !right.isBST) {
			isBST = false;
		}
		boolean moreLeftMax = left == null ? true : root.val > left.max;
		boolean lessRightMin = right == null ? true : root.val < right.min;

		if (!(moreLeftMax && lessRightMin)) {
			isBST = false;
		}
		return new Info(isBST, max, min);
	}



	//soltion 3: use the Morris Traversal way,
	//time O(N), space O(1)
	public boolean isValidBST3(TreeNode root) {
		if (root == null) {
			return true;
		}
		TreeNode cur = root;
		TreeNode mostRight = null;
		Integer pre = null;
		boolean ans = true;
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
			if (pre != null && pre >= cur.val) {
				ans = false;
			}
			pre = cur.val;
			cur = cur.right;
		}
		return ans;
	}

}

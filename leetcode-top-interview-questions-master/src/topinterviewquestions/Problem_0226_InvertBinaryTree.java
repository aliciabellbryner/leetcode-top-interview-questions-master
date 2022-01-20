package topinterviewquestions;

import java.util.LinkedList;
import java.util.Queue;

public class Problem_0226_InvertBinaryTree {

	public class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	//recursive:
	// the time complexity is O(n), where n is the number of nodes in the tree
	//space: O(n). Because of recursion, O(h) function calls will be placed on the stack in the worst case,
	// where h is the height of the tree. Because h∈O(n), the space complexity is O(n).
	public static TreeNode invertTree(TreeNode root) {
		if (root == null) {
			return null;
		}
		TreeNode left = root.left;
		root.left = invertTree(root.right);
		root.right = invertTree(left);
		return root;
	}

	//iterative
	//Since each node in the tree is visited / added to the queue only once, the time complexity is O(n)O(n), where nn is the number of nodes in the tree.
	//Space complexity is O(n), since in the worst case, the queue will contain all nodes in one level of the binary tree. For a full binary tree, the leaf level has O(n/2) = O(n) leaves.
	public TreeNode invertTree2(TreeNode root) {
		if (root == null) return null;
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.add(root);
		while (!queue.isEmpty()) {
			TreeNode node = queue.poll();
			TreeNode temp = node.left;
			node.left = node.right;
			node.right = temp;
			if (node.left != null) {
				queue.add(node.left);
			}
			if (node.right != null) {
				queue.add(node.right);
			}
		}
		return root;
	}
}

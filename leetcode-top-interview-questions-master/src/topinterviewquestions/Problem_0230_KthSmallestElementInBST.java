package topinterviewquestions;

import java.util.Stack;

//BST!!!
public class Problem_0230_KthSmallestElementInBST {

	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	//use morris traverse
	//best solution
	//time O(N), space O(1)
	public static int kthSmallest(TreeNode head, int k) {
		if (head == null) {
			return -1;
		}
		TreeNode cur = head;
		TreeNode mostRight = null;
		int index = 1;
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
			if (index++ == k) {
				return cur.val;
			}
			cur = cur.right;
		}
		return -1;
	}

	//recursive
	//Time complexity : O(N) to build a traversal.
	//Space complexity : O(N) to keep an inorder traversal.
	int count = 0;
	int result = Integer.MIN_VALUE;

	public int kthSmallest2(TreeNode root, int k) {
		traverse(root, k);
		return result;
	}

	public void traverse(TreeNode root, int k) {
		if(root == null) return;
		traverse(root.left, k);
		count ++;
		if(count == k) result = root.val;
		traverse(root.right, k);
	}


	//iterative
	// Time complexity: O(H+k), where H is a tree height. This complexity is defined by the stack,
	// which contains at least H+k elements, since before starting to pop out one has to go down to a leaf.
	// This results in O(logN+k) for the balanced tree and O(N+k) for completely unbalanced tree with all the nodes in the left subtree.
	//Space complexity: O(H) to keep the stack, where HH is a tree height. That makes O(N) in the worst case of the skewed tree, and
	// O(logN) in the average case of the balanced tree.
	public int kthSmallest3(TreeNode root, int k) {
		Stack<TreeNode> stack = new Stack<TreeNode>();
		TreeNode cur = root;
		int count = 0;

		while(!stack.isEmpty() || cur != null) {
			if(cur != null) {
				stack.push(cur);  // Just like recursion
				cur = cur.left;
			} else {
				TreeNode node = stack.pop();
				if(++count == k) {
					return node.val;
				}
				cur = node.right;
			}
		}

		return Integer.MIN_VALUE;
	}

}

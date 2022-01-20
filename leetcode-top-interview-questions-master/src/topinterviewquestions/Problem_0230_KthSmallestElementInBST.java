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
	// the time complex for the iterative solution should be O(n) because each node is visited
	// at least twice (push and pop) for the worst case k==N. However, if we were given that k<<N and the tree is balanced,
	// the time complexity would be O(log(n)) since we shrink the tree by half after every step.
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
				if(++count == k) return node.val;
				cur = node.right;
			}
		}

		return Integer.MIN_VALUE;
	}

}

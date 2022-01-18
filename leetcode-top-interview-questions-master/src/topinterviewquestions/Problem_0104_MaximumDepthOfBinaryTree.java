package topinterviewquestions;

import java.util.ArrayDeque;
import java.util.Deque;

public class Problem_0104_MaximumDepthOfBinaryTree {

	/*
	 * 注意最小高度比这个复杂，要额外小心判断空
	 * */
	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode (int v) {
			this.val = v;
		}
	}

	//Time complexity : O(N): we visit each node exactly once, thus the time complexity is O(N), where N is the number of nodes.
	//Space complexity : O(N): in the worst case, the tree is completely unbalanced, e.g. each node has only left child node, the recursion call would occur NN times (the height of the tree), therefore the storage to keep the call stack would be O(N).
	// But in the best case (the tree is completely balanced), the height of the tree would be log(N). Therefore, the space complexity in this case would be O(log(N)).
	public static int maxDepth(TreeNode root) {
		if (root == null) {
			return 0;
		}
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
	}


	//iterative
	//Time complexity : O(N): we visit each node exactly once, thus the time complexity is O(N), where N is the number of nodes.
	//Space complexity : O(N): in the worst case, the tree is completely unbalanced, e.g. each node has only left child node, the recursion call would occur NN times (the height of the tree), therefore the storage to keep the call stack would be O(N).
	// But in the best case (the tree is completely balanced), the height of the tree would be log(N). Therefore, the space complexity in this case would be O(log(N)).
	public int maxDepth2(TreeNode root) {
		if (root == null) {
			return 0;
		}
		Deque<TreeNode> deque = new ArrayDeque<>();
		int res = 0, next = 0;
		TreeNode cur;
		deque.offer(root);
		while (!deque.isEmpty()) {
			res++;
			next = deque.size();//you have to use a new var, you cannot use deque.size() in the for loop below as the value change over time in the for loop
			for (int i = 0; i < next; i++) {
				cur = deque.poll();
				if (cur.left != null) {
					deque.offer(cur.left);
				}
				if (cur.right != null) {
					deque.offer(cur.right);
				}
			}
		}
		return res;
	}
}

package topinterviewquestions;

import java.util.*;

public class Problem_0103_BinaryTreeZigzagLevelOrderTraversal {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode (int v) {
			this.val = v;
		}
	}


	//iterative: using deque
	//Time Complexity: O(N), where N is the number of nodes in the tree.
	//Space Complexity: O(H), where H is the height of the tree, i.e. the number of levels in the tree, which would be roughly log2(N)
	public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
		List<List<Integer>> ans = new ArrayList<>();
		if (root == null) {
			return ans;
		}
		Deque<TreeNode> deque = new ArrayDeque<>();//it is better than linkedlist!
		deque.add(root);
		int size = 0;
		boolean isLtoR = true;//means it is from left to right order
		while (!deque.isEmpty()) {
			size = deque.size();
			List<Integer> curLevel = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				TreeNode cur = isLtoR ? deque.pollFirst() : deque.pollLast();
				curLevel.add(cur.val);
				if(isLtoR) {
					if (cur.left != null) {
						deque.addLast(cur.left);
					}
					if (cur.right != null) {
						deque.addLast(cur.right);
					}
				}else {
					if (cur.right != null) {
						deque.addFirst(cur.right);
					}
					if (cur.left != null) {
						deque.addFirst(cur.left);
					}
				}
			}
			ans.add(curLevel);
			isLtoR = !isLtoR;
		}
		return ans;
	}

	//recursive
	//Time Complexity: O(N), where N is the number of nodes in the tree.
	//Same as the previous BFS approach, we visit each node once and only once.
	//Space Complexity: O(H), where H is the height of the tree, i.e. the number of levels in the tree, which would be roughly log2(N)
	public List<List<Integer>> zigzagLevelOrder2(TreeNode root) {
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		if (root == null) return res;
		helper(root, 0, res);
		return res;
	}
	public void helper(TreeNode node, int level, List<List<Integer>> res) {
		if (node == null) {
			return;
		}
		// start the current level
		if (res.size() == level) {
			res.add(new ArrayList<>());
		}
		// fulfil the current level
		if (level % 2 == 0) {
			res.get(level).add(node.val);
		} else {
			res.get(level).add(0, node.val);
		}

		// process child nodes for the next level
		helper(node.left, level + 1, res);
		helper(node.right, level + 1, res);
	}
}

package topinterviewquestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Problem_0102_BinaryTreeLevelOrderTraversal {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode (int v) {
			this.val = v;
		}
	}

	//iterative: use queue
	public List<List<Integer>> levelOrder_queue(TreeNode root) {
		List<List<Integer>> res = new ArrayList<>();
		if (root == null) {
			return res;
		}
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		int size = 0;
		while (!queue.isEmpty()) {
			size = queue.size();
			List<Integer> temp = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				TreeNode tn = queue.poll();
				temp.add(tn.val);
				if (tn.left != null) {
					queue.add(tn.left);
				}
				if (tn.right != null) {
					queue.add(tn.right);
				}
			}
			res.add(temp);
		}
		return res;
	}

	//recursive
	public List<List<Integer>> levelOrder_recursive(TreeNode root) {
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
		res.get(level).add(node.val);

		// process child nodes for the next level
		helper(node.left, level + 1, res);
		helper(node.right, level + 1, res);
	}


	//use deque
	public static List<List<Integer>> levelOrder(TreeNode root) {
		List<List<Integer>> ans = new ArrayList<>();
		if (root == null) {
			return ans;
		}
		LinkedList<TreeNode> deque = new LinkedList<>();
		deque.add(root);
		int size = 0;
		while(!deque.isEmpty()) {
			size = deque.size();
			List<Integer> curLevel = new ArrayList<>();
			for(int i = 0 ; i< size;i++) {
				TreeNode cur = deque.pollLast();
				curLevel.add(cur.val);
				if(cur.left != null) {
					deque.addFirst(cur.left);
				}
				if(cur.right != null) {
					deque.addFirst(cur.right);
				}
			}
			ans.add(curLevel);
		}
		return ans;
	}



}

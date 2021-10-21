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

	//use queue
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

}

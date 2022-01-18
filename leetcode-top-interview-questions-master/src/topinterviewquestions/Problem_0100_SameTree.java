package topinterviewquestions;

import java.util.LinkedList;
import java.util.Queue;

public class Problem_0100_SameTree {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode (int v) {
			this.val = v;
		}
	}

	//Time complexity : O(N), where N is a number of nodes in the tree, since one visits each node exactly once.
	//Space complexity : O(log(N)) in the best case of completely balanced tree and O(N) in the worst case of completely unbalanced tree, to keep a recursion stack.
	public boolean isSameTree(TreeNode p, TreeNode q) {
		if (p == null && q == null) {
			return true;
		} else if (p == null || q == null) {
			return false;
		} else {
			return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
		}
	}

	//iterative using one queue
	//time O(N), space O(N)
	public boolean isSameTree2(TreeNode p, TreeNode q) {
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(p);
		queue.add(q);
		while(!queue.isEmpty()){
			TreeNode f = queue.poll();
			TreeNode s = queue.poll();
			if(f == null && s == null){
				continue;
			}else if(f == null || s == null || f.val != s.val){
				return false;
			}
			queue.add(f.left);
			queue.add(s.left);
			queue.add(f.right);
			queue.add(s.right);
		}
		return true;
	}
}

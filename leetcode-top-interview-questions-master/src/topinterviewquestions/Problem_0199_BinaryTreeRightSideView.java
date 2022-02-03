package topinterviewquestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Problem_0199_BinaryTreeRightSideView {
	  public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
		  this.right = right;
      }
  }
	//recursive
	//https://leetcode.com/problems/binary-tree-right-side-view/discuss/56012/My-simple-accepted-solution(JAVA)
	//1.Each depth of the tree only select one node.
	//2. View depth is current size of result list.
	public class Solution {
		public List<Integer> rightSideView(TreeNode root) {
			List<Integer> result = new ArrayList<Integer>();
			rightView(root, result, 0);
			return result;
		}

		public void rightView(TreeNode curr, List<Integer> result, int currDepth){
			if(curr == null){
				return;
			}
			if(currDepth == result.size()){
				result.add(curr.val);
			}

			rightView(curr.right, result, currDepth + 1);
			rightView(curr.left, result, currDepth + 1);

		}
	}

	//BFS iterative using queue
	//https://leetcode.com/problems/binary-tree-right-side-view/discuss/56012/My-simple-accepted-solution(JAVA)/239974
	class Solution1 {
		public List<Integer> rightSideView(TreeNode root) {
			if (root == null)
				return new ArrayList();
			Queue<TreeNode> queue = new LinkedList();
			queue.offer(root);
			List<Integer> res = new ArrayList();

			while(!queue.isEmpty()){
				int size = queue.size();

				while (size -- > 0){
					TreeNode cur = queue.poll();
					if (size == 0)
						res.add(cur.val);

					if (cur.left != null)
						queue.offer(cur.left);
					if (cur.right != null)
						queue.offer(cur.right);
				}
			}

			return res;
		}
	}
}

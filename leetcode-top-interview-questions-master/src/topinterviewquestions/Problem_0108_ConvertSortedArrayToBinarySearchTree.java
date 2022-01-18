package topinterviewquestions;

import java.util.Stack;

public class Problem_0108_ConvertSortedArrayToBinarySearchTree {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int val) {
			this.val = val;
		}
	}

	//recursive
	//Time complexity: O(N) since we visit each node exactly once.
	//Space complexity: O(logN).
	//The recursion stack requires O(logN) space because the tree is height-balanced.
	// Note that the O(N) space used to store the output does not count as auxiliary space,
	// so it is not included in the space complexity.
	public TreeNode sortedArrayToBST(int[] nums) {
		return process(nums, 0, nums.length - 1);
	}

	public static TreeNode process(int[] nums, int L, int R) {
		if (L > R) {
			return null;
		}
		if (L == R) {
			return new TreeNode(nums[L]);
		}
		int M = (L + R) / 2;
		TreeNode head = new TreeNode(nums[M]);
		head.left = process(nums, L, M - 1);
		head.right = process(nums, M + 1, R);
		return head;
	}

	//iterative:
	//Use an inner class to save space
	public class MyNode{
		TreeNode node;
		int start;
		int end;

		public MyNode(int start, int end, TreeNode node){
			this.start = start;
			this.end = end;
			this.node = node;
		}
	}


	public TreeNode sortedArrayToBST2(int[] nums) {
		if(nums.length ==0 ) return null;
		int N = nums.length;
		Stack<MyNode> stack = new Stack<MyNode>();
		int mid = (N-1)/2;
		TreeNode node = new TreeNode(nums[mid]);
		MyNode myRoot = new MyNode(0, N -1, node);
		stack.push(myRoot);
		while(!stack.isEmpty()){
			MyNode cur = stack.pop();
			int oldMid = (cur.end + cur.start)/2;
			if(oldMid -1 >= cur.start){
				mid = (oldMid-1 + cur.start)/2;
				node = new TreeNode(nums[mid]);
				stack.push(new MyNode(cur.start, oldMid - 1, node));
				cur.node.left = node;
			}
			if(oldMid +1 <= cur.end){
				mid = (cur.end + oldMid+1)/2;
				node = new TreeNode(nums[mid]);
				stack.push(new MyNode(oldMid + 1, cur.end, node));
				cur.node.right = node;
			}
		}

		return myRoot.node;
	}

}

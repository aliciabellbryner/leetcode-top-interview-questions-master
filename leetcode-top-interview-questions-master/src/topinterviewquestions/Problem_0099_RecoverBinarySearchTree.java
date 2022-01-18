package topinterviewquestions;

public class Problem_0099_RecoverBinarySearchTree {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int v) {
			this.val = v;
		}
	}

	//time O(N), space O(1)
	public void recoverTree(TreeNode root) {

		TreeNode first = null;     // first node need to be swap
		TreeNode second = null;    // second node need to be swap
		TreeNode pre = new TreeNode(Integer.MIN_VALUE);  //previous node.
		TreeNode cur = root;
		TreeNode rightMost;
		while(cur != null){
			rightMost = cur.left;
			// If left is not null, we need to find the rightmost rightMost of left subtree,
			// Set its right child to current rightMost
			if(rightMost!=null){
				//find the rightmost
				while(rightMost.right!=null && rightMost.right != cur){
					rightMost = rightMost.right;
				}

				//There are two cases,
				//null: first time we access current, set rightMost.right to current and move to left child of current and continue;
				//current: we accessed current before, thus we've finished traversing left subtree, set rightMost.right back to null;
				if(rightMost.right == null){
					rightMost.right = cur;
					cur = cur.left;
					continue;
				}else{
					rightMost.right = null;
				}
			}

			// compare current rightMost with previous rightMost
			if(cur.val < pre.val ){
				// first time we enconter reversed order, we set previous rightMost to first
				if( first == null ){
					first = pre;
				}
				//In case that two nodes are successive, we set second to current every time.
				second = cur;
			}
			pre = cur;
			cur = cur.right;
		}
		//swap the value;
		int temp = second.val;
		second.val = first.val;
		first.val = temp;
	}
}

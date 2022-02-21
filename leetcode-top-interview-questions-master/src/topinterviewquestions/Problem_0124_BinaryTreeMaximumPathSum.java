package topinterviewquestions;
/*
A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting them. A node can only appear in the sequence at most once. Note that the path does not need to pass through the root.

The path sum of a path is the sum of the node's values in the path.

Given the root of a binary tree, return the maximum path sum of any non-empty path.



Example 1:


Input: root = [1,2,3]
Output: 6
Explanation: The optimal path is 2 -> 1 -> 3 with a path sum of 2 + 1 + 3 = 6.
Example 2:


Input: root = [-10,9,20,null,null,15,7]
Output: 42
Explanation: The optimal path is 15 -> 20 -> 7 with a path sum of 15 + 20 + 7 = 42.


Constraints:

The number of nodes in the tree is in the range [1, 3 * 104].
-1000 <= Node.val <= 1000
 */
public class Problem_0124_BinaryTreeMaximumPathSum {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int v) {
			this.val = v;
		}
	}

	//solution1: use an inner class
	//time O(N): O(N), where N is number of nodes, since we visit each node not more than 2 times.
	//space O(H), where H is a tree height, to keep the recursion stack. In the average case of balanced tree, the tree height H=logN, in the worst case of skewed tree, H=N.
	public class Info {
		int maxFromHead;//the max value you can get if you start from head
		int max;//the max value you can get wherever you start
		public Info(int i, int j) {
			maxFromHead = i;
			max = j;
		}
	}
	public int maxPathSum(TreeNode root) {
		if (root == null) {
			return -1;
		}
		return process(root).max;
	}
	public Info process(TreeNode root) {
		if (root == null) {
			return null;
		}
		Info l = process(root.left);
		Info r = process(root.right);
		int m1 = l == null ? Integer.MIN_VALUE : l.max;
		int m2 = l == null ? Integer.MIN_VALUE : l.maxFromHead;
		int m3 = l == null ? Integer.MIN_VALUE : l.maxFromHead + root.val;
		int m4 = r == null ? Integer.MIN_VALUE : r.max;
		int m5 = r == null ? Integer.MIN_VALUE : r.maxFromHead;
		int m6 = r == null ? Integer.MIN_VALUE : r.maxFromHead + root.val;
		int m7 = root.val;
		int m8 = root.val;
		if (l != null) {
			m8 += l.maxFromHead;
		}
		if (r != null) {
			m8 += r.maxFromHead;
		}
		int maxFromHead = Math.max(m7, Math.max(m3, m6));
		int max = Math.max(Math.max(maxFromHead, m1), Math.max(Math.max(m2, m4), Math.max(m5, m8)));
		return new Info(maxFromHead, max);
	}


	//solution2: very similiar
	public static int maxPathSum2(TreeNode root) {
		if (root == null) {
			return 0;
		}
		return process2(root).maxPathSum;
	}

	public static class Info2 {
		public int maxPathSum;
		public int maxPathSumFromHead;

		public Info2(int path, int head) {
			maxPathSum = path;
			maxPathSumFromHead = head;
		}
	}

	public static Info2 process2(TreeNode x) {
		if (x == null) {
			return null;
		}
		Info2 leftInfo = process2(x.left);
		Info2 rightInfo = process2(x.right);
		int p1 = Integer.MIN_VALUE;
		if (leftInfo != null) {
			p1 = leftInfo.maxPathSum;
		}
		int p2 = Integer.MIN_VALUE;
		if (rightInfo != null) {
			p2 = rightInfo.maxPathSum;
		}
		int p3 = x.val;
		int p4 = Integer.MIN_VALUE;
		if (leftInfo != null) {
			p4 = x.val + leftInfo.maxPathSumFromHead;
		}
		int p5 = Integer.MIN_VALUE;
		if (rightInfo != null) {
			p5 = x.val + rightInfo.maxPathSumFromHead;
		}
		int p6 = Integer.MIN_VALUE;
		if (leftInfo != null && rightInfo != null) {
			p6 = x.val + leftInfo.maxPathSumFromHead + rightInfo.maxPathSumFromHead;
		}
		int maxSum = Math.max(Math.max(Math.max(p1, p2), Math.max(p3, p4)), Math.max(p5, p6));
		int maxSumFromHead = Math.max(p3, Math.max(p4, p5));
		return new Info2(maxSum, maxSumFromHead);
	}

}

package topinterviewquestions;

import java.util.*;

/*
Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”



Example 1:


Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.
Example 2:


Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
Example 3:

Input: root = [1,2], p = 1, q = 2
Output: 1


Constraints:

The number of nodes in the tree is in the range [2, 105].
-109 <= Node.val <= 109
All Node.val are unique.
p != q
p and q will exist in the tree.
 */
//treenode没有parent属性，而且p，q一定是在tree上的
public class Problem_0236_LowestCommonAncestorOfBinaryTree {

	//time O(N): we need to visit all the nodes in the worst case
	//space O(H): H is the height of the recursion tree, worst case is O(N), In the worst case the space utilized by stack would be N
	// since the height of a skewed binary tree could be N.
	public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		return process(root, p, q).lca;
	}


	//solution1: recursive
	public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
		if (root == p || root == q || root == null) return root;
		TreeNode left = lowestCommonAncestor1(root.left, p, q);
		TreeNode right = lowestCommonAncestor1(root.right, p, q);
		if(left != null && right != null) {
			return root;
		}
		return left != null ? left : right;
	}

	//solution2: use an inner class，这个方法也可以用在p，q不一定在tree上的1644题
	public static class Info {
		public TreeNode lca;
		public boolean findp;
		public boolean findq;

		public Info(TreeNode node, boolean f1, boolean f2) {
			lca = node;
			findp = f1;
			findq = f2;
		}
	}

	public static Info process(TreeNode node, TreeNode p, TreeNode q) {
		if (node == null) {
			return new Info(null, false, false);
		}
		Info leftInfo = process(node.left, p, q);
		Info rightInfo = process(node.right, p, q);
		boolean findp = node == p || leftInfo.findp || rightInfo.findp;
		boolean findq = node == q || leftInfo.findq || rightInfo.findq;
		TreeNode lca = null;
		if (leftInfo.lca != null) {
			lca = leftInfo.lca;
		}
		if (rightInfo.lca != null) {
			lca = rightInfo.lca;
		}
		if (lca == null) {
			if (findp && findq) {
				lca = node;
			}
		}
		return new Info(lca, findp, findq);
	}


	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	//solution: iterative
	//To find the lowest common ancestor, we need to find where is p and q and a way to track their ancestors. A parent pointer for each node found is good for the job. After we found both p and q, we create a set of p's ancestors. Then we travel through q's ancestors, the first one appears in p's is our answer.
	public class Solution {
		public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
			Map<TreeNode, TreeNode> parent = new HashMap<>();
			Deque<TreeNode> stack = new ArrayDeque<>();
			parent.put(root, null);
			stack.push(root);

			while (!parent.containsKey(p) || !parent.containsKey(q)) {
				TreeNode node = stack.pop();
				if (node.left != null) {
					parent.put(node.left, node);
					stack.push(node.left);
				}
				if (node.right != null) {
					parent.put(node.right, node);
					stack.push(node.right);
				}
			}
			Set<TreeNode> ancestors = new HashSet<>();
			while (p != null) {
				ancestors.add(p);
				p = parent.get(p);
			}
			while (!ancestors.contains(q))
				q = parent.get(q);
			return q;
		}
	}

}

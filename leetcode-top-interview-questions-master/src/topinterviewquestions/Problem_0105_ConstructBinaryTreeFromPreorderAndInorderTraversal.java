package topinterviewquestions;

import java.util.HashMap;
import java.util.Stack;
/*
Given two integer arrays preorder and inorder where preorder is the preorder traversal of a binary tree and inorder is the inorder traversal of the same tree, construct and return the binary tree.

Example 1:


Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
Output: [3,9,20,null,null,15,7]
Example 2:

Input: preorder = [-1], inorder = [-1]
Output: [-1]


Constraints:

1 <= preorder.length <= 3000
inorder.length == preorder.length
-3000 <= preorder[i], inorder[i] <= 3000
preorder and inorder consist of unique values.
Each value of inorder also appears in preorder.
preorder is guaranteed to be the preorder traversal of the tree.
inorder is guaranteed to be the inorder traversal of the tree.
 */
public class Problem_0105_ConstructBinaryTreeFromPreorderAndInorderTraversal {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int val) {
			this.val = val;
		}
	}

	//recursive
	//Time complexity : O(N) as each node will be visited once
	//Building the hashmap takes O(N) time, as there are N nodes to add, and adding items to a hashmap has a cost of O(1), so we get N*O(1)=O(N).
	//Building the tree also takes O(N) time. The recursive helper method has a cost of O(1) for each call (it has no loops), and it is called once for each of the N nodes, giving a total of O(N).Taking both into consideration, the time complexity is O(N)O(N).
	//Space complexity : O(N) Building the hashmap and storing the entire tree each requires O(N) memory. The size of the implicit system stack used by recursion calls depends on the height of the tree, which is O(N) in the worst case and O(logN) on average. Taking both into consideration, the space complexity is O(N).
	public static TreeNode buildTree(int[] preorder, int[] inorder) {
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < inorder.length; i++) {
			map.put(inorder[i], i);
		}
		return f(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, map);
	}

	public static TreeNode f(int[] pre, int L1, int R1, int[] in, int L2, int R2, HashMap<Integer, Integer> map) {
		if (L1 > R1) {
			return null;
		}
		TreeNode head = new TreeNode(pre[L1]);
		if (L1 == R1) {
			return head;
		}
		int findIndex = map.get(pre[L1]);
		head.left = f(pre, L1 + 1, L1 + findIndex - L2, in, L2, findIndex - 1, map);
		head.right = f(pre, L1 + findIndex - L2 + 1, R1, in, findIndex + 1, R2, map);
		return head;
	}

	// Iterative.
	// Simulate the preorder traversal, backtrack with inorder.
	// In preorder traversal, we start from root, go left until reaching left most node
	// (and we pushing nodes to stack along the path), and then backtrack to the first node with not null right child,
	// go to its right child and repeat. Note the backtracking is entirely following the inorder,
	// which gives us a way to locate the first node with not null right child, i.e. keep poping stack top
	// if it's equal to next inorder value, the next preorder val is just the right child of last poped node.
	public TreeNode buildTree2(int[] preorder, int[] inorder) {
		if (preorder.length == 0) return null;
		Stack<TreeNode> stack = new Stack<>();
		TreeNode root = new TreeNode(preorder[0]), cur = root;
		for (int i = 1, j = 0; i < preorder.length; i++) {
			if (cur.val != inorder[j]) {//从root出发，找到最左的node： In preorder traversal, we start from root, go left until reaching left most node
				cur.left = new TreeNode(preorder[i]);
				stack.push(cur);//每次找的过程中把经过的node依次放进stack里：we pushing nodes to stack along the path
				cur = cur.left;
			} else {
				j++;
				while (!stack.empty() && stack.peek().val == inorder[j]) {//当stack中的top元素等于inorder的下一个元素时，就把这个top元素pop出，赋值给cur，backtrack to the first node with not null right child, i.e. keep poping stack top if it's equal to next inorder value
					cur = stack.pop();
					j++;
				}
				cur.right = new TreeNode(preorder[i]);//那上面的这个top元素的右子节点就是preorder的下一个元素the next preorder val is just the right child of last poped node
				cur = cur.right;
			}
		}
		return root;
	}

}

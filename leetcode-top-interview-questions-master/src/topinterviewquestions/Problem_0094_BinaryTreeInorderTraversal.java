package topinterviewquestions;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Problem_0094_BinaryTreeInorderTraversal {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int v) {
			this.val = v;
		}
	}

	//dont use stack: zuo's solution //use Morris Traversal
	public static List<Integer> inorderTraversal(TreeNode root) {
		List<Integer> ans = new ArrayList<>();
		if (root == null) {
			return ans;
		}
		TreeNode cur = root;
		TreeNode mostRight = null;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {// 第一次到达
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {// mostRight.right == cur      // 第二次到达
					mostRight.right = null;
				}
			}
			ans.add(cur.val);
			cur = cur.right;
		}
		return ans;
	}

	//use Morris Traversal
	public static List<Integer> preTraversal(TreeNode root) {
		List<Integer> ans = new ArrayList<>();
		if (root == null) {
			return ans;
		}
		TreeNode cur = root;
		TreeNode mostRight = null;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {// 第一次到达
					ans.add(cur.val);
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {// 第二次到达
					mostRight.right = null;
				}
			} else {// 只有一次到达
				ans.add(cur.val);
			}
			cur = cur.right;
		}
		return ans;
	}

	//posorder
	public static List<Integer> posTraversal(TreeNode head) {
		List<Integer> ans = new ArrayList<>();
		if (head == null) {
			return ans;
		}
		TreeNode cur = head;
		TreeNode mostRight = null;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
					printEdge(cur.left, ans);
				}
			}
			cur = cur.right;
		}
		printEdge(head, ans);
		return ans;
	}

	public static void printEdge(TreeNode head, List<Integer> ans) {
		TreeNode tail = reverseEdge(head);
		TreeNode cur = tail;
		while (cur != null) {
			ans.add(cur.val);
			cur = cur.right;
		}
		reverseEdge(tail);
	}

	public static TreeNode reverseEdge(TreeNode from) {
		TreeNode pre = null;
		TreeNode next = null;
		while (from != null) {
			next = from.right;
			from.right = pre;
			pre = from;
			from = next;
		}
		return pre;
	}

	//use stack
	//Time complexity: O(n)
	//Space complexity: O(n)
	public List<Integer> inorderTraversal_2(TreeNode root) {
		List<Integer> list = new ArrayList<Integer>();

		Stack<TreeNode> stack = new Stack<TreeNode>();
		TreeNode cur = root;

		while(cur!=null || !stack.empty()){
			while(cur!=null){
				stack.add(cur);
				cur = cur.left;
			}
			cur = stack.pop();
			list.add(cur.val);
			cur = cur.right;
		}

		return list;
	}

	//recursive way:
	//Time complexity: O(n)
	//The time complexity is O(n) because the recursive function is T(n) = 2*T(n/2)+1T(n)=2⋅T(n/2)+1.
	//Space complexity: O(n)
	//The worst case space required is O(n), and in the average case it's O(\log n)O(logn) where n is number of nodes.
	public List<Integer> inorderTraversal3(TreeNode root) {
		List<Integer> res = new ArrayList<>();
		helper3(root, res);
		return res;
	}

	public void helper3(TreeNode root, List<Integer> res) {
		if (root != null) {
			helper3(root.left, res);
			res.add(root.val);
			helper3(root.right, res);
		}
	}

	public static void main(String[] args) {
		TreeNode head = new TreeNode(4);
		head.left = new TreeNode(2);
		head.right = new TreeNode(6);
		head.left.left = new TreeNode(1);
		head.left.right = new TreeNode(3);
		head.right.left = new TreeNode(5);
		head.right.right = new TreeNode(7);
		System.out.println(posTraversal(head));

	}

}

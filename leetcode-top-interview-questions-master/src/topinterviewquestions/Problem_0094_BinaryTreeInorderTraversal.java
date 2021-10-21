package topinterviewquestions;

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

	//dont use stack: zuo's solution
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

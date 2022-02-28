package topinterviewquestions;

import java.util.*;

public class Problem_0297_SerializeAndDeserializeBinaryTree {

	//https://leetcode.com/problems/serialize-and-deserialize-binary-tree/discuss/74253/Easy-to-understand-Java-Solution/77363
	class Solution1 {
		// 提交代码时不要提交TreeNode类
		public String serialize(TreeNode root) {
			return serial(new StringBuilder(), root).toString();
		}

		// Generate preorder string
		private StringBuilder serial(StringBuilder str, TreeNode root) {
			if (root == null) return str.append("#");
			str.append(root.val).append(",");
			serial(str, root.left).append(",");
			serial(str, root.right);
			return str;
		}

		public TreeNode deserialize(String data) {
			return deserial(new LinkedList<>(Arrays.asList(data.split(","))));
		}

		// Use queue to simplify position move
		private TreeNode deserial(Queue<String> q) {
			String val = q.poll();
			if ("#".equals(val)) return null;
			TreeNode root = new TreeNode(Integer.valueOf(val));
			root.left = deserial(q);
			root.right = deserial(q);
			return root;
		}
	}

	//solution by Zuo
	//pre-order serialize
	//time O(N), space O(N)
	public String serialize(TreeNode root) {
		LinkedList<String> res = new LinkedList<>();//must use this, cannot write as List<String> res = new LinkedList<>()
		// or you won't be able to use the peekLast() and pollLast() method, and arraylist doesn't have peek or poll method
		if (root == null) {//corner case
			res.add(null);
		} else {
			res.add(String.valueOf(root.val));
			Queue<TreeNode> queue = new LinkedList<TreeNode>();
			queue.add(root);
			while (!queue.isEmpty()) {
				root = queue.poll();
				if (root.left != null) {//如果root的左边不为null则把root的左边放到res中去，同时这个左node放到queue中去
					res.add(String.valueOf(root.left.val));
					queue.add(root.left);
				} else {
					res.add(null);
				}
				if (root.right != null) {//如果root的右边不为null则把root的左边放到res中去，同时这个右node放到queue中去
					res.add(String.valueOf(root.right.val));
					queue.add(root.right);
				} else {
					res.add(null);
				}
			}
		}
		while (!res.isEmpty() && res.peekLast() == null) {//
			res.pollLast();//pollLast() remove the last element entered, poll() remove the first element entered
		}//去除leaf是null且右边都是null的节点
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		String str = res.pollFirst();
		sb.append(str == null ? "null" : str);
		while (!res.isEmpty()) {
			str = res.pollFirst();
			sb.append("," + (str == null ? "null" : str));
		}
		sb.append("]");
		return sb.toString();
	}

	public TreeNode deserialize(String data) {
		String[] strs = data.substring(1, data.length() - 1).split(",");//substring(1, data.length() - 1)这个是去除左右【】
		int index = 0;
		TreeNode root = generateNode(strs[index++]);
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		if (root != null) {
			queue.add(root);
		}
		while (!queue.isEmpty()) {
			TreeNode node = queue.poll();
			node.left = generateNode(index == strs.length ? "null" : strs[index++]);
			node.right = generateNode(index == strs.length ? "null" : strs[index++]);
			if (node.left != null) {
				queue.add(node.left);
			}
			if (node.right != null) {
				queue.add(node.right);
			}
		}
		return root;
	}

	private TreeNode generateNode(String val) {
		if (val.equals("null")) {
			return null;
		}
		return new TreeNode(Integer.valueOf(val));
	}

	public static void main(String[] args) {
		LinkedList<TreeNode> list = new LinkedList<>();
		//List<TreeNode> list = new LinkedList<>(); //这么写list不能用peek/peeklast或者poll的method，必须写成上面的方式
		//ArrayList<TreeNode> list2 = new ArrayList<>(); //arraylist同样也没有peek或者poll的method
		list.add(new TreeNode(1));
		list.add(new TreeNode(2));
		list.poll();
		System.out.println(list.peek().val);//peeklast看的是最后加的元素，peek看的是加的第一个元素
	}

	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int value) {
			val = value;
		}
	}


}

package topinterviewquestions;

import java.util.LinkedList;
import java.util.Queue;

public class Problem_0116_PopulatingNextRightPointersInEachNode {


	public static class Node {
		public int val;
		public Node left;
		public Node right;
		public Node next;
		public Node (int v) {
			this.val = v;
		}
	}


	//my solution: this is not as good as Zuo's solution as if you use the system's LinkedList class, you will waste space as that class has many methods, variables
	//Time Complexity: O(N) since we process each node exactly once.
	//Space Complexity: O(N). This is a perfect binary tree which means the last level contains N/2 nodes. The space complexity for breadth first traversal is the space occupied by the queue which is dependent upon the maximum number of nodes in particular level.
	// So, in this case, the space complexity would be O(N)O(N).
	public static Node connect_j(Node root) {
		if (root == null) {
			return root;
		}
		Queue<Node> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			// 第一个弹出的节点
			Node pre = null;
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				Node cur = queue.poll();
				if (cur.left != null) {
					queue.offer(cur.left);
				}
				if (cur.right != null) {
					queue.offer(cur.right);
				}
				if (pre != null) {
					pre.next = cur;
				}
				pre = cur;
			}
		}
		return root;
	}


	//followup: how to improve
	//Zuo's solution, write a new simple class to save space

	public static class MyQueue {
		public Node head;
		public Node tail;
		public int size;

		public MyQueue() {
			head = null;
			tail = null;
			size = 0;
		}

		public boolean isEmpty() {
			return size == 0;
		}

		public void offer(Node cur) {
			size++;
			if (head == null) {
				head = cur;
				tail = cur;
			} else {
				tail.next = cur;
				tail = cur;
			}
		}

		public Node poll() {
			size--;
			Node ans = head;
			head = head.next;
			ans.next = null;
			return ans;
		}

	}

	public static Node connect(Node root) {
		if (root == null) {
			return root;
		}
		MyQueue queue = new MyQueue();//can use Queue<Node> queue = new LinkedList<>(), but will cost more space, but Zuo's own class save space
		queue.offer(root);
		while (!queue.isEmpty()) {
			// 第一个弹出的节点
			Node pre = null;
			int size = queue.size;
			for (int i = 0; i < size; i++) {
				Node cur = queue.poll();
				if (cur.left != null) {
					queue.offer(cur.left);
				}
				if (cur.right != null) {
					queue.offer(cur.right);
				}
				if (pre != null) {
					pre.next = cur;
				}
				pre = cur;
			}
		}
		return root;
	}

}

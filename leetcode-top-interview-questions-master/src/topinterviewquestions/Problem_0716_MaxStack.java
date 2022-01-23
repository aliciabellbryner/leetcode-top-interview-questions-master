package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

public class Problem_0716_MaxStack {

	//https://leetcode.com/problems/max-stack/solution/

	//Approach #2: Double Linked List + TreeMap
	//Time Complexity: O(logN) for all operations except peek which is O(1),
	// where N is the number of operations performed. Most operations involving TreeMap are O(logN).
	//Space Complexity: O(N), the size of the data structures used.
	class MaxStack2 {
		TreeMap<Integer, List<Node>> treemap;//treemap相对于一半的hashmap的优点在于它可以把key进行排序，
		// 可以通过lastKey，firstkey直接拿到头尾元素，
		// TreeMap can find the largest value, insert values, and delete values, all in O(logN) time.
		//这个key是每次push进来的元素，value是这个key对应的每次push之后把元素新加到list中形成的新的list形成的arraylist
		DoubleLinkedList dList;

		public MaxStack2() {
			treemap = new TreeMap();
			dList = new DoubleLinkedList();
		}

		public void push(int x) {//push的时候
			Node node = dList.add(x);//每一次push新元素的时候list都会更新
			if(!treemap.containsKey(x)) {
				treemap.put(x, new ArrayList<Node>());
			}
			treemap.get(x).add(node);
		}

		public int pop() {
			int topNum = dList.pop();
			List<Node> curList = treemap.get(topNum);
			curList.remove(curList.size() - 1);
			if (curList.isEmpty()) {//说明这个topNum对应的list为空了
				treemap.remove(topNum);
			}
			return topNum;
		}

		public int top() {
			return dList.peek();
		}

		public int peekMax() {
			return treemap.lastKey();
		}

		public int popMax() {//注意，这个popMax调用的时候只把一个max给删除并返回，
			// 如果之前有两个5都是max，popMax一次之后只把最后加入的元素给删除，popMax一次之后max仍然是5
			int curMax = peekMax();
			List<Node> curList = treemap.get(curMax);
			Node node = curList.remove(curList.size() - 1);//只删除最后一个加入的max
			dList.unlink(node);
			if (curList.isEmpty()) {
				treemap.remove(curMax);
			}
			return curMax;
		}
	}

	class DoubleLinkedList {
		Node dummyHead, dummyTail;//注意他们都不是实际的头尾节点

		public DoubleLinkedList() {
			dummyHead = new Node(0);
			dummyTail = new Node(0);
			dummyHead.next = dummyTail;
			dummyTail.prev = dummyHead;
		}

		public Node add(int val) {
			Node newTail = new Node(val);
			newTail.next = dummyTail;
			newTail.prev = dummyTail.prev;
			dummyTail.prev.next = newTail;
			dummyTail.prev = dummyTail.prev.next;
			return newTail;
		}

		public int pop() {//把实际的tail节点删除并返回
			return unlink(dummyTail.prev).val;
		}

		public int peek() {
			return dummyTail.prev.val;
		}

		public Node unlink(Node node) {//可以删除任意一个node，并不是只能删最后一个node
			node.prev.next = node.next;
			node.next.prev = node.prev;
			return node;
		}
	}

	class Node {
		int val;
		Node prev, next;
		public Node(int v) {val = v;}
	}


	//Approach #1: Two Stacks
	//Time Complexity: O(N) for the popMax operation,
	// and O(1) for the other operations, where NN is the number of operations performed.
	//Space Complexity: O(N), the maximum size of the stack.
	class MaxStack1 {
		Stack<Integer> stack;
		Stack<Integer> maxStack;

		public MaxStack1() {
			stack = new Stack();
			maxStack = new Stack();
		}

		public void push(int x) {
			int max = maxStack.isEmpty() ? x : maxStack.peek();
			maxStack.push(max > x ? max : x);
			stack.push(x);
		}

		public int pop() {
			maxStack.pop();
			return stack.pop();
		}

		public int top() {
			return stack.peek();
		}

		public int peekMax() {
			return maxStack.peek();
		}

		public int popMax() {
			int max = peekMax();
			Stack<Integer> buffer = new Stack();
			while (top() != max) buffer.push(pop());
			pop();
			while (!buffer.isEmpty()) push(buffer.pop());
			return max;
		}
	}



}

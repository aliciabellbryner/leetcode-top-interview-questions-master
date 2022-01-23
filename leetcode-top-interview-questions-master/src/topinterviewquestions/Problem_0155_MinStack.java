package topinterviewquestions;

import java.util.Stack;

/*
 * 在leetcode上提交时，把文字替换成下面的代码
 * 然后把类名、构造方法名从Problem_0155_MinStack改为MinStack即可
 */
public class Problem_0155_MinStack {
//https://leetcode.com/problems/min-stack/solution/

	//Approach 3: Improved Two Stacks
	//相对approach2，2需要每次加入x，而且minStack每次需要加元素，不管这个x是不是等于cur min
	//解决方案是put pairs onto the min-tracker Stack. The first value of the pair would be the same as before,
	// and the second value would be how many times that minimum was repeated.
	// For example, this is how the min-tracker Stack for the example just above would appear.
	//Time Complexity : O(1) for all operations.
	//Space Complexity : O(n).

	class MinStack {

		private Stack<Integer> dataStack;
		private Stack<int[]> minStack;//放两个元素，第一个是data第二个是出现次数


		public MinStack() {
			dataStack = new Stack<>();
			minStack = new Stack<>();
		}


		public void push(int x) {

			// We always put the number onto the main stack.
			dataStack.push(x);

			// If the min stack is empty, or this number is smaller than
			// the top of the min stack, put it on with a count of 1.
			if (minStack.isEmpty() || x < minStack.peek()[0]) {
				minStack.push(new int[]{x, 1});
			}

			// Else if this number is equal to what's currently at the top
			// of the min stack, then increment the count at the top by 1.
			else if (x == minStack.peek()[0]) {
				minStack.peek()[1]++;
			}
		}


		public void pop() {

			// If the top of min stack is the same as the top of stack
			// then we need to decrement the count at the top by 1.
			if (dataStack.peek().equals(minStack.peek()[0])) {
				minStack.peek()[1]--;
			}

			// If the count at the top of min stack is now 0, then remove
			// that value as we're done with it.
			if (minStack.peek()[1] == 0) {
				minStack.pop();
			}

			// And like before, pop the top of the main stack.
			dataStack.pop();
		}


		public int top() {
			return dataStack.peek();
		}


		public int getMin() {
			return minStack.peek()[0];
		}
	}

	//Approach 1: One Stack第一个元素是data，第二个是cur min
	//stack里
	//Time Complexity : O(1) for all operations.
	//Same as above. All our modifications are still O(1).
	//Space Complexity : O(n).
	class MinStack1 {

		private Stack<int[]> stack;

		public MinStack1() {
			stack = new Stack<>();
		}

		public void push(int x) {
			/* If the stack is empty, then the min value
			 * must just be the first value we add. */
			if (stack.isEmpty()) {
				stack.push(new int[]{x, x});
				return;
			}
			int currentMin = stack.peek()[1];
			stack.push(new int[]{x, Math.min(x, currentMin)});
		}

		public void pop() {
			stack.pop();
		}

		public int top() {
			return stack.peek()[0];
		}

		public int getMin() {
			return stack.peek()[1];
		}
	}

	//Approach 2: Two Stacks
	//Time Complexity : O(1) for all operations.
	//Same as above. All our modifications are still O(1).
	//Space Complexity : O(n).
	class MinStack2 {
		private Stack<Integer> data;
		private Stack<Integer> min;

		public MinStack2() {
			data = new Stack<>();
			min = new Stack<>();
		}

		//用两个stack，data所有的数据push的时候都放进去，min放min的头元素和x相对小的那个值
		public void push(int x) {
			data.push(x);
			if (min.isEmpty()) {
				min.push(x);
			} else {
				min.push(Math.min(min.peek(), x));
			}
		}

		public void pop() {
			data.pop();
			min.pop();
		}

		public int top() {
			return data.peek();
		}

		public int getMin() {
			return min.peek();
		}
	}
}

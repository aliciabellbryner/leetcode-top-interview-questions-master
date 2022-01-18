package topinterviewquestions;

import java.util.Arrays;
import java.util.Stack;

public class Problem_0084_LargestRectangleInHistogram {

	//time O(N), space O(N)
	public static int largestRectangleArea(int[] height) {
		if (height == null || height.length == 0) {
			return 0;
		}
		int res = 0;
		int N = height.length;
		Stack<Integer> stack = new Stack<>();// 只放下标, the elements(the index of the elements) in the stack is increasing, everytime found a new height[i] smaller than the top of stack, pop that top element
		for (int i = 0; i <= height.length; i++) {
			while (!stack.isEmpty() && (i == N || height[i] <= height[stack.peek()])) {
				int index = stack.pop();
				int leftLess = stack.isEmpty() ? -1 : stack.peek();
				int rightLess = i;
				res = Math.max(res, (rightLess - leftLess - 1) * height[index]);
			}
			stack.push(i);
		}
		return res;
	}

	//a little bit improve than the first one, using an int[] saves time than stack
	public int largestRectangleArea2(int[] height) {
		if (height == null || height.length == 0) {
			return 0;
		}
		int N = height.length;
		int[] stack = new int[N];
		int si = -1;
		int res = Integer.MIN_VALUE;
		for (int i = 0; i <= N; i++) {
			while (si != -1 && (i == N || height[i] < height[stack[si]])) {
				int index = stack[si--];
				int  leftLess = si == -1 ? -1 : stack[si];
				int rightLess = i;
				res = Math.max(res, (rightLess - leftLess - 1) * height[index]);
			}
			stack[++si] = i;
		}
		return res;
	}
}

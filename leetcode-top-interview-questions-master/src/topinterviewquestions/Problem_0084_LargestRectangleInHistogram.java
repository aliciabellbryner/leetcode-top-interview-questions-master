package topinterviewquestions;
/*
Given an array of integers heights representing the histogram's bar height where the width of each bar is 1, return the area of the largest rectangle in the histogram.

Example 1:


Input: heights = [2,1,5,6,2,3]
Output: 10
Explanation: The above is a histogram where width of each bar is 1.
The largest rectangle is shown in the red area, which has an area = 10 units.
Example 2:

Input: heights = [2,4]
Output: 4

Constraints:

1 <= heights.length <= 105
0 <= heights[i] <= 104
 */
import java.util.Arrays;
import java.util.Stack;

public class Problem_0084_LargestRectangleInHistogram {

	//time O(N), space O(N)
	//用单调栈来做
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

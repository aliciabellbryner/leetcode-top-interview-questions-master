package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/*
Given an array of integers temperatures represents the daily temperatures, return an array answer such that answer[i] is the number of days you have to wait after the ith day to get a warmer temperature. If there is no future day for which this is possible, keep answer[i] == 0 instead.

Example 1:

Input: temperatures = [73,74,75,71,69,72,76,73]
Output: [1,1,4,2,1,1,0,0]
Example 2:

Input: temperatures = [30,40,50,60]
Output: [1,1,1,0]
Example 3:

Input: temperatures = [30,60,90]
Output: [1,1,0]


Constraints:

1 <= temperatures.length <= 105
30 <= temperatures[i] <= 100
 */
public class Problem_0739_DailyTemperatures {

	//单调栈，time O(N) space O(N)
	public static int[] dailyTemperatures(int[] arr) {
		if (arr == null || arr.length == 0) {
			return new int[0];
		}
		int N = arr.length;
		int[] res = new int[N];
		Stack<List<Integer>> stack = new Stack<>();//store list of index which directs to same value，里面idx对应的arr[idx]递减,
		for (int i = 0; i < N; i++) {
			while (!stack.isEmpty() && arr[stack.peek().get(0)] < arr[i]) {
				List<Integer> popIs = stack.pop();
				for (Integer popi : popIs) {
					res[popi] = i - popi;
				}
			}
			if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {//如果对应的温度相同就把他们的index都放进stack里对应的list里
				stack.peek().add(Integer.valueOf(i));
			} else {//说明和上面一个不相同温度
				ArrayList<Integer> list = new ArrayList<>();
				list.add(i);
				stack.push(list);
			}
		}
		return res;
	}

}

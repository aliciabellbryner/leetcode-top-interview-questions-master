package topinterviewquestions;

import java.util.Stack;

public class Problem_0085_MaximalRectangle {

    public static int maximalRectangle(char[][] map) {
		if (map == null || map.length == 0 || map[0].length == 0) {
			return 0;
		}
		int maxArea = 0;
		int[] height = new int[map[0].length];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				height[j] = map[i][j] == '0' ? 0 : height[j] + 1;//this step is to count每一行竖向连续的1的最大个数组成的直方图，然后再用84的解
			}
			maxArea = Math.max(maxRecFromBottom(height), maxArea);
		}
		return maxArea;
	}

	// height是正方图数组
	public static int maxRecFromBottom(int[] height) {
		if (height == null || height.length == 0) {
			return 0;
		}
		int maxArea = 0;
		// 左右两侧离最近，小的 ,,,,  底  -> 顶        小   ->  大
		Stack<Integer> stack = new Stack<>();
		int N = height.length;
		for (int i = 0; i <= N; i++) {
			while (!stack.isEmpty() && (i == N || height[i] <= height[stack.peek()])) {
				int index = stack.pop(); // 结算哪个位置的答案
				int leftLess = stack.isEmpty() ? -1 : stack.peek(); // 左边不能扩到的位置
				int rightLess = i;
				maxArea = Math.max(maxArea, (rightLess - leftLess - 1) * height[index]);
			}
			stack.push(i);
		}
		return maxArea;
	}
	
}

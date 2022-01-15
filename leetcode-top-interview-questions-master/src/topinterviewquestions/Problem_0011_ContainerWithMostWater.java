package topinterviewquestions;

public class Problem_0011_ContainerWithMostWater {

	public static int maxArea(int[] height) {
		int max = 0;
		int l = 0;
		int r = height.length - 1;
		while (l < r) {
			max = Math.max(max, Math.min(height[l], height[r]) * (r - l));
			if (height[l] > height[r]) {
				r--;
			} else {
				l++;
			}
		}
		return max;
	}

}

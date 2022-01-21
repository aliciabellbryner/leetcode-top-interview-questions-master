package topinterviewquestions;

public class Problem_0581_ShortestUnsortedContinuousSubarray {

	public static int findUnsortedSubarray(int[] nums) {
		if (nums == null || nums.length < 2) {
			return 0;
		}
		int N = nums.length;
		int right = -1;//他是符合nums[right]比左边所有值最大值小的最右的数
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < N; i++) {
			if (max > nums[i]) {
				right = i;
			}
			max = Math.max(max, nums[i]);
		}
		int min = Integer.MAX_VALUE;
		int left = N;//他是符合nums[left]比右边所有值最小值大的最左的数
		for (int i = N - 1; i >= 0; i--) {
			if (min < nums[i]) {
				left = i;
			}
			min = Math.min(min, nums[i]);
		}
		return Math.max(0, right - left + 1);
	}

}

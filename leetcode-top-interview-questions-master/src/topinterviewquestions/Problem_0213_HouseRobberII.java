package topinterviewquestions;

public class Problem_0213_HouseRobberII {

	//相当于198题算两个情况，第一个是抢第一家得到的最大值，第二个是不抢第一家
	public static int rob(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		if (nums.length == 1) {
			return nums[0];
		}
		if (nums.length == 2) {
			return Math.max(nums[0], nums[1]);
		}
		int pre2 = nums[0];
		int pre1 = Math.max(nums[0], nums[1]);
		for (int i = 2; i < nums.length - 1; i++) {
			int tmp = Math.max(pre1, nums[i] + pre2);
			pre2 = pre1;
			pre1 = tmp;
		}
		int ans1 = pre1;
		pre2 = nums[1];
		pre1 = Math.max(nums[1], nums[2]);
		for (int i = 3; i < nums.length; i++) {
			int tmp = Math.max(pre1, nums[i] + pre2);
			pre2 = pre1;
			pre1 = tmp;
		}
		int ans2 = pre1;
		return Math.max(ans1, ans2);
	}


	public int rob_j(int[] nums) {
		if (nums == null) {
			return -1;
		}
		int N = nums.length;
		if (N == 0) {
			return 0;
		}
		if (N == 1) {
			return nums[0];
		}
		if (N == 2) {
			return Math.max(nums[0], nums[1]);
		}
		int premax1 = nums[0];
		int premax2 = Math.max(nums[1], nums[0]);
		for (int i = 2; i < nums.length - 1; i++) {
			int temp = Math.max(nums[i] + premax1, premax2);
			premax1 = premax2;
			premax2 = temp;
		}
		int ans1 = premax2;
		premax1 = nums[1];
		premax2 = Math.max(nums[1], nums[2]);
		for (int i = 3; i < nums.length; i++) {
			int temp = Math.max(nums[i] + premax1, premax2);
			premax1 = premax2;
			premax2 = temp;
		}
		int ans2 = premax2;
		return Math.max(ans1, ans2);
	}

}

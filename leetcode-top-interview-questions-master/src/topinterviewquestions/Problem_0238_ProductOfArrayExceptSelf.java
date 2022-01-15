package topinterviewquestions;

public class Problem_0238_ProductOfArrayExceptSelf {

	public int[] productExceptSelf_leetcode(int[] nums) {
		int[] arr = new int[nums.length];
		arr[0] = 1;
		for (int i = 1; i < nums.length; i++) {
			arr[i] = arr[i-1] * nums[i-1];
		}
		int right = 1;
		int[] res = new int[nums.length];
		for (int j = nums.length - 1; j >= 0; j--) {
			res[j] = right * arr[j];
			right *= nums[j];
		}
		return res;
	}

	public int[] productExceptSelf(int[] nums) {
		int zeros = 0;
		int all = 1;
		for (int num : nums) {
			if (num == 0) {
				zeros++;
			} else {
				all *= num;
			}
		}
		if (zeros > 1) {
			for (int i = 0; i < nums.length; i++) {
				nums[i] = 0;
			}
		} else {
			if (zeros == 0) {
				for (int i = 0; i < nums.length; i++) {
					nums[i] = all / nums[i];
				}
			} else {
				for (int i = 0; i < nums.length; i++) {
					nums[i] = nums[i] == 0 ? all : 0;
				}
			}
		}
		return nums;
	}
	
	public int[] productExceptSelf2(int[] nums) {
		int n = nums.length;
		int[] ans = new int[n];
		ans[0] = nums[0];
		for (int i = 1; i < n; i++) {
			ans[i] = ans[i - 1] * nums[i];
		}
		int right = 1;
		for (int i = n - 1; i > 0; i--) {
			ans[i] = ans[i - 1] * right;
			right *= nums[i];
		}
		ans[0] = right;
		return ans;
	}

}

package topinterviewquestions;

public class Problem_0162_FindPeakElement {


	//Approach 3: Iterative Binary Search
	//https://leetcode.com/problems/find-peak-element/solution/
	//Time complexity : O(log_2(n))
	// Space O(1)
	public int findPeakElement(int[] nums) {
		int l = 0, r = nums.length - 1;
		while (l < r) {
			int mid = r + ((l - r) >> 1);
			if (nums[mid] > nums[mid + 1]) {
				r = mid;
			} else {
				l = mid + 1;
			}
		}
		return l;
	}



	//zuo's solution: too long and slow
	public static int findPeakElement2(int[] nums) {
		int N = nums.length;
		if (N < 2) {
			return 0;
		}
		if (nums[0] > nums[1]) {
			return 0;
		}
		if (nums[N - 1] > nums[N - 2]) {
			return N - 1;
		}
		int L = 1;
		int R = N - 2;
		int M = 0;
		while (L < R) {
			M = (L + R) / 2;
			if (nums[M - 1] < nums[M] && nums[M] > nums[M + 1]) {
				return M;
			} else if (nums[M - 1] > nums[M]) {
				R = M - 1;
			} else {
				L = M + 1;
			}
		}
		return L;
	}

}

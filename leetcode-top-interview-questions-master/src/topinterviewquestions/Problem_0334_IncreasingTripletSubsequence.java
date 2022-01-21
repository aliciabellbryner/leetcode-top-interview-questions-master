package topinterviewquestions;

public class Problem_0334_IncreasingTripletSubsequence {


	//https://leetcode.com/problems/increasing-triplet-subsequence/solution/
	//Approach 1: Linear Scan
	//time O(N) space O(1)
	public boolean increasingTriplet(int[] nums) {
		int first_num = Integer.MAX_VALUE;
		int second_num = Integer.MAX_VALUE;
		for (int n: nums) {
			if (n <= first_num) {
				first_num = n;
			} else if (n <= second_num) {
				second_num = n;
			} else {
				return true;
			}
		}
		return false;
	}

	//Zuo
	//跟300一样，你只要找到right值大于等于2，这样的话升序子序列的长度就是3，就可以满足
	public static boolean increasingTriplet1(int[] arr) {
		if (arr == null || arr.length < 3) {
			return false;
		}
		int[] ends = new int[3];
		ends[0] = arr[0];
		int right = 0;
		int l = 0;
		int r = 0;
		int m = 0;
		for (int i = 1; i < arr.length; i++) {
			l = 0;
			r = right;
			while (l <= r) {
				m = (l + r) / 2;
				if (arr[i] > ends[m]) {
					l = m + 1;
				} else {
					r = m - 1;
				}
			}
			right = Math.max(right, l);
			if (right > 1) {
				return true;
			}
			ends[l] = arr[i];
		}
		return false;
	}

}

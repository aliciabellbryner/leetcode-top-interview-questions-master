package topinterviewquestions;

public class Problem_0300_LongestIncreasingSubsequence {

	//进阶课leetcode 20讲里有详细讲解
	public static int lengthOfLIS(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[] ends = new int[arr.length];//代表所以长度为i+1的递增子序列中，最小结尾是啥，它一定是递增的
		ends[0] = arr[0];
		int right = 0;
		int l = 0;
		int r = 0;
		int m = 0;
		int max = 1;
		for (int i = 1; i < arr.length; i++) {
			l = 0;
			r = right;
			while (l <= r) {//二分法，目的就是求出第一个让ends[i]大于等于arr[i]最左的位置,如果没找到，则根据l=m+1, l最终会落在最左边还没被初始化的ends位置，就相当于用arr[i]的值扩充了一位的ends
				m = (l + r) / 2;
				if (ends[m] < arr[i]) {
					l = m + 1;
				} else {
					r = m - 1;
				}
			}
			right = Math.max(right, l);
			ends[l] = arr[i];//把end[l]大于等于arr[i]最左的位置改成arr[i]
			max = Math.max(max, l + 1);//每到一次i位置，则可以知道如果以i结尾的最长升序列的长度是l+1
		}
		return max;
	}

}

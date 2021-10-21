package topinterviewquestions;

public class Problem_0088_MergeSortedArray {

	//my solution
	public void merge_j(int[] nums1, int m, int[] nums2, int n) {
		if (nums1 == null || nums1.length == 0 || m < 0 || nums2 == null || n <= 0) {
			return;
		}
		int N = nums1.length-1;
		m--;
		n--;
		while (m >= 0 && n >= 0) {
			nums1[N--] = nums1[m] >= nums2[n] ? nums1[m--] : nums2[n--];
		}
		while (n >= 0) {
			nums1[N--] = nums2[n--];
		}
	}

	//Zuo's solution
	public static void merge(int[] nums1, int m, int[] nums2, int n) {
		int index = nums1.length;
		while (m > 0 && n > 0) {
			if (nums1[m - 1] >= nums2[n - 1]) {
				nums1[--index] = nums1[--m];
			} else {
				nums1[--index] = nums2[--n];
			}
		}
		while (m > 0) {
			nums1[--index] = nums1[--m];
		}
		while (n > 0) {
			nums1[--index] = nums2[--n];
		}
	}

}

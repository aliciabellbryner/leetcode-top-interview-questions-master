package topinterviewquestions;

import java.util.Arrays;

public class Problem_0324_WiggleSortII {


	//https://leetcode.com/problems/wiggle-sort-ii/discuss/77746/AC-java-solution(7ms)/81956
	//space is not O(1), space O(N)
	public void wiggleSort(int[] nums) {
		Arrays.sort(nums);
		int N = nums.length;
		int[] temp = new int[N];
		int mid = N%2==0 ? N/2-1 : N/2;
		int index = 0;
		for(int i=0;i<=mid;i++){
			temp[index] = nums[mid-i];
			if(index+1<N) {
				temp[index + 1] = nums[N - i - 1];
			}
			index = index+2;
		}
		for(int i=0;i<N;i++){
			nums[i] = temp[i];
		}
	}




	//Zuo's solution
	// 时间复杂度O(N)，额外空间复杂度O(1)
	public void wiggleSort2(int[] nums) {
		if (nums == null || nums.length < 2) {
			return;
		}
		int N = nums.length;
		findIndexNum(nums, 0, nums.length - 1, N / 2);
		if ((N & 1) == 0) {
			shuffle(nums, 0, nums.length - 1);
			reverse(nums, 0, nums.length - 1);
		} else {
			shuffle(nums, 1, nums.length - 1);
		}
	}

	public static int findIndexNum(int[] arr, int L, int R, int index) {
		int pivot = 0;
		int[] range = null;
		while (L < R) {
			pivot = arr[L + (int) (Math.random() * (R - L + 1))];
			range = partition(arr, L, R, pivot);
			if (index >= range[0] && index <= range[1]) {
				return arr[index];
			} else if (index < range[0]) {
				R = range[0] - 1;
			} else {
				L = range[1] + 1;
			}
		}
		return arr[L];
	}

	public static int[] partition(int[] arr, int L, int R, int pivot) {
		int less = L - 1;
		int more = R + 1;
		int cur = L;
		while (cur < more) {
			if (arr[cur] < pivot) {
				swap(arr, ++less, cur++);
			} else if (arr[cur] > pivot) {
				swap(arr, cur, --more);
			} else {
				cur++;
			}
		}
		return new int[] { less + 1, more - 1 };
	}

	public static void shuffle(int[] nums, int l, int r) {
		while (r - l + 1 > 0) {
			int lenAndOne = r - l + 2;
			int bloom = 3;
			int k = 1;
			while (bloom <= lenAndOne / 3) {
				bloom *= 3;
				k++;
			}
			int m = (bloom - 1) / 2;
			int mid = (l + r) / 2;
			rotate(nums, l + m, mid, mid + m);
			cycles(nums, l - 1, bloom, k);
			l = l + bloom - 1;
		}
	}

	public static void cycles(int[] nums, int base, int bloom, int k) {
		for (int i = 0, trigger = 1; i < k; i++, trigger *= 3) {
			int next = (2 * trigger) % bloom;
			int cur = next;
			int record = nums[next + base];
			int tmp = 0;
			nums[next + base] = nums[trigger + base];
			while (cur != trigger) {
				next = (2 * cur) % bloom;
				tmp = nums[next + base];
				nums[next + base] = record;
				cur = next;
				record = tmp;
			}
		}
	}

	public static void rotate(int[] arr, int l, int m, int r) {
		reverse(arr, l, m);
		reverse(arr, m + 1, r);
		reverse(arr, l, r);
	}

	public static void reverse(int[] arr, int l, int r) {
		while (l < r) {
			swap(arr, l++, r--);
		}
	}

	public static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

}

package topinterviewquestions;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Problem_0215_KthLargestElementInAnArray {

	//quick sort
	public int findKthLargest(int[] nums, int k) {
		return minKth(nums, nums.length + 1 - k);
	}

	//Time complexity : O(N) in the average case, O(N^2) in the worst case.
	//Space complexity : O(1).
	public static int minKth(int[] arr, int k) {
		return process(arr, 0, arr.length - 1, k - 1);
	}

	public static int process(int[] arr, int L, int R, int index) {
		if (L == R) {
			return arr[L];
		}
		int pivot = arr[L + (int) (Math.random() * (R - L + 1))];
		int[] range = partition(arr, L, R, pivot);//range[0]是arr中开始等于pivot的index起点，range[1]是终点
		if (index >= range[0] && index <= range[1]) {
			return arr[index];
		} else if (index < range[0]) {
			return process(arr, L, range[0] - 1, index);
		} else {
			return process(arr, range[1] + 1, R, index);
		}
	}

	public static int[] partition(int[] arr, int L, int R, int pivot) {//返回的是arr数组中值等于pivot的区间的index范围，
		// 这个pivot等值区间的左边都是小于pivot的值，但是他们彼此并不sorting，右边都是大于pivot的值，但彼此也不sorting
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

	public static void swap(int[] arr, int i1, int i2) {
		int tmp = arr[i1];
		arr[i1] = arr[i2];
		arr[i2] = tmp;
	}

	//也可以用priorityqueue的方法，但是时间复杂度要差一些
	// 利用小根堆，时间复杂度O(N*logK)
	//Time complexity : O(Nlogk).
	//Space complexity : O(k) to store the heap elements.
	public int findKthLargest2(int[] nums, int k) {
		// init minHeap 'the smallest element first'
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>((n1, n2) -> n1 - n2);

		// keep k largest elements in the minHeap
		for (int n: nums) {
			minHeap.add(n);
			if (minHeap.size() > k)
				minHeap.poll();
		}

		// output
		return minHeap.poll();
	}
}

package topinterviewquestions;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Problem_0215_KthLargestElementInAnArray {

	public int findKthLargest(int[] nums, int k) {
		return minKth(nums, nums.length + 1 - k);
	}

	//time O(N)
	public static int minKth(int[] arr, int k) {
		return process(arr, 0, arr.length - 1, k - 1);
	}

	public static int process(int[] arr, int L, int R, int index) {
		if (L == R) {
			return arr[L];
		}
		int pivot = arr[L + (int) (Math.random() * (R - L + 1))];
		int[] range = partition(arr, L, R, pivot);
		if (index >= range[0] && index <= range[1]) {
			return arr[index];
		} else if (index < range[0]) {
			return process(arr, L, range[0] - 1, index);
		} else {
			return process(arr, range[1] + 1, R, index);
		}
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

	public static void swap(int[] arr, int i1, int i2) {
		int tmp = arr[i1];
		arr[i1] = arr[i2];
		arr[i2] = tmp;
	}

	//也可以用priorityqueue的方法，但是时间复杂度要差一些
	// 利用大根堆，时间复杂度O(N*logK)
	public static int minKth1(int[] arr, int k) {
		PriorityQueue<Integer> minHeap = new PriorityQueue<>(new MinHeapComparator());
		for (int i = 0; i < k; i++) {
			minHeap.add(arr[i]);
		}
		for (int i = k; i < arr.length; i++) {
			if (arr[i] > minHeap.peek()) {
				minHeap.poll();
				minHeap.add(arr[i]);
			}
		}
		return minHeap.peek();
	}

	public static class MinHeapComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer o1, Integer o2) {
			return o1 - o2;
		}

	}
}

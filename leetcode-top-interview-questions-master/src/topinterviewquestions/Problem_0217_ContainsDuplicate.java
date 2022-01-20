package topinterviewquestions;

import java.util.Arrays;
import java.util.HashSet;

public class Problem_0217_ContainsDuplicate {
	//solution1 use quicksort
	//用系统自带的排序（quicksort）time O(nlogn), space O(logn) for quick sort, if you use heapsort, time O(nlogn), space O(1)
	public boolean containsDuplicate(int[] nums) {
		Arrays.sort(nums);
		for (int i = 0; i < nums.length - 1; ++i) {
			if (nums[i] == nums[i + 1]) return true;
		}
		return false;
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	////solution2： use heapsort
	//time O(nlogn), space O(1)
	public boolean containsDuplicate_heapsort(int[] nums) {
		if (nums == null || nums.length < 2) {
			return false;
		}
		heapSort(nums);
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] == nums[i - 1]) {
				return true;
			}
		}
		return false;
	}

	public static void heapSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		for (int i = arr.length - 1; i >= 0; i--) {
			heapify(arr, i, arr.length);
		}
		int heapSize = arr.length;
		swap(arr, 0, --heapSize);
		while (heapSize > 0) {
			heapify(arr, 0, heapSize);
			swap(arr, 0, --heapSize);
		}
	}

	public static void heapify(int[] arr, int index, int heapSize) {
		int left = index * 2 + 1;
		while (left < heapSize) {
			int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
			largest = arr[largest] > arr[index] ? largest : index;
			if (largest == index) {
				break;
			}
			swap(arr, largest, index);
			index = largest;
			left = index * 2 + 1;
		}
	}


	//solution3
	// use hashset
	//time O(N) space O(N)
	public boolean containsDuplicate2(int[] nums) {
		if (nums == null || nums.length < 2) {
			return false;
		}
		HashSet<Integer> set = new HashSet<>();
		for (int num : nums) {
			if (set.contains(num)) {
				return true;
			}
			set.add(num);
		}
		return false;
	}


}

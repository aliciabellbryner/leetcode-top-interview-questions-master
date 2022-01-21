package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

public class Problem_0315_CountOfSmallerNumbersAfterSelf {

	public static class Node {
		public int value;
		public int index;

		public Node(int v, int i) {
			value = v;
			index = i;
		}
	}

	//用merge sort的思路来做，但是是从右往左填help的值
	//time O(NlogN)
	//space O(N)
	public static List<Integer> countSmaller(int[] nums) {
		List<Integer> res = new ArrayList<>();
		if (nums == null) {
			return res;
		}
		for (int i = 0; i < nums.length; i++) {
			res.add(0);
		}
		if (nums.length < 2) {
			return res;
		}
		Node[] arr = new Node[nums.length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new Node(nums[i], i);
		}
		process(arr, 0, arr.length - 1, res);
		return res;
	}

	public static void process(Node[] arr, int l, int r, List<Integer> res) {
		if (l == r) {
			return;
		}
		int mid = l + ((r - l) >> 1);
		process(arr, l, mid, res);
		process(arr, mid + 1, r, res);
		merge(arr, l, mid, r, res);
	}

	public static void merge(Node[] arr, int l, int mid, int r, List<Integer> res) {
		Node[] help = new Node[r - l + 1];
		int i = help.length - 1;//从右往左填help的值，大的放右小的放左,传统的mergesort是从左往右
		int p1 = mid;
		int p2 = r;
		while (p1 >= l && p2 >= mid + 1) {
			if (arr[p1].value > arr[p2].value) {
				res.set(arr[p1].index, res.get(arr[p1].index) + p2 - mid);//p2-mid是一定会比arr[p2]小的区间，
				// 既然arr[p1].value > arr[p2].value，则arr[p1].value一定比[mid,p2]整个区间的都小，所以一起加进去
			}
			help[i--] = arr[p1].value > arr[p2].value ? arr[p1--] : arr[p2--];
		}
		while (p1 >= l) {
			help[i--] = arr[p1--];
		}
		while (p2 >= mid + 1) {
			help[i--] = arr[p2--];
		}
		for (i = 0; i < help.length; i++) {
			arr[l + i] = help[i];
		}
	}

}

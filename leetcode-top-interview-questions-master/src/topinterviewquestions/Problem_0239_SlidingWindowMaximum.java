package topinterviewquestions;

import java.util.*;

public class Problem_0239_SlidingWindowMaximum {

	//using deque
	public static int[] maxSlidingWindow(int[] a, int k) {
		if (a == null || k <= 0) {
			return new int[0];
		}
		int N = a.length;
		int[] res = new int[N-k+1];
		int index = 0;
		// store index
		Deque<Integer> deque = new ArrayDeque<>();
		for (int i = 0; i < a.length; i++) {
			// remove numbers out of range k
			while (!deque.isEmpty() && deque.peek() < i - k + 1) {//deque.peek() equals to deque.peekFirst()
				deque.poll();//equals to pollFirst
			}
			// remove smaller numbers in k range as they are useless
			while (!deque.isEmpty() && a[deque.peekLast()] < a[i]) {
				deque.pollLast();
			}
			// deque contains index... res contains content
			deque.offer(i);
			if (i >= k - 1) {
				res[index++] = a[deque.peek()];//deque.peek() equals to deque.peekFirst()
			}
		}
		return res;
	}

	//solution by Zuo, not that good as it cost more time using linkedlist or arraylist to get and remove
	public static int[] maxSlidingWindow_zuo(int[] nums, int k) {
		if (nums == null || k < 1 || nums.length < k) {
			return null;
		}
		List<Integer> qmax = new ArrayList<>();//you can also use LinkedList here which have polllast pollfirst method
		int[] res = new int[nums.length - k + 1];
		int index = 0;
		for (int i = 0; i < nums.length; i++) {
			while (!qmax.isEmpty() && nums[qmax.get(qmax.size()-1)] <= nums[i]) {
				qmax.remove(qmax.size()-1);
			}
			qmax.add(i);
			if (qmax.get(0) == i - k) {
				qmax.remove(0);
			}
			if (i >= k - 1) {
				res[index++] = nums[qmax.get(0)];
			}
		}
		return res;
	}

	public static void main(String[] args) {
//		int[] test = new int[]{1,3,-1,-3,5,3,6,7};
//		System.out.println(Arrays.toString(maxSlidingWindow(test, 3)));

		Deque<Integer> q = new ArrayDeque<>();
		q.offer(1);
		q.offer(2);
		q.offer(3);
		System.out.println(q.peek());
	}
}

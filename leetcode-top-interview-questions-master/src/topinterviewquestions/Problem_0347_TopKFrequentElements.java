package topinterviewquestions;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
/*
Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.

Example 1:

Input: nums = [1,1,1,2,2,3], k = 2
Output: [1,2]
Example 2:

Input: nums = [1], k = 1
Output: [1]


Constraints:

1 <= nums.length <= 105
k is in the range [1, the number of unique elements in the array].
It is guaranteed that the answer is unique.


Follow up: Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 */
public class Problem_0347_TopKFrequentElements {

	public static class Node {
		public int num;
		public int count;

		public Node(int k) {
			num = k;
			count = 1;
		}
	}

	//Time complexity : O(Nlogk) if k<N and O(N) in the particular case of N=k.
	// That ensures time complexity to be better than O(NlogN).
	//Space complexity : O(N+k) to store the hash map with not more N elements and a heap with k elements.
	public static int[] topKFrequent(int[] nums, int k) {
		HashMap<Integer, Node> map = new HashMap<>();
		for (int num : nums) {
			if (!map.containsKey(num)) {
				map.put(num, new Node(num));
			} else {
				map.get(num).count++;
			}
		}
		//minheap存放每个Node，当数量大于k的时候，poll count最小的那个元素
		PriorityQueue<Node> heap = new PriorityQueue<>((n1, n2) -> n1.count - n2.count);
		for (Node node : map.values()) {
			if (heap.size() < k || (heap.size() == k && node.count > heap.peek().count)) {
				heap.add(node);
			}
			if (heap.size() > k) {
				heap.poll();//poll的是最小的元素
			}
		}
		int[] ans = new int[k];
		int index = 0;
		while (!heap.isEmpty()) {
			ans[index++] = heap.poll().num;
		}
		return ans;
	}


}

package topinterviewquestions;

import java.util.*;

/*
Given the root of a binary search tree, a target value, and an integer k, return the k values in the BST that are closest to the target. You may return the answer in any order.

You are guaranteed to have only one unique set of k values in the BST that are closest to the target.



Example 1:


Input: root = [4,2,5,1,3], target = 3.714286, k = 2
Output: [4,3]
Example 2:

Input: root = [1], target = 0.000000, k = 1
Output: [1]


Constraints:

The number of nodes in the tree is n.
1 <= k <= n <= 104.
0 <= Node.val <= 109
-109 <= target <= 109


Follow up: Assume that the BST is balanced. Could you solve it in less than O(n) runtime (where n = total nodes)?
 */
public class Problem_0272_ClosestBinarySearchTreeValueII {

/*
Solution
Overview
The problem is a BST variation of the "kth-smallest" classical problem. It is popular both in Google and Facebook, but these two companies are waiting for you to show different approaches to this problem. We're proposing 3 solutions here, and it's more an overview.

Prerequisites

Because of that, you might want first to check out the list of prerequisites:

Inorder traversal of BST is an array sorted in the ascending order. To compute inorder traversal follow the direction Left -> Node -> Right.

Closest BST value: find one closest element.

kth-smallest problem for the array could be solved by using heap in \mathcal{O}(N \log k)O(Nlogk) time, or by using quickselect in \mathcal{O}(N)O(N) time.

Google vs. Facebook

There are three ways to solve the problem:

Approach 1. Sort, \mathcal{O}(N \log N)O(NlogN) time. The idea is to convert BST into an array, sort it by the distance to the target, and return the k closest elements.

Approach 2. Facebook-friendly, heap, \mathcal{O}(N \log k)O(Nlogk) time. We could use the heap of capacity k, sorted by the distance to the target. It's not an optimal but very straightforward solution - traverse the tree, push the elements into the heap, and then return this heap. Facebook interviewer would insist on implementing this solution because the interviews are a bit shorter than Google ones, and it's important to get problem solved end-to-end.

Approach 3. Google-friendly, quickselect, \mathcal{O}(N)O(N) time. Here you could find a very detailed explanation of quickselect algorithm. In this article, we're going to provide a relatively brief implementation. Google guys usually prefer the best-time solutions, well-structured clean skeleton, even if you have no time to implement everything in time end-to-end.


Approach 1: Recursive Inorder + Sort, O(N log N) time
Intuition

img Figure 1. Sort.

The most straightforward approach is to build inorder traversal and then find the k closest elements using build-in sort.

Algorithm

Build an inorder traversal array.

Find the k closest to the target elements using build-in sort.

Implementation


Complexity Analysis

Time complexity: \mathcal{O}(N \log N)O(NlogN). \mathcal{O}(N)O(N) to build inorder traversal and then \mathcal{O}(N \log N)O(NlogN) to sort it.

Space complexity: \mathcal{O}(N)O(N) to store list nums of NN elements.


Approach 2: Recursive Inorder + Heap, O(N log k) time
img Figure 2. Heap.

Algorithm

Instantiate the heap with "less close element first" strategy so that the heap contains the elements that are closest to the target.

Use inorder traversal to traverse the tree following the direction Left -> Node -> Right.

Push all elements into heap during the traversal, keeping the heap size less than or equal to kk.
As a result, the heap contains kk elements that are closest to target. Convert it into a list and return.

Implementation


Optimisations

One could optimize the solution by adding the stop condition. Inorder traversal pops the elements in the sorted order. Hence once the distance of the current element to the target becomes greater than the distance of the first element in a heap, one could stop the computations. The overall worst-case time complexity would be still \mathcal{O}(N \log k)O(Nlogk), but the average time could be improved to \mathcal{O}(H \log k)O(Hlogk), where HH is a tree height.

Complexity Analysis

Time complexity: \mathcal{O}(N \log k)O(Nlogk) to push N elements into the heap of the size kk.

Space complexity: \mathcal{O}(k + H)O(k+H) to keep the heap of k elements and the recursion stack of the tree height.


Approach 3: QuickSelect, O(N) time.
Hoare's selection algorithm

Quickselect is a textbook algorithm typically used to solve the problems "find kth something": kth smallest, kth largest, etc. Like quicksort, quickselect was developed by Tony Hoare, and also known as Hoare's selection algorithm.

It has \mathcal{O}(N)O(N) average time complexity and widely used in practice. It is worth to note that its worst-case time complexity is \mathcal{O}(N^2)O(N
2
 ), although the probability of this worst-case is negligible.

The approach is the same as for quicksort.

One chooses a pivot and defines its position in a sorted array in a linear time using the so-called partition algorithm.

As an output, we have an array where the pivot is on its perfect position in the ascending sorted array, sorted by the frequency. All elements on the left of the pivot are more close to the target than the pivot, and all elements on the right are less close or on the same distance from the target.

The array is now split into two parts. If by chance, our pivot element took kth final position, then kk elements on the left are these kk closest elements we're looking for. If not, we can choose one more pivot and place it in its perfect position.

img Figure 3. Quickselect.

If that were a quicksort algorithm, one would have to process both parts of the array. That would result in \mathcal{O}(N \log N)O(NlogN) time complexity. In this case, there is no need to deal with both parts since one knows in which part to search for kth closest element, and that reduces the average time complexity to \mathcal{O}(N)O(N).

Algorithm

The algorithm is relatively straightforward:

Traverse the tree and convert it into array nums.

Implement the simple function to compute the distance to the target. Note that the distance is not unique. That means we need a partition algorithm that works fine with duplicates.

Work with nums array. Use a partition scheme (please check the next section) to place the pivot into its perfect position pivot_index in the sorted array, move more close elements to the left of the pivot, and less close or of the same distance - to the right.

Compare pivot_index and k.

If pivot_index == k, the pivot is the kth less close element, and all elements on the left are the kk closest elements to the target. Return these elements.

Otherwise, choose the side of the array to proceed recursively.

Hoare's Partition vs. Lomuto's Partition

There is a zoo of partition algorithms. The most simple one is Lomuto's Partition Scheme.

The drawback of Lomuto's partition is that it fails with duplicates.

Here we work with an array of unique elements, but they are compared by the distances to the target, which are not unique. That's why we choose Hoare's Partition here.

Hoare's partition is more efficient than Lomuto's partition because it does three times fewer swaps on average, and creates efficient partitions even when all values are equal.

Here is how it works:

Move pivot at the end of the array using swap.

Set the pointer at the beginning of the array store_index = left.

Iterate over the array and move all more close elements to the left swap(store_index, i). Move store_index one step to the right after each swap.

Move the pivot to its final place, and return this index.

Implementation


Complexity Analysis

Time complexity: \mathcal{O}(N)O(N), \mathcal{O}(N^2)O(N
2
 ) in the worst case. Please refer to this card for the good detailed explanation of Master Theorem. Master Theorem helps to get an average complexity by writing the algorithm cost as T(N) = a T(N / b) + f(N)T(N)=aT(N/b)+f(N). Here we have an example of Master Theorem case III: T(N) = T \left(\frac{N}{2}\right) + NT(N)=T(
2
N
​
 )+N, that results in \mathcal{O}(N)O(N) time complexity. That's the case of random pivots.

In the worst-case of constantly bad chosen pivots, the problem is not divided by half at each step, it becomes just one element less, that leads to \mathcal{O}(N^2)O(N
2
 ) time complexity. It happens, for example, if at each step you choose the pivot not randomly, but take the rightmost element. For the random pivot choice, the probability of having such a worst-case is negligibly small.

Space complexity: \mathcal{O}(N)O(N) to store nums.
 */
class Solution1 {
	public void inorder(TreeNode root, List<Integer> nums) {
		if (root == null) return;
		inorder(root.left, nums);
		nums.add(root.val);
		inorder(root.right, nums);
	}

	public List<Integer> closestKValues(TreeNode root, double target, int k) {
		List<Integer> nums = new ArrayList();
		inorder(root, nums);

		Collections.sort(nums, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Math.abs(o1 - target) < Math.abs(o2 - target) ? -1 : 1;
			}
		});
		return nums.subList(0, k);
	}
}
	class Solution2 {
		public void inorder(TreeNode r, List<Integer> nums, Queue<Integer> heap, int k) {
			if (r == null)
				return;

			inorder(r.left, nums, heap, k);
			heap.add(r.val);
			if (heap.size() > k)
				heap.remove();
			inorder(r.right, nums, heap, k);
		}

		public List<Integer> closestKValues(TreeNode root, double target, int k) {
			List<Integer> nums = new ArrayList();

			// init heap 'less close element first'
			Queue<Integer> heap = new PriorityQueue<>((o1, o2) -> Math.abs(o1 - target) > Math.abs(o2 - target) ? -1 : 1);
			inorder(root, nums, heap, k);
			return new ArrayList<>(heap);
		}
	}

	class Solution3 {
		List<Integer> nums;
		double target;

		public void swap(int a, int b) {
			int tmp = nums.get(a);
			nums.set(a, nums.get(b));
			nums.set(b, tmp);
		}

		public void inorder(TreeNode r, List<Integer> nums) {
			if (r == null)
				return;

			inorder(r.left, nums);
			nums.add(r.val);
			inorder(r.right, nums);
		}

		public int partition(int left, int right, int pivotIndex) {
			double pivotDist = dist(pivotIndex);
			// 1. move pivot to end
			swap(pivotIndex, right);
			int storeIndex = left;

			// 2. move more close elements to the left
			for (int i = left; i <= right; i++) {
				if (dist(i) < pivotDist) {
					swap(storeIndex, i);
					storeIndex++;
				}
			}

			// 3. move pivot to its final place
			swap(storeIndex, right);

			return storeIndex;
		}

		public void quickselect(int left, int right, int kSmallest) {
        /*
        Sort a list within left..right till kth less close element
        takes its place.
        */

			// base case: the list contains only one element
			if (left >= right) return;

			// select a random pivot_index
			Random randomNum = new Random();
			int pivotIndex = left + randomNum.nextInt(right - left);

			// find the pivot position in a sorted list
			pivotIndex = partition(left, right, pivotIndex);

			// if the pivot is in its final sorted position
			if (kSmallest == pivotIndex) {
				return;
			} else if (kSmallest < pivotIndex) {
				// go left
				quickselect(left, pivotIndex - 1, kSmallest);
			} else {
				// go right
				quickselect(pivotIndex + 1, right, kSmallest);
			}
		}

		public double dist(int idx) {
			return Math.abs(nums.get(idx) - target);
		}

		public List<Integer> closestKValues(TreeNode root, double target, int k) {
			nums = new ArrayList();
			this.target = target;
			inorder(root, nums);
			quickselect(0, nums.size() - 1, k);
			return nums.subList(0, k);
		}
	}


	  public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }
}

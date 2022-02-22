package topinterviewquestions;

import java.util.HashMap;
import java.util.Map;

/*
Given an integer array nums and an integer k, return the maximum length of a subarray that sums to k. If there is not one, return 0 instead.



Example 1:

Input: nums = [1,-1,5,-2,3], k = 3
Output: 4
Explanation: The subarray [1, -1, 5, -2] sums to 3 and is the longest.
Example 2:

Input: nums = [-2,-1,2,1], k = 1
Output: 2
Explanation: The subarray [-1, 2] sums to 1 and is the longest.


Constraints:

1 <= nums.length <= 2 * 105
-104 <= nums[i] <= 104
-109 <= k <= 109
 */
public class Problem_0325_MaximumSizeSubarraySumEqualsK {
/*
Approach: Prefix Sum + Hash Map
Intuition

How many possible subarrays are there in an array of size n? There is 1 subarray with length n, 2 subarrays with length n - 1, 3 subarrays with length n - 2 and so on. This means there are n + (n - 1) + (n - 2) + ... + 2 + 1 = \frac{n(n + 1)}{2}n+(n−1)+(n−2)+...+2+1=
2
n(n+1)
​
  possible subarrays. This question has bounds of n <= 2 * 10^5n<=2∗10
5
 , which means naively checking every possible subarray could mean looking at over 20 billion subarrays. This is far too slow and we need a better solution.

Fortunately, we don't care about the vast majority of the subarrays - only the ones that have a sum equal to k, so we can do much better than the naive solution. The fact that a subarray needs to be contiguous helps us a lot - it allows us to make use of an idea called prefix sum. For those who are unfamiliar with the term prefix sum, a prefix sum is simply the running total of an array. For example, for nums = [1, 2, 2, 3], the prefix sum would be prefix = [1, 3, 5, 8]. For an index i, prefix[i] is the sum of all numbers in nums up to and including the number at index i.

Let's take a step back - if the question was instead asking "does a subarray with sum k exist," how would we detect a subarray having sum k? In a prefix sum, differences between elements represent subarray sums. For example, if you had prefix representing the prefix sum for an array nums, then prefix[10] - prefix[2] would be equal to the sum of the subarray in nums from index 3 to index 10. This is because prefix[10] = nums[0] + nums[1] + nums[2] + ... + nums[10] and prefix[2] = nums[0] + nums[1] + nums[2]. As you can see, all of prefix[2] is contained within prefix[10], and subtracting it leaves nums[3] + ... + nums[10].

Current
1 / 5

Therefore, if there is a subarray with sum k in nums, then there is a pair of numbers in prefix whose difference is k. This rephrased problem may seem familiar to you; it's basically a variation of Two Sum. In Two Sum, we must find two different numbers in an array that equal a target value when added. We can accomplish this in one pass by storing previously seen numbers in a hash table and, for each number, check if its complement has already been seen. We can adopt a similar technique to solve this problem - store the previously seen prefix sums in a hash map for quick (O(1)O(1)) checking, and check if a specific value exists in the hash map as we iterate along prefix. In this case, as we iterate from left to right along prefix, if prefix[i] - k has already been seen, then we found a pair of indices for a subarray with sum k.

Now that we've established how to detect subarrays with sum k, we need to deal with the other part of the original problem - finding the length of the longest subarray with sum k. As mentioned before, we use a hash map to check for existing numbers quickly. Similar to Two Sum, we can store indices as values in this hash map. Therefore, when we find a pair, we can use the stored index and the current index to find the length of the subarray formed by the index pair.

We don't actually have a prefix array, nor do we need it - one was just being used to describe examples above. Instead, we can use an integer variable to keep track of the prefix sum, and at each number, store the prefix sum up to that number (inclusive) in a hash map along with the current index. If we run into a duplicate (which is possible because of negative numbers), we should not update the index in the hash map because we want the longest subarray, so we want to keep the index as far to the left as possible. For example, if we had the input nums = [1, -1, 1, 3] and k = 4, then the longest subarray would be the entire array. The prefix sum at each step would be [1, 0, 1, 4]. As you can see, we always want to pick the leftmost index to maximize length. Therefore, when we get to the third element and see that 1 already exists in the hash map, we should not replace the value with the current index.

One more thing: we need to consider the case when the prefix sum is equal to k. We can either specifically check when the prefix sum is equal to k or we can initialize our hash map with a key of 0 corresponding to a value of -1. If you had, for example, nums = [1, 2] and k = 1, then the longest subarray is, of course, 1. However, at the first element, our prefix sum is 1, which means we would need to find a 0 in our hash map to make a pair. Without checking for this case, our algorithm will think that no subarray exists with a sum of 1.

Algorithm

Initialize three variables:

An integer prefixSum that keeps track of the prefix sum of nums as 0.
An integer longestSubarray that will keep track of the longest subarray with sum k as 0.
A hash map indices that has keys of prefix sums seen so far and values of the first index that each key was seen.
Iterate through nums. At each index i, add nums[i] to prefixSum. Then, make the following checks:

If prefixSum == k, that means the sum of the array up to this index is equal to k. Update longestSubarray = i + 1 (because i is 0-indexed)
If prefixSum - k exists in indices, that means there is a subarray with sum k ending at the current i. The length will be i - indices[prefixSum - k]. If this length is greater than longestSubarray, update longestSubarray.
If the current prefixSum does not yet exist in indices, then set indices[prefixSum] = i. Only do this if it does not already exist because we only want the earliest instance of this presum.
Return longestSubarray.

Current
1 / 6

Implementation


Complexity Analysis

Given NN as the length of nums,

Time complexity: O(N)O(N)

We only make one pass through nums, each time doing a constant amount of work. All hash map operations are O(1)O(1).

Space complexity: O(N)O(N)

Our hash map can potentially hold as many key-value pairs as there are numbers in nums. An example of this is when there are no negative numbers in the array.
 */
class Solution {
	public int maxSubArrayLen(int[] nums, int k) {
		int prefixSum = 0;
		int longestSubarray = 0;
		HashMap<Integer, Integer> indices = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			prefixSum += nums[i];

			// Check if all of the numbers seen so far sum to k.
			if (prefixSum == k) {
				longestSubarray = i + 1;
			}

			// If any subarray seen so far sums to k, then
			// update the length of the longest_subarray.
			if (indices.containsKey(prefixSum - k)) {
				longestSubarray = Math.max(longestSubarray, i - indices.get(prefixSum - k));
			}

			// Only add the current prefix_sum index pair to the
			// map if the prefix_sum is not already in the map.
			if (!indices.containsKey(prefixSum)) {
				indices.put(prefixSum, i);
			}
		}

		return longestSubarray;
	}
}

//discussion
public int maxSubArrayLen(int[] nums, int k) {
	Map<Integer, Integer> seen = new HashMap<>();
	int max = 0;
	for (int i = 0, sum = 0; i < nums.length; i++) {
		sum += nums[i];
		if (sum == k) max = i + 1; /* i+1 must be longest by now, so no need to max() */
		else max = Math.max(max, i - seen.getOrDefault(sum - k, i)); /* [0,j]=sum-k, (j,i]=k */
		seen.putIfAbsent(sum, i);
	}
	return max;
}



//The subarray sum reminds me the range sum problem. Preprocess the input array such that you get
//the range sum in constant time.
//sum[i] means the sum from 0 to i inclusively
//the sum from i to j is sum[j] - sum[i - 1] except that from 0 to j is sum[j].
//
//j-i is equal to the length of subarray of original array. we want to find the max(j - i)
//for any sum[j] we need to find if there is a previous sum[i] such that sum[j] - sum[i] = k
//Instead of scanning from 0 to j -1 to find such i, we use hashmap to do the job in constant time.
//However, there might be duplicate value of of sum[i] we should avoid overriding its index as we want the max j - i, so we want to keep i as left as possible.
	public class Solution3 {
		public int maxSubArrayLen(int[] nums, int k) {
			if (nums == null || nums.length == 0)
				return 0;
			int n = nums.length;
			for (int i = 1; i < n; i++)
				nums[i] += nums[i - 1];
			Map<Integer, Integer> map = new HashMap<>();
			map.put(0, -1); // add this fake entry to make sum from 0 to j consistent
			int max = 0;
			for (int i = 0; i < n; i++) {
				if (map.containsKey(nums[i] - k))
					max = Math.max(max, i - map.get(nums[i] - k));
				if (!map.containsKey(nums[i])) // keep only 1st duplicate as we want first index as left as possible
					map.put(nums[i], i);
			}
			return max;
		}
	}
}

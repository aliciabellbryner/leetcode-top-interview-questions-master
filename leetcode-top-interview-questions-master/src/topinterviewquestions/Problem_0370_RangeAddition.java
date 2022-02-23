package topinterviewquestions;
/*
You are given an integer length and an array updates where updates[i] = [startIdxi, endIdxi, inci].

You have an array arr of length length with all zeros, and you have some operation to apply on arr. In the ith operation, you should increment all the elements arr[startIdxi], arr[startIdxi + 1], ..., arr[endIdxi] by inci.

Return arr after applying all the updates.



Example 1:


Input: length = 5, updates = [[1,3,2],[2,4,3],[0,2,-2]]
Output: [-2,0,3,5,3]
Example 2:

Input: length = 10, updates = [[2,4,6],[5,6,8],[1,9,-4]]
Output: [0,-4,2,2,2,4,4,-4,-4,-4]


Constraints:

1 <= length <= 105
0 <= updates.length <= 104
0 <= startIdxi <= endIdxi < length
-1000 <= inci <= 1000
 */
public class Problem_0370_RangeAddition {

/*
Approach 1: Naïve Approach
Algorithm

The algorithm is trivial. For each update query, we iterate over the required update range and update each element individually.

Each query of updates is a tuple of 3 integers: start, endstart,end (the start and end indexes for the update range) and valval (the amount by which each array element in this range must be incremented).


Complexity Analysis

Time complexity : O(n \cdot k)O(n⋅k) (worst case) where kk is the number of update queries and nn is the length of the array. Each of the kk update operations take up O(n)O(n) time (worst case, when all updates are over the entire range).

Space complexity : O(1)O(1). No extra space required.


Approach 2: Range Caching
Intuition

There is only one read query on the entire range, and it occurs at the end of all update queries. Additionally, the order of processing update queries is irrelevant.

Cumulative sums or partial_sum operations apply the effects of past elements to the future elements in the sequence.

Algorithm

The algorithm makes use of the above intuition to simply store changes at the borders of the update ranges (instead of processing the entire range). Finally a single post processing operation is carried out over the entire output array.

The two steps that are required are as follows:

For each update query (start, end, val)(start,end,val) on the array arrarr, we need to do only two operations:

Update startstart boundary of the range:
arr_{start} = arr_{start} + valarr
start
​
 =arr
start
​
 +val

Update just beyond the endend boundary of the range:
arr_{end+1} = arr_{end+1} - valarr
end+1
​
 =arr
end+1
​
 −val

Final Transformation. The cumulative sum of the entire array is taken (0 - based indexing)

arr_i = arr_i + arr_{i-1} \quad \forall \quad i \in [1, n)arr
i
​
 =arr
i
​
 +arr
i−1
​
 ∀i∈[1,n)


Formal Explanation

For each update query (start, end, val)(start,end,val) on the array arrarr, the goal is to achieve the result:

arr_i = arr_i + val \quad \forall \quad i \in [start, end]arr
i
​
 =arr
i
​
 +val∀i∈[start,end]

Applying the final transformation, ensures two things:

It carries over the +val+val increment over to every element arr_i \; \forall \; i \ge startarr
i
​
 ∀i≥start.
It carries over the -val−val increment (equivalently, a +val+val decrement) over to every element arr_j \; \forall \; j \gt endarr
j
​
 ∀j>end.
The net result is that:

\begin{aligned} arr_i &= arr_i + val \quad && \forall \quad i \in [start, end] \\ arr_j &= arr_j + val - val = arr_j \quad && \forall \quad i \in (end, length) \end{aligned}
arr
i
​

arr
j
​

​

=arr
i
​
 +val
=arr
j
​
 +val−val=arr
j
​

​

​

∀i∈[start,end]
∀i∈(end,length)
​


which meets our end goal. It is easy to see that the updates over a range did not carry over beyond it due to the compensating effect of the -val−val increment over the +val+val increment.

It is good to note that this works for multiple update queries because the particular binary operations here (namely addition and subtraction):

are closed over the entire domain of Integers. (A counter example is division which is not closed over all Integers).

are complementary operations. (As a counter example multiplication and division are not always complimentary due to possible loss of precision when dividing Integers).

Complexity Analysis

Time complexity : O(n + k)O(n+k). Each of the kk update operations is done in constant O(1)O(1) time. The final cumulative sum transformation takes O(n)O(n) time always.

Space complexity : O(1)O(1). No extra space required.


Further Thoughts
An extension of this problem is to apply such updates on an array where all elements are not the same.

In this case, the second approach requires that the original configuration must be stored separately before applying the final transformation. This incurs an additional space complexity of O(n)O(n).

@StefanPochmann suggested another method (see comment section) which does not require extra space, but requires an extra linear pass over the entire array. The idea is to apply reverse partial_sum operation on the array (for example, array [2, 3, 10, 5][2,3,10,5] transforms to [2, 1, 7, -5][2,1,7,−5]) as an initialization step and then proceed with the second method as usual.

Another general, more complex version of this problem comprises of multiple read and update queries over ranges. Such problems can be solved quite efficiently with Segment Trees by applying a popular trick called Lazy Propogation.

Analysis written by @babhishek21.
 */

	class Solution2 {
		public int[] getModifiedArray(int length, int[][] updates) {
			int[] res = new int[length];
			for (int[] update : updates) {
				int start = update[0], end = update[1], val = update[2];
				res[start] += val;
				if (end < length - 1) res[end + 1] -= val;
			}
			int cur = 0;
			// "range caching"
			for (int i = 0; i < length; i ++) {
				cur += res[i];
				res[i] = cur;
			}
			return res;
		}
	}


	//diss
	//Java O(K + N)time complexity Solution
	//Just store every start index for each value and at end index plus one minus it
	//
	//for example it will look like:
	//
	//[1 , 3 , 2] , [2, 3, 3] (length = 5)
	//
	//res[ 0, 2, ,0, 0 -2 ]
	//
	//res[ 0 ,2, 3, 0, -5]
	//
	//sum 0, 2, 5, 5, 0
	//
	//res[0, 2, 5, 5, 0]

	public int[] getModifiedArray(int length, int[][] updates) {

		int[] res = new int[length];
		for(int[] update : updates) {
			int value = update[2];
			int start = update[0];
			int end = update[1];

			res[start] += value;

			if(end < length - 1)
				res[end + 1] -= value;

		}

		int sum = 0;
		for(int i = 0; i < length; i++) {
			sum += res[i];
			res[i] = sum;
		}

		return res;
	}



	//Java O(n+k) time O(1) space with algorithm explained
	//segment [i,j] is made of two parts [0,i-1] and [0, j]
	//so [i,j] increase 2 is same as [0,j] increase 2 and [0,i-1] increase -2. so you only need to update value at nums[j] with inc and nums[i-1] -inc. initially nums[i] is defined as all elements [0,i] increases inc
	//
	//then think from length-1 to 0 backward. The last spot nums[length-1] does not need any modification.
	//nums[length-2] value should be updated as nums[length-2] + nums[length-1] as the latter covers the front. but front does not influence what is after it. so every spot should be updated as + the accumulate sum from the end.

	public class Solution {
		public int[] getModifiedArray(int length, int[][] updates) {
			int[] nums = new int[length];
			for (int[] update : updates) {
				nums[update[1]] += update[2];
				if (update[0] > 0) {
					nums[update[0] - 1] -= update[2];
				}
			}

			int sum = nums[length - 1];
			for (int i = length - 2; i >= 0; i--) {
				int tmp = sum + nums[i];
				nums[i] += sum;
				sum = tmp;
			}
			return nums;
		}
	}
}

package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/*
Given an integer array nums of length n, return true if there is a triplet (i, j, k) which satisfies the following conditions:

0 < i, i + 1 < j, j + 1 < k < n - 1
The sum of subarrays (0, i - 1), (i + 1, j - 1), (j + 1, k - 1) and (k + 1, n - 1) is equal.
A subarray (l, r) represents a slice of the original array starting from the element indexed l to the element indexed r.


Example 1:

Input: nums = [1,2,1,2,1,2,1]
Output: true
Explanation:
i = 1, j = 3, k = 5.
sum(0, i - 1) = sum(0, 0) = 1
sum(i + 1, j - 1) = sum(2, 2) = 1
sum(j + 1, k - 1) = sum(4, 4) = 1
sum(k + 1, n - 1) = sum(6, 6) = 1
Example 2:

Input: nums = [1,2,1,2,1,2,1,2]
Output: false


Constraints:

n == nums.length
1 <= n <= 2000
-106 <= nums[i] <= 106
 */
public class Problem_0548_SplitArrayWithEqualSum {

/*
Solution
Approach #1 Brute Force [Time Limit Exceeded]
Algorithm

Before we start looking at any of the approaches for solving this problem, firstly we need to look at the limits imposed on ii, jj and kk by the given set of constraints. The figure below shows the maximum and minimum values that ii, jj and kk can assume.

Split_Array

Thus, the limits based on the length of the array nn can now be rewritten as:

1 ≤ i ≤ n-6

i+2 ≤ j ≤ n-4

j+2 ≤ k ≤ n-2

Having discussed the limits imposed on the cuts ii, jj, kk that we will apply on the given array numsnums, let's look at the first solution that comes to our mind.

We simply traverse over all the elements of the array. We consider all the possible subarrays taking care of the constraints imposed on the cuts, and check if any such cuts exist which satisfy the given equal sum quadruples criteria.


Complexity Analysis

Time complexity : O(n^4)O(n
4
 ). Four for loops inside each other each with a worst case run of length nn.
Space complexity : O(1)O(1). Constant Space required.
Approach #2 Cumulative Sum [Time Limit Exceeded]
Algorithm

In the brute force approach, we traversed over the subarrays for every triplet of cuts considered. Rather than doing this, we can save some calculation effort if we make use of a cumulative sum array sumsum, where sum[i]sum[i] stores the cumulative sum of the array numsnums upto the i^{th}i
th
  element. Thus, now in order to find the sum\big(subarray(i:j)\big)sum(subarray(i:j)), we can simply use sum[j]-sum[i]sum[j]−sum[i]. Rest of the process remains the same.


Complexity Analysis

Time complexity : O(n^3)O(n
3
 ). Three for loops are there, one within the other.

Space complexity : O(n)O(n). sumsum array of size nn is used for storing cumulative sum.

Approach #3 Slightly Better Approach [Time Limit Exceeded]
Algorithm

We can improve the previous implementation to some extent if we stop checking for further quadruples if the first and second parts formed till now don't have equal sums. This idea is used in the current implementation.


Complexity Analysis

Time complexity : O(n^3)O(n
3
 ). Three loops are there.

Space complexity : O(n)O(n). sumsum array of size nn is used for storing the cumulative sum.

Approach #4 Using HashMap [Time limit Exceeded]
Algorithm

In this approach, we create a data structure called mapmap which is simply a HashMap, with data arranged in the format:

\big\{csum(i):[i_1,i_2,i_3,....]\big\}{csum(i):[i
1
​
 ,i
2
​
 ,i
3
​
 ,....]}, here csum(i)csum(i) represents the cumulative sum in the given array numsnums upto the i^{th}i
th
  index and its corresponding value represents indices upto which cumulative sum=csum(i).

Once we create this mapmap, the solutions gets simplified a lot. Consider only the first two cuts formed by ii and jj. Then, the cumulative sum upto the (j-1)^{th}(j−1)
th
  index will be given by: csum(j-1)=sum(part1) + nums[i] + sum(part2)csum(j−1)=sum(part1)+nums[i]+sum(part2). Now, if we want the first two parts to have the same sum, the same cumulative sum can be rewritten as:

csum'(j-1) = csum(i-1) + nums[i] + csum(i-1) = 2csum(i-1) + nums[i]csum
′
 (j−1)=csum(i−1)+nums[i]+csum(i−1)=2csum(i−1)+nums[i].

Thus, we traverse over the given array, changing the value of the index ii forming the first cut, and look if the mapmap formed initially contains a cumulative sum equal to csum'(j-1)csum
′
 (j−1). If mapmap contains such a cumulative sum, we consider every possible index jj satisfying the given constraints and look for the equalities of the first cumulative sum with the third and the fourth parts.

Following the similar lines as the discussion above, the cumulative sum upto the third cut by k^{th}k
th
  index is given by

csum(k-1) = sum(part1) + nums[i] + sum(part2) + nums[j] + sum(part3)csum(k−1)=sum(part1)+nums[i]+sum(part2)+nums[j]+sum(part3).

For equality of sum, the condition becomes:

csum'(k-1) = 3*csum(i-1) + nums[i] + nums[j]csum
′
 (k−1)=3∗csum(i−1)+nums[i]+nums[j].

Similarly, the cumulative sum upto the last index becomes:

csum(end) = sum(part1) + nums[i] + sum(part2) + nums[j] + sum(part3) + nums[k] + sum(part4)csum(end)=sum(part1)+nums[i]+sum(part2)+nums[j]+sum(part3)+nums[k]+sum(part4).

Again, for equality, the condition becomes:

csum'(end) = 4*csum(i-1) + nums[i] + nums[j] + nums[k]csum
′
 (end)=4∗csum(i−1)+nums[i]+nums[j]+nums[k].

For every cut chosen, we look if the required cumulative sum exists in mapmap. Thus, we need not calculate sums again and again or traverse over the array for all the triplets (i, j, k)(i,j,k) possible. Rather, now, we directly know, what cumulative sum to look for in the mapmap, which reduces a lot of computations.


Complexity Analysis

Time complexity : O(n^3)O(n
3
 ). Three nested loops are there and every loop runs nn times in the worst case. Consider the worstcase [0,0,0....,1,1,1,1,1,1,1].

Space complexity : O(n)O(n). HashMap size can go upto nn.

Approach #5 Using Cumulative Sum and HashSet [Accepted]
Algorithm

In this approach, firstly we form a cumulative sum array sumsum, where sum[i]sum[i] stores the cumulative sum of the array numsnums upto the i^{th}i
th
  index. Then, we start by traversing over the possible positions for the middle cut formed by jj. For every jj, firstly, we find all the left cut's positions, ii, that lead to equalizing the sum of the first and the second part (i.e. sum[i-1] = sum [j-1] - sum[i]sum[i−1]=sum[j−1]−sum[i]) and store such sums in the setset (a new HashSet is formed for every jj chosen). Thus, the presence of a sum in setset implies that such a sum is possible for having equal sum of the first and second part for the current position of the middle cut(jj).

Then, we go for the right cut and find the position of the right cut that leads to equal sum of the third and the fourth part (sum[n-1]-sum[k]=sum[k-1] - sum[j]sum[n−1]−sum[k]=sum[k−1]−sum[j]), for the same middle cut as chosen earlier. We also, look if the same sum exists in the setset. If so, such a triplet (i, j, k)(i,j,k) exists which satisfies the required criteria, otherwise not.

Look at the animation below for a visual representation of the process:

Current
1 / 10

Complexity Analysis

Time complexity : O(n^2)O(n
2
 ). One outer loop and two inner loops are used.

Space complexity : O(n)O(n). HashSet size can go upto nn.
 */

    public class Solution2 {
        public boolean splitArray(int[] nums) {
            if (nums.length < 7)
                return false;
            int[] sum = new int[nums.length];
            sum[0] = nums[0];
            for (int i = 1; i < nums.length; i++) {
                sum[i] = sum[i - 1] + nums[i];
            }
            for (int i = 1; i < nums.length - 5; i++) {
                int sum1 = sum[i - 1];
                for (int j = i + 2; j < nums.length - 3; j++) {
                    int sum2 = sum[j - 1] - sum[i];
                    for (int k = j + 2; k < nums.length - 1; k++) {
                        int sum3 = sum[k - 1] - sum[j];
                        int sum4 = sum[nums.length - 1] - sum[k];
                        if (sum1 == sum2 && sum3 == sum4 && sum2 == sum4)
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public class Solution3 {
        public boolean splitArray(int[] nums) {
            if (nums.length < 7)
                return false;

            int[] sum = new int[nums.length];
            sum[0] = nums[0];
            for (int i = 1; i < nums.length; i++) {
                sum[i] = sum[i - 1] + nums[i];
            }
            for (int i = 1; i < nums.length - 5; i++) {
                int sum1 = sum[i - 1];
                for (int j = i + 2; j < nums.length - 3; j++) {
                    int sum2 = sum[j - 1] - sum[i];
                    if (sum1 != sum2)
                        continue;
                    for (int k = j + 2; k < nums.length - 1; k++) {
                        int sum3 = sum[k - 1] - sum[j];
                        int sum4 = sum[nums.length - 1] - sum[k];
                        if (sum3 == sum4 && sum2 == sum4)
                            return true;
                    }
                }
            }
            return false;
        }
    }


    public class Solution4 {
        public boolean splitArray(int[] nums) {
            HashMap< Integer, ArrayList < Integer >> map = new HashMap < > ();
            int summ = 0, tot = 0;
            for (int i = 0; i < nums.length; i++) {
                summ += nums[i];
                if (map.containsKey(summ))
                    map.get(summ).add(i);
                else {
                    map.put(summ, new ArrayList< Integer >());
                    map.get(summ).add(i);
                }
                tot += nums[i];
            }
            summ = nums[0];
            for (int i = 1; i < nums.length - 5; i++) {
                if (map.containsKey(2 * summ + nums[i])) {
                    for (int j: map.get(2 * summ + nums[i])) {
                        j++;
                        if (j > i + 1 && j < nums.length - 3 && map.containsKey(3 * summ + nums[i] + nums[j])) {
                            for (int k: map.get(3 * summ + nums[j] + nums[i])) {
                                k++;
                                if (k < nums.length - 1 && k > j + 1 && 4 * summ + nums[i] + nums[j] + nums[k] == tot)
                                    return true;
                            }
                        }
                    }
                }
                summ += nums[i];
            }
            return false;
        }
    }
    public class Solution5 {
        public boolean splitArray(int[] nums) {
            if (nums.length < 7)
                return false;
            int[] sum = new int[nums.length];
            sum[0] = nums[0];
            for (int i = 1; i < nums.length; i++) {
                sum[i] = sum[i - 1] + nums[i];
            }
            for (int j = 3; j < nums.length - 3; j++) {
                HashSet< Integer > set = new HashSet < > ();
                for (int i = 1; i < j - 1; i++) {
                    if (sum[i - 1] == sum[j - 1] - sum[i])
                        set.add(sum[i - 1]);
                }
                for (int k = j + 2; k < nums.length - 1; k++) {
                    if (sum[nums.length - 1] - sum[k] == sum[k - 1] - sum[j] && set.contains(sum[k - 1] - sum[j]))
                        return true;
                }
            }
            return false;
        }
    }


    //diss
    //Here j is used for middle cut, i for left cut and k for right cut.
    //Iterate middle cuts and then find left cuts which divides the first half into two equal quarters, store that quarter sums in the hashset. Then find right cuts which divides the second half into two equal quarters and check if quarter sum is present in the hashset. If yes return true.
    public class Solution7 {
        public boolean splitArray(int[] nums) {
            if (nums.length < 7)
                return false;
            int[] sum = new int[nums.length];
            sum[0] = nums[0];
            for (int i = 1; i < nums.length; i++) {
                sum[i] = sum[i - 1] + nums[i];
            }
            for (int j = 3; j < nums.length - 3; j++) {
                HashSet < Integer > set = new HashSet < > ();
                for (int i = 1; i < j - 1; i++) {
                    if (sum[i - 1] == sum[j - 1] - sum[i])
                        set.add(sum[i - 1]);
                }
                for (int k = j + 2; k < nums.length - 1; k++) {
                    if (sum[nums.length - 1] - sum[k] == sum[k - 1] - sum[j] && set.contains(sum[k - 1] - sum[j]))
                        return true;
                }
            }
            return false;
        }
    }

    //DFS
    //Just think this problem as a DFS. What we need is to search for 3 positions (i, j, k) and see if they divide the array to 4 parts with same summary. Some tricks:
    //
    //Calculate left on the fly. Thus at last we don't need to calc summary of the 4th part.
    //Skip 0 during calculate target because adding zero won't change it.
    public class Solution8 {
        public boolean splitArray(int[] nums) {
            int sum = 0, target = 0;
            for (int num : nums) sum += num;
            for (int i = 1; i + 5 < nums.length; i++) {
                if (i != 1 && nums[i - 1] == 0  && nums[i] == 0) continue;
                target += nums[i - 1];
                if (dfs(nums, i + 1, target, sum - target - nums[i], 1)) return true;
            }
            return false;
        }

        private boolean dfs(int[] nums, int start, int target, int left, int depth) {
            if (depth == 3) {
                if (left == target) return true;
                return false;
            }

            int sub = 0;
            for (int j = start + 1; j + 5 - depth * 2 < nums.length; j++) {
                sub += nums[j - 1];
                if (sub == target) {
                    if (dfs(nums, j + 1, target, left - sub - nums[j], depth + 1)) {
                        return true;
                    }
                }
            }

            return false;
        }
    }


}

package topinterviewquestions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
Constraints:

n == nums.length
2 <= n <= 105
-105 <= k, nums[i] <= 105
 */
public class Problem_2025_MaximumNumberofWaystoPartitionanArray {
    //diss

    //https://leetcode.com/problems/maximum-number-of-ways-to-partition-an-array/discuss/1499316/Java-O(n)-prefix-sum
    //Our task is trying to find at least two partitions with equals sums. Obvious :), we have only one sum to find for our partitions : totalSum / 2. We need to find two partitions with sum = totalSum / 2 and other partitions with sum = 0 between them.
    //
    //Example :
    //
    //[8, -5, 3, 2, -2, 2, 3], k = 3
    //prefix sum [8, 3, 6, 8, 6, 8, 11]
    //total sum = 11
    //sum [-5, 3, 2] = 0
    //sum [-2, 2] = 0
    //if change 8 -> 3 :
    //
    //nums :     [3, -5, 3, 2, -2, 2, 3]
    //prefix sum [3, -2, 1, 3,  1, 3, 6]
    //total sum = 6
    //sum to find = 3
    //result = 3
    //
    //[3], [-5, 3, 2, 2, -2, 3]
    //[3, -5, 3, 2], [2, -2, 3]
    //[3, -5, 3, 2, 2, -2], [3]
    class Solution1 {
        //time - O(n), space - O(n)
        public int waysToPartition(int[] nums, int k) {
            int n = nums.length;

            int[] preSum = new int[n];
            preSum[0] = nums[0];
            Map<Integer, Integer> count = new HashMap<>(); //contribution of prefixes without changing
            count.put(preSum[0], 1);

            for (int i = 1; i < n - 1; i++){
                preSum[i] += preSum[i - 1] + nums[i];
                count.put(preSum[i], count.getOrDefault(preSum[i], 0) + 1);
            }
            preSum[n - 1] += preSum[n - 2] + nums[n - 1]; //last step doesn't add into 'count' map, because we're trying to find at least two parts.

            int sum = preSum[n - 1];
            int res = 0;
            if (sum % 2 == 0)
                res = count.getOrDefault(sum / 2, 0); //res without changing

            Map<Integer, Integer> countPrev = new HashMap<>(); //contribution of prefixes before current step
            for (int i = 0; i < n; i++){
                int diff = k - nums[i];
                int changedSum = sum + diff;
                if (changedSum % 2 == 0) {
                    res = Math.max(res, count.getOrDefault(changedSum / 2 - diff, 0) + countPrev.getOrDefault(changedSum / 2, 0));
                }
                count.put(preSum[i], count.getOrDefault(preSum[i], 0) - 1);
                countPrev.put(preSum[i], countPrev.getOrDefault(preSum[i], 0) + 1);
            }
            return res;
        }
    }


    //https://leetcode.com/problems/maximum-number-of-ways-to-partition-an-array/discuss/1499365/C++-Frequency-Map-O(N)/1188982
    class Solution {
        public int waysToPartition(int[] nums, int k) {
            int n = nums.length;
            long sum = 0;
            for (int i : nums) {
                sum += i;
            }

            // sum of left part minus sum of right part
            // diff[i] = (nums[0] + ... + nums[i - 1]) - (nums[i] + ... + nums[n - 1])
            // where 1 <= i < n

            // prefix and suffix
            // frequency map of diff[1..i] and diff[(i + 1)..(n - 1)] respectively
            Map<Long, Integer> p = new HashMap<>(), s = new HashMap<>();
            // running sum
            long left = 0, right = 0;
            for (int i = 0; i < n - 1; i++) {
                left += nums[i];
                right = sum - left;
                s.compute(left - right, (key, v) -> v == null ? 1 : v + 1);
            }

            // no replacement
            int ways = s.getOrDefault(0l, 0);

            // if we replace nums[i] with k,
            // then diff[1...i] decrease by d, and diff[(i + 1)...(n - 1)] increase by d
            // where d = k - nums[i]
            // the ways is the number of 0s in this new diff array.
            left = 0;
            for (int i = 0; i < n; i++) {
                left += nums[i];
                right = sum - left;
                long d = k - nums[i];

                // replaces nums[i] with k
                // we don't actually modify the diff arrays

                // diff[1...i] decrease by d, which means we need to find p[d] before the replacement
                // so that after the replacement, these diff elements with value d will become 0
                // similarly, we need to find s[-d]
                ways = Math.max(ways, p.getOrDefault(d, 0) + s.getOrDefault(-d, 0));

                // transfers the frequency from suffix map to prefix map
                s.compute(left - right, (key, v) -> v == null ? 1 : v - 1);
                p.compute(left - right, (key, v) -> v == null ? 1 : v + 1);
            }
            return ways;
        }
    }

    class Solution2 {
        public int waysToPartition(int[] nums, int k) {
            Map<Long, Long> left = new HashMap<>();
            Map<Long, Long> right = new HashMap<>();

            int res = 0, n = nums.length;
            long total = 0, lsum = 0, rsum = 0, diff, count;
            for(int val : nums)
                total += val;

            lsum = total;
            for(int i = n-1; i > 0; i--) {
                rsum += nums[i];
                lsum -= nums[i];
                diff = lsum - rsum;

                count = right.getOrDefault(diff, 0l);
                right.put(diff, count + 1);
            }

            rsum = total;
            lsum = 0;
            res = (int) (long) right.getOrDefault(0l, 0l);
            for(int i = 0; i < n; i++) {
                diff = k - nums[i];
                res = Math.max(res, (int) (long) (left.getOrDefault(diff , 0l) + right.getOrDefault(-diff, 0l)));

                lsum += nums[i];
                rsum -= nums[i];
                diff = lsum - rsum;

                count = left.getOrDefault(diff, 0l);
                left.put(diff, count + 1);
                count = right.getOrDefault(diff, 0l);
                right.put(diff, count - 1);
            }

            return res;
        }
    }
}

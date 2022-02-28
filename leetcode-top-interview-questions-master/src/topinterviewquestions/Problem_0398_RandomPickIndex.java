package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/*
Given an integer array nums with possible duplicates, randomly output the index of a given target number. You can assume that the given target number must exist in the array.

Implement the Solution class:

Solution(int[] nums) Initializes the object with the array nums.
int pick(int target) Picks a random index i from nums where nums[i] == target. If there are multiple valid i's, then each index should have an equal probability of returning.


Example 1:

Input
["Solution", "pick", "pick", "pick"]
[[[1, 2, 3, 3, 3]], [3], [1], [3]]
Output
[null, 4, 0, 2]

Explanation
Solution solution = new Solution([1, 2, 3, 3, 3]);
solution.pick(3); // It should return either index 2, 3, or 4 randomly. Each index should have equal probability of returning.
solution.pick(1); // It should return 0. Since in the array only nums[0] is equal to 1.
solution.pick(3); // It should return either index 2, 3, or 4 randomly. Each index should have equal probability of returning.


Constraints:

1 <= nums.length <= 2 * 104
-231 <= nums[i] <= 231 - 1
target is an integer from nums.
At most 104 calls will be made to pick.
 */
public class Problem_0398_RandomPickIndex {
    //Approach 2: Caching results using a hashmap
    class Solution2 {

        private HashMap<Integer, List<Integer>> map;
        private Random rand;

        public Solution2(int[] nums) {
            this.rand = new Random();
            this.map = new HashMap<Integer, List<Integer>>();
            int l = nums.length;
            for (int i = 0; i < l; ++i) {
                if (!map.containsKey(nums[i])) {
                    map.put(nums[i], new ArrayList<>());
                }
                this.map.get(nums[i]).add(i);
            }
        }

        public int pick(int target) {
            int l = map.get(target).size();
            // pick an index at random
            int randomIndex = map.get(target).get(rand.nextInt(l));
            return randomIndex;
        }
    }


    //Approach 3: Reservoir sampling
    //https://leetcode.com/problems/random-pick-index/solution/
    class Solution3 {

        private int[] nums;
        private Random rand;

        public Solution3(int[] nums) {
            this.nums = nums;
            this.rand = new Random();
        }

        public int pick(int target) {
            int n = this.nums.length;
            int count = 0;
            int idx = 0;
            for (int i = 0; i < n; ++i) {
                // if nums[i] is equal to target, i is a potential candidate
                // which needs to be chosen uniformly at random
                if (this.nums[i] == target) {
                    // increment the count of total candidates
                    // available to be chosen uniformly at random
                    count++;
                    // we pick the current number with probability 1 / count (reservoir sampling)
                    if (rand.nextInt(count) == 0) {
                        idx = i;
                    }
                }
            }
            return idx;
        }
    }
}

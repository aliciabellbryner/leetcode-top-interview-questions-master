package topinterviewquestions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
/*
A frog is crossing a river. The river is divided into some number of units, and at each unit, there may or may not exist a stone. The frog can jump on a stone, but it must not jump into the water.

Given a list of stones' positions (in units) in sorted ascending order, determine if the frog can cross the river by landing on the last stone. Initially, the frog is on the first stone and assumes the first jump must be 1 unit.

If the frog's last jump was k units, its next jump must be either k - 1, k, or k + 1 units. The frog can only jump in the forward direction.



Example 1:

Input: stones = [0,1,3,5,6,8,12,17]
Output: true
Explanation: The frog can jump to the last stone by jumping 1 unit to the 2nd stone, then 2 units to the 3rd stone, then 2 units to the 4th stone, then 3 units to the 6th stone, 4 units to the 7th stone, and 5 units to the 8th stone.
Example 2:

Input: stones = [0,1,2,3,4,8,9,11]
Output: false
Explanation: There is no way to jump to the last stone as the gap between the 5th and 6th stone is too large.


Constraints:

2 <= stones.length <= 2000
0 <= stones[i] <= 231 - 1
stones[0] == 0
stones is sorted in a strictly increasing order.
 */
public class Problem_0403_FrogJump_G {

    //Approach #5 Using Dynamic Programming[Accepted]
    //Time complexity : O(n^2). Two nested loops are there.
    //Space complexity : O(n^2). hashmap size can grow upto n^2
    //In the DP Approach, we make use of a hashmap mapmap which contains key:valuekey:value pairs such that keykey refers to the position at which a stone is present and valuevalue is a set containing the jumpsizejumpsize which can lead to the current stone position. We start by making a hashmap whose keykeys are all the positions at which a stone is present and the valuevalues are all empty except position 0 whose value contains 0. Then, we start traversing the elements(positions) of the given stone array in sequential order. For the currentPositioncurrentPosition, for every possible jumpsizejumpsize in the valuevalue set, we check if currentPosition + newjumpsizecurrentPosition+newjumpsize exists in the mapmap, where newjumpsizenewjumpsize can be either jumpsize-1jumpsize−1, jumpsizejumpsize, jumpsize+1jumpsize+1. If so, we append the corresponding valuevalue set with newjumpsizenewjumpsize. We continue in the same manner. If at the end, the valuevalue set corresponding to the last position is non-empty, we conclude that reaching the end is possible, otherwise, it isn't.
    public class Solution5 {
        public boolean canCross(int[] stones) {
            //key refers to the position at which a stone is present
            // value is a set containing the jumpsize which can lead to the current stone position.
            HashMap<Integer, Set<Integer>> map = new HashMap<>();
            for (int i = 0; i < stones.length; i++) {
                map.put(stones[i], new HashSet<Integer>());
            }
            map.get(0).add(0);
            for (int i = 0; i < stones.length; i++) {
                for (int k : map.get(stones[i])) {
                    for (int step = k - 1; step <= k + 1; step++) {
                        if (step > 0 && map.containsKey(stones[i] + step)) {
                            map.get(stones[i] + step).add(step);
                        }
                    }
                }
            }
            return map.get(stones[stones.length - 1]).size() > 0;
        }
    }

    //Approach #4 Using Memoization with Binary Search [Accepted]
    //Time complexity : O(n^2 log(n)). We traverse the complete dpdp matrix once (O(n^2)). For every entry we take at most n numbers as pivot.
    //Space complexity : O(n^2), dp matrix of size n^2 is used.
    public class Solution4 {
        public boolean canCross(int[] stones) {
            int[][] memo = new int[stones.length][stones.length];
            for (int[] row : memo) {
                Arrays.fill(row, -1);
            }
            return can_Cross(stones, 0, 0, memo) == 1;
        }
        public int can_Cross(int[] stones, int ind, int jumpsize, int[][] memo) {
            if (memo[ind][jumpsize] >= 0) {
                return memo[ind][jumpsize];
            }
            int ind1 = Arrays.binarySearch(stones, ind + 1, stones.length, stones[ind] + jumpsize);
            if (ind1 >= 0 && can_Cross(stones, ind1, jumpsize, memo) == 1) {
                memo[ind][jumpsize] = 1;
                return 1;
            }
            int ind2 = Arrays.binarySearch(stones, ind + 1, stones.length, stones[ind] + jumpsize - 1);
            if (ind2 >= 0 && can_Cross(stones, ind2, jumpsize - 1, memo) == 1) {
                memo[ind][jumpsize - 1] = 1;
                return 1;
            }
            int ind3 = Arrays.binarySearch(stones, ind + 1, stones.length, stones[ind] + jumpsize + 1);
            if (ind3 >= 0 && can_Cross(stones, ind3, jumpsize + 1, memo) == 1) {
                memo[ind][jumpsize + 1] = 1;
                return 1;
            }
            memo[ind][jumpsize] = ((ind == stones.length - 1) ? 1 : 0);
            return memo[ind][jumpsize];
        }
    }



}

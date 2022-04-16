package topinterviewquestions;

import java.util.HashMap;
import java.util.Map;

public class Problem_0532_KdiffPairsinanArray_G {
    //https://leetcode.com/problems/k-diff-pairs-in-an-array/discuss/100098/Java-O(n)-solution-one-Hashmap-easy-to-understand/146186
    class Solution {
        public int findPairs(int[] nums, int k) {
            if (k < 0) {
                return 0;
            }
            Map<Integer, Integer> map = new HashMap<>();
            int result = 0;
            for (int i : nums) {
                if (map.containsKey(i)) {
                    if (k == 0 && map.get(i) == 1) {
                        result++;
                    }
                    map.put(i, map.get(i) + 1);
                } else {
                    if (map.containsKey(i - k)) {
                        result++;
                    }
                    if (map.containsKey(i + k)) {
                        result++;
                    }
                    map.put(i, 1);
                }
            }
            return result;
        }
    }
}

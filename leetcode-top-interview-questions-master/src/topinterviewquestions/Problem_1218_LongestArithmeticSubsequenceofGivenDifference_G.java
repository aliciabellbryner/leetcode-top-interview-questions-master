package topinterviewquestions;

import java.util.HashMap;
import java.util.Map;

public class Problem_1218_LongestArithmeticSubsequenceofGivenDifference_G {
    //https://leetcode.com/problems/longest-arithmetic-subsequence-of-given-difference/discuss/398196/C%2B%2B-O(n)-DP-using-Hashmap
    //At each integer i in the array we can consider it as the end of AP(Arithmetic Progression/Sequence)
    // and check the length of that AP which will be the (length of AP that ends with i-difference) + 1.
    // We can thus use a hashmap to store this while traversing the array.
    //Space: O(n).
    //Time: O(n).
    public int longestSubsequence(int[] arr, int difference) {
        int res = 1;
        Map<Integer, Integer> map = new HashMap<>();
        for(int i=0; i<arr.length; i++) {
            if(!map.containsKey(arr[i]-difference)) {
                map.put(arr[i], 1);
            } else {
                map.put(arr[i], map.get(arr[i]-difference)+1);
            }
            res = Math.max(res, map.get(arr[i]));
        }
        return res;
    }
}

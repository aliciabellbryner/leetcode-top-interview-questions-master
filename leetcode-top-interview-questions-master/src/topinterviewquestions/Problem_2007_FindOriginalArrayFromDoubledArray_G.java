package topinterviewquestions;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Problem_2007_FindOriginalArrayFromDoubledArray_G {
    //https://leetcode.com/problems/find-original-array-from-doubled-array/discuss/1470959/JavaC%2B%2BPython-Match-from-the-Smallest-or-Biggest-100
    //time O(NlogK), The time complexity should technically be O(NLogN) because have to insert
    // N elements into the BST of size K (which will be N in the worst case).
    // K is used because the author wants to signify that K may be smaller than N in many cases
    // (i.e. any case where the array isn't full of unique elements).
    public int[] findOriginalArray(int[] A) {
        int N = A.length;
        if (N % 2 == 1) {
            return new int[0];
        }
        int[] res = new int[N / 2];
        Map<Integer, Integer> count = new TreeMap<>();
        //using treemap as we wanna get keySet()
        // return value in sorted from smallest to largest
        for (int a : A)
            count.put(a, count.getOrDefault(a, 0) + 1);
        int i = 0;
        for (int x : count.keySet()) {
            if (count.get(x) > count.getOrDefault(x + x, 0))
                return new int[0];
            for (int j = 0; j < count.get(x); ++j) {
                res[i++] = x;
                count.put(x + x, count.get(x + x) - 1);
            }
        }
        return res;
    }

}

package topinterviewquestions;

import java.util.Map;
import java.util.TreeMap;

public class Problem_1094_CarPooling_G {
    //https://leetcode.com/problems/car-pooling/discuss/317610/JavaC%2B%2BPython-Meeting-Rooms-III
    //Explanation
    //Save all time points and the change on current capacity
    //Sort all the changes on the key of time points.
    //Track the current capacity and return false if negative
    //Complexity
    //Time O(NlogN)
    //Space O(N)
    public boolean carPooling(int[][] trips, int capacity) {
        Map<Integer, Integer> m = new TreeMap<>();
        for (int[] t : trips) {
            m.put(t[1], m.getOrDefault(t[1], 0) + t[0]);
            m.put(t[2], m.getOrDefault(t[2], 0) - t[0]);
        }
        for (int v : m.values()) {
            capacity -= v;
            if (capacity < 0) {
                return false;
            }
        }
        return true;
    }
}

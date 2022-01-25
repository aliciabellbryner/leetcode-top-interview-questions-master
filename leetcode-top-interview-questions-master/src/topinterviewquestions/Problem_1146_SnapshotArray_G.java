package topinterviewquestions;

import java.util.TreeMap;

public class Problem_1146_SnapshotArray_G {
    //https://leetcode.com/problems/snapshot-array/discuss/350562/JavaPython-Binary-Search
    //Time O(logS)
    //Space O(S)
    //where S is the number of set called.
    //
    //SnapshotArray(int length) is O(N) time
    //set(int index, int val) is O(logSnap) in Java//as treemap is all sorted, you have to find the right place to put value
    //snap() is O(1)
    //get(int index, int snap_id) is O(logSnap)
    class SnapshotArray {
        TreeMap<Integer, Integer>[] A;//TreeMap stores keys in sorted order.
        // So you can do a binary search and get the floorKey in O(lgN)
        int snap_id = 0;
        public SnapshotArray(int length) {
            A = new TreeMap[length];
            for (int i = 0; i < length; i++) {
                A[i] = new TreeMap<Integer, Integer>();
                A[i].put(0, 0);// Initially, each element equals 0.
            }
        }

        public void set(int index, int val) {
            A[index].put(snap_id, val);
        }

        public int snap() {
            return snap_id++;
        }

        public int get(int index, int snap_id) {//O(lgN)
            return A[index].floorEntry(snap_id).getValue();
        }
    }
}

package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

public class Problem_0658_FindKClosestElements_G {
    //https://leetcode.com/problems/find-k-closest-elements/discuss/202785/Very-simple-Java-O(n)-solution-using-two-pointers/285459
    //
    public List<Integer> findClosestElements1(int[] arr, int k, int x) {
        int l = 0, r = arr.length - 1;
        while (r - l >= k) {
            if (arr[r] - x >= x - arr[l]) {
                r--;
            } else {
                l++;
            }
        }
        List<Integer> res = new ArrayList<>();
        for (int i = l; i <= r; i++) res.add(arr[i]);
        return res;
    }
}

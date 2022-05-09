package topinterviewquestions;

public class Problem_1855_MaximumDistanceBetweenaPairofValues_G {
    //Since our arrays are sorted, we can advance i while n1[i] is bigger than n2[j], and increment j otherwise.
    //https://leetcode.com/problems/maximum-distance-between-a-pair-of-values/discuss/1198709/O(n)-2-pointers
    public int maxDistance(int[] n1, int[] n2) {
        int i = 0, j = 0, res = 0;
        while (i < n1.length && j < n2.length)
            if (n1[i] > n2[j])
                ++i;
            else
                res = Math.max(res, j++ - i);
        return res;
    }
}

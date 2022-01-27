package topinterviewquestions;

public class Problem_0278_FirstBadVersion_G {
    //https://leetcode.com/problems/first-bad-version/solution/
    public int firstBadVersion(int n) {
        int l = 1;
        int r = n;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (isBadVersion(mid)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }
    //don't submit the isBadVersion method
    private boolean isBadVersion(int i) {
        return true;
    }
}

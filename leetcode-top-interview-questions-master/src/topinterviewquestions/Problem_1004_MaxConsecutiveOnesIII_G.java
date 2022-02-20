package topinterviewquestions;

public class Problem_1004_MaxConsecutiveOnesIII_G {
    //https://leetcode.com/problems/max-consecutive-ones-iii/discuss/247564/JavaC%2B%2BPython-Sliding-Window
    //Intuition
    //Translation:
    //Find the longest subarray with at most K zeros.
    //Explanation
    //For each A[j], try to find the longest subarray.
    //If A[i] ~ A[j] has zeros <= K, we continue to increment j.
    //If A[i] ~ A[j] has zeros > K, we increment i (as well as j).
    public int longestOnes(int[] A, int K) {
        int left = 0, right;
        for (right = 0; right < A.length; ++right) {
            if (A[right] == 0) {
                K--;
            }
            if (K < 0 && A[left++] == 0) {
                K++;
            }
        }
        return right - left;
    }

}

package topinterviewquestions;

public class Problem_0461_HammingDistance_G {
    //https://leetcode.com/problems/hamming-distance/discuss/94693/Java-3-Line-Solution
    public int hammingDistance(int x, int y) {
        int xor = x ^ y, count = 0;
        for (int i=0;i<32;i++) count += (xor >> i) & 1;
        return count;
    }
}

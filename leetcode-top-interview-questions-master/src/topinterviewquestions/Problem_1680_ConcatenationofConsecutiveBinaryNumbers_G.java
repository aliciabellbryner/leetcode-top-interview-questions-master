package topinterviewquestions;

public class Problem_1680_ConcatenationofConsecutiveBinaryNumbers_G {
    //https://leetcode.com/problems/concatenation-of-consecutive-binary-numbers/discuss/961446/Detailed-Thought-Process-with-Steps-Example-or-O(n)-Time-or-Java-8-1-Liner
    class Solution {
        public int concatenatedBinary(int n) {
            int mod = (int)(1e9 + 7);
            long res = 0;
            for (int i = 1; i <= n; i++) {
                int bitLen = (int)(Math.log(i) / Math.log(2)) + 1;//相当于是i对应的需要几位数来表示
                res = ((res << bitLen) + i) % mod;
            }
            return (int)res;
        }
    }
}

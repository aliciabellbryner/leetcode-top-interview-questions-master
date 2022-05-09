package topinterviewquestions;

public class Problem_1987_NumberofUniqueGoodSubsequences_G {

    //https://leetcode.com/problems/number-of-unique-good-subsequences/discuss/1432051/DP-O(n)-or-O(1)
    class Solution {
        /*
            pre1 : previous 1's count(only one step) before 'previous' 0
                   1 -> 11 -> 111 -> 1111  : we are adding same count to the result
            cnt1 : sum between two 0
            pre0 : count(only one step) in 'previous' 0

            1   [1] 1               pre1 = 1, pre0 = 0, cnt1 = 1, ans = 1
            0   [10]  1             pre1 = 1, pre0 = 1, cnt1 = 0, ans = 2
            1   [11, 101] 2         pre1 = 1, pre0 = 1, cnt1 = 2, ans = 4
            1   [111, 1011] 2       pre1 = 1, pre0 = 1, cnt1 = 4, ans = 6
            0   [100, 110, 1010, 1110, 10110] 5  pre1 = 2, pre0 = 5, cnt1 = 5, ans = 11
            1   [1111, 10111, 1001, 1101, 10101, 11101, 101101] 7  pre1 = 2, pre0 = 5, cnt1 = 7, ans = 18
        */
        public int numberOfUniqueGoodSubsequences(String binary) {
            long pre1 = 0, pre0 = 0, cnt1 = 0, hasZero = 0, mod = 1000_000_007;
            long ans = 0;
            for(int i = 0; i < binary.length(); i++) {
                if(binary.charAt(i) == '0') {
                    hasZero = 1;
                    pre1 = pre1 + pre0;
                    pre0 = cnt1 + pre0;
                    ans += pre0;
                    cnt1 = 0;
                } else {
                    if(pre1 == 0) {
                        pre1 = 1;
                    }
                    ans += pre1 + pre0;  //pre1 = 1
                    cnt1 += pre1 + pre0; //cnt1 = 1
                }
                ans %= mod;
                cnt1 %= mod;
                pre1 %= mod;
                pre0 %= mod;
            }
            return (int)(ans + hasZero);
        }
    }

    //https://leetcode.com/problems/number-of-unique-good-subsequences/discuss/1431819/JavaC%2B%2BPython-DP-4-lines-O(N)-Time-O(1)-Space
    public int numberOfUniqueGoodSubsequences(String binary) {
        int mod = (int)1e9 + 7, ends0 = 0, ends1 = 0, has0 = 0;
        for (int i = 0; i < binary.length(); ++i) {
            if (binary.charAt(i) == '1') {
                ends1 = (ends0 + ends1 + 1) % mod;
            } else {
                ends0 = (ends0 + ends1) % mod;
                has0 = 1;
            }
        }
        return (ends0 + ends1 + has0) % mod;
    }
}

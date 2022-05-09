package topinterviewquestions;

public class Problem_1049_LastStoneWeightII_G {

    //https://leetcode.com/problems/last-stone-weight-ii/discuss/294888/JavaC%2B%2BPython-Easy-Knapsacks-DP
    public int lastStoneWeightII(int[] A) {
        boolean[] dp = new boolean[1501];
        dp[0] = true;
        int sumA = 0;
        for (int a : A) {
            sumA += a;
            for (int i = Math.min(1500, sumA); i >= a; --i)//The dp[i] present if the sum of one group can be i.
                //So we can only record the smaller one for less space cost, which less than half of the upper bound of total sum 3000.
                dp[i] |= dp[i - a];
        }
        for (int i = sumA / 2; i >= 0; --i)
            if (dp[i]) return sumA - i - i;
        return 0;
    }


    //https://leetcode-cn.com/problems/last-stone-weight-ii/solution/zui-hou-yi-kuai-shi-tou-de-zhong-liang-i-95p9/
    class Solution {
        public int lastStoneWeightII(int[] stones) {
            int sum = 0;
            for (int weight : stones) {
                sum += weight;
            }
            int n = stones.length, m = sum / 2;
            boolean[][] dp = new boolean[n + 1][m + 1];
            dp[0][0] = true;
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j <= m; ++j) {
                    if (j < stones[i]) {
                        dp[i + 1][j] = dp[i][j];
                    } else {
                        dp[i + 1][j] = dp[i][j] || dp[i][j - stones[i]];
                    }
                }
            }
            for (int j = m;; --j) {
                if (dp[n][j]) {
                    return sum - 2 * j;
                }
            }
        }
    }


    class Solution2 {
        public int lastStoneWeightII(int[] stones) {
            int sum = 0;
            for (int weight : stones) {
                sum += weight;
            }
            int m = sum / 2;
            boolean[] dp = new boolean[m + 1];
            dp[0] = true;
            for (int weight : stones) {
                for (int j = m; j >= weight; --j) {
                    dp[j] = dp[j] || dp[j - weight];
                }
            }
            for (int j = m;; --j) {
                if (dp[j]) {
                    return sum - 2 * j;
                }
            }
        }
    }


}

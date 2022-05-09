package topinterviewquestions;

public class Problem_1230_TossStrangeCoins_G {
    //https://leetcode.ca/2019-04-13-1230-Toss-Strange-Coins/
    //Use dynamic programming. Let length be the length of array prob, and it can be seen that length equals the number of coins.
    //
    //Create a 2D array dp with length + 1 rows and target + 1 columns. The element at dp[i][j] stands for the probability that after the first i coins are tossed, there are j coins facing heads.
    //
    //Obviously, if no coin is tossed, then definitely no coin faces heads, which has a probability 1, so dp[0][0] = 1. Each time a coin is tossed, the number of coins facing heads either remains the same or increase by 1, depending on the current coin’s probability of facing heads. So the probability can be calculated. For the case j equals 0, dp[i][0] only depends on prob[i - 1] and dp[i - 1][0]. For other cases, dp[i][j] depends on prob[i - 1], dp[i - 1][j - 1] and dp[i - 1][j].
    //
    //Finally, return dp[length][target] for the result.
    class Solution {
        public double probabilityOfHeads(double[] prob, int target) {
            int length = prob.length;
            double[][] dp = new double[length + 1][target + 1];
            dp[0][0] = 1;
            for (int i = 1; i <= length; i++) {
                double curProb = prob[i - 1];
                dp[i][0] = dp[i - 1][0] * (1 - curProb);
                for (int j = 1; j <= target; j++)
                    dp[i][j] = dp[i - 1][j - 1] * curProb + dp[i - 1][j] * (1 - curProb);
            }
            return dp[length][target];
        }
    }
}

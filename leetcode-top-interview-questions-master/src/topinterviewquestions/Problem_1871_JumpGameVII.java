package topinterviewquestions;

import java.util.LinkedList;
import java.util.Queue;

public class Problem_1871_JumpGameVII {
    //https://leetcode.com/problems/jump-game-vii/discuss/1236272/JAVA-BFS-Detailed-Analysis-O(n)-BFS-Solution
    class Solution {
        public boolean canReach(String s, int minJump, int maxJump) {
            if(s.charAt(s.length() - 1) != '0')
                return false;

            Queue<Integer> queue = new LinkedList<>();
            queue.add(0);

            // This variable tells us till which index we have processed
            int maxReach = 0;

            while(!queue.isEmpty()){
                int idx = queue.remove();
                // If we reached the last index
                if(idx == s.length() - 1)
                    return true;

                // start the loop from max of [current maximum (idx + minJump), maximum processed index (maxReach)]
                for(int j = Math.max(idx + minJump, maxReach); j <= Math.min(idx + maxJump, s.length() - 1); j++){
                    if(s.charAt(j) == '0')
                        queue.add(j);
                }

                // since we have processed till idx + maxJump so update maxReach to next index
                maxReach = Math.min(idx + maxJump + 1, s.length() - 1);
            }

            return false;
        }
    }

    //https://leetcode.com/problems/jump-game-vii/discuss/1224804/JavaC%2B%2BPython-One-Pass-DP
    //It's a bottom-up DP implementation. The boolean value represents whether this position is reachable from start. So the first step is to generate the table. Here the table was pre-labeled True or False, thus '1's are already labeled False.
    //To determine the state of dp[i], one need to check the states in window dp[i-maxJ : i-minJ], because any one of them can reach i if it's labeled True.
    //Then you need to check if there is a True in this window. Notice that this is a sliding window problem, so you don't need to calculate it everytime. You only need to remove the effect from dp[i-maxJ-1] and add the dp[i-minJ]. This is done by these two lines of code pre += dp[i - minJ] and pre -= dp[i - maxJ - 1]
    //The if statements if i >= minJ: and if i > maxJ: are dealing with the initial boundary.
    //The brilliance of this algorithm is combining the sliding window to DP, hope you enjoy it.
    //https://leetcode.com/problems/jump-game-vii/discuss/1224804/JavaC%2B%2BPython-One-Pass-DP
    public boolean canReach(String s, int minJ, int maxJ) {
        int n = s.length(), pre = 0;
        boolean[] dp = new boolean[n];
        dp[0] = true;
        for (int i = 1; i < n; ++i) {
            if (i >= minJ && dp[i - minJ])
                pre++;
            if (i > maxJ && dp[i - maxJ - 1])
                pre--;
            dp[i] = pre > 0 && s.charAt(i) == '0';
        }
        return dp[n - 1];
    }

}

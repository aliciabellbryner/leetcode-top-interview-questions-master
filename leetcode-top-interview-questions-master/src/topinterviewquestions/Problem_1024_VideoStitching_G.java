package topinterviewquestions;

import java.util.Arrays;

public class Problem_1024_VideoStitching_G {

    //本题要求从一系列视频子区间中选出尽可能少的一部分，使得这部分视频子区间能够重新剪出一个完整的视频。我们可以这样理解：给定区间 [0,\textit{time})[0,time) 的一系列子区间（可能重叠），要求从中选出尽可能少的子区间，使得这些子区间能够完全覆盖区间 [0,\textit{time})[0,time)。
    //
    //为下文表述方便，我们用 [a,b)[a,b) 来代表每一个子区间，第 ii 个子区间表示为 [a_i,b_i)[a

    //方法一：动态规划
    //思路及解法
    //
    //比较容易想到的方法是动态规划，我们令 \textit{dp}[i]dp[i] 表示将区间 [0,i)[0,i) 覆盖所需的最少子区间的数量。由于我们希望子区间的数目尽可能少，因此可以将所有 \textit{dp}[i]dp[i] 的初始值设为一个大整数，并将 \textit{dp}[0]dp[0]（即空区间）的初始值设为 00。
    //
    //我们可以枚举所有的子区间来依次计算出所有的 \textit{dp}dp 值。我们首先枚举 ii，同时对于任意一个子区间 [a_j,b_j)[a
    // )，若其满足 a_j < i \leq b_ja
    // ，那么它就可以覆盖区间 [0, i)[0,i) 的后半部分，而前半部分则可以用 \textit{dp}[a_j]dp[a
    //j
    //​
    // ] 对应的最优方法进行覆盖，因此我们可以用 dp[a_j] + 1dp[a
    //j
    //​
    // ]+1 来更新 \textit{dp}[i]dp[i]。状态转移方程如下：
    //
    //\textit{dp}[i] = \min \{ \textit{dp}[a_j] \} + 1 \quad (a_j < i \leq b_j)
    //dp[i]=min{dp[a
    //最终的答案即为 dp[time]。
    //
    //作者：LeetCode-Solution
    //链接：https://leetcode-cn.com/problems/video-stitching/solution/shi-pin-pin-jie-by-leetcode-solution/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution {
        public int videoStitching(int[][] clips, int time) {
            int[] dp = new int[time + 1];//dp[i] 表示将区间 [0,i)[0,i) 覆盖所需的最少子区间的数量
            Arrays.fill(dp, Integer.MAX_VALUE - 1);
            dp[0] = 0;
            for (int i = 1; i <= time; i++) {
                for (int[] clip : clips) {
                    if (clip[0] < i && i <= clip[1]) {
                        dp[i] = Math.min(dp[i], dp[clip[0]] + 1);
                    }
                }
            }
            return dp[time] == Integer.MAX_VALUE - 1 ? -1 : dp[time];
        }
    }



    //https://leetcode.com/problems/video-stitching/discuss/270036/JavaC%2B%2BPython-Greedy-Solution-O(1)-Space
    public int videoStitching(int[][] clips, int T) {
        int res = 0;
        Arrays.sort(clips, (a, b) ->  a[0] - b[0]);
        for (int i = 0, start = 0, end = 0; start < T; start = end, ++res) {
            for (; i < clips.length && clips[i][0] <= start; ++i)
                end = Math.max(end, clips[i][1]);
            if (start == end) return -1;
        }
        return res;
    }
}

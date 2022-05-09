package topinterviewquestions;

import java.util.Arrays;

public class Problem_1406_StoneGameIII_G {
    //方法一：动态规划
    //对于这种两个玩家、分先后手、博弈类型的题目，我们一般可以使用动态规划来解决。
    //
    //由于玩家只能拿走前面的石子，因此我们考虑使用状态 f[i]f[i] 表示还剩下第 i, i+1, \cdots, n-1i,i+1,⋯,n−1 堆石子时，当前玩家（也就是当前准备拿石子的那一名玩家）的某一个状态。这个「某一个状态」具体是什么状态，我们暂且不表，这里带着大家一步一步来分析这个状态。
    //
    //根据题目描述，当前玩家有三种策略可以选择，即取走前 11、22 或 33 堆石子，那么留给 下一位玩家（也就是下一个准备拿石子的那一名玩家） 的状态为 f[i+1]f[i+1]、f[i+2]f[i+2] 或 f[i+3]f[i+3]。设想一下，假如你是当前玩家，你希望 f[i]f[i] 表示什么，才可以帮助你选择自己的 最优策略 呢？
    //
    //作者：LeetCode-Solution
    //链接：https://leetcode-cn.com/problems/stone-game-iii/solution/shi-zi-you-xi-iii-by-leetcode-solution/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution {
        public String stoneGameIII(int[] stoneValue) {
            int n = stoneValue.length;
            int[] f = new int[n + 1];
            Arrays.fill(f, Integer.MIN_VALUE);
            // 边界情况，当没有石子时，分数为 0
            f[n] = 0;
            for (int i = n - 1; i >= 0; --i) {
                int pre = 0;
                for (int j = i + 1; j <= i + 3 && j <= n; ++j) {
                    pre += stoneValue[j - 1];
                    f[i] = Math.max(f[i], pre - f[j]);
                }
            }
            if (f[0] == 0) {
                return "Tie";
            } else {
                return f[0] > 0 ? "Alice" : "Bob";
            }
        }
    }

}

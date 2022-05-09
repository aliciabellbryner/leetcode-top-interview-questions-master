package topinterviewquestions;

import java.util.HashSet;
import java.util.Set;

public class Problem_0983_MinimumCostForTickets_G {
    //方法一：记忆化搜索（日期变量型）
    //思路和算法
    //
    //我们用 \textit{dp}(i)dp(i) 来表示从第 ii 天开始到一年的结束，我们需要花的钱。考虑到一张通行证可以让我们在「接下来」的若干天进行旅行，所以我们「从后往前」倒着进行动态规划。
    //
    //对于一年中的任意一天：
    //
    //如果这一天不是必须出行的日期，那我们可以贪心地选择不买。这是因为如果今天不用出行，那么也不必购买通行证，并且通行证越晚买越好。所以有 \textit{dp}(i) = \textit{dp}(i + 1)dp(i)=dp(i+1)；
    //
    //如果这一天是必须出行的日期，我们可以选择买 11，77 或 3030 天的通行证。若我们购买了 jj 天的通行证，那么接下来的 j - 1j−1 天，我们都不再需要购买通行证，只需要考虑第 i + ji+j 天及以后即可。因此，我们有
    //
    //\textit{dp}(i) = \min\{\textit{cost}(j) + \textit{dp}(i + j)\}, \quad j \in \{1, 7, 30\}
    //dp(i)=min{cost(j)+dp(i+j)},j∈{1,7,30}
    //
    //其中 \textit{cost}(j)cost(j) 表示 jj 天通行证的价格。为什么我们只需要考虑第 i+ji+j 天及以后呢？这里和第一条的贪心思路是一样的，如果我们需要购买通行证，那么一定越晚买越好，在握着一张有效的通行证的时候购买其它的通行证显然是不划算的。
    //
    //由于我们是倒着进行动态规划的，因此我们可以使用记忆化搜索，减少代码的编写难度。我们使用一个长度为 366366 的数组（因为天数是 [1, 365][1,365]，而数组的下标是从 00 开始的）存储所有的动态规划结果，这样所有的 \textit{dp}(i)dp(i) 只会被计算一次（和普通的动态规划相同），时间复杂度不会增大。
    //
    //最终的答案记为 \textit{dp}(1)dp(1)。
    //
    //作者：LeetCode-Solution
    //链接：https://leetcode-cn.com/problems/minimum-cost-for-tickets/solution/zui-di-piao-jie-by-leetcode-solution/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution {
        int[] costs;
        Integer[] memo;
        Set<Integer> dayset;

        public int mincostTickets(int[] days, int[] costs) {
            this.costs = costs;
            memo = new Integer[366];
            dayset = new HashSet();
            for (int d: days) {
                dayset.add(d);
            }
            return dp(1);
        }

        public int dp(int i) {
            if (i > 365) {
                return 0;
            }
            if (memo[i] != null) {
                return memo[i];
            }
            if (dayset.contains(i)) {
                memo[i] = Math.min(Math.min(dp(i + 1) + costs[0], dp(i + 7) + costs[1]), dp(i + 30) + costs[2]);
            } else {
                memo[i] = dp(i + 1);
            }
            return memo[i];
        }
    }

}

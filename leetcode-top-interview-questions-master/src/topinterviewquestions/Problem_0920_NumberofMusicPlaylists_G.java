package topinterviewquestions;

import java.util.Arrays;

public class Problem_0920_NumberofMusicPlaylists_G {
    //方法 1：动态规划
    //想法
    //
    //令 dp[i][j] 为播放列表长度为 i 包含恰好 j 首不同歌曲的数量。我们需要计算 dp[L][N]，看上去可以通过 dp 来解决。
    //
    //算法
    //
    //考虑 dp[i][j]。最后一首歌，我们可以播放没有播放过的歌也可以是播放过的。如果未播放过的，那么就是 dp[i-1][j-1] * (N-j) 种选择方法。如果不是，那么就是选择之前的一首歌，dp[i-1][j] * max(j-K, 0)（j 首歌，最近的 K 首不可以播放）。
    //
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/number-of-music-playlists/solution/bo-fang-lie-biao-de-shu-liang-by-leetcode/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution {
        public int numMusicPlaylists(int N, int L, int K) {
            int MOD = 1_000_000_007;

            long[][] dp = new long[L+1][N+1];
            dp[0][0] = 1;
            for (int i = 1; i <= L; ++i)
                for (int j = 1; j <= N; ++j) {
                    dp[i][j] += dp[i-1][j-1] * (N-j+1);
                    dp[i][j] += dp[i-1][j] * Math.max(j-K, 0);
                    dp[i][j] %= MOD;
                }

            return (int) dp[L][N];
        }
    }


    //方法 2：分类 + 动态规划
    //（注意：这个方法相当具有挑战性，但是在模拟这种列表时是一个常见的结论）
    //
    //想法
    //
    //由于我们只关心播放次数至少一次的歌，我们记录每首歌第一次播放的时刻 x = (x_1, x_2, \cdots)x=(x
    //1
    //​
    // ,x
    //2
    //​
    // ,⋯)。例如，我们有 5 首歌 abcde，播放列表为 abacabdcbaeacbd，那么 x = (1, 2, 4, 7, 11)x=(1,2,4,7,11) 就是第一首歌出现的时刻。方便起见，我们让 x_{N+1} = L+1x
    //N+1
    //​
    // =L+1。我们的策略就是计算满足 xx 的播放列表个数 \#_x#
    //x
    //​
    // ，最后结果是 \sum \#_x∑#
    //x
    //​
    // 。
    //
    //直接计算，
    //
    //\#_x = N * (N-1) * \cdots * (N-K+1) 1^{x_{K+1} - x_K - 1} * (N-K+2) 2^{x_{K+2} - x_{K+1}} * \cdots
    //#
    //x
    //​
    // =N∗(N−1)∗⋯∗(N−K+1)1
    //x

    //\Rightarrow \#_x = N! \prod_{j=1}^{N-K+1} j^{x_{K+j} - x_{K+j-1} - 1}

    //令 \delta_i = x_{K+i} - x_{K+i-1} - 1δ
    //i

    // −1，所以 \sum \delta_i = L-N∑δ
    //i
    //​
    // =L−N。所以最后结果是（S = L-N, P = N-K+1S=L−N,P=N−K+1）：
    //
    //N! \Big(\sum\limits_{\delta : \sum\limits_{0 \leq i \leq P} \delta_i = S} \prod\limits_{j=1}^P j^{\delta_j} \Big)

    //方便起见,将这个结果记录为 \langle S, P\rangle⟨S,P⟩。
    //
    //算法
    //
    //我们可以通过数学方法迭代计算 \langle S, P\rangle⟨S,P⟩ 的值，通过提出因子 P^{\delta_P}P

    //\langle S, P\rangle = \sum_{\delta_P = 0}^S P^{\delta_P} \sum_{\sum\limits_{0\leq i < P} \delta_i = S - \delta_P} \prod\limits_{j=1}^{P-1} j^{\delta_j}
    //⟨S,P⟩=

    //\Rightarrow \langle S, P\rangle = \sum_{\delta_P = 0}^S P^{\delta_P} \langle S - \delta_P, P-1\rangle
    //⇒⟨S,P⟩=

    //所以可以写成代数形式：
    //
    //\langle S, P \rangle = P \langle S-1, P-1 \rangle + \langle S, P-1 \rangle
    //⟨S,P⟩=P⟨S−1,P−1⟩+⟨S,P−1⟩
    //
    //通过这个迭代，我们可以通过类似方法 1 使用动态规划算法。最后的结果是 N! \langle L-N, N-K+1 \rangleN!⟨L−N,N−K+1⟩。
    //
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/number-of-music-playlists/solution/bo-fang-lie-biao-de-shu-liang-by-leetcode/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution2 {
        public int numMusicPlaylists(int N, int L, int K) {
            int MOD = 1_000_000_007;

            // dp[S] at time P = <S, P> as discussed in article
            long[] dp = new long[L-N+1];
            Arrays.fill(dp, 1);
            for (int p = 2; p <= N-K; ++p)
                for (int i = 1; i <= L-N; ++i) {
                    dp[i] += dp[i-1] * p;
                    dp[i] %= MOD;
                }

            // Multiply by N!
            long ans = dp[L-N];
            for (int k = 2; k <= N; ++k)
                ans = ans * k % MOD;
            return (int) ans;
        }
    }


}

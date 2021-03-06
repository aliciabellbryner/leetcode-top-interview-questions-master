package topinterviewquestions;

import java.util.Arrays;

public class Problem_1397_FindAllGoodStrings_G {

    //https://leetcode.com/problems/find-all-good-strings/discuss/555591/JavaC%2B%2B-Memoization-DFS-and-KMP-with-Picture-Clean-code-~-12ms
    public int findGoodStrings(int n, String s1, String s2, String evil) {
        int[] dp = new int[1 << 17]; // Need total 17 bits, can check getKey() function
        return dfs(0, 0, true, true,
                n, s1.toCharArray(), s2.toCharArray(), evil.toCharArray(), computeLPS(evil.toCharArray()), dp);
    }
    int dfs(int i, int evilMatched, boolean leftBound, boolean rightBound,
            int n, char[] s1, char[] s2, char[] evil, int[] lps, int[] dp) {
        if (evilMatched == evil.length) return 0; // contain `evil` as a substring -> not good string
        if (i == n) return 1; // it's a good string
        int key = getKey(i, evilMatched, leftBound, rightBound);
        if (dp[key] != 0) return dp[key];
        char from = leftBound ? s1[i] : 'a';
        char to = rightBound ? s2[i] : 'z';
        int res = 0;
        for (char c = from; c <= to; c++) {
            int j = evilMatched; // j means the next match between current string (end at char `c`) and `evil` string
            while (j > 0 && evil[j] != c) j = lps[j - 1];
            if (c == evil[j]) j++;
            res += dfs(i + 1, j, leftBound && (c == from), rightBound && (c == to),
                    n, s1, s2, evil, lps, dp);
            res %= 1000000007;
        }
        return dp[key] = res;
    }
    int getKey(int n, int m, boolean b1, boolean b2) {
        // Need 9 bits store n (2^9=512), 6 bits store m (2^6=64), 1 bit store b1, 1 bit store b2
        return (n << 8) | (m << 2) | ((b1 ? 1 : 0) << 1) | (b2 ? 1 : 0);
    }
    int[] computeLPS(char[] str) { // Longest Prefix also Suffix
        int n = str.length;
        int[] lps = new int[n];
        for (int i = 1, j = 0; i < n; i++) {
            while (j > 0 && str[i] != str[j]) j = lps[j - 1];
            if (str[i] == str[j]) lps[i] = ++j;
        }
        return lps;
    }


    //https://leetcode-cn.com/problems/find-all-good-strings/solution/zhao-dao-suo-you-hao-zi-fu-chuan-by-leetcode-solut/
    class Solution {
        int[] fail;
        // ???????????????????????????????????????
        // ??????????????????????????? pos, stats, bound
        int[][][] f;
        // int f[500][50][4];
        // ???????????????????????????????????????
        // ????????????????????? stats ??? d
        int[][] trans;
        static final int MOD = 1000000007;
        String s1, s2, evil;

        public int findGoodStrings(int n, String s1, String s2, String evil) {
            this.s1 = s1;
            this.s2 = s2;
            this.evil = evil;

            int m = evil.length();
            fail = new int[m];
            // ?????? KMP ??????????????????
            // ??????evil????????????????????????????????? fail[] ??????
            for (int i = 1; i < m; ++i) {
                int j = fail[i - 1];
                while (j != 0 && evil.charAt(j) != evil.charAt(i)) {
                    j = fail[j - 1];
                }
                if (evil.charAt(j) == evil.charAt(i)) {
                    fail[i] = j + 1;
                }
            }
            // ?????????????????????????????????????????? -1
            f = new int[n][m][4];
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < m; ++j) {
                    Arrays.fill(f[i][j], -1);
                }
            }
            // ???????????????????????????????????? -1
            trans = new int[m][26];
            for (int i = 0; i < m; ++i) {
                Arrays.fill(trans[i], -1);
            }
            // ???????????? f[0][0][3]
            return dfs(0, 0, 3);
        }

        // ??????????????????????????????????????????
        // ????????? pos, stats ??? bound
        // ????????? f[pos][stats][bound]
        public int dfs(int pos, int stats, int bound) {
            // ?????????????????? evil ?????????
            // ?????????????????????????????????
            // ?????? 0
            if (stats == evil.length()) {
                return 0;
            }
            // ????????????????????????????????????
            // ?????????????????????????????????????????????
            // ?????? 1
            if (pos == s1.length()) {
                return 1;
            }
            // ????????????????????????
            // ???????????????????????????????????????????????????
            if (f[pos][stats][bound] != -1) {
                return f[pos][stats][bound];
            }

            // ????????????????????????
            // ????????????????????????????????? -1
            f[pos][stats][bound] = 0;
            // ????????? pos ???????????????????????????
            char l = ((bound & 1) != 0 ? s1.charAt(pos) : 'a');
            // ????????? pos ???????????????????????????
            char r = ((bound & 2) != 0 ? s2.charAt(pos) : 'z');
            for (char ch = l; ch <= r; ++ch) {
                int nxt_stats = getTrans(stats, ch);
                // ????????????????????????
                // ????????????????????? bound ???????????????
                // ?????????????????????????????????
                int nxt_bound = ((bound & 1) != 0 ? (ch == s1.charAt(pos) ? 1 : 0) : 0) ^ ((bound & 2) != 0 ? (ch == s2.charAt(pos) ? 1 : 0) << 1 : 0);
                f[pos][stats][bound] += dfs(pos + 1, nxt_stats, nxt_bound);
                f[pos][stats][bound] %= MOD;
            }
            return f[pos][stats][bound];
        }

        // ?????????????????? stats ???????????????
        // ????????? stats ??? d
        // ???????????????????????? g_s(stats, d)
        public int getTrans(int stats, char ch) {
            // ?????????????????????????????????????????????????????????
            if (trans[stats][ch - 'a'] != -1) {
                return trans[stats][ch - 'a'];
            }
            // ?????? KMP ??????????????????
            // ??? evil ????????????????????????stats ????????????????????????????????????
            while (stats != 0 && evil.charAt(stats) != ch) {
                stats = fail[stats - 1];
            }
            // ?????????????????????
            return trans[stats][ch - 'a'] = (evil.charAt(stats) == ch ? stats + 1 : 0);
        }
    }
}

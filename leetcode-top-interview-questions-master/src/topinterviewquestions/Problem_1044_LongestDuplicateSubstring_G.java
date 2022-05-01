package topinterviewquestions;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Problem_1044_LongestDuplicateSubstring_G {
    //方法一：二分查找 + Rabin-Karp 字符串编码
    //思路及解法
    //
    //记 ss 的长度为 nn。这个问题可以分为两步：从 n - 1n−1 到 11 由大至小遍历选取长度 LL，判断 ss 中是否有长度为 LL 的重复子串。从大至小遍历的时候，第一次遇到的符合条件的 LL，即为最大的符合条件的 LL，记为 L_1L
    //  的所有子串也是 ss 的重复子串。而对于任意满足 L_2 \gt L_1L
    // ，则均不符合条件。因此，我们可以用二分查找的方法，来查找 L_1L
    //剩下的任务便是如何高效判断 ss 中是否有长度为 LL 的重复子串。我们可以使用 Rabin-Karp 算法对固定长度的字符串进行编码。当两个字符串的编码相同时，则这两个字符串也相同。在 ss 中 {n-L+1}n−L+1 个长度为 LL 的子串中，有两个子串的编码相同时，则说明存在长度为 LL 的重复子串。具体步骤如下：
    //
    //首先，我们需要对 ss 的每个字符进行编码，得到一个数组 arrarr。因为本题中 ss 仅包含小写字母，我们可按照 \texttt{arr[i] = (int)s.charAt(i) - (int)`a'}arr[i] = (int)s.charAt(i) - (int)‘a’，将所有字母编码为 0-250−25 之间的数字。比如字符串 \text{``abcde"}“abcde" 可以编码为数组 [0, 1, 2, 3, 4][0,1,2,3,4]。
    //我们将子串看成一个 2626 进制的数，它对应的 1010 进制数就是它的编码。假设此时我们需要求长度为 33 的子串的编码。那么第一个子串 \text{``abc''}“abc” 的编码就是 h_0 = 0 \times 26^2 + 1 \times 26^1 + 2 \times 26^0 = 28h
    //  为 ss 的第 ii 个字符编码后的数字，aa (a\geq26)(a≥26) 为编码的进制，那么有 h_0 = c_0a^{L-1} + c_1a^{L-2} + ... +c_{L-1}a^1 = \sum_{i=0}^{L-1} c_ia^{L-1-i}h
    //上一步我们只求了第一个子串 \text{``abc''}“abc” 的编码。当我们要求第二个子串 \text{``bcd''}“bcd” 的编码时，也可以按照上一步的方法求：h_1 = 1 \times 26^2 + 2 \times 26^1 + 3 \times 26^0 = 731h
    // =731，但是这样时间复杂度是 O(L)O(L)。我们可以在 h_0h
    //0
    //​
    //  的基础上，更快地求出它的编码：h_1 = (h_0 - 0 \times 26^2) \times 26 + 3 \times 26^0 = 731h
    // =731。更一般的表达式是：h_1 = (h_0 \times a - c_0 \times a^L) + c_{L+1}
    // 。这样，我们只需要在常数时间内就可以根据上一个子串的编码求出下一个子串的编码。我们用一个哈希表 \textit{seen}seen 来存储子串的编码。在求子串的编码时，如果某个子串的编码出现过，则表示存在长度为 LL 的重复子串，否则，我们将当前的编码放入 \textit{seen}seen 中。如果所有编码都不重复，则说明不存在长度为 LL 的重复子串。
    //还有一点需要考虑的是，本题中 a^La
    //L
    //  会非常大。一般的做法是需要对编码进行取模来防止溢出，模一般选取编码的信息量的平方的数量级。而取模则会带来哈希碰撞。本题中为了避免碰撞，我们使用双哈希，即用两套进制和模的组合，来对字符串进行编码。只有两种编码都相同时，我们才认为字符串相同。
    //本题要求返回最长重复子串而不是最长重复子串长度。因此，当存在长度为 LL 的子串时，我们的判断函数可以返回重复子串的起点。而当不存在时，可以返回 -1−1 用做区分。
    //
    //作者：LeetCode-Solution
    //链接：https://leetcode-cn.com/problems/longest-duplicate-substring/solution/zui-chang-zhong-fu-zi-chuan-by-leetcode-0i9rd/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution {
        public String longestDupSubstring(String s) {
            Random random = new Random();
            // 生成两个进制
            int a1 = random.nextInt(75) + 26;
            int a2 = random.nextInt(75) + 26;
            // 生成两个模
            int mod1 = random.nextInt(Integer.MAX_VALUE - 1000000007 + 1) + 1000000007;
            int mod2 = random.nextInt(Integer.MAX_VALUE - 1000000007 + 1) + 1000000007;
            int n = s.length();
            // 先对所有字符进行编码
            int[] arr = new int[n];
            for (int i = 0; i < n; ++i) {
                arr[i] = s.charAt(i) - 'a';
            }
            // 二分查找的范围是[1, n-1]
            int l = 1, r = n - 1;
            int length = 0, start = -1;
            while (l <= r) {
                int m = l + (r - l + 1) / 2;
                int idx = check(arr, m, a1, a2, mod1, mod2);
                if (idx != -1) {
                    // 有重复子串，移动左边界
                    l = m + 1;
                    length = m;
                    start = idx;
                } else {
                    // 无重复子串，移动右边界
                    r = m - 1;
                }
            }
            return start != -1 ? s.substring(start, start + length) : "";
        }

        public int check(int[] arr, int m, int a1, int a2, int mod1, int mod2) {
            int n = arr.length;
            long aL1 = pow(a1, m, mod1);
            long aL2 = pow(a2, m, mod2);
            long h1 = 0, h2 = 0;
            for (int i = 0; i < m; ++i) {
                h1 = (h1 * a1 % mod1 + arr[i]) % mod1;
                h2 = (h2 * a2 % mod2 + arr[i]) % mod2;
                if (h1 < 0) {
                    h1 += mod1;
                }
                if (h2 < 0) {
                    h2 += mod2;
                }
            }
            // 存储一个编码组合是否出现过
            Set<Long> seen = new HashSet<Long>();
            seen.add(h1 * mod2 + h2);
            for (int start = 1; start <= n - m; ++start) {
                h1 = (h1 * a1 % mod1 - arr[start - 1] * aL1 % mod1 + arr[start + m - 1]) % mod1;
                h2 = (h2 * a2 % mod2 - arr[start - 1] * aL2 % mod2 + arr[start + m - 1]) % mod2;
                if (h1 < 0) {
                    h1 += mod1;
                }
                if (h2 < 0) {
                    h2 += mod2;
                }

                long num = h1 * mod2 + h2;
                // 如果重复，则返回重复串的起点
                if (!seen.add(num)) {
                    return start;
                }
            }
            // 没有重复，则返回-1
            return -1;
        }

        public long pow(int a, int m, int mod) {
            long ans = 1;
            long contribute = a;
            while (m > 0) {
                if (m % 2 == 1) {
                    ans = ans * contribute % mod;
                    if (ans < 0) {
                        ans += mod;
                    }
                }
                contribute = contribute * contribute % mod;
                if (contribute < 0) {
                    contribute += mod;
                }
                m /= 2;
            }
            return ans;
        }
    }

}

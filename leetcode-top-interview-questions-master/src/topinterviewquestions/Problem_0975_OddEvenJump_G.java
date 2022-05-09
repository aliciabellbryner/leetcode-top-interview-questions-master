package topinterviewquestions;

import java.util.TreeMap;

public class Problem_0975_OddEvenJump_G {
    //https://leetcode-cn.com/problems/odd-even-jump/solution/qi-ou-tiao-by-leetcode/
    //方法二： 树映射（Tree Map）
    //思路
    //
    //在 方法一 中，原问题简化为：奇数次跳跃时，对于一些索引 i，下一步应该跳到哪里去（如果有的话）。
    //
    //算法
    //
    //我们可以使用 TreeMap，一个维护有序数据的绝佳数据结构。我们将索引 i 映射到 v = A[i] 上。
    //
    //从 i = N-2 到 i = 0 的遍历过程中，对于 v = A[i]， 我们想知道比它略大一点和略小一点的元素是谁。 TreeMap.lowerKey 与 TreeMap.higherKey 函数就是用来做这样一件事情的。
    //
    //了解这一点之后，解法接下来的内容就非常直接了： 我们使用动态规划来维护 odd[i] 和 even[i]：从索引 i 出发奇数次跳跃与偶数次跳跃是否能到达数组末尾。
    //
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/odd-even-jump/solution/qi-ou-tiao-by-leetcode/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution {
        public int oddEvenJumps(int[] A) {
            int N = A.length;
            if (N <= 1) return N;
            boolean[] odd = new boolean[N];//从索引 i 出发奇数次跳跃与偶数次跳跃是否能到达数组末尾。
            boolean[] even = new boolean[N];
            odd[N-1] = even[N-1] = true;

            TreeMap<Integer, Integer> vals = new TreeMap();
            vals.put(A[N-1], N-1);
            for (int i = N-2; i >= 0; --i) {
                int v = A[i];
                if (vals.containsKey(v)) {
                    odd[i] = even[vals.get(v)];
                    even[i] = odd[vals.get(v)];
                } else {
                    Integer lower = vals.lowerKey(v);
                    Integer higher = vals.higherKey(v);

                    if (lower != null)
                        even[i] = odd[vals.get(lower)];
                    if (higher != null) {
                        odd[i] = even[vals.get(higher)];
                    }
                }
                vals.put(v, i);
            }

            int ans = 0;
            for (boolean b: odd)
                if (b) ans++;
            return ans;
        }
    }


}

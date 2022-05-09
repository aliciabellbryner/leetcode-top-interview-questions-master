package topinterviewquestions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Problem_1296_DivideArrayinSetsofKConsecutiveNumbers_G {
    //方法一：贪心
    //题目要求将数组 \textit{nums}nums 中的元素分组使得每组的大小是 kk。假设数组 \textit{nums}nums 的长度是 nn，只有当 n \bmod k = 0nmodk=0 时才可能完成分组，因此如果 n \bmod k \ne 0nmodk
    //
    //​
    // =0 则直接返回 \text{false}false。
    //
    //当 n \bmod k = 0nmodk=0 时，可以将数组 \textit{nums}nums 中的元素分组使得每组的大小是 kk，此时需要判断是否存在一种分组方式使得同一组的元素都是连续的。
    //
    //由于每个元素都必须被分到某个组，因此可以使用贪心的策略。假设尚未分组的元素中，最小的数字是 xx，则如果存在符合要求的分组方式，xx 一定是某个组中的最小数字（否则 xx 不属于任何一个组，不符合每个元素都必须被分到某个组），该组的数字范围是 [x, x + k - 1][x,x+k−1]。在将 xx 到 x + k - 1x+k−1 的 kk 个元素分成一个组之后，继续使用贪心的策略对剩下的元素分组，直到所有的元素分组结束或者无法完成分组。如果在分组过程中发现从最小数字开始的连续 kk 个数字中有不存在的数字，则无法完成分组。
    //
    //首先对数组 \textit{nums}nums 排序，并使用哈希表记录数组 \textit{nums}nums 中每个元素的出现次数，然后遍历数组 \textit{nums}nums，使用基于上述贪心策略的做法判断是否可以完成分组。贪心策略的具体做法如下：
    //
    //将当前元素记为 xx，如果 xx 不在哈希表中则跳过，如果 xx 在哈希表中，则 xx 是某个组中的最小数字（因为数组 \textit{nums}nums 有序，当遍历到 xx 时，xx 一定是所有尚未分组的元素中的最小数字），该组的数字范围是 [x, x + k - 1][x,x+k−1]；
    //
    //如果可以完成分组，则 xx 到 x + k - 1x+k−1 的每个整数在哈希表中记录的出现次数都至少为 11，如果遇到某个整数的出现次数为 00 则无法完成分组，返回 \text{false}false；
    //
    //将 xx 到 x + k - 1x+k−1 的每个整数在哈希表中记录的出现次数减 11，如果出现次数减为 00 则从哈希表中移除该整数；
    //
    //对于其余元素，重复上述操作，直到遍历结束。
    //
    //遍历结束之后，如果没有出现无法完成分组的情况，返回 \text{true}true。
    //
    //作者：LeetCode-Solution
    //链接：https://leetcode-cn.com/problems/divide-array-in-sets-of-k-consecutive-numbers/solution/hua-fen-shu-zu-wei-lian-xu-shu-zi-de-ji-he-by-le-2/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution {
        public boolean isPossibleDivide(int[] nums, int k) {
            int n = nums.length;
            if (n % k != 0) {
                return false;
            }
            Arrays.sort(nums);
            Map<Integer, Integer> cnt = new HashMap<Integer, Integer>();
            for (int x : nums) {
                cnt.put(x, cnt.getOrDefault(x, 0) + 1);
            }
            for (int x : nums) {
                if (!cnt.containsKey(x)) {
                    continue;
                }
                for (int j = 0; j < k; j++) {
                    int num = x + j;
                    if (!cnt.containsKey(num)) {
                        return false;
                    }
                    cnt.put(num, cnt.get(num) - 1);
                    if (cnt.get(num) == 0) {
                        cnt.remove(num);
                    }
                }
            }
            return true;
        }
    }
}

package topinterviewquestions;

import java.util.Deque;
import java.util.LinkedList;

public class Problem_1438_LongestContinuousSubarrayWithAbsoluteDiffLessThanorEqualtoLimit_G {
    //方法二：滑动窗口 + 单调队列
    //思路和解法
    //
    //在方法一中，我们仅需要统计当前窗口内的最大值与最小值，因此我们也可以分别使用两个单调队列解决本题。
    //
    //在实际代码中，我们使用一个单调递增的队列 \textit{queMin}queMin 维护最小值，一个单调递减的队列 \textit{queMax}queMax 维护最大值。这样我们只需要计算两个队列的队首的差值，即可知道当前窗口是否满足条件。
    //
    //作者：LeetCode-Solution
    //链接：https://leetcode-cn.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/solution/jue-dui-chai-bu-chao-guo-xian-zhi-de-zui-5bki/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution {
        public int longestSubarray(int[] nums, int limit) {
            Deque<Integer> queMax = new LinkedList<Integer>();
            Deque<Integer> queMin = new LinkedList<Integer>();
            int n = nums.length;
            int left = 0, right = 0;
            int ret = 0;
            while (right < n) {
                while (!queMax.isEmpty() && queMax.peekLast() < nums[right]) {
                    queMax.pollLast();
                }
                while (!queMin.isEmpty() && queMin.peekLast() > nums[right]) {
                    queMin.pollLast();
                }
                queMax.offerLast(nums[right]);
                queMin.offerLast(nums[right]);
                while (!queMax.isEmpty() && !queMin.isEmpty() && queMax.peekFirst() - queMin.peekFirst() > limit) {
                    if (nums[left] == queMin.peekFirst()) {
                        queMin.pollFirst();
                    }
                    if (nums[left] == queMax.peekFirst()) {
                        queMax.pollFirst();
                    }
                    left++;
                }
                ret = Math.max(ret, right - left + 1);
                right++;
            }
            return ret;
        }
    }

}

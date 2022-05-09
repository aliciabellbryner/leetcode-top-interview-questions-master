package topinterviewquestions;

import java.util.Arrays;

public class Problem_1509_MinimumDifferenceBetweenLargestandSmallestValueinThreeMoves_G {
    //方法二：贪心
    //思路及算法
    //https://leetcode-cn.com/problems/minimum-difference-between-largest-and-smallest-value-in-three-moves/solution/san-ci-cao-zuo-hou-zui-da-zhi-yu-zui-xiao-zhi-de-2/
    //直接维护最大的四个数与最小的四个数即可，我们用两个数组分别记录最大值与最小值，不断更新这两个最值数组。
    class Solution {
        public int minDifference(int[] nums) {
            int n = nums.length;
            if (n <= 4) {
                return 0;
            }

            int[] maxn = new int[4];
            int[] minn = new int[4];
            Arrays.fill(maxn, -1000000000);
            Arrays.fill(minn, 1000000000);
            for (int i = 0; i < n; i++) {
                int add = 0;
                while (add < 4 && maxn[add] > nums[i]) {
                    add++;
                }
                if (add < 4) {
                    for (int j = 3; j > add; j--) {
                        maxn[j] = maxn[j - 1];
                    }
                    maxn[add] = nums[i];
                }
                add = 0;
                while (add < 4 && minn[add] < nums[i]) {
                    add++;
                }
                if (add < 4) {
                    for (int j = 3; j > add; j--) {
                        minn[j] = minn[j - 1];
                    }
                    minn[add] = nums[i];
                }
            }
            int ret = Integer.MAX_VALUE;
            for (int i = 0; i < 4; i++) {
                ret = Math.min(ret, maxn[i] - minn[3 - i]);
            }
            return ret;
        }
    }
}

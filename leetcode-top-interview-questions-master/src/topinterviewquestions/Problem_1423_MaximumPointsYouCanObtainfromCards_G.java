package topinterviewquestions;

import java.util.Arrays;

public class Problem_1423_MaximumPointsYouCanObtainfromCards_G {

    //https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/discuss/597825/Simple-Clean-Intuitive-Explanation-with-Visualization/548495
    class Solution {
        public int maxScore(int[] cardPoints, int k) {
            int n = cardPoints.length, lSum = 0;
            for(int i = 0; i < k; ++i){
                lSum += cardPoints[i];
            }
            int max = lSum;
            for(int rSum = 0, i = 0; i < k; ++i){
                rSum += cardPoints[n-1-i];
                lSum -= cardPoints[k-1-i];
                max = Math.max(max,lSum+rSum);
            }
            return max;
        }
    }


    //方法一：滑动窗口
    //思路
    //
    //记数组cardPoints 的长度为 n，由于只能从开头和末尾拿 kk 张卡牌，所以最后剩下的必然是连续的 n-kn−k 张卡牌。
    //
    //我们可以通过求出剩余卡牌点数之和的最小值，来求出拿走卡牌点数之和的最大值。
    //
    //算法
    //
    //由于剩余卡牌是连续的，使用一个固定长度为 n-kn−k 的滑动窗口对数组 \textit{cardPoints}cardPoints 进行遍历，求出滑动窗口最小值，然后用所有卡牌的点数之和减去该最小值，即得到了拿走卡牌点数之和的最大值。
    //
    //作者：LeetCode-Solution
    //链接：https://leetcode-cn.com/problems/maximum-points-you-can-obtain-from-cards/solution/ke-huo-de-de-zui-da-dian-shu-by-leetcode-7je9/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution2 {
        public int maxScore(int[] cardPoints, int k) {
            int n = cardPoints.length;
            // 滑动窗口大小为 n-k
            int windowSize = n - k;
            // 选前 n-k 个作为初始值
            int sum = 0;
            for (int i = 0; i < windowSize; ++i) {
                sum += cardPoints[i];
            }
            int minSum = sum;
            for (int i = windowSize; i < n; ++i) {
                // 滑动窗口每向右移动一格，增加从右侧进入窗口的元素值，并减少从左侧离开窗口的元素值
                sum += cardPoints[i] - cardPoints[i - windowSize];
                minSum = Math.min(minSum, sum);
            }
            return Arrays.stream(cardPoints).sum() - minSum;
        }
    }
}

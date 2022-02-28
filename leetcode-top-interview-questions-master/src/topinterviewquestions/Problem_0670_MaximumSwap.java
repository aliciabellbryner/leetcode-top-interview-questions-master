package topinterviewquestions;

public class Problem_0670_MaximumSwap {
    //https://leetcode.com/problems/maximum-swap/discuss/107068/Java-simple-solution-O(n)-time
    //Use buckets to record the last position of digit 0 ~ 9 in this num.
    //Time O(N)
    //Loop through the num array from left to right. For each position,
    // we check whether there exists a larger digit in this num (start from 9 to current digit).
    // We also need to make sure the position of this larger digit is behind the current one.
    // If we find it, simply swap these two digits and return the result.
    //思路就是先用bucket来记录0。。。9这些数字出现在num中的最后一个位置
    //然后再从开始到最后遍历，对每个i，从buckets[9]开始找到num[i]的位置，如果找到最大的值在i后面，那就把他们调换一下就是结果
    public int maximumSwap(int num) {
        char[] digits = Integer.toString(num).toCharArray();

        int[] buckets = new int[10];
        for (int i = 0; i < digits.length; i++) {
            buckets[digits[i] - '0'] = i;
        }

        for (int i = 0; i < digits.length; i++) {
            for (int k = 9; k > digits[i] - '0'; k--) {//we check whether there exists a larger digit in this num (start from 9 to current digit).
                if (buckets[k] > i) {
                    char tmp = digits[i];
                    digits[i] = digits[buckets[k]];
                    digits[buckets[k]] = tmp;
                    return Integer.valueOf(new String(digits));
                }
            }
        }

        return num;
    }
}

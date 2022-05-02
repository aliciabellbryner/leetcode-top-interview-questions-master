package topinterviewquestions;

import java.util.Stack;

public class Problem_1673_FindtheMostCompetitiveSubsequence_G {
    //https://leetcode.com/problems/find-the-most-competitive-subsequence/discuss/952786/JavaC%2B%2BPython-One-Pass-Stack-Solution
    //Use a mono incrasing stack.
    public int[] mostCompetitive(int[] nums, int k) {
        Stack<Integer> stack = new Stack<>();
        int[] result = new int[k];
        for (int i = 0; i < nums.length; i++) {
            while (!stack.empty() && nums[i] < nums[stack.peek()] && nums.length - i + stack.size() > k) {
                //nums.length - i + stack.size() > k： nums中还剩nums.length - i， 加上stack中的数，sum已经比k大，所以就直接pop stack中最大的值
                stack.pop();
            }
            if (stack.size() < k) {
                stack.push(i);
            }
        }
        for (int i = k - 1; i >= 0; i--) {
            result[i] = nums[stack.pop()];
        }
        return result;
    }
}

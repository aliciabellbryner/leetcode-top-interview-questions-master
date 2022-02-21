package topinterviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
You are given an integer array cards of length 4. You have four cards, each containing a number in the range [1, 9]. You should arrange the numbers on these cards in a mathematical expression using the operators ['+', '-', '*', '/'] and the parentheses '(' and ')' to get the value 24.

You are restricted with the following rules:

The division operator '/' represents real division, not integer division.
For example, 4 / (1 - 2 / 3) = 4 / (1 / 3) = 12.
Every operation done is between two numbers. In particular, we cannot use '-' as a unary operator.
For example, if cards = [1, 1, 1, 1], the expression "-1 - 1 - 1 - 1" is not allowed.
You cannot concatenate numbers together
For example, if cards = [1, 2, 1, 2], the expression "12 + 12" is not valid.
Return true if you can get such expression that evaluates to 24, and false otherwise.


Example 1:

Input: cards = [4,1,8,7]
Output: true
Explanation: (8-4) * (7-1) = 24
Example 2:

Input: cards = [1,2,1,2]
Output: false


Constraints:

cards.length == 4
1 <= cards[i] <= 9
 */
public class Problem_0679_24Game_G {
    //https://leetcode.com/problems/24-game/discuss/107673/JAVA-Easy-to-understand.-Backtracking./196343
    class Solution {
        public boolean judgePoint24(int[] nums) {
            List<Double> list = new ArrayList<>();
            for (int i : nums) {
                list.add((double) i);
            }
            return dfs(list);
        }

        // 每次dfs都是选取两张牌
        private boolean dfs(List<Double> list) {
            //base case
            if (list.size() == 1) {
                // 如果此时list只剩下了一张牌
                if (Math.abs(list.get(0)- 24.0) < 0.001) {
                    return true;
                }
                return false;
            }

            // 选取两张牌
            for(int i = 0; i < list.size(); i++) {
                for(int j = i + 1; j < list.size(); j++) {
                    // 对于每下一个可能的产生的组合
                    for (double c : compute(list.get(i), list.get(j))) {
                        List<Double> nextRound = new ArrayList<>();
                        // 将他们加入到下一个list循环中去
                        nextRound.add(c);
                        for(int k = 0; k < list.size(); k++) {
                            if(k == j || k == i) {
                                continue;
                            }
                            nextRound.add(list.get(k));
                        }
                        if (dfs(nextRound)) {
                            return true;
                        }
                    }
                }
            }
            return false;

        }
        // 计算下一个可能产生的组合
        private List<Double> compute(double a, double b) {
            List<Double> res = Arrays.asList(a + b,a-b,b-a,a*b,a/b,b/a);
            return res;
        }
    }
}

package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

/*
Given a string num that contains only digits and an integer target, return all possibilities to insert the binary operators '+', '-', and/or '*' between the digits of num so that the resultant expression evaluates to the target value.

Note that operands in the returned expressions should not contain leading zeros.



Example 1:

Input: num = "123", target = 6
Output: ["1*2*3","1+2+3"]
Explanation: Both "1*2*3" and "1+2+3" evaluate to 6.
Example 2:

Input: num = "232", target = 8
Output: ["2*3+2","2+3*2"]
Explanation: Both "2*3+2" and "2+3*2" evaluate to 8.
Example 3:

Input: num = "3456237490", target = 9191
Output: []
Explanation: There are no expressions that can be created from "3456237490" to evaluate to 9191.


Constraints:

1 <= num.length <= 10
num consists of only digits.
-231 <= target <= 231 - 1
 */
public class Problem_0282_ExpressionAddOperators_G {


    //https://leetcode.com/problems/expression-add-operators/discuss/572099/C%2B%2BJavaPython-Backtracking-and-Evaluate-on-the-fly-Clean-and-Concise
    //Approach 2: Backtracking & Evaluate on the fly (Best Solution)
    //
    //We use backtracking to generate all possible expressions by adding +, -, * to between the digits of s string.
    //There is no priority since there are no parentheses ( and ) in our s string, so we can evaluate the expression on the fly to save time.
    //There are total 3 operators:
    //+ operator: newResult = resSoFar + num
    //- operator: newResult = resSoFar - num.
    //* operator: We need to keep the prevNum so that to calculate newResult we need to minus prevNum then plus with prevNum * num. So newResult = resSoFar - prevNum + prevNum * num.
    class Solution {
        List<String> ans = new ArrayList<>();
        String s;
        int target;
        public List<String> addOperators(String s, int target) {
            this.s = s;
            this.target = target;
            backtrack( 0, "", 0, 0);
            return ans;
        }
        void backtrack(int i, String path, long resSoFar, long prevNum) {
            if (i == s.length()) {
                if (resSoFar == target) ans.add(path);
                return;
            }
            for (int j = i; j < s.length(); j++) {
                if (j > i && s.charAt(i) == '0') break; // Skip leading zero number
                long num = Long.parseLong(s.substring(i, j + 1));
                if (i == 0) {
                    backtrack(j + 1, path + num, num, num); // First num, pick it without adding any operator!
                } else {
                    backtrack(j + 1, path + "+" + num, resSoFar + num, num);
                    backtrack(j + 1, path + "-" + num, resSoFar - num, -num);
                    backtrack(j + 1, path + "*" + num, resSoFar - prevNum + prevNum * num, prevNum * num); // Can imagine with example: 1-2*3
                }
            }
        }
    }


    //https://leetcode.com/problems/expression-add-operators/discuss/71895/Java-Standard-Backtrace-AC-Solutoin-short-and-clear/239225
    /**
     * When we use dfs to do this question, the most tricky part is that how to deal with multiplication. For every
     * addition and subtraction, we just directly adding or subtracting the new number. However, for multiplication,
     * we should multiply current number and previous number firstly, and then add previous previous number.
     * So we can use a variable preNum to record every previous number in each recursion step. If current recursive
     * call is trying multiplication, we should use previous calculation value subtract previous number, and then
     * adding multiplication result between previous number and current number.
     * */
    public List<String> addOperators(String num, int target) {
        if (num.length() == 0) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        dfs(result, num, target, "", 0, 0, 0);
        return result;
    }

    /**
     * @param result: result list to store valid expressions
     * @param num: input num candidates
     * @param target: input target number
     * @param expr: current expression string
     * @param calcVal: current calculation value
     * @param preNum: previous number, in order to multiply current number if we want to put * between preNum and curNum
     * @param pos: current index in the input num array
     * */
    public void dfs(List<String> result, String num, int target, String expr, long calcVal, long preNum, int pos) {
        if (pos == num.length()) {
            if (calcVal == target) {
                result.add(expr);
            }
            return;
        }

        // start from first index of current position in num string, try all possible length of nums
        for (int i = pos; i < num.length(); i++) {
            // corner case: if current position is 0, we can only use it as a single digit number, should be 0
            // if it is not a single digit number with leading 0, it should be considered as an invalid number
            if (i != pos && num.charAt(pos) == '0') {
                break;
            }
            long curNum = Long.parseLong(num.substring(pos, i + 1));

            // position 0 should be considered individually, since it does not have any operand character before curNum
            if (pos == 0) {
                dfs(result, num, target, expr + curNum, curNum, curNum, i + 1);
            }
            else {
                dfs(result, num, target, expr + "+" + curNum, calcVal + curNum, curNum, i + 1);
                dfs(result, num, target, expr + "-" + curNum, calcVal - curNum, -curNum, i + 1);
                // trick part: to calculate multiplication, we should subtract previous number, and then add current
                // multiplication result to the subtraction result
                dfs(result, num, target, expr + "*" + curNum, calcVal - preNum + preNum * curNum, preNum * curNum, i + 1);
            }
        }
    }

}

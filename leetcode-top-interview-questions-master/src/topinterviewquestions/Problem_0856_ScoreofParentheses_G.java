package topinterviewquestions;

import java.util.Stack;

/*
Given a balanced parentheses string s, return the score of the string.

The score of a balanced parentheses string is based on the following rule:

"()" has score 1.
AB has score A + B, where A and B are balanced parentheses strings.
(A) has score 2 * A, where A is a balanced parentheses string.


Example 1:

Input: s = "()"
Output: 1
Example 2:

Input: s = "(())"
Output: 2
Example 3:

Input: s = "()()"
Output: 2


Constraints:

2 <= s.length <= 50
s consists of only '(' and ')'.
s is a balanced parentheses string.
 */
public class Problem_0856_ScoreofParentheses_G {
    //diss
    //https://leetcode.com/problems/score-of-parentheses/discuss/141777/C%2B%2BJavaPython-O(1)-Space
    //Approach 0: Stack
    //cur record the score at the current layer level.
    //
    //If we meet '(',
    //we push the current score to stack,
    //enter the next inner layer level,
    //and reset cur = 0.
    //
    //If we meet ')',
    //the cur score will be doubled and will be at least 1.
    //We exit the current layer level,
    //and set cur += stack.pop() + cur
    //
    //Complexity: O(N) time and O(N) space
    public int scoreOfParentheses(String S) {
        Stack<Integer> stack = new Stack<>();
        int cur = 0;
        for (char c : S.toCharArray()) {
            if (c == '(') {
                stack.push(cur);
                cur = 0;
            } else {
                cur = stack.pop() + Math.max(cur * 2, 1);
            }
        }
        return cur;
    }

    //Approach 1: Array
    //Same as stack, I do it with an array.
    //We change a pointer instead of pushing/popping repeatedly.
    //
    //Complexity: O(N) time and O(N) space
    public int scoreOfParentheses2(String S) {
        int res[] = new int[30], i = 0;
        for (char c : S.toCharArray()) {
            if (c == '(') {
                res[++i] = 0;
            } else {
                res[i - 1] += Math.max(res[i--] * 2, 1);
            }
        }
        return res[0];
    }


    //Follow-Up
    //Can you solve it in O(1) space?
    //
    //
    //Approach 2: O(1) Space
    //We count the number of layers.
    //If we meet '(' layers number l++
    //else we meet ')' layers number l--
    //
    //If we meet "()", we know the number of layer outside,
    //so we can calculate the score res += 1 << l.
    public int scoreOfParentheses3(String S) {
        int res = 0, depth = 0;
        for (int i = 0; i < S.length(); ++i) {
            if (S.charAt(i) == '(') {
                depth++;
            } else {
                depth--;
            }
            if (S.charAt(i) == ')' && S.charAt(i - 1) == '(') {
                res += 1 << depth;//// Whenever you meet a () pair, you multiply 1 by all the 2 outside of it, and accumulate the result
            }
        }
        return res;
    }




}

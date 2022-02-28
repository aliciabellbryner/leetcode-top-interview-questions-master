package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

/*
Given a string s that contains parentheses and letters, remove the minimum number of invalid parentheses to make the input string valid.

Return all the possible results. You may return the answer in any order.



Example 1:

Input: s = "()())()"
Output: ["(())()","()()()"]
Example 2:

Input: s = "(a)())()"
Output: ["(a())()","(a)()()"]
Example 3:

Input: s = ")("
Output: [""]


Constraints:

1 <= s.length <= 25
s consists of lowercase English letters and parentheses '(' and ')'.
There will be at most 20 parentheses in s.
 */
public class Problem_0301_RemoveInvalidParentheses {
    //https://leetcode.com/problems/remove-invalid-parentheses/discuss/75027/Easy-Short-Concise-and-Fast-Java-DFS-3-ms-solution/156556
    class Solution {
        public List<String> removeInvalidParentheses(String s) {
            List<String> output = new ArrayList<>();
            removeHelper(s, output, 0, 0, '(', ')');
            return output;
        }

        public void removeHelper(String s, List<String> output, int iStart, int jStart, char openParen, char closedParen) {
            int numOpenParen = 0, numClosedParen = 0;
            for (int i = iStart; i < s.length(); i++) {
                if (s.charAt(i) == openParen) numOpenParen++;
                if (s.charAt(i) == closedParen) numClosedParen++;
                if (numClosedParen > numOpenParen) { // We have an extra closed paren we need to remove
                    for (int j = jStart; j <= i; j++) // Try removing one at each position, skipping duplicates
                        if (s.charAt(j) == closedParen && (j == jStart || s.charAt(j - 1) != closedParen))
                            // Recursion: iStart = i since we now have valid # closed parenthesis thru i. jStart = j prevents duplicates
                            removeHelper(s.substring(0, j) + s.substring(j + 1, s.length()), output, i, j, openParen, closedParen);
                    return; // Stop here. The recursive calls handle the rest of the string.
                }
            }
            // No invalid closed parenthesis detected. Now check opposite direction, or reverse back to original direction.
            // What if s = ‘(()(()’ in which we need remove ‘(‘?
            //The answer is: do the same from right to left.
            //However a cleverer idea is: reverse the string and reuse the code!
            String reversed = new StringBuilder(s).reverse().toString();
            if (openParen == '(')
                removeHelper(reversed, output, 0, 0, ')','(');
            else
                output.add(reversed);
        }
    }
}

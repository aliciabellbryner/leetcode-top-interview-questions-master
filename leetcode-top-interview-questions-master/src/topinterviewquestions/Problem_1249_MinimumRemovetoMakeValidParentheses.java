package topinterviewquestions;

import java.util.Stack;

/*
Given a string s of '(' , ')' and lowercase English characters.

Your task is to remove the minimum number of parentheses ( '(' or ')', in any positions ) so that the resulting parentheses string is valid and return any valid string.

Formally, a parentheses string is valid if and only if:

It is the empty string, contains only lowercase characters, or
It can be written as AB (A concatenated with B), where A and B are valid strings, or
It can be written as (A), where A is a valid string.


Example 1:

Input: s = "lee(t(c)o)de)"
Output: "lee(t(c)o)de"
Explanation: "lee(t(co)de)" , "lee(t(c)ode)" would also be accepted.
Example 2:

Input: s = "a)b(c)d"
Output: "ab(c)d"
Example 3:

Input: s = "))(("
Output: ""
Explanation: An empty string is also valid.


Constraints:

1 <= s.length <= 105
s[i] is either'(' , ')', or lowercase English letter.
 */
public class Problem_1249_MinimumRemovetoMakeValidParentheses {
    //https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/discuss/419402/JavaC%2B%2B-Stack
    //To make the string valid with minimum removals, we need to get rid of all parentheses that do not have a matching pair.
    //
    //Push char index into the stack when we see '('.
    //
    //Pop from the stack when we see ')'.
    //
    //If the stack is empty, then we have ')' without the pair, and it needs to be removed.
    //In the end, the stack will contain indexes of '(' without the pair, if any. We need to remove all of them too.
    public String minRemoveToMakeValid(String s) {
        StringBuilder sb = new StringBuilder(s);
        Stack<Integer> st = new Stack<>();//放的是（的位置index
        for (int i = 0; i < sb.length(); ++i) {
            if (sb.charAt(i) == '(') st.add(i);
            if (sb.charAt(i) == ')') {
                if (!st.empty()) st.pop();//如果是多余的）则说明这个位置的）可以和stack中的（配对
                else sb.setCharAt(i, '*');//如果前面没有（那就说明这个位置的）需要删除，那就直接加个placeholder *到时候再删
            }
        }
        while (!st.empty())
            sb.setCharAt(st.pop(), '*');
        return sb.toString().replaceAll("\\*", "");
    }

    //solution2
    //https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/discuss/419466/Constant-Space-Solution/712362
    // For the current parenthesis we know how many '(' come before it, and how many ')' come after it. This is all the information we need to determine whether the current parenthesis should be removed or not.
// O(s.length()) time, O(1) space.
    public String minRemoveToMakeValid2(String s) {
        if (s == null || s.length() == 0) return s;
        // Number of unmatched '(' to the left and number of unmatched ')' to the right of the current index i.
        int open = 0, closed = 0;
        // Count the number of ')' parenthesis. When i == 0 that's the number of unmatched ')' to the right.
        for (int i = 0; i < s.length(); ++i) {
            if(s.charAt(i) == ')') closed++;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '(') {
                open++;     // Increment count of '(' to the left so that a future ')' can match it.
                if (closed > 0) {
                    closed--;
                    // Only add the current '(' to the result if there is a ')' to the right that can match (close) it.
                    result.append(s.charAt(i));
                }
            } else if (s.charAt(i) == ')') {
                if (open > 0) {
                    open--;
                    // Only add the current ')' to the result if there is a '(' to the left that can match it.
                    result.append(s.charAt(i));
                } else {
                    closed--;   // The current ')' hasn't been closed by a '(' to the left AND it can't be used to close future '('.
                }
            } else {
                result.append(s.charAt(i));  // Simply append all non-parenthesis characters.
            }
        }
        return result.toString();
    }

}

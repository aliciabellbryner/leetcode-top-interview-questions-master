package topinterviewquestions;

import java.util.LinkedList;
import java.util.Stack;
/*
Given a string s representing a valid expression, implement a basic calculator to evaluate it, and return the result of the evaluation.

Note: You are not allowed to use any built-in function which evaluates strings as mathematical expressions, such as eval().



Example 1:

Input: s = "1 + 1"
Output: 2
Example 2:

Input: s = " 2-1 + 2 "
Output: 3
Example 3:

Input: s = "(1+(4+5+2)-3)+(6+8)"
Output: 23


Constraints:

1 <= s.length <= 3 * 105
s consists of digits, '+', '-', '(', ')', and ' '.
s represents a valid expression.
'+' is not used as a unary operation (i.e., "+1" and "+(2 + 3)" is invalid).
'-' could be used as a unary operation (i.e., "-1" and "-(2 + 3)" is valid).
There will be no two consecutive operators in the input.
Every number and running calculation will fit in a signed 32-bit integer.
 */
public class Problem_0224_BasicCalculator {

	public int calculate(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		Stack<Integer> stack = new Stack<>();
		int num = 0;
		int sign = 1;
		int res = 0;
		stack.push(sign);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= '0' && c <= '9') {
				num = num * 10 + c - '0';
			} else if (c == '+' || c == '-') {
				res += sign * num;
				sign = stack.peek() * (c == '+' ? 1 : -1);
				num = 0;
			} else if (c == '(') {
				stack.push(sign);
			} else if (c == ')') {
				stack.pop();
			}

		}
		res += sign * num;
		return res;
	}

}

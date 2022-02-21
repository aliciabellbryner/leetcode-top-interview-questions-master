package topinterviewquestions;

import java.util.LinkedList;
import java.util.Stack;
/*
Given a string s which represents an expression, evaluate this expression and return its value.

The integer division should truncate toward zero.

You may assume that the given expression is always valid. All intermediate results will be in the range of [-231, 231 - 1].

Note: You are not allowed to use any built-in function which evaluates strings as mathematical expressions, such as eval().



Example 1:

Input: s = "3+2*2"
Output: 7
Example 2:

Input: s = " 3/2 "
Output: 1
Example 3:

Input: s = " 3+5 / 2 "
Output: 5


Constraints:

1 <= s.length <= 3 * 105
s consists of integers and operators ('+', '-', '*', '/') separated by some number of spaces.
s represents a valid expression.
All the integers in the expression are non-negative integers in the range [0, 231 - 1].
The answer is guaranteed to fit in a 32-bit integer.
 */
//https://leetcode.com/problems/basic-calculator-ii/
public class Problem_0227_BasicCalculatorII {

	//better option: leetcode discussion
	//注意题目：没有括号，所以思路是用stack存放每一个加号隔开的个体，有乘除包含的就直接算一个数然后放进stack里
	public static int calculate_leetcode(String s) {
		if (s == null || s.length() == 0) return 0;
		Stack<Integer> stack = new Stack<>();//
		s += '+';//目的是为了把最后一部分也放到stack里
		char sign = '+';
		int num = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= '0' && c <= '9') {
				num = num * 10 + c - '0';
				continue;
			} else if (c == ' ') {
				continue;
			} else if (sign == '+') {
				stack.push(num);
			} else if (sign == '-') {
				stack.push(-num);
			} else if (sign == '*') {
				stack.push(stack.pop()*num);
			} else if (sign == '/') {
				stack.push(stack.pop()/num);
			}
			sign = c;
			num = 0;
		}

		int res = 0;
		while (!stack.isEmpty()) {
			res += stack.pop();
		}
		return res;
	}


	//Zuo's solution, not easy to understand, use the solution below
	public static int calculate(String s) {
		char[] str = s.toCharArray();
		LinkedList<String> list = new LinkedList<>();
		StringBuilder builder = new StringBuilder();
		builder.setLength(0);
		for (int i = 0; i < str.length; i++) {
			if (str[i] != ' ') {
				if (str[i] >= '0' && str[i] <= '9') {
					builder.append(str[i]);
				} else {
					handleStack(list, builder.toString(), str[i]);
					builder.setLength(0);
				}
			}
		}
		handleStack(list, builder.toString(), ' ');
		return computeStack(list);
	}

	public static void handleStack(LinkedList<String> list, String str, char op) {
		if (list.isEmpty() || (list.peekLast().equals("+") || list.peekLast().equals("-"))) {
			list.addLast(str);
		} else {
			int num = Integer.valueOf(str);
			String preOp = list.pollLast();
			int preNum = Integer.valueOf(list.pollLast());
			if (preOp.equals("*")) {
				list.addLast(String.valueOf(preNum * num));
			} else {
				list.addLast(String.valueOf(preNum / num));
			}
		}
		list.addLast(String.valueOf(op));
	}

	public static int computeStack(LinkedList<String> list) {
		int ans = Integer.valueOf(list.pollFirst());
		while (list.size() != 1) {
			String op = list.pollFirst();
			int cur = Integer.valueOf(list.pollFirst());
			if (op.equals("+")) {
				ans += cur;
			} else {
				ans -= cur;
			}
		}
		return ans;
	}



	public static void main(String[] args) {
		System.out.println(calculate_leetcode(" 3/2 "));
	}
}

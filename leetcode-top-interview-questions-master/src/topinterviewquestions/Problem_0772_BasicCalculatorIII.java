package topinterviewquestions;

import java.util.LinkedList;
import java.util.Stack;
/*
Implement a basic calculator to evaluate a simple expression string.

The expression string contains only non-negative integers, '+', '-', '*', '/' operators, and open '(' and closing parentheses ')'. The integer division should truncate toward zero.

You may assume that the given expression is always valid. All intermediate results will be in the range of [-231, 231 - 1].

Note: You are not allowed to use any built-in function which evaluates strings as mathematical expressions, such as eval().



Example 1:

Input: s = "1+1"
Output: 2
Example 2:

Input: s = "6-4/2"
Output: 4
Example 3:

Input: s = "2*(5+5*2)/3+(6/2+8)"
Output: 21


Constraints:

1 <= s <= 104
s consists of digits, '+', '-', '*', '/', '(', and ')'.
s is a valid expression.
 */
public class Problem_0772_BasicCalculatorIII {

//leetcode discussion solution, easier to understand
	public static int calculate_leetcode(String s) {
		if (s == null || s.length() == 0) return 0;
		Stack<Integer> nums = new Stack<>(); // the stack that stores numbers
		Stack<Character> ops = new Stack<>(); // the stack that stores operators (including parentheses)
		int num = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == ' ') continue;
			if (Character.isDigit(c)) {
				num = c - '0';
				// iteratively calculate each number
				while (i < s.length() - 1 && Character.isDigit(s.charAt(i+1))) {
					num = num * 10 + (s.charAt(i+1) - '0');
					i++;
				}
				nums.push(num);
				num = 0; // reset the number to 0 before next calculation
			} else if (c == '(') {
				ops.push(c);
			} else if (c == ')') {
				// do the math when we encounter a ')' until '('
				while (ops.peek() != '(') {
					nums.push(operation(ops.pop(), nums.pop(), nums.pop()));
				}
				ops.pop(); // get rid of '(' in the ops stack
			} else if (c == '+' || c == '-' || c == '*' || c == '/') {
				while (!ops.isEmpty() && precedence(c, ops.peek())) {
					nums.push(operation(ops.pop(), nums.pop(),nums.pop()));
				}
				ops.push(c);
			}
		}
		while (!ops.isEmpty()) {
			nums.push(operation(ops.pop(), nums.pop(), nums.pop()));
		}
		return nums.pop();
	}

	private static int operation(char op, int b, int a) {
		switch (op) {
			case '+': return a + b;
			case '-': return a - b;
			case '*': return a * b;
			case '/': return a / b; // assume b is not 0
		}
		return 0;
	}
	// helper function to check precedence of current operator and the uppermost operator in the ops stack
	private static boolean precedence(char op1, char op2) {
		if (op2 == '(' || op2 == ')') return false;
		if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) return false;
		return true;
	}



	//zuo
	public static int calculate(String str) {
		return f(str.toCharArray(), 0)[0];
	}

	// 请从str[i...]往下算，遇到字符串终止位置或者右括号，就停止
	// 返回两个值，长度为2的数组
	// 0) 负责的这一段的结果是多少
	// 1) 负责的这一段计算到了哪个位置
	public static int[] f(char[] str, int i) {
		LinkedList<String> que = new LinkedList<String>();
		int cur = 0;
		int[] bra = null;
		// 从i出发，开始撸str
		while (i < str.length && str[i] != ')') {
			if (str[i] == ' ') {
				i++;
				continue;
			}
			if (str[i] >= '0' && str[i] <= '9') {
				cur = cur * 10 + str[i++] - '0';
			} else if (str[i] != '(') { // 你遇到了运算符
				// que 接受的是str的东西，cur是一个整型
				addNum(que, cur);
				que.addLast(String.valueOf(str[i++]));
				cur = 0;
			} else { // 遇到i左括号了
				bra = f(str, i + 1);
				cur = bra[0];
				i = bra[1] + 1;
			}
		}
		addNum(que, cur);
		return new int[] { getNum(que), i };
	}

	public static void addNum(LinkedList<String> que, int num) {
		if (!que.isEmpty()) {
			int cur = 0;
			String top = que.pollLast();
			if (top.equals("+") || top.equals("-")) {
				que.addLast(top);
			} else {
				cur = Integer.valueOf(que.pollLast());
				num = top.equals("*") ? (cur * num) : (cur / num);
			}
		}
		que.addLast(String.valueOf(num));
	}

	public static int getNum(LinkedList<String> que) {
		int res = 0;
		boolean add = true;
		String cur = null;
		int num = 0;
		while (!que.isEmpty()) {
			cur = que.pollFirst();
			if (cur.equals("+")) {
				add = true;
			} else if (cur.equals("-")) {
				add = false;
			} else {
				num = Integer.valueOf(cur);
				res += add ? num : (-num);
			}
		}
		return res;
	}


	//https://leetcode.com/problems/basic-calculator/
	public static int calculate_224(String s) {
		if(s == null) return 0;

		int result = 0;
		int sign = 1;
		int num = 0;

		Stack<Integer> stack = new Stack<Integer>();
		stack.push(sign);

		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if(c >= '0' && c <= '9') {
				num = num * 10 + (c - '0');

			} else if(c == '+' || c == '-') {
				result += sign * num;
				sign = stack.peek() * (c == '+' ? 1: -1);
				num = 0;

			} else if(c == '(') {
				stack.push(sign);

			} else if(c == ')') {
				stack.pop();
			}
		}

		result += sign * num;
		return result;
	}

	public static void main(String[] args) {
		System.out.println(calculate_224("-(2+(1-2))"));
	}
}

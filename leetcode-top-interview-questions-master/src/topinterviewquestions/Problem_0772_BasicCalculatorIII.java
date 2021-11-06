package topinterviewquestions;

import java.util.LinkedList;
import java.util.Stack;

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
				while (ops.peek() != '(') nums.push(operation(ops.pop(), nums.pop(), nums.pop()));
				ops.pop(); // get rid of '(' in the ops stack
			} else if (c == '+' || c == '-' || c == '*' || c == '/') {
				while (!ops.isEmpty() && precedence(c, ops.peek())) nums.push(operation(ops.pop(), nums.pop(),nums.pop()));
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

}

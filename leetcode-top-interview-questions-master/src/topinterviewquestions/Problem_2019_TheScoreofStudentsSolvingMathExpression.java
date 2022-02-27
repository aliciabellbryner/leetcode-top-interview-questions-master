package topinterviewquestions;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/*
3 <= s.length <= 31
s represents a valid expression that contains only digits 0-9, '+', and '*' only.
All the integer operands in the expression are in the inclusive range [0, 9].
1 <= The count of all operators ('+' and '*') in the math expression <= 15
Test data are generated such that the correct answer of the expression is in the range of [0, 1000].
n == answers.length
1 <= n <= 104
0 <= answers[i] <= 1000
 */
public class Problem_2019_TheScoreofStudentsSolvingMathExpression {
    //https://leetcode.com/problems/the-score-of-students-solving-math-expression/discuss/1486306/PythonJava-Explanation-with-pictures-DP
    class Solution {
        public int scoreOfStudents(String s, int[] A) {
            int n = (int) (s.length() / 2 + 1);
            Set<Integer>[][] res = new Set[n][n];
            for (int i = 0; i < n; ++i) {
                res[i][i] = new HashSet<>();
                res[i][i].add(s.charAt(2 * i) - '0');
            }
            for (int dif = 1; dif < n; ++dif) {
                for (int start = 0; start < n - dif; ++start) {
                    int end = start + dif;
                    res[start][end] = new HashSet<>();
                    for (int i = start * 2 + 1; i < end * 2; i += 2) {
                        if (s.charAt(i) - '+' == 0) {
                            for (int a : res[start][(int) (i / 2)]) {
                                for (int b : res[(int) (i / 2 + 1)][end]) {
                                    if (a + b <= 1000) res[start][end].add(a + b);
                                }
                            }
                        } else {
                            for (int a : res[start][(int) (i / 2)]) {
                                for (int b : res[(int) (i / 2 + 1)][end]) {
                                    if (a * b <= 1000) res[start][end].add(a * b);
                                }
                            }
                        }
                    }
                }
            }

            int correct = calculate(s), ans = 0;
            for (int a : A) {
                if (a == correct) ans += 5;
                else if (res[0][n - 1].contains(a)) ans += 2;
            }
            return ans;

        }

        public int calculate(String s) {
            int i = 0;
            Stack<Integer> stack = new Stack<>();
            char operator = '+';
            int num = 0;
            while (i < s.length()) {
                char ch = s.charAt(i++);
                if (ch >= '0' && ch <= '9') num = ch - '0';
                if (i >= s.length() || ch == '+' || ch == '*') {
                    if (operator == '+') stack.push(num);
                    else if (operator == '*') stack.push(stack.pop() * num);
                    operator = ch;
                    num = 0;
                }
            }
            return stack.stream().mapToInt(Integer::intValue).sum();
        }

    }

}

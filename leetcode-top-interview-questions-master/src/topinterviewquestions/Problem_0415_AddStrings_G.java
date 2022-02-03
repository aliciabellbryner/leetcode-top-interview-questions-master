package topinterviewquestions;

public class Problem_0415_AddStrings_G {
    //https://leetcode.com/problems/add-strings/solution/
    class Solution {
        public String addStrings(String num1, String num2) {
            StringBuilder res = new StringBuilder();

            int carry = 0;
            int p1 = num1.length() - 1;
            int p2 = num2.length() - 1;
            while (p1 >= 0 || p2 >= 0) {
                int x1 = p1 >= 0 ? num1.charAt(p1) - '0' : 0;
                int x2 = p2 >= 0 ? num2.charAt(p2) - '0' : 0;
                int value = (x1 + x2 + carry) % 10;
                carry = (x1 + x2 + carry) / 10;
                res.append(value);
                p1--;
                p2--;
            }

            if (carry != 0)
                res.append(carry);

            return res.reverse().toString();
        }
    }

    //follow up: how to add two float
    public class AddFloat1 {
        public String solution(String s1, String s2) {
            int d1 = s1.indexOf(".");
            int d2 = s2.indexOf(".");
            int r = Math.max(s1.length() - d1, s2.length() - d2);
            int i = r - 1;
            int carry = 0;
            StringBuilder sb = new StringBuilder();
            while (i + d1 >= 0 || i + d2 >= 0) {
                if (i == 0) {
                    sb.append('.');
                    i--;
                    continue;
                }
                int sum  = 0;
                if (i + d1 >= 0 && i + d1 < s1.length()) {
                    sum += s1.charAt(i + d1) - '0';
                }
                if (i + d2 >= 0 && i + d2 < s2.length()) {
                    sum += s2.charAt(i + d2) - '0';
                }
                sum += carry;
                sb.append(sum % 10);
                carry = sum / 10;
                i--;
            }

            if (carry != 0) {
                sb.append('1');
            }

            return sb.reverse().toString();
        }
    }
}

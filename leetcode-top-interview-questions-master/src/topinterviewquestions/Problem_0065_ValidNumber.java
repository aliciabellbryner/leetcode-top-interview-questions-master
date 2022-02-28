package topinterviewquestions;
/*
A valid number can be split up into these components (in order):

A decimal number or an integer.
(Optional) An 'e' or 'E', followed by an integer.
A decimal number can be split up into these components (in order):

(Optional) A sign character (either '+' or '-').
One of the following formats:
One or more digits, followed by a dot '.'.
One or more digits, followed by a dot '.', followed by one or more digits.
A dot '.', followed by one or more digits.
An integer can be split up into these components (in order):

(Optional) A sign character (either '+' or '-').
One or more digits.
For example, all the following are valid numbers: ["2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789"], while the following are not valid numbers: ["abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53"].

Given a string s, return true if s is a valid number.



Example 1:

Input: s = "0"
Output: true
Example 2:

Input: s = "e"
Output: false
Example 3:

Input: s = "."
Output: false


Constraints:

1 <= s.length <= 20
s consists of only English letters (both uppercase and lowercase), digits (0-9), plus '+', minus '-', or dot '.'.
 */
public class Problem_0065_ValidNumber {
//https://leetcode.com/problems/valid-number/discuss/23738/Clear-Java-solution-with-ifs
	public class Solution {
		public boolean isNumber(String s) {
			s = s.toLowerCase().trim();//去掉头尾space
			boolean dotSeen = false;
			boolean eSeen   = false;
			boolean numberBeforeE = false;
			boolean numberAfterE  = false;
			for (int i = 0; i < s.length(); i++) {
				char cur = s.charAt(i);
				if ('0' <= cur && cur <= '9') {
					if (!eSeen) numberBeforeE = true;
					if (eSeen)  numberAfterE  = true;
				} else if (cur == '-' || cur == '+') {
					if (i != 0 && s.charAt(i - 1) != 'e') return false;
				} else if (cur == '.') {
					if (eSeen || dotSeen) return false;
					dotSeen = true;
				} else if (cur == 'e' ) {
					if (eSeen) return false;
					eSeen = true;
				} else { // invalid chars
					return false;
				}
			}
			return eSeen ? (numberBeforeE && numberAfterE) : numberBeforeE;
			//(numberBeforeE && numberAfterE): 有出现过e那必须头尾都有数字
		}
	}

}

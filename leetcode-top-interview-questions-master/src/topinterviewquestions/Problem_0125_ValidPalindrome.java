package topinterviewquestions;

import java.util.Arrays;

public class Problem_0125_ValidPalindrome {
	//my solution
	public boolean isPalindrome_j(String s) {
		if (s == null) {
			return true;
		}
		int l = 0;
		int r = s.length()-1;
		while (l < r) {
			if ((Character.toLowerCase(s.charAt(l)) > 'z' || Character.toLowerCase(s.charAt(l)) < 'a') && (s.charAt(l) > '9' || s.charAt(l) < '0')) {
				l++;
				continue;
			}//s.charAt(l)不是字母或数字，则跳过
			if ((Character.toLowerCase(s.charAt(r)) > 'z' || Character.toLowerCase(s.charAt(r)) < 'a') && (s.charAt(r) > '9' || s.charAt(r) < '0')) {
				r--;
				continue;
			}//s.charAt(r)不是字母或数字，则跳过
			if (Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r))) {
				return false;
			}
			l++;
			r--;
		}
		return true;
	}

	public static boolean isPalindrome(String s) {
		if (s == null || s.length() == 0) {
			return true;
		}
		char[] str = s.toCharArray();
		int L = 0;
		int R = str.length - 1;
		while (L < R) {
			if (validChar(str[L]) && validChar(str[R])) {
				if (!equal(str[L], str[R])) {
					return false;
				}
				L++;
				R--;
			} else {
				L += validChar(str[L]) ? 0 : 1;
				R -= validChar(str[R]) ? 0 : 1;
			}
		}
		return true;
	}

	public static boolean validChar(char c) {
		return isLetter(c) || isNumber(c);
	}

	public static boolean equal(char c1, char c2) {
		if (isNumber(c1) || isNumber(c2)) {
			return c1 == c2;
		}
		return (c1 == c2) || (Math.max(c1, c2) - Math.min(c1, c2) == 32);
	}

	public static boolean isLetter(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}

	public static boolean isNumber(char c) {
		return (c >= '0' && c <= '9');
	}



	public static void main(String[] args) {

		String s = "test";
		char[] ca = s.toCharArray();
		char temp = ca[2];
		ca[2] = ca[3];
		ca[3] = temp;
		System.out.println(String.valueOf(ca));
		System.out.println(s);
//		System.out.println(Character.toLowerCase('9'));
	}

}

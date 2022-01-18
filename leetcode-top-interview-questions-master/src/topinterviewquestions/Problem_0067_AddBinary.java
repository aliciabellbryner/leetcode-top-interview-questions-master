package topinterviewquestions;

import java.util.Arrays;

public class Problem_0067_AddBinary {

	public static String addBinary(String a, String b) {
		StringBuilder sb = new StringBuilder();
		int p1 = a.length() - 1;
		int p2 = b.length() - 1;
		int carry = 0;
		int sum = 0;
		while (p1 >= 0 || p2 >= 0) {
			int n1 = p1 >= 0 ? a.charAt(p1--) - '0' : 0;
			int n2 = p2 >= 0 ? b.charAt(p2--) - '0' : 0;
			sum = n1 + n2 + carry;
			sb.append((sum) % 2);
			carry = sum / 2;
		}
		if (carry == 1) {
			sb.append(1);
		}
		return sb.reverse().toString();
	}
}

package topinterviewquestions;

import java.util.Arrays;

public class Problem_0066_PlusOne {

	public static int[] plusOne(int[] digits) {
		int N = digits.length;
		for (int i = N - 1; i >= 0; i--) {
			if (digits[i] < 9) {
				digits[i]++;
				return digits;
			}
			digits[i] = 0;
		}
		int[] ans = new int[N + 1];
		ans[0] = 1;
		return ans;
	}

	//my bad solution, don't use this!
	public static int[] plusOne_j(int[] digits) {
		if (digits == null || digits.length == 0) {
			return null;
		}
		int N = digits.length;
		int sum = digits[N-1]+1;
		digits[N-1] = sum%10;
		int carry = sum/10;
		for (int i = N-2; i >= 0; i--) {
			sum = digits[i] + carry;
			digits[i] = sum % 10;
			carry = sum / 10;
		}
		if (carry == 1) {
			int[] res = new int[N+1];
			res[0] = 1;
			for (int j = 1; j < N+1; j++) {
				res[j] = digits[j-1];
			}
			return res;
		}
		return digits;
	}

	public static void main(String[] args) {
		System.out.println(Arrays.toString(plusOne_j(new int[]{9,9})));
	}
}

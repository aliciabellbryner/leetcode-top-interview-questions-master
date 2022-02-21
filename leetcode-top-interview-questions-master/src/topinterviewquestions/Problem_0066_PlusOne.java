package topinterviewquestions;

import java.util.Arrays;
/*
You are given a large integer represented as an integer array digits, where each digits[i] is the ith digit of the integer. The digits are ordered from most significant to least significant in left-to-right order. The large integer does not contain any leading 0's.

Increment the large integer by one and return the resulting array of digits.

Example 1:

Input: digits = [1,2,3]
Output: [1,2,4]
Explanation: The array represents the integer 123.
Incrementing by one gives 123 + 1 = 124.
Thus, the result should be [1,2,4].
Example 2:

Input: digits = [4,3,2,1]
Output: [4,3,2,2]
Explanation: The array represents the integer 4321.
Incrementing by one gives 4321 + 1 = 4322.
Thus, the result should be [4,3,2,2].
Example 3:

Input: digits = [9]
Output: [1,0]
Explanation: The array represents the integer 9.
Incrementing by one gives 9 + 1 = 10.
Thus, the result should be [1,0].

Constraints:

1 <= digits.length <= 100
0 <= digits[i] <= 9
digits does not contain any leading 0's.
 */
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

package topinterviewquestions;

public class Problem_0171_ExcelSheetColumnNumber {

	// 这道题反过来也要会写
	public static int titleToNumber(String s) {
		int res = 0;
		for (int i = 0; i < s.length(); i++) {
			res = res * 26 + (s.charAt(i) - 'A') + 1;
		}
		return res;
	}

	//反过来
	public static String numberToTitle(int n) {
		StringBuilder sb = new StringBuilder();
		while (n != 0) {
			char c = (char) ('A' + n % 26 - 1);
			sb.insert(0, c);
			n /= 26;
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(titleToNumber("JF"));
		System.out.println(numberToTitle(266));
	}


	//二进制和十进制转换
	static int binaryToDecimal(int n)
	{
		int num = n;
		int dec_value = 0;
		// Initializing base
		// value to 1, i.e 2^0
		int base = 1;
		int temp = num;
		while (temp > 0) {
			int last_digit = temp % 10;
			temp = temp / 10;
			dec_value += last_digit * base;
			base = base * 2;
		}

		return dec_value;
	}

	static void decToBinary(int n)
	{
		// array to store binary number
		int[] binaryNum = new int[1000];
		// counter for binary array
		int i = 0;
		while (n > 0)
		{
			// storing remainder in binary array
			binaryNum[i] = n % 2;
			n = n / 2;
			i++;
		}
		// printing binary array in reverse order
		for (int j = i - 1; j >= 0; j--)
			System.out.print(binaryNum[j]);
	}

}

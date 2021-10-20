package topinterviewquestions;

public class Problem_0069_SqrtX {

	// x一定非负，输入可以保证 Zuo's solution
	public static int mySqrt(int x) {
		if (x == 0) {
			return 0;
		}
		if (x < 3) {
			return 1;
		}
		long ans = 1;
		long L = 1;
		long R = x;
		long M = 0;
		while (L <= R) {//binary search
			M = (L + R) / 2;
			if (M * M <= x) {
				ans = M;
				L = M + 1;
			} else {
				R = M - 1;
			}
		}
		return (int) ans;
	}

	//my bad solution: although can get the right ans, but time complexity is much longer than Zuo's
	public int mySqrt_j(int x) {
		if (x <= 1) {
			return x;
		}
		long i = 1;
		for (; i <= x/2; i++) {
			if (i*i > x) {
				break;
			}
		}
		return (int)(i - 1);
	}
}

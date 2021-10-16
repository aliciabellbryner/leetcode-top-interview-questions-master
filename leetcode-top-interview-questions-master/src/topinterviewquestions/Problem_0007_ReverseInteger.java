package topinterviewquestions;

public class Problem_0007_ReverseInteger {

	//求一个数的反序
	public static int reverse(int x) {
		boolean neg = ((x >>> 31) & 1) == 1;//不带符号位右移
		x = neg ? x : -x;//把这个数转成负数， 目的是因为负数能表示的范围是-2^31-1, 整数是2^31，所以不会漏了系统最值
		int m = Integer.MIN_VALUE / 10;
		int o = Integer.MIN_VALUE % 10;
		int res = 0;
		while (x != 0) {
			if (res < m || (res == m && x % 10 < o)) {//防止溢出
				return 0;
			}
			res = res * 10 + x % 10;
			x /= 10;
		}
		return neg ? res : Math.abs(res);
	}

}

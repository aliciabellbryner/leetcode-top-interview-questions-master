package topinterviewquestions;

public class Problem_0191_NumberOf1Bits {

//https://leetcode.com/problems/number-of-1-bits/solution/

	//Approach 1
	public int hammingWeight(int n) {
		int res = 0;
		int mask = 1;
		for (int i = 0; i < 32; i++) {
			if ((n & mask) != 0) {
				res++;
			}
			mask <<= 1;
		}
		return res;
	}

	//Approach 2
	public int hammingWeight2(int n) {
		int sum = 0;
		while (n != 0) {
			sum++;
			n &= (n - 1);
		}
		return sum;
	}

	public static int hammingWeight3(int n) {
		n = (n & 0x55555555) + ((n >>> 1) & 0x55555555);
		n = (n & 0x33333333) + ((n >>> 2) & 0x33333333);
		n = (n & 0x0f0f0f0f) + ((n >>> 4) & 0x0f0f0f0f);
		n = (n & 0x00ff00ff) + ((n >>> 8) & 0x00ff00ff);
		n = (n & 0x0000ffff) + ((n >>> 16) & 0x0000ffff);
		return n;
	}
	
}

package topinterviewquestions;

import java.util.*;

public class Problem_0279_PerfectSquares {


	//Approach 2: Dynamic Programming
	// https://leetcode.com/problems/perfect-squares/solution/
	//Time complexity: O(n*sqrt{n}) In main step, we have a nested loop,
	// where the outer loop is of n iterations and in the inner loop it takes at maximum sqrt{n} iterations.
	//Space Complexity: O(n). We keep all the intermediate sub-solutions in the array dp[].
	//https://leetcode.com/problems/perfect-squares/discuss/71495/An-easy-understanding-DP-solution-in-Java
	public int numSquares_dp(int n) {
		int[] dp = new int[n + 1];
		Arrays.fill(dp, Integer.MAX_VALUE);
		dp[0] = 0;
		for(int i = 1; i <= n; ++i) {
			int min = Integer.MAX_VALUE;
			int j = 1;
			while(i - j*j >= 0) {
				min = Math.min(min, dp[i - j*j] + 1);
				++j;
			}
			dp[i] = min;
		}
		return dp[n];
	}


	//hashset+queue
	public int numSquares(int n) {
		Set<Integer> vis = new HashSet<>();
		Queue<Integer> q = new LinkedList<>();
		q.offer(n);
		for (int level = 0; !q.isEmpty(); level++) {
			for (int i = q.size(); i > 0; i--) {
				int num = q.poll();
				if (num == 0) {
					return level;
				}
				for (int j = 1; j * j <= num; j++)
					if (vis.add(num - j * j))
						q.offer(num - j * j);
			}
		}
		return 0;
	}

	// 1 : 1, 4, 9, 16, 25, 36, ...
	// 4 : 7, 15, 23, 28, 31, 39, 47, 55, 60, 63, 71, ...
	// 规律解
	// 规律一：个数不超过4
	// 规律二：出现1个的时候，显而易见
	// 规律三：任何数 % 8 == 7，一定是4个
	// 规律四：任何数消去4的因子之后，剩下rest，rest % 8 == 7，一定是4个
	public static int numSquares2(int n) {
		int rest = n;
		while (rest % 4 == 0) {
			rest /= 4;
		}
		if (rest % 8 == 7) {
			return 4;
		}
		int f = (int) Math.sqrt(n);
		if (f * f == n) {
			return 1;
		}
		for (int first = 1; first * first <= n; first++) {
			int second = (int) Math.sqrt(n - first * first);
			if (first * first + second * second == n) {
				return 2;
			}
		}
		return 3;
	}

	// 数学解
	// 1）四平方和定理
	// 2）任何数消掉4的因子，结论不变
	//Time complexity: O(sqrt(n))
	// ). In the main loop, we check if the number can be decomposed into the sum of two squares,
	// which takes O(sqrt(n)) iterations. In the rest of cases, we do the check in constant time.
	//Space complexity: O(1). The algorithm consumes a constant space, regardless the size of the input number.
	public static int numSquares3(int n) {
		while (n % 4 == 0) {
			n /= 4;
		}
		if (n % 8 == 7) {
			return 4;
		}
		for (int a = 0; a * a <= n; ++a) {
			int b = (int) Math.sqrt(n - a * a);
			if (a * a + b * b == n) {
				return (a > 0 && b > 0) ? 2 : 1;
			}
		}
		return 3;
	}

	public static void main(String[] args) {
		for (int i = 1; i < 1000; i++) {
			System.out.println(i + " , " + numSquares1(i));
		}
	}


	// 暴力解
	public static int numSquares1(int n) {
		int res = n, num = 2;
		while (num * num <= n) {
			int a = n / (num * num), b = n % (num * num);
			res = Math.min(res, a + numSquares1(b));
			num++;
		}
		return res;
	}

}

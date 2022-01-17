package topinterviewquestions;

public class Problem_0096_UniqueBinarySearchTrees {


	//G(n): the number of unique BST for a sequence of length n.
	//G(n) = G(0) * G(n-1) + G(1) * G(n-2) + … + G(n-1) * G(0)
	//time complexity is O(N^2)
	//space O(N)
	public int numTrees_leetcode(int n) {
		int [] G = new int[n+1];
		G[0] = G[1] = 1;

		for(int i=2; i<=n; ++i) {
			for(int j=1; j<=i; ++j) {
				G[i] += G[j-1] * G[i-j];
			}
		}
		return G[n];
	}


	public static int numTrees(int N) {
		if (N < 0) {
			return 0;
		}
		if (N < 2) {
			return 1;
		}
		long a = 1;
		long b = 1;
		for (int i = 1, j = N + 1; i <= N; i++, j++) {
			a *= i;
			b *= j;
			long gcd = gcd(a, b);
			a /= gcd;
			b /= gcd;
		}
		return (int) ((b / a) / (N + 1));
	}

	public static long gcd(long m, long n) {
		return n == 0 ? m : gcd(n, m % n);
	}

}

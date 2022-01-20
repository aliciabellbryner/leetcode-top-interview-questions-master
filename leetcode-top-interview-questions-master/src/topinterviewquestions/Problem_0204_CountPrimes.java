package topinterviewquestions;

public class Problem_0204_CountPrimes {


	//https://leetcode.com/problems/count-primes/solution/
	//Time Complexity: The overall time complexity is O(sqrt(n)log(logn)))
	//Space Complexity: O(n)
	public static int countPrimes(int n) {
		if (n < 3) {
			return 0;
		}

		boolean[] isNotPrime = new boolean[n];
		for (int i = 2; i <= (int)Math.sqrt(n); ++i) {
			if (isNotPrime[i] == false) {
				for (int j = i*i; j < n; j += i) {
					isNotPrime[j] = true;
				}
			}
		}

		int res = 0;
		for (int i = 2; i < n; i++) {
			if (!isNotPrime[i]) {
				res++;
			}
		}

		return res;
	}


	//you can not use transverse to decide every time whether it is prime or not as it will time exceeding
	public static int countPrimes2(int n) {
		if (n < 3) {
			return 0;
		}
		boolean[] f = new boolean[n];
		int count = n / 2;//the even number cannot be prime, so delete that from the res first;
		for (int i = 3; i * i < n; i += 2) {
			if (f[i]) {
				continue;
			}
			for (int j = i * i; j < n; j += 2 * i) {//this step is to delete the answer that 3*3, 5*3, 7*3 ......
				// the step is 2*i not i is because if you use i or 3i, the j will be even which has already been deleted before
				if (!f[j]) {
					--count;
					f[j] = true;
				}
			}
		}
		return count;
	}

	public static void main(String[] args) {
		System.out.println(countPrimes(2));
	}

}

package topinterviewquestions;

public class Problem_0204_CountPrimes {

	//you can not use transverse to decide every time whether it is prime or not as it will time exceeding
	public static int countPrimes(int n) {
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

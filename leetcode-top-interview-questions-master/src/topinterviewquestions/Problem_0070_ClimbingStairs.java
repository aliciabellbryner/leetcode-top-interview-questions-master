package topinterviewquestions;

public class Problem_0070_ClimbingStairs {

	//Time complexity : O(n). Single loop upto nn is required to calculate nth fibonacci number
	//Space complexity : O(1). Constant space is used.
	public int climbStairs(int n) {
		if (n == 1) {
			return 1;
		}
		int first = 1;
		int second = 2;
		for (int i = 3; i <= n; i++) {
			int third = first + second;
			first = second;
			second = third;
		}
		return second;
	}

}


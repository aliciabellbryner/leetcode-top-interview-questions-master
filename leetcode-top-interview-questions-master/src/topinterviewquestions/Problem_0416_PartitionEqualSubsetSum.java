package topinterviewquestions;

public class Problem_0416_PartitionEqualSubsetSum {

	public static boolean canPartition(int[] nums) {
		int N = nums.length;
		int sum = 0;
		for (int i = 0; i < N; i++) {
			sum += nums[i];
		}//求出nums总和
		if ((sum & 1) != 0) {
			return false;
		}
		sum >>= 1;
		boolean[][] dp = new boolean[N][sum + 1];//dp[i][j]代表用nums前i+1个数是否可以凑成sum
		for (int i = 0; i < N; i++) {
			dp[i][0] = true;//一个数不用，肯定可以凑成0
		}
		if (nums[0] <= sum) {
			dp[0][nums[0]] = true;
		}
		for (int i = 1; i < N; i++) {
			for (int j = 1; j <= sum; j++) {
				dp[i][j] = dp[i - 1][j];
				if (j - nums[i] >= 0) {
					dp[i][j] |= dp[i - 1][j - nums[i]];//|代表只要至少有一个是true它就是true
				}
			}
			if (dp[i][sum]) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		boolean test = false;
		System.out.println(test | true);
	}

}

package topinterviewquestions;

import java.util.Arrays;

public class Problem_0135_Candy {

	//https://leetcode.com/problems/candy/solution/
	//Approach 3: Using one array
	//Time complexity : O(n). The array candies of size n is traversed thrice.
	//Space complexity : O(n). An array candies of size n is used.
	public int candy(int[] ratings) {
		int N = ratings.length;
		int[] candies = new int[N];
		Arrays.fill(candies, 1);
		for (int i = 1; i < N; i++) {
			if (ratings[i] > ratings[i - 1]) {
				candies[i] = candies[i - 1] + 1;
			}
		}
		int res = candies[N - 1];
		for (int i = ratings.length - 2; i >= 0; i--) {
			if (ratings[i] > ratings[i + 1]) {
				candies[i] = Math.max(candies[i], candies[i + 1] + 1);
			}
			res += candies[i];
		}
		return res;
	}

}

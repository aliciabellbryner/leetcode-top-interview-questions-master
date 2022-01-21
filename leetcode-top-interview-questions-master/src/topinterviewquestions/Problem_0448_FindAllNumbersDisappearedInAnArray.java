package topinterviewquestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Problem_0448_FindAllNumbersDisappearedInAnArray {

	//leetcode solution https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/solution/
	//Approach 2: O(1) Space InPlace Modification Solution
	//time O(N) space O(1)
	public List<Integer> findDisappearedNumbers(int[] nums) {

		// Iterate over each of the elements in the original array
		for (int i = 0; i < nums.length; i++) {

			// Treat the value as the new index
			int newIndex = Math.abs(nums[i]) - 1;

			// Check the magnitude of value at this new index
			// If the magnitude is positive, make it negative
			// thus indicating that the number nums[i] has
			// appeared or has been visited.
			if (nums[newIndex] > 0) {
				nums[newIndex] *= -1;
			}
		}

		// Response array that would contain the missing numbers
		List<Integer> res = new LinkedList<Integer>();

		// Iterate over the numbers from 1 to N and add all those
		// that have positive magnitude in the array
		for (int i = 1; i <= nums.length; i++) {
			if (nums[i - 1] > 0) {
				res.add(i);
			}
		}

		return res;
	}


	//zuo
	public static List<Integer> findDisappearedNumbers2(int[] nums) {
		List<Integer> ans = new ArrayList<>();
		if (nums == null || nums.length == 0) {
			return ans;
		}
		int N = nums.length;
		for (int i = 0; i < N; i++) {
			walk(nums, i);
		}
		for (int i = 0; i < N; i++) {
			if (nums[i] != i + 1) {
				ans.add(i + 1);
			}
		}
		return ans;
	}

	public static void walk(int[] nums, int i) {
		while (nums[i] != i + 1) { // 不断从i发货
			int nexti = nums[i] - 1;
			if (nums[nexti] == nexti + 1) {
				break;
			}
			swap(nums, i, nexti);
		}
	}

	public static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

}

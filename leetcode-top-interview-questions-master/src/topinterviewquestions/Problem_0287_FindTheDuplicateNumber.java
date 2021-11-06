package topinterviewquestions;

public class Problem_0287_FindTheDuplicateNumber {

	public static int findDuplicate(int[] nums) {
		if (nums == null || nums.length < 2) {
			return -1;
		}
		int slow = nums[0];
		int fast = nums[nums[0]];
		while (slow != fast) {
			slow = nums[slow];
			fast = nums[nums[fast]];
		}
		fast = 0;
		while (slow != fast) {
			fast = nums[fast];
			slow = nums[slow];
		}
		return slow;
	}

	//from leetcode solution
	public int findDuplicate_leetcodesolution(int[] nums) {
		while (nums[0] != nums[nums[0]]) {
			int nxt = nums[nums[0]];
			nums[nums[0]] = nums[0];
			nums[0] = nxt;
		}
		return nums[0];
	}

}

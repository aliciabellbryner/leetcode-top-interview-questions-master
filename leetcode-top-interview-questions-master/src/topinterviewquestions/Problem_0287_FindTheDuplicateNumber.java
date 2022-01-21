package topinterviewquestions;

public class Problem_0287_FindTheDuplicateNumber {

	//zuo's solution, using fast and slow pointer,把每个数当成是listnode的指针，就相当于找交点
	//time O(N), space O(1)
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
	//time O(N), space O(1)
	public int findDuplicate_leetcodesolution(int[] nums) {
		while (nums[0] != nums[nums[0]]) {//把nums[nums[0]] 和 nums[0]交换
			int temp = nums[nums[0]];
			nums[nums[0]] = nums[0];
			nums[0] = temp;
		}
		return nums[0];
	}

}

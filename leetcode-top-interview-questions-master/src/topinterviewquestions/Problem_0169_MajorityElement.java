package topinterviewquestions;

public class Problem_0169_MajorityElement {

	//note: The majority element is the element that appears more than ⌊n / 2⌋ times.
	public static int majorityElement(int[] nums) {
		int res = nums[0];
		int HP = 1;
		for (int i = 1; i < nums.length; i++) {
			if (HP == 0) {
				res = nums[i];
				HP = 1;
			} else if (nums[i] == res) {
				HP++;
			} else {
				HP--;
			}
		}
		return res;
	}

	public static void main(String[] args) {
		int[] test = new int[]{1,1,1,2,3,2,3,2};
		System.out.println(majorityElement(test));
	}

}

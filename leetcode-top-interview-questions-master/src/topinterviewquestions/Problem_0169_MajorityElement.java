package topinterviewquestions;

public class Problem_0169_MajorityElement {

	public int majorityElement(int[] nums) {
		int cand = nums[0];
		int HP = 1;
		for (int i = 1; i < nums.length; i++) {
			if (HP == 0) {
				cand = nums[i];
				HP = 1;
			} else if (nums[i] == cand) {
				HP++;
			} else {
				HP--;
			}
		}
		return cand;
	}

}

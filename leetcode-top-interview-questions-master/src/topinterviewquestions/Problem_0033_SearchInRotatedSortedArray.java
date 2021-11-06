package topinterviewquestions;

public class Problem_0033_SearchInRotatedSortedArray {

	//if nums has same values, Zuo's solution, hard to remember, don't use
	public static int search(int[] nums, int target) {
		int l = 0;
		int r = nums.length - 1;
		int m = 0;
		while (l <= r) {
			m = (l + r) / 2;
			if (nums[m] == target) {
				return m;
			}
			// nums[M] != target
			if (nums[l] == nums[m] && nums[m] == nums[r]) {//这一题其实可以不用这一部分，因为题目说了arr中每个元素都不一样
				while (l != m && nums[l] == nums[m]) {
					l++;
				}
				// L和M没撞上，[l]!=[M] l,.....M
				if (l == m) {
					l = m + 1;
					continue;
				}
			}
			// nums[M] != target
			// [l] [M] [r] 不都一样的情况
			if (nums[l] != nums[m]) { //[l] != [M] ,  [M]==[r]
				if (nums[m] > nums[l]) {
					if (target >= nums[l] && target < nums[m]) {
						r = m - 1;
					} else {
						l = m + 1;
					}
				} else { //  [l]  >  [M]
					if (target > nums[m] && target <= nums[r]) {
						l = m + 1;
					} else {
						r = m - 1;
					}
				}
			} else { // [l] === [M] ->  [M]!=[r]
				if (nums[m] < nums[r]) {
					if (target > nums[m] && target <= nums[r]) {
						l = m + 1;
					} else {
						r = m - 1;
					}
				} else {
					if (target >= nums[l] && target < nums[m]) {
						r = m - 1;
					} else {
						l = m + 1;
					}
				}
			}
		}
		return -1;
	}

	//if nums has distinctive values
	public static int search_2(int[] nums, int target) {
		int l = 0;
		int r = nums.length - 1;
		int m = -1;
		while (l <= r) {
			m = l + ((r - l) >> 1);
			if (nums[m] == target) {
				return m;
			}
			if (nums[m] >= nums[l]) {
				if (target >= nums[l] && target < nums[m]) {
					r = m - 1;
				} else {
					l = m + 1;
				}
			} else {
				if (target > nums[m] && target <= nums[r]) {
					l = m + 1;
				} else {
					r = m - 1;
				}
			}
		}
		return -1;
	}

	//if nums has duplicates, just a little bit change in the nums[m] vs nums[l] relations
	public static boolean search_3(int[] nums, int target) {
		int l = 0;
		int r = nums.length - 1;
		int m = -1;
		while (l <= r) {
			m = l + ((r - l) >> 1);
			if (nums[m] == target) {
				return true;
			}
			if (nums[m] > nums[l]) {
				if (target >= nums[l] && target < nums[m]) {
					r = m - 1;
				} else {
					l = m + 1;
				}
			} else if (nums[m] < nums[l]){
				if (target > nums[m] && target <= nums[r]) {
					l = m + 1;
				} else {
					r = m - 1;
				}
			} else {
				l++;
			}
		}
		return false;
	}


}

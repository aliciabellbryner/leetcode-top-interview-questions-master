package topinterviewquestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/*
You are given an inclusive range [lower, upper] and a sorted unique integer array nums, where all elements are in the inclusive range.

A number x is considered missing if x is in the range [lower, upper] and x is not in nums.

Return the smallest sorted list of ranges that cover every missing number exactly. That is, no element of nums is in any of the ranges, and each missing number is in one of the ranges.

Each range [a,b] in the list should be output as:

"a->b" if a != b
"a" if a == b


Example 1:

Input: nums = [0,1,3,50,75], lower = 0, upper = 99
Output: ["2","4->49","51->74","76->99"]
Explanation: The ranges are:
[2,2] --> "2"
[4,49] --> "4->49"
[51,74] --> "51->74"
[76,99] --> "76->99"
Example 2:

Input: nums = [-1], lower = -1, upper = -1
Output: []
Explanation: There are no missing ranges since there are no missing numbers.


Constraints:

-109 <= lower <= upper <= 109
0 <= nums.length <= 100
lower <= nums[i] <= upper
All the values of nums are unique.
 */
public class Problem_0163_MissingRanges {

	//my solution, better miss function than Zuo using stringbuilder
	public List<String> findMissingRanges_j(int[] nums, int lower, int upper) {
		List<String> res = new LinkedList<>();
		if (lower > upper) {
			return res;
		}
		for (int num : nums) {
			if (num > lower) {
				res.add(miss(lower, num-1));
			}
			if (num == upper) {
				return res;
			}
			lower = num + 1;
		}
		if (lower <= upper) {
			res.add(miss(lower, upper));
		}
		return res;
	}
	public String miss(int lower, int upper) {
		StringBuilder sb = new StringBuilder();
		sb.append(lower);
		if (upper > lower) {
			sb.append("->"+upper);
		}
		return sb.toString();
	}

// zuo's solution, not good miss func
//	public static List<String> findMissingRanges(int[] nums, int lower, int upper) {
//		List<String> ans = new ArrayList<>();
//		for (int num : nums) {
//			if (num > lower) {
//				ans.add(miss(lower, num - 1));
//			}
//			if (num == upper) {
//				return ans;
//			}
//			lower = num + 1;
//		}
//		if (lower <= upper) {
//			ans.add(miss(lower, upper));
//		}
//		return ans;
//	}
//
//	// ??????"lower->upper"?????????????????????lower==upper???????????????"lower"
//	public static String miss(int lower, int upper) {
//		String left = String.valueOf(lower);
//		String right = "";
//		if (upper > lower) {
//			right = "->" + String.valueOf(upper);
//		}
//		return left + right;
//	}

}

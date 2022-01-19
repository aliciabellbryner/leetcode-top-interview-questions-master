package topinterviewquestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
//	// 生成"lower->upper"的字符串，如果lower==upper，只用生成"lower"
//	public static String miss(int lower, int upper) {
//		String left = String.valueOf(lower);
//		String right = "";
//		if (upper > lower) {
//			right = "->" + String.valueOf(upper);
//		}
//		return left + right;
//	}

}

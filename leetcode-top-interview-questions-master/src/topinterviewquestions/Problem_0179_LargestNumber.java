package topinterviewquestions;

import java.util.Arrays;
import java.util.Comparator;

public class Problem_0179_LargestNumber {

	public String largestNumber(int[] nums) {
		String[] strs = new String[nums.length];
		for (int i = 0; i < nums.length; i++) {
			strs[i] = String.valueOf(nums[i]);
		}
		Arrays.sort(strs, (s1, s2) -> (s2 + s1).compareTo(s1 + s2));////这样排出来的大的在前面
		StringBuilder sb = new StringBuilder();
		for (String str : strs) {
			sb.append(str);
		}
		String res = sb.toString();
		int startIdx = -1;//后面的目的是为了排除如果全都是0的情况
		for (int i = 0; i < res.length(); i++) {
			if (res.charAt(i) != '0') {
				startIdx = i;
				break;
			}
		}
		return startIdx == -1 ? "0" : res.substring(startIdx);
	}

	public static void main(String[] args) {
		System.out.println("abc".compareTo("abd"));
	}
}

package topinterviewquestions;

import java.util.Arrays;
import java.util.Comparator;

public class Problem_0179_LargestNumber {

	public static class MyComparator implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {
			return (o2 + o1).compareTo(o1 + o2);
		}//这样排出来的大的在前面

	}

	public String largestNumber(int[] nums) {
		String[] strs = new String[nums.length];
		for (int i = 0; i < nums.length; i++) {
			strs[i] = String.valueOf(nums[i]);
		}
		Arrays.sort(strs, new MyComparator());
		StringBuilder builder = new StringBuilder();
		for (String str : strs) {
			builder.append(str);
		}
		String ans = builder.toString();
		char[] str = ans.toCharArray();
		int index = -1;//后面的目的是为了排除如果全都是0的情况
		for (int i = 0; i < str.length; i++) {
			if (str[i] != '0') {
				index = i;
				break;
			}
		}
		return index == -1 ? "0" : ans.substring(index);
	}

	public static void main(String[] args) {
		System.out.println("abc".compareTo("abd"));
	}
}

package topinterviewquestions;

public class Problem_0003_LongestSubstringWithoutRepeatingCharacters {

	public static int lengthOfLongestSubstring(String s) {
		if (s == null || s.equals("")) {
			return 0;
		}
		// map (a, ?) (b, ?)
		// a, 17
		// map[97] = 17
		int[] map = new int[256];//存放出现的char存放的当前最后一个位置
		//必须要全部变成-1,如果是0的话只有一个元素的s就会出现计算结果0
		for (int i = 0; i < 256; i++) {
			map[i] = -1;
		}
		// 收集答案
		int res = 0;
		int pre = -1; // i-1位置结尾的情况下，往左推，推不动的位置是谁
		for (int i = 0; i < s.length(); i++) {
			// i位置结尾的情况下，往左推，推不动的位置是谁
			// pre (i-1信息) -> pre(i 结尾信息)
			pre = Math.max(pre, map[s.charAt(i)]);
			res = Math.max(res, i-pre);
			map[s.charAt(i)] = i;
		}
		return res;
	}

	public static void main(String[] args) {
		System.out.println(lengthOfLongestSubstring("H"));
	}
}

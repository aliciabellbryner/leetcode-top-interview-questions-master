package topinterviewquestions;

public class Problem_0340_LongestSubstringWithAtMostKDistinctCharacters {

	public static int lengthOfLongestSubstringKDistinct(String s, int k) {
		if (s == null || s.length() == 0 || k < 1) {
			return 0;
		}
		int N = s.length();
		int[] count = new int[256];
		int diff = 0;
		int R = 0;
		int res = 0;
		for (int L = 0; L < N; L++) {
			// R 窗口的右边界
			while (R < N && (diff < k || (diff == k && count[s.charAt(R)] > 0))) {
				diff += count[s.charAt(R)] == 0 ? 1 : 0;
				count[s.charAt(R++)]++;
			}
			// R 来到违规的第一个位置
			res = Math.max(res, R - L);
			diff -= count[s.charAt(L)] == 1 ? 1 : 0;//因为L马上要右移了，如果它是唯一的一个元素，右移后不同letter总数就要减1
			count[s.charAt(L)]--;
		}
		return res;
	}

}

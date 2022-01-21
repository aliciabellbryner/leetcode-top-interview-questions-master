package topinterviewquestions;

public class Problem_0395_LongestSubstringWithAtLeastKRepeatingCharacters {


	//https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/discuss/87739/Java-Strict-O(N)-Two-Pointer-Solution/92538

	public int longestSubstring(String s, int k) {
		int d = 0;

		for (int numUniqueTarget = 1; numUniqueTarget <= 26; numUniqueTarget++)
			d = Math.max(d, longestSubstringWithNUniqueChars(s, k, numUniqueTarget));

		return d;
	}

	private int longestSubstringWithNUniqueChars(String s, int k, int numUniqueTarget) {
		int[] map = new int[128];
		int numUnique = 0; // counter 1
		int numNoLessThanK = 0; // counter 2
		int begin = 0, end = 0;
		int d = 0;

		while (end < s.length()) {
			if (map[s.charAt(end)]++ == 0) numUnique++; // increment map[c] after this statement
			if (map[s.charAt(end++)] == k) numNoLessThanK++; // inc end after this statement

			while (numUnique > numUniqueTarget) {
				if (map[s.charAt(begin)]-- == k) numNoLessThanK--; // decrement map[c] after this statement
				if (map[s.charAt(begin++)] == 0) numUnique--; // inc begin after this statement
			}

			// if we found a string where the number of unique chars equals our target
			// and all those chars are repeated at least K times then update max
			if (numUnique == numUniqueTarget && numUnique == numNoLessThanK)
				d = Math.max(end - begin, d);
		}

		return d;
	}



	//用这个方法，time O（N）
	public static int longestSubstring2(String s, int k) {
		char[] str = s.toCharArray();
		int N = str.length;
		int max = 0;
		for (int require = 1; require <= 26; require++) {
			// a~z  a~z 出现次数
			// count[0  1  2]  a b c 
			int[] count = new int[26];
			// 目前窗口内收集了几种字符了
			int collect = 0;
			// 目前窗口内出现次数>=k次的字符，满足了几种
			int satisfy = 0;
			// 窗口右边界
			int R = -1;
			for (int L = 0; L < N; L++) { // L要尝试每一个窗口的最左位置
				// [L..R]  R+1
				while (R + 1 < N && !(collect == require && count[str[R + 1] - 'a'] == 0)) {
					R++;
					if (count[str[R] - 'a'] == 0) {
						collect++;
					}
					if (count[str[R] - 'a'] == k - 1) {
						satisfy++;
					}
					count[str[R] - 'a']++;
				}
				// [L...R]
				if (satisfy == require) {
					max = Math.max(max, R - L + 1);
				}
				// L++
				if (count[str[L] - 'a'] == 1) {
					collect--;
				}
				if (count[str[L] - 'a'] == k) {
					satisfy--;
				}
				count[str[L] - 'a']--;
			}
		}
		return max;
	}
	//168ms太慢了，time = O(N^2)用下面的方法
	public static int longestSubstring1(String s, int k) {
		char[] str = s.toCharArray();
		int N = str.length;
		int max = 0;
		for (int i = 0; i < N; i++) {
			int[] count = new int[256];
			int collect = 0;
			int satisfy = 0;
			for (int j = i; j < N; j++) {
				if (count[str[j]] == 0) {
					collect++;
				}
				count[str[j]]++;
				if (count[str[j]] == k) {
					satisfy++;
				}
				if (collect == satisfy) {
					max = Math.max(max, j - i + 1);
				}
			}
		}
		return max;
	}


	// 会超时，但是思路的确是正确的
	public static int longestSubstring3(String s, int k) {
		return process(s.toCharArray(), 0, s.length() - 1, k);
	}

	public static int process(char[] str, int L, int R, int k) {
		if (L > R) {
			return 0;
		}
		int[] counts = new int[26];
		for (int i = L; i <= R; i++) {
			counts[str[i] - 'a']++;
		}
		char few = 0;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < 26; i++) {
			if (counts[i] != 0 && min > counts[i]) {
				few = (char) (i + 'a');
				min = counts[i];
			}
		}
		if (min >= k) {
			return R - L + 1;
		}
		int pre = 0;
		int max = Integer.MIN_VALUE;
		for (int i = L; i <= R; i++) {
			if (str[i] == few) {
				max = Math.max(max, process(str, pre, i - 1, k));
				pre = i + 1;
			}
		}
		if (pre != R + 1) {
			max = Math.max(max, process(str, pre, R, k));
		}
		return max;
	}

}

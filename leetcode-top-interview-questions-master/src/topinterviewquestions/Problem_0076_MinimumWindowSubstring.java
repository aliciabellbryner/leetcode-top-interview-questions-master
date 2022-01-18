package topinterviewquestions;

public class Problem_0076_MinimumWindowSubstring {

	//Time Complexity: O(∣S∣+∣T∣) where |S| and |T| represent the lengths of strings S and T. In the worst case we might end up visiting every element of string S twice, once by left pointer and once by right pointer. ∣T∣ represents the length of string T.
	//Space Complexity: O(∣S∣+∣T∣). ∣S∣ when the window size is equal to the entire string S. ∣T∣ when T has all unique characters.
	public static String minWindow(String s, String t) {
		if (s.length() < t.length()) {
			return "";
		}
		int[] map = new int[256];
		for (char cha : t.toCharArray()) {
			map[cha]++;
		}
		int all = t.length();
		int L = 0;
		int R = 0;
		// -1(从来没找到过合法的)
		int minLen = -1;
		int ansl = -1;//the ans's left idx
		int ansr = -1;//the ans's right idx
		// [L..R)   [0,0)  R
		while (R != s.length()) {
			map[s.charAt(R)]--;
			if (map[s.charAt(R)] >= 0) {//说明是有效还款
				all--;
			}
			if (all == 0) {//说明还完了
				while (map[s.charAt(L)] < 0) {//如果减的太多需要把他们还回来，比如s=aaabc, t=abc, 那么R走到c的位置的时候map[a]是小于0的，所以要把L右移使得a的出现次数等于t中的次数
					map[s.charAt(L++)]++;
				}
				if (minLen == -1 || minLen > R - L + 1) {//minLen == -1说明第一次出现符合要求的，minLen > R - L + 说明出现更短的符合要求的，所以要更新
					minLen = R - L + 1;
					ansl = L;
					ansr = R;
				}
				all++;//左边界继续往右边扩，这个目前是把L上的有效还款撤销
				map[s.charAt(L++)]++;
			}
			R++;
		}
		return minLen == -1 ? "" : s.substring(ansl, ansr + 1);
	}

}

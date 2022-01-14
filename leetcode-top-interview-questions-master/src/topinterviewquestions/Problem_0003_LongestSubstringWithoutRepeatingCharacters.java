package topinterviewquestions;

public class Problem_0003_LongestSubstringWithoutRepeatingCharacters {

	//time O(2N) = O(N) space O(min(m, n)), m is the size of the String s, and n is the size of the charset/alphabet m
	public int lengthOfLongestSubstring(String s) {
		int[] cnt = new int[256];
		int res = 0;
		int l = 0;
		int r = 0;
		for (char c : s.toCharArray()) {
			cnt[c]++;
			r++;
			while (cnt[c] > 1) {
				cnt[s.charAt(l)]--;
				l++;
			}
			res = Math.max(res, r-l);
		}
		return res;
	}

	 public int lengthOfLongestSubstring2(String s) {
	     int[] cnts = new int[256];
	     int l = 0;
	     int r = 0;
	     int res = 0;
	     while (r < s.length()) {
	         char right = s.charAt(r);
	         cnts[right]++;
	         while (cnts[right] > 1) {
	             cnts[s.charAt(l)]--;
	             l++;
	         }
	         res = Math.max(res, r - l + 1);
	         r++;
	     }
	     return res;
	 }
}

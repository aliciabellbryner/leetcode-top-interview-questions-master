package topinterviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Problem_0438_FindAllAnagramsInAString {


	//https://leetcode.com/problems/find-all-anagrams-in-a-string/solution/
	//Approach 2: Sliding Window with Array
	//Time complexity: O(N_s + N_p)
	// since it's one pass along both strings.
	//Space complexity: O(1), because pCount and sCount contain 26 elements each.
	public List<Integer> findAnagrams(String s, String p) {
		int ns = s.length(), np = p.length();
		if (ns < np) return new ArrayList();

		int [] p_cnt = new int[26];
		int [] s_cnt = new int[26];
		// build reference array using string p
		for (char ch : p.toCharArray()) {
			p_cnt[(int)(ch - 'a')]++;
		}

		List<Integer> res = new ArrayList();
		// sliding window on the string s
		for (int i = 0; i < ns; ++i) {
			// add one more letter
			// on the right side of the window
			s_cnt[(int)(s.charAt(i) - 'a')]++;
			// remove one letter
			// from the left side of the window
			if (i >= np) {
				s_cnt[(int)(s.charAt(i - np) - 'a')]--;
			}
			// compare array in the sliding window
			// with the reference array
			if (Arrays.equals(p_cnt, s_cnt)) {
				res.add(i - np + 1);
			}
		}
		return res;
	}

	//zuo
	public static List<Integer> findAnagrams2(String s, String p) {
		List<Integer> ans = new ArrayList<>();
		if (s == null || p == null || s.length() < p.length()) {
			return ans;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		char[] pst = p.toCharArray();
		int M = pst.length;
		HashMap<Character, Integer> map = new HashMap<>();
		for (char cha : pst) {
			if (!map.containsKey(cha)) {
				map.put(cha, 1);
			} else {
				map.put(cha, map.get(cha) + 1);
			}
		}
		int all = M;
		for (int end = 0; end < M - 1; end++) {
			if (map.containsKey(str[end])) {
				int count = map.get(str[end]);
				if (count > 0) {
					all--;
				}
				map.put(str[end], count - 1);
			}
		}
		for (int end = M - 1, start = 0; end < N; end++, start++) {
			if (map.containsKey(str[end])) {
				int count = map.get(str[end]);
				if (count > 0) {
					all--;
				}
				map.put(str[end], count - 1);
			}
			if (all == 0) {
				ans.add(start);
			}
			if (map.containsKey(str[start])) {
				int count = map.get(str[start]);
				if (count >= 0) {
					all++;
				}
				map.put(str[start], count + 1);
			}
		}
		return ans;
	}

}

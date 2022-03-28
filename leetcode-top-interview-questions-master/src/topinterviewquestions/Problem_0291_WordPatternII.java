package topinterviewquestions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//Given a pattern and a string str, find if str follows the same pattern.
//
//Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty substring in str.
//
//Examples:
//
//pattern = "abab", str = "redblueredblue" should return true. pattern = "aaaa", str = "asdasdasdasd" should return true. pattern = "aabb", str = "xyzabcxzyabc" should return false.
//
//Notes:
//
//You may assume both pattern and str contains only lowercase letters.

public class Problem_0291_WordPatternII {

	//https://blog.baozitraining.org/2019/07/leetcode-solution-291-word-pattern-ii.html
	class Solution {
		public boolean wordPatternMatch(String pattern, String str) {
			if (pattern == null || str == null) {
				return false;
			}

			Map<Character, String> lookup = new HashMap<>();
			Set<String> dup = new HashSet<>();

			return this.isMatch(pattern, str, lookup, dup);
		}

		// DFS recursion to list out all the possibilities
		public boolean isMatch(String pattern, String str, Map<Character, String> lookup, Set<String> dup) {
			if (pattern.length() == 0) {
				return str.length() == 0;
			}

			char c = pattern.charAt(0);

			if (lookup.containsKey(c)) {
				String mappedString = lookup.get(c);
				if (mappedString.length() > str.length()) {
					return false;
				}

				// could use str.startsWith(mappedString)
				if (!mappedString.equals(str.substring(0, mappedString.length()))) {
					return false;
				}

				return this.isMatch(pattern.substring(1), str.substring(mappedString.length()), lookup, dup);

			} else {
				for (int i = 1; i <= str.length(); i++) {
					String mappingString = str.substring(0, i);
					if (dup.contains(mappingString)) {
						// not a bijection mapping, not not return false, but continue
						continue;
					}

					lookup.put(c, mappingString);
					dup.add(mappingString);
					if (this.isMatch(pattern.substring(1), str.substring(i), lookup, dup)) {
						return true;
					}
					// reset value for next recursion iteration for backtracking
					lookup.remove(c);
					dup.remove(mappingString);
				}
			}

			return false;
		}
	}

	//
	public class Solution1 {
		Map<Character, String> map = new HashMap();
		Set<String> set = new HashSet();

		public boolean wordPatternMatch(String pattern, String str) {
			if (pattern.isEmpty()) {
				return str.isEmpty();
			}
			if (map.containsKey(pattern.charAt(0))) {
				String value = map.get(pattern.charAt(0));
				if (str.length() < value.length() || !str.substring(0, value.length()).equals(value)) {
					return false;
				}
				if (wordPatternMatch(pattern.substring(1), str.substring(value.length()))) {
					return true;
				}
			} else {
				for (int i = 1; i <= str.length(); i++) {
					if (set.contains(str.substring(0, i))) {
						continue;
					}
					map.put(pattern.charAt(0), str.substring(0, i));
					set.add(str.substring(0, i));
					if (wordPatternMatch(pattern.substring(1), str.substring(i))) {
						return true;
					}
					set.remove(str.substring(0, i));
					map.remove(pattern.charAt(0));
				}
			}
			return false;
		}

	}




	public class Solution2 {
		public boolean wordPatternMatch(String pattern, String str) {
			if (str == null || str.length() == 0) {
				return pattern == null || pattern.length() == 0;
			}

			if (pattern == null || pattern.length() == 0) {
				return str == null || str.length() == 0;
			}

			int len = pattern.length();

			Map<String, Character> map = new HashMap<>();
			Map<Character, String> invertedMap = new HashMap<>();

			return wordPatternMatchHelper(0, 0, pattern, str, map, invertedMap);
		}

		private boolean wordPatternMatchHelper(int start, int seg, String pattern,
											   String str,
											   Map<String, Character> map,
											   Map<Character, String> invertedMap) {
			if (start == str.length() && seg == pattern.length()) {
				return true;
			}

			if (start >= str.length() || seg >= pattern.length()) {
				return false;
			}

			char c = pattern.charAt(seg);
			for (int i = start; i < str.length(); i++) {
				String substr = str.substring(start, i + 1);

				if (map.containsKey(substr) &&
						invertedMap.containsKey(c) &&
						map.get(substr) == c &&
						invertedMap.get(c).equals(substr)) {
					if (wordPatternMatchHelper(i + 1, seg + 1, pattern,
							str, map, invertedMap)) {
						return true;
					}
				}

				if (!map.containsKey(substr) && !invertedMap.containsKey(c)) {
					map.put(substr, c);
					invertedMap.put(c, substr);

					if (wordPatternMatchHelper(i + 1, seg + 1, pattern,
							str, map, invertedMap)) {
						return true;
					}

					map.remove(substr);
					invertedMap.remove(c);
				}
			}

			return false;
		}
	}

}

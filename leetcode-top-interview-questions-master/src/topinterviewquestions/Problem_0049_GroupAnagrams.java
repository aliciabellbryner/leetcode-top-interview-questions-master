package topinterviewquestions;

import java.util.*;

public class Problem_0049_GroupAnagrams {


	//time O(nm),n is the total number of words and m is the length of each word.
	public static List<List<String>> groupAnagrams(String[] strs) {
		if (strs == null || strs.length == 0) return new ArrayList<>();
		Map<String, List<String>> map = new HashMap<>();
		for (String s : strs) {
			char[] ca = new char[26];
			//char array to String is really fast than int[]
			//why not use int[] to store the freq? https://leetcode.com/problems/group-anagrams/discuss/19176/Share-my-short-JAVA-solution/241458
			for (char c : s.toCharArray()) ca[c - 'a']++;
			String keyStr = String.valueOf(ca);
			if (!map.containsKey(keyStr)) map.put(keyStr, new ArrayList<>());
			map.get(keyStr).add(s);
		}
		return new ArrayList<>(map.values());
	}


	//time O(nmlogm),n is the total number of words and m is the length of each word.
	public static List<List<String>> groupAnagrams1(String[] strs) {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		for (String str : strs) {
			char[] chs = str.toCharArray();
			Arrays.sort(chs);
			String key = String.valueOf(chs);
			if (!map.containsKey(key)) {
				map.put(key, new ArrayList<String>());
			}
			map.get(key).add(str);
		}
		List<List<String>> res = new ArrayList<List<String>>();
		for (List<String> list : map.values()) {
			res.add(list);
		}
		return res;
	}

	public static List<List<String>> groupAnagrams2(String[] strs) {
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		for (String str : strs) {
			int[] record = new int[26];
			for (char cha : str.toCharArray()) {
				record[cha - 'a']++;
			}
			StringBuilder builder = new StringBuilder();
			for (int value : record) {
				builder.append(String.valueOf(value)).append("_");
			}
			String key = builder.toString();
			if (!map.containsKey(key)) {
				map.put(key, new ArrayList<String>());
			}
			map.get(key).add(str);
		}
		List<List<String>> res = new ArrayList<List<String>>();
		for (List<String> list : map.values()) {
			res.add(list);
		}
		return res;
	}


	public static void main(String[] args) {
		String[] strs = new String[]{"aa","teta","tan","ate","nat","bat"};
//		System.out.println(groupAnagrams(strs));
		char[] ca = new char[3];
		ca[0]++;
		ca[0]++;
		ca[0]++;
		System.out.println(ca[0]);
	}

}

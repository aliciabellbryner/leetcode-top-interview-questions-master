package topinterviewquestions;

import java.util.HashMap;
import java.util.HashSet;

public class Problem_0128_LongestConsecutiveSequence {


	//time O(N), space O(N)
	//利用hashset里的数字不论放置的顺序如何，如果你调用for (int i : set)它都会从小到大返回出来
	public static int longestConsecutive_leetcode(int[] nums) {
		HashSet<Integer> set = new HashSet<>();
		for (int i : nums) {
			set.add(i);
		}
		int res = 0;
		int curLink;
		for (int i : set) {
			if (!set.contains(i - 1)) {
				curLink = 1;
				while (set.contains(i + 1)) {
					curLink++;
					i++;
				}
				res = Math.max(res, curLink);
			}
		}
		return res;
	}


	public static int longestConsecutive(int[] nums) {
		HashMap<Integer, Integer> map = new HashMap<>();
		int len = 0;
		for (int num : nums) {
			if (!map.containsKey(num)) {
				map.put(num, 1);
				int preLen = map.containsKey(num - 1) ? map.get(num - 1) : 0;
				int posLen = map.containsKey(num + 1) ? map.get(num + 1) : 0;
				int all = preLen + posLen + 1;
				map.put(num - preLen, all);
				map.put(num + posLen, all);
				len = Math.max(len, all);
			}
		}
		return len;
	}

	public static void main(String[] args) {
		HashSet<Integer> set = new HashSet<>();
		set.add(9);
		set.add(2);
		set.add(12);
		set.add(8);
		for (int i : set) {
			System.out.println(i);
		}

		int[] test = new int[]{100,4,200,1,3,2};
		System.out.println(longestConsecutive_leetcode(test));
	}

}

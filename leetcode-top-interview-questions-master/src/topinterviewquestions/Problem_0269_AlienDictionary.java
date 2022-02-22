package topinterviewquestions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
/*
There is a new alien language that uses the English alphabet. However, the order among the letters is unknown to you.

You are given a list of strings words from the alien language's dictionary, where the strings in words are sorted lexicographically by the rules of this new language.

Return a string of the unique letters in the new alien language sorted in lexicographically increasing order by the new language's rules. If there is no solution, return "". If there are multiple solutions, return any of them.

A string s is lexicographically smaller than a string t if at the first letter where they differ, the letter in s comes before the letter in t in the alien language. If the first min(s.length, t.length) letters are the same, then s is smaller if and only if s.length < t.length.



Example 1:

Input: words = ["wrt","wrf","er","ett","rftt"]
Output: "wertf"
Example 2:

Input: words = ["z","x"]
Output: "zx"
Example 3:

Input: words = ["z","x","z"]
Output: ""
Explanation: The order is invalid, so return "".


Constraints:

1 <= words.length <= 100
1 <= words[i].length <= 100
words[i] consists of only lowercase English letters.
 */

public class Problem_0269_AlienDictionary {

	//time and space:https://leetcode.com/problems/alien-dictionary/solution/
	//Time complexity : O(C).  C be the total length of all the words in the input list, added together.
	//Space complexity : O(1) or O(U + min(U^2, N)),  U is the total number of unique letters in the alien alphabet. N is the total number of strings in the input list.
	public static String alienOrder(String[] words) {
		if (words == null || words.length == 0) {
			return "";
		}
		int N = words.length;
		HashMap<Character, Integer> indegree = new HashMap<>();//key是letter，value是入度多少
		for (int i = 0; i < N; i++) {//先把所有的words里出现过的letter（注意不是word）都放进hashmap里，并且indegree设为0
			for (char c : words[i].toCharArray()) {
				indegree.put(c, 0);
			}
		}
		HashMap<Character, HashSet<Character>> nextsMap = new HashMap<>();
		//这个hashmap 的key是每个字母，value是排在这个字母后面的所有字母的set集合
		for (int i = 0; i < N - 1; i++) {
			String cur = words[i];
			String next = words[i + 1];
			int len = Math.min(cur.length(), next.length());
			int j = 0;
			for (; j < len; j++) {
				if (cur.charAt(j) != next.charAt(j)) {
					if (!nextsMap.containsKey(cur.charAt(j))) {
						nextsMap.put(cur.charAt(j), new HashSet<>());
					}
					if (!nextsMap.get(cur.charAt(j)).contains(next.charAt(j))) {//避免重复计算，比如两次b->a就不能把a的入度加成2，只有两次不同的比如c->a和d->a两次才可以加成2
						nextsMap.get(cur.charAt(j)).add(next.charAt(j));
						indegree.put(next.charAt(j), indegree.get(next.charAt(j)) + 1);
					}
					break;//如果找到cur.charAt(j) != next.charAt(j)说明没有意义再比较目前的cur和next下面的值了，所以break
				}
			}
			if (j < cur.length() && j == next.length()) {//说明cur的长度比next长，同时next里所有出现的letter都出现在cur相同位置，
				// 这说明肯定不符合尝试，比如cur = loveeee, next = love,所以直接返回“”
				return "";
			}
		}
		//接下来就是topology sort的基本思路了
		StringBuilder ans = new StringBuilder();
		Queue<Character> q = new LinkedList<>();
		for (Character key : indegree.keySet()) {
			if (indegree.get(key) == 0) {
				q.offer(key);
			}
		}
		while (!q.isEmpty()) {
			char cur = q.poll();
			ans.append(cur);
			if (nextsMap.containsKey(cur)) {
				for (char next : nextsMap.get(cur)) {
					indegree.put(next, indegree.get(next) - 1);
					if (indegree.get(next) == 0) {
						q.offer(next);
					}
				}
			}
		}
		return ans.length() == indegree.size() ? ans.toString() : "";
		//这最后一步非常关键也非常难理解，这是考虑到了如果有环的情况比如a->b->c->d->b，bcd构成了一个环，则这个时候indegree则是a:0, b:2, c:1, c:1,
		// 第一步line46-48可以将a放到q去，进而通过line53放到ans里，但是后面b减1变成1，c和d都是1，那后面if (indegree.get(next) == 0)一直false，ans里也就无法加bcd的值。
		// 所以ans的length肯定小于indegree.size()
	}

}

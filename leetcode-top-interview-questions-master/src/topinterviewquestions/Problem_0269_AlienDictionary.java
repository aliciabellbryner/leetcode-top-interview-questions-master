package topinterviewquestions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Problem_0269_AlienDictionary {

	public static String alienOrder(String[] words) {
		if (words == null || words.length == 0) {
			return "";
		}
		int N = words.length;
		HashMap<Character, Integer> indegree = new HashMap<>();//入度多少，可以参考Code03_TopologySort
		for (int i = 0; i < N; i++) {
			for (char c : words[i].toCharArray()) {
				indegree.put(c, 0);
			}
		}
		HashMap<Character, HashSet<Character>> graph = new HashMap<>();
		//这个hashmap 的key是每个字母，value是这个字母后面的所有字母的set集合
		for (int i = 0; i < N - 1; i++) {
			char[] cur = words[i].toCharArray();
			char[] nex = words[i + 1].toCharArray();
			int len = Math.min(cur.length, nex.length);
			int j = 0;
			for (; j < len; j++) {
				if (cur[j] != nex[j]) {
					if (!graph.containsKey(cur[j])) {
						graph.put(cur[j], new HashSet<>());
					}
					if (!graph.get(cur[j]).contains(nex[j])) {//避免重复计算，比如两次b->a就不能把a的入度加成2，只有两次不同的比如c->a和d->a两次才可以加成2
						graph.get(cur[j]).add(nex[j]);
						indegree.put(nex[j], indegree.get(nex[j]) + 1);
					}
					break;
				}
			}
			if (j < cur.length && j == nex.length) {
				return "";
			}
		}
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
			if (graph.containsKey(cur)) {
				for (char next : graph.get(cur)) {
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

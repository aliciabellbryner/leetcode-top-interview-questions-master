package topinterviewquestions;

import java.util.*;

public class Problem_0140_WordBreakII {


	//https://leetcode.com/problems/word-break-ii/discuss/44167/My-concise-JAVA-solution-based-on-memorized-DFS/808237
	//Time Complexity: O(N ^ 2 + 2^N + W), Let N be the length of the input string and W be the number of words in the dictionary.
	//Space Complexity: O(2 ^ N * N + W)
	int maxLen = 0;//这个的目的是为了减少for循环的长度
	public List<String> wordBreak(String s, List<String> wordDict) {
		Set<String> set = new HashSet<>(wordDict);
		for(String str: wordDict){
			if(str.length() > maxLen) maxLen = str.length();
		}
		HashMap<Integer,List<String>> map = new HashMap<>();
		return backtrack(s,set,0, map);
	}
	private List<String> backtrack(String s, Set<String> set,int start, HashMap<Integer,List<String>> map){
		if(map.containsKey(start)) {//hashmap的key是代表从s的[start, end]所有的chars中可以用dict中的word组成的list
			return map.get(start);
		}
		if(start == s.length()){
			List<String> list = new LinkedList<>();
			list.add("");
			map.put(start,list);
			return list;
		}
		List<String> list = new LinkedList<>();
		for(int i = start;i<Math.min(s.length(),start+maxLen);i++){
			String tempStr = s.substring(start,i+1);
			if(set.contains(tempStr)){
				for(String str: backtrack(s,set,i+1,map)){
					if(str.length() == 0){
						list.add(tempStr);
					}else list.add(tempStr+" "+str);
				}
			}
		}
		map.put(start,list);
		return list;
	}



	//https://leetcode.com/problems/word-break-ii/discuss/44167/My-concise-JAVA-solution-based-on-memorized-DFS
	//simpler solution used HashMap to save the previous results to prune duplicated branches
	//Time complexity is O(len(wordDict) ^ len(s / minWordLenInDict)), because there're len(wordDict) possibilities for each cut
	public List<String> wordBreak1(String s, List<String> wordDict) {
		return DFS(s, wordDict, new HashMap<String, LinkedList<String>>());
	}

	// DFS function returns an array including all substrings derived from s.
	List<String> DFS(String s, List<String> wordDict, HashMap<String, LinkedList<String>>map) {
		if (map.containsKey(s))
			return map.get(s);

		LinkedList<String>res = new LinkedList<String>();
		if (s.length() == 0) {
			res.add("");
			return res;
		}
		for (String word : wordDict) {
			if (s.startsWith(word)) {
				List<String> sublist = DFS(s.substring(word.length()), wordDict, map);
				for (String sub : sublist)
					res.add(word + (sub.isEmpty() ? "" : " ") + sub);
			}
		}
		map.put(s, res);
		return res;
	}

	public static class Node {
		public String path;
		public boolean end;
		public Node[] nexts;

		public Node() {
			path = null;
			end = false;
			nexts = new Node[26];
		}
	}

	public static List<String> wordBreak2(String s, List<String> wordDict) {
		char[] str = s.toCharArray();
		Node root = gettrie(wordDict);
		boolean[] dp = getdp(s, root);
		ArrayList<String> path = new ArrayList<>();
		List<String> ans = new ArrayList<>();
		process(str, 0, root, dp, path, ans);
		return ans;
	}

	public static void process(char[] str, int index, Node root, boolean[] dp, ArrayList<String> path,
			List<String> ans) {
		if (index == str.length) {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < path.size() - 1; i++) {
				builder.append(path.get(i) + " ");
			}
			builder.append(path.get(path.size() - 1));
			ans.add(builder.toString());
		} else {
			Node cur = root;
			for (int end = index; end < str.length; end++) {
				int road = str[end] - 'a';
				if (cur.nexts[road] == null) {
					break;
				}
				cur = cur.nexts[road];
				if (cur.end && dp[end + 1]) {
					path.add(cur.path);
					process(str, end + 1, root, dp, path, ans);
					path.remove(path.size() - 1);
				}
			}
		}
	}

	public static Node gettrie(List<String> wordDict) {
		Node root = new Node();
		for (String str : wordDict) {
			char[] chs = str.toCharArray();
			Node node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					node.nexts[index] = new Node();
				}
				node = node.nexts[index];
			}
			node.path = str;
			node.end = true;
		}
		return root;
	}

	public static boolean[] getdp(String s, Node root) {
		char[] str = s.toCharArray();
		int N = str.length;
		boolean[] dp = new boolean[N + 1];
		dp[N] = true;
		for (int i = N - 1; i >= 0; i--) {
			Node cur = root;
			for (int end = i; end < N; end++) {
				int path = str[end] - 'a';
				if (cur.nexts[path] == null) {
					break;
				}
				cur = cur.nexts[path];
				if (cur.end && dp[end + 1]) {
					dp[i] = true;
					break;
				}
			}
		}
		return dp;
	}

}

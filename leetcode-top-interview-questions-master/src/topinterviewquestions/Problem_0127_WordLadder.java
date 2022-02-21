package topinterviewquestions;

import java.util.*;
/*
A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:

Every adjacent pair of words differs by a single letter.
Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
sk == endWord
Given two words, beginWord and endWord, and a dictionary wordList, return the number of words in the shortest transformation sequence from beginWord to endWord, or 0 if no such sequence exists.



Example 1:

Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
Output: 5
Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot" -> "dog" -> cog", which is 5 words long.
Example 2:

Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
Output: 0
Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.


Constraints:

1 <= beginWord.length <= 10
endWord.length == beginWord.length
1 <= wordList.length <= 5000
wordList[i].length == beginWord.length
beginWord, endWord, and wordList[i] consist of lowercase English letters.
beginWord != endWord
All the words in wordList are unique.
 */
public class Problem_0127_WordLadder {


	//https://leetcode.com/problems/word-ladder/discuss/40707/C++-BFS/1095951
	//For BFS we need length of wordList we need O(N) time, N is the number of word in wordList.
	//For word we need O(M) time where M is the word length.
	//For word comparison we again need extra O(M) time.
	//And finally we are checking for every 26 character i.e., O(26)
	//Time Complexity = O(N * M * M * 26) = O(N*M^2)

	//Space: O(N*M^2) same as time complexity
	//complexity explain: https://leetcode.com/problems/word-ladder/solution/
	public int ladderLength(String beginWord, String endWord, List<String> wordList) {
		int res = 1;

		Set<String> dict = new HashSet<>(wordList);
		Queue<String> queue = new LinkedList<>();
		queue.offer(beginWord);
		Set<String> visited = new HashSet<>();

		while(!queue.isEmpty()){
			int size = queue.size();
			for(int i = 0; i < size; i++){
				String cur = queue.poll();
				if(cur.equals(endWord)) {
					return res;
				}

				char[] ch = cur.toCharArray();
				for(int j = 0; j < ch.length; j++){
					char temp = ch[j];//store the ch[j] to temp
					for(char c = 'a'; c <= 'z'; c++){
						ch[j] = c;
						String tmp = new String(ch);
						if(dict.contains(tmp) && !visited.contains(tmp)){
							queue.offer(tmp);
							visited.add(tmp);
						}
					}
					ch[j] = temp;//recover it
				}
			}
			res++;
		}
		return 0;
	}


	//too long, don't use
	public static int ladderLength2(String beginWord, String endWord, List<String> wordList) {
		HashSet<String> dict = new HashSet<>(wordList);
		if (!dict.contains(endWord)) {
			return 0;//endWord is not in wordList, therefore there is no valid transformation sequence.
		}
		HashSet<String> startSet = new HashSet<>();
		HashSet<String> endSet = new HashSet<>();
		HashSet<String> visit = new HashSet<>();//in order to avoid duplicate
		startSet.add(beginWord);
		endSet.add(endWord);
		for (int len = 2; !startSet.isEmpty(); len++) {
			HashSet<String> nextSet = new HashSet<>();
			for (String w : startSet) {
				for (int j = 0; j < w.length(); j++) {
					char[] ch = w.toCharArray();//这个一定要放在这一层，别放错放在了上一层
					for (char c = 'a'; c <= 'z'; c++) {
						if (c != w.charAt(j)) {
							ch[j] = c;
							String next = String.valueOf(ch);
							if (endSet.contains(next)) {
								return len;
							}
							if (dict.contains(next) && !visit.contains(next)) {
								nextSet.add(next);
								visit.add(next);
							}
						}
					}
				}
			}
			startSet = (nextSet.size() < endSet.size()) ? nextSet : endSet;//把短的set给startset
			endSet = (startSet == nextSet) ? endSet : nextSet;//把长的set给endset
		}
		return 0;
	}

	public static int ladderLength1(String beginWord, String endWord, List<String> wordList) {
		wordList.add(beginWord);
		HashMap<String, ArrayList<String>> nexts = getNexts(wordList);
		HashMap<String, Integer> distanceMap = new HashMap<>();
		distanceMap.put(beginWord, 1);
		HashSet<String> set = new HashSet<>();
		set.add(beginWord);
		Queue<String> queue = new LinkedList<>();
		queue.add(beginWord);
		while (!queue.isEmpty()) {
			String cur = queue.poll();
			Integer distance = distanceMap.get(cur);
			for (String next : nexts.get(cur)) {
				if (next.equals(endWord)) {
					return distance + 1;
				}
				if (!set.contains(next)) {
					set.add(next);
					queue.add(next);
					distanceMap.put(next, distance + 1);
				}
			}

		}
		return 0;
	}

	public static HashMap<String, ArrayList<String>> getNexts(List<String> words) {
		HashSet<String> dict = new HashSet<>(words);
		HashMap<String, ArrayList<String>> nexts = new HashMap<>();
		for (int i = 0; i < words.size(); i++) {
			nexts.put(words.get(i), getNext(words.get(i), dict));
		}
		return nexts;
	}

	// 应该根据具体数据状况决定用什么来找邻居
	// 1)如果字符串长度比较短，字符串数量比较多，以下方法适合
	// 2)如果字符串长度比较长，字符串数量比较少，以下方法不适合
	public static ArrayList<String> getNext(String word, HashSet<String> dict) {
		ArrayList<String> res = new ArrayList<String>();
		char[] chs = word.toCharArray();
		for (char cur = 'a'; cur <= 'z'; cur++) {
			for (int i = 0; i < chs.length; i++) {
				if (chs[i] != cur) {
					char tmp = chs[i];
					chs[i] = cur;
					if (dict.contains(String.valueOf(chs))) {
						res.add(String.valueOf(chs));
					}
					chs[i] = tmp;
				}
			}
		}
		return res;
	}


}

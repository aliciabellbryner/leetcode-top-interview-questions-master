package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Problem_0211_DesignAddAndSearchWordsDataStructure {

	public class TrieNode {
		public char val;
		public boolean isEnd;
		public TrieNode[] children = new TrieNode[26];
		public TrieNode() {}
		TrieNode(char c){
			TrieNode node = new TrieNode();
			isEnd = false;
			node.val = c;
		}
		public boolean containsChild(char c) {
			return children[c-'a'] != null;
		}
	}
	class WordDictionary {

		TrieNode root;
		/** Initialize your data structure here. */
		public WordDictionary() {
			root = new TrieNode();
		}

		public void addWord(String word) {
			TrieNode cur = root;
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (cur.containsChild(c)) {
					cur = cur.children[c-'a'];
				} else {
					TrieNode next = new TrieNode();
					cur.children[c-'a'] = next;
					cur = next;
				}
			}
			cur.isEnd = true;
		}

		public boolean search(String word) {
			return helper(word, root, 0);
		}

		public boolean helper(String word, TrieNode cur, int pos) {
			if (cur == null) {
				return false;
			}
			if (pos == word.length()) {
				return cur.isEnd;
			}
			char c = word.charAt(pos);
			if (c != '.') {
				return helper(word, cur.children[c-'a'],pos+1);
			} else {
				for (int i = 0; i < cur.children.length; i++) {
					if (helper(word, cur.children[i],pos+1)) {
						return true;
					}
				}
			}
			return false;
		}
	}

}

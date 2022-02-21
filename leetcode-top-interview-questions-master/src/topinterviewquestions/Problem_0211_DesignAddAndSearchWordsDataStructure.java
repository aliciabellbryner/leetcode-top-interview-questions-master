package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
/*
Design a data structure that supports adding new words and finding if a string matches any previously added string.

Implement the WordDictionary class:

WordDictionary() Initializes the object.
void addWord(word) Adds word to the data structure, it can be matched later.
bool search(word) Returns true if there is any string in the data structure that matches word or false otherwise. word may contain dots '.' where dots can be matched with any letter.


Example:

Input
["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
[[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
Output
[null,null,null,null,false,true,true,true]

Explanation
WordDictionary wordDictionary = new WordDictionary();
wordDictionary.addWord("bad");
wordDictionary.addWord("dad");
wordDictionary.addWord("mad");
wordDictionary.search("pad"); // return False
wordDictionary.search("bad"); // return True
wordDictionary.search(".ad"); // return True
wordDictionary.search("b.."); // return True


Constraints:

1 <= word.length <= 25
word in addWord consists of lowercase English letters.
word in search consist of '.' or lowercase English letters.
There will be at most 3 dots in word for search queries.
At most 104 calls will be made to addWord and search.
 */
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

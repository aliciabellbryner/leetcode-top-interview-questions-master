package topinterviewquestions;
/*
A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently store and retrieve keys in a dataset of strings. There are various applications of this data structure, such as autocomplete and spellchecker.

Implement the Trie class:

Trie() Initializes the trie object.
void insert(String word) Inserts the string word into the trie.
boolean search(String word) Returns true if the string word is in the trie (i.e., was inserted before), and false otherwise.
boolean startsWith(String prefix) Returns true if there is a previously inserted string word that has the prefix prefix, and false otherwise.


Example 1:

Input
["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
[[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
Output
[null, null, true, false, true, null, true]

Explanation
Trie trie = new Trie();
trie.insert("apple");
trie.search("apple");   // return True
trie.search("app");     // return False
trie.startsWith("app"); // return True
trie.insert("app");
trie.search("app");     // return True


Constraints:

1 <= word.length, prefix.length <= 2000
word and prefix consist only of lowercase English letters.
At most 3 * 104 calls in total will be made to insert, search, and startsWith.
 */
/*
 * 提交时把类名、构造函数名从Problem_0208_Trie改为Trie
 * 
 * */
public class Problem_0208_Trie {

	public static class Node {
		public boolean end;//if end = true, means it has a word that end here
		public Node[] nexts;

		public Node() {
			end = false;
			nexts = new Node[26];
		}
	}

	private Node root;

	public Problem_0208_Trie() {
		root = new Node();
	}

	public void insert(String word) {
		if (word == null) {
			return;
		}
		Node node = root;
		int path;
		for (int i = 0; i < word.length(); i++) {
			path = word.charAt(i) - 'a';
			if (node.nexts[path] == null) {
				node.nexts[path] = new Node();
			}
			node = node.nexts[path];
		}
		node.end = true;
	}

	public boolean search(String word) {
		if (word == null) {
			return false;
		}
		Node node = root;
		int index;
		for (int i = 0; i < word.length(); i++) {
			index = word.charAt(i) - 'a';
			if (node.nexts[index] == null) {
				return false;
			}
			node = node.nexts[index];
		}
		return node.end;
	}

	public boolean startsWith(String pre) {
		if (pre == null) {
			return false;
		}
		Node node = root;
		int index;
		for (int i = 0; i < pre.length(); i++) {
			index = pre.charAt(i) - 'a';
			if (node.nexts[index] == null) {
				return false;
			}
			node = node.nexts[index];
		}
		return true;
	}

}

//don't use the previous solution with pass, end value as this question we only have to maintain the end and nexts
// if we use the previous complex solution, it will time exceed
//class Trie {
//	int pass;
//	int end;
//	Trie[] nexts;
//	public Trie() {
//		nexts = new Trie[26];
//	}
//
//	Trie root = new Trie();
//	public void insert(String word) {
//		Trie node = root;
//		int index = 0;
//		for (int i = 0; i < word.length(); i++) {
//			index = word.charAt(i) - 'a';
//			if (node.nexts[index] == null) {
//				node.nexts[index] = new Trie();
//			}
//			node = node.nexts[index];
//			node.pass++;
//		}
//		node.end++;
//	}
//
//	public boolean search(String word) {
//		if (word == null) {
//			return false;
//		}
//		int index = 0;
//		Trie node = root;
//		for (int i = 0; i < word.length(); i++) {
//			index = word.charAt(i) - 'a';
//			if (node.nexts[index] == null) {
//				return false;
//			}
//			node = node.nexts[index];
//		}
//		return node.end > 0;
//	}
//
//	public boolean startsWith(String prefix) {
//		if (prefix == null) {
//			return false;
//		}
//		int index = 0;
//		Trie node = root;
//		for (int i = 0; i < prefix.length(); i++) {
//			index = prefix.charAt(i) - 'a';
//			if (node.nexts[index] == null) {
//				return false;
//			}
//			node = node.nexts[index];
//		}
//		return node.pass > 0;
//	}
//}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */

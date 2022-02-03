package topinterviewquestions;

import java.util.*;

public class Problem_0425_WordSquare {
    //https://leetcode.com/problems/word-squares/solution/
    //Approach 2: Backtracking with Trie
    class TrieNode {
        HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
        List<Integer> wordList = new ArrayList<Integer>();

        public TrieNode() {}
    }


    class Solution {
        int N = 0;
        String[] words = null;
        TrieNode trie = null;

        public List<List<String>> wordSquares(String[] words) {
            this.words = words;
            this.N = words[0].length();

            List<List<String>> results = new ArrayList<List<String>>();
            this.buildTrie(words);

            for (String word : words) {
                LinkedList<String> wordSquares = new LinkedList<String>();
                wordSquares.addLast(word);
                this.backtracking(1, wordSquares, results);
            }
            return results;
        }

        protected void backtracking(int step, LinkedList<String> wordSquares,
                                    List<List<String>> results) {
            if (step == N) {
                results.add((List<String>) wordSquares.clone());
                return;
            }

            StringBuilder prefix = new StringBuilder();
            for (String word : wordSquares) {
                prefix.append(word.charAt(step));
            }

            for (Integer wordIndex : this.getWordsWithPrefix(prefix.toString())) {
                wordSquares.addLast(this.words[wordIndex]);
                this.backtracking(step + 1, wordSquares, results);
                wordSquares.removeLast();
            }
        }

        protected void buildTrie(String[] words) {
            this.trie = new TrieNode();

            for (int wordIndex = 0; wordIndex < words.length; ++wordIndex) {
                String word = words[wordIndex];

                TrieNode node = this.trie;
                for (Character letter : word.toCharArray()) {
                    if (node.children.containsKey(letter)) {
                        node = node.children.get(letter);
                    } else {
                        TrieNode newNode = new TrieNode();
                        node.children.put(letter, newNode);
                        node = newNode;
                    }
                    node.wordList.add(wordIndex);
                }
            }
        }

        protected List<Integer> getWordsWithPrefix(String prefix) {
            TrieNode node = this.trie;
            for (Character letter : prefix.toCharArray()) {
                if (node.children.containsKey(letter)) {
                    node = node.children.get(letter);
                } else {
                    // return an empty list.
                    return new ArrayList<Integer>();
                }
            }
            return node.wordList;
        }
    }


    //Trie (PrefixSearch) + Backtracking
    //https://leetcode.com/problems/word-squares/discuss/1569959/
    //O(N*L + N*L) --> Space Taken by Trie (Characters & Word Indexes)
    //O(L + L) --> Space taken by TempList + Recursion Stack
    //Total Space Complexity: O(N*L + L)
    //https://leetcode.com/problems/word-squares/discuss/1565494/Premium-Daily-LeetCoding-Challenge-November-Week-2/1145290
    class Solution1 {
        public class TrieNode {
            HashMap<Character, TrieNode> children;
            HashSet<Integer> wordIndexes;

            public TrieNode() {
                children = new HashMap<>();
                wordIndexes = new HashSet<>();
            }
        }

        public List<List<String>> wordSquares(String[] words) {
            List<List<String>> result = new ArrayList<>();
            if (words == null || words.length == 0) {
                return result;
            }

            TrieNode root = buildTrie(words);
            List<String> tempList = new ArrayList<>();
            for (String word : words) {
                tempList.add(word);
                wordSquaresHelper(result, words, root, tempList);
                tempList.remove(tempList.size() - 1);
            }
            return result;
        }

        private void wordSquaresHelper(List<List<String>> result, String[] words, TrieNode root, List<String> tempList) {
            if (tempList.size() == words[0].length()) {
                result.add(new ArrayList<>(tempList));
                return;
            }
            for (int i : getMatchingWordIndexes(root, tempList)) {
                tempList.add(words[i]);
                wordSquaresHelper(result, words, root, tempList);
                tempList.remove(tempList.size() - 1);
            }
        }

        private HashSet<Integer> getMatchingWordIndexes(TrieNode root, List<String> wordList) {
            int col = wordList.size();
            TrieNode cur = root;

            for (int i = 0; i < col; i++) {
                char c = wordList.get(i).charAt(col);
                cur = cur.children.get(c);
                if (cur == null) {
                    return new HashSet<>();
                }
            }

            return cur.wordIndexes;
        }

        private TrieNode buildTrie(String[] words) {
            TrieNode root = new TrieNode();

            for (int i = 0; i < words.length; i++) {
                TrieNode cur = root;
                String word = words[i];
                for (int j = 0; j < word.length(); j++) {
                    char c = word.charAt(j);
                    cur.children.putIfAbsent(c, new TrieNode());
                    cur = cur.children.get(c);
                    cur.wordIndexes.add(i);
                }
            }

            return root;
        }
    }
}

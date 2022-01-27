package topinterviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem_0336_PalindromePairs_G {
    //https://leetcode.com/problems/palindrome-pairs/discuss/79195/O(n-*-k2)-java-solution-with-Trie-structure
    private static class TrieNode {
        TrieNode[] next;
        int index;//这个的目的是把words中每个word的index记录起来
        List<Integer> list;

        TrieNode() {
            next = new TrieNode[26];
            index = -1;
            list = new ArrayList<>();
        }
    }

    //Let n be the number of words, and k be the length of the longest word.
    //Time Complexity : O(k^2 * n)
    //Inserting each word into the Trie takes O(k) time. As well as inserting the word,
    // we also checked at each letter whether or not the remaining part of the word was a palindrome.
    // These checks had a cost of O(k)O(k), and with kk of them, gave a total cost of O(k^2).
    // With n words to insert, the total cost of building the Trie was therefore O(k^2 * n)
    // Space O(n^2 * k)
    //The Trie is the main space usage. In the worst case, each of the O(n⋅k) letters
    // in the input would be on separate nodes, and each node would have up to nn indexes
    // in its list. This gives us a worst case of O(n^2 * k)
    // which is strictly larger than the input or the output.
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> res = new ArrayList<>();

        TrieNode root = new TrieNode();

        for (int i = 0; i < words.length; i++) {
            addWord(root, words[i], i);
        }

        for (int i = 0; i < words.length; i++) {
            search(words, i, root, res);
        }

        return res;
    }

    private void addWord(TrieNode root, String word, int index) {
        for (int i = word.length() - 1; i >= 0; i--) {
            int j = word.charAt(i) - 'a';

            if (root.next[j] == null) {
                root.next[j] = new TrieNode();
            }

            if (isPalindrome(word, 0, i)) {
                root.list.add(index);
            }

            root = root.next[j];
        }

        root.list.add(index);
        root.index = index;
    }

    private void search(String[] words, int i, TrieNode root, List<List<Integer>> res) {
        for (int j = 0; j < words[i].length(); j++) {
            if (root.index >= 0 && root.index != i && isPalindrome(words[i], j, words[i].length() - 1)) {
                res.add(Arrays.asList(i, root.index));
            }

            root = root.next[words[i].charAt(j) - 'a'];
            if (root == null) {
                return;
            }
        }

        for (int j : root.list) {
            if (i == j) {
                continue;
            }
            res.add(Arrays.asList(i, j));
        }
    }

    private boolean isPalindrome(String word, int i, int j) {
        while (i < j) {
            if (word.charAt(i++) != word.charAt(j--)) {
                return false;
            }
        }

        return true;
    }

}

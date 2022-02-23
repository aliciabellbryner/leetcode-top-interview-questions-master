package topinterviewquestions;

import java.util.HashMap;
import java.util.Map;

/*
A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently store and retrieve keys in a dataset of strings. There are various applications of this data structure, such as autocomplete and spellchecker.
Implement the Trie class:
Trie() Initializes the trie object.
void insert(String word) Inserts the string wordinto the trie.
int countWordsEqualTo(String word) Returns the number of instances of the string word in the trie.
int countWordsStartingWith(String prefix)Returns the number of strings in the trie that have the string prefix as a prefix.
void erase(String word) Erases the string wordfrom the trie.
Example 1:
Input
["Trie", "insert", "insert", "countWordsEqualTo", "countWordsStartingWith", "erase", "countWordsEqualTo", "countWordsStartingWith", "erase", "countWordsStartingWith"]
[[], ["apple"], ["apple"], ["apple"], ["app"], ["apple"], ["apple"], ["app"], ["apple"], ["app"]]
Output
[null, null, null, 2, 2, null, 1, 1, null, 0]

Explanation
Trie trie = new Trie();
trie.insert("apple");               // Inserts "apple".
trie.insert("apple");               // Inserts another "apple".
trie.countWordsEqualTo("apple");    // There are two instances of "apple" so return 2.
trie.countWordsStartingWith("app"); // "app" is a prefix of "apple" so return 2.
trie.erase("apple");                // Erases one "apple".
trie.countWordsEqualTo("apple");    // Now there is only one instance of "apple" so return 1.
trie.countWordsStartingWith("app"); // return 1
trie.erase("apple");                // Erases "apple". Now the trie is empty.
trie.countWordsStartingWith("app"); // return 0
Constraints:
1 <= word.length, prefix.length <= 2000
word and prefix consist only of lowercase English letters.
At most 3 * 10^4 calls in total will be made to insert, countWordsEqualTo, countWordsStartingWith, and erase.
It is guaranteed that for any function call to erase, the string word will exist in the trie.
 */
public class Problem_1804_ImplementTrieiiPrefixTree {

    //diss
    class Trie {
        TrieNode root;
        public Trie() {
            this.root = new TrieNode();
        }

        public void insert(String word) {
            TrieNode p = root;
            for(int i = 0; i < word.length(); i++){
                if(p.children[word.charAt(i)] == null){
                    p.children[word.charAt(i)] = new TrieNode();
                }
                p = p.children[word.charAt(i)];
                p.startWiths.put(word, p.startWiths.getOrDefault(word, 0) + 1);
            }
        }

        public int countWordsEqualTo(String word) {
            TrieNode p = root;
            for(int i = 0; i < word.length(); i++){
                if(p.children[word.charAt(i)] == null){
                    return 0;
                }
                p = p.children[word.charAt(i)];
            }
            for(String key : p.startWiths.keySet()){
                if(key.equals(word)){
                    return p.startWiths.get(key);
                }
            }
            return 0;
        }

        public int countWordsStartingWith(String prefix) {
            TrieNode p = root;
            int count = 0;
            for(int i = 0; i < prefix.length(); i++){
                if(p.children[prefix.charAt(i)] == null){
                    return 0;
                }
                p = p.children[prefix.charAt(i)];
            }
            for(String key : p.startWiths.keySet()){
                count += p.startWiths.get(key);
            }
            return count;
        }

        public void erase(String word) {
            TrieNode p = root;
            for(int i = 0; i < word.length(); i++){
                if(p.children[word.charAt(i)] == null){
                    return;
                }
                p = p.children[word.charAt(i)];
                p.startWiths.put(word, p.startWiths.get(word) - 1);
            }
        }
    }
    class TrieNode{
        TrieNode[] children = new TrieNode[256];
        Map<String, Integer> startWiths = new HashMap<>();
        TrieNode(){}
    }


    //another
    //Java Trie with comments
    class Solution2 {
        class Node
        {
            Node[] children = new Node[26];
            int count;
            int end;
        }
        class Trie
        {
            private Node root;

            public Trie()
            {
                root = new Node();
            }

            // We are iterating through each word character and if Node doesn't have a child we will add it.
            // on each step after adding/stepping the node we increment prefix count
            // after last step we add to words end's counter
            public void insert(String word)
            {
                Node current = root;
                int pos;
                for (char ch : word.toCharArray())
                {
                    pos = ch - 'a';
                    if (current.children[pos] == null)
                    {
                        current.children[pos] = new Node();
                    }

                    current = current.children[pos];
                    current.count++;
                }

                current.end++;
            }

            // We are iterating through each word character and if Node doesn't have a child we return 0.
            // In case we iterated entire word, just return end's counter
            public int countWordsEqualTo(String word)
            {
                Node current = root;
                int pos;
                for (char ch : word.toCharArray())
                {
                    pos = ch - 'a';
                    if (current.children[pos] == null)
                    {
                        return 0;
                    }

                    current = current.children[pos];
                }

                return current.end;
            }

            // We are iterating through each word character and if Node doesn't have a child we return 0.
            // In case we iterated entire word, just return count counter
            public int countWordsStartingWith(String prefix)
            {
                Node current = root;
                int pos;
                for (char ch : prefix.toCharArray())
                {
                    pos = ch - 'a';
                    if (current.children[pos] == null)
                    {
                        return 0;
                    }

                    current = current.children[pos];
                }

                return current.count;
            }

            // We are iterating through each word character and if Node doesn't have a child we return
            // In case we iterated entire word, just decrement end's counter
            public void erase(String word)
            {
                Node current = root;
                int pos;
                for (char ch : word.toCharArray())
                {
                    pos = ch - 'a';
                    current = current.children[pos];
                    current.count--;
                }

                current.end--;
            }
        }

    }
}

package topinterviewquestions;

import java.util.*;
/*
You are given an array of words where each word consists of lowercase English letters.

wordA is a predecessor of wordB if and only if we can insert exactly one letter anywhere in wordA without changing the order of the other characters to make it equal to wordB.

For example, "abc" is a predecessor of "abac", while "cba" is not a predecessor of "bcad".
A word chain is a sequence of words [word1, word2, ..., wordk] with k >= 1, where word1 is a predecessor of word2, word2 is a predecessor of word3, and so on. A single word is trivially a word chain with k == 1.

Return the length of the longest possible word chain with words chosen from the given list of words.



Example 1:

Input: words = ["a","b","ba","bca","bda","bdca"]
Output: 4
Explanation: One of the longest word chains is ["a","ba","bda","bdca"].
Example 2:

Input: words = ["xbc","pcxbcf","xb","cxbc","pcxbc"]
Output: 5
Explanation: All the words can be put in a word chain ["xb", "xbc", "cxbc", "pcxbc", "pcxbcf"].
Example 3:

Input: words = ["abcd","dbqca"]
Output: 1
Explanation: The trivial word chain ["abcd"] is one of the longest word chains.
["abcd","dbqca"] is not a valid word chain because the ordering of the letters is changed.


Constraints:

1 <= words.length <= 1000
1 <= words[i].length <= 16
words[i] only consists of lowercase English letters.
 */

public class Problem_1048_LongestStringChain_G {


    //https://leetcode.com/problems/longest-string-chain/discuss/294890/JavaC%2B%2BPython-DP-Solution
    //Time O(NlogN) for sorting,
    //Time O(NSS) for the for loop, where the second S refers to the string generation and S <= 16.(S is the average length of word in words)
    //Space O(NS)
    //思路就是先把words按照word的长度升序排列，然后利用hashmap保存每一个String如果当成是chain的最后一个word，他的chain最长长度是多少
    //所以我们就可以对words里的每一个word的每一个char分别删除，看删除之后的string是否在hashmap里就可以知道是否可以由之前长度短一点的string获得，best就是这个word对应的最长chain长度
    public int longestStrChain(String[] words) {
        Map<String, Integer> map = new HashMap<>();
        Arrays.sort(words, (a, b)->a.length() - b.length());
        int res = 0;
        for (String word : words) {
            int best = 0;
            for (int i = 0; i < word.length(); ++i) {
                String prev = word.substring(0, i) + word.substring(i + 1);
                best = Math.max(best, map.getOrDefault(prev, 0) + 1);
            }
            map.put(word, best);
            res = Math.max(res, best);
        }
        return res;
    }


    //https://leetcode.com/problems/longest-string-chain/solution/
    //Time complexity: O（(L^2）* N）N is the length of words, L is average length of word in words
    //Space complexity: O(N).
      class Solution {

        public int longestStrChain(String[] words) {
            Map<String, Integer> memo = new HashMap<>();
            Set<String> wordsPresent = new HashSet<>();
            Collections.addAll(wordsPresent, words);
            int res = 0;
            for (String word : words) {
                res = Math.max(res, dfs(wordsPresent, memo, word));
            }
            return res;
        }
        private int dfs(Set<String> words, Map<String, Integer> memo, String currentWord) {
            // If the word is encountered previously we just return its value present in the map (memoization).
            if (memo.containsKey(currentWord)) {
                return memo.get(currentWord);
            }
            // This stores the maximum length of word sequence possible with the 'currentWord' as the
            int maxLength = 1;
            StringBuilder sb = new StringBuilder(currentWord);

            // creating all possible strings taking out one character at a time from the `currentWord`
            for (int i = 0; i < currentWord.length(); i++) {
                sb.deleteCharAt(i);
                String newWord = sb.toString();
                // If the new word formed is present in the list, we do a dfs search with this newWord.
                if (words.contains(newWord)) {
                    int currentLength = 1 + dfs(words, memo, newWord);
                    maxLength = Math.max(maxLength, currentLength);
                }
                sb.insert(i, currentWord.charAt(i));
            }
            memo.put(currentWord, maxLength);

            return maxLength;
        }

    }




}

package topinterviewquestions;

import java.util.ArrayList;

public class Problem_0792_NumberofMatchingSubsequences_G {
    class Solution {
        //Approach #2: Next Letter Pointers [Accepted]
        //https://leetcode.com/problems/number-of-matching-subsequences/solution/
        //思路就是用words里每个单词的开头建立ArrayList,然后对S中的每个char遍历，每遍历一个字母就把对应的Node里的index加1，
        // 如果加到这个node里的string的length说明遍历完了，找到一个符合要求的，res+1， 然后再从index下一位开始重新走知道全部走完S里每一个char
        //Time Complexity: O(S.length+∑words[i].length).
        //Space Complexity: O(words.length+∑words[i].length),words.length for the heads, ∑words[i].length for the old_bucket in the for loop
        class Node {
            String s;//代表什么单词word
            int index;//代表遍历到哪个index了，也就是前面的char都用过了
            public Node(String w, int i) {
                s = w;
                index = i;
            }
        }

        public int numMatchingSubseq(String S, String[] words) {
            int res = 0;
            ArrayList<Node>[] heads = new ArrayList[26];
            for (int i = 0; i < 26; ++i)
                heads[i] = new ArrayList<Node>();

            for (String word: words)
                heads[word.charAt(0) - 'a'].add(new Node(word, 0));

            for (char c: S.toCharArray()) {
                ArrayList<Node> old_bucket = heads[c - 'a'];
                heads[c - 'a'] = new ArrayList<Node>();

                for (Node node: old_bucket) {
                    node.index++;
                    if (node.index == node.s.length()) {
                        res++;
                    } else {
                        heads[node.s.charAt(node.index) - 'a'].add(node);//把index加1之后的node放到新的index位置的char对应的heads中去
                    }
                }
                old_bucket.clear();
            }
            return res;
        }

    }


}

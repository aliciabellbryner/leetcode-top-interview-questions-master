package topinterviewquestions;

import java.util.*;

/*
We can represent a sentence as an array of words, for example, the sentence "I am happy with leetcode" can be represented as arr = ["I","am",happy","with","leetcode"].

Given two sentences sentence1 and sentence2 each represented as a string array and given an array of string pairs similarPairs where similarPairs[i] = [xi, yi] indicates that the two words xi and yi are similar.

Return true if sentence1 and sentence2 are similar, or false if they are not similar.

Two sentences are similar if:

They have the same length (i.e., the same number of words)
sentence1[i] and sentence2[i] are similar.
Notice that a word is always similar to itself, also notice that the similarity relation is transitive. For example, if the words a and b are similar, and the words b and c are similar, then a and c are similar.



Example 1:

Input: sentence1 = ["great","acting","skills"], sentence2 = ["fine","drama","talent"], similarPairs = [["great","good"],["fine","good"],["drama","acting"],["skills","talent"]]
Output: true
Explanation: The two sentences have the same length and each word i of sentence1 is also similar to the corresponding word in sentence2.
Example 2:

Input: sentence1 = ["I","love","leetcode"], sentence2 = ["I","love","onepiece"], similarPairs = [["manga","onepiece"],["platform","anime"],["leetcode","platform"],["anime","manga"]]
Output: true
Explanation: "leetcode" --> "platform" --> "anime" --> "manga" --> "onepiece".
Since "leetcode is similar to "onepiece" and the first two words are the same, the two sentences are similar.
Example 3:

Input: sentence1 = ["I","love","leetcode"], sentence2 = ["I","love","onepiece"], similarPairs = [["manga","hunterXhunter"],["platform","anime"],["leetcode","platform"],["anime","manga"]]
Output: false
Explanation: "leetcode" is not similar to "onepiece".


Constraints:

1 <= sentence1.length, sentence2.length <= 1000
1 <= sentence1[i].length, sentence2[i].length <= 20
sentence1[i] and sentence2[i] consist of lower-case and upper-case English letters.
0 <= similarPairs.length <= 2000
similarPairs[i].length == 2
1 <= xi.length, yi.length <= 20
xi and yi consist of English letters.
 */
public class Problem_0737_SentenceSimilarityII {
    /*
    Approach #1: Depth-First Search [Accepted]
Intuition

Two words are similar if they are the same, or there is a path connecting them from edges represented by pairs.

We can check whether this path exists by performing a depth-first search from a word and seeing if we reach the other word. The search is performed on the underlying graph specified by the edges in pairs.

Algorithm

We start by building our graph from the edges in pairs.

The specific algorithm we go for is an iterative depth-first search. The implementation we go for is a typical "visitor pattern": when searching whether there is a path from w1 = words1[i] to w2 = words2[i], stack will contain all the nodes that are queued up for processing, while seen will be all the nodes that have been queued for processing (whether they have been processed or not).


Complexity Analysis

Time Complexity: O(NP)O(NP), where NN is the maximum length of words1 and words2, and PP is the length of pairs. Each of NN searches could search the entire graph.

Space Complexity: O(P)O(P), the size of pairs.

Approach #2: Union-Find [Accepted]
Intuition

As in Approach #1, we want to know if there is path connecting two words from edges represented by pairs.

Our problem comes down to finding the connected components of a graph. This is a natural fit for a Disjoint Set Union (DSU) structure.

Algorithm

Draw edges between words if they are similar. For easier interoperability between our DSU template, we will map each word to some integer ix = index[word]. Then, dsu.find(ix) will tell us a unique id representing what component that word is in.

For more information on DSU, please look at Approach #2 in the article here. For brevity, the solutions showcased below do not use union-by-rank.

After putting each word in pairs into our DSU template, we check successive pairs of words w1, w2 = words1[i], words2[i]. We require that w1 == w2, or w1 and w2 are in the same component. This is easily checked using dsu.find.


Complexity Analysis

Time Complexity: O(N \log P + P)O(NlogP+P), where NN is the maximum length of words1 and words2, and PP is the length of pairs. If we used union-by-rank, this complexity improves to O(N * \alpha(P) + P) \approx O(N + P)O(N∗α(P)+P)≈O(N+P), where \alphaα is the Inverse-Ackermann function.

Space Complexity: O(P)O(P), the size of pairs.
     */

    class Solution1 {
        public boolean areSentencesSimilarTwo(
                String[] words1, String[] words2, String[][] pairs) {
            if (words1.length != words2.length) return false;
            Map<String, List<String>> graph = new HashMap();
            for (String[] pair: pairs) {
                for (String p: pair) if (!graph.containsKey(p)) {
                    graph.put(p, new ArrayList());
                }
                graph.get(pair[0]).add(pair[1]);
                graph.get(pair[1]).add(pair[0]);
            }

            for (int i = 0; i < words1.length; ++i) {
                String w1 = words1[i], w2 = words2[i];
                Stack<String> stack = new Stack();
                Set<String> seen = new HashSet();
                stack.push(w1);
                seen.add(w1);
                search: {
                    while (!stack.isEmpty()) {
                        String word = stack.pop();
                        if (word.equals(w2)) break search;
                        if (graph.containsKey(word)) {
                            for (String nei: graph.get(word)) {
                                if (!seen.contains(nei)) {
                                    stack.push(nei);
                                    seen.add(nei);
                                }
                            }
                        }
                    }
                    return false;
                }
            }
            return true;
        }
    }

    class Solution2 {
        public boolean areSentencesSimilarTwo(String[] words1, String[] words2, String[][] pairs) {
            if (words1.length != words2.length) return false;
            Map<String, Integer> index = new HashMap();
            int count = 0;
            DSU dsu = new DSU(2 * pairs.length);
            for (String[] pair: pairs) {
                for (String p: pair) if (!index.containsKey(p)) {
                    index.put(p, count++);
                }
                dsu.union(index.get(pair[0]), index.get(pair[1]));
            }

            for (int i = 0; i < words1.length; ++i) {
                String w1 = words1[i], w2 = words2[i];
                if (w1.equals(w2)) continue;
                if (!index.containsKey(w1) || !index.containsKey(w2) ||
                        dsu.find(index.get(w1)) != dsu.find(index.get(w2)))
                    return false;
            }
            return true;
        }
    }

    class DSU {
        int[] parent;
        public DSU(int N) {
            parent = new int[N];
            for (int i = 0; i < N; ++i)
                parent[i] = i;
        }
        public int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }
        public void union(int x, int y) {
            parent[find(x)] = find(y);
        }
    }



    //diss
    //This is a good use case for Union-Find, compare to Sentence Similarity I, here the similarity between words are transitive, so all the connected(similar) words should be group into an union represented by their ultimate parent(or family holder, you name it).
    //The connections can be represented by an parent map Map<String, String> m, which record the direct parent-ship we learned in each pair, but not the ultimate-parent. To build it, go through the input pairs, for each pair<w1, w2>, use the recursive find() method to find the ultimate-parent for both word - parent1, parent2, if they are different, assign parent1 as parent of parent2(or the other way around), so that the to families are merged.
    //The classic find(x) method will find the ultimate-parent of x. I modified it a little bit, make it do a little of extra initialization work - assign x itself as its parent when it is not initialize - so that we don't have to explicitly initialize the map at the beginning.
    class Solution3 {
        public boolean areSentencesSimilarTwo(String[] a, String[] b, String[][] pairs) {
            if (a.length != b.length) return false;
            Map<String, String> m = new HashMap<>();
            for (String[] p : pairs) {
                String parent1 = find(m, p[0]), parent2 = find(m, p[1]);
                if (!parent1.equals(parent2)) m.put(parent1, parent2);
            }

            for (int i = 0; i < a.length; i++)
                if (!a[i].equals(b[i]) && !find(m, a[i]).equals(find(m, b[i]))) return false;

            return true;
        }

        private String find(Map<String, String> m, String s) {
            if (!m.containsKey(s)) m.put(s, s);
            return s.equals(m.get(s)) ? s : find(m, m.get(s));
        }
    }


    //Notice there's no java DFS solution posted by others. I love DFS, what about you?
    //The idea is simple:
    //
    //Build the graph according to the similar word pairs. Each word is a graph node.
    //For each word in words1, we do DFS search to see if the corresponding word is existing in words2.
    //See the clean code below. Happy coding!
    class Solution4 {
        public boolean areSentencesSimilarTwo(String[] words1, String[] words2, String[][] pairs) {
            if (words1.length != words2.length) {
                return false;
            }

            Map<String, Set<String>> graph = new HashMap<>();
            for (String[] p : pairs) {
                graph.putIfAbsent(p[0], new HashSet<>());
                graph.putIfAbsent(p[1], new HashSet<>());
                graph.get(p[0]).add(p[1]);
                graph.get(p[1]).add(p[0]);
            }

            for (int i = 0; i < words1.length; i++) {
                if (words1[i].equals(words2[i])) continue;
                if (!graph.containsKey(words1[i])) return false;
                if (!dfs(graph, words1[i], words2[i], new HashSet<>())) return false;
            }

            return true;
        }

        private boolean dfs(Map<String, Set<String>> graph, String source, String target, Set<String> visited) {
            if (graph.get(source).contains(target)) return true;

            if (visited.add(source)) {
                for (String next : graph.get(source)) {
                    if (!visited.contains(next) && dfs(graph, next, target, visited))
                        return true;
                }
            }
            return false;
        }
    }


}

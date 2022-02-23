package topinterviewquestions;
/*
Given an array of strings wordsDict and two different strings that already exist in the array word1 and word2, return the shortest distance between these two words in the list.



Example 1:

Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "coding", word2 = "practice"
Output: 3
Example 2:

Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "makes", word2 = "coding"
Output: 1


Constraints:

1 <= wordsDict.length <= 3 * 104
1 <= wordsDict[i].length <= 10
wordsDict[i] consists of lowercase English letters.
word1 and word2 are in wordsDict.
word1 != word2
 */
public class Problem_0243_ShortestWordDistance {
/*
This is a straight-forward coding problem. The distance between any two positions i_1i
1
​
  and i_2i
2
​
  in an array is |i_1 - i_2|∣i
1
​
 −i
2
​
 ∣. To find the shortest distance between word1 and word2, we need to traverse the input array and find all occurrences i_1i
1
​
  and i_2i
2
​
  of the two words, and check if |i_1 - i_2|∣i
1
​
 −i
2
​
 ∣ is less than the minimum distance computed so far.

Approach #1 (Brute Force)
Algorithm

A naive solution to this problem is to go through the entire array looking for the first word. Every time we find an occurrence of the first word, we search the entire array for the closest occurrence of the second word.


Complexity Analysis

The time complexity is O(n^2)O(n
2
 ), since for every occurrence of word1, we traverse the entire array in search for the closest occurrence of word2.

Space complexity is O(1)O(1), since no additional space is used.

Approach #2 (One-pass)
Algorithm

We can greatly improve on the brute-force approach by keeping two indices i1 and i2 where we store the most recent locations of word1 and word2. Each time we find a new occurrence of one of the words, we do not need to search the entire array for the other word, since we already have the index of its most recent occurrence.


Complexity Analysis

Time complexity: O(N \cdot M)O(N⋅M) where NN is the number of words in the input list, and MM is the total length of two input words.

Space complexity: O(1)O(1), since no additional space is allocated.
 */

	class Solution2 {
		public int shortestDistance(String[] words, String word1, String word2) {
			int i1 = -1, i2 = -1;
			int minDistance = words.length;
			for (int i = 0; i < words.length; i++) {
				if (words[i].equals(word1)) {
					i1 = i;
				} else if (words[i].equals(word2)) {
					i2 = i;
				}

				if (i1 != -1 && i2 != -1) {
					minDistance = Math.min(minDistance, Math.abs(i1 - i2));
				}
			}
			return minDistance;
		}
	}


	//diss
	public int shortestDistance3(String[] words, String word1, String word2) {
		int p1 = -1, p2 = -1, min = Integer.MAX_VALUE;

		for (int i = 0; i < words.length; i++) {
			if (words[i].equals(word1))
				p1 = i;

			if (words[i].equals(word2))
				p2 = i;

			if (p1 != -1 && p2 != -1)
				min = Math.min(min, Math.abs(p1 - p2));
		}

		return min;
	}

	//@jeantimex You don't need to calculate min every round, because once you find both indexes, you check min even though neither is changed, which is inefficient. Here is my code, please review it.
	public class Solution3 {
		public int shortestDistance(String[] words, String word1, String word2) {
			Integer index1 = null, index2 = null;
			int res = Integer.MAX_VALUE;
			for(int i = 0; i < words.length; i++) {
				if(words[i].equals(word1)) {
					index1 = i;
					if(index2 != null) res = Math.min(res, Math.abs(index1 - index2));
				}

				if(words[i].equals(word2)) {
					index2 = i;
					if(index1 != null) res = Math.min(res, Math.abs(index1 - index2));
				}
			}
			return res;
		}
	}

}

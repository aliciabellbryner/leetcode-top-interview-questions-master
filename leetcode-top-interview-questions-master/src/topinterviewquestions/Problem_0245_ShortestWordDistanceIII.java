package topinterviewquestions;
/*
Given an array of strings wordsDict and two strings that already exist in the array word1 and word2, return the shortest distance between these two words in the list.

Note that word1 and word2 may be the same. It is guaranteed that they represent two individual words in the list.



Example 1:

Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "makes", word2 = "coding"
Output: 1
Example 2:

Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "makes", word2 = "makes"
Output: 3


Constraints:

1 <= wordsDict.length <= 105
1 <= wordsDict[i].length <= 10
wordsDict[i] consists of lowercase English letters.
word1 and word2 are in wordsDict.
 */
public class Problem_0245_ShortestWordDistanceIII {
/*
diss
 */
    //Solution 1 ... Java "short"
    //
    //i1 and i2 are the indexes where word1 and word2 were last seen. Except if they're the same word, then i1 is the previous index.
public int shortestWordDistance1(String[] words, String word1, String word2) {
    long dist = Integer.MAX_VALUE, i1 = dist, i2 = -dist;
    for (int i=0; i<words.length; i++) {
        if (words[i].equals(word1))
            i1 = i;
        if (words[i].equals(word2)) {
            if (word1.equals(word2))
                i1 = i2;
            i2 = i;
        }
        dist = Math.min(dist, Math.abs(i1 - i2));
    }
    return (int) dist;
}
//Solution 2 ... Java "fast"
//
//Same as solution 1, but minimizing the number of string comparisons.

    public int shortestWordDistance2(String[] words, String word1, String word2) {
        long dist = Integer.MAX_VALUE, i1 = dist, i2 = -dist;
        boolean same = word1.equals(word2);
        for (int i=0; i<words.length; i++) {
            if (words[i].equals(word1)) {
                if (same) {
                    i1 = i2;
                    i2 = i;
                } else {
                    i1 = i;
                }
            } else if (words[i].equals(word2)) {
                i2 = i;
            }
            dist = Math.min(dist, Math.abs(i1 - i2));
        }
        return (int) dist;
    }



    public int shortestWordDistance3(String[] words, String word1, String word2) {
        int n = words.length, min = n - 1;
        for(int i = 0, j = -1; i < n; i++){
            if(words[i].equals(word1) || words[i].equals(word2)){
                if(j != -1 && (!words[j].equals(words[i]) || word1.equals(word2))) min = Math.min(min, i - j);
                j = i;
            }
        }
        return min;
    }

    public int shortestWordDistance4(String[] words, String word1, String word2) {
        boolean isSame = word1.equals(word2);
        int idx1 = -1, idx2 = -1;
        int min = words.length;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word1))
                idx1 = i;
            else if (words[i].equals(word2))
                idx2 = i;
            else
                continue;
            if (idx1 >= 0 && idx2 >= 0)
                min = Math.min(min, Math.abs(idx1 - idx2));
            if (isSame)
                idx2 = idx1;
        }

        return min;
    }
}

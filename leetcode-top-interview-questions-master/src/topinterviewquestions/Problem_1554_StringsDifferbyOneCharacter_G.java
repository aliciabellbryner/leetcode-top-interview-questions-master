package topinterviewquestions;

import java.util.HashSet;
import java.util.Set;
/*
1554. Strings Differ by One Character
Medium

176

5

Add to List

Share
Given a list of strings dict where all the strings are of the same length.

Return true if there are 2 strings that only differ by 1 character in the same index, otherwise return false.



Example 1:

Input: dict = ["abcd","acbd", "aacd"]
Output: true
Explanation: Strings "abcd" and "aacd" differ only by one character in the index 1.
Example 2:

Input: dict = ["ab","cd","yz"]
Output: false
Example 3:

Input: dict = ["abcd","cccc","abyd","abab"]
Output: true


Constraints:

The number of characters in dict <= 105
dict[i].length == dict[j].length
dict[i] should be unique.
dict[i] contains only lowercase English letters.


Follow up: Could you solve this problem in O(n * m) where n is the length of dict and m is the length of each string.
 */
public class Problem_1554_StringsDifferbyOneCharacter_G {

    //https://leetcode.com/problems/strings-differ-by-one-character/discuss/805033/Easy-Java-Hashset-O(Nm2)-solution-beats-83/913959
    //time O(N*(M^2)), M is the average length of words[i], N is the length of words.length
    public boolean differByOne(String[] words) {
        for (int i = 0; i < words[0].length(); i++) {
            Set<String> s = new HashSet<>();
            for (String w : words)
                if (!s.add(w.substring(0, i) + w.substring(i + 1)))
                    return true;
        }
        return false;
    }


    //https://leetcode.com/problems/strings-differ-by-one-character/discuss/802871/Rabin-Karp-O(nm)/1145411
    public boolean differByOne2(String[] dict) {
        if (dict == null || dict.length < 2) {
            return false;
        }

        //if there are no duplicate in the dict, the words hashset is not necessary
        // We remove duplicates since they can't impact the result. Now all remaining pairs are guaranteed to differ by >= 1 characters.
//        Set<String> words = new HashSet();
//        for (String word : dict) {
//            words.add(word);
//        }

        Set<String> stringsWithAMissingChar = new HashSet<>();
        for (String word : dict) {
            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; ++i) {
                char temp = chars[i];//backtracking
                chars[i] = '#';   // This represents the missing character.
                String newString = new String(chars);
                if (stringsWithAMissingChar.contains(newString)) {
                    return true;  // Strings are equal in all characters except the '#', we know that they differ in this missing character because we removed duplicates before.
                } else {
                    stringsWithAMissingChar.add(newString);
                }
                chars[i] = temp;//backtracking recovering
            }
        }
        return false;
    }
}

package topinterviewquestions;

import java.util.HashSet;
import java.util.Set;

/*
A pangram is a sentence where every letter of the English alphabet appears at least once.

Given a string sentence containing only lowercase English letters, return true if sentence is a pangram, or false otherwise.



Example 1:

Input: sentence = "thequickbrownfoxjumpsoverthelazydog"
Output: true
Explanation: sentence contains at least one of every letter of the English alphabet.
Example 2:

Input: sentence = "leetcode"
Output: false


Constraints:

1 <= sentence.length <= 1000
sentence consists of lowercase English letters.
 */
public class Problem_1832_CheckiftheSentenceIsPangram {
    public boolean checkIfPangram(String s) {
        //不需要用set增加space，用bit即可，如果都包涵了26个字母，那temp必定等于max=11111。。。111
        int max = (1 << 26) - 1, temp = 0;

        for(char c : s.toCharArray()){
            temp |= 1 << c - 'a';
            if(temp == max) {
                return true;
            }
        }
        return false;
    }
}

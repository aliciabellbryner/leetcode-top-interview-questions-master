package topinterviewquestions;

import java.util.HashSet;
import java.util.Set;
/*
A string s is called good if there are no two different characters in s that have the same frequency.

Given a string s, return the minimum number of characters you need to delete to make s good.

The frequency of a character in a string is the number of times it appears in the string. For example, in the string "aab", the frequency of 'a' is 2, while the frequency of 'b' is 1.



Example 1:

Input: s = "aab"
Output: 0
Explanation: s is already good.
Example 2:

Input: s = "aaabbbcc"
Output: 2
Explanation: You can delete two 'b's resulting in the good string "aaabcc".
Another way it to delete one 'b' and one 'c' resulting in the good string "aaabbc".
Example 3:

Input: s = "ceabaacb"
Output: 2
Explanation: You can delete both 'c's resulting in the good string "eabaab".
Note that we only care about characters that are still in the string at the end (i.e. frequency of 0 is ignored).


Constraints:

1 <= s.length <= 105
s contains only lowercase English letters.
 */

public class Problem_1647_MinimumDeletionstoMakeCharacterFrequenciesUnique {
/*
https://leetcode.com/problems/minimum-deletions-to-make-character-frequencies-unique/discuss/927549/C%2B%2BJavaPython-3-Greedy
 */
    //Greedy works since we can only delete characters (we cannot add or replace characters).
//So, count each character first. For each 26 characters, check if it's count is already used. If so, delete characters until you find unused count, or reach zero.
    public int minDeletions(String s) {
        int cnt[] = new int[26], res = 0;
        Set<Integer> used = new HashSet<>();//放的是频率
        for (int i = 0; i < s.length(); ++i)
            ++cnt[s.charAt(i) - 'a'];
        for (int i = 0; i < 26; ++i) {
            while (cnt[i] > 0 && !used.add(cnt[i])) {//used.add(cnt[i]): true if this used set did not already contain cnt[i]
                --cnt[i];
                ++res;
            }
        }
        return res;
    }
}


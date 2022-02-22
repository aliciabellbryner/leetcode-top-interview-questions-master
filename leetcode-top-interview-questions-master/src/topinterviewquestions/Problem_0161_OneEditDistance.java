package topinterviewquestions;
/*
Given two strings s and t, return true if they are both one edit distance apart, otherwise return false.

A string s is said to be one distance apart from a string t if you can:

Insert exactly one character into s to get t.
Delete exactly one character from s to get t.
Replace exactly one character of s with a different character to get t.


Example 1:

Input: s = "ab", t = "acb"
Output: true
Explanation: We can insert 'c' into s to get t.
Example 2:

Input: s = "", t = ""
Output: false
Explanation: We cannot get t from s by only one step.


Constraints:

0 <= s.length, t.length <= 104
s and t consist of lowercase letters, uppercase letters, and digits.
 */
public class Problem_0161_OneEditDistance {
    //https://leetcode.com/problems/one-edit-distance/discuss/50098/My-CLEAR-JAVA-solution-with-explanation
    //Time complexity : O(max(Len(s), Len(t))) in the worst case when string lengths are close enough abs(ns - nt) <= 1, where N is a number of characters in the longest string. O(1) in the best case when abs(ns - nt) > 1.
    //Space complexity : O(max(Len(s), Len(t))) because strings are immutable in  Java and to create substring costs O(N) space.
    public boolean isOneEditDistance(String s, String t) {
        for (int i = 0; i < Math.min(s.length(), t.length()); i++) {
            if (s.charAt(i) != t.charAt(i)) {
                if (s.length() == t.length()) // s has the same length as t, so the only possibility is replacing one char in s and t
                    return s.substring(i + 1).equals(t.substring(i + 1));
                else if (s.length() < t.length()) // t is longer than s, so the only possibility is deleting one char from t
                    return s.substring(i).equals(t.substring(i + 1));
                else // s is longer than t, so the only possibility is deleting one char from s
                    return t.substring(i).equals(s.substring(i + 1));
            }
        }
        //All previous chars are the same, the only possibility is deleting the end char in the longer one of s and t
        return Math.abs(s.length() - t.length()) == 1;
    }
}

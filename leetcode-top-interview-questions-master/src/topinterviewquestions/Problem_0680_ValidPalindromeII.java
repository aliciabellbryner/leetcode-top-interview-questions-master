package topinterviewquestions;
/*
Given a string s, return true if the s can be palindrome after deleting at most one character from it.



Example 1:

Input: s = "aba"
Output: true
Example 2:

Input: s = "abca"
Output: true
Explanation: You could delete the character 'c'.
Example 3:

Input: s = "abc"
Output: false


Constraints:

1 <= s.length <= 105
s consists of lowercase English letters.
 */
public class Problem_0680_ValidPalindromeII {
    //https://leetcode.com/problems/valid-palindrome-ii/discuss/209252/Java-Python-Two-Pointers-with-Thinking-Process
    //Assuming i = 0, j = s.length() - 1;, s is a valid palindrome (as defined in the problem) if
    //
    //there is only one pair of i, j such that s.charAt(i) != s.charAt(j)
    //after the different pair hit, we try removing i or j, the characters in middle should be a palindrome.
    //there is no pair of i, j such that s.charAt(i) != s.charAt(j)
    public boolean validPalindrome(String s) {
        int i = 0, j = s.length() - 1;

        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return isPalindrome(s, i + 1, j) || isPalindrome(s, i, j - 1);
            }
            i++;
            j--;
        }

        return true;
    }

    /* Check is s[i...j] is palindrome. */
    private boolean isPalindrome(String s, int i, int j) {

        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }

        return true;
    }
}

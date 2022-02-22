package topinterviewquestions;
/*
Given a character array s, reverse the order of the words.

A word is defined as a sequence of non-space characters. The words in s will be separated by a single space.

Your code must solve the problem in-place, i.e. without allocating extra space.



Example 1:

Input: s = ["t","h","e"," ","s","k","y"," ","i","s"," ","b","l","u","e"]
Output: ["b","l","u","e"," ","i","s"," ","s","k","y"," ","t","h","e"]
Example 2:

Input: s = ["a"]
Output: ["a"]


Constraints:

1 <= s.length <= 105
s[i] is an English letter (uppercase or lowercase), digit, or space ' '.
There is at least one word in s.
s does not contain leading or trailing spaces.
All the words in s are guaranteed to be separated by a single space.
 */
public class Problem_0186_ReverseWordsInAStringII {

/*
Approach 1: Reverse the Whole String and Then Reverse Each Word
To have this problem in Amazon interview is a good situation, since input is a mutable structure and hence one could aim \mathcal{O}(1)O(1) space solution without any technical difficulties.

The idea is simple: reverse the whole string and then reverse each word.

fig

Algorithm

Let's first implement two functions:

reverse(l: list, left: int, right: int), which reverses array characters between left and right pointers. C++ users could directly use built-in std::reverse.

reverse_each_word(l: list), which uses two pointers to mark the boundaries of each word and previous function to reverse it.

Now reverseWords(s: List[str]) implementation is straightforward:

Reverse the whole string: reverse(s, 0, len(s) - 1).

Reverse each word: reverse_each_word(s).

Implementation

Current
1 / 2

Complexity Analysis

Time complexity: \mathcal{O}(N)O(N), it's two passes along the string.

Space complexity: \mathcal{O}(1)O(1), it's a constant space solution.

 */
class Solution {
	public void reverse(char[] s, int left, int right) {
		while (left < right) {
			char tmp = s[left];
			s[left++] = s[right];
			s[right--] = tmp;
		}
	}

	public void reverseEachWord(char[] s) {
		int n = s.length;
		int start = 0, end = 0;

		while (start < n) {
			// go to the end of the word
			while (end < n && s[end] != ' ') ++end;
			// reverse the word
			reverse(s, start, end - 1);
			// move to the next word
			start = end + 1;
			++end;
		}
	}

	public void reverseWords(char[] s) {
		// reverse the whole string
		reverse(s, 0, s.length - 1);

		// reverse each word
		reverseEachWord(s);
	}
}

}

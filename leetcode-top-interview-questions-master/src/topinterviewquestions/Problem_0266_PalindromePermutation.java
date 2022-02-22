package topinterviewquestions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/*
Given a string s, return true if a permutation of the string could form a palindrome.



Example 1:

Input: s = "code"
Output: false
Example 2:

Input: s = "aab"
Output: true
Example 3:

Input: s = "carerac"
Output: true


Constraints:

1 <= s.length <= 5000
s consists of only lowercase English letters.
 */
public class Problem_0266_PalindromePermutation {
/*
Approach #1 Brute Force [Accepted]
If a string with an even length is a palindrome, every character in the string must always occur an even number of times. If the string with an odd length is a palindrome, every character except one of the characters must always occur an even number of times. Thus, in case of a palindrome, the number of characters with odd number of occurrences can't exceed 1(1 in case of odd length and 0 in case of even length).

Based on the above observation, we can find the solution for the given problem. The given string could contain almost all the ASCII characters from 0 to 127. Thus, we iterate over all the characters from 0 to 127. For every character chosen, we again iterate over the given string ss and find the number of occurrences, chch, of the current character in ss. We also keep a track of the number of characters in the given string ss with odd number of occurrences in a variable \text{count}count.

If, for any character currently considered, its corresponding count, chch, happens to be odd, we increment the value of \text{count}count, to reflect the same. In case of even value of chch for any character, the \text{count}count remains unchanged.

If, for any character, the countcount becomes greater than 1, it indicates that the given string ss can't lead to the formation of a palindromic permutation based on the reasoning discussed above. But, if the value of \text{count}count remains lesser than 2 even when all the possible characters have been considered, it indicates that a palindromic permutation can be formed from the given string ss.


Complexity Analysis

Time complexity : O(n)O(n). We iterate constant number of times(128) over the string ss of length nn, i.e. O(128 \cdot n) = O(n)O(128⋅n)=O(n).

Space complexity : O(1)O(1). Constant extra space is used.

Approach #2 Using HashMap [Accepted]
Algorithm

From the discussion above, we know that to solve the given problem, we need to count the number of characters with odd number of occurrences in the given string ss. To do so, we can also make use of a hashmap, \text{map}map. This \text{map}map takes the form (\text{character}, \text{number of occurrences of character})(character,number of occurrences of character).

We traverse over the given string ss. For every new character found in ss, we create a new entry in the \text{map}map for this character with the number of occurrences as 1. Whenever we find the same character again, we update the number of occurrences appropriately.

At the end, we traverse over the \text{map}map created and find the number of characters with odd number of occurrences. If this \text{count}count happens to exceed 1 at any step, we conclude that a palindromic permutation isn't possible for the string ss. But, if we can reach the end of the string with \text{count}count lesser than 2, we conclude that a palindromic permutation is possible for ss.

The following animation illustrates the process.

Current
1 / 13

Complexity Analysis

Time complexity : O(n)O(n). We traverse over the given string ss with nn characters once. We also traverse over the \text{map}map which can grow up to a size of nn in case all characters in ss are distinct.

Space complexity : O(1)O(1). The \text{map}map can grow up to a maximum number of all distinct elements. However, the number of distinct characters are bounded, so as the space complexity.

Approach #3 Using Array [Accepted]
Algorithm

Instead of making use of the inbuilt Hashmap, we can make use of an array as a hashmap. For this, we make use of an array \text{map}map with length 128. Each index of this \text{map}map corresponds to one of the 128 ASCII characters possible.

We traverse over the string ss and put in the number of occurrences of each character in this \text{map}map appropriately as done in the last case. Later on, we find the number of characters with odd number of occurrences to determine if a palindromic permutation is possible for the string ss or not as done in previous approaches.

 **Complexity Analysis**
Time complexity : O(n)O(n). We traverse once over the string ss of length nn. Then, we traverse over the \text{map}map of length 128(constant).

Space complexity : O(1)O(1). Constant extra space is used for \text{map}map of size 128.

Approach #4 Single Pass [Accepted]:
Algorithm

Instead of first traversing over the string ss for finding the number of occurrences of each element and then determining the \text{count}count of characters with odd number of occurrences in ss, we can determine the value of \text{count}count on the fly while traversing over ss.

For this, we traverse over ss and update the number of occurrences of the character just encountered in the \text{map}map. But, whevenever we update any entry in \text{map}map, we also check if its value becomes even or odd. We start of with a \text{count}count value of 0. If the value of the entry just updated in mapmap happens to be odd, we increment the value of \text{count}count to indicate that one more character with odd number of occurrences has been found. But, if this entry happens to be even, we decrement the value of \text{count}count to indicate that the number of characters with odd number of occurrences has reduced by one.

But, in this case, we need to traverse till the end of the string to determine the final result, unlike the last approaches, where we could stop the traversal over \text{map}map as soon as the \text{count}count exceeded 1. This is because, even if the number of elements with odd number of occurrences may seem very large at the current moment, but their occurrences could turn out to be even when we traverse further in the string ss.

At the end, we again check if the value of \text{count}count is lesser than 2 to conclude that a palindromic permutation is possible for the string ss.


Complexity Analysis

Time complexity : O(n)O(n). We traverse over the string ss of length nn once only.

Space complexity : O(1)O(1). A mapmap of constant size(128) is used.

Approach #5 Using Set [Accepted]:
Algorithm

Another modification of the last approach could be by making use of a \text{set}set for keeping track of the number of elements with odd number of occurrences in ss. For doing this, we traverse over the characters of the string ss. Whenever the number of occurrences of a character becomes odd, we put its entry in the \text{set}set. Later on, if we find the same element again, lead to its number of occurrences as even, we remove its entry from the \text{set}set. Thus, if the element occurs again(indicating an odd number of occurrences), its entry won't exist in the \text{set}set.

Based on this idea, when we find a character in the string ss that isn't present in the \text{set}set(indicating an odd number of occurrences currently for this character), we put its corresponding entry in the \text{set}set. If we find a character that is already present in the \text{set}set(indicating an even number of occurrences currently for this character), we remove its corresponding entry from the \text{set}set.

At the end, the size of \text{set}set indicates the number of elements with odd number of occurrences in ss. If it is lesser than 2, a palindromic permutation of the string ss is possible, otherwise not.

Below code is inspired by @StefanPochmann


Complexity Analysis

Time complexity : O(n)O(n). We traverse over the string ss of length nn once only.

Space complexity : O(1)O(1). The \text{set}set can grow up to a maximum number of all distinct elements. However, the number of distinct characters are bounded, so as the space complexity.
 */
public class Solution2 {
	public boolean canPermutePalindrome(String s) {
		HashMap< Character, Integer > map = new HashMap < > ();
		for (int i = 0; i < s.length(); i++) {
			map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
		}
		int count = 0;
		for (char key: map.keySet()) {
			count += map.get(key) % 2;
		}
		return count <= 1;
	}
}

	public class Solution3 {
		public boolean canPermutePalindrome(String s) {
			int[] map = new int[128];
			for (int i = 0; i < s.length(); i++) {
				map[s.charAt(i)]++;
			}
			int count = 0;
			for (int key = 0; key < map.length && count <= 1; key++) {
				count += map[key] % 2;
			}
			return count <= 1;
		}
	}
	public class Solution4 {
		public boolean canPermutePalindrome(String s) {
			int[] map = new int[128];
			int count = 0;
			for (int i = 0; i < s.length(); i++) {
				map[s.charAt(i)]++;
				if (map[s.charAt(i)] % 2 == 0)
					count--;
				else
					count++;
			}
			return count <= 1;
		}
	}

	public class Solution5 {
		public boolean canPermutePalindrome(String s) {
			Set< Character > set = new HashSet< >();
			for (int i = 0; i < s.length(); i++) {
				if (!set.add(s.charAt(i)))
					set.remove(s.charAt(i));
			}
			return set.size() <= 1;
		}
	}



}

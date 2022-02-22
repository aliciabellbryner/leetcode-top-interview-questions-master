package topinterviewquestions;

import java.util.*;

/*
Given a string s, return all the palindromic permutations (without duplicates) of it.

You may return the answer in any order. If s has no palindromic permutation, return an empty list.



Example 1:

Input: s = "aabb"
Output: ["abba","baab"]
Example 2:

Input: s = "abc"
Output: []


Constraints:

1 <= s.length <= 16
s consists of only lowercase English letters.
 */
public class Problem_0267_PalindromePermutationII {
/*
Approach #1 Brute Force [Time Limit Exceeded]
The simplest solution is generate every possible permutation of the given string ss and check if the generated permutation is a palindrome. After this, the palindromic permuations can be added to a setset in order to eliminate the duplicates. At the end, we can return an array comprised of the elements of this setset as the resultant array.

Let's look at the way these permutations are generated. We make use of a recursive function permute which takes the index of the current element current_indexcurrent
i
​
 ndex as one of the arguments. Then, it swaps the current element with every other element in the array, lying towards its right, so as to generate a new ordering of the array elements. After the swapping has been done, it makes another call to permute but this time with the index of the next element in the array. While returning back, we reverse the swapping done in the current function call. Thus, when we reach the end of the array, a new ordering of the array's elements is generated.

The animation below depicts the ways the permutations are generated.

Current
1 / 11

Complexity Analysis

Time complexity : O((n+1)!)O((n+1)!). A total of n!n! permutations are possible. For every permutation generated, we need to check if it is a palindrome, each of which requires O(n)O(n) time.

Space complexity : O(n)O(n). The depth of the recursion tree can go upto nn.

Approach #2 Backtracking [Accepted]
Algorithm

It might be possible that no palindromic permutation could be possible for the given string ss. Thus, it is useless to generate the permutations in such a case. Taking this idea, firstly we check if a palindromic permutation is possible for ss. If yes, then only we proceed further with generating the permutations. To check this, we make use of a hashmap mapmap which stores the number of occurences of each character(out of 128 ASCII characters possible). If the number of characters with odd number of occurences exceeds 1, it indicates that no palindromic permutation is possible for ss. To look at this checking process in detail, look at Approach 4 of the article for Palindrome Permutation.

Once we are sure that a palindromic permutation is possible for ss, we go for the generation of the required permutations. But, instead of wildly generating all the permutations, we can include some smartness in the generation of permutations i.e. we can generate only those permutations which are already palindromes.

One idea to to do so is to generate only the first half of the palindromic string and to append its reverse string to itself to generate the full length palindromic string.

Based on this idea, by making use of the number of occurences of the characters in ss stored in mapmap, we create a string stst which contains all the characters of ss but with the number of occurences of these characters in stst reduced to half their original number of occurences in ss.

Thus, now we can generate all the permutations of this string stst and append the reverse of this permuted string to itself to create the palindromic permutations of ss.

In case of a string ss with odd length, whose palindromic permutations are possible, one of the characters in ss must be occuring an odd number of times. We keep a track of this character, chch, and it is kept separte from the string stst. We again generate the permutations for stst similarly and append the reverse of the generated permutation to itself, but we also place the character chch at the middle of the generated string.

In this way, only the required palindromic permutations will be generated. Even if we go with the above idea, a lot of duplicate strings will be generated.

In order to avoid generating duplicate palindromic permutations in the first place itself, as much as possible, we can make use of this idea. As discussed in the last approach, we swap the current element with all the elements lying towards its right to generate the permutations. Before swapping, we can check if the elements being swapped are equal. If so, the permutations generated even after swapping the two will be duplicates(redundant). Thus, we need not proceed further in such a case.

See this animation for a clearer understanding.

Current
1 / 9

Complexity Analysis

Time complexity : O\big((\frac{n}{2}+1)!\big)O((
2
n
​
 +1)!). Atmost \frac{n}{2}!
2
n
​
 ! permutations need to be generated in the worst case. Further, for each permutation generated, string.reverse() function will take n/4n/4 time.

Space complexity : O(n)O(n). The depth of recursion tree can go upto n/2n/2 in the worst case.
 */
public class Solution2 {
	Set< String > set = new HashSet< >();
	public List< String > generatePalindromes(String s) {
		int[] map = new int[128];
		char[] st = new char[s.length() / 2];
		if (!canPermutePalindrome(s, map))
			return new ArrayList< >();
		char ch = 0;
		int k = 0;
		for (int i = 0; i < map.length; i++) {
			if (map[i] % 2 == 1)
				ch = (char) i;
			for (int j = 0; j < map[i] / 2; j++) {
				st[k++] = (char) i;
			}
		}
		permute(st, 0, ch);
		return new ArrayList < String > (set);
	}
	public boolean canPermutePalindrome(String s, int[] map) {
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
	public void swap(char[] s, int i, int j) {
		char temp = s[i];
		s[i] = s[j];
		s[j] = temp;
	}
	void permute(char[] s, int l, char ch) {
		if (l == s.length) {
			set.add(new String(s) + (ch == 0 ? "" : ch) + new StringBuffer(new String(s)).reverse());
		} else {
			for (int i = l; i < s.length; i++) {
				if (s[l] != s[i] || l == i) {
					swap(s, l, i);
					permute(s, l + 1, ch);
					swap(s, l, i);
				}
			}
		}
	}
}

//discussion
	//AC Java solution with explanation
	//Basically, the idea is to perform permutation on half of the palindromic string and then form the full palindromic result.
public List<String> generatePalindromes(String s) {
	int odd = 0;
	String mid = "";
	List<String> res = new ArrayList<>();
	List<Character> list = new ArrayList<>();
	Map<Character, Integer> map = new HashMap<>();

	// step 1. build character count map and count odds
	for (int i = 0; i < s.length(); i++) {
		char c = s.charAt(i);
		map.put(c, map.containsKey(c) ? map.get(c) + 1 : 1);
		odd += map.get(c) % 2 != 0 ? 1 : -1;
	}

	// cannot form any palindromic string
	if (odd > 1) return res;

	// step 2. add half count of each character to list
	for (Map.Entry<Character, Integer> entry : map.entrySet()) {
		char key = entry.getKey();
		int val = entry.getValue();

		if (val % 2 != 0) mid += key;

		for (int i = 0; i < val / 2; i++) list.add(key);
	}

	// step 3. generate all the permutations
	getPerm(list, mid, new boolean[list.size()], new StringBuilder(), res);

	return res;
}

	// generate all unique permutation from list
	void getPerm(List<Character> list, String mid, boolean[] used, StringBuilder sb, List<String> res) {
		if (sb.length() == list.size()) {
			// form the palindromic string
			res.add(sb.toString() + mid + sb.reverse().toString());
			sb.reverse();
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			// avoid duplication
			if (i > 0 && list.get(i) == list.get(i - 1) && !used[i - 1]) continue;

			if (!used[i]) {
				used[i] = true; sb.append(list.get(i));
				// recursion
				getPerm(list, mid, used, sb, res);
				// backtracking
				used[i] = false; sb.deleteCharAt(sb.length() - 1);
			}
		}
	}


}

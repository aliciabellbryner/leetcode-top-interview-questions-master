package topinterviewquestions;

import java.util.HashSet;

/*
Given a string s, return the length of the longest repeating substrings. If no repeating substring exists, return 0.



Example 1:

Input: s = "abcd"
Output: 0
Explanation: There is no repeating substring.
Example 2:

Input: s = "abbaba"
Output: 2
Explanation: The longest repeating substrings are "ab" and "ba", each of which occurs twice.
Example 3:

Input: s = "aabcaabdaab"
Output: 3
Explanation: The longest repeating substring is "aab", which occurs 3 times.


Constraints:

1 <= s.length <= 2000
s consists of lowercase English letters.
 */
public class Problem_1062_LongestRepeatingSubstring {
    /*
    Split into two subtasks
Let's focus here on the solutions which are performing better than naive \mathcal{O}(N^2)O(N
2
 ) at least in the best/average cases.

Here we have "two in one" problem :

Perform a search by a substring length in the interval from 1 to N.

Check if there is a duplicate substring of a given length L.

Subtask one : Binary search by a substring length

A naive solution would be to check all possible string length one by one starting from N - 1: if there is a duplicate substring of length N - 1, then of length N - 2, etc. Note that if there is a duplicate substring of length k, that means that there is a duplicate substring of length k - 1. Hence one could use a binary search by string length here, and have the first problem solved in \mathcal{O}(\log N)O(logN) time.

pic

The binary search code is quite standard and we will use it here for all approaches to focus on much more interesting subtask number two.


Subtask two : Check if there is a duplicate substring of length L

We will discuss here three different ideas how to proceed. They are all based on sliding window + hashset, though their performance and space consumption are quite different.

Linear-time slice + hashset of already seen strings. \mathcal{O}((N - L) L)O((N−L)L) time complexity and huge space consumption in the case of large strings.

Linear-time slice + hashset of hashes of already seen strings. \mathcal{O}((N - L) L)O((N−L)L) time complexity and moderate space consumption even in the case of large strings.

Rabin-Karp = constant-time slice + hashset of hashes of already seen strings. Hashes are computed with the rolling hash algorithm. \mathcal{O}(N - L)O(N−L) time complexity and moderate space consumption even in the case of large strings.

pic



Approach 1: Binary Search + Hashset of Already Seen Strings
The idea is straightforward :

Move a sliding window of length L along the string of length N.

Check if the string in the sliding window is in the hashset of already seen strings.

If yes, the duplicate substring is right here.

If not, save the string in the sliding window in the hashset.

Current
1 / 10
Obvious drawback of this approach is a huge memory consumption in the case of large strings.


Complexity Analysis

Time complexity : \mathcal{O}(N \log N)O(NlogN) in the average case and \mathcal{O}(N^2)O(N
2
 ) in the worst case. One needs \mathcal{O}((N - L)L)O((N−L)L) for one duplicate check, and one does up to \mathcal{O}(\log N)O(logN) checks. Together that results in \mathcal{O}(\sum\limits_{L}{(N - L)L})O(
L
∑
​
 (N−L)L), i.e. in \mathcal{O}(N \log N)O(NlogN) in the average case and in \mathcal{O}(N^2)O(N
2
 ) in the worst case of L close to N/2N/2.

Space complexity : \mathcal{O}(N^2)O(N
2
 ) to keep the hashset.


Approach 2: Binary Search + Hashset of Hashes of Already Seen Strings
To reduce the memory consumption by the hashset structure, one could store not the full strings, but their hashes.

The drawback of this approach is a time performance, which is still not always linear.

pic


Complexity Analysis

Time complexity : \mathcal{O}(N \log N)O(NlogN) in the average case and \mathcal{O}(N^2)O(N
2
 ) in the worst case. One needs \mathcal{O}((N - L)L)O((N−L)L) for one duplicate check, and one does up to \mathcal{O}(\log N)O(logN) checks. Together that results in \mathcal{O}(\sum\limits_{L}{(N - L)L})O(
L
∑
​
 (N−L)L), i.e. in \mathcal{O}(N \log N)O(NlogN) in the average case and in \mathcal{O}(N^2)O(N
2
 ) in the worst case of L close to N/2N/2.

Space complexity : \mathcal{O}(N)O(N) to keep the hashset.



Approach 3: Binary Search + Rabin-Karp
Rabin-Karp algorithm is used to perform a multiple pattern search in a linear time and with a moderate memory consumption suitable for the large strings.

The linear time implementation of this idea is a bit tricky because of two technical problems:

How to implement a string slice in a constant time?

Hashset memory consumption could be huge for very long strings. One could keep the string hash instead of string itself but hash generation costs \mathcal{O}(L)O(L) for the string of length L, and the complexity of algorithm would be \mathcal{O}((N - L)L)O((N−L)L), N - L for the slice and L for the hash generation. One has to think how to generate hash in a constant time here.

Let's now address these problems.

String slice in a constant time

That's a very language-dependent problem. For the moment for Java and Python there is no straightforward solution, and to move sliding window in a constant time one has to convert string to another data structure.

The simplest solution both for Java and Python is to convert string to integer array of ascii-values.

Rolling hash : hash generation in a constant time

To generate hash of array of length L, one needs \mathcal{O}(L)O(L) time.

How to have constant time of hash generation? Use the advantage of slice: only one integer in, and only one - out.

That's the idea of rolling hash. Here we'll implement the simplest one, polynomial rolling hash. Beware that's polynomial rolling hash is NOT the Rabin fingerprint.

Since one deals here with lowercase English letters, all values in the integer array are between 0 and 25 : arr[i] = (int)S.charAt(i) - (int)'a'.
So one could consider string abcd -> [0, 1, 2, 3] as a number in a numeral system with the base 26. Hence abcd -> [0, 1, 2, 3] could be hashed as

h_0 = 0 \times 26^3 + 1 \times 26^2 + 2 \times 26^1 + 3 \times 26^0h
0
​
 =0×26
3
 +1×26
2
 +2×26
1
 +3×26
0


Let's write the same formula in a generalised way, where c_ic
i
​
  is an integer array element and a = 26a=26 is a system base.

h_0 = c_0 a^{L - 1} + c_1 a^{L - 2} + ... + c_i a^{L - 1 - i} + ... + c_{L - 1} a^1 + c_L a^0h
0
​
 =c
0
​
 a
L−1
 +c
1
​
 a
L−2
 +...+c
i
​
 a
L−1−i
 +...+c
L−1
​
 a
1
 +c
L
​
 a
0


h_0 = \sum_{i = 0}^{L - 1}{c_i a^{L - 1 - i}}h
0
​
 =∑
i=0
L−1
​
 c
i
​
 a
L−1−i


Now let's consider the slice abcd -> bcde. For int arrays that means [0, 1, 2, 3] -> [1, 2, 3, 4], to remove number 0 and to add number 4.

h_1 = (h_0 - 0 \times 26^3) \times 26 + 4 \times 26^0h
1
​
 =(h
0
​
 −0×26
3
 )×26+4×26
0


In a generalised way

h_1 = (h_0 a - c_0 a^L) + c_{L + 1}h
1
​
 =(h
0
​
 a−c
0
​
 a
L
 )+c
L+1
​


Now hash regeneration is perfect and fits in a constant time. There is one more issue to address: possible overflow problem.

How to avoid overflow

a^La
L
  could be a large number and hence the idea is to set limits to avoid the overflow. To set limits means to limit a hash by a given number called modulus and use everywhere not hash itself but h % modulus.

It's quite obvious that modulus should be large enough, but how large? Here one could read more about the topic, for the problem here 2^{24}2
24
  is enough.

In a real life, when not all testcases are known in advance, one has to check if the strings with equal hashes are truly equal. Such false-positive strings could happen because of a modulus which is too small and strings which are too long. That leads to Rabin-Karp time complexity \mathcal{O}(NL)O(NL) in the worst case then almost all strings are false-positive. Here it's not the case because all testcases are known and one could adjust the modulus.

Another one overflow issue here is purely Java related. While in Python the hash regeneration goes perfectly fine, in Java the same thing is better to rewrite to avoid long overflow.


Rabin-Karp algorithm

search(L) :

Compute the hash of substring S.substring(0, L) and initiate the hashset of already seen substring with this value.

Iterate over the start position of substring : from 1 to N - LN−L.

Compute rolling hash based on the previous hash value.

Return start position if the hash is in the hashset, because that means one met the duplicate.

Otherwise, add hash in the hashset.

Return -1, that means there is no duplicate string of length L.

Implementation


Complexity Analysis

Time complexity : \mathcal{O}(N \log N)O(NlogN). \mathcal{O}(\log N)O(logN) for the binary search and \mathcal{O}(N)O(N) for Rabin-Karp algorithm.
Space complexity : \mathcal{O}(N)O(N) to keep the hashset.
     */

    class Solution1 {
        /*
          Search a substring of given length
          that occurs at least 2 times.
          Return start position if the substring exits and -1 otherwise.
          */
        public int search(int L, int n, String S) {
            HashSet<String> seen = new HashSet();
            String tmp;
            for(int start = 0; start < n - L + 1; ++start) {
                tmp = S.substring(start, start + L);
                if (seen.contains(tmp)) return start;
                seen.add(tmp);
            }
            return -1;
        }

        public int longestRepeatingSubstring(String S) {
            int n = S.length();
            // binary search, L = repeating string length
            int left = 1, right = n;
            int L;
            while (left <= right) {
                L = left + (right - left) / 2;
                if (search(L, n, S) != -1) left = L + 1;
                else right = L - 1;
            }

            return left - 1;
        }
    }
    class Solution2 {
        /*
          Search a substring of given length
          that occurs at least 2 times.
          Return start position if the substring exits and -1 otherwise.
          */
        public int search(int L, int n, String S) {
            HashSet<Integer> seen = new HashSet();
            String tmp;
            int h;
            for(int start = 0; start < n - L + 1; ++start) {
                tmp = S.substring(start, start + L);
                h = tmp.hashCode();
                if (seen.contains(h)) return start;
                seen.add(h);
            }
            return -1;
        }

        public int longestRepeatingSubstring(String S) {
            int n = S.length();
            // binary search, L = repeating string length
            int left = 1, right = n;
            int L;
            while (left <= right) {
                L = left + (right - left) / 2;
                if (search(L, n, S) != -1) left = L + 1;
                else right = L - 1;
            }

            return left - 1;
        }
    }
    class Solution3 {
        /*
        Rabin-Karp with polynomial rolling hash.
            Search a substring of given length
            that occurs at least 2 times.
            Return start position if the substring exits and -1 otherwise.
            */
        public int search(int L, int a, long modulus, int n, int[] nums) {
            // compute the hash of string S[:L]
            long h = 0;
            for(int i = 0; i < L; ++i) h = (h * a + nums[i]) % modulus;

            // already seen hashes of strings of length L
            HashSet<Long> seen = new HashSet();
            seen.add(h);
            // const value to be used often : a**L % modulus
            long aL = 1;
            for (int i = 1; i <= L; ++i) aL = (aL * a) % modulus;

            for(int start = 1; start < n - L + 1; ++start) {
                // compute rolling hash in O(1) time
                h = (h * a - nums[start - 1] * aL % modulus + modulus) % modulus;
                h = (h + nums[start + L - 1]) % modulus;
                if (seen.contains(h)) return start;
                seen.add(h);
            }
            return -1;
        }

        public int longestRepeatingSubstring(String S) {
            int n = S.length();
            // convert string to array of integers
            // to implement constant time slice
            int[] nums = new int[n];
            for(int i = 0; i < n; ++i) nums[i] = (int)S.charAt(i) - (int)'a';
            // base value for the rolling hash function
            int a = 26;
            // modulus value for the rolling hash function to avoid overflow
            long modulus = (long)Math.pow(2, 24);

            // binary search, L = repeating string length
            int left = 1, right = n;
            int L;
            while (left <= right) {
                L = left + (right - left) / 2;
                if (search(L, a, modulus, n, nums) != -1) left = L + 1;
                else right = L - 1;
            }

            return left - 1;
        }
    }

    //diss
    //O(n^3) -> 2  O(n^2 logn) ->  2*O (n^2) -> O (n logn) -> O(n)

    /*
    O (n^3) Approach 1.
loop result from max to 1, once found a max, then return

public int longestRepeatingSubstring(String S) {
	Set<String> set = new HashSet<>();
	int max = S.length() - 1, i = 0;
	for (;i <= S.length();i++) {
		int j = i;
		if (j + max > S.length()) {
			if (--max == 0) break;
			i = -1;
			set.clear();
			continue;
		}
		String k = S.substring(j,j+max);
		if (!set.add(k)) {
			return max;
		}
	}
	return max;
}
O (n^3) Approach 2.
loop result from 1 to max, once meet a ans with not found duplicate string, then return ans - 1

public int longestRepeatingSubstring(String S) {
	int l = S.length(), max = 0, i = 0;
	Set<String> s = new HashSet<>();
	while (i <= l) {
		int j = i;
		if (j + max == l) {
			return max;
		}
		String k = S.substring(j,j + max + 1);
		if (!s.add(k)) {
			i = 0;
			s.clear();
			max++;
		} else {
			i++;
		}
	}
	return max;
}
O (n^2 log n) Approach 1
get n suffix of the string. "abc" -> "abc","bc","c"
then sort them, if have two common prefix, they must be neighbors. for loop them find longest common prefix.

public int longestRepeatingSubstring(String S) {
	int l = S.length();
	String[] suffix = new String[l];
	for (int i = 0; i < l; i++) suffix[i] = S.substring(i);
	Arrays.sort(suffix);
	int max = 0;
	for (int i = 1; i < l; i++) {
		int j = 0;
		for (; j < Math.min(suffix[i].length(),suffix[i-1].length()); j++) {
			if (suffix[i].charAt(j) != suffix[i-1].charAt(j)) break;
		}
		max = Math.max(max,j);
	}
	return max;
}
O (n^2 log n) Approach 2
binary search -> if length 3 have duplicate pattern, length 2 must have.
so if we can search the answer,s = mid + 1. if not , e = mid - 1.
max is 's - 1';

public int longestRepeatingSubstring(String S) {
	char[] cs = S.toCharArray();
	int s = 1, e = cs.length - 1;
	while (s <= e) {
		int mid = (s + e) / 2;
		if (search(cs,mid)) {
			s = mid + 1;
		} else {
			e = mid - 1;
		}
	}
	return s - 1;
}
boolean search(char[] cs,int k) {
	Set<String> s = new HashSet<>();
	for (int i = 0; i <= cs.length - k; i++) {
		if (!s.add(new String(cs,i,k)))
			return true;
	}
	return false;
}
O (n^2 ) Approach 1
dp[i][j] means end with i, end with j , what's max length of common string.
abcbc. dp[2][4] = 2 because bc == bc, abc != cbc

public int longestRepeatingSubstring(String S) {
	int l = S.length();
	int[][] dp = new int[l+1][l+1];
	int res = 0;
	for (int i = 1; i <= l; i++) {
		for (int j = i + 1; j <= l; j++) {
			if (S.charAt(i - 1) == S.charAt(j - 1)) {
				dp[i][j] = dp[i - 1][j - 1] + 1;
				res = Math.max(dp[i][j],res);
			}
		}
	}
	return res;
}
O (n^2 ) Approach 2
we could use MSD radix sort, we can sort n string , in O(26 * N * N)

public int longestRepeatingSubstring(String S) {
        int l = S.length();
        String[] suffix = new String[l];
        for (int i = 0; i < l; i++) suffix[i] = S.substring(i);
        msdRadixSort(suffix);
        int max = 0;
        for (int i = 1; i < l; i++) {
            int j = 0;
            for (; j < Math.min(suffix[i].length(),suffix[i-1].length()); j++) {
                if (suffix[i].charAt(j) != suffix[i-1].charAt(j)) break;
            }
            max = Math.max(max,j);
        }
        return max;
    }
    void msdRadixSort(String[] input) {
        sort(input, 0, input.length - 1, 0, new String[input.length]);
    }
    private void sort(String[] input, int lo, int hi, int depth, String[] aux) {
        if (lo >= hi) return;
        int[] cnt = new int[28];
        for (int i = lo; i <= hi; i++) {
            cnt[charAt(input[i], depth) + 1]++;
        }
        for (int i = 1; i < 28; i++) cnt[i] += cnt[i - 1];
        for (int i = lo; i <= hi; i++) {
            aux[cnt[charAt(input[i], depth)]++] = input[i];
        }
        for (int i = lo; i <= hi; i++) input[i] = aux[i - lo];
        for (int i = 0; i < 27; i++)
            sort(input, lo + cnt[i], lo + cnt[i + 1] - 1, depth + 1, aux);
    }
    private int charAt(String str, int i) {
        if (i >= str.length()) return 0;
        return str.charAt(i) - 'a' + 1;
    }
O (n logn ) Approach
binary search with rolling hash monte carlo version
below version is Monte Carlo version. Return match if hash match.
there exists another version for 100% correctness.
Las Vegas version. Check for substring match if hash match;
continue search if false collision.
Theory. If Q is a sufficiently large random prime (about M * N ^ 2),
then the probability of a false collision is about 1 / N.
Practice. Choose Q to be a large prime (but not so large to cause overflow).
Under reasonable assumptions, probability of a collision is about 1 / Q

Monte Carlo version.
・Always runs in n logn time.
・Extremely likely to return correct answer (but not always!).
Las Vegas version.
・Always returns correct answer.
・Extremely likely to run in n logn time (but worst case is n^2 log n)

public int longestRepeatingSubstring(String S) {
	char[] cs = S.toCharArray();
	int s = 1, e = cs.length - 1;
	while (s <= e) {
		int mid = (s + e) / 2;
		if (search(cs,mid)) {
			s = mid + 1;
		} else {
			e = mid - 1;
		}
	}
	return s - 1;
}
boolean search(char[] cs,int k) {
	Set<Integer> s = new HashSet<>();
	long mod = 10000000007L, pow = 1, cur = 0;
	for (int i = 0; i < cs.length; i++) {
		cur = (cur * 26 + (cs[i] - 'a')) % mod;
		if (i >= k) {
			cur = (cur - ((cs[i - k] - 'a') * pow % mod) + mod) % mod;
		} else {
			 pow = pow * 26  % mod;
		}
		if (i >= k - 1) {
			if (!s.add((int)cur)) return true;
		}
	}
	return false;
}
average O (n ) Approach
use Ukkonen’s algorithm to build the suffix array in O(n)
then compare i and i+1, i from 0 to n - 2; in worst case, this algorithm toke O(n^2), because the time compare two neighbor suffix cost O(n), there are n neighbor, so is O(n^2), but if string is random, there have no this problem.

worst case O (n ) Approach
use Ukkonen’s algorithm to build the suffix tree in O(n)
then find deepest internal node that is the answer, find deepest internal node cost O(n)
so totally O(n)

class Solution {
    public int longestRepeatingSubstring(String S) {
        SuffixTree st = new SuffixTree(S);
        return st.deepInternalNode();
    }
}
class SuffixTree {

    public static final int MAX_CHAR = 128;

    class Node {
        int start, index = -1, end;
        Node suffixLink;
        Node[] chds = new Node[MAX_CHAR];
        public Node(int start, int end) {
            this.start = start;
            this.end = end;
            if (end != inf) suffixLink = root;
        }
        int len() {
            return Math.min(end, globalEnd)  - start + 1;
        }
    }

    Node root = new Node(-1, -1);
    Node lastNewNode = null;
    int globalEnd = -1;
    int inf = Integer.MAX_VALUE / 2;
    char[] text;

    Node activeNode = root;
    int activeEdgeAsTextIndex = -1;
    int activeLength = 0;
    int remaining = 0;

    boolean tryWalkDown(Node cur) {
        int edgeLen = cur.len();
        if (activeLength >= edgeLen) {
            activeEdgeAsTextIndex += edgeLen;
            activeLength -= edgeLen;
            activeNode = cur;
            return true;
        }
        return false;
    }

    private void extend(char c) {
        text[++globalEnd] = c;
        remaining++;
        lastNewNode = null;
        while (remaining > 0) {
            if (activeLength == 0) activeEdgeAsTextIndex = globalEnd;
            if (activeNode.chds[text[activeEdgeAsTextIndex]] == null) {
                activeNode.chds[text[activeEdgeAsTextIndex]] = new Node(globalEnd, inf);
                addSuffixLinkIfLastNodeExists(activeNode);
            } else {
                Node chd = activeNode.chds[text[activeEdgeAsTextIndex]];
                if (tryWalkDown(chd)) continue;
                if (text[chd.start + activeLength] == c) { // do nothing
                    addSuffixLinkIfLastNodeExists(activeNode);
                    activeLength++;
                    break;
                }
                Node internalSplit = new Node(chd.start, chd.start + activeLength - 1);
                activeNode.chds[text[activeEdgeAsTextIndex]] = internalSplit;
                internalSplit.chds[c] = new Node(globalEnd, inf);
                chd.start += activeLength;
                internalSplit.chds[text[chd.start]] = chd;
                addSuffixLinkIfLastNodeExists(internalSplit);
            }
            remaining--;
            if (activeNode != root) activeNode = activeNode.suffixLink;
            else if (activeLength > 0) {
                activeLength--;
                activeEdgeAsTextIndex = globalEnd - remaining + 1;
            }
        }
    }

    private void addSuffixLinkIfLastNodeExists(Node node) {
        if (lastNewNode != null)
            lastNewNode.suffixLink = node;
        lastNewNode = node;
    }

    private void setSuffixIndexByDFS(Node cur, int labelHeight) {
        if (cur.suffixLink == null && cur.start != -1) {
            cur.index = globalEnd + 1 - labelHeight;
        } else {
            for (int i = 0; i < MAX_CHAR; i++) {
                if (cur.chds[i] == null) continue;
                setSuffixIndexByDFS(cur.chds[i], labelHeight + cur.chds[i].len());
            }
        }
    }

    public SuffixTree(String str) {
        this.text = new char[str.length() + 1];
        for (int i = 0; i <= str.length(); i++) {
            char c = (i == str.length() ?  '$' : str.charAt(i));
            extend(c);
        }
        setSuffixIndexByDFS(root, 0);
    }

    int maxDepth;
    public int deepInternalNode() {
        maxDepth = 0;
        help(root, 0);
        return maxDepth;
    }
    void help(Node cur, int dep) {
        if (cur == null) return;
        if (cur.index == -1) { // internal
            maxDepth = Math.max(maxDepth, dep);
            for (int i = 0; i < MAX_CHAR; i++) {
                if (cur.chds[i] == null) continue;
                help(cur.chds[i], dep + cur.chds[i].len());
            }
        }
    }
     */



//Straightforward Trie solution O(N^2)
    class Solution4 {
        class TrieNode{
            TrieNode[] next;
            public TrieNode(){
                next = new TrieNode[26];
            }
        }

        public int longestRepeatingSubstring(String S) {
            char[] A = S.toCharArray();
            int res = 0;
            TrieNode root = new TrieNode();
            for(int i=0; i<S.length(); i++){
                TrieNode cur = root;
                for(int j=i; j<S.length(); j++){
                    if(cur.next[A[j]-'a']==null){
                        TrieNode newNode = new TrieNode();
                        cur.next[A[j]-'a'] = newNode;
                        cur = newNode;
                    }
                    else{
                        res = Math.max(res,j-i+1);
                        cur = cur.next[A[j]-'a'];
                    }
                }
            }
            return res;
        }
    }

    //Slightly more concise
    public int longestRepeatingSubstring(String s) {
        int r = 0;
        Node trie = new Node();
        char[] a = s.toCharArray();
        for (int i = 0; i < a.length; i++) {
            Node node = trie;
            for (int j = i; j < a.length; node = node.nodes[a[j++] - 'a'])
                if (node.nodes[a[j] - 'a'] != null)
                    r = Math.max(r, j - i + 1);
                else node.nodes[a[j] - 'a'] = new Node();
        }
        return r;
    }

    class Node {
        final Node[] nodes = new Node[26];
    }
}

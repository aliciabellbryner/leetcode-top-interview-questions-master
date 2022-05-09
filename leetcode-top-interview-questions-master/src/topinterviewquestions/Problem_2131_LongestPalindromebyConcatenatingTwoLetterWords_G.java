package topinterviewquestions;

import java.util.HashMap;

public class Problem_2131_LongestPalindromebyConcatenatingTwoLetterWords_G {
    //https://leetcode.com/problems/longest-palindrome-by-concatenating-two-letter-words/discuss/1675343/Python3-Java-C%2B%2B-Counting-Mirror-Words-O(n)
    /*
    2 letter words can be of 2 types:

Where both letters are same
Where both letters are different
Based on the above information:

If we are able to find the mirror of a word, ans += 4
The variable unpaired is used to store the number of unpaired words with both letters same.
Unpaired here means a word that has not found its mirror word.
At the end if unpaired same letter words are > 0, we can use one of them as the center of the palindromic string.
     */
    //Solution 1: With HashMap
    public int longestPalindrome(String[] words) {
        HashMap<String, Integer> map = new HashMap();
        int unpaired = 0, res = 0;
        //The variable unpaired is used to store the number of unpaired words with both letters same.
        //Unpaired here means a word that has not found its mirror word.
        for (String w: words) {
            if (!map.containsKey(w)) {
                map.put(w, 0);
            }
            if (w.charAt(0) == w.charAt(1)) {
                if (map.get(w) > 0) {//If we are able to find the mirror of a word, res += 4
                    unpaired--;
                    map.put(w, map.get(w) - 1);
                    res += 4;
                }
                else {
                    map.put(w, map.get(w) + 1);
                    unpaired++;
                }
            }
            else {
                String rev = Character.toString(w.charAt(1)) +
                        Character.toString(w.charAt(0));
                if (map.containsKey(rev) && map.get(rev) > 0) {
                    res += 4;
                    map.put(rev, map.get(rev) - 1);
                }
                else map.put(w, map.get(w) + 1);
            }

        }
        if (unpaired > 0) {//At the end if unpaired same letter words are > 0, we can use one of them as the center of the palindromic string.
            res += 2;
        }
        return res;
    }
    //Solution 2: Without HashMap
    public int longestPalindrome2(String[] words) {
        int counter[][] = new int[26][26];
        int ans = 0;
        for (String w: words) {
            int a = w.charAt(0) - 'a', b = w.charAt(1) - 'a';
            if (counter[b][a] > 0) {
                ans += 4;
                counter[b][a]--;
            }
            else counter[a][b]++;
        }
        for (int i = 0; i < 26; i++) {
            if (counter[i][i] > 0) {
                ans += 2;
                break;
            }
        }
        return ans;
    }
}

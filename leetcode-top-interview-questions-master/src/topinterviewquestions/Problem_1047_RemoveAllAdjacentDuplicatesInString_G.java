package topinterviewquestions;

public class Problem_1047_RemoveAllAdjacentDuplicatesInString_G {
    // https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/discuss/294893/JavaC%2B%2BPython-Two-Pointers-and-Stack-Solution
    //Solution 1: Two Pointers
    //i refers to the index to set next character in the output string.
    //j refers to the index of current iteration in the input string.
    //
    //Iterate characters of S one by one by increasing j.
    //
    //If S[j] is same as the current last character S[i - 1],
    //we remove duplicates by doing i -= 2.
    //
    //If S[j] is different as the current last character S[i - 1],
    //we set S[i] = S[j] and increment i++.
    public String removeDuplicates(String s) {
        int i = 0, n = s.length();
        char[] res = s.toCharArray();
        for (int j = 0; j < n; ++j, ++i) {
            res[i] = res[j];
            if (i > 0 && res[i - 1] == res[i]) // count = 2
                i -= 2;
        }
        return new String(res, 0, i);
    }

    //Solution 2: Stack
    //Keep a res as a characters stack.
    //Iterate characters of S one by one.
    //
    //If the next character is same as the last character in res,
    //pop the last character from res.
    //In this way, we remove a pair of adjacent duplicates characters.
    //
    //If the next character is different,
    //we append it to the end of res.
    public String removeDuplicates2(String S) {
        StringBuilder sb = new StringBuilder();
        for (char c : S.toCharArray()) {
            int size = sb.length();
            if (size > 0 && sb.charAt(size - 1) == c) {
                sb.deleteCharAt(size - 1);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}

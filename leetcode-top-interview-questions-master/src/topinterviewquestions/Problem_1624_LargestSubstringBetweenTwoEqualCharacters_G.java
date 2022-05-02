package topinterviewquestions;

public class Problem_1624_LargestSubstringBetweenTwoEqualCharacters_G {
    //https://leetcode.com/problems/largest-substring-between-two-equal-characters/discuss/899464/JavaPython-3-One-pass-O(n)-114-liners-w-brief-explanation-and-analysis.
    public int maxLengthBetweenEqualCharacters(String s) {
        int[] indices = new int[26];
        int maxLen = -1;
        for (int i = 0; i < s.length(); ++i) {
            int idx = s.charAt(i) - 'a';
            if (indices[idx] > 0) {
                maxLen = Math.max(maxLen, i - indices[idx]);
            }else {
                indices[idx] = i + 1;
            }
        }
        return maxLen;
    }
}

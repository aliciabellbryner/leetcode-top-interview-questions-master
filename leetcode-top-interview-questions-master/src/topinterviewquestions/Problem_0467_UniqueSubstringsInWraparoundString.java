package topinterviewquestions;

public class Problem_0467_UniqueSubstringsInWraparoundString {
    //https://leetcode.com/problems/unique-substrings-in-wraparound-string/discuss/95439/Concise-Java-solution-using-DP
    //思路就是用cnt[i]表示以i+'a'结尾的可以达到的最长连续子序列长度，这样不仅可以算出最大长度，还可以防止duplicate
    public int findSubstringInWraproundString(String p) {
        // [i] is the maximum unique substring end with ith letter.
        // 0 - 'a', 1 - 'b', ..., 25 - 'z'.
        int[]  cnt = new int[26];//cnt[i]表示以i+'a'结尾的可以达到的最长连续子序列长度
        // store longest contiguous substring ends at current position.
        int curMax = 0;
        for (int i = 0; i < p.length(); i++) {
            if (i > 0 && (p.charAt(i) - p.charAt(i - 1) == 1 || (p.charAt(i - 1) - p.charAt(i) == 25))) {
                curMax++;
            }
            else {
                curMax = 1;
            }
            if(curMax > cnt[p.charAt(i) - 'a']) {
                cnt[p.charAt(i) - 'a'] = curMax;//如果curMax比之前的大就更新
            }
        }

        // Sum to get result
        int res = 0;
        for (int i = 0; i < 26; i++) {
            res += cnt[i];
        }
        return res;
    }
}

package topinterviewquestions;

//求以相同字母开头和结尾的子串总数 - 力扣

public class Problem_2083_SubstringsThatBeginandEndWiththeSameLetter_G {
    class Solution {
        public long numberOfSubstrings(String s) {
            long ans = 0;
            int[] count = new int[128];

            for (final char c : s.toCharArray()) {
                ans += count[c] + 1;
                ++count[c];
            }

            return ans;
        }
    }
}

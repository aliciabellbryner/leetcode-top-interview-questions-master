package topinterviewquestions;

public class Problem_1446_ConsecutiveCharacters_G {
    //https://leetcode-cn.com/problems/consecutive-characters/solution/lian-xu-zi-fu-by-leetcode-solution-lctm/
    class Solution {
        public int maxPower(String s) {
            int ans = 1, cnt = 1;
            for (int i = 1; i < s.length(); ++i) {
                if (s.charAt(i) == s.charAt(i - 1)) {
                    ++cnt;
                    ans = Math.max(ans, cnt);
                } else {
                    cnt = 1;
                }
            }
            return ans;
        }
    }

}

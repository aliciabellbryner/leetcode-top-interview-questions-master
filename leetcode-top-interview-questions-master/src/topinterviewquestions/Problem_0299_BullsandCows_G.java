package topinterviewquestions;

public class Problem_0299_BullsandCows_G {
    //https://leetcode.com/problems/bulls-and-cows/solution/
    //Approach 2: One Pass
    //Time complexity: O(N), we do one pass over the strings.
    //Space complexity: O(1) to keep hashmap (or array) h which contains at most 10 elements.
    class Solution {
        public String getHint(String secret, String guess) {
            int[] h = new int[10];

            int bulls = 0, cows = 0;
            int N = guess.length();
            for (int i = 0; i < N; i++) {
                char sc = secret.charAt(i);
                char gc = guess.charAt(i);
                if (sc == gc) {
                    bulls++;
                } else {
                    if (h[sc - '0'] < 0) {
                        cows++;
                    }
                    if (h[gc - '0'] > 0) {
                        cows++;
                    }
                    h[sc - '0']++;
                    h[gc - '0']--;
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append(bulls);
            sb.append("A");
            sb.append(cows);
            sb.append("B");
            return sb.toString();
        }
    }
}

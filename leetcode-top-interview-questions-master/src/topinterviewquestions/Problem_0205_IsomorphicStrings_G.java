package topinterviewquestions;

import java.util.HashMap;
import java.util.Map;

public class Problem_0205_IsomorphicStrings_G {
    //solution: 思路就是把string转变成每个char在string中最早出现的index
    //比如s = "paper", t = "title"，s就变成0 1 0 3 4, t就变成0 1 0 3 4
    class Solution {
        private String transformString(String s) {
            Map<Character, Integer> indexMapping = new HashMap<>();
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < s.length(); ++i) {
                char c1 = s.charAt(i);

                if (!indexMapping.containsKey(c1)) {
                    indexMapping.put(c1, i);
                }

                builder.append(Integer.toString(indexMapping.get(c1)));
                builder.append(" ");
            }
            return builder.toString();
        }

        public boolean isIsomorphic(String s, String t) {
            return transformString(s).equals(transformString(t));
        }
    }
}

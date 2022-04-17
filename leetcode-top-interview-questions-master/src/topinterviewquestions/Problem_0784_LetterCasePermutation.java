package topinterviewquestions;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Problem_0784_LetterCasePermutation {
    //There we go! Is that a typical BFS/DFS problem? Yes, you are right!
    //Be careful to check whether a character is a digit or a letter(lower case or upper case).
    //BFS Solution:
    class Solution {
        public List<String> letterCasePermutation(String S) {
            if (S == null) {
                return new LinkedList<>();
            }
            Queue<String> queue = new LinkedList<>();
            queue.offer(S);

            for (int i = 0; i < S.length(); i++) {
                if (Character.isDigit(S.charAt(i))) continue;
                int size = queue.size();
                for (int j = 0; j < size; j++) {
                    String cur = queue.poll();
                    char[] chs = cur.toCharArray();

                    chs[i] = Character.toUpperCase(chs[i]);
                    queue.offer(String.valueOf(chs));

                    chs[i] = Character.toLowerCase(chs[i]);
                    queue.offer(String.valueOf(chs));
                }
            }

            return new LinkedList<>(queue);
        }
    }


    //DFS Solution:
    class Solution2 {
        public List<String> letterCasePermutation(String S) {
            if (S == null) {
                return new LinkedList<>();
            }

            List<String> res = new LinkedList<>();
            helper(S.toCharArray(), res, 0);
            return res;
        }

        public void helper(char[] chs, List<String> res, int pos) {
            if (pos == chs.length) {
                res.add(new String(chs));
                return;
            }
            if (chs[pos] >= '0' && chs[pos] <= '9') {
                helper(chs, res, pos + 1);
                return;
            }

            chs[pos] = Character.toLowerCase(chs[pos]);
            helper(chs, res, pos + 1);

            chs[pos] = Character.toUpperCase(chs[pos]);
            helper(chs, res, pos + 1);
        }
    }
}

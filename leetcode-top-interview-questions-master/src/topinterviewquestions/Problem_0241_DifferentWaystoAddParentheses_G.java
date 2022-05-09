package topinterviewquestions;

import java.util.*;

public class Problem_0241_DifferentWaystoAddParentheses_G {

    //https://leetcode.com/problems/different-ways-to-add-parentheses/discuss/66328/A-recursive-Java-solution-(284-ms)
    //The time complexity might be n!, which is the size of result list. It's similar to Catalan number. https://en.wikipedia.org/wiki/Catalan_number
    public class Solution {
        public List<Integer> diffWaysToCompute(String input) {
            List<Integer> res = new LinkedList<>();
            for (int i=0; i<input.length(); i++) {
                if (input.charAt(i) == '-' ||
                        input.charAt(i) == '*' ||
                        input.charAt(i) == '+' ) {
                    String part1 = input.substring(0, i);
                    String part2 = input.substring(i+1);
                    List<Integer> part1Ret = diffWaysToCompute(part1);
                    List<Integer> part2Ret = diffWaysToCompute(part2);
                    for (Integer p1 :   part1Ret) {
                        for (Integer p2 :   part2Ret) {
                            int op = 0;
                            switch (input.charAt(i)) {
                                case '+': op = p1+p2;
                                    break;
                                case '-': op = p1-p2;
                                    break;
                                case '*': op = p1*p2;
                                    break;
                            }
                            res.add(op);
                        }
                    }
                }
            }
            if (res.size() == 0) {
                res.add(Integer.valueOf(input));
            }
            return res;
        }
    }


    //https://leetcode.com/problems/different-ways-to-add-parentheses/discuss/66328/A-recursive-Java-solution-(284-ms)/68439
    public class Solution2 {

        Map<String, List<Integer>> map = new HashMap<>();

        public List<Integer> diffWaysToCompute(String input) {
            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c == '+' || c == '-' || c == '*') {
                    String p1 = input.substring(0, i);
                    String p2 = input.substring(i + 1);
                    List<Integer> l1 = map.getOrDefault(p1, diffWaysToCompute(p1));
                    List<Integer> l2 = map.getOrDefault(p2, diffWaysToCompute(p2));
                    for (Integer i1 : l1) {
                        for (Integer i2 : l2) {
                            int r = 0;
                            switch (c) {
                                case '+':
                                    r = i1 + i2;
                                    break;
                                case '-':
                                    r = i1 - i2;
                                    break;
                                case '*':
                                    r = i1 * i2;
                                    break;
                            }
                            res.add(r);
                        }
                    }
                }
            }
            if (res.size() == 0) {
                res.add(Integer.valueOf(input));
            }
            map.put(input, res);
            return res;
        }
    }
}

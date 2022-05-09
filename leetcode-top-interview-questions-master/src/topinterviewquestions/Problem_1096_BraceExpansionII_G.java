package topinterviewquestions;

import java.util.*;

public class Problem_1096_BraceExpansionII_G {
    //https://leetcode.com/problems/brace-expansion-ii/discuss/348541/JAVA-iter_dfs-36ms/431372
    class Solution {
        public List<String> braceExpansionII(String expression) {
            Queue<String> queue = new LinkedList<>();
            queue.offer(expression);
            Set<String> set = new HashSet<>();//to store all the possibilities

            while (!queue.isEmpty()) {
                String str = queue.poll();

                if (str.indexOf('{') == -1) {
                    set.add(str);
                    continue;
                }

                int i = 0, l = 0, r = 0;
                while (str.charAt(i) != '}') {
                    if (str.charAt(i) == '{')
                        l = i;
                    i++;
                }
                r = i;

                String before = str.substring(0, l);
                String after = str.substring(r+1);
                String[] strs = str.substring(l+1, r).split(",");

                StringBuilder sb = new StringBuilder();
                for (String ss : strs) {
                    sb.setLength(0);
                    queue.offer(sb.append(before).append(ss).append(after).toString());
                }
            }

            List<String> ans = new ArrayList<>(set);
            Collections.sort(ans);
            return ans;
        }
    }

    //https://leetcode.com/problems/brace-expansion-ii/discuss/317623/Python3-Clear-and-Short-Recursive-Solution/294691
    class Solution2 {
        public List<String> braceExpansionII(String expression) {
            List<List<List<String>>> groups = new ArrayList<>();
            groups.add(new ArrayList<>());
            int level = 0;
            int start = -1, end = -1;

            for (int i = 0; i < expression.length(); i++) {
                char c = expression.charAt(i);
                if (c == '{') {
                    if (level == 0) {
                        start = i + 1;
                    }
                    level++;
                } else if (c == '}') {
                    level--;
                    if (level == 0) {
                        String subExpression = expression.substring(start, i);
                        List<String> subRes = braceExpansionII(subExpression);
                        groups.get(groups.size() - 1).add(subRes);
                    }
                } else if (c == ',' && level == 0) {
                    groups.add(new ArrayList<>());
                } else if (level == 0) {
                    groups.get(groups.size() - 1).add(Arrays.asList(String.valueOf(c)));
                }
            }

            Set<String> set = new HashSet<>();
            for (List<List<String>> group : groups) {
                List<String> pre = new ArrayList<>();
                pre.add("");
                for (List<String> g : group) {
                    List<String> tmp = new ArrayList<>();
                    for (String pStr : pre) {
                        for (String gStr : g) {
                            tmp.add(pStr + gStr);
                        }
                    }
                    pre = tmp;
                }
                set.addAll(pre);
            }

            List<String> res = new ArrayList<>(set);
            Collections.sort(res);

            return res;
        }
    }
}

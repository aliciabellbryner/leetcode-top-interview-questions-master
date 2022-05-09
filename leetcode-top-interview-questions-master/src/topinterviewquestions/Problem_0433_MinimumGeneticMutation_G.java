package topinterviewquestions;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Problem_0433_MinimumGeneticMutation_G {
    //Java Solution using BFS
    //https://leetcode.com/problems/minimum-genetic-mutation/discuss/91484/Java-Solution-using-BFS
    public class Solution {
        public int minMutation(String start, String end, String[] bank) {
            if(start.equals(end)) return 0;

            Set<String> bankSet = new HashSet<>();
            for(String b: bank) bankSet.add(b);

            char[] charSet = new char[]{'A', 'C', 'G', 'T'};

            int level = 0;
            Set<String> visited = new HashSet<>();
            Queue<String> queue = new LinkedList<>();
            queue.offer(start);
            visited.add(start);

            while(!queue.isEmpty()) {
                int size = queue.size();
                while(size-- > 0) {
                    String curr = queue.poll();
                    if(curr.equals(end)) return level;

                    char[] currArray = curr.toCharArray();
                    for(int i = 0; i < currArray.length; i++) {
                        char old = currArray[i];
                        for(char c: charSet) {
                            currArray[i] = c;
                            String next = new String(currArray);
                            if(!visited.contains(next) && bankSet.contains(next)) {
                                visited.add(next);
                                queue.offer(next);
                            }
                        }
                        currArray[i] = old;
                    }
                }
                level++;
            }
            return -1;
        }
    }

    //My DFS Solution.
    //What I do here is just compare each word in bank with start, and if the difference is just 1, then I'll add that word to set, and move to the next set of words, making that word as start.
    //https://leetcode.com/problems/minimum-genetic-mutation/discuss/91484/Java-Solution-using-BFS/96140
    class Solution2 {
        public int minMutation(String start, String end, String[] bank) {
            recurse(start, end, bank, 0, new HashSet<String>());
            return count == Integer.MAX_VALUE ? -1 : count;
        }
        int count = Integer.MAX_VALUE;
        private void recurse(String start, String end, String[] bank, int soFar, Set<String> visited) {
            if(start.intern() == end.intern()) {
                count = Math.min(count, soFar);
            }

            for(String e : bank) {
                int diff = 0;
                for(int i = 0; i < e.length(); i++) {
                    if(start.charAt(i) != e.charAt(i)) {
                        diff++;
                        if(diff > 1) break;
                    }
                }
                if(diff == 1 && !visited.contains(e)) {
                    visited.add(e);
                    recurse(e, end, bank, soFar+1, visited);
                    visited.remove(e);
                }
            }
        }
    }

}

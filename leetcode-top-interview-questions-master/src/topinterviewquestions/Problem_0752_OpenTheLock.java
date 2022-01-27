package topinterviewquestions;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Problem_0752_OpenTheLock {
    //https://leetcode.com/problems/open-the-lock/solution/
    //Approach #1: Breadth-First Search [Accepted]
    class Solution {
        public int openLock(String[] deadends, String target) {
            Set<String> deadSet = new HashSet();
            for (String d: deadends) {
                deadSet.add(d);
            }

            Queue<String> queue = new LinkedList();
            queue.offer("0000");
            queue.offer(null);// uses null inputs in the queue to represent a layer change

            Set<String> visited = new HashSet();
            visited.add("0000");

            int res = 0;
            while (!queue.isEmpty()) {
                String cur = queue.poll();
                if (cur == null) {
                    res++;
                    if (queue.peek() != null)
                        queue.offer(null);// uses null inputs in the queue to represent a layer change
                } else if (cur.equals(target)) {
                    return res;
                } else if (!deadSet.contains(cur)) {
                    for (int i = 0; i < 4; ++i) {
                        for (int d = -1; d <= 1; d += 2) {//只能加一或者减一
                            int y = ((cur.charAt(i) - '0') + d + 10) % 10;
                            String neighbor = cur.substring(0, i) + ("" + y) + cur.substring(i+1);
                            if (!visited.contains(neighbor)) {
                                visited.add(neighbor);
                                queue.offer(neighbor);
                            }
                        }
                    }
                }
            }
            return -1;
        }
    }
}

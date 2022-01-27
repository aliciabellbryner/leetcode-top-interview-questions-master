package topinterviewquestions;

import java.util.PriorityQueue;

public class Problem_1167_MinimumCostToConnectSticks {
    //Time complexity : O(NlogN), where NN is the length of the input array. Let's break it down:
    //Step 1) Adding NN elements to the priority queue will be )O(NlogN).
    //Step 2) We remove two of the smallest elements and then add one element to the priority queue until we are left with one element.
    // Since each such operation will reduce one element from the priority queue, we will perform N-1N−1 such operations.
    // Now, we know that both add and remove operations take O(logN) in priority queue,
    // therefore, complexity of this step will be O(N\log{N})O(NlogN).
    //Space complexity : O(N) since we will store NN elements in our priority queue.
    //https://leetcode.com/problems/minimum-cost-to-connect-sticks/solution/
    class Solution {
        public int connectSticks(int[] sticks) {
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            for (int i : sticks) {
                minHeap.add(i);
            }
            int res = 0;
            while (minHeap.size() >= 2) {
                int f = minHeap.poll();
                int s = minHeap.poll();
                res += f + s;
                minHeap.add(f + s);
            }
            return res;
        }
    }
}

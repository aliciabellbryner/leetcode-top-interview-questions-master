package topinterviewquestions;

import java.util.*;

/*
Given a string s and an integer k, rearrange s such that the same characters are at least distance k from each other. If it is not possible to rearrange the string, return an empty string "".



Example 1:

Input: s = "aabbcc", k = 3
Output: "abcabc"
Explanation: The same letters are at least a distance of 3 from each other.
Example 2:

Input: s = "aaabc", k = 3
Output: ""
Explanation: It is not possible to rearrange the string.
Example 3:

Input: s = "aaadbbcc", k = 2
Output: "abacabcd"
Explanation: The same letters are at least a distance of 2 from each other.


Constraints:

1 <= s.length <= 3 * 105
s consists of only lowercase English letters.
0 <= k <= s.length
 */
public class Problem_0358_RearrangeStringkDistanceApart_G {
    //Java 7 version of PriorityQueue O(nlogn) with comments and explanations
    //The greedy algorithm is that in each step, select the char with highest remaining count if possible (if it is not in the waiting queue). PQ is used to achieve the greedy. A regular queue waitQueue is used to "freeze" previous appeared char in the period of k.
    //In each iteration, we need to add current char to the waitQueue and also release the char at front of the queue, put back to maxHeap. The "impossible" case happens when the maxHeap is empty but there is still some char in the waitQueue.
    public class Solution {
        public String rearrangeString(String str, int k) {

            StringBuilder rearranged = new StringBuilder();
            //count frequency of each char
            Map<Character, Integer> map = new HashMap<>();
            for (char c : str.toCharArray()) {
                if (!map.containsKey(c)) {
                    map.put(c, 0);
                }
                map.put(c, map.get(c) + 1);
            }

            //construct a max heap using self-defined comparator, which holds all Map entries, Java is quite verbose
            Queue<Map.Entry<Character, Integer>> maxHeap = new PriorityQueue<>(new Comparator<Map.Entry<Character, Integer>>() {
                public int compare(Map.Entry<Character, Integer> entry1, Map.Entry<Character, Integer> entry2) {
                    return entry2.getValue() - entry1.getValue();
                }
            });

            Queue<Map.Entry<Character, Integer>> waitQueue = new LinkedList<>();
            maxHeap.addAll(map.entrySet());

            while (!maxHeap.isEmpty()) {

                Map.Entry<Character, Integer> current = maxHeap.poll();
                rearranged.append(current.getKey());
                current.setValue(current.getValue() - 1);
                waitQueue.offer(current);

                if (waitQueue.size() < k) { // intial k-1 chars, waitQueue not full yet
                    continue;
                }
                // release from waitQueue if char is already k apart
                Map.Entry<Character, Integer> front = waitQueue.poll();
                //note that char with 0 count still needs to be placed in waitQueue as a place holder
                if (front.getValue() > 0) {
                    maxHeap.offer(front);
                }
            }

            return rearranged.length() == str.length() ? rearranged.toString() : "";
        }

    }
}

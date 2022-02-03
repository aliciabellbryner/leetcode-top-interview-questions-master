package topinterviewquestions;

import java.util.*;

public class Problem_0692_TopKFrequentWords_G {


    //https://leetcode.com/problems/top-k-frequent-words/discuss/108346/My-simple-Java-solution-using-HashMap-and-PriorityQueue-O(nlogk)-time-and-O(n)-space
    //The idea is to keep a count of each word in a HashMap and then insert in a Priority Queue.
    //While inserting in pq, if the count of two words is same then insert based on string compare of the keys.
    //time O(NlogK), using a minHeap
    class Solution {
        public List<String> topKFrequent(String[] words, int k) {

            List<String> res = new LinkedList<>();
            Map<String, Integer> map = new HashMap<>();
            for(int i=0; i<words.length; i++)
            {
                map.put(words[i], map.getOrDefault(words[i], 0)+1);
            }

            PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
                    (a,b) -> a.getValue()==b.getValue() ? b.getKey().compareTo(a.getKey()) : a.getValue()-b.getValue()
            );

            for(Map.Entry<String, Integer> entry: map.entrySet())
            {
                pq.offer(entry);
                if(pq.size()>k)
                    pq.poll();
            }

            while(!pq.isEmpty())
                res.add(0, pq.poll().getKey());

            return res;
        }
    }


    //https://leetcode.com/problems/top-k-frequent-words/discuss/108346/My-simple-Java-solution-using-HashMap-and-PriorityQueue-O(nlogk)-time-and-O(n)-space/119001
    class Solution2 {
        public List<String> topKFrequent(String[] words, int k) {
            List<String> res = new ArrayList<>();
            if (words == null || words.length == 0) {
                return res;
            }
            Map<String, Integer> map = new HashMap<>();//key is the string, value is the appearance times
            for (String w : words) {
                map.put(w, map.getOrDefault(w, 0) + 1);
            }
            PriorityQueue<String> pq = new PriorityQueue<String>((s1, s2) -> map.get(s1) == map.get(s2) ? s1.compareTo(s2) : map.get(s2) - map.get(s1));
            pq.addAll(map.keySet());
            for (int i = 0; i < k; i++) {
                if (!pq.isEmpty()) {
                    res.add(pq.poll());
                }
            }
            return res;
        }
    }
}

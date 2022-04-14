package topinterviewquestions;

import java.util.*;

public class Problem_0332_ReconstructItinerary_G {
    //https://leetcode.com/problems/reconstruct-itinerary/discuss/78766/Share-my-solution
    public class Solution {

        Map<String, PriorityQueue<String>> flights;
        LinkedList<String> res;

        public List<String> findItinerary(String[][] tickets) {
            flights = new HashMap<>();
            res = new LinkedList<>();
            for (String[] ticket : tickets) {
                flights.putIfAbsent(ticket[0], new PriorityQueue<>());
                flights.get(ticket[0]).add(ticket[1]);
            }
            dfs("JFK");
            return res;
        }

        public void dfs(String departure) {
            PriorityQueue<String> arrivals = flights.get(departure);
            while (arrivals != null && !arrivals.isEmpty()) {
                dfs(arrivals.poll());
            }
            res.addFirst(departure);
        }
    }
}

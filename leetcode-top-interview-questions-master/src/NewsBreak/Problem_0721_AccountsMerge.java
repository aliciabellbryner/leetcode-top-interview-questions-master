package NewsBreak;

import java.util.*;

public class Problem_0721_AccountsMerge {
    /**
     * Solution 2: Graph + BFS
     *
     * @params graph: one to many mapping graph that connects all emails with same name. The graph may contain several
     * separated components
     * @params emailToName: one to one mapping for finding name by any email belong to same group
     * */
    public List<List<String>> accountsMerge2(List<List<String>> accounts) {
        Map<String, Set<String>> emailToEmails = new HashMap<>();//this is the map for whoever has connection with each other
        Map<String, String> emailToName = new HashMap<>();

        // step 1: build emailToEmails that connects all emails have relationships
        for (List<String> account : accounts) {
            String name = account.get(0);
            for (int i = 1; i < account.size(); i++) {
                emailToEmails.putIfAbsent(account.get(i), new HashSet<>());
                emailToName.put(account.get(i), name);
                if (i != 1) {
                    emailToEmails.get(account.get(i)).add(account.get(i - 1));
                    emailToEmails.get(account.get(i - 1)).add(account.get(i));
                }
            }
        }

        // step 2: BFS traversal to traverse all nodes in every single component and generate each res list individually
        List<List<String>> res = new ArrayList<>();
        Set<String> visited = new HashSet<>();//store emails that has been used
        for (String email : emailToEmails.keySet()) {
            if (!visited.contains(email)) {
                visited.add(email);
                List<String> list = bfs(emailToEmails, visited, email);
                Collections.sort(list);
                list.add(0, emailToName.get(list.get(0)));//put the name in the very first pos
                res.add(list);
            }
        }
        return res;
    }

    public List<String> bfs(Map<String, Set<String>> emailToEmails, Set<String> visited, String startPoint) {
        List<String> newList = new ArrayList<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(startPoint);

        while(!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curEmail = queue.poll();
                newList.add(curEmail);
                Set<String> neighbors = emailToEmails.get(curEmail);
                for (String neighbor : neighbors) {
                    // WARING: DO NOT FORGET to check whether current email has been visited before
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.offer(neighbor);
                    }
                }
            }
        }
        return newList;
    }


}

package topinterviewquestions;

import java.util.*;

/*
Given a list of accounts where each element accounts[i] is a list of strings, where the first element accounts[i][0] is a name, and the rest of the elements are emails representing emails of the account.

Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some common email to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.

After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails in sorted order. The accounts themselves can be returned in any order.



Example 1:

Input: accounts = [["John","johnsmith@mail.com","john_newyork@mail.com"],["John","johnsmith@mail.com","john00@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]
Output: [["John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]
Explanation:
The first and second John's are the same person as they have the common email "johnsmith@mail.com".
The third John and Mary are different people as none of their email addresses are used by other accounts.
We could return these lists in any order, for example the answer [['Mary', 'mary@mail.com'], ['John', 'johnnybravo@mail.com'],
['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']] would still be accepted.
Example 2:

Input: accounts = [["Gabe","Gabe0@m.co","Gabe3@m.co","Gabe1@m.co"],["Kevin","Kevin3@m.co","Kevin5@m.co","Kevin0@m.co"],["Ethan","Ethan5@m.co","Ethan4@m.co","Ethan0@m.co"],["Hanzo","Hanzo3@m.co","Hanzo1@m.co","Hanzo0@m.co"],["Fern","Fern5@m.co","Fern1@m.co","Fern0@m.co"]]
Output: [["Ethan","Ethan0@m.co","Ethan4@m.co","Ethan5@m.co"],["Gabe","Gabe0@m.co","Gabe1@m.co","Gabe3@m.co"],["Hanzo","Hanzo0@m.co","Hanzo1@m.co","Hanzo3@m.co"],["Kevin","Kevin0@m.co","Kevin3@m.co","Kevin5@m.co"],["Fern","Fern0@m.co","Fern1@m.co","Fern5@m.co"]]


Constraints:

1 <= accounts.length <= 1000
2 <= accounts[i].length <= 10
1 <= accounts[i][j] <= 30
accounts[i][0] consists of English letters.
accounts[i][j] (for j > 0) is a valid email.
 */
public class Problem_0721_AccountsMerge_G {

    /**
     * Solution 1: Union Find
     *
     * Use two hash map with union find class to solve the problem
     * @params mailToIndex: one to one mapping: mail string to its parent index mapping
     * @params disjointSet: one to many mapping: parent index to all emails that belong to same group mapping
     * */
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        if (accounts.size() == 0) {
            return new ArrayList<>();
        }

        int n = accounts.size();
        UnionFind uf = new UnionFind(n);

        // Step 1: traverse all emails except names, if we have not seen an email before, put it with its index into map.
        // Otherwise, union the email to its parent index.
        Map<String, Integer> mailToIndex = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < accounts.get(i).size(); j++) {
                String curMail = accounts.get(i).get(j);
                if (mailToIndex.containsKey(curMail)) {
                    int preIndex = mailToIndex.get(curMail);
                    uf.union(preIndex, i);
                }
                else {
                    mailToIndex.put(curMail, i);
                }
            }
        }

        // Step 2: traverse every email list, find the parent of current list index and put all emails into the set list
        // that belongs to key of its parent index
        Map<Integer, Set<String>> disjointSet = new HashMap<>();
        for (int i = 0; i < n; i++) {
            // find parent index of current list index in parent array
            int parentIndex = uf.find(i);
            disjointSet.putIfAbsent(parentIndex, new HashSet<>());

            Set<String> curSet = disjointSet.get(parentIndex);
            for (int j = 1; j < accounts.get(i).size(); j++) {
                curSet.add(accounts.get(i).get(j));
            }
            disjointSet.put(parentIndex, curSet);
        }

        // step 3: traverse ket set of disjoint set group, retrieve all emails from each parent index, and then SORT
        // them, as well as adding the name at index 0 of every sublist
        List<List<String>> result = new ArrayList<>();
        for (int index : disjointSet.keySet()) {
            List<String> curList = new ArrayList<>();
            if (disjointSet.containsKey(index)) {
                curList.addAll(disjointSet.get(index));
            }
            Collections.sort(curList);
            curList.add(0, accounts.get(index).get(0));
            result.add(curList);
        }
        return result;
    }

    class UnionFind {
        int size;
        int[] parent;
        public UnionFind(int size) {
            this.size = size;
            this.parent = new int[size];

            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public void union(int a, int b) {
            parent[find(a)] = parent[find(b)];
        }

        public int find(int x) {
            if (x != parent[x]) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
    }





    /**
     * Solution 2: Graph + BFS
     *
     * @params graph: one to many mapping graph that connects all emails with same name. The graph may contain several
     * separated components
     * @params emailToName: one to one mapping for finding name by any email belong to same group
     * */
    public List<List<String>> accountsMerge2(List<List<String>> accounts) {
        Map<String, Set<String>> graph = new HashMap<>();
        Map<String, String> emailToName = new HashMap<>();

        // step 1: build graph that connects all emails have relationships
        for (List<String> account : accounts) {
            String name = account.get(0);
            for (int i = 1; i < account.size(); i++) {
                graph.putIfAbsent(account.get(i), new HashSet<>());
                emailToName.put(account.get(i), name);
                if (i != 1) {
                    graph.get(account.get(i)).add(account.get(i - 1));
                    graph.get(account.get(i - 1)).add(account.get(i));
                }
            }
        }

        // step 2: BFS traversal to traverse all nodes in every single component and generate each result list individually
        List<List<String>> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        for (String email : graph.keySet()) {
            if (!visited.contains(email)) {
                visited.add(email);
                List<String> newList = bfs(graph, visited, email);
                Collections.sort(newList);
                newList.add(0, emailToName.get(newList.get(0)));
                result.add(newList);
            }
        }
        return result;
    }

    public List<String> bfs(Map<String, Set<String>> graph, Set<String> visited, String startPoint) {
        List<String> newList = new ArrayList<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(startPoint);

        while(!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curEmail = queue.poll();
                newList.add(curEmail);
                Set<String> neighbors = graph.get(curEmail);
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





    /**
     * Solution 3: Build graph + DFS
     *
     * @params graph: one to many mapping graph that connects all emails with same name. The graph may contain several
     * separated components
     * @params emailToName: one to one mapping for finding name by any email belong to same group
     * */
    public List<List<String>> accountsMerge3(List<List<String>> accounts) {
        Map<String, Set<String>> graph = new HashMap<>();
        Map<String, String> emailToName = new HashMap<>();

        // step 1: build graph that connects all emails have relationships
        for (List<String> account : accounts) {
            String name = account.get(0);
            for (int i = 1; i < account.size(); i++) {
                graph.putIfAbsent(account.get(i), new HashSet<>());
                emailToName.put(account.get(i), name);
                if (i != 1) {
                    graph.get(account.get(i)).add(account.get(i - 1));
                    graph.get(account.get(i - 1)).add(account.get(i));
                }
            }
        }

        // step 2: DFS traversal to traverse all nodes in every single component and generate each result list individually
        List<List<String>> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        for (String email : graph.keySet()) {
            if (!visited.contains(email)) {
                visited.add(email);
                List<String> newList = new ArrayList<>();
                dfs(newList, graph, visited, email);
                Collections.sort(newList);
                newList.add(0, emailToName.get(newList.get(0)));
                result.add(newList);
            }
        }
        return result;
    }

    public void dfs(List<String> result, Map<String, Set<String>> graph, Set<String> visited, String curPoint) {
        result.add(curPoint);
        Set<String> neighbors = graph.get(curPoint);
        for (String neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                dfs(result, graph, visited, neighbor);
            }
        }
    }


    class Solution {
        public List<List<String>> accountsMerge(List<List<String>> accounts) {
            List<List<String>> res = new ArrayList<>();
            if (accounts == null) {
                return res;
            }
            HashMap<String, Set<String>> emailToEmails = new HashMap<>();
            HashMap<String, String> emailToName = new HashMap<>();
            for (List<String> list : accounts) {
                String name = list.get(0);
                for (int i = 0; i < list.size() - 1; i++) {
                    String email = list.get(i + 1);
                    emailToEmails.putIfAbsent(email, new HashSet<>());
                    emailToName.put(email, name);
                    if (i != 0) {
                        String preEmail = list.get(i);
                        emailToEmails.get(email).add(preEmail);
                        emailToEmails.get(preEmail).add(email);
                    }
                }
            }
            Set<String> visited = new HashSet<>();
            for (String email : emailToEmails.keySet()) {
                if (!visited.contains(email)) {
                    visited.add(email);
                    List<String> list = bfs(emailToEmails, email, visited);
                    Collections.sort(list);
                    list.add(0, emailToName.get(list.get(0)));
                    res.add(list);
                }
            }

            return res;
        }

        private List<String> bfs(HashMap<String, Set<String>> emailToEmails, String email, Set<String> visited) {
            List<String> res = new ArrayList<>();
            Queue<String> q = new LinkedList<>();
            q.add(email);

            while (!q.isEmpty()) {
                int size = q.size();
                for (int i = 0; i < size; i++) {
                    String curEmail = q.poll();
                    res.add(curEmail);
                    for (String s : emailToEmails.get(curEmail)) {
                        if (!visited.contains(s)) {
                            visited.add(s);
                            q.add(s);
                        }
                    }
                }
            }

            return res;
        }

    }


    //Union Find
    //https://leetcode.com/problems/accounts-merge/discuss/109157/JavaC%2B%2B-Union-Find
    class Solution2 {
        public List<List<String>> accountsMerge(List<List<String>> acts) {
            Map<String, String> owner = new HashMap<>();//key是Email， value是owner
            Map<String, String> parents = new HashMap<>();//key value都是邮箱
            Map<String, TreeSet<String>> unions = new HashMap<>();
            for (List<String> a : acts) {
                for (int i = 1; i < a.size(); i++) {
                    parents.put(a.get(i), a.get(i));
                    owner.put(a.get(i), a.get(0));
                }
            }
            for (List<String> a : acts) {
                String p = findParent(a.get(1), parents);
                for (int i = 2; i < a.size(); i++)
                    parents.put(findParent(a.get(i), parents), p);
            }
            for(List<String> a : acts) {
                String p = findParent(a.get(1), parents);
                if (!unions.containsKey(p)) unions.put(p, new TreeSet<>());
                for (int i = 1; i < a.size(); i++)
                    unions.get(p).add(a.get(i));
            }
            List<List<String>> res = new ArrayList<>();
            for (String p : unions.keySet()) {
                List<String> emails = new ArrayList(unions.get(p));
                emails.add(0, owner.get(p));
                res.add(emails);
            }
            return res;
        }
        private String findParent(String s, Map<String, String> p) {
            return p.get(s) == s ? s : findParent(p.get(s), p);
        }
    }


}

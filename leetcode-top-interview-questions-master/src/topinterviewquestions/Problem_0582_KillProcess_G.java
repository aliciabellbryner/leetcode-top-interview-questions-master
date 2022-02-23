package topinterviewquestions;

import java.util.*;

/*
You have n processes forming a rooted tree structure. You are given two integer arrays pid and ppid, where pid[i] is the ID of the ith process and ppid[i] is the ID of the ith process's parent process.

Each process has only one parent process but may have multiple children processes. Only one process has ppid[i] = 0, which means this process has no parent process (the root of the tree).

When a process is killed, all of its children processes will also be killed.

Given an integer kill representing the ID of a process you want to kill, return a list of the IDs of the processes that will be killed. You may return the answer in any order.



Example 1:


Input: pid = [1,3,10,5], ppid = [3,0,5,3], kill = 5
Output: [5,10]
Explanation: The processes colored in red are the processes that should be killed.
Example 2:

Input: pid = [1], ppid = [0], kill = 1
Output: [1]


Constraints:

n == pid.length
n == ppid.length
1 <= n <= 5 * 104
1 <= pid[i] <= 5 * 104
0 <= ppid[i] <= 5 * 104
Only one process has no parent.
All the values of pid are unique.
kill is guaranteed to be in pid.
 */
public class Problem_0582_KillProcess_G {
    /*
    Approach #1 Depth First Search [Time Limit Exceeded]
Algorithm

Since killing a process leads to killing all its children processes, the simplest solution is to traverse over the ppidppid array and find out all the children of the process to be killed. Further, for every child chosen to be killed we recursively make call to the killProcess function now treating this child as the new parent to be killed. In every such call, we again traverse over the ppidppid array now considering the id of the child process, and continue in the same fashion. Further, at every step, for every process chosen to be killed, it is added to the list ll that needs to be returned at the end.


Complexity Analysis

Time complexity : O(n^n)O(n
n
 ). O(n^n)O(n
n
 ) function calls will be made in the worst case

Space complexity : O(n)O(n). The depth of the recursion tree can go upto nn.

Approach #2 Tree Simulation [Accepted]
Algorithm

We can view the given process relationships in the form of a tree. We can construct the tree in such a way that every node stores information about its own value as well as the list of all its direct children nodes. Thus, now, once the tree has been generated, we can simply start off by killing the required node, and recursively killing the children of each node encountered rather than traversing over the whole ppidppid array for every node as done in the previous approach.

In order to implement this, we've made use of a NodeNode class which represents a node of a tree. Each node represents a process. Thus, every node stores its own value(Node.valNode.val) and the list of all its direct children(Node.childrenNode.children). We traverse over the whole pidpid array and create nodes for all of them. Then, we traverse over the ppidppid array, and make the parent nodes out of them, and at the same time add all their direct children nodes in their Node.childrenNode.children list. In this way, we convert the given process structure into a tree structure.

Now, that we've obtained the tree structure, we can add the node to be killed to the return list ll. Now, we can directly obtain all the direct children of this node from the tree, and add its direct children to the return list. For every node added to the return list, we repeat the same process of obtaining the children recursively.


Complexity Analysis

Time complexity : O(n)O(n). We need to traverse over the ppidppid and pidpid array of size nn once. The getAllChildren function also takes atmost nn time, since no node can be a child of two nodes.

Space complexity : O(n)O(n). mapmap of size nn is used.

Approach #3 HashMap + Depth First Search [Accepted]
Algorithm

Instead of making the tree structure, we can directly make use of a data structure which stores a particular process value and the list of its direct children. For this, in the current implementation, we make use of a hashmap mapmap, which stores the data in the form {parent: [list of all its direct children]}parent:[listofallitsdirectchildren].

Thus, now, by traversing just once over the ppidppid array, and adding the corresponding pidpid values to the children list at the same time, we can obtain a better structure storing the parent-children relationship.

Again, similar to the previous approach, now we can add the process to be killed to the return list, and keep on adding its children to the return list in a recursive manner by obtaining the child information from the structure created previously.

Current
1 / 8
 **Complexity Analysis**
Time complexity : O(n)O(n). We need to traverse over the ppidppid array of size nn once. The getAllChildren function also takes atmost nn time, since no node can be a child of two nodes.

Space complexity : O(n)O(n). mapmap of size nn is used.

Approach #4 HashMap + Breadth First Search [Accepted]:
Algorithm

We can also make use of Breadth First Search to obtain all the children(direct+indirect) of a particular node, once the data structure of the form (process: [list of all its direct children](process:[listofallitsdirectchildren] has been obtained. The process of obtaining the data structure is the same as in the previous approach.

In order to obtain all the child processes to be killed for a particular parent chosen to be killed, we can make use of Breadth First Search. For this, we add the node to be killed to a queuequeue. Then, we remove an element from the front of the queuequeue and add it to the return list. Further, for every element removed from the front of the queue, we add all its direct children(obtained from the data structure created) to the end of the queue. We keep on doing so till the queue becomes empty.

Current
1 / 9

Complexity Analysis

Time complexity : O(n)O(n). We need to traverse over the ppidppid array of size nn once. Also, atmost nn additions/removals are done from the queuequeue.

Space complexity : O(n)O(n). mapmap of size nn is used.
     */
    public class Solution2 {
        class Node {
            int val;
            List < Node > children = new ArrayList < > ();
        }
        public List < Integer > killProcess(List < Integer > pid, List < Integer > ppid, int kill) {
            HashMap< Integer, Node > map = new HashMap < > ();
            for (int id: pid) {
                Node node = new Node();
                node.val = id;
                map.put(id, node);
            }
            for (int i = 0; i < ppid.size(); i++) {
                if (ppid.get(i) > 0) {
                    Node par = map.get(ppid.get(i));
                    par.children.add(map.get(pid.get(i)));
                }
            }
            List< Integer > l = new ArrayList< >();
            l.add(kill);
            getAllChildren(map.get(kill), l);
            return l;
        }
        public void getAllChildren(Node pn, List < Integer > l) {
            for (Node n: pn.children) {
                l.add(n.val);
                getAllChildren(n, l);
            }
        }
    }

    public class Solution3 {
        public List < Integer > killProcess(List < Integer > pid, List < Integer > ppid, int kill) {
            HashMap < Integer, List < Integer >> map = new HashMap < > ();
            for (int i = 0; i < ppid.size(); i++) {
                if (ppid.get(i) > 0) {
                    List < Integer > l = map.getOrDefault(ppid.get(i), new ArrayList < Integer > ());
                    l.add(pid.get(i));
                    map.put(ppid.get(i), l);
                }
            }
            List < Integer > l = new ArrayList < > ();
            l.add(kill);
            getAllChildren(map, l, kill);
            return l;
        }
        public void getAllChildren(HashMap < Integer, List < Integer >> map, List < Integer > l, int kill) {
            if (map.containsKey(kill))
                for (int id: map.get(kill)) {
                    l.add(id);
                    getAllChildren(map, l, id);
                }
        }
    }


    public class Solution4 {

        public List < Integer > killProcess(List < Integer > pid, List < Integer > ppid, int kill) {
            HashMap < Integer, List < Integer >> map = new HashMap < > ();
            for (int i = 0; i < ppid.size(); i++) {
                if (ppid.get(i) > 0) {
                    List < Integer > l = map.getOrDefault(ppid.get(i), new ArrayList < Integer > ());
                    l.add(pid.get(i));
                    map.put(ppid.get(i), l);
                }
            }
            Queue< Integer > queue = new LinkedList< >();
            List < Integer > l = new ArrayList < > ();
            queue.add(kill);
            while (!queue.isEmpty()) {
                int r = queue.remove();
                l.add(r);
                if (map.containsKey(r))
                    for (int id: map.get(r))
                        queue.add(id);
            }
            return l;
        }
    }


    //diss hashmap

    public class Solution5 {
        public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
            if (kill == 0) return pid;

            int n = pid.size();
            Map<Integer, Set<Integer>> tree = new HashMap<>();
            for (int i = 0; i < n; i++) {
                tree.put(pid.get(i), new HashSet<Integer>());
            }
            for (int i = 0; i < n; i++) {
                if (tree.containsKey(ppid.get(i))) {
                    Set<Integer> children = tree.get(ppid.get(i));
                    children.add(pid.get(i));
                    tree.put(ppid.get(i), children);
                }
            }

            List<Integer> result = new ArrayList<>();
            traverse(tree, result, kill);

            return result;
        }

        private void traverse(Map<Integer, Set<Integer>> tree, List<Integer> result, int pid) {
            result.add(pid);

            Set<Integer> children = tree.get(pid);
            for (Integer child : children) {
                traverse(tree, result, child);
            }
        }
    }



    //Short iterative version
    public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
        Map<Integer, List<Integer>> m = new HashMap<>();
        for (int i = 0; i < pid.size(); i++) {
            m.putIfAbsent(ppid.get(i), new LinkedList<>());
            m.get(ppid.get(i)).add(pid.get(i));
        }
        LinkedList<Integer> r = new LinkedList<>(), q = new LinkedList<>();
        q.add(kill);
        while (!q.isEmpty()) {
            Integer p = q.poll();
            r.add(p);
            if (m.containsKey(p))
                q.addAll(m.get(p));
        }
        return r;
    }

    //A slightly more concise version
    public List<Integer> killProcess6(List<Integer> pid, List<Integer> ppid, int kill) {
        if (kill == 0) return pid;

        int n = pid.size();
        Map<Integer, Set<Integer>> tree = new HashMap<>();
        for (int i = 0; i < n; i++) {
            tree.computeIfAbsent(ppid.get(i),k->new HashSet<>()).add(pid.get(i));
        }
        List<Integer> result = new ArrayList<>();
        traverse(tree, result, kill);
        return result;
    }

    private void traverse(Map<Integer, Set<Integer>> tree, List<Integer> result, int pid) {
        result.add(pid);
        Set<Integer> children = tree.getOrDefault(pid,new HashSet<>());
        for (Integer child : children) {
            traverse(tree, result, child);
        }
    }
}

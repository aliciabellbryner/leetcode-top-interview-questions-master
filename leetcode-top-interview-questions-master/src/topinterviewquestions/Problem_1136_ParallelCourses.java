package topinterviewquestions;

import java.util.*;

/*
You are given an integer n, which indicates that there are n courses labeled from 1 to n. You are also given an array relations where relations[i] = [prevCoursei, nextCoursei], representing a prerequisite relationship between course prevCoursei and course nextCoursei: course prevCoursei has to be taken before course nextCoursei.

In one semester, you can take any number of courses as long as you have taken all the prerequisites in the previous semester for the courses you are taking.

Return the minimum number of semesters needed to take all courses. If there is no way to take all the courses, return -1.



Example 1:


Input: n = 3, relations = [[1,3],[2,3]]
Output: 2
Explanation: The figure above represents the given graph.
In the first semester, you can take courses 1 and 2.
In the second semester, you can take course 3.
Example 2:


Input: n = 3, relations = [[1,2],[2,3],[3,1]]
Output: -1
Explanation: No course can be studied because they are prerequisites of each other.


Constraints:

1 <= n <= 5000
1 <= relations.length <= 5000
relations[i].length == 2
1 <= prevCoursei, nextCoursei <= n
prevCoursei != nextCoursei
All the pairs [prevCoursei, nextCoursei] are unique.
 */
public class Problem_1136_ParallelCourses {
/*
Solution
Overview
In this problem, we need to learn all the courses as fast as possible. The number of courses we can learn in one semester is unlimited, and the only limitation is the prerequisite relationship: we can only learn those courses whose prerequisite(s) is fulfilled.

This problem is an application of Topological sorting, and there are mainly two different kinds of solutions: BFS (Breadth-First Search) and DFS (Depth-First Search).

In this article, three approaches are introduced:

Breadth-First Search (Kahn's Algorithm)
Depth-First Search: Depth-First Search: Check for Cycles + Find Longest Path
Depth-First Search: Combine
Generally, we recommend Approach 1 and Approach 3 since they are efficient and easy to implement. We include Approach 2 for a better understanding to Approach 3. (Therefore, it is recommended to read Approach 2 before Approach 3.)

Once you've finished this problem, you can try challenging the follow-up 1494. Parallel Courses II.


Approach 1: Breadth-First Search (Kahn's Algorithm)
Intuition

We can treat the problem as a directed graph problem (the courses are nodes and the prerequisites are egdes). What we need to do is somehow iterate over all the nodes in the graph.

For iteration, we can do BFS or DFS. We introduce BFS in this approach and DFS in the following approaches.

To achieve the fastest learning speed, our strategy is:

Learn all courses available in each semester.

This is intuitive. Even if we deliberately choose not to learn one available course, we still need to learn it in the following semesters. There is no harm to learn it now. Also, if we learn it later, then we have to postpone all courses whose prerequisite is that course.

Now, the first question is:

Where to start? (Which courses are available?)

We can not start from courses with prerequisites.

We start from nodes with no prerequisites.

For example, in this graph, which courses can we learn in the first semester?

Figure 1.1

Yes, those courses marked with yellow can be learned in the first semester.

Figure 1.2

Now, we have learned those courses, what should we learn next?

Figure 1.3

Yes, the new yellow courses can be learned, since their prerequisites are fulfilled:

Figure 1.4

Keep going until no available courses to learn.

By using this strategy to allocate courses to semesters, we are guaranteed to minimize the number of semesters needed. This is because in each semester, we're learning every course that isn't "locked" by a prerequisite, and so there is no possible way to be faster.

Let's finish this example with Breadth-First Search:

Current
1 / 6
In some other cases, we can not learn all nodes. If the number of nodes we visited is strictly less than the number of total nodes, then there is not way to learn all the courses and we can do nothing but return -1.

For example, in this graph with a cycle, we can not learn all the courses:

Current
1 / 2
This approach is also called Kahn's algorithm (with some modifications to adapt to the problem).

Algorithm

Step 1: Build a directed graph from relations.

Step 2: Record the in-degree of each node. (i.e., the number of edges towards the node)

Step 3: Initialize a queue, queue. Put nodes with an in-degree of 0 into queue. Initialize step = 0, visited_count = 0.

Step 4: Start BFS: Loop until queue is empty:

Initialize a queue next_queueto record the nodes needed in the next iteration.
Increment step.
For each node in queue:
Increment visitedCount
For each end_node reachable from node:
Decrement the in-degree of end_node
If the in-degree of end_node reaches 0, push it into next_queue
Assign queue to next_queue
Step 5: If visited_count == N, return step. Otherwise, return -1.

Implementation


Complexity Analysis

Let EE be the length of relations. NN is the number of courses, as explained in the problem description.

Time Complexity: \mathcal{O}(N+E)O(N+E). For building the graph, we spend \mathcal{O}(N)O(N) to initialize the graph, and spend \mathcal{O}(E)O(E) to add egdes since we iterate relations once. For BFS, we spend \mathcal{O}(N+E)O(N+E) since we need to visit every node and edge once in BFS in the worst case.

Space Complexity: \mathcal{O}(N+E)O(N+E). For the graph, we spend \mathcal{O}(N+E)O(N+E) since we have \mathcal{O}(N)O(N) keys and \mathcal{O}(E)O(E) values. For BFS, we spend \mathcal{O}(N)O(N) since in the worst case, we need to add all nodes to the queue in the same time.


Approach 2: Depth-First Search: Check for Cycles + Find Longest Path
Intuition

There is an important insight:

The number of semesters needed is equal to the length of the longest path in the graph.

For example, the longest path in the graph below is 5, so the number of semesters needed is 5:

Figure 2.1

Why? Treat the path as a sequence of prerequisites, and for each prerequisite, we need to spend one semester to advance to the next node.

But there is a problem: if the graph has a cycle, then the longest path would be infinite.

Figure 2.3

So firstly, we need to check if the graph has a cycle. If it does, we can directly return -1 since we can never finish all courses.

Now we break the problem into two parts:

Check if the graph has a cycle
Calculate the length of the longest path
Each of the two parts can be done with DFS. In Approach 3, we will show how to achieve those two-part simultaneously in one single DFS. However, in this approach, for a better understanding, we separate them into two separate DFS traverals.

Check If the Graph Has A Cycle

Each node has one of the three states: unvisited, visiting, and visited.

Before the DFS, we initialize all nodes in the graph to unvisited.

When performing a DFS, we mark the current node as visiting until we search all paths out of the node from the node. If we meet a node marked with processing, it must come from the upstream path and therefore, we've detected a cycle.

If DFS finishes, and all node are marked as visited, then the graph contains no cycle.

Calculate the Length of the Longest Path

The DFS function should return the maximum out of the recursive calls for its child nodes, plus one (the node itself).

In order to prevent redundant calculations, we need to store the calculated results. This is an example of dynamic programming, as we're storing the result of subproblems.

Algorithm

Step 1: Build a directed graph from relations.

Step 2: Implement a function dfsCheckCycle to check whether the graph has a cycle.

Step 3: Implement a function dfsMaxPath to calculate the length of the longest path in the graph.

Step 4: Call dfsCheckCycle, return -1 if the graph has a cycle.

Step 5: Otherwise, call dfsMaxPath. Return the length of the longest path in the graph.

Implementation


Complexity Analysis

Let EE be the length of relations.

Time Complexity: \mathcal{O}(N+E)O(N+E). For building the graph, we spend \mathcal{O}(N)O(N) to initialize the graph, and spend \mathcal{O}(E)O(E) to add egdes since we iterate relations once. For DFS, we spend \mathcal{O}(N+E)O(N+E) since we need to visit every node and edge once in DFS in the worst case.

Space Complexity: \mathcal{O}(N+E)O(N+E). For the graph, we spend \mathcal{O}(N+E)O(N+E) since we have \mathcal{O}(N)O(N) keys and \mathcal{O}(E)O(E) values. For DFS, we spend \mathcal{O}(N)O(N) since in the worst case, we need to add all nodes to the stack to recursively call DFS. Also, we run DFS twice.


Approach 3: Depth-First Search: Combine
Intuition

This approach is an improvement of Approach 2. It is recommended to ensure that you fully understood Approach 2 before continuing onto this final approach.

Here, we combine the two functions in Approach 2, dfsCheckCycle and dfsMaxPath, into one single function, dfs.

The new dfs should return -1 if a cycle is detected, and return the longest length otherwise.

Just simple modifications on dfsCheckCycle will do:

Recall in dfsCheckCycle, each node has three states: unvisited, visiting, and visited.

We can change the visited state to the longest length starting from the current node, and let the dfs return the longest length starting from the current node.

The pseudo-code is as below:

set states of all nodes to unvisited

def dfs(node):
    if the state of node is visiting:
        # detects cycles
        return -1
    else if the state of node is visited:
        return the state of node # the longest length

    set the state of node to visiting

    max_length = -1
    for child_node in child_nodes:
        child_answer = dfs(child_node)
        # if detects cycles in child_node
        if child_answer == -1:
            return -1
        else:
            max_length = max(max_length, child_answer)

    set the state of node to max_length
    return max_length
Algorithm

Step 1: Build a directed graph from relations.

Step 2: Implement a function dfs to check whether the graph has a cycle and calculate the length of the longest path in the graph.

Step 3: Call dfs; return -1 if the graph has a cycle. Otherwise, return the length of the longest path in the graph.

Implementation


Complexity Analysis

Let EE be the length of relations.

Time Complexity: \mathcal{O}(N+E)O(N+E). For building the graph, we spend \mathcal{O}(N)O(N) to initialize the graph, and spend \mathcal{O}(E)O(E) to add egdes since we iterate relations once. For DFS, we spend \mathcal{O}(N+E)O(N+E) since we need to visit every node and edge once in DFS in the worst case.

Space Complexity: \mathcal{O}(N+E)O(N+E). For the graph, we spend \mathcal{O}(N+E)O(N+E) since we have \mathcal{O}(N)O(N) keys and \mathcal{O}(E)O(E) values. For DFS, we spend \mathcal{O}(N)O(N) since in the worst case, we need to add all nodes to the stack to recursively call DFS.
 */
class Solution1 {
    public int minimumSemesters(int N, int[][] relations) {
        int[] inCount = new int[N + 1]; // or indegree
        List<List<Integer>> graph = new ArrayList<>(N + 1);
        for (int i = 0; i < N + 1; ++i) {
            graph.add(new ArrayList<Integer>());
        }
        for (int[] relation : relations) {
            graph.get(relation[0]).add(relation[1]);
            inCount[relation[1]]++;
        }
        int step = 0;
        int studiedCount = 0;
        List<Integer> bfsQueue = new ArrayList<>();
        for (int node = 1; node < N + 1; node++) {
            if (inCount[node] == 0) {
                bfsQueue.add(node);
            }
        }
        // start learning with BFS
        while (!bfsQueue.isEmpty()) {
            // start new semester
            step++;
            List<Integer> nextQueue = new ArrayList<>();
            for (int node : bfsQueue) {
                studiedCount++;
                for (int endNode : graph.get(node)) {
                    inCount[endNode]--;
                    // if all prerequisite courses learned
                    if (inCount[endNode] == 0) {
                        nextQueue.add(endNode);
                    }
                }
            }
            bfsQueue = nextQueue;
        }

        // check if learn all courses
        return studiedCount == N ? step : -1;
    }
}


    class Solution2 {
        public int minimumSemesters(int N, int[][] relations) {
            List<List<Integer>> graph = new ArrayList<>(N + 1);
            for (int i = 0; i < N + 1; ++i) {
                graph.add(new ArrayList<Integer>());
            }
            for (int[] relation : relations) {
                graph.get(relation[0]).add(relation[1]);
            }
            // check if the graph contains a cycle
            int[] visited = new int[N + 1];
            for (int node = 1; node < N + 1; node++) {
                // if has cycle, return -1
                if (dfsCheckCycle(node, graph, visited) == -1) {
                    return -1;
                }
            }

            // if no cycle, return the longest path
            int[] visitedLength = new int[N + 1];
            int maxLength = 1;
            for (int node = 1; node < N + 1; node++) {
                int length = dfsMaxPath(node, graph, visitedLength);
                maxLength = Math.max(length, maxLength);
            }
            return maxLength;
        }

        private int dfsCheckCycle(int node, List<List<Integer>> graph, int[] visited) {
            // return -1 if has a cycle
            // return 1 if does not have any cycle
            if (visited[node] != 0) {
                return visited[node];
            } else {
                // mark as visiting
                visited[node] = -1;
            }
            for (int endNode : graph.get(node)) {
                if (dfsCheckCycle(endNode, graph, visited) == -1) {
                    // we meet a cycle!
                    return -1;
                }
            }
            // mark as visited
            visited[node] = 1;
            return 1;
        }

        private int dfsMaxPath(int node, List<List<Integer>> graph, int[] visitedLength) {
            // return the longest path (inclusive)
            if (visitedLength[node] != 0) {
                return visitedLength[node];
            }
            int maxLength = 1;
            for (int endNode : graph.get(node)) {
                int length = dfsMaxPath(endNode, graph, visitedLength);
                maxLength = Math.max(length + 1, maxLength);
            }
            // store it
            visitedLength[node] = maxLength;
            return maxLength;
        }
    }

    class Solution3 {
        public int minimumSemesters(int N, int[][] relations) {
            List<List<Integer>> graph = new ArrayList<>(N + 1);
            for (int i = 0; i < N + 1; ++i) {
                graph.add(new ArrayList<Integer>());
            }
            for (int[] relation : relations) {
                graph.get(relation[0]).add(relation[1]);
            }
            int[] visited = new int[N + 1];

            int maxLength = 1;
            for (int node = 1; node < N + 1; node++) {
                int length = dfs(node, graph, visited);
                // we meet a cycle!
                if (length == -1) {
                    return -1;
                }
                maxLength = Math.max(length, maxLength);
            }
            return maxLength;
        }

        private int dfs(int node, List<List<Integer>> graph, int[] visited) {
            // return the longest path (inclusive)
            if (visited[node] != 0) {
                return visited[node];
            } else {
                // mark as visiting
                visited[node] = -1;
            }
            int maxLength = 1;
            for (int endNode : graph.get(node)) {
                int length = dfs(endNode, graph, visited);
                // we meet a cycle!
                if (length == -1) {
                    return -1;
                }
                maxLength = Math.max(length + 1, maxLength);
            }
            // mark as visited
            visited[node] = maxLength;
            return maxLength;
        }
    }


    //dis
    //[Java] Topological Sort + BFS w/ comment and analysis.
    //Time & space: O(V + E).
    public int minimumSemesters(int N, int[][] relations) {
        Map<Integer, List<Integer>> g = new HashMap<>(); // key: prerequisite, value: course list.
        int[] inDegree = new int[N + 1]; // inDegree[i]: number of prerequisites for i.
        for (int[] r : relations) {
            g.computeIfAbsent(r[0], l -> new ArrayList<>()).add(r[1]); // construct graph.
            ++inDegree[r[1]]; // count prerequisites for r[1].
        }
        Queue<Integer> q = new LinkedList<>(); // save current 0 in-degree vertices.
        for (int i = 1; i <= N; ++i)
            if (inDegree[i] == 0)
                q.offer(i);
        int semester = 0;
        while (!q.isEmpty()) { // BFS traverse all currently 0 in degree vertices.
            for (int sz = q.size(); sz > 0; --sz) { // sz is the search breadth.
                int c = q.poll();
                --N;
                if (!g.containsKey(c)) continue; // c's in-degree is currently 0, but it has no prerequisite.
                for (int course : g.remove(c))
                    if (--inDegree[course] == 0) // decrease the in-degree of course's neighbors.
                        q.offer(course); // add current 0 in-degree vertex into Queue.
            }
            ++semester; // need one more semester.
        }
        return N == 0 ? semester : -1;
    }

}

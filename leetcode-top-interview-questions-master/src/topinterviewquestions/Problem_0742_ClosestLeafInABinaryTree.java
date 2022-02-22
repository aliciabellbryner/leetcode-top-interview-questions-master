package topinterviewquestions;

import java.util.*;

/*
Given the root of a binary tree where every node has a unique value and a target integer k, return the value of the nearest leaf node to the target k in the tree.

Nearest to a leaf means the least number of edges traveled on the binary tree to reach any leaf of the tree. Also, a node is called a leaf if it has no children.



Example 1:


Input: root = [1,3,2], k = 1
Output: 2
Explanation: Either 2 or 3 is the nearest leaf node to the target of 1.
Example 2:


Input: root = [1], k = 1
Output: 1
Explanation: The nearest leaf node is the root node itself.
Example 3:


Input: root = [1,2,3,4,null,null,null,5,null,6], k = 2
Output: 3
Explanation: The leaf node with value 3 (and not the leaf node with value 6) is nearest to the node with value 2.


Constraints:

The number of nodes in the tree is in the range [1, 1000].
1 <= Node.val <= 1000
All the values of the tree are unique.
There exist some node in the tree where Node.val == k.
 */
public class Problem_0742_ClosestLeafInABinaryTree {
    /*
Approach #1: Convert to Graph [Accepted]
Intuition

Instead of a binary tree, if we converted the tree to a general graph, we could find the shortest path to a leaf using breadth-first search.

Algorithm

We use a depth-first search to record in our graph each edge travelled from parent to node.

After, we use a breadth-first search on nodes that started with a value of k, so that we are visiting nodes in order of their distance to k. When the node is a leaf (it has one outgoing edge, where the root has a "ghost" edge to null), it must be the answer.


Complexity Analysis

Time Complexity: O(N)O(N) where NN is the number of nodes in the given input tree. We visit every node a constant number of times.

Space Complexity: O(N)O(N), the size of the graph.

Approach #2: Annotate Closest Leaf [Accepted]
Intuition and Algorithm

Say from each node, we already knew where the closest leaf in it's subtree is. Using any kind of traversal plus memoization, we can remember this information.

Then the closest leaf to the target (in general, not just subtree) has to have a lowest common ancestor with the target that is on the path from the root to the target. We can find the path from root to target via any kind of traversal, and look at our annotation for each node on this path to determine all leaf candidates, choosing the best one.


Complexity Analysis

Time and Space Complexity: O(N)O(N). The analysis is the same as in Approach #1.
     */

    class Solution1 {
        public int findClosestLeaf(TreeNode root, int k) {
            Map<TreeNode, List<TreeNode>> graph = new HashMap();
            dfs(graph, root, null);

            Queue<TreeNode> queue = new LinkedList();
            Set<TreeNode> seen = new HashSet();

            for (TreeNode node: graph.keySet()) {
                if (node != null && node.val == k) {
                    queue.add(node);
                    seen.add(node);
                }
            }

            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if (node != null) {
                    if (graph.get(node).size() <= 1)
                        return node.val;
                    for (TreeNode nei: graph.get(node)) {
                        if (!seen.contains(nei)) {
                            seen.add(nei);
                            queue.add(nei);
                        }
                    }
                }
            }
            throw null;
        }

        public void dfs(Map<TreeNode, List<TreeNode>> graph, TreeNode node, TreeNode parent) {
            if (node != null) {
                if (!graph.containsKey(node)) graph.put(node, new LinkedList<TreeNode>());
                if (!graph.containsKey(parent)) graph.put(parent, new LinkedList<TreeNode>());
                graph.get(node).add(parent);
                graph.get(parent).add(node);
                dfs(graph, node.left, node);
                dfs(graph, node.right, node);
            }
        }
    }

    class Solution2 {
        List<TreeNode> path;
        Map<TreeNode, LeafResult> annotation;

        public int findClosestLeaf(TreeNode root, int k) {
            path = new ArrayList();
            annotation = new HashMap();

            dfs(root, k);

            int distanceFromTarget = path.size() - 1;
            int dist = Integer.MAX_VALUE;
            TreeNode leaf = null;
            for (TreeNode node: path) {
                LeafResult lr = closestLeaf(node);
                if (lr.dist + distanceFromTarget < dist) {
                    dist = lr.dist + distanceFromTarget;
                    leaf = lr.node;
                }
                distanceFromTarget--;
            }
            return leaf.val;
        }

        public boolean dfs(TreeNode node, int k) {
            if (node == null) {
                return false;
            } else if (node.val == k) {
                path.add(node);
                return true;
            } else {
                path.add(node);
                boolean ans = dfs(node.left, k);
                if (ans) return true;
                ans = dfs(node.right, k);
                if (ans) return true;
                path.remove(path.size() - 1);
                return false;
            }
        }

        public LeafResult closestLeaf(TreeNode root) {
            if (root == null) {
                return new LeafResult(null, Integer.MAX_VALUE);
            } else if (root.left == null && root.right == null) {
                return new LeafResult(root, 0);
            } else if (annotation.containsKey(root)) {
                return annotation.get(root);
            } else {
                LeafResult r1 = closestLeaf(root.left);
                LeafResult r2 = closestLeaf(root.right);
                LeafResult ans = new LeafResult(r1.dist < r2.dist ? r1.node : r2.node,
                        Math.min(r1.dist, r2.dist) + 1);
                annotation.put(root, ans);
                return ans;
            }
        }
    }
    class LeafResult {
        TreeNode node;
        int dist;
        LeafResult(TreeNode n, int d) {
            node = n;
            dist = d;
        }
    }

    public class TreeNode {

        int val;

        TreeNode left;

        TreeNode right;

        TreeNode() {}

        TreeNode(int val) { this.val = val; }

        TreeNode(int val, TreeNode left, TreeNode right) {

            this.val = val;

            this.left = left;

            this.right = right;

        }

    }


}

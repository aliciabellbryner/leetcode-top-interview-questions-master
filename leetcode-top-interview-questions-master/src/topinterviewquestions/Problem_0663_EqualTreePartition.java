package topinterviewquestions;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/*
Given the root of a binary tree, return true if you can partition the tree into two trees with equal sums of values after removing exactly one edge on the original tree.



Example 1:


Input: root = [5,10,10,null,null,2,3]
Output: true
Example 2:


Input: root = [1,2,10,null,null,2,20]
Output: false
Explanation: You cannot split the tree into two trees with equal sums after removing exactly one edge on the tree.


Constraints:

The number of nodes in the tree is in the range [1, 104].
-105 <= Node.val <= 105
 */
public class Problem_0663_EqualTreePartition {
/*
Approach #1: Depth-First Search [Accepted]
Intuition and Algorithm

After removing some edge from parent to child, (where the child cannot be the original root) the subtree rooted at child must be half the sum of the entire tree.

Let's record the sum of every subtree. We can do this recursively using depth-first search. After, we should check that half the sum of the entire tree occurs somewhere in our recording (and not from the total of the entire tree.)

Our careful treatment and analysis above prevented errors in the case of these trees:

  0
 / \
-1  1

 0
  \
   0

Complexity Analysis

Time Complexity: O(N)O(N) where NN is the number of nodes in the input tree. We traverse every node.

Space Complexity: O(N)O(N), the size of seen and the implicit call stack in our DFS.
 */


    class Solution {
        Stack<Integer> seen;
        public boolean checkEqualTree(TreeNode root) {
            seen = new Stack();
            int total = sum(root);
            seen.pop();
            if (total % 2 == 0)
                for (int s: seen)
                    if (s == total / 2)
                        return true;
            return false;
        }

        public int sum(TreeNode node) {
            if (node == null) return 0;
            seen.push(sum(node.left) + sum(node.right) + node.val);
            return seen.peek();
        }
    }


    //diss Simple solution with only one HashMap<>.
    //The idea is to use a hash table to record all the different sums of each subtree in the tree. If the total sum of the tree is sum, we just need to check if the hash table constains sum/2.
    //
    //The following code has the correct result at a special case when the tree is [0,-1,1], which many solutions dismiss. I think this test case should be added.
    //
    //Java version:
    public boolean checkEqualTree(TreeNode root) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int sum = getsum(root, map);
        if(sum == 0)return map.getOrDefault(sum, 0) > 1;
        return sum%2 == 0 && map.containsKey(sum/2);
    }

    public int getsum(TreeNode root, Map<Integer, Integer> map ){
        if(root == null)return 0;
        int cur = root.val + getsum(root.left, map) + getsum(root.right, map);
        map.put(cur, map.getOrDefault(cur,0) + 1);
        return cur;
    }


    //no hashmap or hashset
    class Solution2 {
        public boolean checkEqualTree(TreeNode root) {
            int sum = sumNodes(root);
            if (sum % 2 != 0) return false;
            return splitTree(root, sum >> 1);
        }
        private int sumNodes(TreeNode root) {
            if (root == null) return 0;
            return root.val + sumNodes(root.left) + sumNodes(root.right);
        }
        private boolean splitTree(TreeNode root, int sum) {
            if (root == null) return false;
            if ((root.left != null && sum == sumNodes(root.left)) ||
                    (root.right != null && sum == sumNodes(root.right))) return true;
            return splitTree(root.left, sum) || splitTree(root.right, sum);
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

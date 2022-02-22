package topinterviewquestions;
/*
Given the root of a binary tree, return the length of the longest consecutive sequence path.

The path refers to any sequence of nodes from some starting node to any node in the tree along the parent-child connections. The longest consecutive path needs to be from parent to child (cannot be the reverse).



Example 1:


Input: root = [1,null,3,2,4,null,null,null,5]
Output: 3
Explanation: Longest consecutive sequence path is 3-4-5, so return 3.
Example 2:


Input: root = [2,null,3,2,null,1]
Output: 2
Explanation: Longest consecutive sequence path is 2-3, not 3-2-1, so return 2.


Constraints:

The number of nodes in the tree is in the range [1, 3 * 104].
-3 * 104 <= Node.val <= 3 * 104
 */
public class Problem_0298_BinaryTreeLongestConsecutiveSequence_G {
    /*
Solution
Approach #1 (Top Down Depth-first Search) [Accepted]
Algorithm

A top down approach is similar to an in-order traversal. We use a variable length to store the current consecutive path length and pass it down the tree. As we traverse, we compare the current node with its parent node to determine if it is consecutive. If not, we reset the length.

private int maxLength = 0;
public int longestConsecutive(TreeNode root) {
    dfs(root, null, 0);
    return maxLength;
}

private void dfs(TreeNode p, TreeNode parent, int length) {
    if (p == null) return;
    length = (parent != null && p.val == parent.val + 1) ? length + 1 : 1;
    maxLength = Math.max(maxLength, length);
    dfs(p.left, p, length);
    dfs(p.right, p, length);
}
@lightmark presents a neat approach without storing the maxLength as a global variable.

public int longestConsecutive(TreeNode root) {
    return dfs(root, null, 0);
}

private int dfs(TreeNode p, TreeNode parent, int length) {
    if (p == null) return length;
    length = (parent != null && p.val == parent.val + 1) ? length + 1 : 1;
    return Math.max(length, Math.max(dfs(p.left, p, length),
                                     dfs(p.right, p, length)));
}
Complexity analysis

Time complexity : O(n)O(n). The time complexity is the same as an in-order traversal of a binary tree with nn nodes.

Space complexity : O(n)O(n). The extra space comes from implicit stack space due to recursion. For a skewed binary tree, the recursion could go up to nn levels deep.

Approach #2 (Bottom Up Depth-first Search) [Accepted]
Algorithm

The bottom-up approach is similar to a post-order traversal. We return the consecutive path length starting at current node to its parent. Then its parent can examine if its node value can be included in this consecutive path.

private int maxLength = 0;
public int longestConsecutive(TreeNode root) {
    dfs(root);
    return maxLength;
}

private int dfs(TreeNode p) {
    if (p == null) return 0;
    int L = dfs(p.left) + 1;
    int R = dfs(p.right) + 1;
    if (p.left != null && p.val + 1 != p.left.val) {
        L = 1;
    }
    if (p.right != null && p.val + 1 != p.right.val) {
        R = 1;
    }
    int length = Math.max(L, R);
    maxLength = Math.max(maxLength, length);
    return length;
}
Complexity analysis

Time complexity : O(n)O(n). The time complexity is the same as a post-order traversal in a binary tree, which is O(n)O(n).

Space complexity : O(n)O(n). The extra space comes from implicit stack space due to recursion. For a skewed binary tree, the recursion could go up to nn levels deep.
     */

    public class Solution1_1 {
        private int maxLength = 0;
        public int longestConsecutive(TreeNode root) {
            dfs(root, null, 0);
            return maxLength;
        }

        private void dfs(TreeNode p, TreeNode parent, int length) {
            if (p == null) return;
            length = (parent != null && p.val == parent.val + 1) ? length + 1 : 1;
            maxLength = Math.max(maxLength, length);
            dfs(p.left, p, length);
            dfs(p.right, p, length);
        }

    }

    //a little bit improvement
    //@lightmark presents a neat approach without storing the maxLength as a global variable.
    public class Solution1_2 {
        public int longestConsecutive(TreeNode root) {
            return dfs(root, null, 0);
        }

        private int dfs(TreeNode p, TreeNode parent, int length) {
            if (p == null) return length;
            length = (parent != null && p.val == parent.val + 1) ? length + 1 : 1;
            return Math.max(length, Math.max(dfs(p.left, p, length),
                    dfs(p.right, p, length)));
        }
    }


    public class Solution {

        private int maxLength = 0;
        public int longestConsecutive(TreeNode root) {
            dfs(root);
            return maxLength;
        }

        private int dfs(TreeNode p) {
            if (p == null) return 0;
            int L = dfs(p.left) + 1;
            int R = dfs(p.right) + 1;
            if (p.left != null && p.val + 1 != p.left.val) {
                L = 1;
            }
            if (p.right != null && p.val + 1 != p.right.val) {
                R = 1;
            }
            int length = Math.max(L, R);
            maxLength = Math.max(maxLength, length);
            return length;
        }
    }

    //
    /*
    Easy Java DFS, is there better time complexity solution?


Just very intuitive depth-first search, send cur node value to the next level and compare it with the next level node.

public class Solution {
    private int max = 0;
    public int longestConsecutive(TreeNode root) {
        if(root == null) return 0;
        helper(root, 0, root.val);
        return max;
    }

    public void helper(TreeNode root, int cur, int target){
        if(root == null) return;
        if(root.val == target) cur++;
        else cur = 1;
        max = Math.max(cur, max);
        helper(root.left, cur, root.val + 1);
        helper(root.right, cur, root.val + 1);
    }
}
     */

/*
public int longestConsecutive(TreeNode root) {

     if(root == null) return 0;
     return Math.max(helper(root.left,root.val + 1, 1),helper(root.right,root.val + 1,1));
}

private int helper(TreeNode n, int t, int c){
    if(n == null) return c;
    if(n.val != t) c = 1;
    else c++;

    int m =  Math.max(helper(n.left,n.val + 1, c),helper(n.right,n.val + 1,c));
    return Math.max(m,c);
}
 */

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

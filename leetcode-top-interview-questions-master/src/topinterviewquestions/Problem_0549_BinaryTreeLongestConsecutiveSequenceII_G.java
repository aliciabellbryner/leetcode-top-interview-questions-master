package topinterviewquestions;
/*
Given the root of a binary tree, return the length of the longest consecutive path in the tree.

A consecutive path is a path where the values of the consecutive nodes in the path differ by one. This path can be either increasing or decreasing.

For example, [1,2,3,4] and [4,3,2,1] are both considered valid, but the path [1,2,4,3] is not valid.
On the other hand, the path can be in the child-Parent-child order, where not necessarily be parent-child order.



Example 1:


Input: root = [1,2,3]
Output: 2
Explanation: The longest consecutive path is [1, 2] or [2, 1].
Example 2:


Input: root = [2,1,3]
Output: 3
Explanation: The longest consecutive path is [1, 2, 3] or [3, 2, 1].


Constraints:

The number of nodes in the tree is in the range [1, 3 * 104].
-3 * 104 <= Node.val <= 3 * 104
 */
public class Problem_0549_BinaryTreeLongestConsecutiveSequenceII_G {
    /*
Summary
Find the length of Longest Consecutive Path in Binary Tree. The path can be either increasing or decreasing i.e. [1,2,3,4] and [4,3,2,1] are both considered valid. The path can be child-parent-child or parent-child.

Solution
Approach #1 Brute Force [Time Limit Exceeded]
Since there are no cycles in a tree, there must be exactly one unique path from one node to another. So, the number of paths possible will be equal to number of pairs of nodes {{N}\choose{2}}(
2
N
​
 ), where NN is the number of nodes.

Brute force solution of this problem is to find the path between every two nodes and check whether it is increasing or decreasing. In this way we can find maximum length increasing or decreasing sequence.

Complexity Analysis

Time complexity: O(n^3)O(n
3
 ). Total possible number of paths are n^2n
2
  and checking every path whether it is increasing or decreasing will take O(n)O(n) for each path.

Space complexity: O(n^3)O(n
3
 ). n^2n
2
  paths each with O(n)O(n) nodes.

Approach #2 Single traversal
Algorithm

For every node, let's associate two values/variables named inrinr and dcrdcr, where inrinr represents the length of the longest incrementing branch below the current node including itself, and dcrdcr represents the length of the longest decrementing branch below the current node (including itself).

We make use of a recursive function longestPath(node) which returns an array of the form [inr, dcr][inr,dcr] for the calling node. We start off by assigning both inrinr and dcrdcr as 1 for the current node. This is because the node itself always forms a consecutive increasing as well as decreasing path of length 1.

Then, we obtain the length of the longest path for the left child of the current node using longestPath(root.left). Now, if the left child's value is one less than the current node, it forms a decreasing sequence with the current node. Thus, the dcrdcr value for the current node is stored as the left child's dcrdcr value + 1. But, if the left child's value is 1 greater than the current node's value, it forms an incrementing sequence with the current node. Thus, we update the current node's inrinr value as left\_child(inr) + 1left_child(inr)+1.

Then, we do the same process with the right child as well. But, for obtaining the inrinr and dcrdcr value for the current node, we need to consider the maximum value out of the two values obtained from the left and the right child for both inrinr and dcrdcr, since we need to consider the longest sequence possible.

Further, after we've obtained the final updated values of inrinr and dcrdcr for a node, we update the length of the longest consecutive path found so far as maxval = \text{max}(inr + dcr - 1)maxval=max(inr+dcr−1). We subtract 1 so that the current node is not counted twice, as both inrinr and dcrdcr include the current node in the path length.

The following animation will help clarify the process:

Current
1 / 16

Complexity Analysis

Time complexity : O(n)O(n). The whole tree is traversed only once.
Space complexity : O(n)O(n). The recursion goes up to a depth of nn in the worst case.
     */

    public class Solution2 {
        int maxval = 0;

        public int longestConsecutive(TreeNode root) {
            longestPath(root);
            return maxval;
        }

        public int[] longestPath(TreeNode root) {
            if (root == null) {
                return new int[] {0,0};
            }

            int inr = 1, dcr = 1;
            if (root.left != null) {
                int[] left = longestPath(root.left);
                if (root.val == root.left.val + 1) {
                    dcr = left[1] + 1;
                } else if (root.val == root.left.val - 1) {
                    inr = left[0] + 1;
                }
            }

            if (root.right != null) {
                int[] right = longestPath(root.right);
                if (root.val == root.right.val + 1) {
                    dcr = Math.max(dcr, right[1] + 1);
                } else if (root.val == root.right.val - 1) {
                    inr = Math.max(inr, right[0] + 1);
                }
            }

            maxval = Math.max(maxval, dcr + inr - 1);
            return new int[] {inr, dcr};
        }
    }

    //diss
    //Neat Java Solution Single pass O(n)
    class Solution3 {
        int maxLen = 0;
        public int longestConsecutive(TreeNode root) {
            if (root == null) {
                return 0;
            }

            dfs(root);
            return maxLen;
        }

        /** maintain a globe variable to dynamically update the max result while doing recursion
         * For the recursion function, we can return a pair of values: max increasing value and max decreasing value.
         * We do not care whether max path is from left or right subtree, we only care the actual value of max increasing path and
         * actual value of max decreasing path. After getting the return value from both of left subtree and right subtree,
         * we can get maxInc and maxDec value by comparing leftInc and rightInc, as well as leftDec and rightDec. Then
         * we are ready to update globe value of max length */
        public NodeStatus dfs(TreeNode curNode) {
            if (curNode == null) {
                return new NodeStatus(0, 0);
            }

            int leftInc = 1, rightInc = 1;
            int leftDec = 1, rightDec = 1;

            // if left subtree has return value of max increasing path length and max decreasing path length
            if (curNode.left != null) {
                NodeStatus leftStatus = dfs(curNode.left);
                if (curNode.val == curNode.left.val + 1) {
                    leftInc = leftStatus.inc + 1;
                }
                else if (curNode.val == curNode.left.val - 1){
                    leftDec = leftStatus.dec + 1;
                }
            }

            // if right subtree has return value of max increasing path length and max decreasing path length
            if (curNode.right != null) {
                NodeStatus rightStatus = dfs(curNode.right);
                if (curNode.val == curNode.right.val + 1) {
                    rightInc = rightStatus.inc + 1;
                }
                else if (curNode.val == curNode.right.val - 1){
                    rightDec = rightStatus.dec + 1;
                }
            }
            // calculate max increasing value and max decreasing value for current node
            int maxInc = Math.max(leftInc, rightInc);
            int maxDec = Math.max(leftDec, rightDec);

            /**
             * max consecutive path has three major conditions:
             * 1. increasing path from only left subtree or only right subtree: maxInc
             * 2. decreasing path from only left subtree or only right subtree: maxDec
             * 3. increasing path from left and decreasing from right through root
             * OR decreasing path from right and increasing path from left through root: maxInc + maxDec - 1
             * */
            maxLen = Math.max(maxLen, Math.max(maxInc, maxDec));
            maxLen = Math.max(maxLen, maxInc + maxDec - 1);
            return new NodeStatus(maxInc, maxDec);
        }

        class NodeStatus {
            int inc;
            int dec;

            public NodeStatus(int increasing, int decreasing) {
                this.inc = increasing;
                this.dec = decreasing;
            }
        }
    }

    //Java solution, Binary Tree Post Order Traversal
    public class Solution6 {
        int max = 0;

        class Result {
            TreeNode node;
            int inc;
            int des;
        }

        public int longestConsecutive(TreeNode root) {
            traverse(root);
            return max;
        }

        private Result traverse(TreeNode node) {
            if (node == null) return null;

            Result left = traverse(node.left);
            Result right = traverse(node.right);

            Result curr = new Result();
            curr.node = node;
            curr.inc = 1;
            curr.des = 1;

            if (left != null) {
                if (node.val - left.node.val == 1) {
                    curr.inc = Math.max(curr.inc, left.inc + 1);
                }
                else if (node.val - left.node.val == -1) {
                    curr.des = Math.max(curr.des, left.des + 1);
                }
            }

            if (right != null) {
                if (node.val - right.node.val == 1) {
                    curr.inc = Math.max(curr.inc, right.inc + 1);
                }
                else if (node.val - right.node.val == -1) {
                    curr.des = Math.max(curr.des, right.des + 1);
                }
            }

            max = Math.max(max, curr.inc + curr.des - 1);

            return curr;
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

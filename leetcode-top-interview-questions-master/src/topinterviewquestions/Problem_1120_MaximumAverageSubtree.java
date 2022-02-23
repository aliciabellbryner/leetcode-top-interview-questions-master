package topinterviewquestions;
/*
Given the root of a binary tree, return the maximum average value of a subtree of that tree. Answers within 10-5 of the actual answer will be accepted.

A subtree of a tree is any node of that tree plus all its descendants.

The average value of a tree is the sum of its values, divided by the number of nodes.



Example 1:


Input: root = [5,6,1]
Output: 6.00000
Explanation:
For the node with value = 5 we have an average of (5 + 6 + 1) / 3 = 4.
For the node with value = 6 we have an average of 6 / 1 = 6.
For the node with value = 1 we have an average of 1 / 1 = 1.
So the answer is 6 which is the maximum.
Example 2:

Input: root = [0,null,1]
Output: 1.00000


Constraints:

The number of nodes in the tree is in the range [1, 104].
0 <= Node.val <= 105
 */
public class Problem_1120_MaximumAverageSubtree {
    /*Approach 1: Postorder Traversal
Intuition and Algorithm

To calculate average value of a subtree rooted at node, we need two things:

Sum of all values of the nodes in the subtree of node, let's refer to it as ValueSum(node).
Count of the nodes in the node subtree, let's refer to it as NodeCount(node).
Then, the average for subtree rooted at node will be ValueSum(node)/NodeCount(node).

Now, to calculate these values for a subtree rooted at node, we can derive them from the child nodes of node.

ValueSum(node) = ValueSum(node.left) + ValueSum(node.right) + Value(node)
NodeCount(node) = NodeCount(node.left) + NodeCount(node.right) + 1
Also, for any leaf node leaf, we know that:

ValueSum(leaf) = node.val
NodeCount(leaf) = 1
Looking at these equations, we can see that we can calculate average for each of the node in the tree by traversing bottom up i.e. first visit and calculate ValueSum and NodeCount for child nodes and then use these child nodes values to solve for parent node. This order of tree traversal is popularly known as postorder traversal.

img

You can read more about different binary tree traversals here.


Complexity Analysis

Time complexity : O(N)O(N), where NN is the number of nodes in the tree. This is because we visit each and every node only once, as we do in postorder traversal.

Space complexity : O(N)O(N), because we will create NN states for each of the nodes in the tree. Also, in cases where we have a skewed tree, we will implicitly maintain a recursion stack of size NN, hence space complexity from this will also be O(N)O(N).

     */

    class Solution {
        // for each node in the tree, we will maintain three values
        class State {
            // count of nodes in the subtree
            int nodeCount;

            // sum of values in the subtree
            int valueSum;

            // max average found in the subtree
            double maxAverage;

            State(int nodes, int sum, double maxAverage) {
                this.nodeCount = nodes;
                this.valueSum = sum;
                this.maxAverage = maxAverage;
            }
        }

        public double maximumAverageSubtree(TreeNode root) {
            return maxAverage(root).maxAverage;
        }

        State maxAverage(TreeNode root) {
            if (root == null) {
                return new State(0, 0, 0);
            }

            // postorder traversal, solve for both child nodes first.
            State left = maxAverage(root.left);
            State right = maxAverage(root.right);

            // now find nodeCount, valueSum and maxAverage for current node `root`
            int nodeCount = left.nodeCount + right.nodeCount + 1;
            int sum = left.valueSum + right.valueSum + root.val;
            double maxAverage = Math.max(
                    (1.0 * (sum)) / nodeCount, // average for current node
                    Math.max(right.maxAverage, left.maxAverage) // max average from child nodes
            );

            return new State(nodeCount, sum, maxAverage);
        }
    }

    //diss
    //[Java] 8 line clean recursive code w/ brief comment and analysis.
    //Time & space: O(n), n is the # of nodes in the tree.
    public double maximumAverageSubtree(TreeNode root) {
        return helper(root)[2];
    }
    private double[] helper(TreeNode n) {
        if (n == null)  // base case.
            return new double[]{0, 0, 0}; // sum, count  & average of nodes
        double[] l = helper(n.left), r = helper(n.right); // recurse to children.
        double child = Math.max(l[2], r[2]); // larger of the children.
        double sum = n.val + l[0] + r[0], cnt = 1 + l[1] + r[1]; // sum & count of subtree rooted at n.
        double maxOfThree = Math.max(child, sum  / cnt); // largest out of n and its children.
        return new double[]{sum, cnt, maxOfThree};
    }


    //[Java] Easy post order recursive O(N)
    class Solution2 {
        double res;
        public double maximumAverageSubtree(TreeNode root) {
            res = 0;
            sumAndNum(root);
            return res;
        }

        //{num,sum}
        public int[] sumAndNum(TreeNode node){
            if(node==null) return new int[]{0,0};
            int sum = node.val;
            int num = 1;
            int[] left = sumAndNum(node.left);
            int[] right = sumAndNum(node.right);
            num+=left[0]+right[0];
            sum+=left[1]+right[1];
            res = Math.max(res, (double)sum/(double)num);
            return new int[]{num,sum};
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

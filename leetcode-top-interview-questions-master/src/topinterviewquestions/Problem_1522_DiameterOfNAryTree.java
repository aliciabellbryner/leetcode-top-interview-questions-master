package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

/*
Given a root of an N-ary tree, you need to compute the length of the diameter of the tree.

The diameter of an N-ary tree is the length of the longest path between any two nodes in the tree. This path may or may not pass through the root.

(Nary-Tree input serialization is represented in their level order traversal, each group of children is separated by the null value.)



Example 1:



Input: root = [1,null,3,2,4,null,5,6]
Output: 3
Explanation: Diameter is shown in red color.
Example 2:



Input: root = [1,null,2,null,3,4,null,5,null,6]
Output: 4
Example 3:



Input: root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
Output: 7


Constraints:

The depth of the n-ary tree is less than or equal to 1000.
The total number of nodes is between [1, 104].
 */
public class Problem_1522_DiameterOfNAryTree {
/*
Overview
We are asked to calculate the diameter of a N-ary tree, which is defined as the longest path between any two nodes in the tree.

At first glance, it seems that we might have to enumerate all pairs of nodes, in order to find out the longest path.

Yet, there are certain insights that would allow us to dramatically reduce the scope of enumeration.

The first insight is that the longest path in a tree can only happen between two leaves nodes or between a leaf node and the root node.

example of paths

The second insight is that each non-leaf node acts as a bridge for the paths between its descendant leaves nodes. If we pick two longest sub-paths from a non-leaf node to its descendant leaves nodes, and combine them together, then the resulting path would be the longest path among all possible ones that are bridged by this non-leaf node.

example of subpaths

As one could see from the above graph, the longest path of the tree would be one of the combined paths from the top two longest sub-paths bridged by a non-leaf node (node 2 in the above graph).

With the above insights, to find the diameter of the tree, it suffices to enumerate all non-leaf nodes and select the top two longest sub-paths bridged by each non-leaf node.

The above idea could be implemented with the help of two important concepts in the tree data structure, namely the height and depth of a node.

In this article, we will present two algorithms with regards to the concept of height and depth respectively.

Approach 1: Distance with Height
Intuition

The height of a node is defined as the length of the longest downward path to a leaf node from that node.

Based on the above definition, a leaf node will have a height of zero.

As we explained in the overview section, the longest path that is bridged by a non-leaf node will come from the combination of two longest sub-paths downward to the leaves nodes from this non-leaf node.

As one might see now, the sub-paths that we mentioned above consist of the top two largest heights of the children nodes.

If we define the top two largest heights of the children nodes as height(node.child_m) and height(node.child_n), then the longest path bridged by this node would be height(node.child_m) + height(node.child_n) + 2.

formula height

Algorithm

Let us first define a function called height(node) which returns the height of the node. The function can be implemented via recursion, based on the following formula:

\text{height(node)} = \max\big(\text{height(child)}\big) + 1, \space \forall \text{child} \in \text{node.children}height(node)=max(height(child))+1, ∀child∈node.children

More importantly, within the function of height(node), we need to select the top two largest heights of its children nodes. With these top two largest heights, we calculate the length of the combined path, which would be the candidate as the diameter of the entire tree.

There are two ways to select the top two largest heights:

A straight-forward way would be that we keep the heights of all children nodes in an array, and then we sort the array and select the top two largest elements.

A constant-space solution would be that we use only two variables which keep track of the current top two largest elements respectively. While we iterate through all the heights, we update the two variables accordingly.

In the following implementation, we opt for the second approach.


Complexity Analysis

Let NN be the number of nodes in the tree.

Time Complexity: \mathcal{O}(N)O(N)

We enumerate each node in the tree once and only once via recursion.
Space Complexity: \mathcal{O}(N)O(N)

We employed only constant-sized variables in the algorithm.

On the other hand, we used recursion which will incur additional memory consumption in the function call stack. In the worst case where all the nodes are chained up in a single path, the recursion will pile up NN times.

As a result, the overall space complexity of the algorithm is \mathcal{O}(N)O(N).

Approach 2: Distance with Depth
Intuition

The depth of a node is the length of the path to the root node.

Still, we would like to know the longest path between two leaves nodes bridged by a non-leaf node. But this time we could calculate it with the concept of depth, rather than height.

If we know the top two largest depths among two leaves nodes starting from the node, namely depth(node.leaf_m) and depth(node.leaf_n), then this longest path could be calculated as the sum of top two largest depths minus the depth of the parent node, namely
depth(node.leaf_m) + depth(node.leaf_n) - 2 * depth(node).

formula depth

Algorithm

Let us define a function called maxDepth(node) which returns the maximum depth of the leaves nodes starting from the node.

Again, we could implement it with recursion, with the following formula:

\text{maxDepth(node)} = \max\big(\text{maxDepth(node.child)}\big), \space \forall \text{child} \in \text{node.children}maxDepth(node)=max(maxDepth(node.child)), ∀child∈node.children

Similarly, within the function, we will also select the top two largest depths. With these top two largest depths, we will update the diameter accordingly.


Complexity Analysis

Let NN be the number of nodes in the tree.

Time Complexity: \mathcal{O}(N)O(N)

We enumerate each node in the tree once and only once via recursion.
Space Complexity: \mathcal{O}(N)O(N)

We employed only constant-sized variables in the algorithm.

On the other hand, we used recursion which will incur additional memory consumption in the function call stack. In the worst case where all the nodes are chained up in a single path, the recursion will pile up NN times.

As a result, the overall space complexity of the algorithm is \mathcal{O}(N)O(N).
 */

    //Approach 1: Distance with Height
    class Solution1 {
        int diameter = 0;

        /**
         * return the height of the node
         */
        protected int height(Node node) {
            if (node.children.size() == 0)
                return 0;

            // select the top two largest heights
            int maxHeight1 = 0, maxHeight2 = 0;
            for (Node child : node.children) {
                int parentHeight = height(child) + 1;
                if (parentHeight > maxHeight1) {
                    maxHeight2 = maxHeight1;
                    maxHeight1 = parentHeight;
                } else if (parentHeight > maxHeight2) {
                    maxHeight2 = parentHeight;
                }
                // calculate the distance between the two farthest leaves nodes.
                int distance = maxHeight1 + maxHeight2;
                this.diameter = Math.max(this.diameter, distance);
            }

            return maxHeight1;
        }

        public int diameter(Node root) {
            this.diameter = 0;
            height(root);
            return diameter;
        }
    }


    //Approach 2: Distance with Depth
    class Solution2 {
        protected int diameter = 0;

        /**
         * return the maximum depth of leaves nodes descending from the given node
         */
        protected int maxDepth(Node node, int currDepth) {
            if (node.children.size() == 0)
                return currDepth;

            // select the top two largest depths
            int maxDepth1 = currDepth, maxDepth2 = 0;
            for (Node child : node.children) {
                int depth = maxDepth(child, currDepth + 1);
                if (depth > maxDepth1) {
                    maxDepth2 = maxDepth1;
                    maxDepth1 = depth;
                } else if (depth > maxDepth2) {
                    maxDepth2 = depth;
                }
                // calculate the distance between the two farthest leaves nodes.
                int distance = maxDepth1 + maxDepth2 - 2 * currDepth;
                this.diameter = Math.max(this.diameter, distance);
            }

            return maxDepth1;
        }

        public int diameter(Node root) {
            this.diameter = 0;
            maxDepth(root, 0);
            return diameter;
        }
    }

    class Node {
        public int val;
        public List<Node> children;


        public Node() {
            children = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            children = new ArrayList<Node>();
        }

        public Node(int _val,ArrayList<Node> _children) {
            val = _val;
            children = _children;
        }
    };
}

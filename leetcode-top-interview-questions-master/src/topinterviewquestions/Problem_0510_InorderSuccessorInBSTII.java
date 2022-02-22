package topinterviewquestions;
/*
Given a node in a binary search tree, return the in-order successor of that node in the BST. If that node has no in-order successor, return null.

The successor of a node is the node with the smallest key greater than node.val.

You will have direct access to the node but not to the root of the tree. Each node will have a reference to its parent node. Below is the definition for Node:

class Node {
    public int val;
    public Node left;
    public Node right;
    public Node parent;
}


Example 1:


Input: tree = [2,1,3], node = 1
Output: 2
Explanation: 1's in-order successor node is 2. Note that both the node and the return value is of Node type.
Example 2:


Input: tree = [5,3,6,2,4,null,null,1], node = 6
Output: null
Explanation: There is no in-order successor of the current node, so the answer is null.


Constraints:

The number of nodes in the tree is in the range [1, 104].
-105 <= Node.val <= 105
All Nodes will have unique values.


Follow up: Could you solve it without looking up any of the node's values?
 */
public class Problem_0510_InorderSuccessorInBSTII {
/*
Successor and Predecessor
Successor = "after node", i.e. the next node in the inorder traversal, or the smallest node after the current one.

Predecessor = "before node", i.e. the previous node in the inorder traversal, or the largest node before the current one.

img


Approach 1: Iteration
Intuition

There are two possible situations here :

Node has a right child, and hence its successor is somewhere lower in the tree. To find the successor, go to the right once and then as many times to the left as you could.
pic

Node has no right child, then its successor is somewhere upper in the tree. To find the successor, go up till the node that is left child of its parent. The answer is the parent. Beware that there could be no successor (= null successor) in such a situation.
pac

fic

Algorithm

If the node has a right child, and hence its successor is somewhere lower in the tree. Go to the right once and then as many times to the left as you could. Return the node you end up with.

Node has no right child, and hence its successor is somewhere upper in the tree. Go up till the node that is left child of its parent. The answer is the parent.

Implementation


Complexity Analysis

Time complexity : \mathcal{O}(H)O(H), where HH is the height of the tree. That means \mathcal{O}(\log N)O(logN) in the average case, and \mathcal{O}(N)O(N) in the worst case, where NN is the number of nodes in the tree.
Space complexity : \mathcal{O}(1)O(1), since no additional space is allocated during the calculation.
 */
class Solution {
    public Node inorderSuccessor(Node x) {
        // the successor is somewhere lower in the right subtree
        if (x.right != null) {
            x = x.right;
            while (x.left != null) x = x.left;
            return x;
        }

        // the successor is somewhere upper in the tree
        while (x.parent != null && x == x.parent.right) x = x.parent;
        return x.parent;
    }
}


//discussion
    //Java find in parents or find in descendents
class Solutio2 {
    public Node inorderSuccessor(Node x) {
        if (x.right == null) {
            Node result = x.parent;
            while (result != null && result.val < x.val) {
                result = result.parent;
            }
            return result;
        } else {
            Node result = x.right;
            while (result.left != null) {
                result = result.left;
            }
            return result;
        }
    }
}

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    };
}

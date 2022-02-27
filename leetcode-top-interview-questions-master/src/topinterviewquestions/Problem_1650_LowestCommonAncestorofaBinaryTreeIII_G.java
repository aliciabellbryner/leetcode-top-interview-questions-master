package topinterviewquestions;

import java.util.HashSet;
import java.util.Set;

/*
Given two nodes of a binary tree p and q, return their lowest common ancestor (LCA).

Each node will have a reference to its parent node. The definition for Node is below:

class Node {
    public int val;
    public Node left;
    public Node right;
    public Node parent;
}
According to the definition of LCA on Wikipedia: "The lowest common ancestor of two nodes p and q in a tree T is the lowest node that has both p and q as descendants (where we allow a node to be a descendant of itself)."



Example 1:


Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.
Example 2:


Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5 since a node can be a descendant of itself according to the LCA definition.
Example 3:

Input: root = [1,2], p = 1, q = 2
Output: 1


Constraints:

The number of nodes in the tree is in the range [2, 105].
-109 <= Node.val <= 109
All Node.val are unique.
p != q
p and q exist in the tree.
 */
//此题的listnode有parent
public class Problem_1650_LowestCommonAncestorofaBinaryTreeIII_G {
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    }
    //https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iii/discuss/932914/Java-in-6-lines
    public Node lowestCommonAncestor(Node p, Node q) {
        Node a = p, b = q;
        while (a != b) {
            a = a == null? q : a.parent;
            b = b == null? p : b.parent;
        }
        return a;
    }


    //more intuitive solution, but bad complexity
    class Solution {
        public Node lowestCommonAncestor(Node p, Node q) {
            Set<Node> set = new HashSet<Node>();
            Node temp = p;
            while (temp != null) {
                set.add(temp);
                temp = temp.parent;
            }
            temp = q;
            while (temp != null) {
                if (set.contains(temp))
                    break;
                else
                    temp = temp.parent;
            }
            return temp;
        }
    }

}


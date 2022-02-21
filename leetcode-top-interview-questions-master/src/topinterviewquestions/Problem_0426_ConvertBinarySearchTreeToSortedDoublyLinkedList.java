package topinterviewquestions;

import java.util.*;
/*
Convert a Binary Search Tree to a sorted Circular Doubly-Linked List in place.

You can think of the left and right pointers as synonymous to the predecessor and successor pointers in a doubly-linked list. For a circular doubly linked list, the predecessor of the first element is the last element, and the successor of the last element is the first element.

We want to do the transformation in place. After the transformation, the left pointer of the tree node should point to its predecessor, and the right pointer should point to its successor. You should return the pointer to the smallest element of the linked list.



Example 1:



Input: root = [4,2,5,1,3]


Output: [1,2,3,4,5]

Explanation: The figure below shows the transformed BST. The solid line indicates the successor relationship, while the dashed line means the predecessor relationship.

Example 2:

Input: root = [2,1,3]
Output: [1,2,3]


Constraints:

The number of nodes in the tree is in the range [0, 2000].
-1000 <= Node.val <= 1000
All the values of the tree are unique.
 */
public class Problem_0426_ConvertBinarySearchTreeToSortedDoublyLinkedList {
    class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }


    //https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/discuss/154659/Divide-and-Conquer-without-Dummy-Node-Java-Solution
    //Step 1: Divide:
    //We divide tree into three parts: left subtree, root node, right subtree.
    //Convert left subtree into a circular doubly linked list as well as the right subtree.
    //Be careful. You have to make the root node become a circular doubly linked list.
    //
    //Step 2: Conquer:
    //Firstly, connect left circular doubly linked list with the root circular doubly linked list.
    //Secondly, connect them with the right circular doubly linked list. Here we go!
    class Solution {
        public Node treeToDoublyList(Node root) {
            if (root == null) {
                return null;
            }

            Node leftHead = treeToDoublyList(root.left);
            Node rightHead = treeToDoublyList(root.right);
            root.left = root;
            root.right = root;
            return connect(connect(leftHead, root), rightHead);
        }

        // Used to connect two circular doubly linked lists. n1 is the head of circular DLL as well as n2.
        private Node connect(Node n1, Node n2) {
            if (n1 == null) {
                return n2;
            }
            if (n2 == null) {
                return n1;
            }

            Node tail1 = n1.left;
            Node tail2 = n2.left;

            tail1.right = n2;
            n2.left = tail1;
            tail2.right = n1;
            n1.left = tail2;

            return n1;
        }
    }

    //Approach 1: Recursion
    //Time complexity : O(N) since each node is processed exactly once.
    //Space complexity : O(N). We have to keep a recursion stack of the size of the tree height,
    // which is O(logN) for the best case of completely balanced tree and O(N) for the worst case of completely unbalanced tree.
    class Solution1 {
        // the smallest (first) and the largest (last) nodes
        Node first = null;
        Node last = null;

        public Node treeToDoublyList(Node root) {
            if (root == null) return null;

            helper(root);
            // close DLL
            last.right = first;
            first.left = last;
            return first;
        }

        public void helper(Node node) {
            if (node != null) {
                // left
                helper(node.left);
                // node
                if (last != null) {
                    // link the previous node (last)
                    // with the current one (node)
                    last.right = node;
                    node.left = last;
                }
                else {
                    // keep the smallest node
                    // to close DLL later on
                    first = node;
                }
                last = node;
                // right
                helper(node.right);
            }
        }
    }




}

package topinterviewquestions;

import java.util.ArrayDeque;
import java.util.Deque;

public class Problem_0430_FlattenAMultilevelDoublyLinkedList {
    //https://leetcode.com/problems/flatten-a-multilevel-doubly-linked-list/solution/
    //Approach 2: DFS by Iteration


    // Definition for a Node.
    class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;

        public Node() {}

        public Node(int _val,Node _prev,Node _next,Node _child) {
            val = _val;
            prev = _prev;
            next = _next;
            child = _child;
        }
    };
    class Solution {
        public Node flatten(Node head) {
            if (head == null) {
                return head;
            }

            Node dummyNode = new Node(0, null, head, null);
            Node cur, prev = dummyNode;

            Deque<Node> stack = new ArrayDeque<>();
            stack.push(head);

            while (!stack.isEmpty()) {
                cur = stack.pop();
                prev.next = cur;
                cur.prev = prev;

                if (cur.next != null) {
                    stack.push(cur.next);
                }
                if (cur.child != null) {
                    stack.push(cur.child);
                    // don't forget to remove all child pointers.
                    cur.child = null;
                }
                prev = cur;
            }
            // detach the pseudo node from the result
            dummyNode.next.prev = null;
            return dummyNode.next;
        }
    }
}

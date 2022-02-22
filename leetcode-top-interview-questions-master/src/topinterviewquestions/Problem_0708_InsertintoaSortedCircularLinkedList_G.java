package topinterviewquestions;
/*
Given a Circular Linked List node, which is sorted in ascending order, write a function to insert a value insertVal into the list such that it remains a sorted circular list. The given node can be a reference to any single node in the list and may not necessarily be the smallest value in the circular list.

If there are multiple suitable places for insertion, you may choose any place to insert the new value. After the insertion, the circular list should remain sorted.

If the list is empty (i.e., the given node is null), you should create a new single circular list and return the reference to that single node. Otherwise, you should return the originally given node.



Example 1:



Input: head = [3,4,1], insertVal = 2
Output: [3,4,1,2]
Explanation: In the figure above, there is a sorted circular list of three elements. You are given a reference to the node with value 3, and we need to insert 2 into the list. The new node should be inserted between node 1 and node 3. After the insertion, the list should look like this, and we should still return node 3.



Example 2:

Input: head = [], insertVal = 1
Output: [1]
Explanation: The list is empty (given head is null). We create a new single circular list and return the reference to that single node.
Example 3:

Input: head = [1], insertVal = 0
Output: [1,0]


Constraints:

The number of nodes in the list is in the range [0, 5 * 104].
-106 <= Node.val, insertVal <= 106
 */
public class Problem_0708_InsertintoaSortedCircularLinkedList_G {
    /*
    Solution
Approach 1: Two-Pointers Iteration
Intuition

As simple as the problem might seem to be, it is actually not trivial to write a solution that covers all cases.

Often the case for the problems with linked list, one could apply the approach of Two-Pointers Iteration, where one uses two pointers as surrogate to traverse the linked list.

One of reasons of having two pointers rather than one is that in singly-linked list one does not have a reference to the precedent node, therefore we keep an additional pointer which points to the precedent node.

For this problem, we iterate through the cyclic list using two pointers, namely prev and curr. When we find a suitable place to insert the new value, we insert it between the prev and curr nodes.

pic

Algorithm

First of all, let us define the skeleton of two-pointers iteration algorithm as follows:

As we mentioned in the intuition, we loop over the linked list with two pointers (i.e. prev and curr) step by step. The termination condition of the loop is that we get back to the starting point of the two pointers (i.e. prev == head)

During the loop, at each step, we check if the current place bounded by the two pointers is the right place to insert the new value.

If not, we move both pointers one step forwards.

Now, the tricky part of this problem is to sort out different cases that our algorithm should deal with within the loop, and then design a concise logic to handle them sound and properly. Here we break it down into three general cases.

Case 1). The value of new node sits between the minimal and maximal values of the current list. As a result, it should be inserted within the list.

pic

As we can see from the above example, the new value (6) sits between the minimal and maximal values of the list (i.e. 1 and 9). No matter where we start from (in this example we start from the node {3}), the new node would end up being inserted between the nodes {5} and {7}.

The condition is to find the place that meets the constraint of {prev.val <= insertVal <= curr.val}.

Case 2). The value of new node goes beyond the minimal and maximal values of the current list, either less than the minimal value or greater than the maximal value. In either case, the new node should be added right after the tail node (i.e. the node with the maximal value of the list).

Here are the examples with the same input list as in the previous example.

pic

pic

Firstly, we should locate the position of the tail node, by finding a descending order between the adjacent, i.e. the condition of {prev.val > curr.val}, since the nodes are sorted in ascending order, the tail node would have the greatest value of all nodes.

Furthermore, we check if the new value goes beyond the values of tail and head nodes, which are pointed by the prev and curr pointers respectively.

The Case 2.1 corresponds to the condition where the value to be inserted is greater than or equal to the one of tail node, i.e. {insertVal >= prev.val}.

The Case 2.2 corresponds to the condition where the value to be inserted is less than or equal to the head node, i.e. {insertVal <= curr.val}.

Once we locate the tail and head nodes, we basically extend the original list by inserting the value in between the tail and head nodes, i.e. in between the prev and curr pointers, the same operation as in the Case 1.

Case 3). Finally, there is one case that does not fall into any of the above two cases. This is the case where the list contains uniform values.

Though not explicitly stated in the problem description, our sorted list can contain some duplicate values. And in the extreme case, the entire list has only one single unique value.

pic

In this case, we would end up looping through the list and getting back to the starting point.

The followup action is just to add the new node after any node in the list, regardless the value to be inserted. Since we are back to the starting point, we might as well add the new node right after the starting point (our entrance node).

Note that, we cannot skip the iteration though, since we have to iterate through the list to determine if our list contains a single unique value.

The above three cases cover the scenarios within and after our iteration loop. There is however one minor corner case we still need to deal with, where we have an empty list. This, we could easily handle before the loop.


Complexity Analysis

Time Complexity: \mathcal{O}(N)O(N) where NN is the size of list. In the worst case, we would iterate through the entire list.

Space Complexity: \mathcal{O}(1)O(1). It is a constant space solution.
     */

    class Solution {
        public Node insert(Node head, int insertVal) {
            if (head == null) {
                Node newNode = new Node(insertVal, null);
                newNode.next = newNode;
                return newNode;
            }

            Node prev = head;
            Node curr = head.next;
            boolean toInsert = false;

            do {
                if (prev.val <= insertVal && insertVal <= curr.val) {
                    // Case 1).
                    toInsert = true;
                } else if (prev.val > curr.val) {
                    // Case 2).
                    if (insertVal >= prev.val || insertVal <= curr.val)
                        toInsert = true;
                }

                if (toInsert) {
                    prev.next = new Node(insertVal, curr);
                    return head;
                }

                prev = curr;
                curr = curr.next;
            } while (prev != head);

            // Case 3).
            prev.next = new Node(insertVal, curr);
            return head;
        }
    }


    //discussion
    //My idea is similar to solution 2, but easier to follow I think. The most tricky corner case in this question is when all values are the same. Here is the the code with comments. I made it a bit verbose to readability
    public Node insert(Node head, int insertVal) {
        Node node = new Node(insertVal, null);
        //deal with head==null
        if(head==null)
        {
            node.next = node;
            return node;
        }

        //Step1: find the largest node
        Node first = head;
        while(first.next.val>=first.val)
        {
            first = first.next;
            if(first==head) break; //break the cycle when all values are the same
        }
        //At this point, first is either the largest value or head
        //Corner case, when first==head, it can also be the largest value, e.g. 5->4->1->5

        //Step2: deal with the case where all values are the same e.g. 2->2->2->2
        if(first==head&&first.val==first.next.val)
            insertAfter(first, node);
            //Step3: after the checking above, first is the largest value for sure
        else if(first.val<=insertVal||first.next.val>=insertVal)//insertVal is either larger than max or smaller than min
            insertAfter(first, node);
        else{
            Node smallest = first.next;
            while(smallest.next.val<=insertVal) //find the last value smaller than insertVal
                smallest = smallest.next;
            insertAfter(smallest, node);
        }
        return head;
    }

    private void insertAfter(Node cur, Node node) //insert node after cur
    {
        Node temp = cur.next;
        cur.next = node;
        node.next = temp;
    }



    //one-pass
    //SOLUTION 1 ONE PASS
    //Just need to be super clear about all the edge cases, especially when start is null, single node or has duplicates, and need to be able to divide the problem up clearly into categories
    //
    //CASE 1 if there is tipping point in the list, which means that there are at least 2 distinct values, we name the node that has the max value to be the tipping point, the node after tipping point has the min value (min != max)
    //
    //CASE 1A: if the to be inserted value x is in a climbing stage, which means there is a node satisfying node.val <= x <= node.next.val, we insert x after this node
    //CASE 1B: if the to be inserted value x is the new min or max value after its insertion, x needs to be inserted after the tipping point
    //CASE 2 if there is NO tipping point in the list, which means that all nodes in the list have the same value
    //
    //we just insert x before we traverse back to start node
    // Test case 1:  insert(null, 1)
// Test case 2:  insert(1->null, 0)
// Test case 3:  insert(1->null, 1)
// Test case 4:  insert(1->null, 2)
// Test case 5:  insert(1->1->1->null, 0)
// Test case 6:  insert(1->1->1->null, 1)
// Test case 7:  insert(1->1->1->null, 2)
// Test case 8:  insert(1->1->3->3->null, 0)
// Test case 9:  insert(1->1->3->3->null, 1)
// Test case 10: insert(1->1->3->3->null, 2)
// Test case 11: insert(1->1->3->3->null, 3)

    class Solution2 {
        public Node insert(Node start, int x) {
            // if start is null, create a node pointing to itself and return
            if (start == null) {
                Node node = new Node(x, null);
                node.next = node;
                return node;
            }
            // is start is NOT null, try to insert it into correct position
            Node cur = start;
            while (true) {
                // case 1A: has a tipping point, still climbing
                if (cur.val < cur.next.val) {
                    if (cur.val <= x && x <= cur.next.val) { // x in between cur and next
                        insertAfter(cur, x);
                        break;
                    }
                    // case 1B: has a tipping point, about to return back to min node
                } else if (cur.val > cur.next.val) {
                    if (cur.val <= x || x <= cur.next.val) { // cur is the tipping point, x is max or min val
                        insertAfter(cur, x);
                        break;
                    }
                    // case 2: NO tipping point, all flat
                } else {
                    if (cur.next == start) {  // insert x before we traverse all nodes back to start
                        insertAfter(cur, x);
                        break;
                    }
                }
                // None of the above three cases met, go to next node
                cur = cur.next;
            }
            return start;
        }

        // insert value x after Node cur
        private void insertAfter(Node cur, int x) {
            cur.next = new Node(x, cur.next);
        }
    }


    class Node {
        public int val;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _next) {
            val = _val;
            next = _next;
        }
    };


}

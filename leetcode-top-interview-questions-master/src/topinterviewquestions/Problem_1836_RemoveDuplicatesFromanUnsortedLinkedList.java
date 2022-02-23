package topinterviewquestions;

import java.util.HashMap;
import java.util.Map;

/*
Given the head of a linked list, find all the values that appear more than once in the list and delete the nodes that have any of those values.

Return the linked list after the deletions.



Example 1:


Input: head = [1,2,3,2]
Output: [1,3]
Explanation: 2 appears twice in the linked list, so all 2's should be deleted. After deleting all 2's, we are left with [1,3].
Example 2:


Input: head = [2,1,1,2]
Output: []
Explanation: 2 and 1 both appear twice. All the elements should be deleted.
Example 3:


Input: head = [3,2,2,1,3,2,4]
Output: [1,4]
Explanation: 3 appears twice and 2 appears three times. After deleting all 3's and 2's, we are left with [1,4].


Constraints:

The number of nodes in the list is in the range [1, 105]
1 <= Node.val <= 105
 */
public class Problem_1836_RemoveDuplicatesFromanUnsortedLinkedList {

    public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
    /*
diss
     */


    //Simple Java Two Pass 90% With Explanation and Picture - Time Complexity O(N) - Space Complexity O(N)
    //At first pass, count the number of times a node value repeated using a HashMap.
    //At second pass, just check the value of each node. if it is repeated more than 1 time, just remove it.
    //Note: We need to have:
    //A HashMap to count
    //A TempNodeHead, in case the list head is one of the nodes we need to delete
    //How we remove a node:
    //We keep track of Prev Node
    //If current Node is repeated more than 1 time and needs to be deleted here is the formula:
    //prev.next = curr.next; // to point the previous node to the next current node as the current node needs to be deleted
    //curr.next = null; // set the current node next to null as we want to set it's pointer to null (as we want to delete the curr node from the list)
    //curr = prev; // make sure we set the pointer back to prev node as the curr.next is null and curr node has been removed from the list.
    //Time Complexity is O(2N) as we pass through the list two times where N is the length of the list.
    //Hence O(2N) ~= O(N)
    //Space Complexity is O(N) in worst scenario where each Node in the list has unique (not repeated) values.
    public ListNode deleteDuplicatesUnsorted(ListNode head) {
        HashMap<Integer, Integer> repeatedNodes = new HashMap<>();
        ListNode tempHead = new ListNode(); // temporary node as a temp head in case the head is one of the nodes needs to be deleted or head is null
        tempHead.next = head; // set temporary node next to head
        ListNode curr = tempHead.next;
        while(curr!=null) {
            repeatedNodes.put(curr.val, repeatedNodes.getOrDefault(curr.val, 0)+1); // count the repeated node values
            curr=curr.next;
        }

        ListNode prev = tempHead; // set the previous node (parent node) to temporary head
        curr = tempHead.next;
        while(curr !=null) {
            if(repeatedNodes.get(curr.val)>1) { // if current node value is bigger than 1, it needs to be deleted
                prev.next = curr.next; // set previous node next to curr.next instead of curr. As curr node needs to be deleted
                curr.next = null; // set curr next to null as we need to remove curr from the list, so need to set it's pointer to null
                curr = prev; // make sure we set current node to previous node as current node has been already removed from the list
            }
            prev=curr;
            curr = curr.next;
        }

        return tempHead.next; // we should return tempHead.next but not tempHead as tempHead is a dummy node we created
    }


    //clean O(N) Solution
    class Solution {
        public ListNode deleteDuplicatesUnsorted(ListNode head) {
            // Key: val  Value: its frequency
            Map<Integer, Integer> map = new HashMap<>();
            ListNode curr = head;
            while (curr != null) {
                map.put(curr.val, map.getOrDefault(curr.val, 0) + 1);
                curr = curr.next;
            }

            ListNode dummy = new ListNode(0);
            curr = dummy;
            while (head != null) {
                if (map.get(head.val) == 1) {
                    curr.next = head;
                    curr = curr.next;
                }

                head = head.next;
            }

            curr.next = null;
            return dummy.next;
        }
    }
}

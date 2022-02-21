package topinterviewquestions;

import java.util.Stack;
/*
Given the head of a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list. Return the linked list sorted as well.

Example 1:


Input: head = [1,2,3,3,4,4,5]
Output: [1,2,5]
Example 2:


Input: head = [1,1,1,2,3]
Output: [2,3]


Constraints:

The number of nodes in the list is in the range [0, 300].
-100 <= Node.val <= 100
The list is guaranteed to be sorted in ascending order.
 */
public class Problem_0082_RemoveDuplicatesFromSortedListII {
	public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
	}

	class Solution {
		public ListNode deleteDuplicates(ListNode head) {
			// dummy node
			ListNode dummy = new ListNode(0, head);

			// predecessor = the last node
			// before the sublist of duplicates
			ListNode pre = dummy;

			while (head != null) {
				// if it's a beginning of duplicates sublist
				// skip all duplicates
				if (head.next != null && head.val == head.next.val) {
					// move till the end of duplicates sublist
					while (head.next != null && head.val == head.next.val) {
						head = head.next;
					}
					// skip all duplicates
					pre.next = head.next;
					// otherwise, move predecessor
				} else {
					pre = pre.next;
				}

				// move forward
				head = head.next;
			}
			return dummy.next;
		}
	}

}

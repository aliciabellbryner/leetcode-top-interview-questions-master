package topinterviewquestions;

import java.util.Stack;

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

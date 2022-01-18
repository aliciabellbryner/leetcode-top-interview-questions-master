package topinterviewquestions;

public class Problem_0092_ReverseLinkedListII {
  public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
	public ListNode reverseBetween(ListNode head, int left, int right) {
		if (head == null) {
			return null;
		}
		ListNode dummy = new ListNode(0);
		dummy.next = head;
		ListNode pre = dummy;
		for(int i = 0; i<left-1; i++) {
			pre = pre.next;
		}
		ListNode start = pre.next;
		ListNode then = start.next;
		for (int i = 0; i < right - left; i++) {
			start.next = then.next;
			then.next = pre.next;
			pre.next = then;
			then = start.next;
		}
		return dummy.next;
	}
}

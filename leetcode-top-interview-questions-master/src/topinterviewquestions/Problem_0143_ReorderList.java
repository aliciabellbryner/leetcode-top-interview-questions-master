package topinterviewquestions;

public class Problem_0143_ReorderList {

	// 这个类不用提交
	public static class ListNode {
		public int val;
		public ListNode next;
	}

	public static void reorderList(ListNode head) {
		if (head == null || head.next == null || head.next.next == null) {
			return;
		}
		ListNode fast = head;
		ListNode slow = head;
		while (fast.next != null && fast.next.next != null) {
			fast = fast.next.next;
			slow = slow.next;
		}
		ListNode secondHead = slow.next;
		slow.next = null;
		secondHead = resverseList(secondHead);
		merge(head, secondHead);
	}

	private static ListNode resverseList (ListNode ln) {
		if (ln == null) {
			return ln;
		}
		ListNode prev = null;
		ListNode cur = ln;
		while (cur != null) {
			ListNode next = cur.next;
			cur.next = prev;
			prev = cur;
			cur = next;
		}
		return prev;
	}

	private static void merge (ListNode head1, ListNode head2) {

		while (head2 != null) {
			ListNode next = head1.next;
			head1.next = head2;
			head1 = head2;
			head2 = next;
		}
	}
}

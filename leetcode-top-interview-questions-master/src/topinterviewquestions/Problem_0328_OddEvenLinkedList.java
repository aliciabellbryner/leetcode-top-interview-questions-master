package topinterviewquestions;

public class Problem_0328_OddEvenLinkedList {

	// 提交时不要提交这个类
	public static class ListNode {
		int val;
		ListNode next;
	}


	//leetcode solution
	//time O(N), space O(1)
	public ListNode oddEvenList(ListNode head) {
		if (head == null) return null;
		ListNode odd = head, even = head.next, firstEven = even;
		while (even != null && even.next != null) {
			odd.next = even.next;
			odd = odd.next;
			even.next = odd.next;
			even = even.next;
		}
		odd.next = firstEven;
		return head;
	}


	//zuo's
	public ListNode oddEvenList2(ListNode head) {
		ListNode firstOdd = null;
		ListNode firstEven = null;
		ListNode odd = null;
		ListNode even = null;
		ListNode next = null;
		int count = 1;
		while (head != null) {
			next = head.next;
			head.next = null;
			if ((count & 1) == 1) {
				firstOdd = firstOdd == null ? head : firstOdd;
				if (odd != null) {
					odd.next = head;
				}
				odd = head;
			} else {
				firstEven = firstEven == null ? head : firstEven;
				if (even != null) {
					even.next = head;
				}
				even = head;
			}
			count++;
			head = next;
		}
		if (odd != null) {
			odd.next = firstEven;
		}
		return firstOdd != null ? firstOdd : firstEven;//or you can say "return firstOdd != null ? firstOdd : null;"
	}

}

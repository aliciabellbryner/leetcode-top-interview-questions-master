package topinterviewquestions;

public class Problem_0234_PalindromeLinkedList {

	public static class ListNode {
		public int val;
		public ListNode next;
	}


	//Approach 3: Reverse Second Half In-place
	//https://leetcode.com/problems/palindrome-linked-list/solution/
	//Time complexity : O(n)
	//space O(1)
	public boolean isPalindrome(ListNode head) {
		if (head == null) {
			return true;
		}
		// Find the end of first half using fast and slow pointer
		ListNode fast = head;
		ListNode slow = head;
		while (fast.next != null && fast.next.next != null) {//after the loop slow is end of first half list
			fast = fast.next.next;
			slow = slow.next;
		}
		ListNode leftEnd = slow;
		ListNode rightStart = reverseList(leftEnd.next);
		// Check whether or not there is a palindrome.
		ListNode p1 = head;
		ListNode p2 = rightStart;
		boolean res = true;
		while (res && p2 != null) {
			if (p1.val != p2.val) res = false;
			p1 = p1.next;
			p2 = p2.next;
		}

		// Restore the list and return the res.
		leftEnd.next = reverseList(rightStart);
		return res;
	}

	// Taken from https://leetcode.com/problems/reverse-linked-list/solution/
	private ListNode reverseList(ListNode head) {
		ListNode prev = null;
		ListNode curr = head;
		while (curr != null) {
			ListNode nextTemp = curr.next;
			curr.next = prev;
			prev = curr;
			curr = nextTemp;
		}
		return prev;
	}

	private ListNode endOfFirstHalf(ListNode head) {
		ListNode fast = head;
		ListNode slow = head;
		while (fast.next != null && fast.next.next != null) {
			fast = fast.next.next;
			slow = slow.next;
		}
		return slow;
	}

	//如果我们不能修改原来的head，可以用下面的方法，如果可以修改，那只要前面一部分
	public static boolean isPalindrome2(ListNode head) {
		if (head == null || head.next == null) {
			return true;
		}
		//先利用快慢指针找到中点的前一个节点n1
		ListNode n1 = head;
		ListNode n2 = head;
		while (n2.next != null && n2.next.next != null) {
			n1 = n1.next;
			n2 = n2.next.next;
		}
		n2 = n1.next;
		n1.next = null;
		ListNode nextNode = null;//n1充当pre的作用
		while (n2 != null) {
			nextNode = n2.next;
			n2.next = n1;
			n1 = n2;
			n2 = nextNode;
		}
		nextNode = n1;//n1就是原来的linkedlist最后一个节点，把n1赋值给nextNode，目的是为了后面恢复的时候要用
		n2 = head;
		boolean res = true;
		while (n1 != null && n2 != null) {//n1是后半段反转后的新起点，n2是原先的头节点
			if (n1.val != n2.val) {
				res = false;
				break;
			}
			n1 = n1.next;
			n2 = n2.next;
		}
		//最后一部需要把原先的linkedlist还原
		n1 = nextNode.next;
		nextNode.next = null;
		while (n1 != null) {
			n2 = n1.next;
			n1.next = nextNode;
			nextNode = n1;
			n1 = n2;
		}
		return res;
	}

}

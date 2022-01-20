package topinterviewquestions;

public class Problem_0160_IntersectionOfTwoLinkedLists {

	public class ListNode {
		int val;
		ListNode next;
	}

	//best solution: Two Pointers
	//https://leetcode.com/problems/intersection-of-two-linked-lists/solution/
	//Time complexity : O(N + M)O(N+M).
	//Space complexity : O(1)O(1).
	//相当于用两个pointer，分别遍历两个listnode，当短的那个到头了就把这个pointer转到长的ln的头，
	// 长的那个到头了就转到短的头，这样他们就会在相交点重逢
	public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
		ListNode pA = headA;
		ListNode pB = headB;
		while (pA != pB) {
			pA = pA == null ? headB : pA.next;
			pB = pB == null ? headA : pB.next;
		}
		return pA;
		// Note: In the case lists do not intersect, the pointers for A and B
		// will still line up in the 2nd iteration, just that here won't be
		// a common node down the list and both will reach their respective ends
		// at the same time. So pA will be NULL in that case.
	}

	//my solution
	public ListNode getIntersectionNode_j(ListNode headA, ListNode headB) {
		if (headA == null || headB == null) {
			return null;
		}
		ListNode curA = headA;
		int lenA = 0;
		while (curA != null) {
			lenA++;
			curA = curA.next;
		}
		ListNode curB = headB;
		int lenB = 0;
		while (curB != null) {
			lenB++;
			curB = curB.next;
		}
		ListNode longln = lenA > lenB ? headA : headB;
		ListNode shortln = longln == headA ? headB : headA;
		int dif = lenA > lenB ? (lenA - lenB) : (lenB - lenA);
		while (dif-- > 0) {
			longln = longln.next;
		}
		while (longln != null) {
			if (longln == shortln) {
				return longln;
			}
			longln = longln.next;
			shortln = shortln.next;
		}
		return null;
	}


	//zuo's solution: similiar as mine
	public static ListNode getIntersectionNode1(ListNode head1, ListNode head2) {
		if (head1 == null || head2 == null) {
			return null;
		}
		ListNode cur1 = head1;
		ListNode cur2 = head2;
		int n = 0;
		while (cur1.next != null) {
			n++;
			cur1 = cur1.next;
		}
		// cur1  end1
		while (cur2.next != null) {
			n--;
			cur2 = cur2.next;
		}
		// cur2 end2
		if (cur1 != cur2) {
			return null;
		}
		cur1 = n > 0 ? head1 : head2; // 谁是长链表，谁把头节点，给cur1赋值
		cur2 = cur1 == head1 ? head2 : head1;
		n = Math.abs(n);
		while (n != 0) {
			n--;
			cur1 = cur1.next;
		}
		while (cur1 != cur2) {
			cur1 = cur1.next;
			cur2 = cur2.next;
		}
		return cur1;
	}



}

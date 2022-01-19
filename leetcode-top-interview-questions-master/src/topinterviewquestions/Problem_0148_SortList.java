package topinterviewquestions;

public class Problem_0148_SortList {

  public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }


	//Approach 1: Top Down Merge Sort
	//https://leetcode.com/problems/sort-list/solution/
	//time O(NlogN) space O(logN)
	//Time Complexity: O(nlogn), where n is the number of nodes in linked list.
	// The algorithm can be split into 2 phases, Split and Merge.
	//Space Complexity: O(logn) , where nn is the number of nodes in linked list.
	// Since the problem is recursive,  we need additional space to store the recursive call stack.
	// The maximum depth of the recursion tree is logn
	public ListNode sortList(ListNode head) {
		if (head == null || head.next == null)
			return head;

		// step 1. cut the list to two halves
		ListNode prev = null, slow = head, fast = head;

		while (fast != null && fast.next != null) {
			prev = slow;
			slow = slow.next;
			fast = fast.next.next;
		}

		prev.next = null;

		// step 2. sort each half
		ListNode l1 = sortList(head);
		ListNode l2 = sortList(slow);

		// step 3. merge l1 and l2
		return merge(l1, l2);
	}

	ListNode merge(ListNode l1, ListNode l2) {
		ListNode dummy = new ListNode(0), cur = dummy;

		while (l1 != null && l2 != null) {
			if (l1.val < l2.val) {
				cur.next = l1;
				l1 = l1.next;
			} else {
				cur.next = l2;
				l2 = l2.next;
			}
			cur = cur.next;
		}

		if (l1 != null)
			cur.next = l1;

		if (l2 != null)
			cur.next = l2;

		return dummy.next;
	}



	//Approach 2: Bottom Up Merge Sort
	//Time Complexity: O(nlogn), where n is the number of nodes in linked list.
	//Space Complexity: O(1) We use only constant space for storing the reference pointers tail, nextSubList etc.
	ListNode tail = new ListNode();
	ListNode nextSubList = new ListNode();

	public ListNode sortList2(ListNode head) {
		if (head == null || head.next == null)
			return head;
		int n = getCount(head);
		ListNode start = head;
		ListNode dummyHead = new ListNode();
		for (int size = 1; size < n; size = size * 2) {
			tail = dummyHead;
			while (start != null) {
				if (start.next == null) {
					tail.next = start;
					break;
				}
				ListNode mid = split(start, size);
				merge(start, mid);
				start = nextSubList;
			}
			start = dummyHead.next;
		}
		return dummyHead.next;
	}

	ListNode split(ListNode start, int size) {
		ListNode midPrev = start;
		ListNode end = start.next;
		//use fast and slow approach to find middle and end of second linked list
		for (int index = 1; index < size && (midPrev.next != null || end.next != null); index++) {
			if (end.next != null) {
				end = (end.next.next != null) ? end.next.next : end.next;
			}
			if (midPrev.next != null) {
				midPrev = midPrev.next;
			}
		}
		ListNode mid = midPrev.next;
		midPrev.next = null;
		nextSubList = end.next;
		end.next = null;
		// return the start of second linked list
		return mid;
	}

	void merge2(ListNode list1, ListNode list2) {
		ListNode dummyHead = new ListNode();
		ListNode newTail = dummyHead;
		while (list1 != null && list2 != null) {
			if (list1.val < list2.val) {
				newTail.next = list1;
				list1 = list1.next;
				newTail = newTail.next;
			} else {
				newTail.next = list2;
				list2 = list2.next;
				newTail = newTail.next;
			}
		}
		newTail.next = (list1 != null) ? list1 : list2;
		// traverse till the end of merged list to get the newTail
		while (newTail.next != null) {
			newTail = newTail.next;
		}
		// link the old tail with the head of merged list
		tail.next = dummyHead.next;
		// update the old tail to the new tail of merged list
		tail = newTail;
	}

	int getCount(ListNode head) {
		int cnt = 0;
		ListNode ptr = head;
		while (ptr != null) {
			ptr = ptr.next;
			cnt++;
		}
		return cnt;
	}

// Zuo's solution: too complicated!!! don't use
//	//use the merge sort algorithm idea, teamFirst is the start of every merge, len is the number of node of the merge object
//	public static ListNode sortList(ListNode head) {
//		int N = 0;
//		ListNode cur = head;
//		while (cur != null) {
//			N++;
//			cur = cur.next;
//		}
//		ListNode h = head;//it is the head of new merged head
//		ListNode teamFirst = head;
//		ListNode pre = null;//it is the locater node
//		for (int len = 1; len < N; len <<= 1) {
//			while (teamFirst != null) {
//				ListNode[] hthtn = hthtn(teamFirst, len);//表示下一组要merge的子链表head tail head tail和下下一组要merge的子链表
//				ListNode[] mhmt = merge(hthtn[0], hthtn[1], hthtn[2], hthtn[3]);
//				if (h == teamFirst) {
//					h = mhmt[0];
//					pre = mhmt[1];
//				} else {
//					pre.next = mhmt[0];
//					pre = mhmt[1];
//				}
//				teamFirst = hthtn[4];
//			}
//			teamFirst = h;
//			pre = null;
//		}
//		return h;
//	}
//
//	public static ListNode[] hthtn(ListNode teamFirst, int len) {
//		ListNode ls = teamFirst;
//		ListNode le = teamFirst;
//		ListNode rs = null;
//		ListNode re = null;
//		ListNode next = null;
//		int pass = 0;
//		while (teamFirst != null) {
//			pass++;
//			if (pass <= len) {
//				le = teamFirst;
//			}
//			if (pass == len + 1) {
//				rs = teamFirst;
//			}
//			if (pass > len) {
//				re = teamFirst;
//			}
//			if (pass == (len << 1)) {
//				break;
//			}
//			teamFirst = teamFirst.next;
//		}
//		le.next = null;
//		if (re != null) {
//			next = re.next;
//			re.next = null;
//		}
//		return new ListNode[]{ls, le, rs, re, next};
//	}
//
//	public static ListNode[] merge(ListNode ls, ListNode le, ListNode rs, ListNode re) {
//		if (rs == null) {
//			return new ListNode[]{ls, le};
//		}
//		ListNode head = null;
//		ListNode pre = null;
//		ListNode cur = null;
//		ListNode tail = null;
//		while (ls != le.next && rs != re.next) {//means ls and rs haven't gone to the end of the list
//			//compare val of two sub-node
//			if (ls.val <= rs.val) {
//				cur = ls;
//				ls = ls.next;
//			} else {
//				cur = rs;
//				rs = rs.next;
//			}
//			//maintain the head and tail of the new merged node list
//			if (pre == null) {
//				head = cur;
//				pre = cur;
//			} else {
//				pre.next = cur;
//				pre = cur;
//			}
//		}
//		if (ls != le.next) {//if ls-le still has values than are not counted
//			while (ls != le.next) {
//				pre.next = ls;
//				pre = ls;
//				tail = ls;
//				ls = ls.next;
//			}
//		} else {
//			//if rs-re still has values than are not counted
//			while (rs != re.next) {
//				pre.next = rs;
//				pre = rs;
//				tail = rs;
//				rs = rs.next;
//			}
//		}
//		return new ListNode[]{head, tail};
//	}
}

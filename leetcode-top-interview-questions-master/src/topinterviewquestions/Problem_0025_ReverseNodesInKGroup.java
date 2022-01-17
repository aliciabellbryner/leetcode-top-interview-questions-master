package topinterviewquestions;

public class Problem_0025_ReverseNodesInKGroup {
  public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

    //iterative way:
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode start = head;
        ListNode end = findKthNode(head, k);
        if (end == null) {
            return head;
        }
        head = end;
        reverse(start, end);
        ListNode lastEnd = start;
        while (lastEnd.next != null) {
            start = lastEnd.next;
            end = findKthNode(start, k);
            if (end == null) {
                return head;
            }
            reverse(start, end);
            lastEnd.next = end;
            lastEnd = start;
        }
        return head;
    }
    private ListNode findKthNode(ListNode head, int k) {
        while(--k!=0 && head != null) {
            head = head.next;
        }
        return head;
    }
    private void reverse(ListNode start, ListNode end) {
        ListNode newEnd = end.next;
        ListNode pre = null;
        ListNode next = null;
        ListNode cur = start;
        while (cur != newEnd) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        start.next = newEnd;
    }


    //recursive way:
    public ListNode reverseKGroup2(ListNode head, int k) {
        ListNode cur = head;
        int count = 0;
        while (cur != null && count != k) { // find the k+1 node
            cur = cur.next;
            count++;
        }
        if (count == k) { // if k+1 node is found
            cur = reverseKGroup2(cur, k); // reverse list with k+1 node as head
            // head - head-pointer to direct part,
            // cur - head-pointer to reversed part;
            while (count-- > 0) { // reverse current k-group:
                ListNode then = head.next; // then - next head in direct part
                head.next = cur; // preappending "direct" head to the reversed list
                cur = head; // move head of reversed part to a new node
                head = then; // move "direct" head to the next node in direct part
            }
            head = cur;
        }
        return head;
    }

}

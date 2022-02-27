package topinterviewquestions;
/*

 */
public class Problem_1721_SwappingNodesinaLinkedList {
    //https://leetcode.com/problems/swapping-nodes-in-a-linked-list/discuss/1009918/Java-or-Two-Pointers-or-Detailed-Explanation-or-O(n)-time-O(1)-space
    //思路很简单粗暴，就是找到k开头和结尾的listnode，然后交换数值
    class Solution {
        public ListNode swapNodes(ListNode head, int k) {
            ListNode fast = head;
            ListNode slow = head;
            ListNode first = head, second = head;

            // Put fast (k-1) nodes after slow
            for(int i = 0; i < k - 1; ++i)
                fast = fast.next;

            // Save the node for swapping
            first = fast;

            // Move until the end of the list
            while(fast.next != null) {
                slow = slow.next;
                fast = fast.next;
            }

            // Save the second node for swapping
            // Note that the pointer second isn't necessary: we could use slow for swapping as well
            // However, having second improves readability
            second = slow;

            // Swap values
            int temp = first.val;
            first.val = second.val;
            second.val = temp;

            return head;
        }
    }

    public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
}

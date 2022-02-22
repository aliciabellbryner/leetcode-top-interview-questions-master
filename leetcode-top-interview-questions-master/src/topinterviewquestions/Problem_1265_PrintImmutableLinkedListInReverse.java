package topinterviewquestions;
/*
You are given an immutable linked list, print out all values of each node in reverse with the help of the following interface:

ImmutableListNode: An interface of immutable linked list, you are given the head of the list.
You need to use the following functions to access the linked list (you can't access the ImmutableListNode directly):

ImmutableListNode.printValue(): Print value of the current node.
ImmutableListNode.getNext(): Return the next node.
The input is only given to initialize the linked list internally. You must solve this problem without modifying the linked list. In other words, you must operate the linked list using only the mentioned APIs.



Example 1:

Input: head = [1,2,3,4]
Output: [4,3,2,1]
Example 2:

Input: head = [0,-4,-1,3,-5]
Output: [-5,3,-1,-4,0]
Example 3:

Input: head = [-2,0,6,4,4,-6]
Output: [-6,4,4,6,0,-2]


Constraints:

The length of the linked list is between [1, 1000].
The value of each node in the linked list is between [-1000, 1000].


Follow up:

Could you solve this problem in:

Constant space complexity?
Linear time complexity and less than linear space complexity?
 */
public class Problem_1265_PrintImmutableLinkedListInReverse {
    /**
     * // This is the ImmutableListNode's API interface.
     * // You should not implement it, or speculate about its implementation.
     * interface ImmutableListNode {
     *     public void printValue(); // print the value of this node.
     *     public ImmutableListNode getNext(); // return the next node.
     * };
     */
    /*
    //diss
    Jave Solution including Follow Up
142
cappuccinuo's avatar
cappuccinuo
608
November 24, 2019 10:14 AM

8.3K VIEWS

Iterative - Recursive

class Solution {
    public void printLinkedListInReverse(ImmutableListNode head) {
        if (head == null) {
            return;
        }
        printLinkedListInReverse(head.getNext());
        head.printValue();
    }
}
Iterative - With Stack

class Solution {
    public void printLinkedListInReverse(ImmutableListNode head) {
        Stack<ImmutableListNode> stack = new Stack<>();
        while (head != null) {
            stack.push(head);
            head = head.getNext();
        }
        while (!stack.isEmpty()) {
            stack.pop().printValue();
        }
    }
}
Follow Up - Constant space complexity (Time: O(n^2))

class Solution {
    public void printLinkedListInReverse(ImmutableListNode head) {
        int numNodesCount = getNumNodesCount(head);
        for (int i = numNodesCount; i >= 1; i--) {
            printNthNode(head, i);
        }
    }

    private void printNthNode(ImmutableListNode head, int index) {
        ImmutableListNode node = head;
        for (int i = 0; i < index - 1; i++) {
            node = node.getNext();
        }
        node.printValue();
    }

    private int getNumNodesCount(ImmutableListNode head) {
        int count = 0;
        ImmutableListNode node = head;
        while (node != null) {
            count++;
            node = node.getNext();
        }
        return count;
    }
}
Follow Up - Linear time complexity and less than linear space complexity
Idea - Split the list to sqrt(n) equal-size small list. Print each part in reverse order.

class Solution {
    public void printLinkedListInReverse(ImmutableListNode head) {
        // Time: O(n)
        int numNodesCount = getNumNodesCount(head);

        // Time: O(n) Space: O(sqrt(n))
        int step = (int)Math.sqrt(numNodesCount) + 1;
        Stack<ImmutableListNode> headNodes = new Stack<>();
        addNodeWithStep(head, step, headNodes);

        // Time: O(n) Space: O(sqrt(n))
        printEachHeadNodesInReverseOrder(headNodes);
    }

    private int getNumNodesCount(ImmutableListNode head) {
        int count = 0;
        ImmutableListNode node = head;
        while (node != null) {
            count++;
            node = node.getNext();
        }
        return count;
    }

    private void addNodeWithStep(ImmutableListNode head, int step, Stack<ImmutableListNode> headNodes) {
        ImmutableListNode node = head;
        int i = 0;
        while (node != null) {
            if (i % step == 0) {
                headNodes.push(node);
            }
            node = node.getNext();
            i++;
        }
    }

    private void printEachHeadNodesInReverseOrder(Stack<ImmutableListNode> headNodes) {
        ImmutableListNode startNode = null;
        ImmutableListNode endNode = null;
        ImmutableListNode tempNode = null;

        while (!headNodes.isEmpty()) {
            endNode = startNode;
            startNode = headNodes.pop();
            tempNode = startNode;

            Stack<ImmutableListNode> stack = new Stack<>();
            while (tempNode != endNode) {
                stack.push(tempNode);
                tempNode = tempNode.getNext();
            }

            while (!stack.isEmpty()) {
                stack.pop().printValue();
            }
        }
    }
}

     */


    /*
    concise recursive sqrt n

class Solution {
    public void printLinkedListInReverse(ImmutableListNode head) {
        int block = (int) Math.ceil(Math.sqrt(size(head)));
        dfsSqrtNBlock(head, block);
    }
    int size(ImmutableListNode p) {
        int n = 0;
        for (;p != null; n++, p = p.getNext()) ;
        return n;
    }
    void dfsSqrtNBlock(ImmutableListNode cur, int block) {
        if (cur == null) return;
        ImmutableListNode nxt = cur;
        for (int i = 0; i < block && nxt != null; i++,nxt = nxt.getNext()) ;
        dfsSqrtNBlock(nxt, block);
        printCurBlock(cur, block);
    }
    void printCurBlock(ImmutableListNode cur, int block) {
        if (block == 0 || cur == null) return;
        printCurBlock(cur.getNext(), block - 1);
        cur.printValue();
    }
}
     */


    /*
    All Java solutions (Including Follow-ups) with FULL explanations
30
parin2's avatar
parin2
191
Last Edit: September 26, 2020 3:52 AM

1.1K VIEWS

The natural idea is to use recursion to print the remaining list first and then the current node.

Space Complexity: O(n)
Time Complexity: O(n)

public void printLinkedListInReverse(ImmutableListNode head) {
	if (head == null) {
		return;

	}

	printLinkedListInReverse(head.getNext());
	head.printValue();
}
Follow-up #1:

Since, it's asked to implement using "constant" space compexity, we will need to give up recursion. If we give up recursion, naturally, the time complexity will be compromised. Since, this is a restrictive condition in which we cannot directly access the node, time complexity would further be compromised.

I used the idea to go to the end everytime, and print the tail first (keeping head reference with me as well). So, I implemented using two loops.

Time Complexity: O(n^2)
Space Complexity: O(1)

public void printLinkedListInReverse(ImmutableListNode head) {
	ImmutableListNode start = head,
		end = null;

	while (start != end) {
		ImmutableListNode cur = start;
		while (cur.getNext() != end) {
			cur = cur.getNext();
		}
		cur.printValue();
		end = cur;
	}
}
Follow-up #2:

Since they mentioned the space-complexity should be "less-than linear time", I attempted to make the space complexity O(log n). Since the logarithmic time-complexity can be achieved by "dividing" the list, I attempted to divide the list in two halves. I used slow and fast pointers to divide the list in two halves. And then apply recursion to print second half of the list first, and then first half of the list.

Time complexity: O(n) O(n log n) (Thank you @amirhosseinq for correcting me)
Space complexity: O(log n)

class Solution {
    public void printLinkedListInReverse(ImmutableListNode head) {
        if (head == null) {
            return;

        }

        helper(head, null);
    }

    private void helper(ImmutableListNode start, ImmutableListNode end) {
        if (start == null || start == end) {
            return;

        } else if (start.getNext() == end) {
            start.printValue();
            return;
        }

        ImmutableListNode slow = start,
            fast = start;

        while (fast != end && fast.getNext() != end) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }

        helper(slow, end);
        helper(start, slow);
    }
}
     */
}

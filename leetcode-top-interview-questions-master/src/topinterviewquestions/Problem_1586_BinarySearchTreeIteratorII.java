package topinterviewquestions;

import java.util.*;

/*
mplement the BSTIterator class that represents an iterator over the in-order traversal of a binary search tree (BST):

BSTIterator(TreeNode root) Initializes an object of the BSTIterator class. The root of the BST is given as part of the constructor. The pointer should be initialized to a non-existent number smaller than any element in the BST.
boolean hasNext() Returns true if there exists a number in the traversal to the right of the pointer, otherwise returns false.
int next() Moves the pointer to the right, then returns the number at the pointer.
boolean hasPrev() Returns true if there exists a number in the traversal to the left of the pointer, otherwise returns false.
int prev() Moves the pointer to the left, then returns the number at the pointer.
Notice that by initializing the pointer to a non-existent smallest number, the first call to next() will return the smallest element in the BST.

You may assume that next() and prev() calls will always be valid. That is, there will be at least a next/previous number in the in-order traversal when next()/prev() is called.



Example 1:



Input
["BSTIterator", "next", "next", "prev", "next", "hasNext", "next", "next", "next", "hasNext", "hasPrev", "prev", "prev"]
[[[7, 3, 15, null, null, 9, 20]], [null], [null], [null], [null], [null], [null], [null], [null], [null], [null], [null], [null]]
Output
[null, 3, 7, 3, 7, true, 9, 15, 20, false, true, 15, 9]

Explanation
// The underlined element is where the pointer currently is.
BSTIterator bSTIterator = new BSTIterator([7, 3, 15, null, null, 9, 20]); // state is   [3, 7, 9, 15, 20]
bSTIterator.next(); // state becomes [3, 7, 9, 15, 20], return 3
bSTIterator.next(); // state becomes [3, 7, 9, 15, 20], return 7
bSTIterator.prev(); // state becomes [3, 7, 9, 15, 20], return 3
bSTIterator.next(); // state becomes [3, 7, 9, 15, 20], return 7
bSTIterator.hasNext(); // return true
bSTIterator.next(); // state becomes [3, 7, 9, 15, 20], return 9
bSTIterator.next(); // state becomes [3, 7, 9, 15, 20], return 15
bSTIterator.next(); // state becomes [3, 7, 9, 15, 20], return 20
bSTIterator.hasNext(); // return false
bSTIterator.hasPrev(); // return true
bSTIterator.prev(); // state becomes [3, 7, 9, 15, 20], return 15
bSTIterator.prev(); // state becomes [3, 7, 9, 15, 20], return 9


Constraints:

The number of nodes in the tree is in the range [1, 105].
0 <= Node.val <= 106
At most 105 calls will be made to hasNext, next, hasPrev, and prev.


Follow up: Could you solve the problem without precalculating the values of the tree?
 */
public class Problem_1586_BinarySearchTreeIteratorII {
    /*
    Overview
We're asked to implement an iterator, i.e., something that can be used to traverse a container and access its elements
without entering into the details of the container implementation.

There are two standard requirements for the iterators, to use them in code easily without impacting time complexity:

To provide next and prev operations in a constant time (or in average constant time).

Do not perform any heavy operations during the iterator's initialization.

Here, the container object is a binary search tree (BST). For the BST, the standard requirement is to return elements in an ascending order. I.e., next operator returns the smallest node greater than the current one. Prev operator returns the largest node less than the current one.

img Figure 1. BST iterator. Next operator returns the smallest node greater than the current one. Prev operator returns the largest node less than the current one.

An essential property of BST is that inorder traversal of BST is an array sorted in the ascending order. Thus, the inorder traversal will be the core of the solution. As a prerequisite, you might want to check the article Recover Binary Search Tree, there all three types of inorder traversal: recursive, iterative, and Morris are discussed in details.



Approach 1: Flatten Binary Search Tree: Recursive Inorder Traversal
Let's start from the first requirement to the iterator: to provide next and prev operations in a constant time. For that, we could flatten the binary tree using recursive inorder traversal and then use a pointer to iterate over the elements. The drawback of this approach is that to initialize an iterator, one has to traverse the entire tree, which takes a linear time.

img Figure 2. Approach 1. Flatten BST and then use pointer to iterate over.

Algorithm

Constructor: flatten BST into the arr list during the iterator initialization. Recursive inorder traversal is simple: follow Left->Node->Right direction, i.e. do the recursive call for the left child, then do all the business with the node (i.e., to add node value into the list), and then do the recursive call for the right child.

Initialize list length n and pointer pointer = -1.

hasNext: compare the pointer to the list length: return pointer < n - 1.

next: increase the pointer by one and return arr[pointer].

hasPrev: compare the pointer to zero: return pointer > 0.

prev: decrease the pointer by one and return arr[pointer].

Implementation


Complexity Analysis

Time complexity: \mathcal{O}(N)O(N) for the iterator constructor, and \mathcal{O}(1)O(1) for hasNext, next, hasPrev, and prev.

Space complexity: \mathcal{O}(N)O(N) to store list arr with NN elements.


Approach 2: Follow-up: Iterative Inorder Traversal
The drawback of Approach 1 is that the iterator constructor takes a linear time. For many practical applications, the initialization in constant time is mandatory.

So, the idea is to do almost nothing during the iterator initialization and parse the bare minimum number of nodes at each next call. This bare minimum in the worst-case situation is a complete leftmost subtree of the last node. Since we need to stop and then restart tree traversal at any moment, we could use iterative inorder traversal here.

img Figure 3. The worst-case situation: one has to parse the leftmost subtree of the last processed node during the next call.

That makes the time complexity of the next call to be equal to \mathcal{O}(N)O(N) because in the worst-case of the skewed tree one has to parse the entire tree, all NN nodes.

However, the important thing to note here is that it's the worst-case time complexity. We only make such a call for the nodes which we've not yet parsed. We could save all parsed nodes in a list and then re-use them if we need to return next from the already parsed area of the tree.

img Figure 4. The average situation: the node to return is in the
parsed area.

Thus, the amortized (average) time complexity for the next call would still be \mathcal{O}(1)O(1), that is perfectly fine for the practical applications.

Algorithm

Constructor in \mathcal{O}(1)O(1):

Initialize the last processed node as root: last = root.

Initialize a list to store already processed nodes: arr.

Initialize service data structure stack to be used during the iterative inorder traversal.

Initialize pointer: pointer = -1. This pointer serves as indicator if we're in the already parsed area or not. We're in the parsed area if pointer + 1 < len(arr).

hasNext:

Return true if last node is not null, or the stack is not empty, or we're in the already parsed area: pointer + 1 < len(arr).
next:

Increase the pointer by 1: pointer += 1.

If we're not in the precomputed part of the tree, parse the bare minimum: the leftmost subtree of the last node:

Go left till you can, while the last node is not null:

Push the last node into the stack: stack.append(last).

Go left: last = left.last.

Pop the last node out of the stack: curr = stack.pop().

Append this node value to the list of the parsed nodes: arr.append(curr.val).

Go one step to the right: last = curr.right.

Otherwise, return arr[pointer].

hasPrev:

Compare the pointer to zero: return pointer > 0.
prev: decrease the pointer by one and return arr[pointer].

Implementation

Note, that Javadocs recommends to use ArrayDeque, and not Stack as a stack implementation.


Complexity Analysis

Time complexity. Let's look at the complexities one by one:

\mathcal{O}(1)O(1) for the constructor.

\mathcal{O}(1)O(1) for hasPrev.

\mathcal{O}(1)O(1) for prev.

\mathcal{O}(1)O(1) for hasNext.

\mathcal{O}(N)O(N) for next. In the worst-case of the skewed tree one has to parse the entire tree, all NN nodes.

However, the important thing to note here is that it's the worst-case time complexity. We only make such a call for the nodes which we've not yet parsed. We save all parsed nodes in a list, and then re-use them if we need to return next from the already parsed area of the tree.

Thus, the amortized (average) time complexity for the next call would still be \mathcal{O}(1)O(1).

Space complexity: \mathcal{O}(N)O(N). The space is taken by stack and arr. stack contains up to HH elements, where HH is the tree height, and arr up to NN elements.
     */
    class BSTIterator1 {

        List<Integer> arr = new ArrayList();
        int pointer;
        int n;

        public void inorder(TreeNode r, List<Integer> arr) {
            if (r == null) return;
            inorder(r.left, arr);
            arr.add(r.val);
            inorder(r.right, arr);
        }

        public BSTIterator1(TreeNode root) {
            inorder(root, arr);
            n = arr.size();
            pointer = -1;
        }

        public boolean hasNext() {
            return pointer < n - 1;
        }

        public int next() {
            ++pointer;
            return arr.get(pointer);
        }

        public boolean hasPrev() {
            return pointer > 0;
        }

        public int prev() {
            --pointer;
            return arr.get(pointer);
        }
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }


    class BSTIterator2 {

        Deque<TreeNode> stack;
        List<Integer> arr;
        TreeNode last;
        int pointer;

        public BSTIterator2(TreeNode root) {
            last = root;
            stack = new ArrayDeque();
            arr = new ArrayList();
            pointer = -1;
        }

        public boolean hasNext() {
            return !stack.isEmpty() || last != null || pointer < arr.size() - 1;
        }

        public int next() {
            ++pointer;
            // if the pointer is out of precomputed range
            if (pointer == arr.size()) {
                // process all predecessors of the last node:
                // go left till you can and then one step right
                while (last != null) {
                    stack.push(last);
                    last = last.left;
                }
                TreeNode curr = stack.pop();
                last = curr.right;

                arr.add(curr.val);
            }

            return arr.get(pointer);
        }

        public boolean hasPrev() {
            return pointer > 0;
        }

        public int prev() {
            --pointer;
            return arr.get(pointer);
        }
    }



    //diss
    class BSTIterator {
        Stack<TreeNode> stack;
        int pos;
        LinkedList<Integer> list;
        public BSTIterator(TreeNode root) {
            this.stack = new Stack<>();
            traverse(root);
            this.pos = -1;
            this.list = new LinkedList<>();
        }

        private void traverse(TreeNode node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        public boolean hasNext() {
            return pos + 1 < list.size() || !stack.isEmpty();
        }

        public int next() {
            if (++pos == list.size()) {
                TreeNode node = stack.pop();
                traverse(node.right);
                list.add(node.val);
                return node.val;
            }
            return list.get(pos);
        }

        public boolean hasPrev() {
            return pos > 0;
        }

        public int prev() {
            return list.get(--pos);
        }
    }

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * boolean param_1 = obj.hasNext();
 * int param_2 = obj.next();
 * boolean param_3 = obj.hasPrev();
 * int param_4 = obj.prev();
 */


//Java - In-Place, O(n)/O(h) (Detailed)
//Same as BST Iterator I. We need a stack to collect all in-order nodes.
//The only difference is that the tree is converted into a list.
//Here, all left nodes will be our prev that we see in ListNodes. Similarly, all right nodes will be our next.
class BSTIterator3 {
    private TreeNode dummy = new TreeNode(-1);
    private TreeNode curr = dummy;
    private Stack<TreeNode> stack = new Stack<>();

    // Init
    public BSTIterator3(TreeNode root) {
        this.collectMins(root);
    }

    // If there's no right and there's no node to append to the 'list', there's no next
    public boolean hasNext() {
        return !(curr.right == null && stack.isEmpty());
    }

    // Go forward. If no right, then we're at the end, append next node. Otherwise, just move forward
    public int next() {
        // If there's no next then we must have a node ready in the Stack
        if (curr.right == null) {
            TreeNode next = stack.pop();

            // Discover next mins and unlink
            collectMins(next.right);
            next.right = null;

            // Append to 'list'
            next.left = curr;
            curr.right = next;
        }

        curr = curr.right;

        return curr.val;
    }

    // If the 'list' is empty or we're at the first nodes, there is no prev
    public boolean hasPrev() {
        return (curr != dummy && curr.left != dummy);
    }

    // Go back
    public int prev() {
        curr = curr.left;
        return curr.val;
    }

    // Should only be called once per node
    private void collectMins(TreeNode root) {
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
    }
}


}

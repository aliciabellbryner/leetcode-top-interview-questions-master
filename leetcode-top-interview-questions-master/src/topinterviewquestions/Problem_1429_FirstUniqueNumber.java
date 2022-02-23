package topinterviewquestions;

import java.util.*;

/*
You have a queue of integers, you need to retrieve the first unique integer in the queue.

Implement the FirstUnique class:

FirstUnique(int[] nums) Initializes the object with the numbers in the queue.
int showFirstUnique() returns the value of the first unique integer of the queue, and returns -1 if there is no such integer.
void add(int value) insert value to the queue.


Example 1:

Input:
["FirstUnique","showFirstUnique","add","showFirstUnique","add","showFirstUnique","add","showFirstUnique"]
[[[2,3,5]],[],[5],[],[2],[],[3],[]]
Output:
[null,2,null,2,null,3,null,-1]
Explanation:
FirstUnique firstUnique = new FirstUnique([2,3,5]);
firstUnique.showFirstUnique(); // return 2
firstUnique.add(5);            // the queue is now [2,3,5,5]
firstUnique.showFirstUnique(); // return 2
firstUnique.add(2);            // the queue is now [2,3,5,5,2]
firstUnique.showFirstUnique(); // return 3
firstUnique.add(3);            // the queue is now [2,3,5,5,2,3]
firstUnique.showFirstUnique(); // return -1
Example 2:

Input:
["FirstUnique","showFirstUnique","add","add","add","add","add","showFirstUnique"]
[[[7,7,7,7,7,7]],[],[7],[3],[3],[7],[17],[]]
Output:
[null,-1,null,null,null,null,null,17]
Explanation:
FirstUnique firstUnique = new FirstUnique([7,7,7,7,7,7]);
firstUnique.showFirstUnique(); // return -1
firstUnique.add(7);            // the queue is now [7,7,7,7,7,7,7]
firstUnique.add(3);            // the queue is now [7,7,7,7,7,7,7,3]
firstUnique.add(3);            // the queue is now [7,7,7,7,7,7,7,3,3]
firstUnique.add(7);            // the queue is now [7,7,7,7,7,7,7,3,3,7]
firstUnique.add(17);           // the queue is now [7,7,7,7,7,7,7,3,3,7,17]
firstUnique.showFirstUnique(); // return 17
Example 3:

Input:
["FirstUnique","showFirstUnique","add","showFirstUnique"]
[[[809]],[],[809],[]]
Output:
[null,809,null,-1]
Explanation:
FirstUnique firstUnique = new FirstUnique([809]);
firstUnique.showFirstUnique(); // return 809
firstUnique.add(809);          // the queue is now [809,809]
firstUnique.showFirstUnique(); // return -1


Constraints:

1 <= nums.length <= 10^5
1 <= nums[i] <= 10^8
1 <= value <= 10^8
At most 50000 calls will be made to showFirstUnique and add.
 */
public class Problem_1429_FirstUniqueNumber {
    /*
    Approach 1: Brute Force
Intuition

The simplest solution for this problem is to create a queue as normal, and then search through it to find the first unique number.

We do this by looping through the items in the queue (starting from the oldest). For each item, we loop through the queue again, counting how many times it appears. If it only appears once, we stop and return it. If there are no items that appear only once, then we return -1.

Algorithm

We don't need to implement counting ourselves; we can use Collections.frequency(...) in Java, and .count(...) in Python.


Complexity Analysis

Let KK be the length of the initial array passed into the constructor. Let NN be the total number of items added onto the queue so far (including those from the constructor).

Time complexity :

constructor: O(K)O(K).

We create a new copy of the passed in numbers; this has a cost of O(K)O(K) time to create.

add(): O(1)O(1).

We perform a single append to a queue; this has a cost of O(1)O(1).

showFirstUnique(): O(N^2)O(N
2
 ).

Counting how many times a given item appears in the queue has a cost of O(N)O(N). This is true even for the library functions we're using.

In the worst case, we search through the entire queue without finding a unique number. At a cost of O(N)O(N) each time, this gives us a cost of O(N^2)O(N
2
 ).

Space complexity : O(N)O(N).

The memory used is simply the total number of items currently in the queue.


Approach 2: Queue and HashMap of Unique-Status
Intuition

When given a data-structure-design question like this one, a good strategy is to start simple and identify where the inefficiencies are.

In Approach 1, we performed numerous count operations; each call to showFirstUnique() performed, in the worst case, NN count operations. At a cost of O(N)O(N) per count, this was expensive! These count-ups are also repetitive, and so should be our focus for optimization.

When deciding whether or not to return a particular number, all we need to know is whether or not that number is unique. In other words, we only want to know whether it has occurred "once", or "more than once". Seeing as numbers cannot be removed from the FirstUnique data structure, we know that once a particular number is added for the second time, that number will never again be unique.

So, instead of counting how many times a number occurred in the queue, we could instead keep a HashMap of numbers to booleans, where for each number that has been added, we're storing the answer to the question is this number unique?. We'll call it isUnique. Then, when FirstUnique.add(number) is called, one of three cases will apply:

This particular number has never been seen before now. Add it to isUnique with a value of true. Also, add it to the queue.

This particular number has already been seen by isUnique, with a value of true. This means that the number was previously unique (and is currently in the queue), but with this new addition, it no longer is. Update its value to false. Do not add it to the queue.

This particular number has already been seen by isUnique, with a value of false. This means that it has already been seen twice before. We don't need to do anything, and shouldn't add it to the queue.

Then, instead of needing to perform "count" operations, the showFirstUnique() can simply look in isUnique for the information it needs.

Here is an animation showing how this works so far.

Current
1 / 26
This is a good start. However, you might have noticed another inefficiency while watching the animation: the 7 at the front needs to be stepped passed on every call to showFirstUnique(). As soon as the second 7 was added, though, this 7 was no longer unique, and so would never be the answer to a call to showFirstUnique() again (remember, this queue has no deletion operation). Therefore, there is no reason to leave it in the queue—we should remove it so that we don't keep looking at it.

But, where should we actually do these removals from the queue? In showFirstUnique(), or in add()?

If we do a removal in the add() method, we'll need to do an O(N)O(N) search of the queue to find the number, and then potentially another to remove it from the queue, (if you're chosen programming language even supports deletions from the middle of a queue!).

If we do the removal in the showFirstUnique() method, we might sometimes need to do lots of removals before we find a unique number to return. However, it will be faster across all calls to the data structure, because we didn't need to do a search to find the non-unique number like we would have with add(). We'll talk more about this in the time complexity section.

So, our best option is to do the removals in the showFirstUnique() method.

We should leave the number's value as false in isUnique, though. Like we said above, a number can never "become" unique again.

Here is an animation showing the full algorithm.

Current
1 / 19
Algorithm


Complexity Analysis

Let KK be the length of the initial array passed into the constructor.

Let NN be the total number of items added onto the queue so far (including those from the constructor).

Time complexity :

constructor: O(K)O(K).

For each of the KK numbers passed into the constructor, we're making a call to add(). As we will determine below, each call to add() has a cost of O(1)O(1). Therefore, for the KK numbers added in the constructor, we get a total cost of K \cdot O(1) = O(K)K⋅O(1)=O(K).

add(): O(1)O(1).

We check the status of the number in a HashMap, which is an O(1)O(1) operation. We also optionally modify its status in the HashMap, which, again, is an O(1)O(1) operation.

Depending on the status of the number, we add it to the queue, which is again, an O(1)O(1) operation.

Therefore, we get an O(1)O(1) time complexity for the add() method.

showFirstUnique(): O(1)O(1) (amortized).

For this implementation, the showFirstUnique() method needs to iterate down the queue until it finds a unique number. For each unique number it encounters along the way, it removes it. Removing an item from a queue has a cost of O(1)O(1). The total number of these removals we need to carry out is proportional to the number of calls to add(), because each add() corresponds to at most one removal that will ultimately have to happen. Then when we find a unique number, it is an O(1)O(1) operation to return it.

Because the number of O(1)O(1) removals is proportional to the number of calls to add(), we say that the time complexity amortizes across all calls to add() and showFirstUnique(), giving an overall time complexity of O(1)O(1) (amortized).

If you're not familiar with amortized time complexity, check out the Wikipedia page.

Space complexity : O(N)O(N).

Each number is stored up to once in the queue, and up to once in the HashMap. Therefore, we get an overall space complexity of O(N)O(N).


Approach 3: LinkedHashSet for Queue, and HashMap of Unique-Statuses
Intuition

While an amortized time of O(1)O(1) will always be better than a non-amortized time of a higher complexity class, say, O(N)O(N), non-amortized O(1)O(1) would be even better. The downside of amortized time complexities is that while the average time per method call is "good", some calls might be really slow. Imagine if every one-millionth Google search took 1,000,000 ms, and all the others took 1 ms. The amortized time would be 2 ms, which, in theory, sounds great! However, the one-in-a-million person waiting 16 minutes for their search results won't be very happy with Google! (There would probably be less bad press if every search operation just took 5 ms...).

Is there a way we could remove the amortization from the data structure we designed in Approach 2?

For this to happen, we'd need to have each "removal" happen with its corresponding add(); not during some arbitrary showFirstUnique() call in the future. This is the only way we could avoid having lots of "waiting" removal operations. How does this work, though? Didn't we already decide it was too difficult?

The trouble with doing the removal in the add() method was that it leads to a worst-case O(N)O(N) search of the queue, and potentially a worst-case O(N)O(N) removal from the middle of a queue—if it's even possible (Java doesn't allow removals from the middle of a queue).

Making the actual remove an O(1)O(1) operation isn't too difficult; we simply need to use a LinkedList-based rather than Array-based queue implementation.

Searching a LinkedList for a value is still O(N)O(N), though. The only data structures that supports search in O(1)O(1) time are hash-sets and hash-maps. But these don't maintain the input order; aren't we just going around in circles now?

There is another, not so well known, data structure we can use here: LinkedHashSet. Note that in Python, we can use an OrderedDict to achieve the same functionality. If you have never heard of it, have a look at its specs before reading any further. Can you see how it solves our problem?

A LinkedHashSet is a HashSet-LinkedList hybrid. Like a HashSet, items can be found, updated, added, and removed in O(1)O(1) time. In addition, it puts links between the entries to keep track of the order they were added. Whenever an item is removed from the LinkedHashSet, the links are updated to point to the previous and next, just like they are in an ordinary LinkedList.

In essence, a LinkedHashSet is a type of queue that supports O(1)O(1) removals from the middle! This is exactly what we need to solve the problem. We can now do the removal in the add() method, and know that it is O(1)O(1).

Algorithm


Complexity Analysis

Let KK be the length of the initial array passed into the constructor.

Let NN be the total number of items added onto the queue so far (including those from the constructor).

Time complexity :

constructor: O(K)O(K).

For each of the KK numbers passed into the constructor, we're making a call to add(). As we will determine below, each call to add() has a cost of O(1)O(1). Therefore, for the KK numbers added in the constructor, we get a total cost of K \cdot O(1) = O(K)K⋅O(1)=O(K).

add(): O(1)O(1).

Like before, we're performing a series of O(1)O(1) operations when add() is called. Additionally, we're also removing the number from the queue if it had been unique, but now no longer is. Seeing as the queue is implemented as a LinkedHashSet, the cost of this removal is O(1)O(1).

Therefore, we get an O(1)O(1) time complexity for add().

showFirstUnique(): O(1)O(1).

This time around, the implementation for showFirstUnique() is straightforward. It simply checks whether or not the queue contains any items, and if it does, it returns the first one (without removing it). This has a cost of O(1)O(1).

Space complexity : O(N)O(N).

Each number is stored up to once in the LinkedHashSet, and up to once in the HashMap. Therefore, we get an overall space complexity of O(N)O(N).
     */
    class FirstUnique2 {

        private Queue<Integer> queue = new ArrayDeque<>();
        private Map<Integer, Boolean> isUnique = new HashMap<>();

        public FirstUnique2(int[] nums) {
            for (int num : nums) {
                // Notice that we're calling the "add" method of FirstUnique; not of the queue.
                this.add(num);
            }
        }

        public int showFirstUnique() {
            // We need to start by "cleaning" the queue of any non-uniques at the start.
            // Note that we know that if a value is in the queue, then it is also in
            // isUnique, as the implementation of add() guarantees this.
            while (!queue.isEmpty() && !isUnique.get(queue.peek())) {
                queue.remove();
            }
            // Check if there is still a value left in the queue. There might be no uniques.
            if (!queue.isEmpty()) {
                return queue.peek(); // We don't want to actually *remove* the value.
            }
            return -1;
        }

        public void add(int value) {
            // Case 1: We need to add the number to the queue and mark it as unique.
            if (!isUnique.containsKey(value)) {
                isUnique.put(value, true);
                queue.add(value);
                // Case 2 and 3: We need to mark the number as no longer unique.
            } else {
                isUnique.put(value, false);
            }
        }
    }

    class FirstUnique3 {

        private Set<Integer> setQueue = new LinkedHashSet<>();
        private Map<Integer, Boolean> isUnique = new HashMap<>();

        public FirstUnique3(int[] nums) {
            for (int num : nums) {
                this.add(num);
            }
        }

        public int showFirstUnique() {
            // If the queue contains values, we need to get the first one from it.
            // We can do this by making an iterator, and getting its first item.
            if (!setQueue.isEmpty()) {
                return setQueue.iterator().next();
            }
            return -1;
        }

        public void add(int value) {
            // Case 1: This value is not yet in the data structure.
            // It should be ADDED.
            if (!isUnique.containsKey(value)) {
                isUnique.put(value, true);
                setQueue.add(value);
                // Case 2: This value has been seen once, so is now becoming
                // non-unique. It should be REMOVED.
            } else if (isUnique.get(value)) {
                isUnique.put(value, false);
                setQueue.remove(value);
            }
        }
    }


    //diss
    /*
    Method 1: HashMap + DoublyLinkedList

In order to achieve optimal time performance, we need

a doubly linked list to keep the order of insertion, and to remove a node corresponding to duplicate numbers and put a node to the end of the list, both in time O(1);
a hash table to locate the node corresponding to a certain number in amortized time O(1).
In short, the data structure is similar to the one used in LRU Cache, but simpler.


    private static final int mi = Integer.MIN_VALUE, mx = Integer.MAX_VALUE; // dummy values of head and tail.
    private static final DoublyLinkedList head = new DoublyLinkedList(mi),
                                            tail = new DoublyLinkedList(mx);
    private static class DoublyLinkedList {

        public DoublyLinkedList prev, next;
        public int val;

        public DoublyLinkedList(int v) {
            val = v;
        }

    }

    private final Map<Integer, DoublyLinkedList> intToNode = new HashMap<>(); // map values to nodes.

    public FirstUnique(int[] nums) {
        head.next = tail; // construct empty list: head connects tail.
        tail.prev = head; // construct empty list: tail connects head.
        for (int num : nums) { // add unique numbers to doubly linked list and all entries corresponding to numbers to HashMap.
            add(num);
        }
    }

    public int showFirstUnique() {
        return head.next == tail ? -1 : head.next.val; // return -1 if empty list; or the first unique value.
    }

    public void add(int value) {
        DoublyLinkedList node = intToNode.putIfAbsent(value, new DoublyLinkedList(value));
        if (node != null) { // HashMap intToNode already contains entry (value=node).
            remove(node); // value is NOT unique, hence remove it from the doubly linked list.
        }else { // HashMap intToNode does NOT contains key value.
            putToEnd(intToNode.get(value)); // value is unique, hence put it to the end of the doubly linked list.
        }
    }

    private boolean remove(DoublyLinkedList node) {
        if (node.prev == null || node.next == null) { // node NOT in the list.
            return false; // remove operation failed.
        }
        node.prev.next = node.next; // modify next pointer of the previous node.
        node.next.prev = node.prev; // modify previous pointer of the next node.
        node.prev = null; // cut off the previous pointer from node to list.
        node.next = null; // cut off the next pointer from node to list.
        return true; // remove operation succeeded.
    }

    private boolean putToEnd(DoublyLinkedList node) {
        if (tail == null || tail.prev == null) { // tail node error.
            return false; // operation failed.
        }
        node.next = tail; // assign the tail to the next pointer of node.
        node.prev = tail.prev; // assign the previous node of the tail to the next pointer of node.
        tail.prev = node; // modify previous pointer of the tail.
        node.prev.next = node; // modify next pointer of the previous node.
        return true; // operation succeeded.
    }


     */

    /*
    LinkedHashSet can guarantee to keep the order of insertion.

Method 2: HashSet + LinkedHashSet - credit to @CauchyPeano's suggestion.

    private Set<Integer> unique = new LinkedHashSet<>();
    private Set<Integer> total = new HashSet<>();

    public FirstUnique(int[] nums) {
        for (int n : nums) {
            add(n);
        }
    }

    public int showFirstUnique() {

        //for (int v : unique) {
        //    return v;
        //}
        //return -1;

        return unique.isEmpty() ? -1 : unique.iterator().next(); // credit to @yelhsabananah
}

    public void add(int value) {
        if (total.add(value)) {
            unique.add(value);
        }else {
            unique.remove(value);
        }
    }
     */

    /*
    Method 3: HashMap + LinkedHashSet

    private Set<Integer> unique = new LinkedHashSet<>();
    private Map<Integer, Integer> total = new HashMap<>();

    public FirstUnique(int[] nums) {
        for (int n : nums) {
            add(n);
        }
    }

    public int showFirstUnique() {

        //for (int v : unique) {
        //    return v;
        //}
        //return -1;

        return unique.isEmpty() ? -1 : unique.iterator().next();
    }

    public void add(int value) {
        if (total.containsKey(value)) {
            unique.remove(value);
        }else {
            unique.add(value);
        }
        total.put(value, 1 + total.getOrDefault(value, 0));
    }

Analysis:
        Init:
        Time & space: O(n), where n = nums.length.

        showFirstUnique():
        Time & space: O(1)

        add():
        Time & space: O(1)
     */



    //another diss
    class FirstUnique {
        Map<Integer, Integer> freq = new HashMap<>();
        Queue<Integer> q = new LinkedList<>();
        public FirstUnique(int[] nums) {
            for (int x : nums)
                add(x);
        }
        public int showFirstUnique() {
            while (!q.isEmpty() && freq.get(q.peek()) > 1)
                q.poll();
            return q.isEmpty() ? -1 : q.peek();
        }
        public void add(int value) {
            freq.put(value, freq.getOrDefault(value, 0) + 1);
            q.offer(value);
        }
    }

}

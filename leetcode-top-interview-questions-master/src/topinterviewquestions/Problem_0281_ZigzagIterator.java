package topinterviewquestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
Given two vectors of integers v1 and v2, implement an iterator to return their elements alternately.

Implement the ZigzagIterator class:

ZigzagIterator(List<int> v1, List<int> v2) initializes the object with the two vectors v1 and v2.
boolean hasNext() returns true if the iterator still has elements, and false otherwise.
int next() returns the current element of the iterator and moves the iterator to the next element.


Example 1:

Input: v1 = [1,2], v2 = [3,4,5,6]
Output: [1,3,2,4,5,6]
Explanation: By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,3,2,4,5,6].
Example 2:

Input: v1 = [1], v2 = []
Output: [1]
Example 3:

Input: v1 = [], v2 = [1]
Output: [1]


Constraints:

0 <= v1.length, v2.length <= 1000
1 <= v1.length + v2.length <= 2000
-231 <= v1[i], v2[i] <= 231 - 1


Follow up: What if you are given k vectors? How well can your code be extended to such cases?

Clarification for the follow-up question:

The "Zigzag" order is not clearly defined and is ambiguous for k > 2 cases. If "Zigzag" does not look right to you, replace "Zigzag" with "Cyclic".

Follow-up Example:

Input: v1 = [1,2,3], v2 = [4,5,6,7], v3 = [8,9]
Output: [1,4,8,2,5,9,3,6,7]
 */
public class Problem_0281_ZigzagIterator {
    /*
    Overview
We are asked to design a Zigzag Iterator for two vectors, so that we could output the elements in an alternative way.

The follow-up question is that what if we are given k vectors, instead of two?

Since this is a design problem, it would be more interesting to tackle the problem while taking into account the follow-up question all at once.

In this article, we will present two approaches which can be easily extended to k vectors.

Approach 1: Two-Pointers
Intuition

We are asked to iterate the elements, while alternating the vectors. One can imagine this as iterating over a two-dimension matrix, where each row represents an input vector.

The idea is that we can employ two pointers for iteration: one pointed to the vector (denoted as p_vec), and the other pointed to the element within the vector (denoted as p_elem).

two pointers

As we can see from the above graph, the vector pointer (p_vec) will move in the zigzag way (more precisely cyclic way), i.e. once it reaches the last vector, it will start all over from the first vector.

The element pointer (p_elem) increments, only when the vector pointer finishes a cycle.

We give the priority to the vector pointer, i.e. we move the vector pointer first, then the element pointer.

Algorithm

With the above-mentioned two pointers, one should have all the elements needed to implement the function of next().

To implement the function of hasNext(), we can keep account of the number of elements we output so far. Once it reaches the total number of elements in the input, we would know that there is no more element to output.

Here are some sample implementations based on the above ideas.


Complexity Analysis

Let KK be the number of input vectors, although it is always two in the setting of the problem. This variable becomes relevant, once the input becomes KK vectors.

Time Complexity:

For the next() function, at most it will take us KK iterations to find a valid element output. Hence, its time complexity is \mathcal{O}(K)O(K).

For the hasNext() function, its time complexity is \mathcal{O}(1)O(1).

Space Complexity:

For the next() function, we keep the references to all the input vectors in the variable self.vectors. As a result, we would need \mathcal{O}(K)O(K) space for KK vectors. In addition, we used some constant-space variables such as the pointers to the vector and the element. Hence, the overall space complexity for this function is \mathcal{O}(K)O(K).

Note: we did not copy the input vectors, but simply keep references to them.

Approach 2: Queue of Pointers
Intuition

The above approach is not the most efficient when the input vectors are not of equal size. For instance, for the input vectors of [1], [2, 3, 4, 5], we would waste some computing cycles to alternate the vector pointer, once we consume all the elements from the shorter vector. The problem exacerbates when the number of input vectors grows.

One idea to alleviate the above problem is to keep a queue of pointers to the input vectors as shown in the following graph.

queue of pointers

The queue functions in the following ways:

Initially, each input vector will have a corresponding pointer in the queue.

At each invocation of next() function, we pop out a pointer from the queue. With the pointer to the chosen vector, we further output an element from the vector.

If the vector still has some elements left, we append another pointer pointed to the vector at the end of the queue. In this way, we alternate the order of vectors.

If all the elements in the chosen vector are already outputted, we will NOT append another pointer. As a result, the vector would be out of the scope of the iteration. Later we won't waste any effort to iterate over the vectors that are exhausted.

As to the hasNext() function, as long as there are still some pointers left in the queue, we would still have more elements to output.

Algorithm

One could use the Iterator object (in Java or C++) as the pointer to the vector. Some of you might argue that we might be building a iterator with a built-in iterator. This has certain truth in it.

However, the key point here is that we could simply use some index and integer to implement the role of pointer in the above idea.

There are several advantages of using the queue of pointers, as one will see from the implementations later:

First of all, we would achieve a constant time complexity for the next() function.

The logics of implementation is much simplified and thus easy to read.


Complexity Analysis

Let KK be the number of input vectors, although it is always two in the setting of the problem. This variable becomes relevant, once the input becomes KK vectors.

Time Complexity: \mathcal{O}(1)O(1)

For both the next() function and the hasNext() function, we have a constant time complexity, as we discussed before.
Space Complexity: \mathcal{O}(K)O(K)

We use a queue to keep track of the pointers to the input vectors in the variable self.vectors. As a result, we would need \mathcal{O}(K)O(K) space for KK vectors.

Although the size of queue will reduce over time once we exhaust some shorter vectors, the space complexity for both functions is still \mathcal{O}(K)O(K).
     */
    public class ZigzagIterator1 {
        private List<List<Integer>> vectors = new ArrayList<>();
        // pointer to vector, and pointer to element
        private Integer pVec = 0, pElem = 0;
        private Integer totalNum = 0, outputCount = 0;

        public ZigzagIterator1(List<Integer> v1, List<Integer> v2) {
            this.vectors.add(v1);
            this.vectors.add(v2);
            for (List<Integer> vec : this.vectors) {
                this.totalNum += vec.size();
            }
        }

        public int next() {
            Integer iterNum = 0, ret = null;
            while (iterNum < this.vectors.size()) {
                List<Integer> currVec = this.vectors.get(this.pVec);
                if (this.pElem < currVec.size()) {
                    ret = currVec.get(this.pElem);
                    this.outputCount += 1;
                }

                iterNum += 1;
                this.pVec = (this.pVec + 1) % this.vectors.size();
                // increment the element pointer once iterating all vectors
                if (this.pVec == 0)
                    this.pElem += 1;

                if (ret != null)
                    return ret;
            }
            // one should raise an exception here.
            return 0;
        }

        public boolean hasNext() {
            return this.outputCount < this.totalNum;
        }
    }

/**
 * Your ZigzagIterator object will be instantiated and called as such: ZigzagIterator i = new
 * ZigzagIterator(v1, v2); while (i.hasNext()) v[f()] = i.next();
 */


/*
public class ZigzagIterator2 {
    private List<List<Integer>> vectors = new ArrayList<>();
    private LinkedList<Pair<Integer, Integer>> queue = new LinkedList<>();

    public ZigzagIterator2(List<Integer> v1, List<Integer> v2) {
        this.vectors.add(v1);
        this.vectors.add(v2);
        int index = 0;
        for (List<Integer> vec : this.vectors) {
            if (vec.size() > 0)
                // <index_to_vec, index_to_element_within_vec>
                this.queue.add(new Pair<Integer, Integer>(index, 0));
            index++;
        }
    }

    public int next() {
        // if (this.queue.size() == 0)
        // throw new Exception("Out of elements!");

        // <index_to_vec, index_to_element_within_vec>
        Pair<Integer, Integer> pointer = this.queue.removeFirst();
        Integer vecIndex = pointer.getKey();
        Integer elemIndex = pointer.getValue();
        Integer nextElemIndex = elemIndex + 1;
        // append the pointer for the next round
        // if there are some elements left.
        if (nextElemIndex < this.vectors.get(vecIndex).size())
            this.queue.addLast(new Pair<>(vecIndex, nextElemIndex));

        return this.vectors.get(vecIndex).get(elemIndex);
    }

    public boolean hasNext() {
        return this.queue.size() > 0;
    }
}


 */

}

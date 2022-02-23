package topinterviewquestions;

import java.util.*;

/*
Given the availability time slots arrays slots1 and slots2 of two people and a meeting duration duration, return the earliest time slot that works for both of them and is of duration duration.

If there is no common time slot that satisfies the requirements, return an empty array.

The format of a time slot is an array of two elements [start, end] representing an inclusive time range from start to end.

It is guaranteed that no two availability slots of the same person intersect with each other. That is, for any two time slots [start1, end1] and [start2, end2] of the same person, either start1 > end2 or start2 > end1.



Example 1:

Input: slots1 = [[10,50],[60,120],[140,210]], slots2 = [[0,15],[60,70]], duration = 8
Output: [60,68]
Example 2:

Input: slots1 = [[10,50],[60,120],[140,210]], slots2 = [[0,15],[60,70]], duration = 12
Output: []


Constraints:

1 <= slots1.length, slots2.length <= 104
slots1[i].length, slots2[i].length == 2
slots1[i][0] < slots1[i][1]
slots2[i][0] < slots2[i][1]
0 <= slots1[i][j], slots2[i][j] <= 109
1 <= duration <= 106
 */
public class Problem_1229_MeetingScheduler {
    /*
    Overview
To find the earliest time slot that works for both the people, the most straightforward approach would be to examine all possible time slots for them. To improve it, we can either sort both input arrays and apply two pointers, or we can use a heap for sorting the time slots and find the earliest overlapping time slot. We will cover both of these approaches in this article.


Approach 1: Two pointers
Intuition

The common slot between two slots.

Figure 1. The common slot between two slots.

We want to sort both slots1 and slots2 by the start time first, then initialize two pointers, each of which points to the beginning of the two arrays. From there, we will compare the two slots, and move one pointer at a time if the common slot is smaller than duration.

Note that sorting by the start time vs the end time is the same, this is because, if a time slot starts earlier, it will end earlier. Remember that for both people, there're no overlapping time slots

Here comes the question: how do we decide which pointer should be incremented?

The answer is: we will always move the one that ends earlier. Assuming that we are comparing slots1[i] and slots2[j] and slots1[i][1] > slots2[j][1], we would always choose to move the pointer j. The reason is that, as both slots are sorted, if slots1[i][1] > slots2[j][1], we know slots1[i+1][0] > slots2[j][1] so that there will be no intersection between slots1[i+1] and slots2[j].

Always move the one that ends earlier.

Figure 2. Always move the one that ends earlier.

Algorithm

Sort both slots1 and slots2 by the start time.
Initialize two pointers, pointer1 and pointer2, pointing to the beginning of slots1 and the beginning of slots2 respectively.
Iterate until pointer1 reaches the end of slots1 or pointer2 reaches the end of slots2:
Find the common slot of slots1[pointer1] and slots2[pointer2].
If the common slot is greater than or equal to duration, return the result.
Else, find the slot that ends earlier and move the pointer.
If no common slot is found, return an empty array.
Implementation


Complexity Analysis

Time complexity: O(M \log M + N \log N)O(MlogM+NlogN), when MM is the length of slots1 and NN is the length of slots2.

Sorting both arrays would take O(M \log M + N \log N)O(MlogM+NlogN). The two pointers take O(M + N)O(M+N) because, during each iteration, we would visit a new element, and there are a total of M+NM+N elements. Putting these together, the total time complexity is O(M \log M + N \log N)O(MlogM+NlogN).

Space complexity: O(1)O(1). This is because we do not use any additional data structures; we only use a few fixed-size integer variables.


Approach 2: Heap
Intuition

Another approach of systematically selecting slots and comparing them is using a heap. We would initialize a heap timeslots and push all of the time slots into it.

The key idea here is that we only need one heap. That is, we can put the time slots for both people into the same heap, and then if we find a common time slot, we know that the two-time slots couldn't possibly be for the same person. Before reading the justification for this, have a think for yourself about why we can draw such a bold conclusion.

The problem description states that the time slots for a single person do not overlap. This means that if, for example, we have the time slots [10, 15] and [14, 20], then we know that these time slots cannot be for the same person. Otherwise, we would have a contradiction. So, given that a common time slot has to overlap, the two slots have to be from different people.

A heap returns the smallest items first. Because of this time slots we remove from the heap are sorted by the start time. Taking advantage of this, we can then compare the time slots in the order of time.

Pop the first time slot out of the heap and compare it against the first element in the heap.

Figure 3. Compare the popped time slot and the top element in the heap.

Algorithm

Initialize a heap timeslots and push time slots that last longer than duration into it.
Iterate until there's only one time slot remaining in timeslots:
Pop the first time slot [start1, end1] from timeslots.
Retrieve the next time slot [start2, end2], which is the current top element in timeslots.
If we find end1 >= start2 + duration, because start1 <= start2, the common slot is longer than duration and we can return it.
If we don't find the common slot that is longer than duration, return an empty array.
Implementation


Complexity Analysis

Time complexity: O((M+N) \log (M+N))O((M+N)log(M+N)), when MM is the length of slots1 and NN is the length of slots2.

There are two parts to be analyzed: 1) building up the heap; 2) the iteration when we keep popping elements from the heap. For the second part, popping one element takes O(\log(M + N))O(log(M+N)), therefore, in the worst case, popping M + NM+N elements takes O((M+N) \log (M+N))O((M+N)log(M+N)).

Regarding the first part, we have different answers for Java and Python implementations. For Python, heapq.heapify transforms a list into a heap, in-place, in linear time; however, in Java, we choose to pop each element into the heap, which leads to a time complexity of O((M+N) \log (M+N))O((M+N)log(M+N)). Note that it is possible to convert the array into a heap in linear time using the constructor of PriorityQueue; however, that will not influence the overall time complexity and will make it less readable.

When we put these two parts together, the total time complexity is O((M+N) \log (M+N))O((M+N)log(M+N)), which is determined by the first part.

Space complexity: O(M+N)O(M+N). This is because we store all M+NM+N time slots in a heap.
     */

    class Solution1 {
        public List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
            Arrays.sort(slots1, (a, b) -> a[0] - b[0]);
            Arrays.sort(slots2, (a, b) -> a[0] - b[0]);

            int pointer1 = 0, pointer2 = 0;

            while (pointer1 < slots1.length && pointer2 < slots2.length) {
                // find the boundaries of the intersection, or the common slot
                int intersectLeft = Math.max(slots1[pointer1][0], slots2[pointer2][0]);
                int intersectRight = Math.min(slots1[pointer1][1], slots2[pointer2][1]);
                if (intersectRight - intersectLeft >= duration) {
                    return new ArrayList<Integer>(Arrays.asList(intersectLeft, intersectLeft + duration));
                }
                // always move the one that ends earlier
                if (slots1[pointer1][1] < slots2[pointer2][1]) {
                    pointer1++;
                } else {
                    pointer2++;
                }
            }
            return new ArrayList<Integer>();
        }
    }
    class Solution2 {
        public List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
            PriorityQueue<int[]> timeslots = new PriorityQueue<>((slot1, slot2) -> slot1[0] - slot2[0]);

            for (int[] slot: slots1) {
                if (slot[1] - slot[0] >= duration) timeslots.offer(slot);
            }
            for (int[] slot: slots2) {
                if (slot[1] - slot[0] >= duration) timeslots.offer(slot);
            }

            while (timeslots.size() > 1) {
                int[] slot1 = timeslots.poll();
                int[] slot2 = timeslots.peek();
                if (slot1[1] >= slot2[0] + duration) {
                    return new ArrayList<Integer>(Arrays.asList(slot2[0], slot2[0] + duration));
                }
            }
            return new ArrayList<Integer>();
        }
    }



    //diss
    //[Java/C++/Python] Two Pointer - Clean & Concise
    /*
    Algorithm

Sort both slots1 and slots2 by the start time.
Initialize two pointers, i and j, pointing to the beginning of slots1 and the beginning of slots2 respectively.
Iterate until i reaches the end of slots1 or j reaches the end of slots2:
Find the intersect slot between slots1[i] and slots2[j]
If the intersect slot >= duration, return the result.
Else, find the slot that ends earlier and move the pointer.
If no result is found, return an empty array.
Q & A

Why move the next pointer of slot with ends earlier?
-> Assume slots1[i][1] < slots2[j][1], we would always choose to move the pointer i by one. Because if slots1[i][1] < slots2[j][1] then slots1[i][1] < slots2[j+1][0] so slots1[i] never intersect with slots2[j+1].
Complexity

Time: O(MlogM + NlogN) for sorting
Space: O(sorting)
     */

    class Solution3 {
        public List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
            Arrays.sort(slots1, Comparator.comparingInt(a -> a[0])); // sort increasing by start time
            Arrays.sort(slots2, Comparator.comparingInt(a -> a[0])); // sort increasing by start time
            int i = 0, j = 0;
            int m = slots1.length, n = slots2.length;
            while (i < m && j < n) {
                // Find intersect between slots1[i] and slots2[j]
                int intersectStart = Math.max(slots1[i][0], slots2[j][0]);
                int intersectEnd = Math.min(slots1[i][1], slots2[j][1]);
                if (intersectEnd - intersectStart >= duration) // Found the result
                    return Arrays.asList(intersectStart, intersectStart + duration);

                if (slots1[i][1] < slots2[j][1])
                    i++;
                else
                    j++;
            }
            return new ArrayList<>();
        }
    }


}

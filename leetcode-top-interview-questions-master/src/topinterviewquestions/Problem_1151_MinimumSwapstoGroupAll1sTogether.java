package topinterviewquestions;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/*
Given a binary array data, return the minimum number of swaps required to group all 1’s present in the array together in any place in the array.



Example 1:

Input: data = [1,0,1,0,1]
Output: 1
Explanation: There are 3 ways to group all 1's together:
[1,1,1,0,0] using 1 swap.
[0,1,1,1,0] using 2 swaps.
[0,0,1,1,1] using 1 swap.
The minimum is 1.
Example 2:

Input: data = [0,0,0,1,0]
Output: 0
Explanation: Since there is only one 1 in the array, no swaps are needed.
Example 3:

Input: data = [1,0,1,0,1,0,0,1,1,0,1]
Output: 3
Explanation: One possible solution that uses 3 swaps is [0,0,0,0,0,1,1,1,1,1,1].


Constraints:

1 <= data.length <= 105
data[i] is either 0 or 1.
 */
public class Problem_1151_MinimumSwapstoGroupAll1sTogether {
    /*
    Overview
Assuming that there are ones 1's in the input array data, we need to find a subarray, or a window, of length ones and put all 1's in it by swapping 0's out. Therefore, among all windows of length ones, to find the minimum number of swaps required, we need to find the maximum number of 1's in the window so that we can make the smallest number of swaps to achieve the goal.

Swapping 0's and 1's

Figure 1. Find a subarray of length ones and swapping 0's with 1's.


Approach 1: Sliding Window with Two Pointers
Algorithm

We will use two pointers left and right to maintain a sliding window of length ones, and while we check every window through the input array data, we would calculate the number of 1's in it as cnt_one and store the largest one as max_one.

While the window is sliding through data, we want to maintain its length as ones. At the same time, we also want to update the number of 1's in the window by adding the new boundary data[right] and deducting the left boundary data[left].

Current
1 / 5

Complexity Analysis

Time complexity: \mathcal{O}(n)O(n), when nn is the length of the array.
Space complexity: \mathcal{O}(1)O(1).
Approach 2: Sliding Window with Deque (Double-ended Queue)
Algorithm

An alternative way to implement the sliding window algorithm is to use Deque (Double-ended Queue). The key idea is to maintain a deque deque with the size of ones by adding new elements to its right end and popping old elements from its left end.

More specifically speaking, after initializing deque, we would start appending elements to its right before its size reaches ones. When there are ones elements in deque, we keep appending new elements to its right, and we would also remove the leftmost element from it so that its size is always ones. Along this process, we can perform counting the number of 1's in this deque which is similar to Approach 1.


Complexity Analysis

Time complexity: \mathcal{O}(n)O(n), when nn is the length of the array. Note that the time complexities of adding the element to deque's right end and popping the element from its left end are both \mathcal{O}(1)O(1).
Space complexity: \mathcal{O}(n)O(n), when nn is the length of the array. This is because we need a Deque of size ones.
     */

    class Solution1 {
        public int minSwaps(int[] data) {
            int ones = Arrays.stream(data).sum();
            int cnt_one = 0, max_one = 0;
            int left = 0, right = 0;

            while (right < data.length) {
                // updating the number of 1's by adding the new element
                cnt_one += data[right++];
                // maintain the length of the window to ones
                if (right - left > ones) {
                    // updating the number of 1's by removing the oldest element
                    cnt_one -= data[left++];
                }
                // record the maximum number of 1's in the window
                max_one = Math.max(max_one, cnt_one);
            }
            return ones - max_one;
        }
    }


    class Solution2 {
        public int minSwaps(int[] data) {
            int ones = Arrays.stream(data).sum();
            int cnt_one = 0, max_one = 0;
            // maintain a deque with the size = ones
            Deque<Integer> deque = new ArrayDeque<>();

            for (int i = 0; i < data.length; i++) {

                // we would always add the new element into the deque
                deque.addLast(data[i]);
                cnt_one += data[i];

                // when there are more than ones elements in the deque,
                // remove the leftmost one
                if (deque.size() > ones) {
                    cnt_one -= deque.removeFirst();;
                }
                max_one = Math.max(max_one, cnt_one);
            }
            return ones - max_one;

        }
    }


    //diss
    //[Java]Sliding window O(n) with detailed explanation, very easy to understand
    //Thinking about it: the final result we want is a window with length of n (total number of the 1s)
    //Check all the window with the same length n, find the maximum one which already contains the most 1s.
    //All we need to do is to swap the rest: n-max.
    class Solution {
        public int minSwaps(int[] nums) {
            if(nums.length < 3) return 0;
            int n = 0;
            for(int num: nums){
                if(num == 1) n++; // total number of 1s
            }
            int i=0, j=0, c=0, max=0; //sliding window i to j
            while(j < nums.length) {
                while(j < nums.length && j - i < n){ //until i to j == n or search is done
                    if(nums[j++] == 1) c++;
                }
                max = Math.max(c, max); // max all the sliding window of which length equals to n
                if(j == nums.length) break;

                if(nums[i++] == 1) { //move i forward
                    c--;
                }
            }
            return n - max; //this is the minimun swaps
        }
    }


    //clean
    class Solution3 {
        public int minSwaps(int[] data) { //sliding window, we just need to find the min number of 0's in the window
            if (data == null || data.length < 3) return 0;
            int size = 0;
            for (int d : data) size += d;
            int left = 0, count = 0, res = data.length;
            for (int right = 0; right < data.length; right++) {
                if (right - left + 1 > size) count -= data[left++];
                count += data[right];
                if (right - left + 1 == size) res = Math.min(res, size - count);
            }
            return res;
        }
    }

    //clean java
    class Solution5 {
        public int minSwaps(int[] data) {
            if(data.length < 3) return 0;

            // count the total number of 1
            int oneCnt = 0;
            for(int num : data) oneCnt += num;

            int maxOneCnt = 0;
            int curOneCnt = 0;
            int i = 0;
            int j = 0;
            while(j < data.length){
                // check sliding window size
                // before adding j, i.e. if the existing number of elements is smaller than oneCnt
                if(j - i < oneCnt){
                    curOneCnt += data[j];
                }else{
                    // add data[j], remove data[i]
                    curOneCnt += data[j];
                    curOneCnt -= data[i];
                    i++;
                }

                // if the sliding window is full, update maxOneCnt
                if(j - i + 1 == oneCnt) maxOneCnt = Math.max(maxOneCnt, curOneCnt);
                j++;
            }

            return oneCnt - maxOneCnt;
        }
    }



}

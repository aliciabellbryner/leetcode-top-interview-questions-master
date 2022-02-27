package topinterviewquestions;
/*
You are given an integer array ribbons, where ribbons[i] represents the length of the ith ribbon, and an integer k. You may cut any of the ribbons into any number of segments of positive integer lengths, or perform no cuts at all.

For example, if you have a ribbon of length 4, you can:
Keep the ribbon of length 4,
Cut it into one ribbon of length 3 and one ribbon of length 1,
Cut it into two ribbons of length 2,
Cut it into one ribbon of length 2 and two ribbons of length 1, or
Cut it into four ribbons of length 1.
Your goal is to obtain k ribbons of all the same positive integer length. You are allowed to throw away any excess ribbon as a result of cutting.

Return the maximum possible positive integer length that you can obtain k ribbons of, or 0 if you cannot obtain k ribbons of the same length.



Example 1:

Input: ribbons = [9,7,5], k = 3
Output: 5
Explanation:
- Cut the first ribbon to two ribbons, one of length 5 and one of length 4.
- Cut the second ribbon to two ribbons, one of length 5 and one of length 2.
- Keep the third ribbon as it is.
Now you have 3 ribbons of length 5.
Example 2:

Input: ribbons = [7,5,9], k = 4
Output: 4
Explanation:
- Cut the first ribbon to two ribbons, one of length 4 and one of length 3.
- Cut the second ribbon to two ribbons, one of length 4 and one of length 1.
- Cut the third ribbon to three ribbons, two of length 4 and one of length 1.
Now you have 4 ribbons of length 4.
Example 3:

Input: ribbons = [5,7,9], k = 22
Output: 0
Explanation: You cannot obtain k ribbons of the same positive integer length.


Constraints:

1 <= ribbons.length <= 105
1 <= ribbons[i] <= 105
1 <= k <= 109
 */
public class Problem_1891_CuttingRibbons {
    /*
    Observation
First, let's forget the "maximum" part, if you are only asked to verify whether you can make at least k cut with length of x, you can do that in O(N).

    public boolean isCutPossible(int[] ribbons, int length, int k) {
        int count = 0;
        for (int ribbon: ribbons) {
            count += (ribbon / length);
        } // I could've written an early 'return' here to save some computation, but for me, the more "if", the more likely to bug
        return count >= k;
    }
Because we are asked to find the maximum valid answer, if you try to guess from 1 to Integer.MAX_VALUE, you will get this:
1:valid, 2: valid, 3: valid, ..., x: valid, x+1: invalid, x+2: invalid, x+3: invalid (x is the answer)
Now it's looks like a legit binary search: find the the last valid value, or like find the last 0 in an array like this: [0,0,0,0,0,1,1,1,1]

Binary Search
If until today you are still struggling to write a binary search, I suggest you read this article: https://medium.com/swlh/binary-search-find-upper-and-lower-bound-3f07867d81fb
For me, I thought understand binary search since my first year in college, but every time I was asked to write a binary search code, I could write it in just a minute but then I would spend 10 minutes debugging: this corner case and that corner case and infinite loop case.
Once I understand the concept of lower bound and upper bound, you will notice that in general, there are only two ways to code binary search: find lower bound or find upper bound.

Because I only memorize lower bound implementation of binary search codes, the implementation below is a lower bound binary search implementation.
Instead of finding the last valid answer (upper bound of valid answer), I try to find the first invalid answer (lower bound of invalid answer).
Then lastly, decrement by 1, obviously.

class Solution {
    public int maxLength(int[] ribbons, int k) {
        int l = 1;
        int r = (int) 1e5 + 1;
        while (l < r) {
            int mid = l + ((r - l) >> 1);

            if (!isCutPossible(ribbons, mid, k)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l - 1;
    }

}
The final running time is O(N log (max length)) with O(1) auxilary space.

     */

    /*
    Here is another way of doing binary search. We take the range end points as inclusive in this case. I prefer this approach since we have symmetry in solutions for finding first element greater than target or first element smaller than target

     */


    class Solution {//用二分法，找到正好可以
        public int maxLength(int[] ribbons, int k) {
            int max = Integer.MIN_VALUE;
            for(int len : ribbons)
                max = Math.max(max, len);

            int n = ribbons.length, low = 1, high = max, mid, count;
            while(low <= high) {
                mid = low + (high - low)/2;

                count = 0;
                for(int length : ribbons)
                    count += length/mid;

                if(count < k) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }

            return high > 0 ? high : 0;
        }
    }
}

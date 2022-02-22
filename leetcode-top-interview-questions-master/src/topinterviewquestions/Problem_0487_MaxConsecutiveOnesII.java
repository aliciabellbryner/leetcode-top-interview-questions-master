package topinterviewquestions;
/*
Given a binary array nums, return the maximum number of consecutive 1's in the array if you can flip at most one 0.



Example 1:

Input: nums = [1,0,1,1,0]
Output: 4
Explanation: Flip the first zero will get the maximum number of consecutive 1s. After flipping, the maximum number of consecutive 1s is 4.
Example 2:

Input: nums = [1,0,1,1,0,1]
Output: 4


Constraints:

1 <= nums.length <= 105
nums[i] is either 0 or 1.


Follow up: What if the input numbers come in one by one as an infinite stream? In other words, you can't store all numbers coming from the stream as it's too large to hold in memory. Could you solve it efficiently?
 */
public class Problem_0487_MaxConsecutiveOnesII {
    /*
    Intuition

First, let's understand our problem.

"Given a binary array, find the maximum number of consecutive 1s in this array..."

Okay makes sense so far.

"...if you can flip at most one 0."

Huh? What does that even mean?

Let's translate that into something more concrete. We can rephrase "if you can flip at most one 0" into "allowing at most one 0 within an otherwise consecutive run of 1s". These statements are equal because if we had one 0 in our consecutive array, we could flip it to satisfy our condition. Note, we're not actually going to flip the 0 which will make our approach simpler.

So our new problem statement is:

"Given a binary array, find the maximum number of consecutive 1s in this array, allowing at most one 0 within an otherwise consecutive run of 1s"

Approach 1: Brute Force
Algorithm

Let's start simple and work our way up.

A brute force solution usually involves trying to check every single possibility. It'll look something like this:

Check every possible consecutive sequence
Count how many 0's are in each sequence
If our sequence has one or fewer 0's, check if that's the longest consecutive sequence of 1's.

Interview Tip: Often times the interviewer doesn't need to see you code the brute force solution. State the brute force approach out loud and discuss his/her expectations. Either way, communicating proactively will give you major bonus points.

Complexity Analysis

Let nn be equal to the length of the input nums array.

Time complexity : O(n^2)O(n
2
 ). The nested for loops turn our approach into a quadratic solution because for every index, we have to check every other index in the array.

Space complexity : O(1)O(1). We are using 4 variables: left, right, numZeroes, longestSequence. The number of variables are constant and do not change based on the size of the input.


Approach 2: Sliding Window
Intuition

The naive approach works but our interviewer is not convinced. Let's see how we can optimize the code we just wrote.

The brute force solution had a time complexity of O(n^2)O(n
2
 ). What was the bottleneck? Checking every single consecutive sequence. Intuitively, we know we're doing repeated work because sequences overlap. We are checking consecutive sequence blindly. We need to establish some rules on how to move our sequence forward.

If our sequence is valid, let's continue expanding our sequence (because our goal is to get the largest sequence possible).
If our sequence is invalid, let's stop expanding and contract our sequence (because an invalid sequence will never count towards our largest sequence).
The pattern that comes to mind for expanding/contracting sequences is the sliding window. Let's define valid and invalid states.

Valid State = one or fewer 0's in our current sequence
Invalid State = two 0's in our current sequence
Algorithm

Great. How do we apply all this to the sliding window?

Let's use left and right pointers to keep track of the current sequence a.k.a. our window. Let's expand our window by moving the right pointer forward until we reach a point where we have more than one 0 in our window. When we reach this invalid state, let's contract our window by moving the left pointer forward until we have a valid window again. By expanding and contracting our window from valid and invalid states, we are able to traverse the array efficiently without repeated overlapping work.

Now we can break this approach down into a few actionable steps:

While our window is in bounds of the array...

Add the rightmost element to our window
Check if our window is invalid. If so, contract the window until valid.
Update our the longest sequence we've seen so far
Continue to expand our window
This will look like this:

Current
1 / 10

Complexity Analysis

Let nn be equal to the length of the input nums array.

Time complexity : O(n)O(n). Since both the pointers only move forward, each of the left and right pointer traverse a maximum of n steps. Therefore, the timecomplexity is O(n)O(n).

Space complexity : O(1)O(1). Same as the previous approach. We don't store anything other than variables. Thus, the space we use is constant because it is not correlated to the length of the input array.
     */

    class Solution2 {
        public int findMaxConsecutiveOnes(int[] nums) {
            int longestSequence = 0;
            int left = 0;
            int right = 0;
            int numZeroes = 0;

            // while our window is in bounds
            while (right < nums.length) {

                // add the right most element into our window
                if (nums[right] == 0) {
                    numZeroes++;
                }

                // if our window is invalid, contract our window
                while (numZeroes == 2) {
                    if (nums[left] == 0) {
                        numZeroes--;
                    }
                    left++;
                }

                // update our longest sequence answer
                longestSequence = Math.max(longestSequence, right - left + 1);

                // expand our window
                right++;
            }
            return longestSequence;
        }
    }


    //discussion
    /*
    The idea is to keep a window [l, h] that contains at most k zero

The following solution does not handle follow-up, because nums[l] will need to access previous input stream
Time: O(n) Space: O(1)

    public int findMaxConsecutiveOnes(int[] nums) {
        int max = 0, zero = 0, k = 1; // flip at most k zero
        for (int l = 0, h = 0; h < nums.length; h++) {
            if (nums[h] == 0)
                zero++;
            while (zero > k)
                if (nums[l++] == 0)
                    zero--;
            max = Math.max(max, h - l + 1);
        }
        return max;
    }
Now let's deal with follow-up, we need to store up to k indexes of zero within the window [l, h] so that we know where to move l next when the window contains more than k zero. If the input stream is infinite, then the output could be extremely large because there could be super long consecutive ones. In that case we can use BigInteger for all indexes. For simplicity, here we will use int
Time: O(n) Space: O(k)

    public int findMaxConsecutiveOnes(int[] nums) {
        int max = 0, k = 1; // flip at most k zero
        Queue<Integer> zeroIndex = new LinkedList<>();
        for (int l = 0, h = 0; h < nums.length; h++) {
            if (nums[h] == 0)
                zeroIndex.offer(h);
            if (zeroIndex.size() > k)
                l = zeroIndex.poll() + 1;
            max = Math.max(max, h - l + 1);
        }
        return max;
    }
Note that setting k = 0 will give a solution to the earlier version Max Consecutive Ones

For k = 1 we can apply the same idea to simplify the solution. Here q stores the index of zero within the window [l, h] so its role is similar to Queue in the above solution

    public int findMaxConsecutiveOnes(int[] nums) {
        int max = 0, q = -1;
        for (int l = 0, h = 0; h < nums.length; h++) {
            if (nums[h] == 0) {
                l = q + 1;
                q = h;
            }
            max = Math.max(max, h - l + 1);
        }
        return max;
    }
     */


    //5455
    //ReputationIterate from left to right, keeping track of the length of the latest segment of ones, including the one to the left of the last zero seen and the one to the right of the last zero seen.
    public int findMaxConsecutiveOnes(int[] nums) {
        int maxConsecutive = 0, zeroLeft = 0, zeroRight = 0;
        for (int i=0;i<nums.length;i++) {
            zeroRight++;
            if (nums[i] == 0) {
                zeroLeft = zeroRight;
                zeroRight = 0;
            }
            maxConsecutive = Math.max(maxConsecutive, zeroLeft+zeroRight);
        }
        return maxConsecutive;
    }
}

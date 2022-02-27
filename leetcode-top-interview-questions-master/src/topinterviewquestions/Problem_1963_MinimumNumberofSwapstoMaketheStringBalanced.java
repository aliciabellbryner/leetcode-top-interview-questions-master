package topinterviewquestions;

import java.util.Stack;

/*
You are given a 0-indexed string s of even length n. The string consists of exactly n / 2 opening brackets '[' and n / 2 closing brackets ']'.

A string is called balanced if and only if:

It is the empty string, or
It can be written as AB, where both A and B are balanced strings, or
It can be written as [C], where C is a balanced string.
You may swap the brackets at any two indices any number of times.

Return the minimum number of swaps to make s balanced.



Example 1:

Input: s = "][]["
Output: 1
Explanation: You can make the string balanced by swapping index 0 with index 3.
The resulting string is "[[]]".
Example 2:

Input: s = "]]][[["
Output: 2
Explanation: You can do the following to make the string balanced:
- Swap index 0 with index 4. s = "[]][][".
- Swap index 1 with index 5. s = "[[][]]".
The resulting string is "[[][]]".
Example 3:

Input: s = "[]"
Output: 0
Explanation: The string is already balanced.


Constraints:

n == s.length
2 <= n <= 106
n is even.
s[i] is either '[' or ']'.
The number of opening brackets '[' equals n / 2, and the number of closing brackets ']' equals n / 2.
 */
public class Problem_1963_MinimumNumberofSwapstoMaketheStringBalanced {
    //思路是要求需要的swap次数，我们可以先把所有符合的[]不考虑，只考虑不match的，所以只剩]]]]]...[[[[[[...
    //那么其实可以知道如果][有1对，需要swap一次，如果][有2对，需要swap一次，如果][有3对，需要swap2次。。。规律就是有n对][，则需要swap的次数是（n+1)/2
    //下面solution中的stack_size就可以求出不平衡的括号对有多少个
    //using a int stack_size, better space complexity
    //Update: As suggested by quite a few people in the comments, here is the Space Optimized Approach which removes the need of the stack. We will simply use a variable stack_size to keep track of mismatches. If the current character is a '[', then we increment stack_size, else, we decrement it only if it is greater than 0 (meaning we have seen some valid [ brackets before, so this ] bracket is going to balance them out). At the end, stack_size will contain the count of brackets which are not balanced, i.e [[[[[..... Code below:
    class Solution {
        public int minSwaps (String s) {
            int stack_size = 0;
            for (int i = 0; i < s.length (); i++) {
                char ch = s.charAt (i);
                if (ch == '[') {
                    stack_size++;
                } else {
                    if (stack_size > 0) {
                        stack_size--;
                    }
                }
            }
            return (stack_size + 1) / 2;
        }
    }

    //using a stack
    //https://leetcode.com/problems/minimum-number-of-swaps-to-make-the-string-balanced/discuss/1390319/How-to-reach-the-optimal-solution-or-Explained-with-Intuition-and-Diagram
    //Intuition:
    //
    //We can discard the balanced components from our input string as they will not matter in balancing the string. Now, after excluding them, we will find that all the unbalanced brackets look like this:
    //
    //]]]]]…..[[[[[…..
    //
    //Now, since we can swap any pair of brackets, the optimal approach is to balance 2 sets of brackets at a time using 1 swap (hinting at result = total unbalanced / 2, which we will handle for even and odd lengths together). Take this for example:
    //
    //image
    //
    //So, following this, we will notice a pattern (try out the same with different number of mismatches to see the pattern):
    //
    //image
    //
    //So, our result (min. swaps) = (mismatches + 1) / 2
    //
    //The key here is to start balancing from the middle.

    class Solution2 {
        public int minSwaps (String s) {
            Stack<Character> stack = new Stack ();
            int mismatch = 0;
            for (int i = 0; i < s.length (); i++) {
                char ch = s.charAt (i);
                if (ch == '[')
                    stack.push (ch);
                else {
                    if (!stack.isEmpty()) {
                        stack.pop();
                    } else {
                        mismatch++;
                    }
                }
            }
            return (mismatch + 1) / 2;
        }
    }
}

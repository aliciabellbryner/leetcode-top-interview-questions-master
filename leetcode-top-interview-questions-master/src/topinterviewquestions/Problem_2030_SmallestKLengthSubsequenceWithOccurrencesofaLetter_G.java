package topinterviewquestions;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class Problem_2030_SmallestKLengthSubsequenceWithOccurrencesofaLetter_G {

    //JAVA Solution with Monotonic Stack
    //https://leetcode.com/problems/smallest-k-length-subsequence-with-occurrences-of-a-letter/discuss/1714332/JAVA-Solution-with-Monotonic-Stack
    class Solution {
        public String smallestSubsequence(String s, int k, char letter, int repetition) {
            int rem = 0;
            for(char c: s.toCharArray()) {
                if(c==letter) {
                    rem++;
                }
            }

            Stack<Integer> stack  = new Stack<>();
            int cnt = 0;
            int len = s.length();
            int taken = 0;
            for(int i = 0; i < len; i++) {
                char c  = s.charAt(i);
                while(!stack.isEmpty() && c < s.charAt(stack.peek())) {
                    char top = s.charAt(stack.peek());
                    if((len - i + taken - 1 >= k) && (top != letter || (top==letter && rem + cnt - 1 >= repetition))) {
                        taken--;
                        stack.pop();
                        cnt -= (top==letter ? 1 : 0);
                    } else {
                        break;
                    }
                }

                if(taken < k && (c==letter || (c!=letter && k - taken > repetition - cnt))) {
                    stack.push(i);
                    taken++;
                    cnt += (c==letter ? 1 : 0);
                }
                rem -= (c==letter ? 1 : 0);
            }

            StringBuilder sb = new StringBuilder();
            while(!stack.isEmpty()) {
                sb.append(s.charAt(stack.pop()));
            }

            return sb.reverse().toString();
        }
    }


    //[JAVA] Monotonic Stack Solution https://leetcode.com/problems/smallest-k-length-subsequence-with-occurrences-of-a-letter/discuss/1505481/JAVA-Monotonic-Stack-Solution
    //The idea is keep a monotonously increasing stack, but make sure follow these constrains.
    //
    //There are have enough space left for repetition - countletters. (count is the number of letter inside the stack)
    //There are have enough letters left to compose k letters.
    //There are at least repetition letters left.
    class Solution1 {
        public String smallestSubsequence(String s, int k, char letter, int repetition) {
            Deque<Character> stack = new ArrayDeque<>();
            int count = 0; // count how many `letter` in the stack
            int left = s.length();
            int letterCount = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == letter) letterCount++;
            }

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                //Make sure ther are still have enough space left for `repetition` letters
                while (!stack.isEmpty() && (c < stack.peek() || k - stack.size() < repetition - count)) {
                    // Make sure there are still have enough letters left to compose `k` letters.
                    if (left + stack.size() <= k) break;
                    // Make sure there are at least `repetition` letters left
                    if (stack.peek() == letter && letterCount <= repetition) break;

                    if (stack.pop() == letter) {
                        count--;
                        letterCount--;
                    }
                }
                stack.push(c);
                if (c == letter) count++;
                left--;
            }

            while (stack.size() > k) stack.pop();

            StringBuilder builder = new StringBuilder();
            while (!stack.isEmpty()) {
                builder.append(stack.pop());
            }
            return builder.reverse().toString();
        }
    }


    //https://leetcode.com/problems/smallest-k-length-subsequence-with-occurrences-of-a-letter/discuss/1500174/PythonJava-O(N)-greedy-solution-using-a-stack
    //O(N) greedy solution using a stack
    class Solution2 {
        public String smallestSubsequence(String s, int k, char letter, int r) {
            int n_letters = 0;
            for (int i = 0; i < s.length(); i++)
                if (s.charAt(i) == letter)
                    n_letters ++;

            Stack<Character> stack = new Stack<>();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                while (!stack.isEmpty() && stack.peek() > c && (s.length() - i + stack.size() > k) && (stack.peek() != letter || n_letters > r)) {
                    if (stack.pop() == letter) {
                        r++;
                    }
                }

                if (stack.size() < k) {
                    if (c == letter) {
                        stack.push(c);
                        r --;
                    } else if (k - stack.size() > r) {
                        stack.push(c);
                    }
                }

                if (c == letter) {
                    n_letters--;
                }
            }

            StringBuilder sb = new StringBuilder(stack.size());
            for(Character c : stack) {
                sb.append(c);
            }
            return sb.toString();
        }
    }
}

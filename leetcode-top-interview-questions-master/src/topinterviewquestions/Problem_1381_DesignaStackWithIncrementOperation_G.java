package topinterviewquestions;

import java.util.Stack;

public class Problem_1381_DesignaStackWithIncrementOperation_G {
    // Lazy increment, O(1) https://leetcode.com/problems/design-a-stack-with-increment-operation/discuss/539716/JavaC%2B%2BPython-Lazy-increment-O(1)
    //Explanation
    //Use an additional array to record the increment value.
    //inc[i] means for all elements stack[0] ~ stack[i],
    //we should plus inc[i] when popped from the stack.
    //Then inc[i-1]+=inc[i],
    //so that we can accumulate the increment inc[i]
    //for the bottom elements and the following pops.
    //
    //
    //Complexity
    //C++/Python, initialization is O(1) time & space.
    //Java, initialization is O(N) time & space.
    //(We cam use ArrayList, but shrug)
    //push, pop, increment, all O(1) time and space.
    class CustomStack {
        int n;
        int[] inc;
        Stack<Integer> stack;
        public CustomStack(int maxSize) {
            n = maxSize;
            inc = new int[n];
            stack = new Stack<>();
        }

        public void push(int x) {
            if (stack.size() < n)
                stack.push(x);
        }

        public int pop() {
            int i = stack.size() - 1;
            if (i < 0)
                return -1;
            if (i > 0)
                inc[i - 1] += inc[i];
            int res = stack.pop() + inc[i];
            inc[i] = 0;
            return res;
        }

        public void increment(int k, int val) {
            int i = Math.min(k, stack.size()) - 1;
            if (i >= 0)
                inc[i] += val;
        }
    }
}

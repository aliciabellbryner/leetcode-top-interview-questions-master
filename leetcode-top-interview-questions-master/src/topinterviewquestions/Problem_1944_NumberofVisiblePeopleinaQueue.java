package topinterviewquestions;

import java.util.Stack;

public class Problem_1944_NumberofVisiblePeopleinaQueue {
    //跟503. Next Greater Element II非常类似
    //利用单调栈
    public int[] canSeePersonsCount(int[] A) {
        int n = A.length, res[] = new int[n];
        Stack<Integer> stack = new Stack();
        for (int i = 0; i < n; ++i) {
            while (!stack.isEmpty() && A[stack.peek()] <= A[i]) {
                res[stack.pop()]++;
            }
            if (!stack.isEmpty()) {
                res[stack.peek()]++;
            }
            stack.add(i);
        }
        return res;
    }
}

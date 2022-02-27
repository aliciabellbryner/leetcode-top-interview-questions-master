package topinterviewquestions;

import java.util.Arrays;
import java.util.Stack;

public class Problem_0503_NextGreaterElementII_G {
    //Explanation
    //Loop once, we can get the Next Greater Number of a normal array.
    //Loop twice, we can get the Next Greater Number of a circular array
    //
    //
    //Complexity
    //Time O(N) for one pass
    //Spce O(N) in worst case
    public int[] nextGreaterElements(int[] A) {
        int n = A.length, res[] = new int[n];
        Arrays.fill(res, -1);
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n * 2; i++) {//循环两次，n*2,这样才可以找到最后一个值下一个更大值是多少
            while (!stack.isEmpty() && A[stack.peek()] < A[i % n]) {//当发现遍历到A[i % n]时他的值比stack头部对应的值大的时候，
                // 说明stack头部对应的值找到了右边比他大的值，所以就可以输出
                res[stack.pop()] = A[i % n];
            }
            stack.push(i % n);
        }
        return res;
    }
}

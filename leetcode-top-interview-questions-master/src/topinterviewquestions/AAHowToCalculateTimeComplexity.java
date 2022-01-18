package topinterviewquestions;

public class AAHowToCalculateTimeComplexity {
    /**
     * 形如 T(N) = a * T(n/b) + O(N^d) (a, b, d都是常数）的递归函数，可以直接通过Master公式来确定时间复杂度
     * 1. 如果log(b, a) < d, 则复杂度为O(N^d)
     * 2. 如果log(b, a) > d, 则复杂度为O(N^log(b,a))
     * 3. 如果log(b, a) == d, 则复杂度为O(N^d*logN)
     */
}

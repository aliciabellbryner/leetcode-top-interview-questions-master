package topinterviewquestions;

public class Problem_0231_PowerofTwo_G {
    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & n - 1) == 0;
    }
}

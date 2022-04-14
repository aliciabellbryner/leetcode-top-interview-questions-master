package topinterviewquestions;

public class Problem_0338_CountingBits_G {
    //思路就是每个f【i】的值就等于是把i右移一位加上最后一位1的个数
    public int[] countBits(int num) {
        int[] f = new int[num + 1];
        for (int i=1; i<=num; i++) {
            f[i] = f[i >> 1] + (i & 1);
        }
        return f;
    }
}

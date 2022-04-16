package topinterviewquestions;

public class Problem_0660_Remove9_G {
    //因为删除的是最后一位9，所以实际上就是10进制转9进制的算法
    public int newInteger(int n) {
        int ans = 0;
        int base = 1;

        while (n > 0){
            ans += n % 9 * base;
            n /= 9;
            base *= 10;
        }
        return ans;
    }
}

package topinterviewquestions;

public class Problem_0233_NumberofDigitOne_G {
    //https://leetcode.com/problems/number-of-digit-one/discuss/64381/4+-lines-O(log-n)-C++JavaPython/65956
    // Basic idea: count number of combination of 1 at each digit in two cases: prefix appears or not
    // Eg.3101592:
    // 1) one at hundreds:      1 (< 5): [1~3101]1[0~99]. So # = 3101 * 100 + 100 (Safe since 31011XX never be greater than 31015XX)
    // 2) one at thousands:     1 (= 1): [1~310]1[0~592]. So # = 310 * 1000 + (592 + 1) (Unsafe if prefix is 0, it must be <= 1592)
    // 3) one at ten thousands: 1 (> 0): [1~30]1[0~9999]. So # = 30 * 10000 (If prefix is 0, no 1 digit number exist)
    public int countDigitOne(int n) {
        if (n <= 0) return 0;
        long ones = 0;
        for (long i = 1, q = n; i <= n; i *= 10, q /= 10) {
            long pre = n / (i * 10), cur = q % 10, suf = n % i;
            ones += pre * i;
            ones += (1 < cur ? i : (1 == cur ? suf + 1: 0));
        }
        return (int) ones;
    }
}

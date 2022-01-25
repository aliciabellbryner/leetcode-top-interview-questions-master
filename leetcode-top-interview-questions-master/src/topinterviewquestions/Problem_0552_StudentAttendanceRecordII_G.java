package topinterviewquestions;

public class Problem_0552_StudentAttendanceRecordII_G {
    //https://leetcode.com/problems/student-attendance-record-ii/solution/
    //Approach #3 Using Dynamic Programming [Accepted]
    //Time complexity : O(n). One loop to fill f array and one to calculate sum
    //Space complexity : O(n). f array of size n is used.
    public class Solution {
        long M = 1000000007;
        public int checkRecord(int n) {
            long[] f = new long[n <= 5 ? 6 : n + 1];
            f[0] = 1;
            f[1] = 2;
            f[2] = 4;
            f[3] = 7;
            for (int i = 4; i <= n; i++)
                f[i] = ((2 * f[i - 1]) % M + (M - f[i - 4])) % M;
            long sum = f[n];
            for (int i = 1; i <= n; i++) {
                sum += (f[i - 1] * f[n - i]) % M;
            }
            return (int)(sum % M);
        }
    }

    //solution2: https://leetcode.com/problems/student-attendance-record-ii/discuss/101638/Simple-Java-O(n)-solution
    static final int M = 1000000007;

    public int checkRecord(int n) {
        long[] PorL = new long[n + 1]; // ending with P or L, no A
        long[] P = new long[n + 1]; // ending with P, no A
        PorL[0] = P[0] = 1; PorL[1] = 2; P[1] = 1;

        for (int i = 2; i <= n; i++) {
            P[i] = PorL[i - 1];
            PorL[i] = (P[i] + P[i - 1] + P[i - 2]) % M;
        }

        long res = PorL[n];
        for (int i = 0; i < n; i++) { // inserting A into (n-1)-length strings
            long s = (PorL[i] * PorL[n - i - 1]) % M;
            res = (res + s) % M;
        }

        return (int) res;
    }

}

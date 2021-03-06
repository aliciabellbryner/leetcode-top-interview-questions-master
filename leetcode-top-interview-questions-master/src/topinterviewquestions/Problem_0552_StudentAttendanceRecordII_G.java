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


    //Approach #1 Brute Force [Time Limit Exceeded]
    //Time complexity : O(3^n)Exploring 3^n combinations.
    //Space complexity : O(n^n), Recursion tree can grow upto depth nn and each node contains string of length O(n)O(n).
    public class Solution1 {
        int count, M=1000000007;
        public int checkRecord(int n) {
            count = 0;
            process("", n);
            return count;
        }
        public void process(String s, int n) {//????????????????????????string
            if (n == 0 && isValid(s)) {
                count = (count + 1) % M;
            }
            else if (n > 0) {
                process(s + "A", n - 1);
                process(s + "P", n - 1);
                process(s + "L", n - 1);
            }
        }
        public boolean isValid(String s) {
            int count = 0;
            for (int i = 0; i < s.length() && count < 2; i++)
                if (s.charAt(i) == 'A')
                    count++;
            return s.length() > 0 && count < 2 && s.indexOf("LLL") < 0;
        }
    }

    //Approach #2 Using Recursive formulae [Time Limit Exceeded]
    //Space complexity : O(n). ff array is used of size nn.
    public class Solution2 {
        int M=1000000007;
        public int checkRecord(int n) {
            int[] f =new int[n+1];
            f[0]=1;
            for(int i=1;i<=n;i++)
                f[i]=func(i);
            int sum=func(n);
            for(int i=1;i<=n;i++){
                sum+=(f[i-1]*f[n-i])%M;
            }
            return sum%M;
        }
        public int func(int n)
        {
            if(n==0)
                return 1;
            if(n==1)
                return 2;
            if(n==2)
                return 4;
            if(n==3)
                return 7;
            return (2*func(n-1) - func(n-4))%M;
        }
    }

}

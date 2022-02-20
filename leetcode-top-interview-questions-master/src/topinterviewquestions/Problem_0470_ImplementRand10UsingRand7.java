package topinterviewquestions;

public class Problem_0470_ImplementRand10UsingRand7 {
    //https://leetcode.com/problems/implement-rand10-using-rand7/solution/
    //Approach 1: Rejection Sampling
    //Time Complexity: O(1) average, but O(∞) worst case.
    //Space Complexity: O(1).
/*
    class Solution extends SolBase {
        public int rand10() {
            int row, col, idx;
            do {
                row = rand7();
                col = rand7();
                idx = col + (row - 1) * 7;
            } while (idx > 40);
            return 1 + (idx - 1) % 10;
        }
    }
*/



    //Approach 2: Utilizing out-of-range samples
    //Time Complexity: O(1) average, but O(∞) worst case.
    //Space Complexity: O(1).
    /*
    class Solution extends SolBase {
    public int rand10() {
        int a, b, idx;
        while (true) {
            a = rand7();
            b = rand7();
            idx = b + (a - 1) * 7;
            if (idx <= 40)
                return 1 + (idx - 1) % 10;
            a = idx - 40;
            b = rand7();
            // get uniform dist from 1 - 63
            idx = b + (a - 1) * 7;
            if (idx <= 60)
                return 1 + (idx - 1) % 10;
            a = idx - 60;
            b = rand7();
            // get uniform dist from 1 - 21
            idx = b + (a - 1) * 7;
            if (idx <= 20)
                return 1 + (idx - 1) % 10;
        }
    }
}
     */
}

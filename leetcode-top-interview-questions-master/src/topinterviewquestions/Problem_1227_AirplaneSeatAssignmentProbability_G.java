package topinterviewquestions;

public class Problem_1227_AirplaneSeatAssignmentProbability_G {
    //https://leetcode-cn.com/problems/airplane-seat-assignment-probability/solution/fei-ji-zuo-wei-fen-pei-gai-lu-by-leetcod-gyw4/
    class Solution {
        public double nthPersonGetsNthSeat(int n) {
            return n == 1 ? 1.0 : 0.5;
        }
    }

}

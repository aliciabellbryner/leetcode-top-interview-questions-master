package topinterviewquestions;

public class Problem_0849_MaximizeDistancetoClosestPerson_G {
    //Approach #2: Two Pointer [Accepted]
    //https://leetcode.com/problems/maximize-distance-to-closest-person/solution/
    //Time Complexity: O(N), where N is the length of seats.
    //
    //Space Complexity: O(1).
    class Solution {
        public int maxDistToClosest(int[] seats) {
            int N = seats.length;
            int prev = -1, future = 0;
            int ans = 0;

            for (int i = 0; i < N; ++i) {
                if (seats[i] == 1) {
                    prev = i;
                } else {
                    while (future < N && seats[future] == 0 || future < i)
                        future++;

                    int left = prev == -1 ? N : i - prev;
                    int right = future == N ? N : future - i;
                    ans = Math.max(ans, Math.min(left, right));
                }
            }

            return ans;
        }
    }
}

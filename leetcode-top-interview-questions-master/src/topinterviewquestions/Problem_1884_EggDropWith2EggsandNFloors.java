package topinterviewquestions;
/*
You are given two identical eggs and you have access to a building with n floors labeled from 1 to n.

You know that there exists a floor f where 0 <= f <= n such that any egg dropped at a floor higher than f will break, and any egg dropped at or below floor f will not break.

In each move, you may take an unbroken egg and drop it from any floor x (where 1 <= x <= n). If the egg breaks, you can no longer use it. However, if the egg does not break, you may reuse it in future moves.

Return the minimum number of moves that you need to determine with certainty what the value of f is.



Example 1:

Input: n = 2
Output: 2
Explanation: We can drop the first egg from floor 1 and the second egg from floor 2.
If the first egg breaks, we know that f = 0.
If the second egg breaks but the first egg didn't, we know that f = 1.
Otherwise, if both eggs survive, we know that f = 2.
Example 2:

Input: n = 100
Output: 14
Explanation: One optimal strategy is:
- Drop the 1st egg at floor 9. If it breaks, we know f is between 0 and 8. Drop the 2nd egg starting from floor 1 and going up one at a time to find f within 8 more drops. Total drops is 1 + 8 = 9.
- If the 1st egg does not break, drop the 1st egg again at floor 22. If it breaks, we know f is between 9 and 21. Drop the 2nd egg starting from floor 10 and going up one at a time to find f within 12 more drops. Total drops is 2 + 12 = 14.
- If the 1st egg does not break again, follow a similar process dropping the 1st egg from floors 34, 45, 55, 64, 72, 79, 85, 90, 94, 97, 99, and 100.
Regardless of the outcome, it takes at most 14 drops to determine f.


Constraints:

1 <= n <= 1000
 */
public class Problem_1884_EggDropWith2EggsandNFloors {

    //https://leetcode.com/problems/egg-drop-with-2-eggs-and-n-floors/discuss/1246123/Java-dp-solution-for-n-floors-and-x-eggs-(if-you-could-not-come-up-with-the-math-solution)/963293
    //思路就是最后一步就只有一层，往后推是1，2，3，4，。。。mid，那和就是(1+mid)*mid>>1
    public int twoEggDrop(int n) {
        int left=1;
        int right=n;
        while(left<=right){
            int mid=(left+right)>>>1;
            int candidate=(1+mid)*mid>>1;
            if(candidate<n)left=mid+1;
            else right=mid-1;
        }
        return left;
    }

    //diss https://leetcode.com/problems/egg-drop-with-2-eggs-and-n-floors/discuss/1246123/Java-dp-solution-for-n-floors-and-x-eggs-(if-you-could-not-come-up-with-the-math-solution)
    //This is a classic problem. You can read about it or watch it on youtube. Just google egg dropping problem. The solution comes down to solving x + (x - 1) + (x - 2) + ... + 2 + 1 >= n equation (when you have two eggs)
    public int twoEggDrop1(int n) {
        return (int) Math.ceil((Math.sqrt(1 + 8 * n) - 1) / 2);
    }

    //You can also solve this problem using recursion. Let's consider a more general problem when we have x eggs and n floors. If you drop an egg from i floor (1<=i<=n), then
    //
    //If the egg breaks, the problem is reduced to x-1 eggs and i - 1 floors
    //If the eggs does not break, the problem is reduced to x eggs and n-i floors
    class Solution2{
        public int twoEggDrop(int n) {
            int eggs = 2;
            return drop(n, eggs, new int[n + 1][eggs + 1]);
        }

        int drop(int floors, int eggs, int[][] dp) {
            if (eggs == 1 || floors <= 1)
                return floors;
            if (dp[floors][eggs] > 0)
                return dp[floors][eggs];
            int min = Integer.MAX_VALUE;
            for (int f = 1; f <= floors; f++)
                min = Math.min(min, 1 + Math.max(drop(f - 1, eggs - 1, dp), drop(floors - f, eggs, dp)));
            dp[floors][eggs] = min;
            return min;
        }
    }


    //https://leetcode.com/problems/egg-drop-with-2-eggs-and-n-floors/discuss/1248069/Recursive-Iterative-Generic
    //Simple Recursion
    //In the solution below, we drop an egg from each floor and find the number of throws for these two cases:
    //
    //We lost an egg but we reduced the number of floors to i.
    //Since we only have one egg left, we can just return i - 1 to check all floors.
    //The egg did not break, and we reduced the number of floors to n - i.
    //Solve this recursively to get the number of throws for n - i floors.
    class Solution {
        static int[] dp = new int[1001];
        public int twoEggDrop(int n) {
            if (dp[n] == 0) {
                for (int i = 1; i <= n; ++i) {
                    dp[n] = Math.min(dp[n] == 0 ? n : dp[n], 1 + Math.max(i - 1, twoEggDrop(n - i)));
                }
            }
            return dp[n];
        }
    }


}

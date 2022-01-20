package topinterviewquestions;

public class Problem_0174_DungeonGame {
    //https://leetcode.com/problems/dungeon-game/discuss/52790/My-AC-Java-Version-Suggestions-are-welcome/239566
    //time complexity: O(m*n)
    //space complexity: O(m*n)

    //More concise with some explanation:
    //We cannot determines the health without knowing how much health loss is waiting for us in the future. Thus we need to consider from the opposite way. i.e. from destination to start position.
    //
    //First we need to know what's the min health we need at the princess location?
    //The key is assuming we have M[m - 1][n - 1] health when we reach dungeon[m - 1][n - 1] before fighting, then
    //1.1 we must have at least 1 blood, i.e. M[m - 1][n - 1] >= 1
    //1.2 after we fight with demons, we need to have at least 1 blood to be alive, i.e. M[m - 1][n - 1] + dungeon[m - 1][n - 1] >= 1
    //With above, we have: M[m - 1][n - 1] >= 1 and M[m - 1][n - 1] >= 1 - dungeon[i][j], thus M[m - 1][n - 1] >= max(1, 1 - dungeon[m - 1][n - 1])
    //Then what about the previous min health we should have, let's denote cur min health as cur?
    //2.1 we must have at least 1 blood, i.e. prev >= 1
    //2.2 after we fight with demons, we have cur health, i.e. prev + dungeon[prev] = cur
    //From above, we have prev >= 1 and prev >= cur - dungeon[prev], thus prev = max(1, cur - dungeon[prev])
    //I will leave rest explanation to you :)
    public int calculateMinimumHP(int[][] dungeon) {
        // corner case
        if(dungeon == null || dungeon.length == 0 || dungeon[0].length == 0){
            return 0;
        }

        // dp[i][j] represents the health when I reach dungeon[i][j]
        int M = dungeon.length;
        int N = dungeon[0].length;
        int[][] dp = new int[M][N];//dp[i][j] means if you start from row i, col j, if you want go to row M - 1, col N - 1, the min health he has to get
        // initialization:
        // dp[M - 1][N - 1] = Math.max(1, 1 - dungeon[M - 1][N - 1]);
        // or dp[M - 1][N - 1] = dungeon[i][j] >= 0? 1 : -dungeon[i][j] + 1;
        // induction rule:
        // dp[i][N - 1] = max(dp[i + 1][N - 1] - dungeon[i][N - 1], 1)
        // dp[M - 1][j] = max(dp[M - 1][j + 1] - dungeon[M - 1][j], 1)
        // dp[i][j] = min(max(dp[i][j + 1] - dungeon[i][j], 1), max(dp[i + 1][j] - dungeon[i][j], 1))
        for(int i = M - 1; i >= 0; i--){
            for(int j = N - 1; j >= 0; j--){
                if(i == M - 1 && j == N - 1){
                    dp[M - 1][N - 1] = dungeon[i][j] >= 0? 1 : -dungeon[i][j] + 1;
                }else if(i == M - 1){
                    dp[i][j] = Math.max(dp[i][j + 1] - dungeon[i][j], 1);
                }else if(j == N - 1){
                    dp[i][j] = Math.max(dp[i + 1][j] - dungeon[i][j], 1);
                }else{
                    dp[i][j] = Math.min(Math.max(dp[i + 1][j] - dungeon[i][j], 1), Math.max(dp[i][j + 1] - dungeon[i][j], 1));
                    //dp[i + 1][j] - dungeon[i][j]: the minimum health you need to have to go downward to dp[i + 1][j]
                    //dp[i][j + 1] - dungeon[i][j]: the minimum health you need to have to go rightward to dp[i][j + 1]
                }
            }
        }
        return dp[0][0];
    }
}

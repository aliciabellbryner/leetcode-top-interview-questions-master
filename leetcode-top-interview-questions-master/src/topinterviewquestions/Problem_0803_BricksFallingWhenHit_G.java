package topinterviewquestions;

public class Problem_0803_BricksFallingWhenHit_G {
    //https://leetcode.com/problems/bricks-falling-when-hit/discuss/119829/Python-Solution-by-reversely-adding-hits-bricks-back/152119
    public int[] hitBricks(int[][] grid, int[][] hits) {
        int[] res = new int[hits.length], dirs = {-1, 0, 1, 0, -1};
        for (int[] h : hits)
            grid[h[0]][h[1]] -= 1;
        for (int i = 0; i < grid[0].length; i++)
            dfs(0, i, grid);
        for (int k = hits.length - 1; k >= 0; k--) {
            int h[] = hits[k], i = h[0], j = h[1];
            grid[i][j] += 1;
            if (grid[i][j] == 1 && isConnected(i, j, grid, dirs))
                res[k] = dfs(i, j, grid) - 1;
        }
        return res;
    }

    int dfs(int i, int j, int[][] g) {
        if (i < 0 || i >= g.length || j < 0 || j >= g[0].length || g[i][j] != 1) return 0;
        g[i][j] = 2;
        return 1 + dfs(i + 1, j, g) + dfs(i - 1, j, g) + dfs(i, j + 1, g) + dfs(i, j - 1, g);
    }

    boolean isConnected(int i, int j, int[][] g, int[] dirs) {
        if (i == 0) return true;
        for (int k = 1; k < dirs.length; k++) {
            int r = i + dirs[k - 1], c = j + dirs[k];
            if (0 <= r && r < g.length && 0 <= c && c < g[0].length && g[r][c] == 2)
                return true;
        }
        return false;
    }
}

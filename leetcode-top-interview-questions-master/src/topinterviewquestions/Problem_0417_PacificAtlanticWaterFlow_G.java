package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

public class Problem_0417_PacificAtlanticWaterFlow_G {
    //https://leetcode.com/problems/pacific-atlantic-water-flow/discuss/90733/Java-BFS-and-DFS-from-Ocean/95157
    //Start DFS from each boundary.
    //Then find common visited node.
    public List<int[]> pacificAtlantic(int[][] matrix) {
        List<int[]> res = new ArrayList();
        int m = matrix.length;
        if(m == 0) return res;
        int n = matrix[0].length;
        boolean[][] pac = new boolean[m][n];//pac[i][j] means matrix[i][j] can flow to pacific
        boolean[][] atl = new boolean[m][n];//atl[i][j] means matrix[i][j] can flow to atlantic
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        for(int i = 0; i < m; i++){
            helper(matrix, dir, pac, i,  0);
            helper(matrix, dir, atl, i, n - 1);
        }

        for(int i = 0; i < n; i++){
            helper(matrix, dir, pac, 0,  i);
            helper(matrix, dir, atl, m - 1, i);
        }

        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(pac[i][j] && atl[i][j]) res.add(new int[]{i, j});
            }
        }
        return res;
    }

    void helper(int[][] matrix, int[][] dir, boolean[][] isVisited, int i, int j){
        int m = matrix.length, n = matrix[0].length;
        isVisited[i][j] = true;
        for(int[] d: dir){
            int x = i + d[0];
            int y = j + d[1];
            if(x < 0 || y < 0 || x >= m || y >= n || isVisited[x][y] || matrix[i][j] > matrix[x][y]){
                continue;//要找到所有在matrix范围内且高度比之前高的位置，把他们标为true
            }
            helper(matrix, dir, isVisited, x, y);
        }
    }
}

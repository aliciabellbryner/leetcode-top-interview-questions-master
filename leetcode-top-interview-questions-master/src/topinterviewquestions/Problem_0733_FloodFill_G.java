package topinterviewquestions;

public class Problem_0733_FloodFill_G {
    //https://leetcode.com/problems/flood-fill/discuss/109584/Java-9-liner-DFS
    //Time complexity: O(m*n), space complexity: O(1). m is number of rows, n is number of columns.
    class Solution {
        public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
            if (image[sr][sc] == newColor) return image;
            fill(image, sr, sc, image[sr][sc], newColor);
            return image;
        }

        private void fill(int[][] image, int sr, int sc, int color, int newColor) {
            if (sr < 0 || sr >= image.length || sc < 0 || sc >= image[0].length || image[sr][sc] != color) return;
            image[sr][sc] = newColor;
            fill(image, sr + 1, sc, color, newColor);
            fill(image, sr - 1, sc, color, newColor);
            fill(image, sr, sc + 1, color, newColor);
            fill(image, sr, sc - 1, color, newColor);
        }
    }
}

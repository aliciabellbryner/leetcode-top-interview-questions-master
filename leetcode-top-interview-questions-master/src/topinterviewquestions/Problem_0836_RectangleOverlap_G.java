package topinterviewquestions;

public class Problem_0836_RectangleOverlap_G {
    //https://leetcode.com/problems/rectangle-overlap/discuss/132340/C++JavaPython-1-line-Solution-1D-to-2D/528840
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {//逆向思维，res_reversed代表两个rect不重叠
        boolean res_reversed = rec2[0] >= rec1[2] || rec2[2] <= rec1[0] || rec2[1] >= rec1[3] || rec2[3] <= rec1[1];
        return !res_reversed;
    }
}

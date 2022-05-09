package topinterviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem_1272_RemoveInterval_G {
    //https://www.cnblogs.com/cnoodle/p/13437387.html
    /*
     1 class Solution {
 2     public List<List<Integer>> removeInterval(int[][] intervals, int[] toBeRemoved) {
            3         List<List<Integer>> res = new ArrayList<>();
            4         for (int[] i : intervals) {
                5             // no overlap
                6             if (i[1] <= toBeRemoved[0] || i[0] >= toBeRemoved[1]) {
                    7                 res.add(Arrays.asList(i[0], i[1]));
                    8             }
                9             // i[1] > toBeRemoved[0] && i[0] < toBeRemoved[1]
                10             else {
                    11                 // left end no overlap
                    12                 if (i[0] < toBeRemoved[0]) {
                        13                     res.add(Arrays.asList(i[0], toBeRemoved[0]));
                        14                 }
                    15                 // right end no overlap
                    16                 if (i[1] > toBeRemoved[1]) {
                        17                     res.add(Arrays.asList(toBeRemoved[1], i[1]));
                        18                 }
                    19             }
                20         }
            21         return res;
            22     }
23 }

     */

}

package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

public class Problem_0228_SummaryRanges_G {
    //two pointers
    //https://leetcode.com/problems/summary-ranges/discuss/63219/Accepted-JAVA-solution-easy-to-understand/269079
    class Solution {
        public List<String> summaryRanges(int[] nums) {
            List<String> res = new ArrayList<>();
            if (nums == null || nums.length == 0) {
                return res;
            }
            int start = nums[0];
            int end = nums[0];
            for (int i = 1; i < nums.length; i++) {
                if (end + 1 != nums[i]) {
                    res.add(format(start, end));
                    start = nums[i];
                }
                end = nums[i];
            }
            res.add(format(start, end));
            return res;
        }
        private String format(int start, int end) {
            return start == end ? String.valueOf(start) : start + "->" + end;
        }
    }
}

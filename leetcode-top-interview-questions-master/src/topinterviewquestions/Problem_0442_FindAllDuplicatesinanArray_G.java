package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

public class Problem_0442_FindAllDuplicatesinanArray_G {
    //https://leetcode.com/problems/find-all-duplicates-in-an-array/discuss/92387/Java-Simple-Solution
    public class Solution {
        // when find a number i, flip the number at position i-1 to negative.
        // if the number at position i-1 is already negative, i is the number that occurs twice.

        public List<Integer> findDuplicates(int[] nums) {
            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < nums.length; ++i) {
                int index = Math.abs(nums[i])-1;
                if (nums[index] < 0)
                    res.add(Math.abs(index+1));
                nums[index] = -nums[index];
            }
            return res;
        }
    }
}

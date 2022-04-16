package topinterviewquestions;

public class Problem_0659_SplitArrayintoConsecutiveSubsequences_G {
    //https://leetcode.com/problems/split-array-into-consecutive-subsequences/discuss/106495/Java-O(n)-time-and-O(1)-space-solution-greedily-extending-shorter-subsequence/307704
    //Java O(n) time & O(1) space solution -- greedily extending shorter subsequence
    public boolean isPossible(int[] nums) {
        //        # p1: number of length 1 sequence ending at previous value
        //        # p2: number of length 1 sequence ending at previous value
        //        # p3: number of length 3 sequence ending at previous value
        int pre = Integer.MIN_VALUE, p1 = 0, p2 = 0, p3 = 0;
        //                # c1: number of length 1 sequence ending at current value
        //                # c2: number of length 2 sequence ending at current value
        //                # c3: number of length 3 sequence ending at current value
        int curr = 0, count = 0, c1 = 0, c2 = 0, c3 = 0;

        for (int i = 0; i < nums.length;) {
            curr = nums[i];
            count = 0;//count the number of appearances for current value
            while (i < nums.length && curr == nums[i]) {
                count++;
                i++;
            }

            if (curr != pre + 1) {//case 1: current value != previous value + 1
                if (p1 != 0 || p2 != 0) return false;//# there shall be no length 1 or 2 sequences ending at previous value
                c1 = count; c2 = 0; c3 = 0;
            } else {
                if (count < p1 + p2) return false;//
                // # there shall be enough values to fill the previous length 1 and length 2 sequences
                c1 = Math.max(0, count - (p1 + p2 + p3));
                //                # if there are even some values left after we fill length 1,2,3
                //                # sequences, then these values have to become the start of new
                //                # length 1 sequences
                c2 = p1;//highest priority will be given to previous length 1 sequences
                c3 = p2 + Math.min(p3, count - (p1 + p2));
                //                # then priority will be given to previous length 2 sequences
                //                # if there are still some remainings, to the previous length 3
                //                # sequences as well
            }

            //update
            pre = curr;
            p1 = c1;
            p2 = c2;
            p3 = c3;
        }
        return (p1 == 0 && p2 == 0);//# in the end, there shall be no length 1 or 2 sequence unfilled
    }



    //https://leetcode.com/problems/split-array-into-consecutive-subsequences/discuss/106495/Java-O(n)-time-and-O(1)-space-solution-greedily-extending-shorter-subsequence/307704
    public boolean isPossible2(int[] nums) {
        int pre = Integer.MIN_VALUE, p1 = 0, p2 = 0, p3 = 0;
        int curr = 0, count = 0, c1 = 0, c2 = 0, c3 = 0;

        for (int i = 0; i < nums.length;) {
            curr = nums[i];
            count = 0;
            while (i < nums.length && curr == nums[i]) {
                count++;
                i++;
            }

            if (curr != pre + 1) {
                if (p1 != 0 || p2 != 0) return false;
                c1 = count; c2 = 0; c3 = 0;
            } else {
                if (count < p1 + p2) return false;
                c1 = Math.max(0, count - (p1 + p2 + p3));
                c2 = p1;
                c3 = p2 + Math.min(p3, count - (p1 + p2));
            }

            pre = curr;
            p1 = c1;
            p2 = c2;
            p3 = c3;
        }
        return (p1 == 0 && p2 == 0);
    }
}

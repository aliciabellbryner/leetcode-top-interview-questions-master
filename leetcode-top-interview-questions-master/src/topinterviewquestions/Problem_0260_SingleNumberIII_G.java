package topinterviewquestions;

public class Problem_0260_SingleNumberIII_G {
    //https://leetcode.com/problems/single-number-iii/discuss/68900/Accepted-C%2B%2BJava-O(n)-time-O(1)-space-Easy-Solution-with-Detail-Explanations
    //In the first pass, we XOR all elements in the array, and get the XOR of the two numbers we need to find. Note that since the two numbers are distinct, so there must be a set bit (that is, the bit with value '1') in the XOR result. Find
    //out an arbitrary set bit (for example, the rightmost set bit).
    //
    //In the second pass, we divide all numbers into two groups, one with the aforementioned bit set, another with the aforementinoed bit unset. Two different numbers we need to find must fall into thte two distrinct groups. XOR numbers in each group, we can find a number in either group.
    //Time: O (n)
    //
    //Space: O (1)
    public class Solution {
        public int[] singleNumber(int[] nums) {
            // Pass 1 :
            // Get the XOR of the two numbers we need to find
            int diff = 0;
            for (int num : nums) {
                diff ^= num;
            }
            // Get its last set bit
            diff &= -diff;

            // Pass 2 :
            int[] res = {0, 0}; // this array stores the two numbers we will return
            for (int num : nums)
            {
                if ((num & diff) == 0) // the bit is not set
                {
                    res[0] ^= num;
                }
                else // the bit is set
                {
                    res[1] ^= num;
                }
            }
            return res;
        }
    }
}

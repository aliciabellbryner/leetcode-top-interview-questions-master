package topinterviewquestions;

public class Problem_1929_ConcatenationofArray_G {
    //https://leetcode.com/problems/concatenation-of-array/discuss/1330265/Java-4-lines
    public int[] getConcatenation(int[] nums) {
        int[] result = new int[nums.length * 2];
        for(int i=0;i<nums.length;i++)
            result[i + nums.length] = result[i] = nums[i];
        return result;
    }
}

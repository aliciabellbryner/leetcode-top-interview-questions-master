package topinterviewquestions;

public class Problem_0209_MinimumSizeSubarraySum_G {
    //two pointers, https://leetcode.com/problems/minimum-size-subarray-sum/discuss/59078/Accepted-clean-Java-O(n)-solution-(two-pointers)/210841
    public int minSubArrayLen(int s, int[] nums) {
        if(nums == null) {
            return 0;
        }
        int res = Integer.MAX_VALUE;
        int sum = 0;
        int localAns = 0;
        for(int fast = 0, slow = 0;fast< nums.length;fast++){ //fast is the faster pointer, slow is the slower pointer
            sum += nums[fast];
            localAns++;
            while(sum >= s){
                res = Math.min(res,localAns);// update global res.
                localAns--;
                sum -= nums[slow];
                slow++; // move the slower pointer forward and decrease the related nums[slow], at the same time localAns will be decreased by 1.
            }
        }
        return (res == Integer.MAX_VALUE) ? 0: res;
    }
}

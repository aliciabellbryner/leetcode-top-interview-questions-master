package topinterviewquestions;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Problem_0414_ThirdMaximumNumber_G {

    //https://leetcode.com/problems/third-maximum-number/discuss/90202/Java-neat-and-easy-understand-solution-O(n)-time-O(1)-space/168202
    public static int thirdMax2(int[] nums) {

        long firstMax = Long.MIN_VALUE,secondMax = Long.MIN_VALUE,thirdMax = Long.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i]> firstMax) {
                thirdMax = secondMax;
                secondMax = firstMax;
                firstMax = nums[i];
            }else if(nums[i]> secondMax && nums[i] < firstMax) {
                thirdMax = secondMax;
                secondMax = nums[i];
            }else if( nums[i] > thirdMax && nums[i] < secondMax) {
                thirdMax = nums[i];
            }
        }
        return (int)(thirdMax == Long.MIN_VALUE ? firstMax: thirdMax);
    }


    //https://leetcode.com/problems/third-maximum-number/discuss/90190/Java-PriorityQueue-O(n)-%2B-O(1)
    public class Solution {
        public int thirdMax(int[] nums) {
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            Set<Integer> set = new HashSet<>();
            for(int n : nums) {
                if(set.add(n)) {
                    pq.offer(n);
                    if(pq.size() > 3 ) pq.poll();
                }
            }
            if(pq.size() == 2) pq.poll();
            return pq.peek();
        }
    }

}

package topinterviewquestions;
/*
Given an array of integers nums, return the number of good pairs.

A pair (i, j) is called good if nums[i] == nums[j] and i < j.



Example 1:

Input: nums = [1,2,3,1,1,3]
Output: 4
Explanation: There are 4 good pairs (0,3), (0,4), (3,4), (2,5) 0-indexed.
Example 2:

Input: nums = [1,1,1,1]
Output: 6
Explanation: Each pair in the array are good.
Example 3:

Input: nums = [1,2,3]
Output: 0


Constraints:

1 <= nums.length <= 100
1 <= nums[i] <= 100
 */
public class Problem_1512_NumberofGoodPairs_G {
    //diss
    //https://leetcode.com/problems/number-of-good-pairs/discuss/731561/JavaC++Python-Count/614803
    public int numIdenticalPairs(int[] nums) {
        int[] cnt = new int[101];
        for(int i = 0;i<nums.length;i++){
            cnt[nums[i]]++;
        }
        int res = 0;
        for(int i = 0;i<cnt.length;i++){
            res += cnt[i]*(cnt[i] - 1)/2;
        }
        return res;
    }


    //https://leetcode.com/problems/number-of-good-pairs/discuss/731561/JavaC%2B%2BPython-Count
    //Explanation
    //count the occurrence of the same elements.
    //For each new element a,
    //there will be more count[a] pairs,
    //with A[i] == A[j] and i < j
    //
    //
    //Complexity
    //Time O(N)
    //Space O(N)
    public int numIdenticalPairs1(int[] A) {
        int res = 0, count[] = new int[101];
        for (int a: A) {
            res += count[a]++;
        }
        return res;
    }


}

package topinterviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem_0368_LargestDivisibleSubset_G {
    //https://leetcode.com/problems/largest-divisible-subset/discuss/684677/3-STEPS-c%2B%2B-or-python-or-java-dp-PEN-PAPER-DIAGRAM
    static class Solution {
        // if we sort the array, every element in a divisibleSubset can be divisible by the element just before it.
        // for any element k, its largestDivisibleSubset that ends with k can be formed in the following way:
        // use element k after any one of the previous elements that is divisble
        public static List<Integer> largestDivisibleSubset(int[] nums) {
            int[] l = new int[nums.length]; // the length of largestDivisibleSubset that ends with element i
            int[] prev = new int[nums.length]; // the previous index of element i in the largestDivisibleSubset ends with element i

            Arrays.sort(nums);

            int max = 0;
            int index = -1;
            for (int i = 0; i < nums.length; i++){
                l[i] = 1;
                prev[i] = -1;
                for (int j = i - 1; j >= 0; j--){
                    if (nums[i] % nums[j] == 0 && l[j] + 1 > l[i]){
                        l[i] = l[j] + 1;
                        prev[i] = j;
                    }
                }
                if (l[i] > max){
                    max = l[i];
                    index = i;
                }
            }
            List<Integer> res = new ArrayList<Integer>();
            while (index != -1){
                res.add(nums[index]);
                index = prev[index];
            }
            System.out.println(Arrays.toString(l));//[1, 2, 2]
            System.out.println(Arrays.toString(prev));//[-1, 0, 0]
            return res;//[2, 1]
        }

    }

    public static void main(String[] args) {
        int[] test = new int[]{1,2,4,8};
        System.out.println((Solution.largestDivisibleSubset(test)));
    }
}

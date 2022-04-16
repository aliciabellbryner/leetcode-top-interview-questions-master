package topinterviewquestions;

import java.util.Arrays;

public class Problem_0360_SortTransformedArray_G {
    //https://tenderleo.gitbooks.io/leetcode-solutions-/content/GoogleMedium/360.html
    //time O(N)
    public static class Solution {
        //思路是要看二次函数a是大于0还是小于0
        public static int[] sortTransformedArray(int[] nums, int a, int b, int c) {
            if(nums == null || nums.length == 0) return nums;
            if(nums.length == 1){
                nums[0] = eval(nums[0],a,b,c);
                return nums;
            }

            int l = 0;
            int r = nums.length -1;
            int[] res = new int[nums.length];
            int k = 0;
            while(l <= r){ // need to equal to get the final number.
                int v1 = eval(nums[l], a, b, c);
                int v2 = eval(nums[r], a, b, c);

                if(a > 0){
                    res[k++] = v1 > v2 ? v1 : v2;
                    if(v1 > v2)  l++;
                    else r--;
                }else{
                    res[k++] = v1 > v2 ? v2 : v1;
                    if(v1 > v2 ) r--;
                    else l++;
                }
            }

            if(a > 0){//如果a > 0,需要把res反转
                int left = 0;
                int right = res.length -1;
                while(left < right){
                    int tmp = res[left];
                    res[left] = res[right];
                    res[right] = tmp;
                    left++;
                    right--;
                }
            }

            return res;
        }

        private static int eval(int n, int a, int b, int c){
            return a*n*n + b*n + c;
        }
    }

    public static void main(String[] args) {
        int[] test = new int[]{-4, -2, 2, 4};
        System.out.println(Arrays.toString(Solution.sortTransformedArray(test, 1, 3, 5)));
    }

    public class Solution2 {
        public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
            if(nums == null || nums.length <=1) return nums;

            int[] res = new int[nums.length];
            if(a > 0){
                int k = res.length-1;
                int l = 0, r = k;
                while(k >=0){
                    int tl = getT(nums[l],a,b,c);
                    int tr = getT(nums[r],a,b,c);

                    if(tl > tr){
                        res[k] = tl;
                        l++;
                    }else{
                        res[k] = tr;
                        r--;
                    }
                    k--;
                }
            }else if(a < 0){
                int k = 0;
                int l = 0, r = res.length-1;
                while(k < nums.length){
                    int tl = getT(nums[l],a,b,c);
                    int tr = getT(nums[r],a,b,c);

                    if(tl < tr){
                        res[k] = tl;
                        l++;
                    }else{
                        res[k] = tr;
                        r--;
                    }
                    k++;
                }
            }else{
                for(int i= 0; i< res.length;i++){
                    res[i] = getT(nums[i], 0,b,c);
                }
                if(b<0){
                    int l =0, r = res.length-1;
                    while(l < r){
                        int tmp = res[l];
                        res[l] = res[r];
                        res[r] = tmp;
                        l++;
                        r--;
                    }
                }
            }

            return res;
        }

        int getT(int x, int a, int b, int c){
            return a*x*x + b*x + c;
        }
    }

}

package NewsBreak;

import java.util.ArrayList;
import java.util.List;

public class PermutationAndCombination {
    //combination
    //Input: n = 4, k = 2
    //Output:
    //[
    //  [2,4],
    //  [3,4],
    //  [2,3],
    //  [1,2],
    //  [1,3],
    //  [1,4],
    //]
    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> combs = new ArrayList<List<Integer>>();
        combine(combs, new ArrayList<Integer>(), 1, n, k);
        return combs;
    }
    public static void combine(List<List<Integer>> combs, List<Integer> comb, int start, int n, int k) {
        if(k == 0) {
            combs.add(new ArrayList<Integer>(comb));
            return;
        }
        for(int i=start;i<=n;i++) {
            comb.add(i);
            combine(combs, comb, i+1, n, k-1);
            comb.remove(comb.size()-1);
        }
    }

    //permutation
    //Input: nums = [1,2,3]
    //Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        process(nums, 0, ans);
        return ans;
    }

    public static void process(int[] nums, int index, List<List<Integer>> ans) {
        if (index == nums.length) {
            ArrayList<Integer> cur = new ArrayList<>();
            for (int num : nums) {
                cur.add(num);
            }
            ans.add(cur);
        } else {
            for (int j = index; j < nums.length; j++) {
                swap(nums, index, j);
                process(nums, index + 1, ans);
                swap(nums, index, j);
            }
        }
    }

    //followup: permutation with duplicate
    public static List<List<Integer>> permute_dup(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        process_dup(nums, 0, ans);
        return ans;
    }

    public static void process_dup(int[] nums, int index, List<List<Integer>> ans) {
        if (index == nums.length) {
            ArrayList<Integer> cur = new ArrayList<>();
            for (int num : nums) {
                cur.add(num);
            }
            ans.add(cur);
        } else {
            boolean[] visited = new boolean[256];
            for (int j = index; j < nums.length; j++) {
                if (!visited[nums[j]]) {
                    visited[nums[j]] = true;
                    swap(nums, index, j);
                    process_dup(nums, index + 1, ans);
                    swap(nums, index, j);
                }

            }
        }
    }

    public static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        System.out.println(permute(new int[]{1,2,2,3}));
    }
}

package NewsBreak;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class P40_CombinationSumII {

    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> comb = new ArrayList<>();
        backtrack(candidates, target, res, comb, 0);
        return res;
    }
    private static void backtrack(int[] candidates, int remain, List<List<Integer>> res, List<Integer> comb, int start) {
        if (remain == 0) {
            res.add(new ArrayList<>(comb));
        }
        if (remain < 0) {
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (i > start && candidates[i] == candidates[i-1]) {//it is not i > 0!!!
                continue;
            }
            comb.add(candidates[i]);
            backtrack(candidates, remain - candidates[i], res, comb, i + 1);
            comb.remove(comb.size() - 1);
        }
    }

    public static void main(String[] args) {
        int[] test = new int[]{10,1,2,7,6,1,5};
        System.out.println(combinationSum2(test, 8));
    }
}

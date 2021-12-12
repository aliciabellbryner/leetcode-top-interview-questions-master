package NewsBreak;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class P39_CombinationSum {
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
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
            comb.add(candidates[i]);
            backtrack(candidates, remain - candidates[i], res, comb, i);
            comb.remove(comb.size() - 1);
        }
    }

    public static void main(String[] args) {
        int[] test = new int[]{2,3,6,7};
        System.out.println(combinationSum(test, 7));
    }
}

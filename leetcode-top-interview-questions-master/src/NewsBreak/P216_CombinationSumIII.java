package NewsBreak;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class P216_CombinationSumIII {
    public static List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> comb = new ArrayList<>();
        backtrack(k, n, res, comb, 1);
        return res;
    }
    private static void backtrack(int k, int remain, List<List<Integer>> res, List<Integer> comb, int start) {
        if (comb.size() == k && remain == 0) {
            res.add(new ArrayList<>(comb));
            return;
        }
        if (comb.size() > k || remain < 0) {
            return;
        }
        for (int i = start; i <= 9; i++) {
            comb.add(i);
            backtrack(k, remain - i, res, comb, i + 1);
            comb.remove(comb.size() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(combinationSum3(3, 7));
    }

}

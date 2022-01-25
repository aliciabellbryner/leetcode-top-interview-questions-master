package topinterviewquestions;

import java.util.HashSet;
import java.util.Set;

public class Problem_2128_RemoveAllOnesWithRowandColumnFlips_G {
    //https://leetcode.com/problems/remove-all-ones-with-row-and-column-flips/discuss/1671908/Python-3-or-Math-or-Explanation/1211383
    //思路就是如果要true则必须每一行的patten要相同，比如001100和110011是相同pattern，
    // 所以把第一行的patten以及都取反之后的pattern都放到hashset里看看下面的行是否是在里面就可以
    public boolean removeOnes(int[][] grid) {
        Set<String> set = new HashSet<>();
        StringBuilder sb = new StringBuilder(), reversedSb = new StringBuilder();
        for(int i = 0; i < grid[0].length; i++){
            sb.append(grid[0][i]);
            reversedSb.append(grid[0][i] == 0 ? 1 : 0);
        }
        set.add(sb.toString());
        set.add(reversedSb.toString());
        for(int j = 1; j < grid.length; j++){
            sb = new StringBuilder();
            for(int i = 0; i < grid[j].length; i++){
                sb.append(grid[j][i]);
            }
            if(!set.contains(sb.toString())){
                return false;
            }
        }
        return true;
    }
}

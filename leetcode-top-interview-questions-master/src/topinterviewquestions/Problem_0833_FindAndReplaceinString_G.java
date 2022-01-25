package topinterviewquestions;

import java.util.*;

public class Problem_0833_FindAndReplaceinString_G {

    //https://leetcode.com/problems/find-and-replace-in-string/discuss/130587/C%2B%2BJavaPython-Replace-S-from-right-to-left
    //time  O(NlogN + S). N is the length of indexes as the Arrays.sort, S is the length of S
    //space O(N)
    public String findReplaceString(String S, int[] indexes, String[] sources, String[] targets) {
        StringBuilder res = new StringBuilder(S);
        int len = indexes.length;
        int[][] sort = new int[len][2];
        for(int i=0;i<len;i++){
            sort[i] = new int[]{i, indexes[i]};
        }
        Arrays.sort(sort, (a, b)->a[1]-b[1]);
        for (int i = len - 1; i >= 0; i--) {//注意是从后往前替换，这样的话每次替换长度变化就不会影响下一步的选择替换的位置了
            int j = sort[i][0];
            int index = sort[i][1];
            if(S.startsWith(sources[j], index)) {//S在index位置是以sources[i]开头的
                res.replace(index,index+sources[j].length(), targets[j]);//把res相应位置作替换
            }
        }
        return res.toString();
    }

}

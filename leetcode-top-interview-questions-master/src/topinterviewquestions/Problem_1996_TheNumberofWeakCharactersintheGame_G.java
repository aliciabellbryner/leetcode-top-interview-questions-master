package topinterviewquestions;

import java.util.Arrays;

public class Problem_1996_TheNumberofWeakCharactersintheGame_G {
    //https://leetcode.com/problems/the-number-of-weak-characters-in-the-game/discuss/1445242/Java-Solution-or-Use-only-Sorting-and-one-max-mark
    //properties[i] = [attacki, defensei]
    //Just sort the array accroding to ascending升序 order of attack;
    // if there are same attack, to ordering with descending降序 defense.
    //Get variable to track the max preivous defense.
    //一个升序一个降序的目的是为了让如果attach相等的情况下，排除相同attach的比较
    public static int numberOfWeakCharacters(int[][] properties) {
        Arrays.sort(properties, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
        int maxDef = Integer.MIN_VALUE;
        int res = 0;
        for (int i = properties.length - 1; i >= 0; i--) {
            if (properties[i][1] < maxDef) {
                res++;
            }
            maxDef = Math.max(properties[i][1], maxDef);
        }

        return res;
    }

    public static void main(String[] args) {
//        Integer[] test = new Integer[]{2,1,4,5};
//        Arrays.sort(test, (a, b) -> a - b);
//        System.out.println(Arrays.toString(test));
        int[][] test = new int[][]{{3,2}, {3,6}};
        System.out.println(numberOfWeakCharacters(test));

    }
}

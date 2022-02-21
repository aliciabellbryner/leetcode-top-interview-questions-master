package topinterviewquestions;

import java.util.Arrays;
/*
You are playing a game that contains multiple characters, and each of the characters has two main properties: attack and defense. You are given a 2D integer array properties where properties[i] = [attacki, defensei] represents the properties of the ith character in the game.

A character is said to be weak if any other character has both attack and defense levels strictly greater than this character's attack and defense levels. More formally, a character i is said to be weak if there exists another character j where attackj > attacki and defensej > defensei.

Return the number of weak characters.



Example 1:

Input: properties = [[5,5],[6,3],[3,6]]
Output: 0
Explanation: No character has strictly greater attack and defense than the other.
Example 2:

Input: properties = [[2,2],[3,3]]
Output: 1
Explanation: The first character is weak because the second character has a strictly greater attack and defense.
Example 3:

Input: properties = [[1,5],[10,4],[4,3]]
Output: 1
Explanation: The third character is weak because the second character has a strictly greater attack and defense.


Constraints:

2 <= properties.length <= 105
properties[i].length == 2
1 <= attacki, defensei <= 105
 */
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

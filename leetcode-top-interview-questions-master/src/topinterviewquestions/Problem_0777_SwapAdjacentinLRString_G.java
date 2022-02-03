package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

public class Problem_0777_SwapAdjacentinLRString_G {

    //https://leetcode.com/problems/swap-adjacent-in-lr-string/discuss/113789/Simple-Java-one-pass-O(n)-solution-with-explaination
    public boolean canTransform(String start, String end) {
        if (!start.replace("X", "").equals(end.replace("X", "")))
            return false;

        int p1 = 0;
        int p2 = 0;

        while(p1 < start.length() && p2 < end.length()){

            // get the non-X positions of 2 strings
            while(p1 < start.length() && start.charAt(p1) == 'X'){
                p1++;
            }
            while(p2 < end.length() && end.charAt(p2) == 'X'){
                p2++;
            }

            //if both of the pointers reach the end the strings are transformable
            if(p1 == start.length() && p2 == end.length()){
                return true;
            }
            // if only one of the pointer reach the end they are not transformable
            if(p1 == start.length() || p2 == end.length()){
                return false;
            }

            if(start.charAt(p1) != end.charAt(p2)){
                return false;
            }
            // if the character is 'L', it can only be moved to the left. p1 should be greater or equal to p2.
            if(start.charAt(p1) == 'L' && p2 > p1){
                return false;
            }
            // if the character is 'R', it can only be moved to the right. p2 should be greater or equal to p1.
            if(start.charAt(p1) == 'R' && p1 > p2){
                return false;
            }
            p1++;
            p2++;
        }
        return true;
    }


    //https://leetcode.com/problems/swap-adjacent-in-lr-string/discuss/217070/Python-using-corresponding-position-
    //https://leetcode.com/problems/swap-adjacent-in-lr-string/discuss/217070/Python-using-corresponding-position-/595856
    class Solution {
        class Pair {
            int i;
            char c;
            Pair(int i, char c) {
                this.i = i;
                this.c = c;
            }
        }

        public boolean canTransform(String start, String end) {
            List<Pair> startPairs = new ArrayList<>();
            List<Pair> endPairs = new ArrayList<>();
            for (int i = 0; i < Math.max(start.length(), end.length()); i++) {
                if (i < start.length() && start.charAt(i) != 'X') {
                    startPairs.add(new Pair(i, start.charAt(i)));
                }
                if (i < end.length() && end.charAt(i) != 'X') {
                    endPairs.add(new Pair(i, end.charAt(i)));
                }
            }

            if (startPairs.size() != endPairs.size()) return false;
            for (int i = 0; i < startPairs.size(); i++) {
                if (startPairs.get(i).c != endPairs.get(i).c) return false;
                if (startPairs.get(i).c == 'L' && startPairs.get(i).i < endPairs.get(i).i) return false;
                if (startPairs.get(i).c == 'R' && startPairs.get(i).i > endPairs.get(i).i) return false;
            }

            return true;
        }
    }

}

package topinterviewquestions;

public class Problem_1888_MinimumNumberofFlipstoMaketheBinaryStringAlternating_G {
    //https://leetcode.com/problems/minimum-number-of-flips-to-make-the-binary-string-alternating/discuss/1253874/C++-Solution-sliding-window.-O(N)-Time-O(1)-Space/963646
    //For the 1st operation, we can simply do s += s to append the whole string to the end.
    //then we make two different string with the same length by 01 and 10 alternative. for example: s = 11100
    //s = 1110011100
    //s1= 1010101010
    //s2= 0101010101
    //finally, use sliding window（size n）to compare s to both s1 and s2.
    //why we can double s to fullfil the first operation, let's look at the same example s = 11100:`
    //double s: 1110011100
    //size n window: |11100|11100 ==> 1|11001|1100 ==> 11|10011|100 and so on, when we move one step of the sliding window, it is the same to append 1 more element from beginning.
    //Time complexity
    //Time O(N)
    //Space O(N) ==> O(1)
    public int minFlips(String s) {
        int n = s.length(), res0 = 0, res1 = 0, res = n;

        for(int i = 0; i < 2 * n; i++){
            char characterInString = s.charAt(i % n);//current character
            char characterInStringStartingWith0 = i % 2 == 0 ? '0' : '1';//calculate character in 01010101..... at i
            char characterInStringStartingWith1 = i % 2 == 0 ? '1' : '0';//calculate character in 10101010..... at i

            if(characterInStringStartingWith0 != characterInString) res0++;//doesn't match means we need to flip
            if(characterInStringStartingWith1 != characterInString) res1++;//doesn't match means we need to flip

            if(i >= n) {//valid window
                int windowStart = i - n;//leftmost element of the window
                char characterInStringStartingWith0AtWindowStart = windowStart % 2 == 0 ? '0' : '1';//calculate character in 01010101..... at window start
                char characterInStringStartingWith1AtWindowStart = windowStart % 2 == 0 ? '1' : '0';//calculate character in 10101010..... at window start

                if(characterInStringStartingWith0AtWindowStart != s.charAt(windowStart)) res0--;//doesn't match means we flipped this before, subtract 1
                if(characterInStringStartingWith1AtWindowStart != s.charAt(windowStart)) res1--;//doesn't match means we flipped this before, subtract 1

                res = Math.min(res, Math.min(res0, res1));//calculate min
            }
        }
        return res;
    }

    //improved to use only one alternating string
    public int minFlips2(String s) {
        int n = s.length(), res0 = 0, res1 = 0, res = n;

        for(int i = 0; i < 2 * n; i++){
            char characterInString = s.charAt(i % n);
            char characterInStringStartingWith0 = i % 2 == 0 ? '0' : '1';

            if(characterInStringStartingWith0 != characterInString) res0++;
            else res1++;

            if(i >= n) {
                int windowStart = i - n;
                char characterInStringStartingWith0AtWindowStart = windowStart % 2 == 0 ? '0' : '1';

                if(characterInStringStartingWith0AtWindowStart != s.charAt(windowStart)) res0--;
                else res1--;

                res = Math.min(res, Math.min(res0, res1));
            }
        }
        return res;
    }
}

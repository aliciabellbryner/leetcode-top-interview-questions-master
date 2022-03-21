package topinterviewquestions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Problem_2135_CountWordsObtainedAfterAddingaLetter_G {
    //https://leetcode.com/problems/count-words-obtained-after-adding-a-letter/discuss/1676852/Python3-bitmask/1218055
    //bitmask
    //BItmask of word is a number that uniquely represents a set of characters occurring once . Hence bitmask(abc)=bitmask(bac) and bitmask(abca)=bitmask(bc) and so on (all this is because of properties of xor: a^a=0 and a^b=b^a). This will later help us check for all permutations later. Note that we are never going to happen abca type situation as given in question.
    //
    //How we obtain bitmask of a word?
    //Well, first map each character to a power of 2 number. That is the 1 << ord(ch)-97 part in the code (97=ascii of a). Essentially we are mapping {a: 1, b: 2, c: 4, d: 8,...,z: pow(2,25). Then for all the characters in the word we just take the sum. Note that since they are all powers of 2, a sum is same as xor. This is the bitmask of the word (The number m in the code)
    public int wordCount1(String[] startWords, String[] targetWords) {
        int ans = 0, m = 0;
        Set<Integer> hs = new HashSet<>();

        for(String w: startWords){
            m = 0;
            for(char c: w.toCharArray()) {
                m |= (1 << (c-'a'));
            }
            hs.add(m);
        }
        for(String w: targetWords){
            m = 0;
            for(char c: w.toCharArray()) {
                m |= (1<< (c-'a'));
            }
            for(char c: w.toCharArray()){
                if(hs.contains(m - (1<< (c-'a')))){
                    ans++;
                    break;
                }
            }
        }
        return ans;
    }


    //3 Step Easy to Understand Solution Without Bitmask, O(n)
    //https://leetcode.com/problems/count-words-obtained-after-adding-a-letter/discuss/1677770/3-Step-Easy-to-Understand-Solution-Without-Bitmask-O(n)
    //
    class Solution2 {
        public int wordCount(String[] startWords, String[] targetWords) {
            int n = startWords.length;
            int count = 0;
            Set<String> set = new HashSet<>();

            //1. store lexicographically sorted letters of startword in set
            for(String start: startWords){
                char[] sAr = start.toCharArray();
                Arrays.sort(sAr);
                set.add(new String(sAr));
            }
            int m = targetWords.length;
            boolean ans = false;
            for(int i = 0; i < m; i++){
                //2. sort lexicographically letters of targetword and store in new string s
                char[] tAr = targetWords[i].toCharArray();
                Arrays.sort(tAr);
                int k = tAr.length;
                String s = String.valueOf(tAr);

                ans = false;
                for(int j = 0; j < k; j++){
                    //3. make a new string by omitting one letter from word and check if it is present in set than increase count value
                    String str = s.substring(0,j) + s.substring(j+1);
                    if(set.contains(str)){
                        count++;
                        break;
                    }
                }
            }
            return count;
        }

    }

}

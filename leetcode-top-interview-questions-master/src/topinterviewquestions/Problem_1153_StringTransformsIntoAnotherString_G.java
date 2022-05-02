package topinterviewquestions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Problem_1153_StringTransformsIntoAnotherString_G {
    //https://www.cnblogs.com/Dylan-Java-NYC/p/12382223.html
    //If two string equals, then return true.
    //
    //If one character a is mapped to 2 different chars, then return false.
    //
    //The order matters, in example 1, a->c, c->e. need to perform c->e first. Otherwise, a->c, becomes ccbcc, then c->e, it becomes eedee, which is not ccdee.
    //
    //Or we need a temp char g a->g->c, first have a->g ggbcc, then c->e, ggbee. Last we have g->c, then ccbee.
    //
    //Inorder to do this, we need one unused char in str2, which makes the size of str2 unique chars smaller than 26.
    //
    //Time Complexity: O(n). n = str1.length().
    //
    //Space: O(n).
    //
    //AC Java:
    class Solution {
        public boolean canConvert(String str1, String str2) {
            if (str1.equals(str2)) {
                return true;
            }
            Map<Character, Character> map = new HashMap<>();
            for (int i = 0; i < str1.length(); i++) {
                char c1 = str1.charAt(i);
                char c2 = str2.charAt(i);
                if (map.containsKey(c1) && map.get(c1) != c2) {
                    return false;
                }
                map.put(c1, c2);
            }
            return new HashSet<Character>(map.values()).size() < 26;//Inorder to do this, we need one unused char in str2,
            // which makes the size of str2 unique chars smaller than 26.
        }
    }
}

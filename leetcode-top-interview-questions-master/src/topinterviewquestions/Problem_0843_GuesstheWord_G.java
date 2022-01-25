package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Problem_0843_GuesstheWord_G {
    interface Master {
      public int guess(String word);
  }

    class Solution {//https://leetcode.com/problems/guess-the-word/discuss/133862/Random-Guess-and-Minimax-Guess-with-Comparison
        public void findSecretWord(String[] wordlist, Master master) {
            for (int i = 0, x = 0; i < 10 && x < 6; ++i) {
                String guess = wordlist[new Random().nextInt(wordlist.length)];
                x = master.guess(guess);
                List<String> wordlist2 = new ArrayList<>();
                for (String w : wordlist)
                    if (match(guess, w) == x) {
                        wordlist2.add(w);
                    }
                wordlist = wordlist2.toArray(new String[wordlist2.size()]);
            }
        }
        public int match(String a, String b) {//how many chars match
            int matches = 0;
            for (int i = 0; i < a.length(); ++i)
                if (a.charAt(i) == b.charAt(i))
                    matches++;
            return matches;
        }
    }

}

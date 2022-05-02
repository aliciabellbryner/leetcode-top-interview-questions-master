package topinterviewquestions;

public class Problem_1859_SortingtheSentence_G {
    //https://leetcode.com/problems/sorting-the-sentence/discuss/1213443/Java-Simple-Solution-or-Runtime:-0-ms-or-O(N)/1071871
    class Solution {
        public String sortSentence(String s) {
            String[] words = s.split(" ");
            String[] bucket = new String[words.length];
            for (String word : words) {
                // Get 0th-based index from the word
                int index = Character.getNumericValue(word.charAt(word.length() - 1)) - 1;
                // Store the original word without a numeric value
                bucket[index] = word.substring(0, word.length() - 1);//不包括最后一个char
            }

            return String.join(" ", bucket);
        }
    }
}

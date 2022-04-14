package topinterviewquestions;

public class Problem_0316_RemoveDuplicateLetters_G {
    public class Solution {
        //https://leetcode.com/problems/remove-duplicate-letters/discuss/76769/Java-solution-using-Stack-with-comments/80556

        public String removeDuplicateLetters(String s) {

            int[] freq = new int[26]; // will contain number of occurences of character (i+'a')
            boolean[] visited = new boolean[26]; // will contain if character ('a' + i) is present in current result Stack
            for(char c : s.toCharArray()){  // count number of occurences of character
                freq[c-'a']++;
            }
            StringBuilder sb = new StringBuilder();; // answer stack
            int index;
            for(char c : s.toCharArray()){
                index = c - 'a';
                freq[index]--;   // decrement number of characters remaining in the string to be analysed
                if(visited[index]) { // if character is already present in stack, don't bother
                    continue;
                }
                // if current character is smaller than last character in stack which occurs later in the string again
                // it can be removed and  added later e.g stack = bc remaining string abc then a can pop b and then c
                while( (sb.length() > 0) && c < sb.charAt(sb.length()-1) && freq[sb.charAt(sb.length()-1)-'a'] != 0){
                    visited[sb.charAt(sb.length()-1) - 'a'] = false;
                    sb.deleteCharAt(sb.length()-1);
                }
                sb.append(c); // add current character and mark it as visited
                visited[index] = true;
            }

            return sb.toString();

        }
    }
}

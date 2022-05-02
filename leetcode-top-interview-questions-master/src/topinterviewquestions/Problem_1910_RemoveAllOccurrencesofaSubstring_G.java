package topinterviewquestions;

import java.util.Stack;

public class Problem_1910_RemoveAllOccurrencesofaSubstring_G {

    //https://leetcode.com/problems/remove-all-occurrences-of-a-substring/discuss/1620726/JAVA-99.11-faster
    class Solution {
        public String removeOccurrences(String s, String part) {
            int index = s.indexOf(part);
            while(index != -1) {
                s = s.substring(0, index) + s.substring(index + part.length());
                index = s.indexOf(part);
            }
            return s;
        }
    }


    //https://leetcode.com/problems/remove-all-occurrences-of-a-substring/discuss/1299275/True-O(n-+-m)-KMP/992909
    public String removeOccurrences(String s, String part) {
        int[] kmpPattern = findKmpPattern(part);

        // using stack to easily delete characters when a pattern is found.
        Stack<Character> stack = new Stack<>();

        // using index array to store 'j' (index of part) so that after character deletion we can resume
        int[] idxArr = new int[s.length() + 1];

        for(int i=0, j=0; i<s.length(); i++){
            char ch = s.charAt(i);
            stack.push(ch);

            if(ch == part.charAt(j)){
                // storing the next index of 'j'
                idxArr[stack.size()] = ++j;

                if(j == part.length()){
                    // deleting character when a pattern match is found
                    int count = part.length();
                    while(count != 0){
                        stack.pop();
                        count--;
                    }

                    // restoring the index of 'j' for finding next pattern.
                    j = stack.isEmpty() ? 0 : idxArr[stack.size()];
                }
            }

            else{
                if(j != 0){
                    i--;
                    j = kmpPattern[j-1];
                    stack.pop();
                }
                else {
                    // if the current stack is not empty and j == 0, we need to correct the previously stored index of 'j'
                    idxArr[stack.size()] = 0;
                }
            }
        }

        // Creating a string out of the left over characters in the stack
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()){
            sb.append(stack.pop());
        }

        return sb.reverse().toString();
    }

    private int[] findKmpPattern(String s){
        int[] arr = new int[s.length()];

        for(int i=1, j=0; i<s.length(); ){
            if(s.charAt(i) == s.charAt(j)){
                arr[i] = ++j;
                i++;
            }
            else if(j != 0){
                j = arr[j-1];
            }
            else{
                i++;
            }
        }

        return arr;
    }
}

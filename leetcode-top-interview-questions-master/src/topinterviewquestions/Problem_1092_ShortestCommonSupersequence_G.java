package topinterviewquestions;

import java.util.Arrays;

public class Problem_1092_ShortestCommonSupersequence_G {
    //Explain line to line based on the idea of longest common sequence. O(m*n) time and space.
    //https://leetcode.com/problems/shortest-common-supersequence/discuss/312710/C++Python-Find-the-LCS/290898
    public String shortestCommonSupersequence(String str1, String str2) {
        //Part1 fill the longest common sequence table
        int[][] dp = new int[str1.length()+1][str2.length()+1];
        for(int i = 0;i<str1.length();i++){
            for(int j = 0;j<str2.length();j++){
                if(str1.charAt(i) == str2.charAt(j)){
                    dp[i+1][j+1] = dp[i][j] + 1;
                }else {
                    dp[i+1][j+1] = Math.max(dp[i][j+1],dp[i+1][j]);
                }
            }
        }
        //Part2: use the table to get the ans
        StringBuilder sb = new StringBuilder();
        for(int i = str1.length()-1,j = str2.length()-1;i>=0 || j>=0;){
            //Case 1: either there is no char in str1 or str2, append char directly
            if(i < 0){
                sb.append(str2.charAt(j));
                j--;
                continue;
            }else if(j < 0){
                sb.append(str1.charAt(i));
                i--;
                continue;
            }
            //Case 2: if the value is the same compared with left or uppper cell, append corresponding char in str1 or str2
            // in longest common sequence, this means the char should be deleted, but in this problem, we need to append
            int val = dp[i+1][j+1];
            if(val == dp[i][j+1]){
                sb.append(str1.charAt(i));
                i--;
            }else if(val == dp[i+1][j]){
                sb.append(str2.charAt(j));
                j--;
                //Case 3 if the value is not the same compared with left or upper cell, append char and i--,j--
                //in longest common sequence, this means we find the common char
            }else {
                sb.append(str1.charAt(i));
                i--;
                j--;
            }
        }
        return sb.reverse().toString();
    }


//More concise version in Java
    //https://leetcode.com/problems/shortest-common-supersequence/discuss/312710/C++Python-Find-the-LCS/434150
    public class Solution {

        private String longestCommonSubSeq(String str1, String str2) {
            String[][] dp = new String[str1.length() + 1][str2.length() + 1];
            for (int i = 0; i < dp.length; i++)
                Arrays.fill(dp[i], "");
            for (int i = 1; i <= str1.length(); i++)
                for (int j = 1; j <= str2.length(); j++)
                    if (str1.charAt(i-1) == str2.charAt(j-1))
                        dp[i][j] = dp[i-1][j-1] + str1.charAt(i-1);
                    else
                        dp[i][j] = dp[i-1][j].length()>dp[i][j-1].length() ?  dp[i-1][j] : dp[i][j-1];
            return dp[str1.length()][str2.length()];
        }

        public String shortestCommonSupersequence(String str1, String str2) {
            char[] lcs = longestCommonSubSeq(str1, str2).toCharArray();
            int i = 0, j = 0;
            StringBuilder sb = new StringBuilder();
            for(char c : lcs) {
                while (str1.charAt(i) != c)
                    sb.append(str1.charAt(i++));
                while (str2.charAt(j) != c)
                    sb.append(str2.charAt(j++));
                sb.append(c);    i++;    j++;
            }
            sb.append(str1.substring(i)).append(str2.substring(j));
            return sb.toString();
        }

    }

}

package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

public class Problem_0722_RemoveComments {
    class Solution {
        //https://leetcode.com/problems/remove-comments/discuss/109197/One-pass-solution-in-Java
        //If we see '//' we stop reading the current line, and add whatever characters we have seen to the result.
        //If we see '/*' then we start the multiline comment mode and we keep on ignoring characters until we see '*/'.
        //If the current character is neither of the above two and the multiline comment mode is off, then we add that character to the current line.
        public List<String> removeComments(String[] source) {
            List<String> res = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            boolean isComment = false;//表示是否是comment，如果遇到//或者/*就把它设为true，遇到*/就要把它设为false
            for (String s : source) {
                int N = s.length();
                for (int i = 0; i < N; i++) {
                    if (isComment) {
                        if (i < N - 1 && s.charAt(i) == '*' && s.charAt(i + 1) == '/') {
                            isComment = false;
                            i++;        //skip '/' on next iteration of i
                        }
                    } else {
                        if (i < N - 1 && s.charAt(i) == '/' && s.charAt(i + 1) == '/') {
                            break;      //ignore remaining characters on line s
                        }
                        else if (i < N - 1 && s.charAt(i) == '/' && s.charAt(i + 1) == '*') {
                            isComment = true;
                            i++;           //skip '*' on next iteration of i
                        }
                        else {
                            sb.append(s.charAt(i));     //not a comment
                        }
                    }
                }
                if (!isComment && sb.length() > 0) {
                    res.add(sb.toString());
                    sb.setLength(0);   //reset for next line of source code
                }
            }
            return res;
        }
    }
}

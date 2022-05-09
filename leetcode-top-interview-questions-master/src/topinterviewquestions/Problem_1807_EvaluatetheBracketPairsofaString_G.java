package topinterviewquestions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Problem_1807_EvaluatetheBracketPairsofaString_G {
    //https://leetcode.com/problems/evaluate-the-bracket-pairs-of-a-string/discuss/1130600/Python-Solution/889921
    public String evaluate(String s, List<List<String>> knowledge) {
        Map<String, String> map = new HashMap<>();

        for(List<String> list : knowledge)
            map.put(list.get(0), list.get(1));//build the map

        StringBuilder sb = new StringBuilder();//to avoid String concatenation
        int i = 0;
        while(i < s.length()){
            while(i < s.length() && s.charAt(i) != '(')//add all characters until '('
                sb.append(s.charAt(i++));

            if(i == s.length()) return sb.toString();//no bracket pair

            int start = i + 1;//store the index after '('
            while(s.charAt(i) != ')') i++;//find the closing bracket ')'
            String key = s.substring(start, i++);//make key
            sb.append(map.getOrDefault(key, "?"));//add value to res
        }
        return sb.toString();
    }
}

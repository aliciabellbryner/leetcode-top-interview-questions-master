package topinterviewquestions;

import java.util.*;

public class Problem_0726_NumberofAtoms_G {

    //https://leetcode.com/problems/number-of-atoms/discuss/109345/Java-Solution-using-Stack-and-Map/509064
    public String countOfAtoms(String formula) {
        if(formula == null || formula.length() == 0) return "";
        Stack<HashMap<String,Integer>> stack = new Stack<>();
        HashMap<String,Integer> currMap = new HashMap<String,Integer>();
        stack.push(currMap);
        for(int i = 0;i<formula.length();i++){
            //Case 1: put everthing after "(" in new HashMap, and put in stack
            if(formula.charAt(i) == '('){
                currMap = new HashMap<>();
                stack.push(currMap);
                //Case 2: multiple and then merge top 2 object in stack
            }else if(formula.charAt(i) == ')'){
                HashMap<String,Integer> tempMap = stack.pop();
                //count structure
                int count = 0;
                if(i+1 == formula.length() || !Character.isDigit(formula.charAt(i+1))){
                    count = 1;
                }else{
                    while(i+1<formula.length() && Character.isDigit(formula.charAt(i+1))){
                        count = count * 10 + (formula.charAt(i+1) - '0');
                        i++;
                    }
                }
                merge(stack.peek(),tempMap,count);
                //update the pointer
                currMap = stack.peek();
                //Case 3: put atoms in the top object in stack, update count
            }else {
                StringBuilder sb = new StringBuilder();
                sb.append(formula.charAt(i));
                while(i+1<formula.length() && Character.isLowerCase(formula.charAt(i+1))){
                    sb.append(formula.charAt(i+1));
                    i++;
                }
                //count structure
                int count = 0;
                //case1: no digit follows when meet with UpperCase
                if(i+1 == formula.length() || !Character.isDigit(formula.charAt(i+1))){
                    count = 1;
                }else{
                    //case2: meet with digits
                    while(i+1<formula.length() && Character.isDigit(formula.charAt(i+1))){
                        count = count * 10 + (formula.charAt(i+1) - '0');
                        i++;
                    }
                }
                String atom = sb.toString();
                int number = currMap.getOrDefault(atom,0);
                currMap.put(atom,number+count);

            }
        }
        TreeMap<String, Integer> treeMap = new TreeMap<>(stack.peek());//store the element and appearance time in sorted sequence
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,Integer> entry: treeMap.entrySet()){
            sb.append(entry.getKey());
            if(entry.getValue() != 1){
                sb.append(entry.getValue());
            }
        }
        return sb.toString();
    }
    public void merge(HashMap<String,Integer> map,HashMap<String,Integer> tempMap, int count){
        for(Map.Entry<String,Integer> entry: tempMap.entrySet()){
            String key = entry.getKey();
            int val = entry.getValue();
            int number = map.getOrDefault(key,0);
            number += val * count;
            map.put(key,number);
        }
    }


    //https://leetcode.com/problems/number-of-atoms/discuss/109346/Java-solution-Map-%2B-Recursion
    class Solution {
        public String countOfAtoms(String formula) {
            Map<String, Integer> map = countHelper(formula);

            List<String> atoms = new ArrayList<>(map.keySet());
            Collections.sort(atoms);
            StringBuilder sb = new StringBuilder();
            for (String atom : atoms) {
                sb.append(atom + (map.get(atom) > 1 ? map.get(atom) : ""));
            }

            return sb.toString();
        }

        private Map<String, Integer> countHelper(String formula) {
            Map<String, Integer> map = new HashMap<>();
            if (formula.isEmpty()) return map;

            int i = 0;
            while (i < formula.length()) {
                if (formula.charAt(i) == '(') {
                    int count = 0, j = i;
                    for (; j < formula.length(); j++) {
                        if (formula.charAt(j) == '(') count++;
                        else if (formula.charAt(j) == ')') count--;
                        if (count == 0) break;
                    }
                    Map<String, Integer> subMap = countHelper(formula.substring(i + 1, j));

                    j++;
                    int n = 1, k = j;
                    while (k < formula.length() && Character.isDigit(formula.charAt(k))) k++;
                    if (k > j) {
                        n = Integer.parseInt(formula.substring(j, k));
                    }

                    for (String atom : subMap.keySet()) {
                        map.put(atom, subMap.get(atom) * n + map.getOrDefault(atom, 0));
                    }

                    i = k;
                } else {
                    int j = i + 1;
                    while (j < formula.length() && formula.charAt(j) >= 'a' && formula.charAt(j) <= 'z') j++;

                    int n = 1, k = j;
                    while (k < formula.length() && Character.isDigit(formula.charAt(k))) k++;
                    if (k > j) {
                        n = Integer.parseInt(formula.substring(j, k));
                    }

                    String atom = formula.substring(i, j);
                    map.put(atom, n + map.getOrDefault(atom, 0));

                    i = k;
                }
            }

            return map;
        }
    }
}

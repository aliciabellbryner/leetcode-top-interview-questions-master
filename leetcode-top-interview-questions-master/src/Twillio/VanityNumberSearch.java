package Twillio;

import java.util.*;

public class VanityNumberSearch {
    public static List<String> vanity(List<String> codes, List<String> numbers) {
        if (codes == null || numbers == null || codes.size() == 0 || numbers.size() == 0) {
            return new ArrayList<>();
        }
        Set<String> set = new HashSet<>();
        List<String> res = new ArrayList<>();
        Map<Integer, String> map = new HashMap<Integer, String>();
        Set<String> sequences = new HashSet<>();
        map.put(2, "ABC");
        map.put(3, "def");
        map.put(4, "ghi");
        map.put(5, "jkl");
        map.put(6, "mno");
        map.put(7, "pqrs");
        map.put(8, "tuv");
        map.put(9, "wxyz");

        for (int i = 0; i < codes.size(); i++) {
            StringBuilder sb = new StringBuilder();
            for (Character c : codes.get(i).toCharArray()) {
                Character ch = Character.toUpperCase(c);
                for (Map.Entry<Integer, String> entry : map.entrySet()) {
                    if (entry.getValue().contains(ch.toString())) {
                        sb.append(entry.getKey());
                        break;
                    }
                }
            }
            sequences.add(sb.toString());
        }

        for (String number : numbers) {
            for (String code : sequences) {
                if (number.contains(code)) {
                    set.add(number);
                }
            }

        }

        res.addAll(set);
        Collections.sort(res);
        return res;
    }



    public static List<String> vanity2(List<String> codes, List<String> numbers) {
        Set<String> opt = new HashSet<>();
        List<String> returnList = new ArrayList<>();
        Map<Integer, String> map = new HashMap<Integer, String>();
        Set<String> numberSequences = new HashSet<>();
        map.put(2, "abc");
        map.put(3, "def");
        map.put(4, "ghi");
        map.put(5, "jkl");
        map.put(6, "mno");
        map.put(7, "pqrs");
        map.put(8, "tuv");
        map.put(9, "wxyz");

        for (String code : codes) {
            StringBuilder numberSequence = new StringBuilder();
            for (Character c : code.toCharArray()) {
                Character ch = Character.toLowerCase(c);
                for (Map.Entry<Integer, String> entry : map.entrySet()) {
                    if (entry.getValue().contains(ch.toString())) {
                        numberSequence.append(entry.getKey());
                        break;
                    }
                }
            }
            numberSequences.add(numberSequence.toString());
        }

        for (String number : numbers) {
            for (String code : numberSequences) {
                if (number.contains(code)) {
                    opt.add(number);
                }
            }

        }

        returnList.addAll(opt);
        Collections.sort(returnList);
        return returnList;
    }


    public static void main(String[] args) {
        List<String> codes = Arrays.asList("TWLO", "CODE", "HTCH");
        List<String> numbers = Arrays.asList("+14157088956");
        System.out.println(vanity(codes, numbers));
    }
}

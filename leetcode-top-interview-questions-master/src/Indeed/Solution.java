package Indeed;

import java.util.*;

public class Solution {
    static Map<Integer, Map<String, Integer>> map = new HashMap<Integer, Map<String, Integer>>();
    public static void storeDocument(final String document, final int documentNumber) {
        // TODO implement
        String[] sa = document.split(System.lineSeparator());
        int N = Integer.valueOf(sa[0]);
        for (int i = 1; i < sa.length; i++) {
            map.put(i - 1, new TreeMap<>());
            for (String s : sa[i].split(" ")) {
                map.get(i-1).putIfAbsent(s, 0);
                map.get(i-1).put(s, map.get(i-1).get(s) + 1);
            }
        }
    }

    public static String performSearch(final String search) {
        // TODO implement
        StringBuilder res = new StringBuilder();
        TreeMap<Integer, Integer> resmap = new TreeMap<>();
        String[] seaA = search.split(System.lineSeparator());
        int M = Integer.valueOf(seaA[0]);
        for (int i = 1; i < seaA.length; i++) {
            Map<String, Integer> treeMap = map.get(i-1);
            int cnt = 0;
            for (String s : seaA[i-1].split(" ")) {
                if (treeMap.containsKey(s)) {
                    cnt++;
                    treeMap.remove(s);
                }
            }
            if (cnt > 0) {
                resmap.put(i-1, cnt);
            }

        }
        List<Map.Entry<Integer, Integer>> list = new ArrayList<Map.Entry<Integer, Integer>>();
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(
                    Map.Entry<Integer, Integer> o1,
                    Map.Entry<Integer, Integer> o2) {
                if (o1.getValue() == o2.getValue()) {
                    return o2.getKey() - o1.getKey();
                } else {
                    return o2.getValue() - o1.getValue();
                }
            }
        });
        for (int i = 0; i < Math.min(10, list.size()); i++) {
            res.append(list.get(i).getKey());
            res.append("\n");
        }
        return res.toString();
    }

    public static void main(String[] args) {
        List<Integer> list  = new ArrayList<>();
        list.add(1);
        list.add(8);
        list.add(5);
        System.out.println(list);

    }
}

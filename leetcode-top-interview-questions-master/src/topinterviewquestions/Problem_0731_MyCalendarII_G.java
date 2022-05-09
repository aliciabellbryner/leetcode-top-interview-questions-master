package topinterviewquestions;

import java.util.Map;
import java.util.TreeMap;

public class Problem_0731_MyCalendarII_G {
    //https://leetcode.com/problems/my-calendar-ii/discuss/109550/Simple-AC-by-TreeMap
    //思路就是先把start的位置加一，end的位置减一，然后把map所有的key value set都累加，如果累加到大于2的位置说明有三个重合了，返回false，如果没有就返回true
    class MyCalendarTwo {

        private TreeMap<Integer, Integer> map;

        public MyCalendarTwo() {
            map = new TreeMap<>();
        }

        public boolean book(int start, int end) {
            map.put(start, map.getOrDefault(start, 0) + 1);
            map.put(end, map.getOrDefault(end, 0) - 1);
            int count = 0;
            for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
                count += entry.getValue();
                if(count > 2) {
                    map.put(start, map.get(start) - 1);
                    if(map.get(start) == 0) {
                        map.remove(start);
                    }
                    map.put(end, map.get(end) + 1);
                    if(map.get(end) == 0) {
                        map.remove(end);
                    }
                    return false;
                }
            }
            return true;
        }
    }
}

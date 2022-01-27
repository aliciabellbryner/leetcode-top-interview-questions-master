package topinterviewquestions;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class Problem_0460_LFUCache_G {
    //https://leetcode.com/problems/lfu-cache/discuss/94521/JAVA-O(1)-very-easy-solution-using-3-HashMaps-and-LinkedHashSet/181685
    class LFUCache {
        HashMap<Integer, Integer> key2Vals;
        HashMap<Integer, Integer> key2Counts;
        HashMap<Integer, LinkedHashSet<Integer>> count2LRUKeys;//其实就是hashset组成的list，可以通过.get(index)得到其中的hashset
        int capacity;
        int min;

        public LFUCache(int capacity) {
            key2Vals = new HashMap<>();
            key2Counts = new HashMap<>();
            count2LRUKeys = new HashMap<>();
            this.capacity = capacity;
            min = 0;
        }

        public int get(int key) {
            if(!key2Vals.containsKey(key))
                return -1;

            update(key);
            return key2Vals.get(key);
        }

        private void update (int key) {
            int cnt = key2Counts.get(key);
            key2Counts.put(key, cnt + 1);
            count2LRUKeys.get(cnt).remove(key);//需要把cnt加一前的数值对应的key删掉

            if(cnt == min && count2LRUKeys.get(cnt).size() == 0) {
                min++;
            }

            addToCount2LRUKeys(cnt + 1, key);
        }

        private void addToCount2LRUKeys(int cnt, int key) {
            if(!count2LRUKeys.containsKey(cnt))
                count2LRUKeys.put(cnt, new LinkedHashSet<>());

            count2LRUKeys.get(cnt).add(key);
        }

        public void put(int key, int value) {
            if (capacity <= 0)
                return;

            if (key2Vals.containsKey(key)) {
                key2Vals.put(key, value);
                update(key);
                return;
            }

            if (key2Vals.size() >= capacity)
                evict();

            key2Vals.put(key, value);
            key2Counts.put(key, 1);
            addToCount2LRUKeys(1, key);
            min = 1;
        }

        private void evict () {
            int key = count2LRUKeys.get(min).iterator().next();
            count2LRUKeys.get(min).remove(key);
            key2Vals.remove(key);
            key2Counts.remove(key);
        }
    }
}

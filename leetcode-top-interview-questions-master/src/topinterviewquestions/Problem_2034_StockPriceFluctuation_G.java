package topinterviewquestions;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class Problem_2034_StockPriceFluctuation_G {
    //https://leetcode.com/problems/stock-price-fluctuation/discuss/1513413/JavaC%2B%2BPython-Strightforward-Solutions
    //思路是用treemap，这样的话可以key自己排序，用lastKey(), firstKey()就可以调用最大最小的key
    //Time O(logn) for each function as TreeMap has complexity of O(logN) for insertion and lookup N is the time you use update
    //Space O(n)
    class StockPrice {
        TreeMap<Integer, Integer> time2price = new TreeMap<>();//key: timestamp, value: latest price
        TreeMap<Integer, Set<Integer>> price2times = new TreeMap<>();//key: latest price, value: all the timestamps that has that same price

        public void update(int timestamp, int price) {
            if (time2price.containsKey(timestamp)) {
                int prevPrice = time2price.get(timestamp);
                Set<Integer> book = price2times.get(prevPrice);
                book.remove(timestamp);
                if (book.isEmpty()) {
                    price2times.remove(prevPrice);
                }
            }
            price2times.putIfAbsent(price, new HashSet<>());
            price2times.get(price).add(timestamp);
            time2price.put(timestamp, price);
        }

        public int current() {
            return time2price.lastEntry().getValue();
        }

        public int maximum() {
            return price2times.lastKey();
        }

        public int minimum() {
            return price2times.firstKey();
        }
    }
}

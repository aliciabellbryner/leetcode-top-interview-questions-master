package topinterviewquestions;

import java.util.Map;
import java.util.TreeMap;

public class Problem_0954_ArrayofDoubledPairs_G {
    //https://leetcode.com/problems/array-of-doubled-pairs/discuss/203183/JavaC%2B%2BPython-Match-from-the-Smallest-or-Biggest-100
    // time O(NlogK): N是arr.length, K是arr中不一样的int的种类数，它代表treemap的复杂度
    //space O(K)
    public boolean canReorderDoubled(int[] arr) {
        Map<Integer, Integer> map = new TreeMap<>();
        for (int i : arr)
            map.put(i, map.getOrDefault(i, 0) + 1);
        for (int x : map.keySet()) {
            if (map.get(x) == 0) {
                continue;
            }
            int want = x < 0 ? x / 2 : x * 2;//因为如果是负数的话，treemap的keySet()从小到大排列，比如-8它对应的就应该是-4，而不是-16
            if (x < 0 && x % 2 != 0 || map.get(x) > map.getOrDefault(want, 0)) {
                return false;
                //x < 0 && x % 2 != 0: 如果x小于0，同时它又是奇数，那么它的下一个数就不是个整数，那必定不满足条件，return false
                //map.get(x) > map.getOrDefault(want, 0)：当x大于0，那则要满足小的数对应的数量要大于等于大的数对应的数量
            }
            map.put(want, map.get(want) - map.get(x));
        }
        return true;
        ////0的条件也满足，因为比如arr是[0,0,0],那么map就是【0，3】，下面for走完变成[0,0],然后return true
    }
}

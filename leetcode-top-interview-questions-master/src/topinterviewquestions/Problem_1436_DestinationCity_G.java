package topinterviewquestions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Problem_1436_DestinationCity_G {
    //方法一：哈希表
    //根据终点站的定义，终点站不会出现在 cityA中，因为存在从cityA出发的线路，所以终点站只会出现在cityB中。据此，我们可以遍历 cityB返回不在 cityA中的城市，即为答案。
    //代码实现时，可以先将所有 cityA
    //  存于一哈希表中，然后遍历cityB并查询 cityB是否在哈希表中。
    //
    //作者：LeetCode-Solution
    //链接：https://leetcode-cn.com/problems/destination-city/solution/lu-xing-zhong-dian-zhan-by-leetcode-solu-pscd/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution {
        public String destCity(List<List<String>> paths) {
            Set<String> citiesA = new HashSet<String>();
            for (List<String> path : paths) {
                citiesA.add(path.get(0));
            }
            for (List<String> path : paths) {
                if (!citiesA.contains(path.get(1))) {
                    return path.get(1);
                }
            }
            return "";
        }
    }

}

package topinterviewquestions;

import java.util.HashMap;
import java.util.Map;

public class Problem_0947_MostStonesRemovedwithSameRoworColumn_G {
    //📖 文字解析
    //把二维坐标平面上的石头想象成图的顶点，如果两个石头横坐标相同、或者纵坐标相同，在它们之间形成一条边。
    //
    //
    //
    //根据可以移除石头的规则：如果一块石头的 同行或者同列 上有其他石头存在，那么就可以移除这块石头。可以发现：一定可以把一个连通图里的所有顶点根据这个规则删到只剩下一个顶点。
    //
    //为什么这么说呢？既然这些顶点在一个连通图里，可以通过遍历的方式（深度优先遍历或者广度优先遍历）遍历到这个连通图的所有顶点。那么就可以按照遍历的方式 逆向 移除石头，最后只剩下一块石头。所以：最多可以移除的石头的个数 = 所有石头的个数 - 连通分量的个数。
    //
    //题目没有让我们给出具体移除石头的方案，可以考虑使用并查集。
    //
    //方法：并查集
    //删到最后，留在图中的顶点一定位于不同的行和不同的列。因此，并查集里的元素是 描述「横坐标」和「纵坐标」的数值。因此我们需要遍历数组 stones，将每个 stone 的横坐标和纵坐标在并查集中进行合并。理解合并的语义十分重要。
    //
    //「合并」的语义
    //「合并」的语义是：所有横坐标为 x 的石头和所有纵坐标为 y 的石头都属于同一个连通分量。
    //
    //并查集里如何区分横纵坐标
    //然而会遇到这样一个问题：石头的位置是「有序数对（二维）」，并查集的底层是「一维数组」，我们在并查集里应该如何区分横纵坐标呢？
    //
    //例如：如果一块石头的坐标为 [3, 3] ，那么所有横坐标为 3 的石头和所有纵坐标为 3 的石头都在一个连通分量中，但是我们需要在并查集里区分「横坐标」和「纵坐标」，它们在并查集里不能相等，根据题目的提示 0 <= x_i, y_i <= 10^40<=x
    //i
    //​
    // ,y
    //i
    //​
    // <=10
    //4
    // ，可以把其中一个坐标 映射 到另一个与 [0, 10000] 不重合的区间，可以的做法是把横坐标全部减去 1000110001 或者全部加上 1000110001，或者按位取反（[0, 10000] 里的 3232 位整数，最高位变成 11 以后，一定不在 [0, 10000] 里）。
    //
    //在并查集里我们需要维护连通分量的个数，新创建顶点的时候连通分量加 11；合并不在同一个连通分量中的两个并查集的时候，连通分量减 11。
    //
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/most-stones-removed-with-same-row-or-column/solution/947-yi-chu-zui-duo-de-tong-xing-huo-tong-ezha/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。


    public class Solution {

        public int removeStones(int[][] stones) {
            UnionFind unionFind = new UnionFind();

            for (int[] stone : stones) {//如果一块石头的坐标为 [3, 3] ，那么所有横坐标为 3 的石头
                // 和所有纵坐标为 3 的石头都在一个连通分量中，但是我们需要在并查集里区分「横坐标」和「纵坐标」，它们在并查集里不能相等
                // 下面这三种写法任选其一
                // unionFind.union(~stone[0], stone[1]);
                // unionFind.union(stone[0] - 10001, stone[1]);
                unionFind.union(stone[0] + 10001, stone[1]);
            }
            return stones.length - unionFind.getCount();
        }

        private class UnionFind {

            private Map<Integer, Integer> parent;
            private int count;

            public UnionFind() {
                this.parent = new HashMap<>();
                this.count = 0;
            }

            public int getCount() {
                return count;
            }

            public int find(int x) {
                if (!parent.containsKey(x)) {
                    parent.put(x, x);
                    // 并查集集中新加入一个结点，结点的父亲结点是它自己，所以连通分量的总数 +1
                    count++;
                }

                if (x != parent.get(x)) {
                    parent.put(x, find(parent.get(x)));
                }
                return parent.get(x);
            }

            public void union(int x, int y) {
                int rootX = find(x);
                int rootY = find(y);
                if (rootX == rootY) {
                    return;
                }

                parent.put(rootX, rootY);
                // 两个连通分量合并成为一个，连通分量的总数 -1
                count--;
            }
        }
    }
}

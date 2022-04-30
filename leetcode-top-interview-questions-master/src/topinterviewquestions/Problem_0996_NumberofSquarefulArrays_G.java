package topinterviewquestions;

import java.util.*;

public class Problem_0996_NumberofSquarefulArrays_G {

    //https://leetcode.com/problems/number-of-squareful-arrays/discuss/238562/C++Python-Backtracking/236559
    class Solution {
        Map<Integer, Integer> cntMap = new HashMap<>();
        Map<Integer, Set<Integer>> squareMap = new HashMap<>();
        int cnt = 0;
        public int numSquarefulPerms(int[] A) {
            for (int num : A) {
                if (!cntMap.containsKey(num)) {
                    cntMap.put(num, 1);
                    squareMap.put(num, new HashSet<>());
                } else {
                    cntMap.put(num, cntMap.get(num) + 1);
                }
            }
            for (int num1 : cntMap.keySet()) {
                for (int num2 : cntMap.keySet()) {
                    double c = Math.sqrt(num1 + num2);
                    if (c == Math.floor(c)) {
                        squareMap.get(num1).add(num2);
                        squareMap.get(num2).add(num1);
                    }
                }
            }
            for (int num: cntMap.keySet()) {
                countPerm(num, A.length - 1);
            }
            return cnt;
        }

        private void countPerm(int num, int left) {
            cntMap.put(num, cntMap.get(num) - 1);
            if (left == 0) { cnt++; }
            else {
                for (int next : squareMap.get(num)) {
                    if (cntMap.get(next) != 0) {
                        countPerm(next, left - 1);
                    }
                }
            }
            cntMap.put(num, cntMap.get(num) + 1);
        }
    }


    //方法一：回溯
    //思路
    //
    //构造一张图，包含所有的边 ii 到 jj ，如果满足 A[i] + A[j]A[i]+A[j] 是一个完全平方数。我们的目标就是求这张图的所有哈密顿路径，即经过图中所有点仅一次的路径。
    //
    //算法
    //
    //我们使用 count 记录对于每一种值还有多少个节点等待被访问，与一个变量 todo 记录还剩多少个节点等待被访问。
    //
    //对于每一个节点，我们可以访问它的所有邻居节点（从数值的角度来看，从而大大减少复杂度）。
    //
    //
    //更多细节请看行内注释。
    //
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/number-of-squareful-arrays/solution/zheng-fang-xing-shu-zu-de-shu-mu-by-leetcode/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution2 {
        Map<Integer, Integer> count;//count 记录对于每一种值还有多少个节点等待被访问
        Map<Integer, List<Integer>> graph;
        public int numSquarefulPerms(int[] A) {
            int N = A.length;
            count = new HashMap();
            graph = new HashMap();

            // count.get(v) : 数组 A 中值为 v 的节点数量
            for (int x: A)
                count.put(x, count.getOrDefault(x, 0) + 1);

            // graph.get(v) : 在 A 中的值 w 满足 v + w 是完全平方数
            //                (ie., "vw" is an edge)
            for (int x: count.keySet())
                graph.put(x, new ArrayList());

            for (int x: count.keySet())
                for (int y: count.keySet()) {
                    int r = (int) (Math.sqrt(x + y) + 0.5);
                    if (r * r == x + y)
                        graph.get(x).add(y);
                }

            // 增加从 x 开始的可行路径数量
            int ans = 0;
            for (int x: count.keySet())
                ans += dfs(x, N - 1);
            return ans;
        }

        public int dfs(int x, int todo) {//todo 记录还剩多少个节点等待被访问。
            count.put(x, count.get(x) - 1);
            int ans = 1;
            if (todo != 0) {
                ans = 0;
                for (int y: graph.get(x)) if (count.get(y) != 0) {
                    ans += dfs(y, todo - 1);
                }
            }
            count.put(x, count.get(x) + 1);
            return ans;
        }
    }

    //方法二：动态规划
    //思路
    //
    //与 方法一 中相似，构造一样的图。因为节点的数量非常少，所以可以使用掩码标记所有已经过点的方式来进行动态规划。
    //
    //算法
    //
    //我们用同样的方式构造与 方法一 中一样的图。
    //
    //现在，我们令 dfs(node, visited) 等于从 node 节点出发访问剩余的节点的可行方法数。这里，visited 是一个掩码：(visited >> i) & 1 为真，当且仅当第 i 个节点已经被访问过了。
    //
    //这样计算之后，对于 A 中拥有相同值的节点我们会重复计算。考虑这个因素，对于 A 中的值 x，如果 A 中包含 k 个值为 x 的节点，我们令最终答案除以 k!。
    //
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/number-of-squareful-arrays/solution/zheng-fang-xing-shu-zu-de-shu-mu-by-leetcode/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution3 {
        int N;
        Map<Integer, List<Integer>> graph;
        Integer[][] memo;

        public int numSquarefulPerms(int[] A) {
            N = A.length;
            graph = new HashMap();
            memo = new Integer[N][1 << N];

            for (int i = 0; i < N; ++i)
                graph.put(i, new ArrayList());

            for (int i = 0; i < N; ++i)
                for (int j = i+1; j < N; ++j) {
                    int r = (int) (Math.sqrt(A[i] + A[j]) + 0.5);
                    if (r * r == A[i] + A[j]) {
                        graph.get(i).add(j);
                        graph.get(j).add(i);
                    }
                }


            int[] factorial = new int[20];
            factorial[0] = 1;
            for (int i = 1; i < 20; ++i)
                factorial[i] = i * factorial[i-1];

            int ans = 0;
            for (int i = 0; i < N; ++i)
                ans += dfs(i, 1 << i);

            Map<Integer, Integer> count = new HashMap();
            for (int x: A)
                count.put(x, count.getOrDefault(x, 0) + 1);
            for (int v: count.values())
                ans /= factorial[v];

            return ans;
        }

        public int dfs(int node, int visited) {
            if (visited == (1 << N) - 1)
                return 1;
            if (memo[node][visited] != null)
                return memo[node][visited];

            int ans = 0;
            for (int nei: graph.get(node))
                if (((visited >> nei) & 1) == 0)
                    ans += dfs(nei, visited | (1 << nei));
            memo[node][visited] = ans;
            return ans;
        }
    }



}

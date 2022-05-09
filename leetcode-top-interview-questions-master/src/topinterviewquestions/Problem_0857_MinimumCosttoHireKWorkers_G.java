package topinterviewquestions;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Problem_0857_MinimumCosttoHireKWorkers_G {

    //https://leetcode-cn.com/problems/minimum-cost-to-hire-k-workers/comments/
    //每个工人都有自己期望的价性比，雇佣K个工人的时候要满足每个人的实际价性比不低于他的期望，即需要按照K个工人中的最高期望价性比给这K个人开工资。
    //
    //使用了一个大根堆，来获取K个工人的最大的价性比，作为K个工人的价性比，使用qsum保存K个工人的质量和。要给他们付的工资就是qsum * 最大性价比。
    class Solution {
        public double mincostToHireWorkers(int[] q, int[] w, int K) {
            double[][] workers = new double[q.length][2];
            for (int i = 0; i < q.length; ++i){//保存工人价性比
                workers[i] = new double[]{(double)(w[i]) / q[i], (double)q[i]};
            }
            Arrays.sort(workers, (a, b) -> Double.compare(a[0], b[0]));
            double res = Double.MAX_VALUE;
            double qsum = 0.0; //qsum保存K个工人的质量和
            PriorityQueue<Double> pq = new PriorityQueue<>();
            for (double[] worker: workers) {
                qsum += worker[1];
                pq.add(-worker[1]);
                if (pq.size() > K) {
                    qsum += pq.poll();
                }
                if (pq.size() == K) {
                    res = Math.min(res, qsum * worker[0]);
                }
            }
            return res;
        }

    }


    //https://leetcode-cn.com/problems/minimum-cost-to-hire-k-workers/solution/gu-yong-k-ming-gong-ren-de-zui-di-cheng-ben-by-lee/
    //方法二：堆（优先队列）
    //在方法一中，我们枚举了一名工人，并对剩下的工人计算对应的工资，并选出 K - 1 个工资最低的进行累加。事实上，我们可以定义一个“价值”，表示工人最低期望工资与工作质量之比。例如某位工人的最低期望工资为 100，工作质量为 20，那么他的价值为 100 / 20 = 5.0。
    //
    //可以发现，如果一名工人的价值为 R，当他恰好拿到最低期望工资时，如果所有价值高于 R 的工人都无法拿到最低期望工资，而所有价值低于 R 的工人都拿得比最低期望工资多。因此，我们可以按照这些工人的价值，对他们进行升序排序。排序后的第 i 名工人可以在它之前任选 K - 1 名工人，并计算对应的工资总和，为 R * sum(quality[c1] + quality[c2] + ... + quality[c{k-1}] + quality[i])，也就是说，我们需要在前 i 名工人中选择 K 个工作质量最低的。我们可以使用一个大根堆来实时维护 K 个最小值。
    //
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/minimum-cost-to-hire-k-workers/solution/gu-yong-k-ming-gong-ren-de-zui-di-cheng-ben-by-lee/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    //时间复杂度：O(N \log N)O(NlogN)。
    //
    //空间复杂度：O(N)O(N)。
    class Solution2 {
        public double mincostToHireWorkers(int[] quality, int[] wage, int K) {
            int N = quality.length;
            Worker[] workers = new Worker[N];
            for (int i = 0; i < N; ++i)
                workers[i] = new Worker(quality[i], wage[i]);
            Arrays.sort(workers);

            double ans = 1e9;
            int sumq = 0;
            PriorityQueue<Integer> pool = new PriorityQueue();
            for (Worker worker: workers) {
                pool.offer(-worker.quality);
                sumq += worker.quality;
                if (pool.size() > K)
                    sumq += pool.poll();
                if (pool.size() == K)
                    ans = Math.min(ans, sumq * worker.ratio());
            }

            return ans;
        }
    }

    class Worker implements Comparable<Worker> {
        public int quality, wage;
        public Worker(int q, int w) {
            quality = q;
            wage = w;
        }

        public double ratio() {
            return (double) wage / quality;
        }

        public int compareTo(Worker other) {
            return Double.compare(ratio(), other.ratio());
        }
    }

}

package topinterviewquestions;

import java.util.Arrays;

public class Problem_0853_CarFleet_G {
    //https://leetcode-cn.com/problems/car-fleet/solution/che-dui-by-leetcode/
    //方法一：排序
    //分析
    //
    //我们首先对这些车辆按照它们的起始位置降序排序，并且用 (target - position) / speed 计算出每辆车在不受其余车的影响时，行驶到终点需要的时间。对于相邻的两辆车 S 和 F，F 的起始位置大于 S，如果 S 行驶到终点需要的时间小于等于 F，那么 S 一定会在终点前追上 F 并形成车队。这是因为在追上 F 之前，S 的行驶速度并不会减小，而 F 却有可能因为追上前面的车辆而速度减小，因此 S 总能在终点前追上 F。
    //
    //算法
    //
    //将车辆按照起始位置降序排序后，我们顺序扫描这些车辆。如果相邻的两辆车，前者比后者行驶到终点需要的时间短，那么后者永远追不上前者，即从后者开始的若干辆车辆会组成一个新的车队；如果前者不比后者行驶到终点需要的时间短，那么后者可以在终点前追上前者，并和前者形成车队。此时我们将后者到达终点的时间置为前者到达终点的时间。
    //
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/car-fleet/solution/che-dui-by-leetcode/
    //时间复杂度：O(N \log N)O(NlogN)，即为排序的时间复杂度。
    //
    //空间复杂度：O(N)O(N)，存储车辆到达终点需要的时间。
    class Solution {
        public int carFleet(int target, int[] position, int[] speed) {
            int N = position.length;
            Car[] cars = new Car[N];
            for (int i = 0; i < N; ++i)
                cars[i] = new Car(position[i], (double) (target - position[i]) / speed[i]);
            Arrays.sort(cars, (a, b) -> Integer.compare(a.position, b.position));

            int ans = 0, t = N;
            while (--t > 0) {
                if (cars[t].time < cars[t-1].time) ans++; //if cars[t] arrives sooner, it can't be caught
                else cars[t-1] = cars[t]; //else, cars[t-1] arrives at same time as cars[t]
            }

            return ans + (t == 0 ? 1 : 0); //lone car is fleet (if it exists)
        }
    }

    class Car {
        int position;
        double time;
        Car(int p, double t) {
            position = p;
            time = t;
        }
    }


}

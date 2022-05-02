package topinterviewquestions;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Problem_1226_TheDiningPhilosophers_G {
    //https://leetcode-cn.com/problems/the-dining-philosophers/solution/zhe-xue-jia-jin-can-by-skyshine94-7blq/
    class DiningPhilosophers {
        //定义Lock
        private ReentrantLock lock = new ReentrantLock();
        //定义Condition数组
        private Condition[] conditions = new Condition[5];
        //定义叉子是否被占用的标记
        private boolean[] forks = new boolean[5];

        public DiningPhilosophers() {
            //创建Condition对象
            for (int i = 0; i < 5; i++) {
                conditions[i] = lock.newCondition();
            }
        }

        // call the run() method of any runnable to execute its code
        public void wantsToEat(int philosopher,
                               Runnable pickLeftFork,
                               Runnable pickRightFork,
                               Runnable eat,
                               Runnable putLeftFork,
                               Runnable putRightFork) throws InterruptedException {
            lock.lock();
            try {
                //定义哲学家左右叉子编号
                int leftFork = philosopher;
                int rightFork = (philosopher + 1) % 5;

                //左右两边任意一个叉子被占用，线程等待
                while (forks[leftFork] || forks[rightFork]) {
                    conditions[philosopher].await();
                }

                //占用叉子
                forks[leftFork] = true;
                forks[rightFork] = true;

                //吃面
                pickLeftFork.run();
                pickRightFork.run();
                eat.run();

                //放下左边叉子
                putLeftFork.run();
                forks[leftFork] = false;
                //唤醒左边哲学家
                conditions[leftFork].signal();
                //放下右边叉子
                putRightFork.run();
                //唤醒右边哲学家
                forks[rightFork] = false;
                conditions[rightFork].signal();
            } finally {
                lock.unlock();
            }

        }
    }
}



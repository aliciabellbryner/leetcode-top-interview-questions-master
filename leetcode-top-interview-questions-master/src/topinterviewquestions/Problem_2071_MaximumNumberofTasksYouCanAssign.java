package topinterviewquestions;

import java.util.*;

public class Problem_2071_MaximumNumberofTasksYouCanAssign {
    /*
    We can also check from the least strong worker and go from weaker to stronger. Therefore, whether to start from the strongest doesn't seem to be critical here, but starting from the hardest work matters.

Here is a strategy that also achieves a solution, but checks the workers in a reversed order:

Starting from the least strong worker, to the strongest: check if the worker has any affordable task to pick.
If the worker has an affordable task, then feed him with the hardest task he can afford, and proceed;
If no task is suitable for the current worker, feed him with pill and check again;
If there is no pill available, or there is no task the current worker can pick even after taking the pill, the check fails, return false;
The check succeeds if all workers are able to get assigned a task from Tasks[0] ~ Tasks[k - 1], return true.
     */
    class Solution {
        public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
            Arrays.sort(tasks);
            Arrays.sort(workers);
            int l = 0, r = Math.min(tasks.length, workers.length);
            while (l + 1 < r) {
                int mid = l + (r - l) / 2;
                if (checkAns(tasks, workers, pills, strength, mid)) {
                    l = mid;
                } else {
                    r = mid;
                }
            }
            if (l + 1 == r) {
                return checkAns(tasks, workers, pills, strength, r) ? r : l;
            } else {
                return l;
            }
        }

        public boolean checkAns(int[] tasks, int[] workers, int pills, int strength, int k) {
            TreeSet<int[]> taskSet = new TreeSet<>((e1, e2) -> (e1[1] == e2[1]) ? (e1[0] - e2[0]) : (e1[1] - e2[1]));
            for (int i = 0; i < k; i++) {
                int[] element = new int[]{i, tasks[i]};
                taskSet.add(element);
            }
            for (int i = 0; i < k; i++) {
                int worker = workers[workers.length - k + i];
                if (taskSet.first()[1] <= worker) {
                    // assign the largest possible task
                    int[] task = taskSet.floor(new int[] {Integer.MAX_VALUE, worker});
                    taskSet.remove(task);
                } else if (taskSet.first()[1] <= worker + strength && pills > 0) {
                    // assign the largest possible task
                    pills--;
                    int[] task = taskSet.floor(new int[] {Integer.MAX_VALUE, worker + strength});
                    taskSet.remove(task);
                } else {
                    return false;
                }
            }
            return true;
        }
    }


    //solution 2: JAVA Solution with Monotonic Queue
    class Solution2 {
        public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
            int left = 0, right = Math.min(tasks.length, workers.length);
            Arrays.sort(tasks);
            Arrays.sort(workers);
            while(left+1<right)
            {
                int mid = left + (right - left)/2;
                if(canAssign(mid, tasks, workers, pills, strength))
                {
                    left = mid;
                }
                else
                {
                    right = mid;
                }
            }

            if(canAssign(right, tasks, workers, pills, strength)) {
                return right;
            } else {
                return left;
            }
        }

        public boolean canAssign(int count, int[] tasks, int[] workers, int pills, int strength){
            Deque<Integer> dq = new ArrayDeque<>();
            int ind = workers.length - 1;
            for (int i = count - 1; i >= 0; i--) {
                while(ind>=workers.length-count && workers[ind]+strength>=tasks[i])
                {
                    dq.offerLast(workers[ind]);
                    ind--;
                }

                if(dq.isEmpty())return false;
                if(dq.peekFirst()>=tasks[i])
                {
                    dq.pollFirst();
                }
                else
                {
                    dq.pollLast();
                    pills--;
                    if(pills<0)return false;
                }
            }

            return true;
        }
    }
}

package topinterviewquestions;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Problem_1834_SingleThreadedCPU_G {
    //https://leetcode.com/problems/single-threaded-cpu/discuss/1164102/Java:-sort-by-time-and-use-PQ/910519
    //I just want share some ideas with you guys : There are 2 situations: 1:no task can be executed E.g. t = 0, [1,2], queue={}, then t = 1 which next start time
    //Situation 2: add all task start time < time into the heap, then pick task, execute this task,
    // update task to the finish time t = t + duration. E.g. t = 1, queue={[1,2]}, poll [1,2] from queue, t = 1+2 = 3.
    public int[] getOrder(int[][] tasks) {
        int[][] arr = new int[tasks.length][3];
        for(int i = 0;i<tasks.length;i++){
            arr[i] = new int[]{tasks[i][0],tasks[i][1],i};
        }
        //sort arr with start time
        Arrays.sort(arr,(a, b)->{
            if(a[0] != b[0]) return a[0]-b[0];
            else return a[1] - b[1];//least duration come first
        });
        //create heap by pq and sort by duration and start time
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b)->{
            if(a[1] != b[1]) return a[1] - b[1];
            else return a[2] - b[2];//least start time come first
        });
        int[] ans = new int[tasks.length];
        int index = 0;
        int t = 0;
        //There are 2 situations:
        //1:no task can be executed E.g. t = 0, [1,2], queue={}, then t = next start time
        //2:there are task start time <= t, add task into queue, then pick the task with least duration
        //  and update t = t + duration
        int i = 0;
        while(i<arr.length){
            //add task into heap
            while(i<arr.length && arr[i][0] <= t){
                pq.offer(arr[i]);
                i++;
            }
            //Situation 1: no task can be executed at time t, update time t = next task's start time
            if(pq.isEmpty()){
                t = arr[i][0];
            }
            //Situation 2:pick task, execute task and update t = t + duration
            if(!pq.isEmpty()){
                int[] curr = pq.poll();
                ans[index] = curr[2];
                index++;
                t += curr[1];
            }
        }
        //Finsh the rest of task in queue
        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            ans[index] = curr[2];
            index++;
        }
        return ans;
    }
}

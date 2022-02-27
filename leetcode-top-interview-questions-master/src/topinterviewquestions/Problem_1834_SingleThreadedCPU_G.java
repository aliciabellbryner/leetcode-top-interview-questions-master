package topinterviewquestions;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Problem_1834_SingleThreadedCPU_G {
    //https://leetcode.com/problems/single-threaded-cpu/discuss/1164102/Java:-sort-by-time-and-use-PQ/910519
    //I just want share some ideas with you guys : There are 2 situations: 1:no task can be executed E.g. t = 0, [1,2], queue={}, then t = 1 which next start time
    //Situation 2: add all task start time < time into the heap, then pick task, execute this task,
    // update task to the finish time t = t + duration. E.g. t = 1, queue={[1,2]}, poll [1,2] from queue, t = 1+2 = 3.
    public int[] getOrder(int[][] tasks) {
        int N = tasks.length;
        int[][] arr = new int[N][3];
        for(int i = 0;i<N;i++){
            arr[i] = new int[]{tasks[i][0],tasks[i][1],i};
        }
        //sort arr with start time
        Arrays.sort(arr,(a, b)-> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);//earliest task come first and then least duration come first
        //create heap by pq and sort by duration and start time
        //a[2] - b[2]: If multiple tasks have the same shortest processing time, it will choose the task with the smallest index.
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b)-> a[1] != b[1] ? a[1] - b[1] : a[2] - b[2]);//least start time come first
        int[] res = new int[N];
        int index = 0;
        int time = 0;
        //There are 2 situations:
        //1:no task can be executed E.g. time = 0, [1,2], queue={}, then time = next start time
        //2:there are task start time <= time, add task into queue, then pick the task with least duration
        //  and update time = time + duration
        int i = 0;
        while(i<arr.length){
            //add task into heap
            while(i<arr.length && arr[i][0] <= time){//arr[i][0] is the start time
                pq.offer(arr[i]);
                i++;
            }
            //Situation 1: no task can be executed at time time, update time time = next task's start time
            if(pq.isEmpty()){
                time = arr[i][0];
            }
            //Situation 2:pick task, execute task and update time = time + duration
            if(!pq.isEmpty()){
                int[] curr = pq.poll();
                res[index] = curr[2];
                index++;
                time += curr[1];
            }
        }
        //Finsh the rest of task in queue
        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            res[index] = curr[2];
            index++;
        }
        return res;
    }
}

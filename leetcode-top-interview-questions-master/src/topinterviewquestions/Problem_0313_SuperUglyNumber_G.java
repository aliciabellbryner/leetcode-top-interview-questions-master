package topinterviewquestions;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Problem_0313_SuperUglyNumber_G {


    //It is actually like how we merge k sorted list:
    //https://leetcode.com/problems/super-ugly-number/discuss/277313/My-view-of-this-question-hope-it-can-help-you-understand!!!
    //Here, each entry has three parts: {num, prime, index},
    // num represents the value of the node, prime means which sorted list this node is in, and index tells us how far we have gone in that list,
    // it works like the next pointer in linkedlist, help us find the next node in that sorted list.
    //Time: O(nlogk)
    //Space: O(n+k)

    static class Solution {
        public static int nthSuperUglyNumber(int n, int[] primes) {
            PriorityQueue<int[]> queue=new PriorityQueue<>((a,b)->(a[0]-b[0]));
            for (int i=0;i<primes.length;i++)
                queue.offer(new int[]{primes[i], primes[i], 0});

            int[] nums=new int[n+1];
            nums[0]=1;

            int i=1;
            while (i<n){
                int[] entry=queue.poll();
                int num=entry[0], prime=entry[1], index=entry[2];
                // remove duplicate
                if (num!=nums[i-1]){
                    nums[i]=num;
                    i++;
                }
                queue.offer(new int[]{prime*nums[index+1], prime, index+1});
            }
            return nums[n-1];
        }
    }

    public static void main(String[] args) {
        int[] primes = new int[]{2,7,13,19};
        System.out.println(Solution.nthSuperUglyNumber(12, primes));
    }


    //https://leetcode.com/problems/super-ugly-number/discuss/76291/Java-three-methods-23ms-36-ms-58ms(with-heap)-performance-explained/79857
    class Solution1 {
        public int nthSuperUglyNumber(int n, int[] primes) {
            PriorityQueue<int[]> queue=new PriorityQueue<>((a, b)->(a[0]-b[0]));
            for (int i=0;i<primes.length;i++)
                queue.offer(new int[]{primes[i], primes[i], 0});

            int[] nums=new int[n+1];
            nums[0]=1;

            int i=1;
            while (i<n){
                int[] entry=queue.poll();
                int num=entry[0], prime=entry[1], index=entry[2];
                if (num!=nums[i-1]){
                    nums[i]=num;
                    i++;
                }
                queue.offer(new int[]{prime*nums[index+1], prime, index+1});
            }
            return nums[n-1];
        }
    }

    //https://leetcode.com/problems/super-ugly-number/discuss/76291/Java-three-methods-23ms-36-ms-58ms(with-heap)-performance-explained/79873

    public class Solution2 {
        public int nthSuperUglyNumber(int n, int[] primes) {

            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b)->(a[0]-b[0]));

            int[] res = new int[n];
            res[0] = 1;

            for(int i = 0; i < primes.length; i++){
                pq.offer(new int[] {primes[i], 0, primes[i]});
            }

            for(int i = 1; i < n; i++){
                int next = pq.peek()[0];
                res[i] = next;
                while(pq.peek()[0] == next){
                    int[] cur = pq.poll();
                    cur[0] = cur[2]*res[cur[1]];
                    cur[1] = cur[1]+1;
                    pq.offer(cur);
                }
            }

            return res[n-1];
        }
    }
}

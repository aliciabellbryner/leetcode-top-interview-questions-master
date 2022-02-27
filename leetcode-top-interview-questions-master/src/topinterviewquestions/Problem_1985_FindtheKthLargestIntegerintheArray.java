package topinterviewquestions;

import java.util.PriorityQueue;

public class Problem_1985_FindtheKthLargestIntegerintheArray {

    //Time complexity : O(N) in the average case, O(N^2) in the worst case.
    //Space complexity : O(1).
    class Solution {
        public String kthLargestNumber(String[] nums, int k) {
            return minKth(nums, nums.length + 1 - k);
        }

        public static String minKth(String[] arr, int k) {
            return process(arr, 0, arr.length - 1, k - 1);
        }

        public static String process(String[] arr, int L, int R, int index) {
            if (L == R) {
                return arr[L];
            }
            String pivot = arr[L + (int) (Math.random() * (R - L + 1))];
            int[] range = partition(arr, L, R, pivot);//range[0]是arr中开始等于pivot的index起点，range[1]是终点
            if (index >= range[0] && index <= range[1]) {
                return arr[index];
            } else if (index < range[0]) {
                return process(arr, L, range[0] - 1, index);
            } else {
                return process(arr, range[1] + 1, R, index);
            }
        }

        public static int[] partition(String[] arr, int L, int R, String pivot) {//返回的是arr数组中值等于pivot的区间的index范围，
            // 这个pivot等值区间的左边都是小于pivot的值，但是他们彼此并不sorting，右边都是大于pivot的值，但彼此也不sorting
            int less = L - 1;
            int more = R + 1;
            int cur = L;
            while (cur < more) {
                if (compare(arr[cur], pivot) < 0) {//arr[cur] < pivot
                    swap(arr, ++less, cur++);
                } else if (compare(arr[cur], pivot) > 0) {
                    swap(arr, cur, --more);
                } else {
                    cur++;
                }
            }
            return new int[] { less + 1, more - 1 };
        }

        private static int compare(String p1, String p2) {
            return p1.length() == p2.length() ?  p1.compareTo(p2) : Integer.compare(p1.length(), p2.length());
        }

        public static void swap(String[] arr, int i1, int i2) {
            String tmp = arr[i1];
            arr[i1] = arr[i2];
            arr[i2] = tmp;
        }
    }

    //也可以用priorityqueue的方法，但是时间复杂度要差一些
    // 利用小根堆，时间复杂度O(N*logK)
    //Time complexity : O(Nlogk).
    //Space complexity : O(k) to store the heap elements.
    public String kthLargestNumber2(String[] nums, int k) {
        // init minHeap 'the smallest element first'
        PriorityQueue<String> minHeap = new PriorityQueue<String>((n1, n2) -> (n1.length() == n2.length() ? n1.compareTo(n2) : Integer.compare(n1.length(), n2.length())));

        // keep k largest elements in the minHeap
        for (String n: nums) {
            minHeap.add(n);
            if (minHeap.size() > k)
                minHeap.poll();
        }

        // output
        return minHeap.poll();
    }

}

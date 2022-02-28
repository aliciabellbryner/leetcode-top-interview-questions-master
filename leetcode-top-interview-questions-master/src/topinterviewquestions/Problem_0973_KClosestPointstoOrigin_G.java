package topinterviewquestions;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane and an integer k, return the k closest points to the origin (0, 0).

The distance between two points on the X-Y plane is the Euclidean distance (i.e., √(x1 - x2)2 + (y1 - y2)2).

You may return the answer in any order. The answer is guaranteed to be unique (except for the order that it is in).



Example 1:


Input: points = [[1,3],[-2,2]], k = 1
Output: [[-2,2]]
Explanation:
The distance between (1, 3) and the origin is sqrt(10).
The distance between (-2, 2) and the origin is sqrt(8).
Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
We only want the closest k = 1 points from the origin, so the answer is just [[-2,2]].
Example 2:

Input: points = [[3,3],[5,-1],[-2,4]], k = 2
Output: [[3,3],[-2,4]]
Explanation: The answer [[-2,4],[3,3]] would also be accepted.


Constraints:

1 <= k <= points.length <= 104
-104 < xi, yi < 104
 */
public class Problem_0973_KClosestPointstoOrigin_G {
    //https://leetcode.com/problems/k-closest-points-to-origin/discuss/220235/Java-Three-solutions-to-this-classical-K-th-problem.

//quick select.
    //Theoretically, the average time complexity is O(N) , but just like quick sort, in the worst case, this solution would be degenerated to O(N^2), and pratically, the real time it takes on leetcode is 15ms.
    //
    //The advantage of this solution is it is very efficient.
    //The disadvatage of this solution are it is neither an online solution nor a stable one. And the K elements closest are not sorted in ascending order.
class Solution3 {

    public int[][] kClosest3(int[][] points, int K) {
        int len = points.length, l = 0, r = len - 1;
        while (l <= r) {
            int mid = helper(points, l, r);
            if (mid == K) break;
            if (mid < K) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return Arrays.copyOfRange(points, 0, K);
    }

    private int helper(int[][] A, int l, int r) {
        int[] pivot = A[l];
        while (l < r) {
            while (l < r && compare(A[r], pivot) >= 0) r--;
            A[l] = A[r];
            while (l < r && compare(A[l], pivot) <= 0) l++;
            A[r] = A[l];
        }
        A[l] = pivot;
        return l;
    }

    private int compare(int[] p1, int[] p2) {
        return p1[0] * p1[0] + p1[1] * p1[1] - p2[0] * p2[0] - p2[1] * p2[1];
    }
}



//I. The very naive and simple solution is sorting the all points by their distance to the origin point directly, then get the top k closest points. We can use the sort function and the code is very short.
//
//Theoretically, the time complexity is O(NlogN), pratically, the real time it takes on leetcode is 104ms.
//
//The advantages of this solution are short, intuitive and easy to implement.
//The disadvatages of this solution are not very efficient and have to know all of the points previously, and it is unable to deal with real-time(online) case, it is an off-line solution.
//
//The short code shows as follows:
public int[][] kClosest1(int[][] points, int K) {
    Arrays.sort(points, (p1, p2) -> p1[0] * p1[0] + p1[1] * p1[1] - p2[0] * p2[0] - p2[1] * p2[1]);
    return Arrays.copyOfRange(points, 0, K);
}


// max-heap with size K.
    //Theoretically, the time complexity is O(NlogK), but pratically, the real time it takes on leetcode is 134ms.
//
//The advantage of this solution is it can deal with real-time(online) stream data. It does not have to know the size of the data previously.
//The disadvatage of this solution is it is not the most efficient solution.
    public int[][] kClosest2(int[][] points, int K) {
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((p1, p2) -> p2[0] * p2[0] + p2[1] * p2[1] - p1[0] * p1[0] - p1[1] * p1[1]);
        for (int[] p : points) {
            pq.offer(p);
            if (pq.size() > K) {
                pq.poll();
            }
        }
        int[][] res = new int[K][2];
        while (K > 0) {
            res[--K] = pq.poll();
        }
        return res;
    }
}

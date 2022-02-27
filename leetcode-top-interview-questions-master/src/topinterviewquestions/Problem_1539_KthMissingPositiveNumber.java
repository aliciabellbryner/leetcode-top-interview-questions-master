package topinterviewquestions;
/*
Given an array arr of positive integers sorted in a strictly increasing order, and an integer k.

Find the kth positive integer that is missing from this array.



Example 1:

Input: arr = [2,3,4,7,11], k = 5
Output: 9
Explanation: The missing positive integers are [1,5,6,8,9,10,12,13,...]. The 5th missing positive integer is 9.
Example 2:

Input: arr = [1,2,3,4], k = 2
Output: 6
Explanation: The missing positive integers are [5,6,7,...]. The 2nd missing positive integer is 6.


Constraints:

1 <= arr.length <= 1000
1 <= arr[i] <= 1000
1 <= k <= 1000
arr[i] < arr[j] for 1 <= i < j <= arr.length
 */
public class Problem_1539_KthMissingPositiveNumber {
    //diss
    //https://leetcode.com/problems/kth-missing-positive-number/discuss/779999/JavaC%2B%2BPython-O(logN)
    /*
    Explanation
Assume the final result is x,
And there are m number not missing in the range of [1, x].
Binary search the m in range [0, A.size()].

If there are m number not missing,
that is A[0], A[1] .. A[m-1],
the number of missing under A[m] is A[m] - 1 - m.

If A[m] - 1 - m < k, m is too small, we update left = m.
If A[m] - 1 - m >= k, m is big enough, we update right = m.

Note that, we exit the while loop, l = r,
which equals to the number of missing number used.
So the Kth positive number will be l + k.


Complexity
Time O(logN)
Space O(1)
     */
    public int findKthPositive(int[] A, int k) {
        int l = 0, r = A.length, mid;
        while (l < r) {
            mid = (l + r) / 2;
            if (A[mid] - 1 - mid < k)//the number of missing under 就是一共少了多少个num
                l = mid + 1;
            else
                r = mid;
        }
        return l + k;
    }

    //solution 2
    //https://leetcode.com/problems/kth-missing-positive-number/discuss/876751/Java-1-liner-O(n)-simplest-easy-to-understand-beats-100
    //Note that the array is in strictly increasing order and hence there is no repetition.
    //
    //Think of this case, if every element in the array > k , the answer would be k.
    //
    //So, for every element <= k , you need to increment k. (i.e. when you iterate from left to right). And since the array is in increasing order, you can break out of the loop on the first instance this condition fails.
    class Solution {
        public int findKthPositive(int[] arr, int k) {
            for(int i : arr){
                if(i <= k) {
                    k++;
                } else {
                    break;
                }
            }
            return k;
        }
    }
}

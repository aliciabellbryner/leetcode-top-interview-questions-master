package topinterviewquestions;
/*
You have an array arr of length n where arr[i] = (2 * i) + 1 for all valid values of i (i.e., 0 <= i < n).

In one operation, you can select two indices x and y where 0 <= x, y < n and subtract 1 from arr[x] and add 1 to arr[y] (i.e., perform arr[x] -=1 and arr[y] += 1). The goal is to make all the elements of the array equal. It is guaranteed that all the elements of the array can be made equal using some operations.

Given an integer n, the length of the array, return the minimum number of operations needed to make all the elements of arr equal.



Example 1:

Input: n = 3
Output: 2
Explanation: arr = [1, 3, 5]
First operation choose x = 2 and y = 0, this leads arr to be [2, 3, 4]
In the second operation choose x = 2 and y = 0 again, thus arr = [3, 3, 3].
Example 2:

Input: n = 6
Output: 9


Constraints:

1 <= n <= 104
 */
public class Problem_1551_MinimumOperationstoMakeArrayEqual {

    /*
    diss
    https://leetcode.com/problems/minimum-operations-to-make-array-equal/discuss/794163/Java-2-Lines
    if odd length:
need 2, 4 .. , 2 * ( n / 2) ops, that is n / 2 * (2 + 2 * (n / 2)) / 2 = n / 2 * (n / 2 + 1) ;

if even length:
need 1, 3 .. , 2 * ( n / 2) - 1 ops, that is n / 2 * (1 + 2 * (n / 2) - 1) / 2 = n / 2 * (n / 2) ;
     */
    public int minOperations(int n) {
        int cnt = n / 2;
        return cnt * (cnt + n % 2);
    }


    /*
    diss
    https://leetcode.com/problems/minimum-operations-to-make-array-equal/discuss/794236/JAVA-O(1)-Constant-time-solution-with-detailed-explanation.
     Basic Approach:
if n is odd, suppose n=5.
The array is :
1 3 5 7 9.
Here, we will have the middle element as 5.
We take 2 from 7 and add to 3 to make each one 5.
We take 4 from 9 and add to 1 to make each one 5.
Total steps: 2+4=6. (sum of first n/2 even numbers)
Sum of first k EVEN numbers =
Sum(i=1 to k) {2 * i} = 2 * ( Sum(i=1 to k){ i } )
= 2 * (k*(k+1))/2
= k*(k+1)
if n is even, suppose n=6.
The array is :
1 3 5 7 9 11.
Here, we will have the middle element as (5+7)/2=6.
We take 1 from 7 and add to 5 to make each one 6.
We take 3 from 9 and add to 3 to make each one 6.
We take 5 from 11 and add to 1 to make each one 6.
Total steps: 1+3+5=9. (sum of first n/2 odd numbers)
Sum of first k ODD numbers = k * k.
     */
    class Solution {
        public int minOperations(int n) {
            // Take care of overflow if n is too large.
            if(n%2==1){
                n/=2;
                return (n*(n+1));
            }
            n/=2;
            return n*n;
        }
    }

}

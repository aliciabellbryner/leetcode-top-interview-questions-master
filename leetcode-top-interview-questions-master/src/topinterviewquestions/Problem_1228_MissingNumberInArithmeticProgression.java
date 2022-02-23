package topinterviewquestions;

import java.util.stream.IntStream;

/*
In some array arr, the values were in arithmetic progression: the values arr[i + 1] - arr[i] are all equal for every 0 <= i < arr.length - 1.

A value from arr was removed that was not the first or last value in the array.

Given arr, return the removed value.



Example 1:

Input: arr = [5,7,11,13]
Output: 9
Explanation: The previous array was [5,7,9,11,13].
Example 2:

Input: arr = [15,13,12]
Output: 14
Explanation: The previous array was [15,14,13,12].


Constraints:

3 <= arr.length <= 1000
0 <= arr[i] <= 105
The given array is guaranteed to be a valid array.
 */
public class Problem_1228_MissingNumberInArithmeticProgression {
    /*
    Approach 1: Linear search
Intuition

Let's try to find the missing number by linearly scanning the array from start to end. Since we are given that the first and the last numbers cannot be removed, we can use them to get the required difference between each pair of consecutive elements.

\text{difference} = \dfrac{\text{last value} - \text{first value}}{\text{number of values}}difference=
number of values
last value−first value
​


Once we have the difference we can use it to know what the value at each index is supposed to be. Using the difference as calculated above, and defining initial to be the value at index 0, we have the following:

\text{index 0} = \text{initial} \\ \text{index 1} = \text{initial} + \text{difference} \\ \text{index 2} = \text{initial} + 2 \cdot \text{difference} \\ \text{index 3} = \text{initial} + 3 \cdot \text{difference} \\ \dots \\ \text{index n} = \text{initial} + \text{n} \cdot \text{difference}index 0=initial
index 1=initial+difference
index 2=initial+2⋅difference
index 3=initial+3⋅difference
…
index n=initial+n⋅difference

Let's use this to figure out the first missing value in the Arithmetic Progression.

Algorithm

Get the value of difference using first and the last element, difference = last_value - first_value / number_of_values.
Start with the first element as expected value expected = first_element
Run a loop from the first value to the last while checking if the current value is equal to expected. If it is not, then increase expected by difference for the next iteration.
Return the first expected value that doesn't match value in the array`.

Complexity Analysis

Time complexity : O(n)O(n). Where nn is the length of array arr since in the worst case we iterate over the entire array.

Space complexity : O(1)O(1). Algorithm requires constant space to execute.


Approach 2: Binary Search
Intuition

In the previous approach we saw that we can get the value required at any index. Let's try to use that property to binary search for the missing value.

We know that there is only one missing number in the given progression. At any index i we can figure out if the value at index i is at the correct position by adding difference times i to the first value in the list, and then comparing it with the value at index i. if they match it means the missing value is in an index on the right of i else it's on the left of i or at i.

This fact can be used to find the index which has the first incorrect number using binary search because if i is the first index with an incorrect number all indices following i would be at incorrect positions (they should be present at 1 position further, since one number is missing) and all numbers before index i will be at correct position. This property is required for binary search to be possible.

Algorithm

Get the value of difference using first and the last element, difference = last_value - first_value / number_of_values.
Start with left index lo = 0 and right index hi = arr.size() - 1.
Pick a mid point index mid = (lo + hi) / 2.
If arr[mid] == first_element + mid * difference. Binary search on the right of mid else binary search on left side of mid including mid itself.
End when there is a single index left as this would be the first index with incorrect value.
Return the value supposed to be at this index which would be first_element + difference * index.

Complexity Analysis

Time complexity : O(\log n)O(logn).Where nn is the length of array arr since we cut the search space in half at every iteration.

Space complexity : O(1)O(1). Algorithm requires constant space to execute.


     */

    class Solution1 {
        public int missingNumber(int[] arr) {
            int n = arr.length;

            // Get the difference `difference`.
            int difference = (arr[arr.length - 1] - arr[0]) / n;

            // The expected element equals the starting element.
            int expected = arr[0];

            for (int val : arr) {
                // Return the expected value that doesn't match val.
                if (val != expected) return expected;

                // Next element will be expected element + `difference`.
                expected += difference;
            }
            return expected;
        }
    }

    class Solution2 {
        public int missingNumber(int arr[]) {
            int n = arr.length;

            // 1. Get the difference `difference`.
            int difference = (arr[n - 1] - arr[0]) / n;
            int lo = 0;
            int hi = n - 1;

            // Basic binary search template.
            while (lo < hi) {
                int mid = (lo + hi) / 2;

                // All numbers upto `mid` have no missing number, so search on the right side.
                if (arr[mid] == arr[0] + mid * difference) {
                    lo = mid + 1;
                }

                // A number is missing before `mid` inclusive of `mid` itself.
                else {
                    hi = mid;
                }
            }

            // Index `lo` will be the position with the first incorrect number.
            // Return the value that was supposed to be at this index.
            return arr[0] + difference * lo;
        }
    }


    //diss
    //Arithmetic Sum and Binary Search
    //Soluton 1: Arithmetic Sequence Sum
    //arithmetic sequence sum = (first + last) * n / 2
    //In this problem, the first and last value are not removed.
    //first = min(A), last = max(A)
    //We can calulate the sum of arithmetic sequence.
    //The difference between sum - sum(A) is the missing number.
    //
    //
    //Complexity
    //Time O(N)
    //Space O(1)
    public int missingNumber(int[] A) {
        int first = A[0], last = A[0], sum = 0, n = A.length;
        for (int a : A) {
            first = Math.min(first, a);
            last = Math.max(last, a);
            sum += a;
        }
        return (first + last) * (n + 1) / 2 - sum;
    }


    //another
    class Solution
    {
        public int missingNumber(int[] arr)
        {
            int diff = (arr[arr.length - 1] - arr[0]) / arr.length;
            int leftAllGood = 1;
            int rightNotGood = arr.length - 2;
            while (leftAllGood <= rightNotGood)
            {
                int mid = leftAllGood + (rightNotGood - leftAllGood) / 2;
                if (arr[mid] == diff * mid + arr[0])
                {
                    leftAllGood = mid + 1;
                }
                else
                {
                    rightNotGood = mid - 1;
                }
            }
            return arr[leftAllGood] - diff;
        }
    }

    //another
    class Solution3 {
        public int missingNumber(int[] arr)
        {
            int diff = Math.min(arr[1] - arr[0], arr[2] - arr[1]);
            int idx = IntStream.range(1, arr.length).filter(i -> arr[i] - arr[i - 1] != diff).findFirst().orElse(0);
            return arr[idx] - diff;
        }
    }
}

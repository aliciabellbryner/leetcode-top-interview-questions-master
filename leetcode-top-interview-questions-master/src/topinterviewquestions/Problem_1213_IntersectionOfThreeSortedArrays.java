package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

/*
Given three integer arrays arr1, arr2 and arr3 sorted in strictly increasing order, return a sorted array of only the integers that appeared in all three arrays.



Example 1:

Input: arr1 = [1,2,3,4,5], arr2 = [1,2,5,7,9], arr3 = [1,3,4,5,8]
Output: [1,5]
Explanation: Only 1 and 5 appeared in the three arrays.
Example 2:

Input: arr1 = [197,418,523,876,1356], arr2 = [501,880,1593,1710,1870], arr3 = [521,682,1337,1395,1764]
Output: []


Constraints:

1 <= arr1.length, arr2.length, arr3.length <= 1000
1 <= arr1[i], arr2[i], arr3[i] <= 2000
 */
public class Problem_1213_IntersectionOfThreeSortedArrays {
    /*
    Approach 1: Brute Force with Hashmap
One of the most straightforward approaches would be counting the frequencies of each item in arr1, arr2, and arr3 so that we would be able to find the numbers that appear exactly three times. This is feasible because all of the three arrays are strictly increasing, hence we can rule out the possibility that some element appears more than once in any of the arrays.

Algorithm

We would initiate a Hashmap counter to record the numbers that appear in the three arrays and the number of times they appear;
then we scan arr1, arr2, and arr3 to count the frequencies;
finally, we would iterate through counter to find the numbers that appear three times.

Complexity Analysis

Time Complexity: \mathcal{O}(n)O(n), where nn is the total length of all of the input arrays.
Space Complexity: \mathcal{O}(n)O(n), where nn is the total length of all of the input arrays. This is because we adopted a Hashhmap to store all numbers and their number of appearances.
Approach 2: Three Pointers
You may notice that Approach 1 does not utilize the fact that all arrays are sorted. Indeed, instead of using a Hashmap to store the frequencies, we can use three pointers p1, p2, and p3 to iterate through arr1, arr2, and arr3 accordingly:

Each time, we want to increment the pointer that points to the smallest number, i.e., min(arr1[p1], arr2[p2], arr3[p3]) forward;
if the numbers pointed to by p1, p2, and p3 are the same, we should then store it and move all three pointers forward.
Moreover, we don't have to move the pointer pointing to the smallest number - we only need to move the pointer pointing to a smaller number. In this case, we avoid comparing three numbers and finding the smallest one before deciding which one to move. You may find the rationale behind this in the Algorithm.

Current
1 / 8
Algorithm

Initiate three pointers p1, p2, p3, and place them at the beginning of arr1, arr2, arr3 by initializing them to 0;
while they are within the boundaries:
if arr1[p1] == arr2[p2] && arr2[p2] == arr3[p3], we should store it because it appears three times in arr1, arr2, and arr3;
else
if arr1[p1] < arr2[p2], move the smaller one, i.e., p1;
else if arr2[p2] < arr3[p3], move the smaller one, i.e., p2;
if neither of the above conditions is met, it means arr1[p1] >= arr2[p2] && arr2[p2] >= arr3[p3], therefore move p3.

Complexity Analysis

Time Complexity: \mathcal{O}(n)O(n), where nn is the total length of all of the input arrays.
Space Complexity: \mathcal{O}(1)O(1), as we only initiate three integer variables using constant space.
     */
    class Solution {
        public List<Integer> arraysIntersection(int[] arr1, int[] arr2, int[] arr3) {
            List<Integer> ans = new ArrayList<>();
            // prepare three pointers to iterate through three arrays
            // p1, p2, and p3 point to the beginning of arr1, arr2, and arr3 accordingly
            int p1 = 0, p2 = 0, p3 = 0;

            while (p1 < arr1.length && p2 < arr2.length && p3 < arr3.length) {

                if (arr1[p1] == arr2[p2] && arr2[p2] == arr3[p3]) {
                    ans.add(arr1[p1]);
                    p1++;
                    p2++;
                    p3++;
                } else {
                    if (arr1[p1] < arr2[p2]) {
                        p1++;
                    } else if (arr2[p2] < arr3[p3]) {
                        p2++;
                    } else {
                        p3++;
                    }

                }
            }
            return ans;
        }
    }

    //diss
    public List<Integer> arraysIntersection(int[] arr1, int[] arr2, int[] arr3) {

        List<Integer> result = new ArrayList<>();

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < arr1.length && j < arr2.length && k < arr3.length) {
            if (arr1[i] == arr2[j] && arr2[j] == arr3[k]) {
                result.add(arr1[i]);
                i++;
                j++;
                k++;
            } else if (arr1[i] < arr2[j]) {
                i++;
            } else if (arr2[j] < arr3[k]) {
                j++;
            } else k++;
        }

        return result;

    }


    //Using 3 pointers in 3 arrays starting at 0. Iterate untill one of them reaches the end of its array.
    //At any given time, if 3 number pointed by 3 pointers are the same, add it to the list and move 3 pointers to the right.
    //If they aren't the same, move the pointers that point to smaller number to the right.
    class Solution2 {
        public List<Integer> arraysIntersection(int[] arr1, int[] arr2, int[] arr3) {
            List<Integer> list = new ArrayList();
            int p1 = 0, p2 = 0, p3 = 0;
            while (p1 < arr1.length && p2 < arr2.length && p3 < arr3.length) {
                int min = Math.min(arr1[p1], Math.min(arr2[p2], arr3[p3]));
                if (arr1[p1] == arr2[p2] && arr1[p1] == arr3[p3]) list.add(arr1[p1]);
                if (arr1[p1] == min) p1++;
                if (arr2[p2] == min) p2++;
                if (arr3[p3] == min) p3++;
            }
            return list;
        }
    }

}

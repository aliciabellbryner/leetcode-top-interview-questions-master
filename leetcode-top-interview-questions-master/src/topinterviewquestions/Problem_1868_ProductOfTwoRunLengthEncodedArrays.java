package topinterviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Run-length encoding is a compression algorithm that allows for an integer array nums with many segments of consecutive repeated numbers to be represented by a (generally smaller) 2D array encoded. Each encoded[i] = [vali, freqi] describes the ith segment of repeated numbers in nums where vali is the value that is repeated freqi times.

For example, nums = [1,1,1,2,2,2,2,2] is represented by the run-length encoded array encoded = [[1,3],[2,5]]. Another way to read this is "three 1's followed by five 2's".
The product of two run-length encoded arrays encoded1 and encoded2 can be calculated using the following steps:

Expand both encoded1 and encoded2 into the full arrays nums1 and nums2 respectively.
Create a new array prodNums of length nums1.length and set prodNums[i] = nums1[i] * nums2[i].
Compress prodNums into a run-length encoded array and return it.
You are given two run-length encoded arrays encoded1 and encoded2 representing full arrays nums1 and nums2 respectively. Both nums1 and nums2 have the same length. Each encoded1[i] = [vali, freqi] describes the ith segment of nums1, and each encoded2[j] = [valj, freqj] describes the jth segment of nums2.

Return the product of encoded1 and encoded2.

Note: Compression should be done such that the run-length encoded array has the minimum possible length.



Example 1:

Input: encoded1 = [[1,3],[2,3]], encoded2 = [[6,3],[3,3]]
Output: [[6,6]]
Explanation: encoded1 expands to [1,1,1,2,2,2] and encoded2 expands to [6,6,6,3,3,3].
prodNums = [6,6,6,6,6,6], which is compressed into the run-length encoded array [[6,6]].
Example 2:

Input: encoded1 = [[1,3],[2,1],[3,2]], encoded2 = [[2,3],[3,3]]
Output: [[2,3],[6,1],[9,2]]
Explanation: encoded1 expands to [1,1,1,2,3,3] and encoded2 expands to [2,2,2,3,3,3].
prodNums = [2,2,2,6,9,9], which is compressed into the run-length encoded array [[2,3],[6,1],[9,2]].


Constraints:

1 <= encoded1.length, encoded2.length <= 105
encoded1[i].length == 2
encoded2[j].length == 2
1 <= vali, freqi <= 104 for each encoded1[i].
1 <= valj, freqj <= 104 for each encoded2[j].
The full arrays that encoded1 and encoded2 represent are the same length.
 */
public class Problem_1868_ProductOfTwoRunLengthEncodedArrays {
    //diss two pointers
    class Solution {
        public List<List<Integer>> findRLEArray(int[][] encoded1, int[][] encoded2) {
            int i1 = 0, i2 = 0;
            int f1 = 0, f2 = 0;
            int p = 0;
            int len1 = encoded1.length, len2 = encoded2.length;
            List<int[]> lst = new ArrayList<>();
            while (i1 < len1 && i2 < len2) {
                f1 = encoded1[i1][1];
                f2 = encoded2[i2][1];
                p = encoded1[i1][0] * encoded2[i2][0];
                if (f1 == f2) {
                    lst.add(new int[]{p, f1});
                    i1++;
                    i2++;
                } else if (f1 < f2) {
                    lst.add(new int[]{p, f1});
                    encoded2[i2][1] = f2 - f1;
                    i1++;
                } else {
                    lst.add(new int[]{p, f2});
                    encoded1[i1][1] = f1 - f2;
                    i2++;
                }
            }
            //for (int[] arr : lst) System.out.println(Arrays.toString(arr));
            List<List<Integer>> res = new ArrayList<>();

            int[] cur = lst.get(0);
            for (int i = 1; i < lst.size(); i++) {
                if (lst.get(i)[0] != cur[0]) {
                    res.add(Arrays.asList(new Integer[]{cur[0], cur[1]})); // if diff from prev, add to result.
                    cur = lst.get(i);
                } else {
                    cur[1] += lst.get(i)[1]; // if same as prev, then add freq.
                }
            }
            res.add(Arrays.asList(new Integer[]{cur[0], cur[1]})); // last one
            return res;

        }
    }


    //Single pass, doesn't modify the input, beats 100%:
    public List<List<Integer>> findRLEArray(int[][] encoded1, int[][] encoded2) {
        if (encoded1 == null || encoded2 == null && encoded1.length == 0 || encoded2.length == 0) {
            return new ArrayList<>();
        }
        List<List<Integer>> result = new ArrayList<>();
        int i = 0, j = 0;
        int[] first = encoded1[i], second = encoded2[j];
        while (i < encoded1.length && j < encoded2.length) {
            if (first[1] < second[1]) {
                addToResult(result, first[0] * second[0], first[1]);
                second[1] -= first[1];      // Reduce frequency of more frequent one.
                first = encoded1[++i];      // Less frequent one is fully used up. Can't be out of bounds because the two fully encoded arrays have an equal length.

            } else if (first[1] > second[1]) {
                addToResult(result, first[0] * second[0], second[1]);
                first[1] -= second[1];
                second = encoded2[++j];

            } else {
                addToResult(result, first[0] * second[0], first[1]);
                first = ++i < encoded1.length ? encoded1[i] : null;
                second = ++j < encoded2.length ? encoded2[j] : null;
            }
        }
        return result;
    }

    private void addToResult(List<List<Integer>> result, int value, int frequency) {
        if (result.size() == 0 || result.get(result.size() - 1).get(0) != value) {
            result.add(Arrays.asList(value, frequency));
        } else {//说明有重复的乘积，所以需要删掉之前的freq，换上updated的乘积
            List<Integer> last = result.remove(result.size() - 1);
            result.add(Arrays.asList(last.get(0), last.get(1) + frequency));
        }
    }


}

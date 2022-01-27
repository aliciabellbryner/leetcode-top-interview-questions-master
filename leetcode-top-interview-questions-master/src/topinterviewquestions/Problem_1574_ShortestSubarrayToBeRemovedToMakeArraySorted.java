package topinterviewquestions;

public class Problem_1574_ShortestSubarrayToBeRemovedToMakeArraySorted {
    //You need to find some sequence from 0th index which is in increasing order, let this sequence a_1 <= a_2 <= ... <= a_i
    //Then you need to find some sequence from the back which is in decreasing order such that b_j <= b_(j+1) <= ... <= b_n (j <= i)
    //Then it is guranteed you need to remove subarray from (i + 1, j - 1). But it is possible if we could merge some part from b_j <= b_(j+1) <= ... <= b_n,
    // with a_1 <= a_2 <= ... <= a_i, to create a bigger increasing subarray.
    public int findLengthOfShortestSubarray(int[] arr) {
        int N = arr.length, l = 0, r = N - 1;
        while(l + 1 < N && arr[l] <= arr[l + 1]) {
            l++;  // find increasing subarray [0..l]//注意包括l
        }
        if(l == N - 1) {
            return 0;
        }
        while(r - 1 >= 0 && arr[r - 1] <= arr[r]) {
            r--; // find increasing subarray [r..N-1],注意包括r
        }
        int firstToRemove = 0, res = Math.min(N - l - 1, r);
        while (firstToRemove <= l && r < N) {  // find max combination
            while(firstToRemove <= l && arr[firstToRemove] <= arr[r]) {
                firstToRemove++;
            }
            res = Math.min(res, r - firstToRemove);  // remove [firstToRemove...r - 1];
            while(firstToRemove < N && r < N && arr[firstToRemove] > arr[r]) {//说明后面半段递增序列第一个num就比前面的firstToRemove的小，所以r++
                r++;
            }
        }
        return res;
    }
}
